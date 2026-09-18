package com.example.demo700.Controllers.RJSCControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.RJSCResponseDTO;
import com.example.demo700.Model.RJSCModels.RJSC;
import com.example.demo700.Services.RJSCServices.RJSCService;
import com.example.demo700.Services.UserServices.ImageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/api/rjsc")
public class RJSCController {

	@Autowired
	private RJSCService rjscService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ImageService imageService;

	private final Path rootPath = Paths.get("Attachments");

	// ==================== CREATE ====================
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> addRJSC(@RequestParam("userId") String userId,
			@RequestParam("compilenceService") String compilenceService,
			@RequestParam("registrationNo") String registrationNo, @RequestParam("email") String email,
			@RequestParam("companyName") String companyName,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "attachmentsId", required = false) String attachmentsId,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (compilenceService == null || compilenceService.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Compliance Service is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (registrationNo == null || registrationNo.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Registration number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (email == null || email.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Email is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (companyName == null || companyName.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Company name is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			RJSC rjsc = new RJSC();
			rjsc.setUserId(userId.trim());
			rjsc.setCompilenceService(compilenceService.trim());
			rjsc.setRegistrationNo(registrationNo.trim());
			rjsc.setEmail(email.trim());
			rjsc.setCompanyName(companyName.trim());

			// Set year (defaults to now if not provided)
			if (year != null && !year.trim().isEmpty()) {
				try {
					rjsc.setYear(Instant.parse(year.trim()));
				} catch (DateTimeParseException e) {
					response.put("status", "error");
					response.put("message", "Invalid year format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			} else {
				rjsc.setYear(Instant.now());
			}

			// ✅ Parse attachmentsId JSON string → List<String> using ObjectMapper
			if (attachmentsId != null && !attachmentsId.trim().isEmpty()) {
				try {
					List<String> attachmentList = objectMapper.readValue(attachmentsId.trim(),
							new TypeReference<List<String>>() {
							});
					rjsc.setDocuments(attachmentList);
					System.out.println("✅ Parsed " + attachmentList.size() + " attachments from attachmentsId");
				} catch (Exception e) {
					System.err.println("❌ Invalid attachmentsId format: " + e.getMessage());
					response.put("status", "error");
					response.put("message", "Invalid attachmentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			} else {
				rjsc.setDocuments(new java.util.ArrayList<>());
			}

			RJSC savedRJSC = rjscService.addRJSC(rjsc, userId, documents);

			response.put("status", "success");
			response.put("message", "RJSC added successfully");
			response.put("data", savedRJSC);
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
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== UPDATE ====================
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> updateRJSC(@PathVariable("id") String id,
			@RequestParam("userId") String userId, @RequestParam(required = false) String compilenceService,
			@RequestParam(required = false) String registrationNo, @RequestParam(required = false) String email,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String year,
			@RequestParam(required = false) String attachmentsId,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "RJSC ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			RJSC rjsc = new RJSC();
			rjsc.setUserId(userId.trim());

			// Set optional fields only if provided
			if (compilenceService != null && !compilenceService.trim().isEmpty()) {
				rjsc.setCompilenceService(compilenceService.trim());
			}
			if (registrationNo != null && !registrationNo.trim().isEmpty()) {
				rjsc.setRegistrationNo(registrationNo.trim());
			}
			if (email != null && !email.trim().isEmpty()) {
				rjsc.setEmail(email.trim());
			}
			if (companyName != null && !companyName.trim().isEmpty()) {
				rjsc.setCompanyName(companyName.trim());
			}
			if (year != null && !year.trim().isEmpty()) {
				try {
					rjsc.setYear(Instant.parse(year.trim()));
				} catch (DateTimeParseException e) {
					response.put("status", "error");
					response.put("message", "Invalid year format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			}

			// ✅ Parse attachmentsId JSON string → List<String> using ObjectMapper
			if (attachmentsId != null && !attachmentsId.trim().isEmpty()) {
				try {
					List<String> attachmentList = objectMapper.readValue(attachmentsId.trim(),
							new TypeReference<List<String>>() {
							});
					rjsc.setDocuments(attachmentList);
					System.out.println("✅ Parsed " + attachmentList.size() + " attachments from attachmentsId");
				} catch (Exception e) {
					System.err.println("❌ Invalid attachmentsId format: " + e.getMessage());
					response.put("status", "error");
					response.put("message", "Invalid attachmentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			} else {
				// If no attachmentsId provided, use empty list
				// The service layer will handle merging with existing documents
				rjsc.setDocuments(new java.util.ArrayList<>());
			}

			RJSC updatedRJSC = rjscService.updateRJSC(rjsc, userId, id, documents);

			response.put("status", "success");
			response.put("message", "RJSC updated successfully");
			response.put("data", updatedRJSC);
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
			e.printStackTrace();
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
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "RJSC ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			RJSCResponseDTO rjsc = rjscService.findById(id);

			if (rjsc == null) {
				response.put("status", "error");
				response.put("message", "RJSC not found with id: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("data", rjsc);
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
			List<RJSCResponseDTO> rjscs = rjscService.findAll();

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY USER ID ====================
	@GetMapping("/search/userId")
	public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			// ✅ Normalize: if userId contains a comma (duplicate param), take the first
			if (userId != null && userId.contains(",")) {
				userId = userId.split(",")[0].trim();
				System.out.println("⚠️ Normalized userId to: " + userId);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByUserId(userId.trim());

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY COMPLIANCE SERVICE ====================
	@GetMapping("/search/compilenceService")
	public ResponseEntity<Map<String, Object>> findByCompilenceService(@RequestParam String compilenceService) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (compilenceService == null || compilenceService.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Compliance service is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService
					.findByCompilenceServiceContainingIgnoreCase(compilenceService.trim());

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY REGISTRATION NO ====================
	@GetMapping("/search/registrationNo")
	public ResponseEntity<Map<String, Object>> findByRegistrationNo(@RequestParam String registrationNo) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (registrationNo == null || registrationNo.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Registration number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByRegistrationNoContainingIgnoreCase(registrationNo.trim());

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY EMAIL ====================
	@GetMapping("/search/email")
	public ResponseEntity<Map<String, Object>> findByEmail(@RequestParam String email) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (email == null || email.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Email is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			RJSCResponseDTO rjsc = rjscService.findByEmail(email.trim());

			if (rjsc == null) {
				response.put("status", "error");
				response.put("message", "RJSC not found with email: " + email);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("data", rjsc);
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

	// ==================== FIND BY COMPANY NAME ====================
	@GetMapping("/search/companyName")
	public ResponseEntity<Map<String, Object>> findByCompanyName(@RequestParam String companyName) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (companyName == null || companyName.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Company name is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByCompanyNameContainingIgnoreCase(companyName.trim());

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY YEAR AFTER ====================
	@GetMapping("/search/year/after")
	public ResponseEntity<Map<String, Object>> findByYearAfter(@RequestParam String year) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (year == null || year.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Year is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			Instant yearInstant;
			try {
				yearInstant = Instant.parse(year.trim());
			} catch (DateTimeParseException e) {
				response.put("status", "error");
				response.put("message", "Invalid year format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByYearAfter(yearInstant);

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY YEAR BEFORE ====================
	@GetMapping("/search/year/before")
	public ResponseEntity<Map<String, Object>> findByYearBefore(@RequestParam String year) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (year == null || year.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Year is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			Instant yearInstant;
			try {
				yearInstant = Instant.parse(year.trim());
			} catch (DateTimeParseException e) {
				response.put("status", "error");
				response.put("message", "Invalid year format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByYearBefore(yearInstant);

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== FIND BY DOCUMENTS ====================
	@GetMapping("/search/documents")
	public ResponseEntity<Map<String, Object>> findByDocuments(@RequestParam String documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (documents == null || documents.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Document is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<RJSCResponseDTO> rjscs = rjscService.findByDocuments(documents.trim());

			response.put("status", "success");
			response.put("message", "RJSCs retrieved successfully");
			response.put("count", rjscs.size());
			response.put("data", rjscs);
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

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteRJSC(@PathVariable("id") String id,
			@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "RJSC ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			boolean isDeleted = rjscService.delete(id, userId);

			if (isDeleted) {
				response.put("status", "success");
				response.put("message", "RJSC deleted successfully");
				return ResponseEntity.ok(response);
			} else {
				response.put("status", "error");
				response.put("message", "RJSC could not be deleted");
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