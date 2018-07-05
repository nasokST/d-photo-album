const DPhotoMarketCategory = artifacts.require("./DPhotoMarketCategory.sol");

module.exports = function(deployer) {
	deployer.deploy(DPhotoMarketCategory, 1, 0x00);
}
