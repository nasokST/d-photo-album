package com.dphotoalbum.objects;

import java.io.Serializable;

public class DPhotoComment implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1085557864405099563L;

	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private String autor;
	private String dateTime;
	private String text;
}
