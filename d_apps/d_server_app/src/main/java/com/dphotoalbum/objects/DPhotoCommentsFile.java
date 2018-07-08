package com.dphotoalbum.objects;

import java.math.BigInteger;
import java.util.List;

import com.dphotoalbum.config.PhotoCategoryType;

public class DPhotoCommentsFile {

	public PhotoCategoryType getCategory() {
		return category;
	}
	public void setCategory(PhotoCategoryType category) {
		this.category = category;
	}
	public BigInteger getPhotoIndex() {
		return photoIndex;
	}
	public void setPhotoIndex(BigInteger photoIndex) {
		this.photoIndex = photoIndex;
	}
	public List<DPhotoComment> getComments() {
		return comments;
	}
	public void setComments(List<DPhotoComment> comments) {
		this.comments = comments;
	}

	private PhotoCategoryType category;
	private BigInteger photoIndex;
	private List<DPhotoComment> comments;
}
