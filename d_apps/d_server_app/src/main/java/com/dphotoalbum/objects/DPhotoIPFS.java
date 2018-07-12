package com.dphotoalbum.objects;

import java.io.Serializable;
import java.math.BigInteger;

import com.dphotoalbum.config.PhotoCategoryType;

public class DPhotoIPFS implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6716297989154056915L;

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
	public IPFSHashUnpacked getIpfsHashUnpacked() {
		return ipfsHashUnpacked;
	}
	public void setIpfsHashUnpacked(IPFSHashUnpacked ipfsHashUnpacked) {
		this.ipfsHashUnpacked = ipfsHashUnpacked;
	}

	public String getIpfsHash() {
		return ipfsHash;
	}
	public void setIpfsHash(String ipfsHash) {
		this.ipfsHash = ipfsHash;
	}

	private BigInteger index;
	private String owner;
	private PhotoCategoryType category;
	private IPFSHashUnpacked ipfsHashUnpacked;
	private String ipfsHash;
}
