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
});
