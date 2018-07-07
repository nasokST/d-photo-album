const DPhotoAlbumCategory = artifacts.require("./DPhotoAlbumCategory.sol");

module.exports = function(deployer) {
	deployer.deploy(DPhotoAlbumCategory, 1, 0x123);
}
