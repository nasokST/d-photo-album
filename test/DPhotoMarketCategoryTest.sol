pragma solidity ^0.4.24;

import "truffle/Assert.sol";
import "../contracts/Funding.sol";
import "truffle/DeployedAddresses.sol";

contract FundingTest {

    uint public initialBalance = 10 ether;
    
    Funding funding;
    
    function () public payable {}
    
    function beforeEach() public {
        funding = new Funding(1 days, 100 finney);
    }

    function testWithdrawalByAnOwner() public {
	uint initBalance = address(this).balance;
	funding.donate.value(50 finney)();
	bool result = address(funding).call(bytes4(keccak256("withdraw()")));
	Assert.equal(result, false, "Allows for withrawla before reaching the goal");
	funding.donate.value(50 finney)();
	Assert.equal(address(this).balance, initBalance - 100 finney, "Balance before withrawal dosent correspond the sum of donations");
	result = address(funding).call(bytes4(keccak256("withdraw()")));
	Assert.equal(result, true, "Dosen't allow for withdrawal after reaching the goal");
	Assert.equal(address(this).balance, initBalance, "Balance after withrawal dosen't correspond the sum of donations");	
    }


    function testDonnatingAfterTimeIsUp() public {
        Funding newFunding = new Funding(0, 100 finney);
        bool result = address(newFunding).call.value(10 finney)(bytes4(keccak256("donate()")));
        Assert.equal(result, false, "Allows for donnations when thime is up");
    }

    function testSettingAnOwnerDuringCreation() public {
        //Funding funding = new Funding();
        Assert.equal(funding.owner(), this, "An owner is different than a deployer");
    }
    
    function testSettingAnOwnerOfDeployedContract() public {
        funding = Funding(DeployedAddresses.Funding());
        Assert.equal(funding.owner(), msg.sender, "An owner is different than a deployer");
    }

    function testAcceptingDonation() public {
        //Funding funding = new Funding();
        Assert.equal(funding.raised(), 0, "Initial raides amount is different than 0");
        funding.donate.value(10 finney)();
        funding.donate.value(20 finney)();
        Assert.equal(funding.raised(), 30 finney, "Initial raides amount is different than sum of donations");
    }

    function testTrackingDonatorsBalance() public {
        //Funding funding = new Funding();
        funding.donate.value(5 finney)();
        funding.donate.value(15 finney)();
        Assert.equal(funding.balances(this), 20 finney, "Donator balance is different than sum of donations");
    }    
}
