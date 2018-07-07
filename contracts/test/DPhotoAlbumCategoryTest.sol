pragma solidity ^0.4.24;

import "truffle/Assert.sol";
import "../contracts/DPhotoAlbumCategory.sol";
import "truffle/DeployedAddresses.sol";

contract DPhotoAlbumCategoryTest {

	uint photo_category_id = 1; 
	address album_on_deploy = address(0x123);
	address album_this = address(0x456);

    DPhotoAlbumCategory photo_category;
    DPhotoAlbumCategory deployed_category;

    function beforeEach() public {
        photo_category = new DPhotoAlbumCategory(photo_category_id, album_this);
        deployed_category = DPhotoAlbumCategory(DeployedAddresses.DPhotoAlbumCategory());
    }

    function testSettingAnOwnerOfDeployedContract() public {
        Assert.equal(deployed_category.owner(), msg.sender, "An owner is different than a deployer");
    }

    function testSettingAnAlbumOfDeployedContract() public {
        Assert.equal(deployed_category.album(), album_on_deploy, "An album is different than a deployed");
    }    

    function testSettingAnPhotoCategoryId() public {
        Assert.equal(photo_category.category_id(), photo_category_id, "An category id is different than a deployed");

        photo_category = new DPhotoAlbumCategory(33, address(this));
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

        address[] memory photo_owners;
 		bytes32[] memory digests;
		uint8[] memory hashs_fng;
		uint8[] memory sizes;

    	uint photo_id = photo_category.addPhoto(0x00aaff, 2, 3);
    	Assert.equal(photo_id, 0, "Error on adding photo hash");    	


		(photo_owners, digests, hashs_fng, sizes) = photo_category.getAllPhotos();

        Assert.equal(photo_owners[0], msg.sender, "get photos error1");
		Assert.equal(digests[0], 0x00aaff, "get photos error1");
		Assert.equal(uint(hashs_fng[0]), 2, "get photos error2");
		Assert.equal(uint(sizes[0]), 3,"get photos error3");
    }

    function testGetPhotoByIndex() public {

        address photo_owner;
        bytes32 digest;
        uint8 hash_fng;
        uint8 size;

        photo_category.addPhoto(0x00aaff, 4, 5);
        photo_category.addPhoto(0xaabbcc, 2, 3);


        (photo_owner, digest, hash_fng, size) = photo_category.getPhotoByIndex(1);

        Assert.equal(photo_owner, msg.sender, "get photos error1");
        Assert.equal(digest, 0xaabbcc, "get photos error1");
        Assert.equal(uint(hash_fng), 2, "get photos error2");
        Assert.equal(uint(size), 3,"get photos error3");
    }    
}