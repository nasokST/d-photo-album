package com.dphotoalbum.contracts.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import com.dphotoalbum.config.PhotoCategoryType;
import com.dphotoalbum.contracts.DPhotoAlbum_sol_DPhotoAlbum;
import com.dphotoalbum.objects.DPhotoIPFS;
import com.dphotoalbum.objects.DPhotoCommentIPFS;
import com.dphotoalbum.objects.IPFSHashInterface;
import com.dphotoalbum.objects.PhotoCategory;
import com.dphotoalbum.utils.DPhotoAlbumUtils;

public class DPhotoAlbumContractService {

	public DPhotoAlbumContractService(String provider, String privateKey) {
		web3 = Web3j.build(new HttpService(provider));
		credentials = Credentials.create(privateKey);
	}

	public boolean deploy() {
		try {
			contract = DPhotoAlbum_sol_DPhotoAlbum
					.deploy(web3, credentials, ManagedTransaction.GAS_PRICE, BigInteger.valueOf(4600000)).send();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (null != contract);
	}

	public boolean load(String address) {
		contract = DPhotoAlbum_sol_DPhotoAlbum.load(address, web3, credentials, ManagedTransaction.GAS_PRICE,
				Contract.GAS_LIMIT);

		return (null != contract);
	}

	public boolean addPhotoCategory(PhotoCategory photoCategory) {
		return addPhotoCategory(photoCategory.getAddress());
	}

	public boolean addPhotoCategory(String photoCategoryAddress) {

		try {
			TransactionReceipt txInfo = contract.addCategory(photoCategoryAddress).send();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean addPhoto(DPhotoIPFS dphoto) {
		try {
			TransactionReceipt txInfo = contract
					.addPhoto(new BigInteger(String.valueOf(dphoto.getCategory().getValue())),
							dphoto.getIpfsHash().digest, dphoto.getIpfsHash().hashFunction, dphoto.getIpfsHash().size)
					.send();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean addPhotoComment(DPhotoCommentIPFS ipfsPhotoComment) {
		try {
			BigInteger categoriId = new BigInteger(String.valueOf(ipfsPhotoComment.getCategory().getValue()));

			TransactionReceipt txInfo = contract.addPhotoComments(categoriId, ipfsPhotoComment.getPhotoIndex(),
					ipfsPhotoComment.getIpfsHash().digest, ipfsPhotoComment.getIpfsHash().hashFunction,
					ipfsPhotoComment.getIpfsHash().size).send();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public IPFSHashInterface getComments(long cateforyId, long photoIndex) {

		IPFSHashInterface ipfsMultihash = null;

		try {
			Tuple3<byte[], BigInteger, BigInteger> commentHash = contract.getPhotoComments(new BigInteger(String.valueOf(cateforyId)),
					new BigInteger(String.valueOf(photoIndex))).send();
			
			if(!DPhotoAlbumUtils.isByteArrayEmpty(commentHash.getValue1())) {
				ipfsMultihash = new IPFSHashInterface();
				ipfsMultihash.digest = Arrays.copyOf(commentHash.getValue1(), commentHash.getValue1().length);
				ipfsMultihash.hashFunction = commentHash.getValue2();
				ipfsMultihash.size = commentHash.getValue3();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ipfsMultihash;
	}

	public List<PhotoCategory> getAvailableCategories() {

		List<PhotoCategory> availableCategories = new ArrayList<>();
		try {
			List<BigInteger> categories = (List<BigInteger>) contract.getCategories().send();

			int categories_size = categories.size();
			for (int idx = 0; idx < categories_size; ++idx) {
				// BigInteger a = categories.get(idx);
				PhotoCategory category = new PhotoCategory(categories.get(idx));
				availableCategories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

		return availableCategories;
	}
	
	public List<DPhotoIPFS> getAllPhotosByCategory(long categoryId) {

		List<DPhotoIPFS> photos = null;

		try {
			Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> tmpPhotos =
					contract.getAllPhotosFromCategory(new BigInteger(String.valueOf(categoryId))).send();

			int photoListSize = tmpPhotos.getValue1().size();
			photos = new ArrayList<>(photoListSize);

			for(int idx = 0; idx < photoListSize; ++idx) {
				DPhotoIPFS photo = new DPhotoIPFS();
				photo.setIndex(new BigInteger(String.valueOf(idx)));
				photo.setCategory(PhotoCategoryType.forValue((int)categoryId));
				photo.setOwner(tmpPhotos.getValue1().get(idx));

				photo.setIpfsHash(new IPFSHashInterface());
				photo.getIpfsHash().digest = Arrays.copyOf(tmpPhotos.getValue2().get(idx), tmpPhotos.getValue2().get(idx).length);
				photo.getIpfsHash().hashFunction = tmpPhotos.getValue3().get(idx);
				photo.getIpfsHash().size = tmpPhotos.getValue4().get(idx);

				photos.add(photo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return photos;		
	}
	
	public List<DPhotoIPFS> getAllPhotosByCategoryAndPhotographer(String photographer, long categoryId) {

		List<DPhotoIPFS> photos = null;

		try {
			Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>> tmpPhotos =
					contract.getAllPhotosForPhotographerByCategory(photographer, new BigInteger(String.valueOf(categoryId))).send();
			
			int photoListSize = tmpPhotos.getValue1().size();
			photos = new ArrayList<>(photoListSize);
			
			for(int idx = 0; idx < photoListSize; ++idx) {
				DPhotoIPFS photo = new DPhotoIPFS();
				photo.setIndex(new BigInteger(String.valueOf(idx)));
				photo.setCategory(PhotoCategoryType.forValue((int)categoryId));
				photo.setOwner(photographer);
				
				photo.setIpfsHash(new IPFSHashInterface());
				photo.getIpfsHash().digest = Arrays.copyOf(tmpPhotos.getValue1().get(idx), tmpPhotos.getValue1().get(idx).length);
				photo.getIpfsHash().hashFunction = tmpPhotos.getValue2().get(idx);
				photo.getIpfsHash().size = tmpPhotos.getValue3().get(idx);
				
				photos.add(photo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return photos;		
	}	

	public String getMsgSenderAddress() {
		return credentials.getAddress();
	}

	private Web3j web3;
	private Credentials credentials;
	private DPhotoAlbum_sol_DPhotoAlbum contract;
}
