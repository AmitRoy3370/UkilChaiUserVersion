package com.example.demo700.Controllers.QNAController;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;
import com.example.demo700.Services.QNAServices.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/*
	 * ------------------------------------------------- ASK QUESTION (POST)
	 * multipart/form-data -------------------------------------------------
	 */
	@PostMapping(value = "/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> askQuestion(@RequestPart("usersId") String usersId, @RequestPart("userId") String userId,
			@RequestPart("message") String message, @RequestPart("questionType") AdvocateSpeciality questionType,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AskQuestion question = new AskQuestion();
			question.setUserId(userId);
			question.setMessage(message);
			question.setQuestionType(questionType);
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
	public ResponseEntity<?> updateQuestion(@RequestPart("questionId") String questionId, @RequestPart("usersId") String usersId,
			@RequestPart("userId") String userId, @RequestPart("message") String message,
			@RequestPart("questionType") AdvocateSpeciality questionType,
			@RequestPart("attachmentId") String attachmentId,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			AskQuestion question = new AskQuestion();
			question.setUserId(userId);
			question.setMessage(message);
			question.setQuestionType(questionType);

			AskQuestion updated = questionService.updateQuestion(question, usersId, questionId, file);

			return ResponseEntity.ok(updated);

		} catch (NullPointerException | NoSuchElementException | ArithmeticException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

	/*
	 * ------------------------------------------------- GET ALL QUESTIONS
	 * -------------------------------------------------
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			List<AskQuestion> list = questionService.seeAll();
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
}
