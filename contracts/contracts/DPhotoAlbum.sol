pragma solidity ^0.4.24;

import "./DPhotoAlbumCategory.sol";

contract DPhotoAlbum {

    address private owner;
    mapping (uint => DPhotoAlbumCategory) private categories;
    uint[] private available_categories;
    mapping(address => mapping(uint => uint[])) photographer_photos; // photographer => (category_id => photos)

    constructor() public {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(owner == msg.sender);
        _;
    }

    modifier onlyCurrentAlbum(DPhotoAlbumCategory _category) {
        require(_category.album() == address(this));
        _;
    }

    modifier isCategoryExists(uint _category_id) {
        require(categories[_category_id] != address(0x00));
        _;
    }

    function addCategory(DPhotoAlbumCategory _category)
     external onlyOwner onlyCurrentAlbum(_category) {

    	categories[_category.category_id()] = _category;
    	available_categories.push(_category.category_id());
    }

    function addPhoto(uint _category_id, bytes32 _digest, uint8 _hash_function, uint8 _size)
     external isCategoryExists(_category_id) {

        uint new_photo_id = categories[_category_id].addPhoto(_digest, _hash_function, _size);
        photographer_photos[msg.sender][_category_id].push(new_photo_id);
    }

    function addPhotoComments(uint _category_id, uint _for_photo_idx, bytes32 _digest, uint8 _hash_function, uint8 _size)
     external isCategoryExists(_category_id) {

        categories[_category_id].addComment(msg.sender, _for_photo_idx, _digest, _hash_function, _size);
    }

    function getCategories() external view returns(uint[]) {
        return available_categories;
    }

    function getPhotoComments(uint _category_id, uint _for_photo_id)
     external view isCategoryExists(_category_id) returns(bytes32, uint8, uint8) {

        return categories[_category_id].getComments(_for_photo_id);
    }

    function getAllPhotosFromCategory(uint _category_id) 
     external view isCategoryExists(_category_id) returns(address[], bytes32[], uint8[], uint8[]) {

        return categories[_category_id].getAllPhotos();
    }

    function getAllPhotosForPhotographerByCategory(address _photographer, uint _category_id)
     external view isCategoryExists(_category_id) returns(bytes32[], uint8[], uint8[]) {

        uint[] memory photo_indices = photographer_photos[_photographer][_category_id];
        //uint photos_size = photographer_photos[_photographer][_category_id].length;

        bytes32[] memory digests = new bytes32[](photographer_photos[_photographer][_category_id].length);
        uint8[] memory hash_functions = new uint8[](photographer_photos[_photographer][_category_id].length);
        uint8[] memory sizes = new uint8[](photographer_photos[_photographer][_category_id].length);

        bytes32 tmp_digests;
        uint8 tmp_hash_function;
        uint8 tmp_size;

        for(uint idx = 0; idx < photographer_photos[_photographer][_category_id].length; ++idx) {
            (, tmp_digests, tmp_hash_function, tmp_size) = categories[_category_id].getPhotoByIndex(photo_indices[idx]);

            digests[idx] = tmp_digests;
            hash_functions[idx] = tmp_hash_function;
            sizes[idx] = tmp_size;
        }

        return (digests, hash_functions, sizes);
    }    



/*     function getAllPhotos() 
     external view isCategoryExists(_category_id) returns(uint[], uint[], bytes32[], uint8[], uint8[]) { // category_id, photo_id, ....

        uint[] memory category_indices;
        uint[] memory photo_indices;
        bytes32[] memory digests;
        uint8[] memory hash_functions;
        uint8[] memory sizes;        

        for(uint idx = 0; idx < available_categories.lenght; idx++) {

        }
    }

    function getAllPhotosForPhotographer() external view{

    } */
}
