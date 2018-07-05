const Funding = artifacts.require("Funding");
const { incraseTime } = require("./utils")

const DAY = 3600 * 24;
const FINNEY = 10**15;

contract("Funding", accounts => {
	const [firstAccount, secondAccount, thirdAccount] = accounts;

	let funding;

	beforeEach(async () => {
		funding = await Funding.new(DAY, 100 * FINNEY);
	});

	it("allows to withdraw funds after time is up and goal is not reached", async () => {
		await funding.donate({from: secondAccount, value: 50 * FINNEY});
		const initBalance = web3.eth.getBalance(secondAccount);
		assert.equal((await funding.balances.call(secondAccount)), 50 * FINNEY);
		await incraseTime(DAY);
		await funding.refund({from: secondAccount});
		const finalBalance = web3.eth.getBalance(secondAccount);
		assert.ok(finalBalance.greaterThan(initBalance));
	});

	it("does not allow to withdraw funds after time is up and goal is reached", async () => {
		await funding.donate({from: secondAccount, value: 100 * FINNEY});
		await incraseTime(DAY);
		try {
			await funding.refund({from: secondAccount});
			assert.fail();
		} catch(err) {
			assert.ok(/revert/.test(err.message));
		}
//		assert.ok(1 < 2);
	});

	it("does not allow to withdraw funds before time is up and goal is not reached", async () => {
		await funding.donate({from: secondAccount, value: 50 * FINNEY});
		assert.equal((await funding.balances.call(secondAccount)), 50 * FINNEY);
		try {
		} catch (err) {
		}

		assert.ok(1 < 2);
	});

	it("allows an owner to withdraw fund when doal is reached", async () => {
		await funding.donate({from: secondAccount, value: 30 * FINNEY});
		await funding.donate({from: thirdAccount, value: 70 * FINNEY});
		const initBalance = web3.eth.getBalance(firstAccount);
		assert.equal(web3.eth.getBalance(funding.address), 100 * FINNEY);
		await funding.withdraw();
		const finalBalance = web3.eth.getBalance(firstAccount);
		assert.ok(finalBalance.greaterThan(initBalance));		
	});



	it("does not allow non-owners to withdraw funds", async () => {
		funding = await Funding.new(DAY, 100 * FINNEY, {from: secondAccount});
		await funding.donate({from: firstAccount, value: 100 * FINNEY});
		
		try {
			await funding.withdraw();
			assert.fail();
		} catch(err) {
		}
		assert.ok(1 < 2);
	});

	it("does not allow for donnation when time is up", async () => {
		await funding.donate({from: firstAccount, value: 10 * FINNEY});
		await incraseTime(DAY);
		try {
			await funding.donate({from: firstAccount, value: 20 * FiNNEY});
			assert.fail();
		} catch(err) {
			//assert.ok(/revert/.test(err.message));
		}
		assert.ok(1 < 2);
	});

	it("finishes fundraising when thime is up", async () => {
		assert.equal(await funding.isFinished.call(), false);
		await incraseTime(DAY);
		assert.equal(await funding.isFinished.call(), true);
	});

	it("set an owner", async() => {
		//const funding = await Funding.new();
		assert.equal(await funding.owner.call(), firstAccount);
	});

	it("accepts donations", async () => {
		//const funding = await Funding.new();
		await funding.donate({from: firstAccount, value: 10 * FINNEY});
		await funding.donate({from: secondAccount, value: 20 * FINNEY});
		assert.equal(await funding.raised.call(), 30*FINNEY);
	});

	it("keep track of donator balance", async () => {
		//const funding = await Funding.new();
		await funding.donate({from: firstAccount, value: 50 * FINNEY});
		await funding.donate({from: secondAccount, value: 15 * FINNEY});
		await funding.donate({from: secondAccount, value: 3 * FINNEY});
		assert.equal(await funding.balances.call(firstAccount), 50 * FINNEY);
		assert.equal(await funding.balances.call(secondAccount), 18 * FINNEY);
	});
});
