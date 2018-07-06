const DPhotoMarketCategory = artifacts.require("DPhotoMarketCategory");
//const { incraseTime } = require("./utils")

//const DAY = 3600 * 24;
//const FINNEY = 10**15;

contract("DPhotoMarketCategory", accounts => {
	const [firstAccount, secondAccount, thirdAccount, fourthAccount] = accounts;

	let deployed_photo_category;
	let photo_category;

	beforeEach(async () => {
		deployed_photo_category = await DPhotoMarketCategory.deployed();
		photo_category = await DPhotoMarketCategory.new(1, secondAccount);
	});

/*	it("add photo as owner", async () => {
		local_category = await DPhotoMarketCategory.new(33, thirdAccount, {from: secondAccount})

		let photo_id = await local_category.addPhoto.call(0xaabbcc, 2, 3, {from: secondAccount})

		assert.equal(photo_id, 0);

		let photo_id1 = await local_category.addPhoto.call(0xaabbff, 2, 3, {from: secondAccount})

		assert.equal(photo_id1, 1);
	});	*/

	it("add photo as not owner", async () => {
		local_category = await DPhotoMarketCategory.new(33, thirdAccount, {from: secondAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.addPhoto(0xaabbcc, 2, 3, {from: fourthAccount})
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});

	it("add comment as not owner", async () => {
		local_category = await DPhotoMarketCategory.new(33, thirdAccount, {from: secondAccount});

		await local_category.addPhoto(0xaabbcc, 2, 3, {from: secondAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.addComment(thirdAccount, 0, 0xaabbcc, 2, 3, {from: fourthAccount});
			test_correct = false
			assert.fail();
		} catch(err) {
		}

		assert.ok(test_correct)
	});	


	it("add comment to wrong photo id", async () => {
		local_category = await DPhotoMarketCategory.new(33, thirdAccount, {from: secondAccount});

		var test_correct = new Boolean(true)
		try {
			await local_category.addComment(thirdAccount, 0, 0xaabbcc, 2, 3, {from: secondAccount});
			test_correct = false
			assert.fail();
		} catch(err) {
		}

		assert.ok(test_correct)
	});	

	it("get all photos as not owner", async () => {
		local_category = await DPhotoMarketCategory.new(33, thirdAccount, {from: secondAccount});

		await local_category.addPhoto(0xaabbcc, 2, 3, {from: secondAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.getAllPhotos({from: fourthAccount});
			test_correct = false
			assert.fail();
		} catch(err) {
		}

		assert.ok(test_correct)
	});	



/*
	it("add comment as not owner", async () => {
		funding = await Funding.new(DAY, 100 * FINNEY, {from: secondAccount});
		await funding.donate({from: firstAccount, value: 100 * FINNEY});
		
		try {
			await funding.withdraw();
			assert.fail();
		} catch(err) {
		}
		assert.ok(1 < 2);
	});	*/















/*	it ("check owner on deploy", async() => {
		let result = await deployed_photo_category.getOwner.call({from: firstAccount})
		assert.equal(result.toString(), firstAccount)
	});*/

/*	it("allows to withdraw funds after time is up and goal is not reached", async () => {
		await funding.donate({from: secondAccount, value: 50 * FINNEY});
		const initBalance = web3.eth.getBalance(secondAccount);
		assert.equal((await funding.balances.call(secondAccount)), 50 * FINNEY);
		await incraseTime(DAY);
		await funding.refund({from: secondAccount});
		const finalBalance = web3.eth.getBalance(secondAccount);
		assert.ok(finalBalance.greaterThan(initBalance));
	});*/

/*	it("does not allow to withdraw funds after time is up and goal is reached", async () => {
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
	});*/
});
