package com.example.demo700.Controllers.TinControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.example.demo700.Model.TinModels.TinRegistrationProcess;
import com.example.demo700.Services.TinServices.TinRegistrationProcessService;

@RestController
@RequestMapping("/api/tin-registration")
public class TinRegistrationProcessController {

    @Autowired
    private TinRegistrationProcessService processService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<Map<String, Object>> addTinRegistrationProcess(
            @RequestBody TinRegistrationProcess process,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate required fields
            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process data is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Validate process fields
            if (process.getCenterAdminId() == null || process.getCenterAdminId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Center Admin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (process.getTinId() == null || process.getTinId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Tin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            TinRegistrationProcess savedProcess = processService.addTinRegistrationProcess(process, userId);
            
            response.put("status", "success");
            response.put("message", "Tin registration process added successfully");
            response.put("data", savedProcess);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (ArithmeticException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== UPDATE ====================
    @PutMapping(value = "/update/{id}", consumes = {"application/json"})
    public ResponseEntity<Map<String, Object>> updateTinRegistrationProcess(
            @PathVariable("id") String id,
            @RequestBody TinRegistrationProcess process,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Process ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process data is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Validate process fields
            if (process.getCenterAdminId() == null || process.getCenterAdminId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Center Admin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (process.getTinId() == null || process.getTinId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Tin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            TinRegistrationProcess updatedProcess = processService.updateTinRegistrationProcess(process, userId, id);
            
            response.put("status", "success");
            response.put("message", "Tin registration process updated successfully");
            response.put("data", updatedProcess);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (ArithmeticException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY ID ====================
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") String id) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Process ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            TinRegistrationProcess process = processService.findById(id);
            
            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            response.put("status", "success");
            response.put("data", process);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND ALL ====================
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<TinRegistrationProcess> processes = processService.findAll();
            
            response.put("status", "success");
            response.put("message", "Processes retrieved successfully");
            response.put("count", processes.size());
            response.put("data", processes);
            return ResponseEntity.ok(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY CENTER ADMIN ID ====================
    @GetMapping("/search/center-admin")
    public ResponseEntity<Map<String, Object>> findByCenterAdminId(
            @RequestParam("centerAdminId") String centerAdminId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (centerAdminId == null || centerAdminId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Center Admin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            List<TinRegistrationProcess> processes = processService.findByCenterAdminId(centerAdminId.trim());
            
            response.put("status", "success");
            response.put("message", "Processes retrieved successfully");
            response.put("count", processes.size());
            response.put("data", processes);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY ADVOCATE ID ====================
    @GetMapping("/search/advocate")
    public ResponseEntity<Map<String, Object>> findByAdvocateId(
            @RequestParam("advocateId") String advocateId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (advocateId == null || advocateId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            List<TinRegistrationProcess> processes = processService.findByAdvocateId(advocateId.trim());
            
            response.put("status", "success");
            response.put("message", "Processes retrieved successfully");
            response.put("count", processes.size());
            response.put("data", processes);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY TIN ID ====================
    @GetMapping("/search/tin")
    public ResponseEntity<Map<String, Object>> findByTinId(
            @RequestParam("tinId") String tinId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (tinId == null || tinId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Tin ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            TinRegistrationProcess process = processService.findByTinId(tinId.trim());
            
            response.put("status", "success");
            response.put("data", process);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY STEPS ====================
    @GetMapping("/search/steps")
    public ResponseEntity<Map<String, Object>> findBySteps(
            @RequestParam("steps") String steps) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (steps == null || steps.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Steps is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            List<TinRegistrationProcess> processes = processService.findByStepsContainingIgnoreCase(steps.trim());
            
            response.put("status", "success");
            response.put("message", "Processes retrieved successfully");
            response.put("count", processes.size());
            response.put("data", processes);
            return ResponseEntity.ok(response);
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteTinRegistrationProcess(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Process ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            boolean isDeleted = processService.removeTinRegistrationProcess(id, userId);
            
            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Tin registration process deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Process could not be deleted");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}