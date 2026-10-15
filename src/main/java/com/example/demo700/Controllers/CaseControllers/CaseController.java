package com.example.demo700.Controllers.CaseControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Services.AdvocateServices.CVUploadService;
import com.example.demo700.Services.CaseServices.CaseService;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Utils.FileHexConverter;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/case")
public class CaseController {

	@Autowired
	private CaseService caseService;

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

	// ================== RESPONSE FORMAT ==================
	private ResponseEntity<?> success(Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("data", data);
		return ResponseEntity.ok(map);
	}

	private ResponseEntity<?> error(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		map.put("error", e.getMessage());

		HttpStatus status = HttpStatus.BAD_REQUEST;

		if (e instanceof NullPointerException)
			status = HttpStatus.BAD_REQUEST;
		else if (e instanceof java.util.NoSuchElementException)
			status = HttpStatus.NOT_FOUND;
		else if (e instanceof ArithmeticException)
			status = HttpStatus.CONFLICT;

		return ResponseEntity.status(status).body(map);
	}

	// ================= UPDATE CASE =================
	@PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateCase(@RequestParam("caseId") String caseId,
			@RequestParam("caseName") String caseName, @RequestParam("userId") String userId,
			@RequestParam("advocateId") String advocateId, @RequestParam("caseType") String caseType,
			@RequestParam(value = "existingFiles", required = false) String existingFilesJson,
			@RequestPart(value = "files", required = false) MultipartFile files[],
			@RequestParam("usersId") String usersId) {
		try {
			Case acceptedCase = new Case();
			acceptedCase.setCaseName(caseName);
			acceptedCase.setUserId(userId);
			acceptedCase.setAdvocateId(advocateId);
			acceptedCase.setCaseType(AdvocateSpeciality.valueOf(caseType));

			try {

				ObjectMapper mapper = new ObjectMapper();
				String[] existingFiles = mapper.readValue(existingFilesJson, String[].class);

				acceptedCase.setAttachmentId(existingFiles);

			} catch (Exception e) {

			}

			return success(caseService.updateCase(acceptedCase, usersId, caseId, files));
		} catch (Exception e) {
			return error(e);
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

	// ------------------- download attachment -----------------

	@GetMapping("/attachment/{attachmentId}")
	public ResponseEntity<?> downloadAttachment(@PathVariable String attachmentId) {

		try {
			return serveAttachment(attachmentId, "attachment");

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
		}

	}

	// ================= FIND BY CASE NAME =================
	@GetMapping("/name/{caseName}")
	public ResponseEntity<?> findByCaseName(@PathVariable String caseName) {
		try {
			return success(caseService.findByCaseNameIgnoreCase(caseName));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= SEARCH =================
	@GetMapping("/search/{name}")
	public ResponseEntity<?> search(@PathVariable String name) {
		try {
			return success(caseService.findByCaseNameContainingIgnoreCase(name));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY USER =================
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> byUser(@PathVariable String userId) {
		try {
			return success(caseService.findByUserId(userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY ADVOCATE =================
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> byAdvocate(@PathVariable String advocateId) {
		try {
			return success(caseService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY TYPE =================
	@GetMapping("/type/{type}")
	public ResponseEntity<?> byType(@PathVariable String type) {
		try {
			return success(caseService.findByCaseType(AdvocateSpeciality.valueOf(type)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= AFTER DATE =================
	@GetMapping("/after")
	public ResponseEntity<?> after(@RequestParam String time) {
		try {
			return success(caseService.findByIssuedTimeAfter(Instant.parse(time)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BEFORE DATE =================
	@GetMapping("/before")
	public ResponseEntity<?> before(@RequestParam String time) {
		try {
			return success(caseService.findByIssuedTimeBefore(Instant.parse(time)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= FIND ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> all() {
		try {
			return success(caseService.findAllCase());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= FIND BY ID =================
	@GetMapping("/{id}")
	public ResponseEntity<?> byId(@PathVariable String id) {
		try {
			return success(caseService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= DELETE =================
	@DeleteMapping("/{caseId}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String caseId, @PathVariable String userId) {
		try {
			return success(caseService.removeCase(caseId, userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// =================================================================
	// ================== PAGINATED ENDPOINTS ==========================
	// =================================================================

	// ================= SEARCH PAGINATED =================
	// GET /api/case/search/paginated?name=xyz&page=0&size=10&sort=issuedTime,desc
	@GetMapping("/search/paginated")
	public ResponseEntity<?> searchPaginated(@RequestParam("name") String name, Pageable pageable) {
		try {
			return success(caseService.findByCaseNameContainingIgnoreCase(name, pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY USER PAGINATED =================
	// GET /api/case/user/paginated/{userId}?page=0&size=10
	@GetMapping("/user/paginated/{userId}")
	public ResponseEntity<?> byUserPaginated(@PathVariable String userId, Pageable pageable) {
		try {
			return success(caseService.findByUserId(userId, pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY TYPE PAGINATED =================
	// GET /api/case/type/paginated/{type}?page=0&size=10
	@GetMapping("/type/paginated/{type}")
	public ResponseEntity<?> byTypePaginated(@PathVariable String type, Pageable pageable) {
		try {
			return success(caseService.findByCaseType(AdvocateSpeciality.valueOf(type), pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY ADVOCATE PAGINATED =================
	// GET /api/case/advocate/paginated/{advocateId}?page=0&size=10
	@GetMapping("/advocate/paginated/{advocateId}")
	public ResponseEntity<?> byAdvocatePaginated(@PathVariable String advocateId, Pageable pageable) {
		try {
			return success(caseService.findByAdvocateId(advocateId, pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= AFTER DATE PAGINATED =================
	// GET /api/case/after/paginated?time=2024-01-01T00:00:00Z&page=0&size=10
	@GetMapping("/after/paginated")
	public ResponseEntity<?> afterPaginated(@RequestParam("time") String time, Pageable pageable) {
		try {
			return success(caseService.findByIssuedTimeAfter(Instant.parse(time), pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BEFORE DATE PAGINATED =================
	// GET /api/case/before/paginated?time=2024-01-01T00:00:00Z&page=0&size=10
	@GetMapping("/before/paginated")
	public ResponseEntity<?> beforePaginated(@RequestParam("time") String time, Pageable pageable) {
		try {
			return success(caseService.findByIssuedTimeBefore(Instant.parse(time), pageable));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= FIND ALL PAGINATED =================
	// GET /api/case/all/paginated?page=0&size=10&sort=issuedTime,desc
	@GetMapping("/all/paginated")
	public ResponseEntity<?> allPaginated(Pageable pageable) {
		try {
			return success(caseService.findAllCase(pageable));
		} catch (Exception e) {
			return error(e);
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