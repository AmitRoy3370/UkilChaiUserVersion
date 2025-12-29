package com.example.demo700.Controllers.QNAController;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.QNAModels.AnswerQuestion;
import com.example.demo700.Services.AdvocateServices.PostContentService;
import com.example.demo700.Services.QNAServices.AnswerQuestionService;
import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/api/answers")
public class AnswerQuestionController {

	@Autowired
	private AnswerQuestionService answerService;

	@Autowired
	private PostContentService imageService;

	/*
	 * ========================================================= 
	 * CREATE ANSWER
	 * (RequestPart) 
	 * =========================================================
	 */
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> answerQuestion(@RequestPart("answer") AnswerQuestion answerQuestion,
			@RequestPart("userId") String userId, @RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AnswerQuestion saved = answerService.answer(answerQuestion, userId, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * UPDATE ANSWER
	 * (RequestPart) 
	 * =========================================================
	 */
	@PutMapping(value = "/update/{answerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateAnswer(@PathVariable String answerId,
			@RequestPart("answer") AnswerQuestion answerQuestion, @RequestPart("userId") String userId,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AnswerQuestion updated = answerService.updateAnswer(answerQuestion, userId, answerId, file);

			return ResponseEntity.ok(updated);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping("/download")
	public ResponseEntity<?> downloadAnswerAttachment(@RequestParam String attachmentId) {

		try {

			GridFSFile file = imageService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
			}

			InputStream stream = imageService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	/*
	 * ========================================================= 
	 * FIND BY ANSWER ID
	 * =========================================================
	 */
	@GetMapping("/{answerId}")
	public ResponseEntity<?> findByAnswerId(@PathVariable String answerId) {
		try {
			return ResponseEntity.ok(answerService.findByAnswerId(answerId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND ALL ANSWERS
	 * =========================================================
	 */
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(answerService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND BY ADVOCATE ID
	 * =========================================================
	 */
	@GetMapping("/by-advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(answerService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND BY QUESTION ID
	 * =========================================================
	 */
	@GetMapping("/by-question/{questionId}")
	public ResponseEntity<?> findByQuestionId(@PathVariable String questionId) {
		try {
			return ResponseEntity.ok(answerService.findByQuestionId(questionId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * SEARCH BY MESSAGE
	 * =========================================================
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchByMessage(@RequestParam String keyword) {
		try {
			return ResponseEntity.ok(answerService.findByMessageContainingIgnoreCase(keyword));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND AFTER TIME
	 * =========================================================
	 */
	@GetMapping("/after")
	public ResponseEntity<?> findAfterTime(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant time) {
		try {
			return ResponseEntity.ok(answerService.findByTimeAfter(time));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND BEFORE TIME
	 * =========================================================
	 */
	@GetMapping("/before")
	public ResponseEntity<?> findBeforeTime(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant time) {
		try {
			return ResponseEntity.ok(answerService.findByTimeBefore(time));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * FIND BETWEEN TIME
	 * =========================================================
	 */
	@GetMapping("/between")
	public ResponseEntity<?> findBetweenTime(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,

			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
		try {
			return ResponseEntity.ok(answerService.findByTimeBetween(start, end));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= 
	 * DELETE ANSWER
	 * =========================================================
	 */
	@DeleteMapping("/delete/{answerId}")
	public ResponseEntity<?> deleteAnswer(@PathVariable String answerId, @RequestParam String userId) {
		try {
			boolean deleted = answerService.deleteAnswer(answerId, userId);
			return ResponseEntity.ok(Map.of("deleted", deleted));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}
}
