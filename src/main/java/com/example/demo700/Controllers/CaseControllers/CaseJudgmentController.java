package com.example.demo700.Controllers.CaseControllers;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.CaseModels.CaseJudgment;
import com.example.demo700.Services.CaseServices.CaseJudgmentService;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/case-judgment")
public class CaseJudgmentController {

	@Autowired
	private CaseJudgmentService caseJudgmentService;

	@Autowired
	private ImageService imageService;

	// ==================== ADD ====================
	@PostMapping(value = "/add", consumes = "multipart/form-data")
	public ResponseEntity<?> addCaseJudgment(@RequestPart("caseId") String caseId, @RequestPart("result") String result,
			@RequestPart(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date,
			@RequestPart(value = "file", required = false) MultipartFile file, @RequestParam("userId") String userId) {
		try {
			CaseJudgment judgment = new CaseJudgment();
			judgment.setCaseId(caseId);
			judgment.setResult(result);
			judgment.setDate(date != null ? date : Instant.now());

			return ResponseEntity.ok(caseJudgmentService.addCaseJudgment(judgment, userId, file));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ==================== UPDATE ====================
	@PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
	public ResponseEntity<?> updateCaseJudgment(@PathVariable("id") String caseJudgmentId,
			@RequestPart("caseId") String caseId, @RequestPart("result") String result,
			@RequestPart(value = "judgmentAttachmentId", required = false) String judgmentAttachmentId,
			@RequestPart(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date,
			@RequestPart(value = "file", required = false) MultipartFile file, @RequestParam("userId") String userId) {
		try {
			CaseJudgment judgment = new CaseJudgment();
			judgment.setCaseId(caseId);
			judgment.setResult(result);
			judgment.setJudgmentAttachmentId(judgmentAttachmentId);
			judgment.setDate(date != null ? date : Instant.now());

			return ResponseEntity.ok(caseJudgmentService.updateCaseJudgment(judgment, userId, caseJudgmentId, file));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// -------------- view the attachment ---------------------

	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
		try {
			GridFSFile file = imageService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
			}

			InputStream stream = imageService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load file");
		}
	}

	// ------------------- download attachment -----------------

	@GetMapping("/attachment/{attachmentId}")
	public ResponseEntity<?> downloadAttachment(@PathVariable String attachmentId) {

		try {
			GridFSFile file = imageService.getFile(attachmentId);

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

	// ==================== FIND BY CASE ====================
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCase(@PathVariable String caseId) {
		try {
			return ResponseEntity.ok(caseJudgmentService.findByCaseId(caseId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== SEARCH BY RESULT ====================
	@GetMapping("/search")
	public ResponseEntity<?> searchByResult(@RequestParam String result) {
		try {
			return ResponseEntity.ok(caseJudgmentService.findByResultContainingIgnoreCase(result));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== DATE AFTER ====================
	@GetMapping("/after")
	public ResponseEntity<?> findAfter(@RequestParam String date) {
		try {
			return ResponseEntity.ok(caseJudgmentService.findByDateAfter(Instant.parse(date)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== DATE BEFORE ====================
	@GetMapping("/before")
	public ResponseEntity<?> findBefore(
			@RequestParam String date) {
		try {
			return ResponseEntity.ok(caseJudgmentService.findByDateBefore(Instant.parse(date)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(caseJudgmentService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(caseJudgmentService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==================== DELETE ====================
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id, @RequestParam String userId) {
		try {
			return ResponseEntity.ok(caseJudgmentService.removeCaseJudgment(id, userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
