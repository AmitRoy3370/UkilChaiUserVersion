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
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.QuestionResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;
import com.example.demo700.Services.AdvocateServices.PostContentService;
import com.example.demo700.Services.QNAServices.QuestionService;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

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
	 * ------------------------------------------------- ASK QUESTION (POST)
	 * multipart/form-data -------------------------------------------------
	 */
	@PostMapping(value = "/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> askQuestion(@RequestPart("usersId") String usersId, @RequestPart("userId") String userId,
			@RequestPart("message") String message, @RequestPart("questionType") String questionType,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AskQuestion question = new AskQuestion();
			question.setUserId(userId);
			question.setMessage(message);
			question.setQuestionType(AdvocateSpeciality.valueOf(questionType));
			question.setPostTime(Instant.now());

			AskQuestion saved = questionService.AskQuestion(question, usersId, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (NullPointerException | NoSuchElementException | ArithmeticException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

	/*
	 * ------------------------------------------------- UPDATE QUESTION (PUT)
	 * multipart/form-data -------------------------------------------------
	 */
	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateQuestion(@RequestPart("questionId") String questionId,
			@RequestPart("usersId") String usersId, @RequestPart("userId") String userId,
			@RequestPart("message") String message, @RequestPart("questionType") String questionType,
			@RequestPart(value = "attachmentId", required = false) String attachmentId,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AskQuestion question = new AskQuestion();
			question.setUserId(userId);
			question.setMessage(message);
			if (attachmentId != null && !attachmentId.isEmpty()) {
				question.setAttachmentId(attachmentId);
			}
			question.setQuestionType(AdvocateSpeciality.valueOf(questionType));

			AskQuestion updated = questionService.updateQuestion(question, usersId, questionId, file);

			return ResponseEntity.ok(updated);

		} catch (NullPointerException | NoSuchElementException | ArithmeticException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

	@GetMapping("/downloadQuestionContent")
	public ResponseEntity<?> downloadPostContent(@RequestParam String attachmentId) {

		try {

			return serveAttachment(attachmentId, "attachment");

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// -------------- view the attachment ---------------------

	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
		try {

			return serveAttachment(attachmentId, "inline");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load file");
		}
	}

	/*
	 * ------------------------------------------------- GET ALL QUESTIONS
	 * -------------------------------------------------
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			List<QuestionResponse> list = questionService.seeAll();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY USER
	 * -------------------------------------------------
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> findByUser(@PathVariable String userId) {
		try {
			return ResponseEntity.ok(questionService.findByUserId(userId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY MESSAGE KEYWORD
	 * -------------------------------------------------
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String keyword) {
		try {
			return ResponseEntity.ok(questionService.findByMessageContainingIgnoreCase(keyword));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY QUESTION TYPE
	 * -------------------------------------------------
	 */
	@GetMapping("/type/{type}")
	public ResponseEntity<?> findByType(@PathVariable AdvocateSpeciality type) {
		try {
			return ResponseEntity.ok(questionService.findByQuestionType(type));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY TIME RANGE
	 * -------------------------------------------------
	 */
	@GetMapping("/between")
	public ResponseEntity<?> findBetween(@RequestParam Instant start, @RequestParam Instant end) {
		try {
			return ResponseEntity.ok(questionService.findByPostTimeBetween(start, end));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/after")
	public ResponseEntity<?> findAfter(@RequestParam Instant time) {

		try {

			return ResponseEntity.status(200).body(questionService.findByPostTimeAfter(time));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	@GetMapping("/before")
	public ResponseEntity<?> findBefore(@RequestParam Instant time) {

		try {

			return ResponseEntity.status(200).body(questionService.findByPostTimeBefore(time));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	@GetMapping("/findByQuestionId")
	public ResponseEntity<?> findById(@RequestParam String questionId) {

		try {

			return ResponseEntity.status(200).body(questionService.findByQuestionId(questionId));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	/*
	 * ------------------------------------------------- DELETE QUESTION
	 * -------------------------------------------------
	 */
	@DeleteMapping("/{questionId}")
	public ResponseEntity<?> delete(@PathVariable String questionId, @RequestParam String userId) {
		try {
			boolean deleted = questionService.removeQuestion(userId, questionId);
			return ResponseEntity.ok(deleted);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// =================================================================
	// ================== PAGINATED ENDPOINTS ==========================
	// =================================================================

	/*
	 * ------------------------------------------------- GET ALL QUESTIONS
	 * PAGINATED -------------------------------------------------
	 */
	// GET /api/questions/all/paginated?page=0&size=10&sort=postTime,desc
	@GetMapping("/all/paginated")
	public ResponseEntity<?> getAllPaginated(Pageable pageable) {
		try {
			return ResponseEntity.ok(questionService.seeAll(pageable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY USER PAGINATED
	 * -------------------------------------------------
	 */
	// GET /api/questions/user/paginated/{userId}?page=0&size=10
	@GetMapping("/user/paginated/{userId}")
	public ResponseEntity<?> findByUserPaginated(@PathVariable String userId, Pageable pageable) {
		try {
			return ResponseEntity.ok(questionService.findByUserId(userId, pageable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY MESSAGE KEYWORD
	 * PAGINATED -------------------------------------------------
	 */
	// GET /api/questions/search/paginated?keyword=xyz&page=0&size=10
	@GetMapping("/search/paginated")
	public ResponseEntity<?> searchPaginated(@RequestParam("keyword") String keyword, Pageable pageable) {
		try {
			return ResponseEntity.ok(questionService.findByMessageContainingIgnoreCase(keyword, pageable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY QUESTION TYPE
	 * PAGINATED -------------------------------------------------
	 */
	// GET /api/questions/type/paginated/{type}?page=0&size=10
	@GetMapping("/type/paginated/{type}")
	public ResponseEntity<?> findByTypePaginated(@PathVariable AdvocateSpeciality type, Pageable pageable) {
		try {
			return ResponseEntity.ok(questionService.findByQuestionType(type, pageable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	 * ------------------------------------------------- FIND BY TIME RANGE PAGINATED
	 * -------------------------------------------------
	 */
	// GET /api/questions/between/paginated?start=...&end=...&page=0&size=10
	@GetMapping("/between/paginated")
	public ResponseEntity<?> findBetweenPaginated(@RequestParam Instant start, @RequestParam Instant end,
			Pageable pageable) {
		try {
			return ResponseEntity.ok(questionService.findByPostTimeBetween(start, end, pageable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// GET /api/questions/after/paginated?time=...&page=0&size=10
	@GetMapping("/after/paginated")
	public ResponseEntity<?> findAfterPaginated(@RequestParam Instant time, Pageable pageable) {

		try {

			return ResponseEntity.status(200).body(questionService.findByPostTimeAfter(time, pageable));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// GET /api/questions/before/paginated?time=...&page=0&size=10
	@GetMapping("/before/paginated")
	public ResponseEntity<?> findBeforePaginated(@RequestParam Instant time, Pageable pageable) {

		try {

			return ResponseEntity.status(200).body(questionService.findByPostTimeBefore(time, pageable));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// =================================================================
	// ================== ATTACHMENT SERVE HELPER ======================
	// =================================================================

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

			// এবার নতুন তৈরি হওয়া লোকাল ক্যাশ ফাইল থেকে রেসপন্স রিটার্ন করা
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