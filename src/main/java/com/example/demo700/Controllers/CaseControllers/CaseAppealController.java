package com.example.demo700.Controllers.CaseControllers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.CaseModels.AppealCase;
import com.example.demo700.Services.CaseServices.CaseAppealService;

@RestController
@RequestMapping("/appealCase")
public class CaseAppealController {

	@Autowired
	private CaseAppealService appealService;

	// ------------------ ADD ------------------
	@PostMapping(value = "/add/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addAppeal(@PathVariable String userId, @RequestPart("caseId") String caseId,
			@RequestPart("reason") String reason) {

		try {
			AppealCase appeal = new AppealCase();
			appeal.setCaseId(caseId);
			appeal.setReason(reason);
			appeal.setAppealDate(Instant.now());

			return ResponseEntity.ok(appealService.addAppealCase(appeal, userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ UPDATE ------------------
	@PutMapping(value="/update/{appealId}/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateAppeal(@PathVariable String appealId, @PathVariable String userId,
			@RequestPart("caseId") String caseId, @RequestPart("reason") String reason) {

		try {
			AppealCase appeal = new AppealCase();
			appeal.setCaseId(caseId);
			appeal.setReason(reason);

			return ResponseEntity.ok(appealService.updateAppealCase(appeal, userId, appealId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ FIND BY CASE ID ------------------
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCaseId(@PathVariable String caseId) {
		try {
			return ResponseEntity.ok(appealService.findByCaseId(caseId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ SEARCH BY REASON ------------------
	@GetMapping("/search")
	public ResponseEntity<?> searchByReason(@RequestParam String reason) {
		try {
			return ResponseEntity.ok(appealService.findByReasonContaingingIgnoreCase(reason));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ DATE AFTER ------------------
	@GetMapping("/afterDate")
	public ResponseEntity<?> findAfter(@RequestParam String date) {
		try {
			return ResponseEntity.ok(appealService.findByAppealDateAfter(Instant.parse(date)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ DATE BEFORE ------------------
	@GetMapping("/beforeDate")
	public ResponseEntity<?> findBefore(@RequestParam String date) {
		try {
			return ResponseEntity.ok(appealService.findByAppealDateBefore(Instant.parse(date)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ GET ALL ------------------
	@GetMapping("/showAll")
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.ok(appealService.seeAll());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ GET BY ID ------------------
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(appealService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ DELETE ------------------
	@DeleteMapping("delete/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			return ResponseEntity.ok(appealService.removeAppealCase(id, userId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ------------------ ERROR HANDLER ------------------
	private ResponseEntity<?> error(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		map.put("error", e.getClass().getSimpleName());
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
}
