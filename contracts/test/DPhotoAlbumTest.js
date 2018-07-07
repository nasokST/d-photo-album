const DPhotoAlbum = artifacts.require("DPhotoAlbum");
const DPhotoAlbumCategory = artifacts.require("DPhotoAlbumCategory");


contract("DPhotoAlbum", accounts => {
	const [firstAccount, secondAccount, thirdAccount, fourthAccount] = accounts;

	let deployed_photo_album;
	let photo_album;

	beforeEach(async () => {
		deployed_photo_album = await DPhotoAlbum.deployed();
		photo_album = await DPhotoAlbum.new();
	});

	it("add photo category as not owner", async () => {
		photo_category = await DPhotoAlbumCategory.new(33, deployed_photo_album.address, {from: firstAccount})

		var test_correct = new Boolean(true)
		try {
			await deployed_photo_album.addCategory(photo_category.address, {from: secondAccount})
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});

	it("add photo category from different album", async () => {
		photo_category = await DPhotoAlbumCategory.new(33, secondAccount, {from: firstAccount})

		var test_correct = new Boolean(true)
		try {
			await deployed_photo_album.addCategory(photo_category.address, {from: firstAccount})
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});	

	it("add photo to not existing category", async () => {

		var test_correct = new Boolean(true)
		try {
			await photo_album.addPhoto(0, 0xaabbcc, 2, 3);
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});

	it("add photo to category", async () => {

		photo_category = await DPhotoAlbumCategory.new(33, photo_album.address)
		await photo_album.addCategory(photo_category.address)

		await photo_album.addPhoto(33, 0xaabbcc, 2, 3);


		var test_correct = new Boolean(true)
		try {
			await photo_album.addPhoto(34, 0xaabbcc, 2, 3);
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});	

/*	it("get photos for photographer by category id", async () => {
		photo_category31 = await DPhotoAlbumCategory.new(31, photo_album, {from: firstAccount})
		photo_category56 = await DPhotoAlbumCategory.new(56, photo_album, {from: firstAccount})

		await photo_album.addPhoto(31, 0xaa, 2, 3, {from: secondAccount});
		await photo_album.addPhoto(31, 0xaabb, 2, 3, {from: secondAccount});
		await photo_album.addPhoto(31, 0xaabbcc, 2, 3, {from: thirdAccount});

		await photo_album.addPhoto(56, 0xbb, 2, 3, {from: thirdAccount});
		await photo_album.addPhoto(56, 0xbbcc, 2, 3, {from: fourthAccount});
		await photo_album.addPhoto(56, 0xbbccdd, 2, 3, {from: thirdAccount});


		await photo_album.getAllPhotosForPhotographerByCategory(thirdAccount, 56)
	});*/

/*	it("add photo category as owner but from different album", async () => {
		photo_category = await DPhotoAlbumCategory.new(33, deployed_photo_album, {from: firstAccount})

		var test_correct = new Boolean(true)
		try {
			await local_category.addPhoto(0xaabbcc, 2, 3, {from: deployed_photo_album})
			test_correct = false
			assert.fail();
		} catch(err) {
			//console.log("5")
		}

		assert.ok(test_correct)
	});	*/
});
