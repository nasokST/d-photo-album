pragma solidity ^0.4.24;

import "truffle/Assert.sol";
import "../contracts/DPhotoMarketCategory.sol";
import "truffle/DeployedAddresses.sol";

contract DPhotoMarketCategoryTest {

	uint photo_category_id = 1; 
	address market_on_deploy = address(0x123);
	address market_this = address(0x456);

    DPhotoMarketCategory photo_category;
    DPhotoMarketCategory deployed_category;

    function beforeEach() public {
        photo_category = new DPhotoMarketCategory(photo_category_id, market_this);
        deployed_category = DPhotoMarketCategory(DeployedAddresses.DPhotoMarketCategory());
    }

    function testSettingAnOwnerOfDeployedContract() public {
        Assert.equal(deployed_category.owner(), msg.sender, "An owner is different than a deployer");
    }

    function testSettingAnMarketOfDeployedContract() public {
        Assert.equal(deployed_category.market(), market_on_deploy, "An market is different than a deployed");
    }    

    function testSettingAnPhotoCategoryId() public {
        Assert.equal(photo_category.category_id(), photo_category_id, "An category id is different than a deployed");

        photo_category = new DPhotoMarketCategory(33, address(this));
        Assert.equal(photo_category.category_id(), 33, "An category id is different than a deployed");
    }

    function testCategoryVersion() public {
        Assert.equal(photo_category.version(), 1, "Wrong category version");
    }

    function testAddPhotoByOwner() public {

    	uint photo_id = photo_category.addPhoto(0x00aaff, 1, 1);
    	Assert.equal(photo_id, 0, "Error on adding photo hash");

    	photo_id = photo_category.addPhoto(0x00aabbff, 2, 2);
    	Assert.equal(photo_id, 1, "Error on adding photo hash");
    }

    function testAddComment() public {

    	uint photo_id = photo_category.addPhoto(0x00aaff, 1, 1);
    	Assert.equal(photo_id, 0, "Error on adding photo hash");    	

		photo_category.addComment(address(this), 0, 0xaabbcc, 3, 3);

		bytes32 digest;
		uint8 hash_fng;
		uint8 size;

		(digest, hash_fng, size ) = photo_category.getComments(0);

		Assert.equal(digest, 0xaabbcc, "Adding comment to invalid photo index");
		Assert.equal(uint(hash_fng), 3, "Adding comment to invalid photo index");
		Assert.equal(uint(size), 3, "Adding comment to invalid photo index");


		(digest, hash_fng, size ) = photo_category.getComments(1);

		Assert.equal(digest, 0x00, "Adding comment to invalid photo index");
		Assert.equal(uint(hash_fng), 0, "Adding comment to invalid photo index");
		Assert.equal(uint(size), 0, "Adding comment to invalid photo index");
    }

    function testGetPhotos() public {

 		bytes32[] memory digests;
		uint8[] memory hashs_fng;
		uint8[] memory sizes;

/* 		(digests, hashs_fng, sizes) = photo_category.getAllPhotos();

		Assert.equal(digests[0], 0, "get photos error1");
		Assert.equal(uint(hashs_fng[0]), 0, "get photos error2");
		Assert.equal(uint(sizes[0]), 0, "get photos error3"); */


    	uint photo_id = photo_category.addPhoto(0x00aaff, 2, 3);
    	Assert.equal(photo_id, 0, "Error on adding photo hash");    	


		(digests, hashs_fng, sizes) = photo_category.getAllPhotos();

		Assert.equal(digests[0], 0x00aaff, "get photos error1");
		Assert.equal(uint(hashs_fng[0]), 2, "get photos error2");
		Assert.equal(uint(sizes[0]), 3,"get photos error3");
    }    
}