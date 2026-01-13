package com.example.demo700.Controllers.CaseControllers;

import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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

@RestController
@RequestMapping("/api/case")
public class CaseController {

	@Autowired
	private CaseService caseService;

	@Autowired
	private ImageService imageService;

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
	public ResponseEntity<?> updateCase(@RequestPart("caseId") String caseId, @RequestPart("caseName") String caseName,
			@RequestPart("userId") String userId, @RequestPart("advocateId") String advocateId,
			@RequestPart("caseType") String caseType,
			@RequestPart(value = "files", required = false) MultipartFile files[]) {
		try {
			Case acceptedCase = new Case();
			acceptedCase.setCaseName(caseName);
			acceptedCase.setUserId(userId);
			acceptedCase.setAdvocateId(advocateId);
			acceptedCase.setCaseType(AdvocateSpeciality.valueOf(caseType));

			return success(caseService.updateCase(acceptedCase, userId, caseId, files));
		} catch (Exception e) {
			return error(e);
		}
	}

	//-------------- view the attachment ---------------------
	
	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
	    try {
	        GridFSFile file = imageService.getFile(attachmentId);

	        if (file == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
	        }

	        InputStream stream = imageService.getStream(file);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
	                .body(new InputStreamResource(stream));

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to load file");
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
}
