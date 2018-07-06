pragma solidity ^0.4.24;

import "./IPFSStorage.sol";

contract DPhotoMarketCategory {

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
    }

    uint public category_id;
    address public owner;
    address public market;
    uint public version;

    PhotoStorage photo_storage;
    mapping(uint => IPFSStorage.Multihash) photo_comments; // mapping(photo_index => comment_hash)
    //mapping(address => uint[]) photographer_photos; // 

    constructor(uint _category_id, address _market) public {
        owner = msg.sender;
        market = _market;
        category_id = _category_id;
        version = 1;
    }

    modifier onlyOwner() {
        require(owner == msg.sender || market == msg.sender);
        _;
    }

    function addPhoto(bytes32 _digest, uint8 _hash_function, uint8 _size) 
    external onlyOwner returns(uint) {
        photo_storage.digests.push(_digest);
        photo_storage.hash_functions.push(_hash_function);
        photo_storage.sizes.push(_size);
        
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
    external view onlyOwner returns(bytes32[], uint8[], uint8[]) { // returns(photo_hash[], photo_index[])
        return (photo_storage.digests, 
                photo_storage.hash_functions, 
                photo_storage.sizes);
    }
}

