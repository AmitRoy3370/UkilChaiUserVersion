package com.example.demo700.Controllers.AdminsController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;
import com.example.demo700.Services.AdminServices.AdminJoinRequestService;

@RestController
@RequestMapping("/api/adminJoinRequest")
public class AdminJoinRequestController {

	@Autowired
	private AdminJoinRequestService adminJoinRequestService;

	// ---------------- ADD REQUEST ----------------
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addJoinRequest(@PathVariable String userId, @RequestBody AdminJoinRequest admin) {
		try {
			AdminJoinRequest saved = adminJoinRequestService.addAdmin(admin, userId);
			return ResponseEntity.ok(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ---------------- VIEW ALL ----------------
	@GetMapping("/all")
	public ResponseEntity<?> seeAll() {
		try {
			return ResponseEntity.ok(adminJoinRequestService.seeAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ---------------- FIND BY USER ID ----------------
	@GetMapping("/findByUser/{userId}")
	public ResponseEntity<?> findByUserId(@PathVariable String userId) {
		try {
			AdminJoinRequest admin = adminJoinRequestService.findByUserId(userId);
			return ResponseEntity.ok(admin);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ---------------- SEARCH BY SPECIALITY ----------------
	@GetMapping("/search/{speciality}")
	public ResponseEntity<?> searchBySpeciality(@PathVariable String speciality) {
		try {
			List<AdminJoinRequest> list = adminJoinRequestService
					.findByAdvocateSpecialityContainingIgnoreCase(speciality);
			return ResponseEntity.ok(list);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ---------------- UPDATE ----------------
	@PutMapping("/update/{adminId}/{userId}")
	public ResponseEntity<?> updateJoinRequest(@PathVariable String adminId, @PathVariable String userId,
			@RequestBody AdminJoinRequest admin) {
		try {
			AdminJoinRequest updated = adminJoinRequestService.updateAdmin(admin, userId, adminId);
			return ResponseEntity.ok(updated);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ---------------- DELETE ----------------
	@DeleteMapping("/delete/{adminId}/{userId}")
	public ResponseEntity<?> deleteJoinRequest(@PathVariable String adminId, @PathVariable String userId) {
		try {
			boolean result = adminJoinRequestService.deleteAdmin(adminId, userId);
			return ResponseEntity.ok(result ? "Delete successful" : "Delete failed");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ---------------- HANDLE REQUEST (ACCEPT/REJECT) ----------------
	@PostMapping("/handle/{adminJoinRequestId}/{userId}/{response}")
	public ResponseEntity<?> handleJoinRequest(@PathVariable String adminJoinRequestId, @PathVariable String userId,
			@PathVariable String response) {
		try {
			Admin admin = adminJoinRequestService.handleAdminJoinRequest(userId, adminJoinRequestId, response);

			return ResponseEntity.ok(admin);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
