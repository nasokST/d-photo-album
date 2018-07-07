const DPhotoAlbum = artifacts.require("./DPhotoAlbum.sol");

module.exports = function(deployer) {
	deployer.deploy(DPhotoAlbum);
}
