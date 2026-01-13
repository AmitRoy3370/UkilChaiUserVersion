package com.example.demo700.Controllers.CaseControllers;

import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Services.CaseServices.HearingService;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/hearing")
public class HearingController {

	@Autowired
	private HearingService hearingService;

	@Autowired
	private ImageService imageService;

	// ================= ADD =================
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addHearing(@PathVariable String userId, @RequestPart("caseId") String caseId,
			@RequestPart("hearningNumber") int hearningNumber,
			@RequestPart(value = "issuedDate", required = false) String issuedDate,
			@RequestPart(value = "files", required = false) MultipartFile files[]) {

		try {
			Hearing hearing = new Hearing();
			hearing.setCaseId(caseId);
			hearing.setHearningNumber(hearningNumber);

			if (issuedDate != null)
				hearing.setIssuedDate(Instant.parse(issuedDate));

			return ResponseEntity.ok(hearingService.addHearing(hearing, userId, files));

		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= UPDATE =================
	@PutMapping("/update/{hearingId}/{userId}")
	public ResponseEntity<?> updateHearing(@PathVariable String hearingId, @PathVariable String userId,
			@RequestPart("caseId") String caseId, @RequestPart("hearningNumber") int hearningNumber,
			@RequestPart(value = "issuedDate", required = false) String issuedDate,
			@RequestPart(value = "files", required = false) MultipartFile files[]) {

		try {
			Hearing hearing = new Hearing();
			hearing.setCaseId(caseId);
			hearing.setHearningNumber(hearningNumber);

			if (issuedDate != null)
				hearing.setIssuedDate(Instant.parse(issuedDate));

			return ResponseEntity.ok(hearingService.updateHearing(hearing, userId, hearingId, files));

		} catch (Exception e) {
			return error(e);
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

	// ================= FIND BY HEARING NUMBER =================
	@GetMapping("/number/{num}")
	public ResponseEntity<?> findByNumber(@PathVariable int num) {
		try {
			return ResponseEntity.ok(hearingService.findByHearningNumber(num));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= FIND BY CASE =================
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCase(@PathVariable String caseId) {
		try {
			return ResponseEntity.ok(hearingService.findByCaseId(caseId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= FIND BY CASE + NUMBER =================
	@GetMapping("/case/{caseId}/number/{num}")
	public ResponseEntity<?> findByCaseAndNumber(@PathVariable String caseId, @PathVariable int num) {
		try {
			return ResponseEntity.ok(hearingService.findByCaseIdAndHearingNumber(caseId, num));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= LESS THAN EQUAL =================
	@GetMapping("/case/{caseId}/upto/{num}")
	public ResponseEntity<?> findUpTo(@PathVariable String caseId, @PathVariable int num) {
		try {
			return ResponseEntity.ok(hearingService.findByCaseIdAndHearingNumberLessThanEqual(caseId, num));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= AFTER DATE =================
	@GetMapping("/after")
	public ResponseEntity<?> after(@RequestParam String date) {
		try {
			return ResponseEntity.ok(hearingService.findByIssuedDateAfter(Instant.parse(date)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BEFORE DATE =================
	@GetMapping("/before")
	public ResponseEntity<?> before(@RequestParam String date) {
		try {
			return ResponseEntity.ok(hearingService.findByIssuedDateBefore(Instant.parse(date)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> all() {
		try {
			return ResponseEntity.ok(hearingService.seeAll());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= BY ID =================
	@GetMapping("/{id}")
	public ResponseEntity<?> byId(@PathVariable String id) {
		try {
			return ResponseEntity.ok(hearingService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= DELETE =================
	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			return ResponseEntity.ok(hearingService.removeHearing(id, userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ================= ERROR HANDLER =================
	private ResponseEntity<?> error(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		map.put("error", e.getClass().getSimpleName());
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
}
