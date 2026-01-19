package com.example.demo700.Controllers.UserControllers;

import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Services.UserServices.UserService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	// ----------------- SEE ALL USERS --------------------
	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		try {
			List<User> list = userService.seeAllUsers();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	// ----------------- UPDATE USER --------------------
	@PutMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateUser(@RequestPart("name") String name, @RequestPart("password") String password,
			@RequestPart(value = "profileImageId", required = false) String profileImageId,
			@RequestPart(value = "file", required = false) MultipartFile file, @PathVariable String userId) {
		try {
			User user = new User(name, password, profileImageId);
			User updated = userService.updateUser(user, userId, file);
			if (updated == null) {
				return ResponseEntity.status(500).body("Data is not updated...");
			}
			ResponseEntity<?> responseEntity = ResponseEntity.status(200).body(updated);
			/*
			 * try { GridFSFile file1 = imageService.getFile(updated.getProfileImageId());
			 * if (file1 == null) { } else { ((Object)
			 * responseEntity).setContentType(file1.getMetadata().getString("type"));
			 * ((Object) responseEntity).setHeader("Content-Disposition",
			 * "inline; filename=\"" + file1.getFilename() + "\""); InputStream stream =
			 * imageService.getStream(file1); IOUtils.copy(stream,
			 * responseEntity.getOutputStream()); ((Object) responseEntity).flushBuffer(); }
			 * } catch (Exception e) { }
			 */
			return responseEntity;
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	// ----------------- DELETE USER --------------------
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId,
			@RequestParam("tryingToDelete") String tryingToDelete) {

		try {
			boolean deleted = userService.deleteUser(userId, tryingToDelete);

			if (!deleted) {
				return ResponseEntity.status(500).body("Failed to delete user...");
			}

			return ResponseEntity.ok("User deleted successfully.");

		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	// ----------------- FIND BY NAME --------------------
	@GetMapping("/find/name/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		try {
			User user = userService.findByName(name);
			return ResponseEntity.ok(user);

		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	// ----------------- FIND BY PROFILE IMAGE ID --------------------
	@GetMapping("/find/profile/{profileImageId}")
	public ResponseEntity<?> findByProfile(@PathVariable String profileImageId) {
		try {
			return ResponseEntity.ok(userService.findByProfileImageId(profileImageId));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	// ✅ DOWNLOAD PROFILE IMAGE
	@GetMapping("/download/{imageId}")
	public ResponseEntity<?> downloadImage(@PathVariable String imageId) {
		try {
			GridFSFile file = imageService.getFile(imageId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
			}

			InputStream stream = imageService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		}
	}

	// ✅ Find user by id

	@GetMapping("/search")
	public ResponseEntity<?> searchUserById(@RequestParam String userId) {

		try {

			User user = userService.searchUser(userId);

			if (user == null) {

				return ResponseEntity.status(404).body("No such user exist at here...");

			}

			return ResponseEntity.status(200).body(user);

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

}
