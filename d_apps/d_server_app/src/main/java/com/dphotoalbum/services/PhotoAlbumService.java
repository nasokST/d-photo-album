package com.dphotoalbum.services;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import com.dphotoalbum.config.DPhotoAlbumConfig;
import com.dphotoalbum.config.PhotoCategoryType;
import com.dphotoalbum.contracts.services.DPhotoCategoryContractService;
import com.dphotoalbum.ipfs.IPFSUtils;
import com.dphotoalbum.contracts.services.DPhotoAlbumContractService;
import com.dphotoalbum.objects.DPhotoIPFS;
import com.dphotoalbum.objects.DPhotoCommentIPFS;
import com.dphotoalbum.objects.DPhotoCommentsFile;
import com.dphotoalbum.objects.DPhotoCommentsInput;
import com.dphotoalbum.objects.DPhoto;
import com.dphotoalbum.objects.DPhotoComment;
import com.dphotoalbum.objects.IPFSHashUnpacked;
import com.dphotoalbum.objects.PhotoCategory;
import com.dphotoalbum.utils.DPhotoAlbumUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("photoAlbumService")
public class PhotoAlbumService {

	// /**
	// *
	// */
	// private static DPhotoAlbumContractService albumContractService;

	/**
	 * 
	 * @return
	 */
	public List<PhotoCategory> getAllcategories() {
		DPhotoAlbumContractService albumContractService = getAlbumService();
		return albumContractService.getAvailableCategories();
	}

	/**
	 * 
	 * @param dphoto
	 * @return
	 */
	public boolean addPhoto(DPhoto dphoto) {

		DPhotoAlbumContractService tmpAlbumContract = getAlbumService(dphoto.getPk());

		if (null != tmpAlbumContract) {

			IPFSHashUnpacked photoIpfsHash = IPFSUtils.uploadDoc("", dphoto.getFile());

			dphoto.setIpfsHashUnpacked(photoIpfsHash);

			return tmpAlbumContract.addPhoto(dphoto);
		}

		return false;
	}

	/**
	 * 
	 * @param dphotos
	 * @return
	 */
	public boolean addPhotos(List<DPhoto> dphotos) {

		boolean error = true;

		for (DPhoto photo : dphotos) {
			error &= addPhoto(photo);
		}

		return error;
	}

	/**
	 * 
	 * @param newPhotoComments
	 * @return
	 */
	public boolean addComments(DPhotoCommentsInput newPhotoComments) {
		DPhotoAlbumContractService tmpAlbumContract = getAlbumService(newPhotoComments.getPk());

		if (null != tmpAlbumContract) {

			// get existing comments
			DPhotoCommentsFile photoComments = getComments(newPhotoComments.getComments().getCategory().getValue(),
					newPhotoComments.getComments().getPhotoIndex().intValue());

			// add new comments to list
			for (DPhotoComment comment : newPhotoComments.getComments().getComments()) {
				comment.setAutor(tmpAlbumContract.getMsgSenderAddress());
				comment.setDateTime(DPhotoAlbumUtils.dateTimeToIsoStr(LocalDateTime.now()));
				photoComments.getComments().add(comment);
			}

			IPFSHashUnpacked commentsIpfsHash = null;

			ObjectMapper om = new ObjectMapper();
			try {
				String commentsFileStr = om.writeValueAsString(photoComments);

				commentsIpfsHash = IPFSUtils.uploadDoc("", commentsFileStr.getBytes(StandardCharsets.UTF_8));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			if (null != commentsIpfsHash) {
				DPhotoCommentIPFS ipfsPhotoComment = new DPhotoCommentIPFS();
				ipfsPhotoComment.setCategory(photoComments.getCategory());
				ipfsPhotoComment.setPhotoIndex(photoComments.getPhotoIndex());
				ipfsPhotoComment.setIpfsHash(commentsIpfsHash);

				return tmpAlbumContract.addPhotoComment(ipfsPhotoComment);
			}
		}

		return false;
	}

	/**
	 * 
	 * @param categoryId
	 * @param photoIndex
	 * @return
	 */
	public DPhotoCommentsFile getComments(long categoryId, long photoIndex) {

		DPhotoAlbumContractService albumContractService = getAlbumService();
		IPFSHashUnpacked commentHash = albumContractService.getComments(categoryId, photoIndex);

		if (null != commentHash) {
			DPhotoCommentsFile comments = null;

			try {

				byte[] commentsBts = IPFSUtils.downloadFile(commentHash);
				String commentsStr = new String(commentsBts);

				ObjectMapper objectMapper = new ObjectMapper();
				comments = objectMapper.readValue(commentsStr, DPhotoCommentsFile.class);

				return comments;

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		DPhotoCommentsFile commentFile = new DPhotoCommentsFile();
		commentFile.setCategory(PhotoCategoryType.forValue((int) categoryId));
		commentFile.setPhotoIndex(new BigInteger(String.valueOf(photoIndex)));
		commentFile.setComments(new ArrayList<DPhotoComment>());

		return commentFile;
	}

	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<DPhoto> getAllPhotosByCategory(long categoryId) {

		DPhotoAlbumContractService albumContractService = getAlbumService();
		List<DPhotoIPFS> ipfsPhotos = albumContractService.getAllPhotosByCategory(categoryId);

		List<DPhoto> photos = new ArrayList<>();
		for (DPhotoIPFS ipfsPhoto : ipfsPhotos) {

			DPhoto photo = new DPhoto(ipfsPhoto);

			// photo.setFile(IPFSUtils.downloadFile(ipfsPhoto.getIpfsHash()));
			// photo.setFileSize(photo.getFile().length);
			photo.setIpfsHash((IPFSUtils.hashInterfaceToIPFSHash(ipfsPhoto.getIpfsHashUnpacked())).toBase58());

			photos.add(photo);
		}

		return photos;
	}

	/**
	 * 
	 * @param photographer
	 * @param categoryId
	 * @return
	 */
	public List<DPhoto> getAllPhotosByPhotographerByCategory(String photographer, long categoryId) {

		DPhotoAlbumContractService albumContractService = getAlbumService();
		if (null == albumContractService) {
			return Collections.emptyList();
		}

		List<DPhotoIPFS> ipfsPhotos = albumContractService.getAllPhotosByCategoryAndPhotographer(photographer,
				categoryId);

		List<DPhoto> photos = new ArrayList<>();
		for (DPhotoIPFS ipfsPhoto : ipfsPhotos) {

			DPhoto photo = new DPhoto(ipfsPhoto);

			// photo.setFile(IPFSUtils.downloadFile(ipfsPhoto.getIpfsHash()));
			// photo.setFileSize(photo.getFile().length);
			photo.setIpfsHash((IPFSUtils.hashInterfaceToIPFSHash(ipfsPhoto.getIpfsHashUnpacked())).toBase58());

			photos.add(photo);
		}

		return photos;
	}

	/**
	 * 
	 * @param photographerPK
	 * @param categoryId
	 * @return
	 */
	public List<DPhoto> getAllPhotosByPhotographerPKByCategory(String photographerPK, long categoryId) {

		Credentials credentials = Credentials.create(photographerPK);

		return getAllPhotosByPhotographerByCategory(credentials.getAddress(), categoryId);
	}

	/**
	 * 
	 * @return
	 */
	public static DPhotoAlbumContractService getAlbumService() {
		return getAlbumService(DPhotoAlbumConfig.getPrivateKey());
	}

	/**
	 * 
	 * @param privateKey
	 * @return
	 */
	public static DPhotoAlbumContractService getAlbumService(String privateKey) {
		DPhotoAlbumContractService photoAlbumContractService = new DPhotoAlbumContractService(
				DPhotoAlbumConfig.getWeb3jProvider(), privateKey);
		photoAlbumContractService.load(DPhotoAlbumConfig.getAlbumContractAddress());
		return photoAlbumContractService;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean deployAlbum() {

		DPhotoAlbumContractService photoAlbumContractService = new DPhotoAlbumContractService(
				DPhotoAlbumConfig.getWeb3jProvider(), DPhotoAlbumConfig.getPrivateKey());

		if (DPhotoAlbumConfig.getAlbumContractAddress().isEmpty()) {
			photoAlbumContractService.deploy();
			System.out.println(photoAlbumContractService.getContractAddress());

			if (!photoAlbumContractService.getContractAddress().isEmpty()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param privateKey
	 * @return
	 */
	public static DPhotoAlbumContractService deployAlbumCategories() {

		DPhotoAlbumContractService photoAlbumContractService = getAlbumService();

		// adding base categories
		if (photoAlbumContractService.load(DPhotoAlbumConfig.getAlbumContractAddress())) {
			List<PhotoCategory> categories = photoAlbumContractService.getAvailableCategories();

			if (!isCategoriExists(PhotoCategoryType.FAMILY, categories)) {
				String categoryAddress = deployPhotoCategory(PhotoCategoryType.FAMILY);
				if (!categoryAddress.isEmpty()) {
					photoAlbumContractService.addPhotoCategory(categoryAddress);
				}
			}

			if (!isCategoriExists(PhotoCategoryType.MOUNTAIN, categories)) {
				String categoryAddress = deployPhotoCategory(PhotoCategoryType.MOUNTAIN);
				if (!categoryAddress.isEmpty()) {
					photoAlbumContractService.addPhotoCategory(categoryAddress);
				}
			}

			if (!isCategoriExists(PhotoCategoryType.SEA, categories)) {
				String categoryAddress = deployPhotoCategory(PhotoCategoryType.SEA);
				if (!categoryAddress.isEmpty()) {
					photoAlbumContractService.addPhotoCategory(categoryAddress);
				}
			}
		}

		return photoAlbumContractService;
	}

	/**
	 * 
	 * @param type
	 * @param categories
	 * @return
	 */
	private static boolean isCategoriExists(PhotoCategoryType type, List<PhotoCategory> categories) {
		List<PhotoCategory> result = categories.stream().filter(category -> type == category.getType())
				.collect(Collectors.toList());

		return (result.isEmpty()) ? false : true;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static String deployPhotoCategory(PhotoCategoryType type) {

		DPhotoCategoryContractService categoryService = new DPhotoCategoryContractService(
				DPhotoAlbumConfig.getWeb3jProvider(), DPhotoAlbumConfig.getPrivateKey());

		String address = categoryService.deploy(new BigInteger(String.valueOf(type.getValue())),
				DPhotoAlbumConfig.getAlbumContractAddress());

		return address;
	}
}
