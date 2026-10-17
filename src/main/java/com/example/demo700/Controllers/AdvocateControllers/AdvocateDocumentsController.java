package com.example.demo700.Controllers.AdvocateControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;
import com.example.demo700.Services.AdvocateServices.AdvocateDocumentsService;

@RestController
@RequestMapping("/api/advocate-documents")
public class AdvocateDocumentsController {

	@Autowired
	private AdvocateDocumentsService advocateDocumentsService;

	// ==================== ADD (multipart) ====================
	@PostMapping(value = "/add/{advocateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addDocuments(@PathVariable String advocateId,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {
		try {
			AdvocateDocuments saved = advocateDocumentsService.addDocuments(advocateId, files);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== UPDATE (multipart) ====================
	@PutMapping(value = "/update/{id}/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateDocuments(@PathVariable String id,
			@PathVariable String userId,
			@RequestPart("documents") AdvocateDocuments documents,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {
		try {
			AdvocateDocuments updated = advocateDocumentsService.updateDocuments(id, userId, documents, files);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(advocateDocumentsService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(advocateDocumentsService.findAll());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ADVOCATE ID ====================
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(advocateDocumentsService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ATTACHMENT ID (contains, ignore case) ====================
	@GetMapping("/attachment/{attachments}")
	public ResponseEntity<?> findByAttachments(@PathVariable String attachments) {
		try {
			return ResponseEntity.ok(
					advocateDocumentsService.findByAttachmentsContainingIgnoreCase(attachments));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			boolean removed = advocateDocumentsService.deleteDocuments(id, userId);
			if (!removed) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Advocate documents not removed");
			}
			return ResponseEntity.ok("Advocate documents removed successfully");
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================================================================
	// ==================== CENTRAL ERROR HANDLER =======================
	// ==================================================================
	private ResponseEntity<?> error(Exception e) {

		HttpStatus status;

		if (e instanceof NullPointerException) {
			status = HttpStatus.BAD_REQUEST;             // 400 — null body / files / id
		} else if (e instanceof NoSuchElementException) {
			status = HttpStatus.NOT_FOUND;               // 404 — no such user/advocate/documents
		} else if (e instanceof ArithmeticException) {
			status = HttpStatus.CONFLICT;                // 409 — duplicate / business rule violation
		} else if (e instanceof IllegalArgumentException) {
			status = HttpStatus.UNPROCESSABLE_ENTITY;    // 422 — bad argument
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;   // 500 — unexpected
		}

		return ResponseEntity.status(status).body(e.getMessage());
	}
}