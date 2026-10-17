package com.example.demo700.Controllers.AdvocateControllers;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;
import com.example.demo700.Services.AdvocateServices.AdvocateAdditionalInfoService;

@RestController
@RequestMapping("/api/advocate-additional-info")
public class AdvocateAdditionalInfoController {

	@Autowired
	private AdvocateAdditionalInfoService additionalInfoService;

	// ==================== ADD ====================
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addAdditional(@RequestBody AdvocateAdditionalInfo info,
			@PathVariable String userId) {
		try {
			AdvocateAdditionalInfo saved = additionalInfoService.addAdditional(info, userId);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== UPDATE ====================
	@PutMapping("/update/{id}/{userId}")
	public ResponseEntity<?> updateAdditional(@RequestBody AdvocateAdditionalInfo info,
			@PathVariable String userId, @PathVariable String id) {
		try {
			AdvocateAdditionalInfo updated = additionalInfoService.updateAdditional(info, userId, id);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(additionalInfoService.findById(id));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND ALL ====================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(additionalInfoService.findAll());
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY ADVOCATE ID ====================
	@GetMapping("/advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
		try {
			return ResponseEntity.ok(additionalInfoService.findByAdvocateId(advocateId));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY PERMANENT ADDRESS (contains, ignore case) ====================
	@GetMapping("/address/{parmanentAdress}")
	public ResponseEntity<?> findByParmanentAdress(@PathVariable String parmanentAdress) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByParmanentAdressContainingIgnoreCase(parmanentAdress));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY CITY OF UPOZILA (contains, ignore case) ====================
	@GetMapping("/city/{cityOfUpozila}")
	public ResponseEntity<?> findByCityOfUpozila(@PathVariable String cityOfUpozila) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByCityOfUpozilaContainingIgnoreCase(cityOfUpozila));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY NID (contains, ignore case) ====================
	@GetMapping("/nid/search/{nidNumber}")
	public ResponseEntity<?> findByNidContaining(@PathVariable String nidNumber) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByNidNumberContainingIgnoreCase(nidNumber));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY NID (exact) ====================
	@GetMapping("/nid/{nidNumber}")
	public ResponseEntity<?> findByNid(@PathVariable String nidNumber) {
		try {
			return ResponseEntity.ok(additionalInfoService.findByNidNumber(nidNumber));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY DATE OF BIRTH AFTER ====================
	@GetMapping("/dob/after")
	public ResponseEntity<?> findByDateOfBirthAfter(@RequestParam String dateOfBirth) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByDateOfBirthAfter(Instant.parse(dateOfBirth)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY DATE OF BIRTH BEFORE ====================
	@GetMapping("/dob/before")
	public ResponseEntity<?> findByDateOfBirthBefore(@RequestParam String dateOfBirth) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByDateOfBirthBefore(Instant.parse(dateOfBirth)));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== FIND BY WHATSAPP NUMBER ====================
	@GetMapping("/whatsapp/{whatsAppNumber}")
	public ResponseEntity<?> findByWhatsAppNumber(@PathVariable String whatsAppNumber) {
		try {
			return ResponseEntity.ok(
					additionalInfoService.findByWhatsAppNumber(whatsAppNumber));
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================== DELETE ====================
	@DeleteMapping("/delete/{id}/{userId}")
	public ResponseEntity<?> delete(@PathVariable String id, @PathVariable String userId) {
		try {
			boolean removed = additionalInfoService.deleteAdditionalInfo(id, userId);
			if (!removed) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Additional info not removed");
			}
			return ResponseEntity.ok("Additional info removed successfully");
		} catch (Exception e) {
			return error(e);
		}
	}

	// ==================================================================
	// ==================== CENTRAL ERROR HANDLER =======================
	// ==================================================================
	private ResponseEntity<?> error(Exception e) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		if (e instanceof NullPointerException) {
			status = HttpStatus.BAD_REQUEST;        // 400
		} else if (e instanceof NoSuchElementException) {
			status = HttpStatus.NOT_FOUND;          // 404
		} else if (e instanceof ArithmeticException) {
			status = HttpStatus.CONFLICT;           // 409 (duplicate/invalid data)
		} else if (e instanceof IllegalArgumentException) {
			status = HttpStatus.UNPROCESSABLE_ENTITY; // 422 (bad input)
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
		}

		return ResponseEntity.status(status).body(e.getMessage());
	}
}