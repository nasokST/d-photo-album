const DPhotoAlbumCategory = artifacts.require("DPhotoAlbumCategory");

contract("DPhotoAlbumCategory", accounts => {
	const [firstAccount, secondAccount, thirdAccount, fourthAccount] = accounts;

	let deployed_photo_category;
	let photo_category;

	beforeEach(async () => {
		deployed_photo_category = await DPhotoAlbumCategory.deployed();
		photo_category = await DPhotoAlbumCategory.new(1, secondAccount);
	});

	it("add photo as not owner", async () => {
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount})

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
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount});

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

	it("add comment as not owner but as album", async () => {
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount});

		await local_category.addPhoto(0xaabbcc, 2, 3, {from: secondAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.addComment(fourthAccount, 0, 0xaabbcc, 2, 3, {from: thirdAccount});
		} catch(err) {
			test_correct = false
		}

		assert.ok(test_correct)
	});		

	it("add comment to wrong photo id", async () => {
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount});

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
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount});

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

	it("get photo by WRONG index", async () => {
		local_category = await DPhotoAlbumCategory.new(33, thirdAccount, {from: secondAccount});

		await local_category.addPhoto(0xaabbcc, 1, 2, {from: secondAccount})
		await local_category.addPhoto(0xaabbdd, 3, 4, {from: secondAccount})
		await local_category.addPhoto(0xaabbee, 5, 6, {from: secondAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.getPhotoByIndex(5, {from: secondAccount});
			test_correct = false
			assert.fail();
		} catch(err) {
		}

		assert.ok(test_correct)
	});	
});
