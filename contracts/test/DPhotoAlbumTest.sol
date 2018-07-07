pragma solidity ^0.4.24;

import "truffle/Assert.sol";
import "../contracts/DPhotoAlbum.sol";
import "../contracts/DPhotoAlbumCategory.sol";
import "truffle/DeployedAddresses.sol";

contract DPhotoAlbumTest {

    DPhotoAlbum photo_album;
    DPhotoAlbum deployed_album;

    function beforeEach() public {
        photo_album = new DPhotoAlbum();
        deployed_album = DPhotoAlbum(DeployedAddresses.DPhotoAlbum());
    }

    function testAddCategoryByOwner() public {

        DPhotoAlbumCategory new_category31 = new DPhotoAlbumCategory(31, photo_album);
        DPhotoAlbumCategory new_category65 = new DPhotoAlbumCategory(65, photo_album);


        photo_album.addCategory(new_category31);
        uint[] memory categories;
        (categories) = photo_album.getCategories();
        Assert.equal(categories[0], 31, "Error on adding photo hash");


        photo_album.addCategory(new_category65);
        (categories) = photo_album.getCategories();
        Assert.equal(categories[1], 65, "Error on adding photo hash");
    }

    function testAddCommentsToPhoto() public {
        DPhotoAlbumCategory new_category31 = new DPhotoAlbumCategory(31, photo_album);
        DPhotoAlbumCategory new_category65 = new DPhotoAlbumCategory(65, photo_album);


        photo_album.addCategory(new_category31);
        photo_album.addCategory(new_category65);

        photo_album.addPhoto(65, 0xaabbccdd, 3, 2);
        photo_album.addPhoto(65, 0xaabbccddee, 3, 2);

        photo_album.addPhotoComments(65, 1, 0xaabbccddeeff, 1, 2);

        bytes32 comment_hash;
        uint8 comment_hash_fng;
        uint8 comment_hash_size;

        (comment_hash, comment_hash_fng, comment_hash_size) = photo_album.getPhotoComments(65, 1) ;

        Assert.equal(comment_hash, 0xaabbccddeeff, "Error on adding comment hash");
        Assert.equal(uint(comment_hash_fng), 1, "Error on adding comment hash fng") ;
        Assert.equal(uint(comment_hash_size), 2, "Error on adding comment hash size");
    }

    function testGetAllPhotosFromCategory() public {
        DPhotoAlbumCategory new_category31 = new DPhotoAlbumCategory(31, photo_album);

        photo_album.addCategory(new_category31);

        photo_album.addPhoto(31, 0xaabbccdd, 3, 2);
        photo_album.addPhoto(31, 0xaabbccddee, 4, 1);

        address[] memory photo_owner;
        bytes32[] memory photo_hashs;
        uint8[] memory photo_hash_fngs;
        uint8[] memory photo_hash_sizes;

        (photo_owner, photo_hashs, photo_hash_fngs, photo_hash_sizes) = photo_album.getAllPhotosFromCategory(31);

        Assert.equal(photo_owner[0], msg.sender, "Error on adding photo owner (0)");
        Assert.equal(photo_hashs[0], 0xaabbccdd, "Error on adding photo hash (0)");
        Assert.equal(uint(photo_hash_fngs[0]), 3, "Error on adding photo hash fng (0)") ;
        Assert.equal(uint(photo_hash_sizes[0]), 2, "Error on adding photo hash size (0)");


        Assert.equal(photo_owner[1], msg.sender, "Error on adding photo owner (1)");
        Assert.equal(photo_hashs[1], 0xaabbccddee, "Error on adding photo hash (1)");
        Assert.equal(uint(photo_hash_fngs[1]), 4, "Error on adding photo hash fng (1)") ;
        Assert.equal(uint(photo_hash_sizes[1]), 1, "Error on adding photo hash size (1)");
    }
    

    function testGetAllPhotosForPhotographerByCategoryId() public {
        DPhotoAlbumCategory new_category31 = new DPhotoAlbumCategory(31, photo_album);
        DPhotoAlbumCategory new_category56 = new DPhotoAlbumCategory(56, photo_album);

        photo_album.addCategory(new_category31);
        photo_album.addCategory(new_category56);

        photo_album.addPhoto(31, 0xaa, 3, 2);
        photo_album.addPhoto(31, 0xaabb, 4, 1);
        photo_album.addPhoto(56, 0xaabbcc, 4, 1);
        photo_album.addPhoto(56, 0xaabbccdd, 4, 1);
        photo_album.addPhoto(56, 0xaabbccddee, 4, 1);

        bytes32[] memory photo_hashs;
        uint8[] memory photo_hash_fngs;
        uint8[] memory photo_hash_sizes;

        (photo_hashs, photo_hash_fngs, photo_hash_sizes) = photo_album.getAllPhotosForPhotographerByCategory(address(this), 56);

        Assert.equal(photo_hashs[0], 0xaabbcc, "Error getting phoyos for photographer by category id");
        Assert.equal(photo_hashs[1], 0xaabbccdd, "Error getting phoyos for photographer by category id");
        Assert.equal(photo_hashs[2], 0xaabbccddee, "Error getting phoyos for photographer by category id");


        (photo_hashs, photo_hash_fngs, photo_hash_sizes) = photo_album.getAllPhotosForPhotographerByCategory(address(this), 31);

        Assert.equal(photo_hashs[0], 0xaa, "Error getting phoyos for photographer by category id");
        Assert.equal(photo_hashs[1], 0xaabb, "Error getting phoyos for photographer by category id");
    }   

/*     function testGettingAllCategoriesByOwner() public {

        DPhotoAlbumCategory new_category31 = new DPhotoAlbumCategory(31, photo_album);
        DPhotoAlbumCategory new_category65 = new DPhotoAlbumCategory(65, photo_album);


        photo_album.addCategory(new_category31);
        uint[] memory categories;
        (categories) = photo_album.getCategories();
        Assert.equal(categories[0], 31, "Error on adding photo hash");


        photo_album.addCategory(new_category65);
        (categories) = photo_album.getCategories();
        Assert.equal(categories[0], 65, "Error on adding photo hash");
    }     */
}