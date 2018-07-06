pragma solidity ^0.4.24;

import "./DPhotoMarketCategory.sol";

contract DPhotoMarket {

    address private owner;
    mapping (uint => DPhotoMarketCategory) private categories;
    uint[] private category_list;

    constructor() public {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(owner == msg.sender);
        _;
    }

    function addCategory(DPhotoMarketCategory _category) external onlyOwner {
    	require(_category.market() == address(this));
    	categories[_category.category_id()] = _category;
    	category_list.push(_category.category_id());
    }
}
