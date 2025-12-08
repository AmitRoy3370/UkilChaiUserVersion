package com.example.demo700.Controllers.AdminsController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Services.AdminServices.CenterAdminService;

@RestController
@RequestMapping("/api/center-admin")
@CrossOrigin("*")
public class CenterAdminController {

	@Autowired
	private CenterAdminService centerAdminService;

	// ✅ ADD CENTER ADMIN
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addCenterAdmin(@RequestBody CenterAdmin centerAdmin, @PathVariable String userId) {

		try {
			CenterAdmin saved = centerAdminService.addCenterAdmin(centerAdmin, userId);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ✅ SEE ALL CENTER ADMINS
	@GetMapping("/all")
	public ResponseEntity<?> getAllCenterAdmins() {
		try {
			List<CenterAdmin> list = centerAdminService.seeAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ✅ FIND BY USER ID
	@GetMapping("/by-user/{userId}")
	public ResponseEntity<?> findByUserId(@PathVariable String userId) {
		try {
			CenterAdmin centerAdmin = centerAdminService.findByUserId(userId);
			return new ResponseEntity<>(centerAdmin, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ✅ FIND BY ADMIN ID (FROM LIST)
	@GetMapping("/by-admin/{adminId}")
	public ResponseEntity<?> findByAdmin(@PathVariable String adminId) {
		try {
			CenterAdmin centerAdmin = centerAdminService.findByAdminsContainingIgnoreCase(adminId);
			return new ResponseEntity<>(centerAdmin, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ✅ FIND BY DISTRICT
	@GetMapping("/by-district/{district}")
	public ResponseEntity<?> findByDistrict(@PathVariable String district) {
		try {
			List<CenterAdmin> list = centerAdminService.findByDistrictsContainingIgnoreCase(district);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ✅ FIND BY ADVOCATE ID
	@GetMapping("/by-advocate/{advocateId}")
	public ResponseEntity<?> findByAdvocate(@PathVariable String advocateId) {
		try {
			CenterAdmin centerAdmin = centerAdminService.findByAdvocatesContainingIgnoreCase(advocateId);
			return new ResponseEntity<>(centerAdmin, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ✅ UPDATE CENTER ADMIN
	@PutMapping("/update/{centerAdminId}/{userId}")
	public ResponseEntity<?> updateCenterAdmin(@RequestBody CenterAdmin centerAdmin, @PathVariable String centerAdminId,
			@PathVariable String userId) {

		try {
			CenterAdmin updated = centerAdminService.updateCenterAdmin(centerAdmin, userId, centerAdminId);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ✅ REMOVE CENTER ADMIN
	@DeleteMapping("/delete/{centerAdminId}/{userId}")
	public ResponseEntity<?> deleteCenterAdmin(@PathVariable String centerAdminId, @PathVariable String userId) {

		try {
			boolean deleted = centerAdminService.removeCentralAdmin(centerAdminId, userId);

			if (deleted) {
				return new ResponseEntity<>("Center Admin deleted successfully.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Center Admin deletion failed.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
