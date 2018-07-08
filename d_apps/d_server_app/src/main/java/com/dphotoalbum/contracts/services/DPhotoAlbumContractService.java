package com.dphotoalbum.contracts.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import com.dphotoalbum.contracts.DPhotoAlbum_sol_DPhotoAlbum;
import com.dphotoalbum.objects.PhotoCategory;

public class DPhotoAlbumContractService {

	public DPhotoAlbumContractService(String provider, String privateKey) {
		web3 = Web3j.build(new HttpService(provider));
		credentials = Credentials.create(privateKey);
	}

	public boolean deploy(){
		try {
			contract = DPhotoAlbum_sol_DPhotoAlbum
					.deploy(web3, credentials, ManagedTransaction.GAS_PRICE, BigInteger.valueOf(4600000)).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public List<PhotoCategory> getAvailableCategories() {
		
		List<PhotoCategory> availableCategories = new ArrayList<>();
		try {
			List<BigInteger> categories = (List<BigInteger>) contract.getCategories().send();

			int categories_size = categories.size();
			for(int idx = 0; idx < categories_size; ++idx) {
				//BigInteger a = categories.get(idx);
				PhotoCategory category = new PhotoCategory(categories.get(idx));
				availableCategories.add(category);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

		return availableCategories;
	}

	private Web3j web3;
	private Credentials credentials;
	private DPhotoAlbum_sol_DPhotoAlbum contract;

}
