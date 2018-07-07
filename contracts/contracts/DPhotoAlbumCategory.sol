pragma solidity ^0.4.24;

import "./IPFSStorage.sol";

contract DPhotoAlbumCategory {

    event NewPhoto(
        uint indexed _photo_index,
        uint _category_id
    );

    event NewComment(
        uint indexed _photo_index,
        address _from,
        uint _category_id
    );    

    using IPFSStorage for IPFSStorage.Multihash;

    struct PhotoStorage {
        bytes32[] digests;
        uint8[] hash_functions;
        uint8[] sizes;
        address[] photo_owner;
    }

    uint public category_id;
    address public owner;
    address public album;
    uint public version;

    PhotoStorage photo_storage;
    mapping(uint => IPFSStorage.Multihash) photo_comments; // mapping(photo_index => comment_hash)

    constructor(uint _category_id, address _album) public {
        owner = msg.sender;
        album = _album;
        category_id = _category_id;
        version = 1;
    }

    modifier onlyOwner() {
        require(owner == msg.sender || album == msg.sender);
        _;
    }

    function addPhoto(bytes32 _digest, uint8 _hash_function, uint8 _size) 
    external onlyOwner returns(uint) {
        photo_storage.digests.push(_digest);
        photo_storage.hash_functions.push(_hash_function);
        photo_storage.sizes.push(_size);
        photo_storage.photo_owner.push(tx.origin);
        
        require(photo_storage.digests.length == photo_storage.hash_functions.length); // this can affect to coverage test "Branch" part
        require(photo_storage.sizes.length == photo_storage.hash_functions.length); // this can affect to coverage test "Branch" part
        
        emit NewPhoto(photo_storage.digests.length - 1, category_id); 

        return photo_storage.digests.length - 1;
    }

    function addComment(address _from, uint _to_photo_idx, bytes32 _digest, uint8 _hash_function, uint8 _size) 
    external onlyOwner{
        require(_to_photo_idx < photo_storage.digests.length);
        photo_comments[_to_photo_idx] = IPFSStorage.Multihash(_digest, _hash_function, _size);
        
        emit NewComment(_to_photo_idx, _from, category_id); 
    }

    function getComments(uint _for_photo_idx) 
    external view onlyOwner returns(bytes32, uint8, uint8) {
        return (photo_comments[_for_photo_idx].digest, 
                photo_comments[_for_photo_idx].hashFunction, 
                photo_comments[_for_photo_idx].size);
    }

    function getAllPhotos() 
    external view onlyOwner returns(address[], bytes32[], uint8[], uint8[]) { // returns(photo_owner, photo_hash...)
        return (photo_storage.photo_owner, 
                photo_storage.digests, 
                photo_storage.hash_functions, 
                photo_storage.sizes);
    }

    function getPhotoByIndex(uint _photo_index)
    external view onlyOwner returns(address, bytes32, uint8, uint8) { // returns(photo_owner, photo_hash...)
        
        require(_photo_index < photo_storage.photo_owner.length);

        return (photo_storage.photo_owner[_photo_index], 
                photo_storage.digests[_photo_index], 
                photo_storage.hash_functions[_photo_index], 
                photo_storage.sizes[_photo_index]);
    }    
}

