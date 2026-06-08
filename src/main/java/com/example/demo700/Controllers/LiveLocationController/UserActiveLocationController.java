package com.example.demo700.Controllers.LiveLocationController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.DTOFiles.UserLiveLocationDataResponse;
import com.example.demo700.Model.LiveLocations.LiveLocationData;
import com.example.demo700.Services.UserActiveLocationServices.UserActiveLocationService;

@RestController
@RequestMapping("/api/user-active-location")
public class UserActiveLocationController {

    @Autowired
    private UserActiveLocationService userActiveLocationService;

    // ================= ADD LOCATION =================
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addLocation(
            @RequestBody LiveLocationData liveLocation,
            @PathVariable String userId) {
        try {
            LiveLocationData saved = userActiveLocationService.addLocation(liveLocation, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================= UPDATE LOCATION =================
    @PutMapping("/update/{userId}/{id}")
    public ResponseEntity<?> updateLocation(
            @RequestBody LiveLocationData liveLocation,
            @PathVariable String userId,
            @PathVariable String id) {
        try {
            LiveLocationData updated = userActiveLocationService.updateLocation(liveLocation, userId, id);
            return ResponseEntity.ok(updated);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================= SEE ALL LOCATIONS =================
    @GetMapping("/all")
    public ResponseEntity<?> seeAll() {
        try {
            List<UserLiveLocationDataResponse> locations = userActiveLocationService.seeAll();
            return ResponseEntity.ok(locations);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            UserLiveLocationDataResponse location = userActiveLocationService.getById(id);
            return ResponseEntity.ok(location);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= FIND BY ADVOCATE ID =================
    @GetMapping("/advocate/{advocateId}")
    public ResponseEntity<?> findByAdvocateId(@PathVariable String advocateId) {
        try {
            UserLiveLocationDataResponse location = userActiveLocationService.findByAdvocateId(advocateId);
            return ResponseEntity.ok(location);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= FIND BY USER ID =================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId) {
        try {
            UserLiveLocationDataResponse location = userActiveLocationService.findByUserId(userId);
            return ResponseEntity.ok(location);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= FIND BY LOCATION NAME (CONTAINING IGNORE CASE) =================
    @GetMapping("/location-name")
    public ResponseEntity<?> findByLocationNameContainingIgnoreCase(
            @RequestParam String locationName) {
        try {
            List<UserLiveLocationDataResponse> locations = 
                    userActiveLocationService.findByLocationNameContainingIgnoreCase(locationName);
            return ResponseEntity.ok(locations);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= FIND BY ADVOCATE IDS LIST =================
    @PostMapping("/advocates/list")
    public ResponseEntity<?> findByAdvocateIdIn(@RequestBody List<String> advocatesId) {
        try {
            List<UserLiveLocationDataResponse> locations = 
                    userActiveLocationService.findByAdvocateIdIn(advocatesId);
            return ResponseEntity.ok(locations);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= FIND BY USER IDS LIST =================
    @PostMapping("/users/list")
    public ResponseEntity<?> findByUserIdIn(@RequestBody List<String> usersId) {
        try {
            List<UserLiveLocationDataResponse> locations = 
                    userActiveLocationService.findByUserIdIn(usersId);
            return ResponseEntity.ok(locations);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ================= DELETE LIVE LOCATION =================
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> deleteLiveLocation(
            @PathVariable String id,
            @PathVariable String userId) {
        try {
            boolean deleted = userActiveLocationService.deleteLiveLocation(id, userId);
            if (deleted) {
                return ResponseEntity.ok("Location deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location not found or could not be deleted");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
