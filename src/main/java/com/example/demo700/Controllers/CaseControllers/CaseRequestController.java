package com.example.demo700.Controllers.CaseControllers;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseRequest;
import com.example.demo700.Services.CaseServices.CaseRequestService;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/case-request")
public class CaseRequestController {

	@Autowired
	private CaseRequestService caseRequestService;
	
	@Autowired
	private ImageService imageService;

	// ================= ADD =================
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> add(@RequestPart("caseName") String caseName, @RequestPart("caseType") String caseType,
			@RequestPart("userId") String userId,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {
		try {
			CaseRequest request = new CaseRequest();
			request.setCaseName(caseName);
			request.setCaseType(AdvocateSpeciality.valueOf(caseType)); // enum
			request.setUserId(userId);

			return ResponseEntity.ok(caseRequestService.addCaseRequest(request, userId, files));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= UPDATE =================
	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@RequestPart("caseRequestId") String caseRequestId,
			@RequestPart("caseName") String caseName, @RequestPart("caseType") String caseType,
			@RequestPart("userId") String userId,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {
		try {
			CaseRequest request = new CaseRequest();
			request.setCaseName(caseName);
			request.setCaseType(AdvocateSpeciality.valueOf(caseType));
			request.setUserId(userId);

			return ResponseEntity.ok(caseRequestService.updateCaseRequest(request, userId, caseRequestId, files));
		} catch (Exception e) {
			return error(e);
		}
	}
	
	// --------------------------- view the attachment-----------------
	
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


	// ================= SEARCH =================
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String name) {
		try {
			return ResponseEntity.ok(caseRequestService.findByCaseNameContainingIgnoreCase(name));
		} catch (Exception e) {
			return error(e);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> byUser(@PathVariable String userId) {
		try {
			return ResponseEntity.ok(caseRequestService.findByUserId(userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	@GetMapping("/type/{type}")
	public ResponseEntity<?> byType(@PathVariable AdvocateSpeciality type) {
		try {
			return ResponseEntity.ok(caseRequestService.findByCaseType(type));
		} catch (Exception e) {
			return error(e);
		}
	}

	@GetMapping("/after")
	public ResponseEntity<?> after(@RequestParam Instant date) {
		try {
			return ResponseEntity.ok(caseRequestService.findByIssuedTimeAfter(date));
		} catch (Exception e) {
			return error(e);
		}
	}

	@GetMapping("/before")
	public ResponseEntity<?> before(@RequestParam Instant date) {
		try {
			return ResponseEntity.ok(caseRequestService.findByIssuedTimeBefore(date));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= GET ONE =================
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		try {
			return ResponseEntity.ok(caseRequestService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= GET ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> all() {
		try {
			return ResponseEntity.ok(caseRequestService.allCaseRequest());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= DELETE =================
	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			return ResponseEntity.ok(caseRequestService.removeCaseRequest(id, userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= ACCEPT CASE =================
	@PostMapping("/accept/{caseRequestId}/{userId}")
	public ResponseEntity<?> accept(@PathVariable String caseRequestId, @PathVariable String userId) {
		try {
			Case c = caseRequestService.handleCaseRequest(caseRequestId, userId);
			return ResponseEntity.ok(c);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= GLOBAL ERROR FORMAT =================
	private ResponseEntity<?> error(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, e.getMessage()));
	}

	static record ErrorResponse(boolean success, String message) {
	}
}
