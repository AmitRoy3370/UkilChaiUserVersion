package com.example.demo700.Controllers.AdvocateControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Services.AdvocateServices.AdvocatePostService;

@RestController
@RequestMapping("/api/advocate/posts")
public class AdvocatePostController {

	@Autowired
	private AdvocatePostService advocatePostService;

	// -------------------------------------------------
	// UPLOAD POST (with optional file)
	// -------------------------------------------------
	@PostMapping(value = "/upload/{userId}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadPost(@PathVariable String userId, @RequestPart("advocateId") String advocateId,
			@RequestPart("postContent") String postContent, @RequestPart("postType") String postType,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			AdvocateSpeciality advocateSpeciality = AdvocateSpeciality.valueOf(postType);

			AdvocatePost advocatePost = new AdvocatePost(advocateId, postContent, "", advocateSpeciality);

			AdvocatePost saved = advocatePostService.uploadPost(advocatePost, userId, file);

			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// SEE ALL POSTS
	// -------------------------------------------------
	@GetMapping("/all")
	public ResponseEntity<?> seeAll() {
		try {
			List<AdvocatePost> list = advocatePostService.seeAll();
			return ResponseEntity.ok(list);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// FIND BY ADVOCATE ID
	// -------------------------------------------------
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(advocatePostService.findByAdvocateId(advocateId));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// FIND BY POST TYPE
	// -------------------------------------------------
	@GetMapping("/type/{postType}")
	public ResponseEntity<?> findByPostType(@PathVariable AdvocateSpeciality postType) {

		try {
			return ResponseEntity.ok(advocatePostService.findByPostType(postType));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// FIND BY ADVOCATE ID + POST TYPE
	// -------------------------------------------------
	@GetMapping("/filter")
	public ResponseEntity<?> findByAdvocateIdAndPostType(@RequestParam String advocateId,
			@RequestParam AdvocateSpeciality postType) {

		try {
			return ResponseEntity.ok(advocatePostService.findByAdvocateIdAndPostType(advocateId, postType));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// SEARCH BY POST CONTENT
	// -------------------------------------------------
	@GetMapping("/search")
	public ResponseEntity<?> searchByContent(@RequestParam String keyword) {
		try {
			return ResponseEntity.ok(advocatePostService.findByPostContentContainingIgnoreCase(keyword));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// LATEST POSTS
	// -------------------------------------------------
	@GetMapping("/latest")
	public ResponseEntity<?> latestPosts() {
		try {
			return ResponseEntity.ok(advocatePostService.findLatestPosts());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// POSTS WITH ATTACHMENTS
	// -------------------------------------------------
	@GetMapping("/attachments")
	public ResponseEntity<?> postsWithAttachment() {
		try {
			return ResponseEntity.ok(advocatePostService.findByAttachmentIdIsNotNull());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// UPDATE POST
	// -------------------------------------------------
	@PutMapping(value = "/update/{postId}/{userId}", consumes = "multipart/form-data")
	public ResponseEntity<?> updatePost(@PathVariable String postId, @PathVariable String userId,
			@RequestPart("advocateId") String advocateId, @RequestPart("postContent") String postContent,
			@RequestPart("postType") String postType, @RequestPart("attachmentId") String attachmentId,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			AdvocateSpeciality advocateSpeciality = AdvocateSpeciality.valueOf(postType);

			AdvocatePost advocatePost = new AdvocatePost(advocateId, postContent, attachmentId, advocateSpeciality);

			AdvocatePost updated = advocatePostService.updateAdvocatePost(postId, userId, advocatePost, file);

			return ResponseEntity.ok(updated);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// -------------------------------------------------
	// DELETE POST
	// -------------------------------------------------
	@DeleteMapping("/delete/{postId}/{userId}")
	public ResponseEntity<?> deletePost(@PathVariable String postId, @PathVariable String userId) {

		try {
			boolean deleted = advocatePostService.deleteAdvocatePost(postId, userId);

			return ResponseEntity.ok("Deleted = " + deleted);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
