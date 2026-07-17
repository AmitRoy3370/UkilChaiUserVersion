package com.example.demo700.Controllers.UserControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.ENums.Gender;
import com.example.demo700.Model.UserModels.UserGender;
import com.example.demo700.Services.UserServices.UserGenderService;

@RestController
@RequestMapping("/api/user-gender")
public class UserGenderController {

    @Autowired
    private UserGenderService userGenderService;

    // ============ CREATE ============
    @PostMapping("/create")
    public ResponseEntity<?> addUserGender(@RequestBody UserGender userGender, 
                                          @RequestParam String userId) {
        try {
            UserGender created = userGenderService.addUserGender(userGender, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ UPDATE ============
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserGender(@RequestBody UserGender userGender,
                                             @RequestParam String userId,
                                             @PathVariable String id) {
        try {
            UserGender updated = userGenderService.updateUserGender(userGender, userId, id);
            return ResponseEntity.ok(updated);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ FIND BY ID ============
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            UserGender userGender = userGenderService.findById(id);
            return ResponseEntity.ok(userGender);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ FIND ALL ============
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        try {
            List<UserGender> userGenders = userGenderService.findAll();
            return ResponseEntity.ok(userGenders);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ FIND BY USER ID ============
    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId) {
        try {
            UserGender userGender = userGenderService.findByUserId(userId);
            return ResponseEntity.ok(userGender);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ FIND BY GENDER ============
    @GetMapping("/find-by-gender")
    public ResponseEntity<?> findByGender(@RequestParam String gender) {
        try {
            List<UserGender> userGenders = userGenderService.findByGender(Gender.valueOf(gender));
            return ResponseEntity.ok(userGenders);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ FIND BY USER IDS ============
    @PostMapping("/find-by-user-ids")
    public ResponseEntity<?> findByUserIdIn(@RequestBody List<String> usersId) {
        try {
            List<UserGender> userGenders = userGenderService.findByUserIdIn(usersId);
            return ResponseEntity.ok(userGenders);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ DELETE ============
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeUserGender(@PathVariable String id, 
                                             @RequestParam String userId) {
        try {
            boolean deleted = userGenderService.removeUserGender(id, userId);
            if (deleted) {
                return ResponseEntity.ok("User gender deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                        .body("Error: Failed to delete user gender");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // ============ ADDITIONAL UTILITY ENDPOINTS ============
    
    // Alternative find by user ID using RequestParam
    @GetMapping("/find-by-user-param")
    public ResponseEntity<?> findByUserIdParam(@RequestParam String userId) {
        try {
            UserGender userGender = userGenderService.findByUserId(userId);
            return ResponseEntity.ok(userGender);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }

    // Check if user has gender set
    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkUserGenderExists(@PathVariable String userId) {
        try {
            UserGender userGender = userGenderService.findByUserId(userId);
            return ResponseEntity.ok(userGender != null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
}