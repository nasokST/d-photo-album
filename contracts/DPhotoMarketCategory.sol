pragma solidity ^0.4.24;

import "./ipfs/IPFSStorage.sol";

contract DPhotoMarketCategory {

    using IPFSStorage for IPFSStorage.Multihash;

    struct PhotoStorage {
        bytes32[] digests;
        uint8[] hash_functions;
        uint8[] sizes;
    }

    address private owner;
    address private market;
    uint8 private version;

    PhotoStorage photo_storage;
    mapping(uint => IPFSStorage.Multihash) photo_comments; // mapping(photo_index => comment_hash)
    //mapping(address => uint[]) photographer_photos; // 

    constructor(address _market) public {
        owner = msg.sender;
        market = _market;
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
        
        require(photo_storage.digests.length == photo_storage.hash_functions.length);
        require(photo_storage.sizes.length == photo_storage.hash_functions.length);

        return photo_storage.digests.length - 1;
    }

    function addComment(uint _to_photo_idx, bytes32 _digest, uint8 _hash_function, uint8 _size) 
    external onlyOwner{
        photo_comments[_to_photo_idx] = IPFSStorage.Multihash(_digest, _hash_function, _size);
    }

    function getVersion() external view onlyOwner returns(uint) {
        return version;
    }
    
    function getComment(uint _for_photo_idx) 
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

