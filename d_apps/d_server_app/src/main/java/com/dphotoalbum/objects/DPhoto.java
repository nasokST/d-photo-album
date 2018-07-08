package com.dphotoalbum.objects;

import java.math.BigInteger;

import com.dphotoalbum.config.PhotoCategoryType;

public class DPhoto {
	public BigInteger getIndex() {
		return index;
	}
	public void setIndex(BigInteger index) {
		this.index = index;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public PhotoCategoryType getCategory() {
		return category;
	}
	public void setCategory(PhotoCategoryType category) {
		this.category = category;
	}
	public IPFSMultihash getIpfsHash() {
		return ipfsHash;
	}
	public void setIpfsHash(IPFSMultihash ipfsHash) {
		this.ipfsHash = ipfsHash;
	}

	private BigInteger index;
	private String owner;
	private PhotoCategoryType category;
	private IPFSMultihash ipfsHash;
}
