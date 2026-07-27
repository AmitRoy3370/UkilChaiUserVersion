package com.example.demo700.Controllers.QNAController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/answers")
public class AnswerQuestionController {

	@Autowired
	private AnswerQuestionService answerService;

	@Autowired
	private PostContentService imageService;

	private final Path rootPath = Paths.get("Attachments");

	@PostConstruct
	public void init() {
		try {
			if (!Files.exists(rootPath)) {
				Files.createDirectories(rootPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ========================================================= CREATE ANSWER
	 * (RequestPart) =========================================================
	 */
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> answerQuestion(@RequestPart("advocateId") String advocateId,
			@RequestPart("message") String message, @RequestPart("questionId") String questionId,
			@RequestPart("userId") String userId, @RequestPart(value = "file", required = false) MultipartFile file) {
		try {

			AnswerQuestion answerQuestion = new AnswerQuestion();

			answerQuestion.setAdvocateId(advocateId);
			answerQuestion.setMessage(message);
			answerQuestion.setQuestionId(questionId);

			AnswerQuestion saved = answerService.answer(answerQuestion, userId, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	/*
	 * ========================================================= UPDATE ANSWER
	 * (RequestPart) =========================================================
	 */
	@PutMapping(value = "/update/{answerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateAnswer(@PathVariable String answerId, @RequestPart("advocateId") String advocateId,
			@RequestPart("message") String message, @RequestPart("questionId") String questionId,
			@RequestPart(value = "attachmentId", required = false) String attachmentId,
			@RequestPart("userId") String userId, @RequestPart(value = "file", required = false) MultipartFile file) {
		try {

			AnswerQuestion answerQuestion = new AnswerQuestion();

			answerQuestion.setAdvocateId(advocateId);
			answerQuestion.setMessage(message);
			answerQuestion.setQuestionId(questionId);
			if (attachmentId != null) {

				answerQuestion.setAttachmentId(attachmentId);

			}

			AnswerQuestion updated = answerService.updateAnswer(answerQuestion, userId, answerId, file);

			return ResponseEntity.ok(updated);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/download")
	public ResponseEntity<?> downloadAnswerAttachment(@RequestParam String attachmentId) {

		try {

			return serveAttachment(attachmentId, "attachment");

			/*
			 * GridFSFile file = imageService.getFile(attachmentId);
			 * 
			 * if (file == null) { return
			 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found"); }
			 * 
			 * InputStream stream = imageService.getStream(file);
			 * 
			 * return
			 * ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().
			 * get("type").toString())) .header(HttpHeaders.CONTENT_DISPOSITION,
			 * "attachment; filename=\"" + file.getFilename() + "\"") .body(new
			 * InputStreamResource(stream));
			 */

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	/*
	 * ========================================================= FIND BY ANSWER ID
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
	 * ========================================================= FIND ALL ANSWERS
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
	 * ========================================================= FIND BY ADVOCATE ID
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
	 * ========================================================= FIND BY QUESTION ID
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
	 * ========================================================= SEARCH BY MESSAGE
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
	 * ========================================================= FIND AFTER TIME
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
	 * ========================================================= FIND BEFORE TIME
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
	 * ========================================================= FIND BETWEEN TIME
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
	 * ========================================================= DELETE ANSWER
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

	private ResponseEntity<?> serveAttachment(String attachmentId, String dispositionType) {
		try {
			Path filePath = rootPath.resolve(attachmentId);
			File localFile = filePath.toFile();

			// 1. CASE 1: Local Disk Cache Hit (সরাসরি লোকাল ফাইল থেকে সার্ভ করবে)
			if (localFile.exists()) {
				String contentType = Files.probeContentType(filePath);
				if (contentType == null) {
					contentType = "application/octet-stream";
				}

				InputStream localStream = new FileInputStream(localFile);

				return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CACHE_CONTROL, "public, max-age=15552000") // 180 Days Browser Caching
						.header(HttpHeaders.CONTENT_DISPOSITION,
								dispositionType + "; filename=\"" + localFile.getName() + "\"")
						.body(new InputStreamResource(localStream));
			}

			// 2. CASE 2: Cache Miss - MongoDB GridFS থেকে ফাইল সংগ্রহ
			GridFSFile file = imageService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attachment/File not found");
			}

			String mimeType = file.getMetadata() != null && file.getMetadata().get("type") != null
					? file.getMetadata().get("type").toString()
					: "application/octet-stream";

			// MongoDB থেকে ফাইল রিড করে লোকাল Attachments ফোল্ডারে সেভ (ক্যাশ) করা
			try (InputStream dbStream = imageService.getStream(file)) {
				Files.copy(dbStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}

			// এবার নতুন তৈরি হওয়া লোকাল ক্যাশ ফাইল থেকে রেসপন্স রিটার্ন করা
			InputStream cachedStream = new FileInputStream(localFile);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
					.header(HttpHeaders.CACHE_CONTROL, "public, max-age=15552000")
					.header(HttpHeaders.CONTENT_DISPOSITION,
							dispositionType + "; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(cachedStream));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to process attachment: " + e.getMessage());
		}
	}

}
