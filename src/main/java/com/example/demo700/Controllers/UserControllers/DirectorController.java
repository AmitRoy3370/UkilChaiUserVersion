package com.example.demo700.Controllers.UserControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.Director;
import com.example.demo700.Services.UserServices.DirectorService;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

	@Autowired
	private DirectorService directorService;

	// ==================== ADD DIRECTOR (POST) ====================
	// Accepts JSON data for the Director and a MultipartFile for the NID
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> addDirector(@RequestPart("director") Director director,
			@RequestParam("userId") String userId, @RequestPart(value = "nid", required = false) MultipartFile nid) {

		try {
			Director createdDirector = directorService.addDirector(director, userId, nid);
			return new ResponseEntity<>(createdDirector, HttpStatus.CREATED);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error adding director: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== UPDATE DIRECTOR (PUT) ====================
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateDirector(@PathVariable String id, @RequestPart("director") Director director,
			@RequestParam("userId") String userId, @RequestPart(value = "nid", required = false) MultipartFile nid) {

		try {
			Director updatedDirector = directorService.updateDirector(director, userId, id, nid);
			return new ResponseEntity<>(updatedDirector, HttpStatus.OK);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating director: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET ALL DIRECTORS ====================
	@GetMapping
	public ResponseEntity<?> getAllDirectors() {
		try {
			List<Director> directors = directorService.findAll();
			return new ResponseEntity<>(directors, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching directors", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET DIRECTOR BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> getDirectorById(@PathVariable String id) {
		try {
			Director director = directorService.findById(id);
			return new ResponseEntity<>(director, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching director", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET DIRECTOR BY USER ID ====================
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getDirectorByUserId(@PathVariable String userId) {
		try {
			Director director = directorService.findByUserId(userId);
			return new ResponseEntity<>(director, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching director by user ID", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET DIRECTORS BY NID ====================
	@GetMapping("/nid/{nid}")
	public ResponseEntity<?> getDirectorsByNid(@PathVariable String nid) {
		try {
			List<Director> directors = directorService.findByNid(nid);
			return new ResponseEntity<>(directors, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching directors by NID", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET DIRECTORS BY POSITION ====================
	@GetMapping("/position/{position}")
	public ResponseEntity<?> getDirectorsByPosition(@PathVariable String position) {
		try {
			List<Director> directors = directorService.findByPosition(position);
			return new ResponseEntity<>(directors, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching directors by position", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== DELETE DIRECTOR ====================
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDirector(@PathVariable String id, @RequestParam("userId") String userId) {

		try {
			boolean deleted = directorService.removeDirector(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Director deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Director could not be deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting director: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}