package com.example.demo700.Controllers.AdvocateControllers;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;
import com.example.demo700.Services.AdvocateServices.AdvocateWorkingExperienceService;

@RestController
@RequestMapping("/api/advocate-working-experience")
public class AdvocateWorkingExperienceController {

	@Autowired
	private AdvocateWorkingExperienceService workingService;

	// ==================== ADD ====================
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> add(@RequestBody AdvocateWorkingExperience experience,
			@PathVariable String userId) {
		try {
			AdvocateWorkingExperience saved = workingService.addWorkingExperience(experience, userId);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== UPDATE ====================
	@PutMapping("/update/{id}/{userId}")
	public ResponseEntity<?> update(@RequestBody AdvocateWorkingExperience experience,
			@PathVariable String userId, @PathVariable String id) {
		try {
			AdvocateWorkingExperience updated = workingService.updateWorkingExperience(experience, userId, id);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(workingService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(workingService.findAll());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ADVOCATE ID ====================
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(workingService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY BAR ASSOCIATION ====================
	@GetMapping("/bar-association/{barAssociation}")
	public ResponseEntity<?> findByBarAssociation(@PathVariable String barAssociation) {
		try {
			return ResponseEntity.ok(
					workingService.findByBarAssociationContainingIgnoreCase(barAssociation));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY BAR CONTROL ENROLMENT ====================
	@GetMapping("/bar-control/{barControllEnrolment}")
	public ResponseEntity<?> findByBarControl(@PathVariable String barControllEnrolment) {
		try {
			return ResponseEntity.ok(
					workingService.findByBarControllEnrolmentContainingIgnoreCase(barControllEnrolment));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ENROLLMENT DATE AFTER ====================
	@GetMapping("/enrollment/after")
	public ResponseEntity<?> findByEnrollmentDateAfter(@RequestParam String enrollmentDate) {
		try {
			return ResponseEntity.ok(
					workingService.findByEnrollmentDateAfter(Instant.parse(enrollmentDate)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ENROLLMENT DATE BEFORE ====================
	@GetMapping("/enrollment/before")
	public ResponseEntity<?> findByEnrollmentDateBefore(@RequestParam String enrollmentDate) {
		try {
			return ResponseEntity.ok(
					workingService.findByEnrollmentDateBefore(Instant.parse(enrollmentDate)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY PRACTICING COAT ====================
	@GetMapping("/practicing-coat/{practicingCoat}")
	public ResponseEntity<?> findByPracticingCoat(@PathVariable String practicingCoat) {
		try {
			return ResponseEntity.ok(
					workingService.findByPracticingCoatContainingIgnoreCase(practicingCoat));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY PRIMARY PRACTICE ====================
	@GetMapping("/primary-practice/{primaryPractice}")
	public ResponseEntity<?> findByPrimaryPractice(@PathVariable String primaryPractice) {
		try {
			return ResponseEntity.ok(
					workingService.findByPrimaryPracticeContainingIgnoreCase(primaryPractice));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY YEAR OF LEGAL PRACTICE >= ====================
	@GetMapping("/legal-practice/gte")
	public ResponseEntity<?> findByLegalPracticeGte(@RequestParam int yearOfLegalPractice) {
		try {
			return ResponseEntity.ok(
					workingService.findByYearOfLegalPracticeGreaterThanEqual(yearOfLegalPractice));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY YEAR OF LEGAL PRACTICE <= ====================
	@GetMapping("/legal-practice/lte")
	public ResponseEntity<?> findByLegalPracticeLte(@RequestParam int yearOfLegalPractice) {
		try {
			return ResponseEntity.ok(
					workingService.findByYearOfLegalPracticeLessThanEqual(yearOfLegalPractice));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY CURRENT CHAMBER NAME ====================
	@GetMapping("/chamber/{currentChemberName}")
	public ResponseEntity<?> findByCurrentChamber(@PathVariable String currentChemberName) {
		try {
			return ResponseEntity.ok(
					workingService.findByCurrentChemberNameContainingIgnoreCase(currentChemberName));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY PROFESSIONAL EXPERIENCE >= ====================
	@GetMapping("/professional-experience/gte")
	public ResponseEntity<?> findByProfessionalExperienceGte(@RequestParam int professionalExperience) {
		try {
			return ResponseEntity.ok(
					workingService.findByProfessionalExperienceGreaterThanEqual(professionalExperience));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY PROFESSIONAL EXPERIENCE <= ====================
	@GetMapping("/professional-experience/lte")
	public ResponseEntity<?> findByProfessionalExperienceLte(@RequestParam int professionalExperience) {
		try {
			return ResponseEntity.ok(
					workingService.findByProfessionalExperienceLessThanEqual(professionalExperience));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			boolean removed = workingService.deleteAdvocateWorkingExperience(id, userId);
			if (!removed) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Advocate working experience not removed");
			}
			return ResponseEntity.ok("Advocate working experience removed successfully");
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================================================================
	// ==================== CENTRAL ERROR HANDLER =======================
	// ==================================================================
	private ResponseEntity<?> error(Exception e) {

		HttpStatus status;

		if (e instanceof NullPointerException) {
			status = HttpStatus.BAD_REQUEST;             // 400 — bad / missing input
		} else if (e instanceof NoSuchElementException) {
			status = HttpStatus.NOT_FOUND;               // 404 — no such document/user/advocate
		} else if (e instanceof ArithmeticException) {
			status = HttpStatus.CONFLICT;                // 409 — duplicate / business rule violation
		} else if (e instanceof IllegalArgumentException) {
			status = HttpStatus.UNPROCESSABLE_ENTITY;    // 422 — invalid argument
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;   // 500 — unexpected
		}

		return ResponseEntity.status(status).body(e.getMessage());
	}
}