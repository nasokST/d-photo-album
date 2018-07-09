package com.dphotoalbum.objects;

import java.io.Serializable;

public class DPhotoCommentsInput implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3414368011669183996L;

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
