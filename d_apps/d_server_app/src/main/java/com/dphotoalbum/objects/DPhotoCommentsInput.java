package com.dphotoalbum.objects;

public class DPhotoCommentsInput {
	
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public DPhotoCommentsFile getComments() {
		return comments;
	}
	public void setComments(DPhotoCommentsFile comments) {
		this.comments = comments;
	}

	private String pk;
	DPhotoCommentsFile comments;
}
