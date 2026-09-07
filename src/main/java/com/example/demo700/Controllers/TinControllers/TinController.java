package com.example.demo700.Controllers.TinControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.TinResponseDTO;
import com.example.demo700.Model.TinModels.Tin;
import com.example.demo700.Services.TinServices.TinService;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/api/tin")
public class TinController {

	@Autowired
	private TinService tinService;

	@Autowired
	private ImageService imageService;

	private final Path rootPath = Paths.get("Attachments");

	// ==================== CREATE ====================
	@PostMapping(value = "/add", consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String, Object>> addTin(@RequestParam String userId, @RequestParam String fullName,
			@RequestParam String fatherName, @RequestParam String motherName, @RequestParam String phone,
			@RequestParam String dateOfBirth, @RequestParam String presentAdress, @RequestParam String permanentAdress,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			Instant dob = Instant.parse(dateOfBirth);

			Tin tin = new Tin(fullName, fatherName, motherName, phone, dob, presentAdress, permanentAdress, null,
					userId);

			Tin savedTin = tinService.addTin(tin, userId, documents);

			response.put("status", "success");
			response.put("message", "Tin added successfully");
			response.put("data", savedTin);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (ArithmeticException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== UPDATE ====================
	@PutMapping(value = "/update/{id}", consumes = { "application/json" })
	public ResponseEntity<Map<String, Object>> updateTin(@PathVariable String id, @RequestParam String userId,
			@RequestParam(required = false) String fullName, @RequestParam(required = false) String fatherName,
			@RequestParam(required = false) String motherName, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String dateOfBirth, @RequestParam(required = false) String presentAdress,
			@RequestParam(required = false) String permanentAdress,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			Tin tin = new Tin();
			tin.setUserId(userId);

			if (fullName != null)
				tin.setFullName(fullName);
			if (fatherName != null)
				tin.setFatherName(fatherName);
			if (motherName != null)
				tin.setMotherName(motherName);
			if (phone != null)
				tin.setPhone(phone);
			if (dateOfBirth != null)
				tin.setDateOfBirth(Instant.parse(dateOfBirth));
			if (presentAdress != null)
				tin.setPresentAdress(presentAdress);
			if (permanentAdress != null)
				tin.setPermanentAdress(permanentAdress);

			Tin updatedTin = tinService.updateTin(tin, userId, id, documents);

			response.put("status", "success");
			response.put("message", "Tin updated successfully");
			response.put("data", updatedTin);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (ArithmeticException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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

			return serveAttachment(attachmentId, "attachment");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

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

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") String id) {

		Map<String, Object> response = new HashMap<>();

		try {
			TinResponseDTO tin = tinService.findById(id);

			if (tin == null) {
				response.put("status", "error");
				response.put("message", "Tin not found with id: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("data", tin);
			return ResponseEntity.ok(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> findAll() {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findAll();

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY FULL NAME ====================
	@GetMapping("/search/fullName")
	public ResponseEntity<Map<String, Object>> findByFullName(@RequestParam("fullName") String fullName) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByFullNameContainingIgnoreCase(fullName);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY FATHER NAME ====================
	@GetMapping("/search/fatherName")
	public ResponseEntity<Map<String, Object>> findByFatherName(@RequestParam("fatherName") String fatherName) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByFatherNameContainingIgnoreCase(fatherName);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY MOTHER NAME ====================
	@GetMapping("/search/motherName")
	public ResponseEntity<Map<String, Object>> findByMotherName(@RequestParam("motherName") String motherName) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByMotherNameContainingIgnoreCase(motherName);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY PHONE ====================
	@GetMapping("/search/phone")
	public ResponseEntity<Map<String, Object>> findByPhone(@RequestParam("phone") String phone) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByPhoneContainingIgnoreCase(phone);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY DATE OF BIRTH BEFORE ====================
	@GetMapping("/search/birthBefore")
	public ResponseEntity<Map<String, Object>> findByDateOfBirthBefore(
			@RequestParam("dateOfBirth") String dateOfBirth) {

		Map<String, Object> response = new HashMap<>();

		try {
			Instant dob = Instant.parse(dateOfBirth);
			List<TinResponseDTO> tins = tinService.findByDateOfBirthBefore(dob);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY DATE OF BIRTH AFTER ====================
	@GetMapping("/search/birthAfter")
	public ResponseEntity<Map<String, Object>> findByDateOfBirthAfter(@RequestParam("dateOfBirth") String dateOfBirth) {

		Map<String, Object> response = new HashMap<>();

		try {
			Instant dob = Instant.parse(dateOfBirth);
			List<TinResponseDTO> tins = tinService.findByDateOfBirthAfter(dob);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY PRESENT ADDRESS ====================
	@GetMapping("/search/presentAddress")
	public ResponseEntity<Map<String, Object>> findByPresentAddress(
			@RequestParam("presentAdress") String presentAdress) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByPresentAdressContainingIgnoreCase(presentAdress);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY PERMANENT ADDRESS ====================
	@GetMapping("/search/permanentAddress")
	public ResponseEntity<Map<String, Object>> findByPermanentAddress(
			@RequestParam("permanentAdress") String permanentAdress) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByPermanentAdressContainingIgnoreCase(permanentAdress);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY DOCUMENT ====================
	@GetMapping("/search/document")
	public ResponseEntity<Map<String, Object>> findByDocument(@RequestParam("document") String document) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TinResponseDTO> tins = tinService.findByDocumentsContainingIgnoreCase(document);

			response.put("status", "success");
			response.put("message", "Tins retrieved successfully");
			response.put("count", tins.size());
			response.put("data", tins);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteTin(@PathVariable("id") String id,
			@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			boolean isDeleted = tinService.removeTin(id, userId);

			if (isDeleted) {
				response.put("status", "success");
				response.put("message", "Tin deleted successfully");
				return ResponseEntity.ok(response);
			} else {
				response.put("status", "error");
				response.put("message", "Tin could not be deleted");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "False request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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