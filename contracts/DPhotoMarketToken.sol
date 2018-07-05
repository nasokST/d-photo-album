pragma solidity ^0.4.24;

import "./openzeppelin-solidity/contracts/ERC20/StandardToken.sol";
import "./openzeppelin-solidity/contracts/ERC20/DetailedERC20.sol";


contract DPhotoMarketToken is DetailedERC20, StandardToken {
    
    address private owner;

  constructor(string _name, string _symbol, uint8 _decimals) 
    DetailedERC20(_name, _symbol, _decimals) public {
        owner = msg.sender;
  }    
}

