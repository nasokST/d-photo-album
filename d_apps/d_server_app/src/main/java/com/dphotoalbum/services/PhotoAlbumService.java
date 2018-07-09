package com.dphotoalbum.services;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
import com.dphotoalbum.objects.IPFSHashInterface;
import com.dphotoalbum.objects.PhotoCategory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("photoAlbumService")
public class PhotoAlbumService {

	private static DPhotoAlbumContractService albumContractService;

	public List<PhotoCategory> getAllcategories() {
		return albumContractService.getAvailableCategories();
	}

	public boolean addPhotos(List<DPhoto> dphotos) {

		boolean error = true;

		for (DPhoto photo : dphotos) {
			error &= addPhoto(photo);
		}

		return error;
	}

	public boolean addPhoto(DPhoto dphoto) {

		DPhotoAlbumContractService tmpAlbumContract = initAlbum(dphoto.getPk());

		if(null != tmpAlbumContract) {

			IPFSHashInterface photoIpfsHash =
					IPFSUtils.uploadDoc("", dphoto.getFile());

			dphoto.setIpfsHash(photoIpfsHash);

			return tmpAlbumContract.addPhoto(dphoto);
		}

		return false;
	}

	public boolean addComments(DPhotoCommentsInput photoComments) {
		DPhotoAlbumContractService tmpAlbumContract = initAlbum(photoComments.getPk());

		if(null != tmpAlbumContract) {

			IPFSHashInterface commentsIpfsHash = null;

			ObjectMapper om = new ObjectMapper();
			try {
				String commentsFileStr = om.writeValueAsString(photoComments.getComments());

				commentsIpfsHash = IPFSUtils.uploadDoc("", commentsFileStr.getBytes(StandardCharsets.UTF_8));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			if (null != commentsIpfsHash) {
				DPhotoCommentIPFS ipfsPhotoComment = new DPhotoCommentIPFS();
				ipfsPhotoComment.setCategory(photoComments.getComments().getCategory());
				ipfsPhotoComment.setPhotoIndex(photoComments.getComments().getPhotoIndex());
				ipfsPhotoComment.setIpfsHash(commentsIpfsHash);

				return tmpAlbumContract.addPhotoComment(ipfsPhotoComment);
			}
		}

		return false;
	}

	public DPhotoCommentsFile getComments(long categoryId, long photoIndex) {

		IPFSHashInterface commentHash = albumContractService.getComments(categoryId, photoIndex);

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

		return new DPhotoCommentsFile();
	}

	public List<DPhoto> getAllPhotosByCategory(long categoryId) {

		List<DPhotoIPFS> ipfsPhotos = albumContractService.getAllPhotosByCategory(categoryId);

		List<DPhoto> photos = new ArrayList<>();		
		for (DPhotoIPFS ipfsPhoto : ipfsPhotos) {
			
			DPhoto photo = new DPhoto(ipfsPhoto);

			photo.setFile(IPFSUtils.downloadFile(ipfsPhoto.getIpfsHash()));
			photo.setFileSize(photo.getFile().length);

			photos.add(photo);
		}
		
		return photos;
	}

	public List<DPhoto> getAllPhotosByPhotographerByCategory(String photographer, long categoryId) {

		List<DPhotoIPFS> ipfsPhotos = albumContractService.getAllPhotosByCategoryAndPhotographer(photographer, categoryId);
		
		List<DPhoto> photos = new ArrayList<>();		
		for (DPhotoIPFS ipfsPhoto : ipfsPhotos) {
			
			DPhoto photo = new DPhoto(ipfsPhoto);

			photo.setFile(IPFSUtils.downloadFile(ipfsPhoto.getIpfsHash()));
			photo.setFileSize(photo.getFile().length);

			photos.add(photo);
		}
		
		return photos;		
	}

	public static boolean initAlbum() {
		albumContractService = initAlbum(DPhotoAlbumConfig.getPrivateKey());
		return (null != albumContractService);
	}

	public static DPhotoAlbumContractService initAlbum(String privateKey) {
		DPhotoAlbumContractService photoAlbumContractService = new DPhotoAlbumContractService(
				DPhotoAlbumConfig.getWeb3jProvider(), privateKey);
		// try {
		// photoAlbumService.deploy();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// adding base categories
		if (photoAlbumContractService.load(DPhotoAlbumConfig.getAlbumContractAddress())) {
			List<PhotoCategory> categories = photoAlbumContractService.getAvailableCategories();

			if (!isCategoriExists(PhotoCategoryType.FAMILY, categories)) {
				String categoryAddress = deployPhotoCategory(PhotoCategoryType.FAMILY);
				if (!categoryAddress.isEmpty()) {
					photoAlbumContractService.addPhotoCategory(categoryAddress);
				}
			}

			if (!isCategoriExists(PhotoCategoryType.MONTAIN, categories)) {
				String categoryAddress = deployPhotoCategory(PhotoCategoryType.MONTAIN);
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

	private static boolean isCategoriExists(PhotoCategoryType type, List<PhotoCategory> categories) {
		List<PhotoCategory> result = categories.stream().filter(category -> type == category.getType())
				.collect(Collectors.toList());

		return (result.isEmpty()) ? false : true;
	}

	private static String deployPhotoCategory(PhotoCategoryType type) {

		DPhotoCategoryContractService categoryService = new DPhotoCategoryContractService(
				DPhotoAlbumConfig.getWeb3jProvider(), DPhotoAlbumConfig.getPrivateKey());

		String address = categoryService.deploy(new BigInteger(String.valueOf(type.getValue())),
				DPhotoAlbumConfig.getAlbumContractAddress());

		return address;
	}
}
