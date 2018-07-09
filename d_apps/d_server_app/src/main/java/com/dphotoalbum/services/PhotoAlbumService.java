package com.dphotoalbum.services;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dphotoalbum.config.DPhotoAlbumConfig;
import com.dphotoalbum.config.PhotoCategoryType;
import com.dphotoalbum.contracts.services.DPhotoCategoryContractService;
import com.dphotoalbum.contracts.services.DPhotoAlbumContractService;
import com.dphotoalbum.objects.DPhoto;
import com.dphotoalbum.objects.DPhotoCommentIPFS;
import com.dphotoalbum.objects.DPhotoCommentsFile;
import com.dphotoalbum.objects.DPhotoCommentsInput;
import com.dphotoalbum.objects.DPhotoInput;
import com.dphotoalbum.objects.IPFSHashInterface;
import com.dphotoalbum.objects.PhotoCategory;

@Service("photoAlbumService")
public class PhotoAlbumService {

	private static DPhotoAlbumContractService albumContractService;

	public List<PhotoCategory> getAllcategories() {
		return albumContractService.getAvailableCategories();
	}

	public boolean addPhotos(List<DPhotoInput> dphotos) {

		boolean error = true;

		for (DPhotoInput photo : dphotos) {
			error &= addPhoto(photo);
		}

		return error;
	}

	public boolean addPhoto(DPhotoInput dphoto) {

		DPhotoAlbumContractService tmpAlbumContract = initAlbum(dphoto.getPk());

		if(null != tmpAlbumContract) {
			
			IPFSHashInterface photoIpfsHash = null;
			// TODO !!!!!!! ADDING on IPFS and getting hash


			dphoto.setIpfsHash(photoIpfsHash);
			
			return tmpAlbumContract.addPhoto(dphoto);
		}

		return false;
	}

	public boolean addComments(DPhotoCommentsInput photoComments) {
		DPhotoAlbumContractService tmpAlbumContract = initAlbum(photoComments.getPk());

		if(null != tmpAlbumContract) {

			IPFSHashInterface commentsIpfsHash = null;
			// TODO !!!!!!! ADDING on IPFS and getting hash



			DPhotoCommentIPFS ipfsPhotoComment = null;
			ipfsPhotoComment.setCategory(photoComments.getComments().getCategory());
			ipfsPhotoComment.setPhotoIndex(photoComments.getComments().getPhotoIndex());
			ipfsPhotoComment.setIpfsHash(commentsIpfsHash);

			return tmpAlbumContract.addPhotoComment(ipfsPhotoComment);
		}

		return false;
	}

	public DPhotoCommentsFile getComments(long categoryId, long photoIndex) {

		IPFSHashInterface commentHash = albumContractService.getComments(categoryId, photoIndex);

		if (null != commentHash) {
			DPhotoCommentsFile comments = null;

			// TODO getting comments from IPFS

			return comments;
		}

		return null;
	}

	public List<DPhoto> getAllPhotosByCategory(long categoryId) {
		return albumContractService.getAllPhotosByCategory(categoryId);
	}

	public List<DPhoto> getAllPhotosByPhotographerByCategory(String photographer, long categoryId) {
		return albumContractService.getAllPhotosByCategoryAndPhotographer(photographer, categoryId);
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
		// // TODO Auto-generated catch block
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
