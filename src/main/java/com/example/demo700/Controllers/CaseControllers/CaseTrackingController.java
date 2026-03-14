package com.example.demo700.Controllers.CaseControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.CaseModels.CaseTracking;
import com.example.demo700.Services.CaseServices.CaseTrackingService;

@RestController
@RequestMapping("/api/caseTracking")
public class CaseTrackingController {

	@Autowired
	private CaseTrackingService caseTrackingService;

	// ================= ADD CASE TRACKING =================
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addCaseTracking(@RequestBody CaseTracking caseTracking, @PathVariable String userId) {

		try {

			CaseTracking tracking = caseTrackingService.addCaseTracking(caseTracking, userId);
			return ResponseEntity.status(HttpStatus.CREATED).body(tracking);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}

	// ================= UPDATE CASE TRACKING =================
	@PutMapping("/update/{id}/{userId}")
	public ResponseEntity<?> updateCaseTracking(@RequestBody CaseTracking caseTracking, @PathVariable String userId,
			@PathVariable String id) {

		try {

			CaseTracking tracking = caseTrackingService.updateCaseTracking(caseTracking, userId, id);
			return ResponseEntity.ok(tracking);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}

	// ================= FIND ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {

		try {

			List<CaseTracking> list = caseTrackingService.findAll();
			return ResponseEntity.ok(list);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	// ================= FIND BY CASE ID =================
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCaseId(@PathVariable String caseId) {

		try {

			List<CaseTracking> list = caseTrackingService.findByCaseId(caseId);
			return ResponseEntity.ok(list);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	// ================= FIND BY CASE STAGE =================
	@GetMapping("/stage/{caseStage}")
	public ResponseEntity<?> findByCaseStage(@PathVariable CasePayment caseStage) {

		try {

			List<CaseTracking> list = caseTrackingService.findByCaseStage(caseStage);
			return ResponseEntity.ok(list);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	// ================= FIND BY CASE ID AND STAGE NUMBER =================
	@GetMapping("/case/{caseId}/stageNumber/{stageNumber}")
	public ResponseEntity<?> findByCaseIdAndStageNumber(@PathVariable String caseId, @PathVariable int stageNumber) {

		try {

			CaseTracking tracking = caseTrackingService.findByCaseIdAndStageNumber(caseId, stageNumber);

			return ResponseEntity.ok(tracking);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	// ================= FIND BY CASE ID AND CASE STAGE =================
	@GetMapping("/case/{caseId}/stage/{caseStage}")
	public ResponseEntity<?> findByCaseIdAndCaseStage(@PathVariable String caseId,
			@PathVariable CasePayment caseStage) {

		try {

			CaseTracking tracking = caseTrackingService.findByCaseIdAndCaseStage(caseId, caseStage);

			return ResponseEntity.ok(tracking);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	// ================= FIND BY CASE ID AND STAGE NUMBER GREATER THAN ===========
	@GetMapping("/remainingLargerStage")
	public ResponseEntity<?> findByCaseIdAndStageNumberGreaterThan(@RequestParam String caseId,
			@RequestParam int stageNumber) {

		try {

			List<CaseTracking> list = caseTrackingService.findByCaseIdAndStageNumberGreaterThan(caseId, stageNumber);

			if (list.isEmpty()) {

				throw new Exception("There is no such case tracking exist at here....");

			}

			return ResponseEntity.status(200).body(list);

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// ================= DELETE CASE TRACKING =================
	@DeleteMapping("/delete/{id}/{userId}")
	public ResponseEntity<?> removeCaseTracking(@PathVariable String id, @PathVariable String userId) {

		try {

			boolean removed = caseTrackingService.removeCaseTracking(id, userId);

			if (removed) {

				return ResponseEntity.ok("Case tracking removed successfully.");

			} else {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Case tracking not removed.");

			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}
}