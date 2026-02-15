package com.example.demo700.Controllers.UserActiveControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserActiveModel.UserActive;
import com.example.demo700.Services.UserActiveServices.UserActiveService;

@RestController
@RequestMapping("/api/user-active")
public class UserActiveController {

    @Autowired
    private UserActiveService userActiveService;

    // ================= ADD =================
    @PostMapping("/add")
    public ResponseEntity<?> addUserActive(@RequestBody UserActive userActive) {
        try {
            UserActive saved = userActiveService.addUserActive(userActive);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= UPDATE =================
    @PutMapping("/update/{id}/{userId}")
    public ResponseEntity<?> updateUserActive(
            @RequestBody UserActive userActive,
            @PathVariable String userId,
            @PathVariable String id) {

        try {
            UserActive updated =
                    userActiveService.updateUserActive(userActive, userId, id);

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FIND BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userActiveService.findById(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND ALL =================
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            List<UserActive> list = userActiveService.findAll();
            return ResponseEntity.ok(list);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND BY USER ID =================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(
                    userActiveService.findByUserId(userId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FILTER BY ACTIVE (REQUEST PARAM) =================
    @GetMapping("/status")
    public ResponseEntity<?> findByActive(@RequestParam boolean active) {
        try {
            return ResponseEntity.ok(
                    userActiveService.findByActive(active));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> deleteUserActive(
            @PathVariable String id,
            @PathVariable String userId) {

        try {
            boolean deleted =
                    userActiveService.removeUserActive(id, userId);

            return ResponseEntity.ok(deleted);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

