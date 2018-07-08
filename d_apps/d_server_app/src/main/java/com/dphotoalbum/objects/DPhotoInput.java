package com.dphotoalbum.objects;

public class DPhotoInput extends DPhoto {

	public DPhotoInput() {
		super();
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
