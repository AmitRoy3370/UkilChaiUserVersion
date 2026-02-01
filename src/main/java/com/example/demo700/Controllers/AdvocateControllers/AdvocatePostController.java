package com.example.demo700.Controllers.AdvocateControllers;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Services.AdvocateServices.AdvocatePostService;
import com.example.demo700.Services.AdvocateServices.PostContentService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/advocate/posts")
public class AdvocatePostController {

	@Autowired
	private AdvocatePostService advocatePostService;

	@Autowired
	private PostContentService postContentService;

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

	// --------------------------------------------------
	// Find post by id
	// --------------------------------------------------

	@GetMapping("/findByPostId")
	public ResponseEntity<?> findPostById(@RequestParam String postId) {

		try {

			AdvocatePost post = advocatePostService.searchPost(postId);

			if (post == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No post find at here...");

			}

			return ResponseEntity.status(200).body(post);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

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

	// --------------------------------------------------
	// Download Post content
	// --------------------------------------------------

	@GetMapping("download/postContent")
	public ResponseEntity<?> downloadPostContent(@RequestParam String attachmentId) {

		try {

			if (attachmentId == null) {

				throw new Exception("False request...");

			}

			String imageId = attachmentId;

			try {
				GridFSFile file = postContentService.getFile(imageId);

				if (file == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
				}

				InputStream stream = postContentService.getStream(file);

				return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
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

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}

	}

	// -------------- view the attachment ---------------------

	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
		try {
			GridFSFile file = postContentService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
			}

			InputStream stream = postContentService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load file");
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
