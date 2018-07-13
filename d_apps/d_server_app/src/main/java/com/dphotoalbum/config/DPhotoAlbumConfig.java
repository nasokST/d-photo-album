package com.dphotoalbum.config;

public class DPhotoAlbumConfig {

	public static String getIPFSProvider() {
		return IPFSProvider;
	}
	
	public static String getWeb3jProvider() {
		return web3jProvider;
	}

	public static String getPrivateKey() {
		return privateKey;
	}
	
	public static String getAlbumContractAddress() {
		return albumContractAddress;
	}
	
	public static String getCategoryContractAddressByType(PhotoCategoryType categoryType) {
		switch(categoryType) {
		case FAMILY:
			return categoryFamilyContractAddress;
		case MOUNTAIN:
			return categoryMountainContractAddress;
		case SEA:
			return categorySеаContractAddress;
		default:
			return "";
		}
	}

	public static void setCategoryContractAddressByType(PhotoCategoryType categoryType, String address) {
		switch(categoryType) {
		case FAMILY:
			categoryFamilyContractAddress = address;
		case MOUNTAIN:
			categoryMountainContractAddress = address;
		case SEA:
			categorySеаContractAddress = address;
		default:
			return;
		}
	}

	private static String IPFSProvider = "/ip4/127.0.0.1/tcp/5001";
	private static String web3jProvider = "https://ropsten.infura.io/FyXOE87ZoBIxl6VmaYgY";
	private static String privateKey = "0x5e71dd6438f51402d2d2fc7e3f398f1f9103e824645f6fd1b3244cec5db7b1e8";
	private static String albumContractAddress = "0xdf43a5e4dea33d09094602deb9c7ad4586a846fa";
	private static String categoryFamilyContractAddress = "";
	private static String categorySеаContractAddress = "";
	private static String categoryMountainContractAddress = "";

//	private static String IPFSProvider = "/ip4/127.0.0.1/tcp/5001";
//	private static String web3jProvider = "http://127.0.0.1:7545/FyXOE87ZoBIxl6VmaYgY";
//	private static String privateKey = "0x5a3af6a389d647ac92f03b502d323950f7c0bad97269c30f7db2d6640d6c1349";
//	private static String albumContractAddress = "0xf594279789e51e33f9ac984ab4f67c00813eae2e";
//	private static String categoryFamilyContractAddress = "";
//	private static String categorySеаContractAddress = "";
//	private static String categoryMountainContractAddress = "";
}