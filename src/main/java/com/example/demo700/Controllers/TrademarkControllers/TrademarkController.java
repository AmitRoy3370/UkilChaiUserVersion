package com.example.demo700.Controllers.TrademarkControllers;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo700.DTOFiles.TrademarkResponse;
import com.example.demo700.Model.Trademarkmodels.Trademark;
import com.example.demo700.Services.TrademarkServices.TrademarkService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/trademark")
public class TrademarkController {

	@Autowired
	private TrademarkService trademarkService;

	@Autowired
	private ObjectMapper objectMapper;

	// ==================== CREATE ====================
	@PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Map<String, Object>> addTrademark(@RequestParam String userId,
			@RequestParam String legalProtection, @RequestParam String nationWiseValidity,
			@RequestParam String applicationType, @RequestParam String applicationName,
			@RequestParam double governmentFee, @RequestParam String organaizationalName,
			@RequestParam String trademarkName, @RequestParam String trademarkType, @RequestParam String classOfGoods,
			@RequestParam String adress, @RequestParam String email, @RequestParam String mobileNumber,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			Trademark trademark = new Trademark(legalProtection, nationWiseValidity, applicationType, applicationName,
					governmentFee, organaizationalName, trademarkName, trademarkType, classOfGoods, adress, email,
					mobileNumber, null, userId);

			Trademark savedTrademark = trademarkService.addTrademark(trademark, userId, documents);

			response.put("status", "success");
			response.put("message", "Trademark added successfully");
			response.put("data", savedTrademark);
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (RuntimeException e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// ==================== UPDATE WITH OBJECT MAPPER ====================
	@PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Map<String, Object>> updateTrademark(@PathVariable String id, @RequestParam String userId,
			@RequestParam(required = false) String legalProtection,
			@RequestParam(required = false) String nationWiseValidity,
			@RequestParam(required = false) String applicationType,
			@RequestParam(required = false) String applicationName,
			@RequestParam(required = false) Double governmentFee,
			@RequestParam(required = false) String organaizationalName,
			@RequestParam(required = false) String trademarkName, @RequestParam(required = false) String trademarkType,
			@RequestParam(required = false) String classOfGoods, @RequestParam(required = false) String adress,
			@RequestParam(required = false) String email, @RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String documentsId,
			@RequestPart(value = "documents", required = false) MultipartFile[] documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Validate required fields
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			Trademark trademark = new Trademark();
			trademark.setUserId(userId.trim());

			// Set optional fields with validation
			if (legalProtection != null && !legalProtection.trim().isEmpty()) {
				trademark.setLegalProtection(legalProtection.trim());
			}
			if (nationWiseValidity != null && !nationWiseValidity.trim().isEmpty()) {
				trademark.setNationWiseValidity(nationWiseValidity.trim());
			}
			if (applicationType != null && !applicationType.trim().isEmpty()) {
				trademark.setApplicationType(applicationType.trim());
			}
			if (applicationName != null && !applicationName.trim().isEmpty()) {
				trademark.setApplicationName(applicationName.trim());
			}
			if (governmentFee != null) {
				trademark.setGovernmentFee(governmentFee);
			}
			if (organaizationalName != null && !organaizationalName.trim().isEmpty()) {
				trademark.setOrganaizationalName(organaizationalName.trim());
			}
			if (trademarkName != null && !trademarkName.trim().isEmpty()) {
				trademark.setTrademarkName(trademarkName.trim());
			}
			if (trademarkType != null && !trademarkType.trim().isEmpty()) {
				trademark.setTrademarkType(trademarkType.trim());
			}
			if (classOfGoods != null && !classOfGoods.trim().isEmpty()) {
				trademark.setClassOfGoods(classOfGoods.trim());
			}
			if (adress != null && !adress.trim().isEmpty()) {
				trademark.setAdress(adress.trim());
			}
			if (email != null && !email.trim().isEmpty()) {
				trademark.setEmail(email.trim());
			}
			if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
				trademark.setMobileNumber(mobileNumber.trim());
			}

			// Use ObjectMapper to convert JSON string to List for documents
			if (documentsId != null && !documentsId.trim().isEmpty()) {
				try {
					List<String> documentsList = objectMapper.readValue(documentsId.trim(),
							new TypeReference<List<String>>() {
							});
					trademark.setDocuments(documentsList);
				} catch (Exception e) {
					response.put("status", "error");
					response.put("message", "Invalid documentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			} else {
				// If no documentsId provided, keep empty list
				trademark.setDocuments(new java.util.ArrayList<>());
			}

			Trademark updatedTrademark = trademarkService.updateTrademark(trademark, userId, id, documents);

			response.put("status", "success");
			response.put("message", "Trademark updated successfully");
			response.put("data", updatedTrademark);
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (RuntimeException e) {
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
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TrademarkResponse trademark = trademarkService.findById(id);

			if (trademark == null) {
				response.put("status", "error");
				response.put("message", "Trademark not found with id: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("data", trademark);
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
			List<TrademarkResponse> trademarks = trademarkService.findAll();

			response.put("status", "success");
			response.put("message", trademarks.isEmpty() ? "No trademarks found" : "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY LEGAL PROTECTION ====================
	@GetMapping("/search/legalProtection")
	public ResponseEntity<Map<String, Object>> findByLegalProtection(
			@RequestParam("legalProtection") String legalProtection) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (legalProtection == null || legalProtection.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Legal protection is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByLegalProtectionContainingIgnoreCase(legalProtection.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY NATION WISE VALIDITY ====================
	@GetMapping("/search/nationWiseValidity")
	public ResponseEntity<Map<String, Object>> findByNationWiseValidity(
			@RequestParam("nationWiseValidity") String nationWiseValidity) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (nationWiseValidity == null || nationWiseValidity.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Nation wise validity is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByNationWiseValidityContainingIgnoreCase(nationWiseValidity.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY APPLICATION TYPE ====================
	@GetMapping("/search/applicationType")
	public ResponseEntity<Map<String, Object>> findByApplicationType(
			@RequestParam("applicationType") String applicationType) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (applicationType == null || applicationType.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Application type is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByApplicationTypeContainingIgnoreCase(applicationType.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY APPLICATION NAME ====================
	@GetMapping("/search/applicationName")
	public ResponseEntity<Map<String, Object>> findByApplicationName(
			@RequestParam("applicationName") String applicationName) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (applicationName == null || applicationName.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Application name is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByApplicationNameContainingIgnoreCase(applicationName.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY GOVERNMENT FEE GREATER THAN EQUAL
	// ====================
	@GetMapping("/search/governmentFee/greaterThan")
	public ResponseEntity<Map<String, Object>> findByGovernmentFeeGreaterThanEqual(
			@RequestParam("governmentFee") double governmentFee) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TrademarkResponse> trademarks = trademarkService.findByGovernmentFeeGreaterThanEqual(governmentFee);

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY GOVERNMENT FEE LESS THAN EQUAL
	// ====================
	@GetMapping("/search/governmentFee/lessThan")
	public ResponseEntity<Map<String, Object>> findByGovernmentFeeLessThanEqual(
			@RequestParam("governmentFee") double governmentFee) {

		Map<String, Object> response = new HashMap<>();

		try {
			List<TrademarkResponse> trademarks = trademarkService.findByGovernmentFeeLessThanEqual(governmentFee);

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY EMAIL ====================
	@GetMapping("/search/email")
	public ResponseEntity<Map<String, Object>> findByEmail(@RequestParam("email") String email) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (email == null || email.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Email is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TrademarkResponse trademark = trademarkService.findByEmailIgnoreCase(email.trim());

			response.put("status", "success");
			response.put("data", trademark);
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

	// ==================== FIND BY EMAIL CONTAINING ====================
	@GetMapping("/search/email/containing")
	public ResponseEntity<Map<String, Object>> findByEmailContaining(@RequestParam("email") String email) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (email == null || email.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Email is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService.findByEmailContainingIgnoreCase(email.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY MOBILE NUMBER ====================
	@GetMapping("/search/mobileNumber")
	public ResponseEntity<Map<String, Object>> findByMobileNumber(@RequestParam("mobileNumber") String mobileNumber) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Mobile number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TrademarkResponse trademark = trademarkService.findByMobileNumberIgnoreCase(mobileNumber.trim());

			response.put("status", "success");
			response.put("data", trademark);
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

	// ==================== FIND BY MOBILE NUMBER CONTAINING ====================
	@GetMapping("/search/mobileNumber/containing")
	public ResponseEntity<Map<String, Object>> findByMobileNumberContaining(
			@RequestParam("mobileNumber") String mobileNumber) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Mobile number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByMobileNumberContainingIgnoreCase(mobileNumber.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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
	public ResponseEntity<Map<String, Object>> findByDocuments(@RequestParam("documents") String documents) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (documents == null || documents.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Document ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService.findByDocumentsContainingIgnoreCase(documents.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY USER ID ====================
	@GetMapping("/search/userId")
	public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService.findByUserId(userId.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY ADDRESS ====================
	@GetMapping("/search/adress")
	public ResponseEntity<Map<String, Object>> findByAdress(@RequestParam("adress") String adress) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (adress == null || adress.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Address is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService.findByAdressContainingIgnoreCase(adress.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY TRADEMARK NAME ====================
	@GetMapping("/search/trademarkName")
	public ResponseEntity<Map<String, Object>> findByTrademarkName(
			@RequestParam("trademarkName") String trademarkName) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (trademarkName == null || trademarkName.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark name is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByTrademarkNameContainingIgnoreCase(trademarkName.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY TRADEMARK TYPE ====================
	@GetMapping("/search/trademarkType")
	public ResponseEntity<Map<String, Object>> findByTrademarkType(
			@RequestParam("trademarkType") String trademarkType) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (trademarkType == null || trademarkType.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark type is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByTrademarkTypeContainingIgnoreCase(trademarkType.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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

	// ==================== FIND BY CLASS OF GOODS ====================
	@GetMapping("/search/classOfGoods")
	public ResponseEntity<Map<String, Object>> findByClassOfGoods(@RequestParam("classOfGoods") String classOfGoods) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (classOfGoods == null || classOfGoods.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Class of goods is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkResponse> trademarks = trademarkService
					.findByClassOfGoodsContainingIgnoreCase(classOfGoods.trim());

			response.put("status", "success");
			response.put("message", "Trademarks retrieved successfully");
			response.put("count", trademarks.size());
			response.put("data", trademarks);
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
	public ResponseEntity<Map<String, Object>> deleteTrademark(@PathVariable("id") String id,
			@RequestParam("userId") String userId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (userId == null || userId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			boolean isDeleted = trademarkService.deleteTrademark(id, userId);

			if (isDeleted) {
				response.put("status", "success");
				response.put("message", "Trademark deleted successfully");
				return ResponseEntity.ok(response);
			} else {
				response.put("status", "error");
				response.put("message", "Trademark could not be deleted");
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