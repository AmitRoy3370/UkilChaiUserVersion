package com.example.demo700.Controllers.TradeLicenseControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.TradeLicenseResponseDTO;
import com.example.demo700.Model.TradeLicenseModels.TradeLicense;
import com.example.demo700.Services.TradeLicenseServices.TradeLicenseService;
import com.example.demo700.Services.UserServices.ImageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/trade-license")
public class TradeLicenseController {

	@Autowired
	private TradeLicenseService tradeLicenseService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ImageService imageService;

	private final Path rootPath = Paths.get("Attachments");

	// ==================== CREATE ====================
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> addTradeLicense(@RequestParam("userId") String userId,
			@RequestParam("buisnessName") String buisnessName, @RequestParam("mobileNumber") String mobileNumber,
			@RequestParam("emailAdress") String emailAdress, @RequestParam("buisnessType") String buisnessType,
			@RequestParam("buisnessCategory") String buisnessCategory,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TradeLicense tradeLicense = new TradeLicense(userId, buisnessName, mobileNumber, emailAdress, buisnessType,
					buisnessCategory, null);

			TradeLicense savedLicense = tradeLicenseService.addTradeLicense(tradeLicense, userId, documents);

			response.put("status", "success");
			response.put("message", "Trade license added successfully");
			response.put("data", savedLicense);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (NoSuchElementException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (ArithmeticException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

		} catch (RuntimeException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== CREATE WITH JSON BODY ====================
	@PostMapping(value = "/add/json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> addTradeLicenseJson(@RequestBody TradeLicense tradeLicense,
			@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (tradeLicense == null) {
				response.put("status", "error");
				response.put("message", "Trade license data is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			tradeLicense.setUserId(userId);

			TradeLicense savedLicense = tradeLicenseService.addTradeLicense(tradeLicense, userId, null);

			response.put("status", "success");
			response.put("message", "Trade license added successfully");
			response.put("data", savedLicense);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

	// ==================== UPDATE WITH FILES ====================
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> updateTradeLicense(@PathVariable("id") String id,
			@RequestParam("userId") String userId, @RequestParam("buisnessName") String buisnessName,
			@RequestParam("mobileNumber") String mobileNumber, @RequestParam("emailAdress") String emailAdress,
			@RequestParam("buisnessType") String buisnessType,
			@RequestParam("buisnessCategory") String buisnessCategory,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trade license ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TradeLicense tradeLicense = new TradeLicense(userId, buisnessName, mobileNumber, emailAdress, buisnessType,
					buisnessCategory, null);

			TradeLicense updatedLicense = tradeLicenseService.updateTradeLicense(tradeLicense, userId, id, documents);

			response.put("status", "success");
			response.put("message", "Trade license updated successfully");
			response.put("data", updatedLicense);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

	// ==================== UPDATE WITH JSON (No Files) ====================
	@PutMapping(value = "/update/{id}/json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateTradeLicenseJson(@PathVariable("id") String id,
			@RequestBody TradeLicense tradeLicense, @RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trade license ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (tradeLicense == null) {
				response.put("status", "error");
				response.put("message", "Trade license data is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			tradeLicense.setUserId(userId);

			TradeLicense updatedLicense = tradeLicenseService.updateTradeLicense(tradeLicense, userId, id, null);

			response.put("status", "success");
			response.put("message", "Trade license updated successfully");
			response.put("data", updatedLicense);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

	// ==================== UPDATE WITH PARTIAL DATA + FILES ====================
	@PutMapping(value = "/update/{id}/partial", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> updateTradeLicensePartial(@PathVariable("id") String id,
			@RequestParam("userId") String userId, @RequestParam(required = false) String buisnessName,
			@RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String emailAdress,
			@RequestParam(required = false) String buisnessType,
			@RequestParam(required = false) String buisnessCategory,
			@RequestParam(required = false) String attachmentsId,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trade license ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TradeLicense tradeLicense = new TradeLicense();
			tradeLicense.setUserId(userId);

			// Set optional fields if provided
			if (buisnessName != null && !buisnessName.trim().isEmpty()) {
				tradeLicense.setBuisnessName(buisnessName.trim());
			}
			if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
				tradeLicense.setMobileNumber(mobileNumber.trim());
			}
			if (emailAdress != null && !emailAdress.trim().isEmpty()) {
				tradeLicense.setEmailAdress(emailAdress.trim());
			}
			if (buisnessType != null && !buisnessType.trim().isEmpty()) {
				tradeLicense.setBuisnessType(buisnessType.trim());
			}
			if (buisnessCategory != null && !buisnessCategory.trim().isEmpty()) {
				tradeLicense.setBuisnessCategory(buisnessCategory.trim());
			}

			// Parse attachments if provided
			if (attachmentsId != null && !attachmentsId.trim().isEmpty()) {
				try {
					List<String> attachmentList = objectMapper.readValue(attachmentsId.trim(),
							new TypeReference<List<String>>() {
							});
					tradeLicense.setDocuments(attachmentList);
				} catch (Exception e) {
					response.put("status", "error");
					response.put("message", "Invalid attachmentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			}

			TradeLicense updatedLicense = tradeLicenseService.updateTradeLicense(tradeLicense, userId, id, documents);

			response.put("status", "success");
			response.put("message", "Trade license updated successfully");
			response.put("data", updatedLicense);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") String id) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trade license ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TradeLicenseResponseDTO license = tradeLicenseService.findById(id);

			if (license == null) {
				response.put("status", "error");
				response.put("message", "Trade license not found with id: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("data", license);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> findAll() {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TradeLicenseResponseDTO> licenses = tradeLicenseService.findAll();

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY USER ID ====================
	@GetMapping("/search/user")
	public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService.findByUserId(userId.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found for user" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY BUSINESS NAME ====================
	@GetMapping("/search/businessName")
	public ResponseEntity<Map<String, Object>> findByBusinessName(@RequestParam("buisnessName") String buisnessName) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (buisnessName == null || buisnessName.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Business name is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByBuisnessNameContainingIgnoreCase(buisnessName.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY MOBILE NUMBER ====================
	@GetMapping("/search/mobile")
	public ResponseEntity<Map<String, Object>> findByMobileNumber(@RequestParam("mobileNumber") String mobileNumber) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Mobile number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByMobileNumberContainingIgnoreCase(mobileNumber.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY EMAIL ====================
	@GetMapping("/search/email")
	public ResponseEntity<Map<String, Object>> findByEmail(@RequestParam("emailAdress") String emailAdress) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (emailAdress == null || emailAdress.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Email address is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByEmailAdressContainingIgnoreCase(emailAdress.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY BUSINESS TYPE ====================
	@GetMapping("/search/businessType")
	public ResponseEntity<Map<String, Object>> findByBusinessType(@RequestParam("buisnessType") String buisnessType) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (buisnessType == null || buisnessType.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Business type is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByBuisnessTypeContainingIgnoreCase(buisnessType.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== FIND BY BUSINESS CATEGORY ====================
	@GetMapping("/search/businessCategory")
	public ResponseEntity<Map<String, Object>> findByBusinessCategory(
			@RequestParam("buisnessCategory") String buisnessCategory) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (buisnessCategory == null || buisnessCategory.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Business category is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByBuisnessCategoryContainingIgnoreCase(buisnessCategory.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
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
			if (document == null || document.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Document ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TradeLicenseResponseDTO> licenses = tradeLicenseService
					.findByDocumentsContainingIgnoreCase(document.trim());

			response.put("status", "success");
			response.put("message",
					licenses.isEmpty() ? "No trade licenses found" : "Trade licenses retrieved successfully");
			response.put("count", licenses.size());
			response.put("data", licenses);
			return ResponseEntity.ok(response);

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download image");
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

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteTradeLicense(@PathVariable("id") String id,
			@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trade license ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			boolean isDeleted = tradeLicenseService.removeTradeLicense(id, userId);

			if (isDeleted) {
				response.put("status", "success");
				response.put("message", "Trade license deleted successfully");
				return ResponseEntity.ok(response);
			} else {
				response.put("status", "error");
				response.put("message", "Trade license could not be deleted");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}

		} catch (NullPointerException e) {
			response.put("status", "error");
			response.put("message", "Invalid request: " + e.getMessage());
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

}