package com.example.demo700.Controllers.CaseControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.example.demo700.Model.CaseModels.DocumentDraft;
import com.example.demo700.Services.CaseServices.DocumentDraftService;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/document-draft")
public class DocumentDraftController {

	@Autowired
	private DocumentDraftService documentDraftService;

	@Autowired
	private ImageService imageService;

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

	// ========================= ADD =========================
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addDocumentDraft(@RequestPart("advocateId") String advocateId,
			@RequestPart("caseId") String caseId,
			@RequestPart(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant issuedDate,
			@RequestPart(value = "files", required = false) MultipartFile files[],
			@RequestParam("userId") String userId) {
		try {
			DocumentDraft draft = new DocumentDraft();
			draft.setAdvocateId(advocateId);
			draft.setCaseId(caseId);
			draft.setIssuedDate(issuedDate != null ? issuedDate : Instant.now());

			return ResponseEntity.ok(documentDraftService.addDocumentDraft(draft, userId, files));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ========================= UPDATE =========================
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateDocumentDraft(@PathVariable("id") String documentDraftId,
			@RequestPart("advocateId") String advocateId, @RequestPart("caseId") String caseId,
			@RequestPart(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant issuedDate,
			@RequestPart(value = "existingFiles", required = false) String existingFilesJson,
			@RequestPart(value = "files", required = false) MultipartFile files[],
			@RequestParam("userId") String userId) {
		try {
			DocumentDraft draft = new DocumentDraft();
			draft.setAdvocateId(advocateId);
			draft.setCaseId(caseId);
			draft.setIssuedDate(issuedDate != null ? issuedDate : Instant.now());

			try {

				ObjectMapper mapper = new ObjectMapper();
				String[] attachmentsId = mapper.readValue(existingFilesJson, String[].class);

				draft.setAttachmentsId(attachmentsId);

			} catch (Exception e) {

			}

			return ResponseEntity.ok(documentDraftService.updateDocumentDraft(draft, userId, documentDraftId, files));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// -------------- view the attachment ---------------------

	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
		try {

			return serveAttachment(attachmentId, "inline");

			/*
			 * GridFSFile file = imageService.getFile(attachmentId);
			 * 
			 * if (file == null) { return
			 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found"); }
			 * 
			 * InputStream stream = imageService.getStream(file);
			 * 
			 * return
			 * ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().
			 * get("type").toString())) .header(HttpHeaders.CONTENT_DISPOSITION,
			 * "inline; filename=\"" + file.getFilename() + "\"") .body(new
			 * InputStreamResource(stream));
			 */

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load file");
		}
	}

	// ------------------- download attachment -----------------

	@GetMapping("/attachment/{attachmentId}")
	public ResponseEntity<?> downloadAttachment(@PathVariable String attachmentId) {

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

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		}

	}

	// ========================= FIND BY ADVOCATE =========================
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocate(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(documentDraftService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= FIND BY CASE =========================
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCase(@PathVariable String caseId) {
		try {
			return ResponseEntity.ok(documentDraftService.findByCaseId(caseId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= FIND AFTER DATE =========================
	@GetMapping("/after")
	public ResponseEntity<?> findAfter(@RequestParam String date) {
		try {
			return ResponseEntity.ok(documentDraftService.findByIssuedDateAfter(Instant.parse(date)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= FIND BEFORE DATE =========================
	@GetMapping("/before")
	public ResponseEntity<?> findBefore(@RequestParam String date) {
		try {
			return ResponseEntity.ok(documentDraftService.findByIssuedDateBefore(Instant.parse(date)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= FIND ALL =========================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(documentDraftService.seeAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= FIND BY ID =========================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(documentDraftService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================= DELETE =========================
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id, @RequestParam String userId) {
		try {
			return ResponseEntity.ok(documentDraftService.removeDocumentDraft(id, userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
