package com.dphotoalbum.objects;

public class DPhoto extends DPhotoIPFS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9066947188838989769L;

	public DPhoto() {
		super();
	}
	
	public DPhoto(DPhotoIPFS ipfsPhoto) {
		this.setIndex(ipfsPhoto.getIndex());
		this.setOwner(ipfsPhoto.getOwner());
		this.setCategory(ipfsPhoto.getCategory());
		this.setIpfsHash(ipfsPhoto.getIpfsHash());
	}

	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}

	private String pk;
	private long fileSize;
	private byte[] file;
}
