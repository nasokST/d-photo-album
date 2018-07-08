package com.dphotoalbum.objects;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Uint;

import com.dphotoalbum.config.DPhotoAlbumConfig;
import com.dphotoalbum.config.PhotoCategoryType;

public class PhotoCategory {
	
	public PhotoCategory(PhotoCategoryType type) {
		initForType(type);
	}
	
	public PhotoCategory(BigInteger type) {
		initForType(type);
	}

	public boolean initForType(BigInteger type) {
		PhotoCategoryType ptype = PhotoCategoryType.forValue(type.intValue());
		return initForType(ptype);
	}
	
	public boolean initForType(PhotoCategoryType type) {

		this.type = type;

		switch(this.type) {
		case FAMILY:
			this.name = "Family";
			this.icon_url = "family.png";
			break;
		case SEA:
			this.name = "Sea";
			this.icon_url = "sea.png";
			break;
		case MONTAIN:
			this.name = "Montain";
			this.icon_url = "montain.png";
			break;
		default:
			return false;
		}

		this.address = DPhotoAlbumConfig.getCategoryContractAddressByType(type);		
		return true;
	}
	
	public PhotoCategoryType getType() {
		return type;
	}
	public void setType(PhotoCategoryType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	

	private PhotoCategoryType type;
	private String name;
	private String icon_url;
	private String address;
}
