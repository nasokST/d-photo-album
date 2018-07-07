pragma solidity ^0.4.24;

//https://github.com/saurfang/ipfs-multihash-on-solidity/blob/master/contracts/IPFSStorage.sol

library IPFSStorage {
    struct Multihash {
        bytes32 digest;
        uint8 hashFunction;
        uint8 size;
    }
}
