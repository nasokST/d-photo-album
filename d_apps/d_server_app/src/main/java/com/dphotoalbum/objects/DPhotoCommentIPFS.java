package com.dphotoalbum.objects;

import java.io.Serializable;
import java.math.BigInteger;

import com.dphotoalbum.config.PhotoCategoryType;

public class DPhotoCommentIPFS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1827653448243095772L;

	public BigInteger getPhotoIndex() {
		return photoIndex;
	}
	public void setPhotoIndex(BigInteger photoIndex) {
		this.photoIndex = photoIndex;
	}
	public PhotoCategoryType getCategory() {
		return category;
	}
	public void setCategory(PhotoCategoryType category) {
		this.category = category;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public IPFSHashInterface getIpfsHash() {
		return ipfsHash;
	}
	public void setIpfsHash(IPFSHashInterface ipfsHash) {
		this.ipfsHash = ipfsHash;
	}

	private BigInteger photoIndex;
	private PhotoCategoryType category;
	private String autor;
	private IPFSHashInterface ipfsHash;
}
