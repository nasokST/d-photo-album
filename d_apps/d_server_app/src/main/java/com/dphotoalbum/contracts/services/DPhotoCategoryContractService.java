package com.dphotoalbum.contracts.services;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import com.dphotoalbum.contracts.DPhotoAlbumCategory_sol_DPhotoAlbumCategory;

public class DPhotoCategoryContractService {

	public DPhotoCategoryContractService(String provider, String privateKey) {
		web3 = Web3j.build(new HttpService(provider));
		credentials = Credentials.create(privateKey);
	}

	public String deploy(BigInteger categoryId, String albumAddress) {
		try {
			contract = DPhotoAlbumCategory_sol_DPhotoAlbumCategory
					.deploy(web3, credentials, ManagedTransaction.GAS_PRICE, BigInteger.valueOf(4600000), categoryId, albumAddress).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (null != contract)? contract.getContractAddress() : "";
	}

	public boolean load(String address) {
		contract = DPhotoAlbumCategory_sol_DPhotoAlbumCategory.load(address, web3, credentials,
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		return (null != contract);
	}

	private Web3j web3;
	private Credentials credentials;
	private DPhotoAlbumCategory_sol_DPhotoAlbumCategory contract;
}
