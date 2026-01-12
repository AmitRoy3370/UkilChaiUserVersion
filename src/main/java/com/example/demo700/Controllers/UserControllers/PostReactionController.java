package com.example.demo700.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.UserModels.PostReaction;
import com.example.demo700.Services.UserServices.PostReactionService;

@RestController
@RequestMapping("/api/post-reactions")
public class PostReactionController {

	@Autowired
	private PostReactionService postReactionService;

	// ================= ADD =================
	@PostMapping("/add")
	public ResponseEntity<?> addPostReaction(@RequestBody PostReaction postReaction) {
		try {
			return ResponseEntity.ok(postReactionService.addPostReaction(postReaction, postReaction.getUserId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ================= UPDATE =================
	@PutMapping("/update/{reactionId}")
	public ResponseEntity<?> updatePostReaction(@PathVariable String reactionId,
			@RequestBody PostReaction postReaction) {
		try {
			return ResponseEntity
					.ok(postReactionService.updatePostReaction(postReaction, postReaction.getUserId(), reactionId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ================= DELETE =================
	@DeleteMapping("/{reactionId}")
	public ResponseEntity<?> deletePostReaction(@PathVariable String reactionId, @RequestParam String userId) {
		try {
			return ResponseEntity.ok(postReactionService.removePostReaction(reactionId, userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ================= GET BY ID =================
	@GetMapping("/{reactionId}")
	public ResponseEntity<?> getById(@PathVariable String reactionId) {
		try {
			return ResponseEntity.ok(postReactionService.findById(reactionId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= GET BY USER =================
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getByUser(@PathVariable String userId) {
		try {
			return ResponseEntity.ok(postReactionService.findByUserId(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= GET BY POST =================
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getByPost(@PathVariable String postId) {
		try {
			return ResponseEntity.ok(postReactionService.findByAdvocatePostId(postId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= GET BY POST + REACTION =================
	@GetMapping("/post/{postId}/reaction")
	public ResponseEntity<?> getByPostAndReaction(@PathVariable String postId, @RequestParam PostReactions reaction) {
		try {
			return ResponseEntity.ok(postReactionService.findByAdvocatePostIdAndPostReaction(postId, reaction));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= SEARCH COMMENT =================
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String comment) {
		try {
			return ResponseEntity.ok(postReactionService.findByCommentContainingIgnoreCase(comment));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= GET ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.ok(postReactionService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
