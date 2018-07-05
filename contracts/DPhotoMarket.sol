pragma solidity ^0.4.24;

import "./DPhotoMarketToken.sol";
import "./DPhotoMarketCategory.sol";

contract DPhotoMarket {
    
    address owner;
    DPhotoMarketToken token;
    DPhotoMarketCategory[] categories;
    
    constructor() {
        owner = msg.sender;
    }
    
}

