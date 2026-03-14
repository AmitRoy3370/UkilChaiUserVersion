package com.example.demo700.Controllers.CaseControllers;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.CaseModels.ReadStatus;
import com.example.demo700.Services.CaseServices.ReadStatusService;

@RestController
@RequestMapping("/api/read-status")
public class ReadStatusController {

	@Autowired
	private ReadStatusService readStatusService;

	// ADD STATUS
	@PostMapping("/{userId}")
	public ResponseEntity<?> addReadStatus(@RequestBody ReadStatus readStatus, @PathVariable String userId) {

		try {

			ReadStatus status = readStatusService.addReadStatus(readStatus, userId);
			return ResponseEntity.ok(status);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// UPDATE STATUS
	@PutMapping("/{id}/{userId}")
	public ResponseEntity<?> updateReadStatus(@RequestBody ReadStatus readStatus, @PathVariable String userId,
			@PathVariable String id) {

		try {

			ReadStatus status = readStatusService.updateReadStatus(readStatus, userId, id);
			return ResponseEntity.ok(status);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {

		try {

			List<ReadStatus> list = readStatusService.findAll();

			if (list.isEmpty()) {

				throw new NullPointerException("No such status submitted till now...");

			}

			return ResponseEntity.status(200).body(list);

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// FIND BY CASE ID
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCaseId(@PathVariable String caseId) {

		try {

			List<ReadStatus> list = readStatusService.findByCaseId(caseId);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// FIND BY ADVOCATE ID
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {

		try {

			List<ReadStatus> list = readStatusService.findByAdvocateId(advocateId);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// FIND BY STATUS
	@GetMapping("/status")
	public ResponseEntity<?> findByStatus(@RequestParam String status) {

		try {

			List<ReadStatus> list = readStatusService.findByStatusContainingIgnoreCase(status);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// FIND BY ISSUED TIME BEFORE
	@GetMapping("/issued-before")
	public ResponseEntity<?> findByIssuedTimeBefore(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant issuedTime) {

		try {

			List<ReadStatus> list = readStatusService.findByIssuedTimeBefore(issuedTime);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// FIND BY ISSUED TIME AFTER
	@GetMapping("/issued-after")
	public ResponseEntity<?> findByIssuedTimeAfter(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant issuedTime) {

		try {

			List<ReadStatus> list = readStatusService.findByIssuedTimeAfter(issuedTime);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// FIND BY ISSUED TIME EXACT
	@GetMapping("/issued")
	public ResponseEntity<?> findByIssuedTime(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant issuedTime) {

		try {

			List<ReadStatus> list = readStatusService.findByIssuedTime(issuedTime);
			return ResponseEntity.ok(list);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

	// DELETE STATUS
	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<?> deleteReadStatus(@PathVariable String id, @PathVariable String userId) {

		try {

			boolean removed = readStatusService.deleteReadStatus(id, userId);
			return ResponseEntity.ok(removed);

		} catch (NullPointerException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (NoSuchElementException e) {

			return ResponseEntity.status(404).body(e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(500).body(e.getMessage());

		}

	}

}
