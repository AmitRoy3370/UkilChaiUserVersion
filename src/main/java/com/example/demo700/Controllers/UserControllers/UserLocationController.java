package com.example.demo700.Controllers.UserControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Services.UserServices.UserlocationService;

@RestController
@RequestMapping("/api/userLocation")
public class UserLocationController {

	@Autowired
	private UserlocationService userlocationService;

	// ------------------------------------------------------------
	// 1️⃣ Add User Location
	// ------------------------------------------------------------
	@PostMapping("/add")
	public ResponseEntity<?> addUserLocation(@RequestBody UserLocation userLocation) {

		try {

			return ResponseEntity.status(200).body(userlocationService.addUserLocation(userLocation));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 2️⃣ Get All User Locations
	// ------------------------------------------------------------
	@GetMapping("/all")
	public ResponseEntity<?> seeAll() {

		try {

			return ResponseEntity.status(200).body(userlocationService.seeAllUserLocation());

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 3️⃣ Update User Location
	// ------------------------------------------------------------
	@PutMapping("/update/{userLocationId}")
	public ResponseEntity<?> updateUserLocation(@RequestBody UserLocation userLocation,
			@RequestParam("userId") String userId, @PathVariable("userLocationId") String userLocationId) {

		try {

			return ResponseEntity.status(200)
					.body(userlocationService.updateUserLocation(userLocation, userId, userLocationId));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 4️⃣ Delete User Location
	// ------------------------------------------------------------
	@DeleteMapping("/delete/{userLocationId}")
	public ResponseEntity<?> deleteUserLocation(@PathVariable("userLocationId") String userLocationId,
			@RequestParam("userId") String userId) {

		try {

			return ResponseEntity.status(200).body(userlocationService.deleteUserLocation(userLocationId, userId));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 5️⃣ Find User Location by User ID
	// ------------------------------------------------------------
	@GetMapping("/findByUserId/{userId}")
	public ResponseEntity<?> findByUserId(@PathVariable("userId") String userId) {

		try {

			return ResponseEntity.status(200).body(userlocationService.findByUserId(userId));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 6️⃣ Find User Locations by Location Name
	// ------------------------------------------------------------
	@GetMapping("/findByLocationName/{locationName}")
	public ResponseEntity<?> findByLocationName(@PathVariable("locationName") String locationName) {

		try {

			return ResponseEntity.status(200).body(userlocationService.findByLocationName(locationName));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// ------------------------------------------------------------
	// 7️⃣ Find User Locations by Latitude and Longitude
	// ------------------------------------------------------------
	@GetMapping("/findByLatLong")
	public ResponseEntity<?> findByLatLong(@RequestParam("lattitude") double lattitude,
			@RequestParam("longitude") double longitude) {

		try {

			return ResponseEntity.status(200)
					.body(userlocationService.findByLattitudeAndLongitude(lattitude, longitude));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}
}
