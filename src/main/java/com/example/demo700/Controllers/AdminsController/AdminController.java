package com.example.demo700.Controllers.AdminsController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Services.AdminServices.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ ADD ADMIN
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin, @PathVariable String userId) {
        try {
            Admin savedAdmin = adminService.addAdmin(admin, userId);
            return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ SEE ALL ADMINS
    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<Admin> list = adminService.seeAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // ✅ FIND ADMIN BY USER ID
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId) {
        try {
            Admin admin = adminService.findByUserId(userId);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // ✅ FIND ADMIN BY SPECIALITY (IGNORE CASE)
    @GetMapping("/by-speciality/{speciality}")
    public ResponseEntity<?> findBySpeciality(@PathVariable String speciality) {
        try {
            List<Admin> list = adminService.findByAdvocateSpecialityContainingIgnoreCase(speciality);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // ✅ UPDATE ADMIN
    @PutMapping("/update/{adminId}/{userId}")
    public ResponseEntity<?> updateAdmin(
            @RequestBody Admin admin,
            @PathVariable String adminId,
            @PathVariable String userId) {

        try {
            Admin updatedAdmin = adminService.updateAdmin(admin, userId, adminId);
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ DELETE ADMIN
    @DeleteMapping("/delete/{adminId}/{userId}")
    public ResponseEntity<?> deleteAdmin(
            @PathVariable String adminId,
            @PathVariable String userId) {

        try {
            boolean result = adminService.deleteAdmin(adminId, userId);

            if (result) {
                return new ResponseEntity<>("Admin deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin deletion failed.", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}