package com.dphotoalbum.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dphotoalbum.config.PhotoCategoryType;
import com.dphotoalbum.objects.DPhotoCommentsFile;
import com.dphotoalbum.objects.DPhotoCommentsInput;
import com.dphotoalbum.objects.DPhoto;
import com.dphotoalbum.services.PhotoAlbumService;

@RestController
public class DPhotoAlbumRestController {

	/**
	 * 
	 */
	@Autowired
	public PhotoAlbumService photoAlbumService;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/categories/all")
	public ResponseEntity<?> getNodeId() {
		return new ResponseEntity<Object>(photoAlbumService.getAllcategories(), HttpStatus.OK);
	}
	
	// --- PHOTOS ---
	/**
	 * 
	 * @param inputFiles
	 * @param categories
	 * @param keys
	 * @return
	 */
	@PostMapping(value = "/dphoto", headers = ("content-type=multipart/*"), produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadMultipleFile(@RequestParam("file") List<MultipartFile> inputFiles,
			@RequestParam("category") List<PhotoCategoryType> categories, 
			@RequestParam("pk") List<String> keys) {

		List<DPhoto> dphotos = new ArrayList<DPhoto>();

		try {
			boolean listSizeEqual = (inputFiles.size() == categories.size() && inputFiles.size() == keys.size());

			if (!listSizeEqual) {
				new ResponseEntity<Object>("Missing parameters", HttpStatus.BAD_REQUEST);
			}

			int inputFilesListSize = inputFiles.size();
			for (int idx = 0; idx < inputFilesListSize; ++idx) {

				DPhoto dphoto = new DPhoto();

				dphoto.setCategory(categories.get(idx));
				dphoto.setPk(keys.get(idx));
				dphoto.setFileSize(inputFiles.get(idx).getSize());
				dphoto.setFile(inputFiles.get(idx).getBytes());

				dphotos.add(dphoto);
			}
			
			if (!photoAlbumService.addPhotos(dphotos)) {
				new ResponseEntity<Object>("ERROR uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<Object>("ERROR uploading file", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("OK", HttpStatus.OK);
	}
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/dphoto/{categoryId}")
	public ResponseEntity<?> getAllPhotosByCategory(@PathVariable long categoryId) {
		
		List<DPhoto> photos = photoAlbumService.getAllPhotosByCategory(categoryId);

		if (null == photos) {
			return new ResponseEntity<Object>("ERROR downloading file", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(photos, HttpStatus.OK);
	}
	/**
	 * 
	 * @param categoryId
	 * @param photographer
	 * @return
	 */
	@GetMapping("/dphoto/{categoryId}/{photographer}")
	public ResponseEntity<?> getAllPhotosForPhotographerByCategory(@PathVariable long categoryId, @PathVariable String photographer) {
		
		List<DPhoto> photos = photoAlbumService.getAllPhotosByPhotographerByCategory(photographer, categoryId);

		if (null == photos) {
			return new ResponseEntity<Object>("ERROR downloading file", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(photos, HttpStatus.OK);
	}
	/**
	 * 
	 * @param pk
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/dphoto/{categoryId}/photographer")
	public ResponseEntity<?> getAllPhotosForPhotographerPKByCategory(@RequestBody String pk, @PathVariable long categoryId) {
		
		List<DPhoto> photos = photoAlbumService.getAllPhotosByPhotographerPKByCategory(pk, categoryId);

		if (null == photos) {
			return new ResponseEntity<Object>("ERROR uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(photos, HttpStatus.OK);
	}	


	// --- COMENTS ---
	/**
	 * 
	 * @param photoComments
	 * @return
	 */
	@PostMapping("/dphoto/comment")
	public ResponseEntity<?> setPhotoComment(@RequestBody DPhotoCommentsInput photoComments) {
		if(!photoAlbumService.addComments(photoComments)) {
			new ResponseEntity<Object>("ERROR uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>("OK", HttpStatus.OK);
	}
	/**
	 * 
	 * @param categoryId
	 * @param photoIndex
	 * @return
	 */
	@GetMapping("/dphoto/comment/{categoryId}/{photoIndex}")
	public ResponseEntity<?> getPhotoComments(@PathVariable long categoryId, @PathVariable long photoIndex) {
		
		DPhotoCommentsFile comments = photoAlbumService.getComments(categoryId, photoIndex);

		if (null == comments) {
			return new ResponseEntity<Object>("ERROR uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(comments, HttpStatus.OK);
	}
}