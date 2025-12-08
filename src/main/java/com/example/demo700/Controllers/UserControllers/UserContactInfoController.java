package com.example.demo700.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Services.UserServices.UserContactInfoService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user/contact-info")
public class UserContactInfoController {

    @Autowired
    private UserContactInfoService userContactInfoService;

    // -----------------------------
    // CREATE (POST)
    // -----------------------------
    @PostMapping("/add")
    public ResponseEntity<?> addContactInfo(
            @RequestBody UserContactInfo userContactInfo,
            @RequestParam String userId
    ) {
        try {
            UserContactInfo result = userContactInfoService.addUserContactInfo(userContactInfo, userId);
            return ResponseEntity.ok(result);
        } catch (NullPointerException | ArithmeticException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // READ ALL (GET)
    // -----------------------------
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<UserContactInfo> list = userContactInfoService.seeAllUserContactInfo();
            return ResponseEntity.ok(list);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // SEARCH BY EMAIL (GET)
    // -----------------------------
    @GetMapping("/email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(userContactInfoService.searchByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // SEARCH BY PHONE (GET)
    // -----------------------------
    @GetMapping("/phone")
    public ResponseEntity<?> getByPhone(@RequestParam String phone) {
        try {
            return ResponseEntity.ok(userContactInfoService.searchByPhone(phone));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // SEARCH BY USER ID (GET)
    // -----------------------------
    @GetMapping("/user")
    public ResponseEntity<?> getByUserId(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(userContactInfoService.searchByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // UPDATE (PUT)
    // -----------------------------
    @PutMapping("/update")
    public ResponseEntity<?> updateUserContactInfo(
            @RequestParam String contactInfoId,
            @RequestParam String userId,
            @RequestBody UserContactInfo userContactInfo
    ) {
        try {
            UserContactInfo updated = userContactInfoService.updateUserContactInfo(contactInfoId, userId, userContactInfo);
            return ResponseEntity.ok(updated);

        } catch (NullPointerException | ArithmeticException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // -----------------------------
    // DELETE (DELETE)
    // -----------------------------
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserContactInfo(
            @RequestParam String contactInfoId,
            @RequestParam String userId
    ) {
        try {
            boolean deleted = userContactInfoService.deleteUserContactInfo(contactInfoId, userId);
            return ResponseEntity.ok(deleted);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}