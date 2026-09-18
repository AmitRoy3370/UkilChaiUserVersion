package com.example.demo700.Controllers.RJSCControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.example.demo700.Model.RJSCModels.RJSCRegistrationProcess;
import com.example.demo700.Services.RJSCServices.RJSCRegistrationProcessService;

@RestController
@RequestMapping("/api/rjsc-registration-process")
public class RJSCRegistrationProcessController {

    @Autowired
    private RJSCRegistrationProcessService processService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addProcess(
            @RequestBody RJSCRegistrationProcess process,
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

            if (process.getUserId() == null || process.getUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Process User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getRjscId() == null || process.getRjscId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "RJSC ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RJSCRegistrationProcess savedProcess = processService.addProcess(process, userId.trim());

            response.put("status", "success");
            response.put("message", "RJSC registration process added successfully");
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== UPDATE ====================
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateProcess(
            @PathVariable("id") String id,
            @RequestBody RJSCRegistrationProcess process,
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

            RJSCRegistrationProcess updatedProcess = processService.updateProcess(process, userId.trim(), id);

            response.put("status", "success");
            response.put("message", "RJSC registration process updated successfully");
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
            e.printStackTrace();
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

            RJSCRegistrationProcess process = processService.findById(id);

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
            List<RJSCRegistrationProcess> processes = processService.findAll();

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

    // ==================== FIND BY USER ID ====================
    @GetMapping("/search/userId")
    public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize: if userId is duplicated (comma-separated), take the first
            if (userId != null && userId.contains(",")) {
                String original = userId;
                userId = userId.split(",")[0].trim();
                System.out.println("⚠️ Normalized userId from [" + original + "] to [" + userId + "]");
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCRegistrationProcess> processes = processService.findByUserId(userId.trim());

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
    @GetMapping("/search/advocateId")
    public ResponseEntity<Map<String, Object>> findByAdvocateId(@RequestParam String advocateId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize duplicates
            if (advocateId != null && advocateId.contains(",")) {
                advocateId = advocateId.split(",")[0].trim();
            }

            if (advocateId == null || advocateId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCRegistrationProcess> processes = processService.findByAdvocateId(advocateId.trim());

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

    // ==================== FIND BY STATUS ====================
    @GetMapping("/search/status")
    public ResponseEntity<Map<String, Object>> findByStatus(@RequestParam boolean status) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<RJSCRegistrationProcess> processes = processService.findByStatus(status);

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

    // ==================== FIND BY RJSC ID ====================
    @GetMapping("/search/rjscId")
    public ResponseEntity<Map<String, Object>> findByRjscId(@RequestParam String rjscId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize duplicates
            if (rjscId != null && rjscId.contains(",")) {
                rjscId = rjscId.split(",")[0].trim();
            }

            if (rjscId == null || rjscId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "RJSC ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RJSCRegistrationProcess process = processService.findByRjscId(rjscId.trim());

            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process not found with RJSC ID: " + rjscId);
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

    // ==================== FIND BY RJSC IDs (BULK) ====================
    @GetMapping("/search/rjscIds")
    public ResponseEntity<Map<String, Object>> findByRjscIdIn(@RequestParam List<String> rjscIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (rjscIds == null || rjscIds.isEmpty()) {
                response.put("status", "error");
                response.put("message", "RJSC IDs are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCRegistrationProcess> processes = processService.findByRjscIdIn(rjscIds);

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

    // ==================== FIND BY STEPS ====================
    @GetMapping("/search/steps")
    public ResponseEntity<Map<String, Object>> findBySteps(@RequestParam String steps) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (steps == null || steps.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Steps is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCRegistrationProcess> processes = processService.findByStepsContainingIgnoreCase(steps.trim());

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
    public ResponseEntity<Map<String, Object>> deleteProcess(
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

            // ✅ Normalize userId
            if (userId.contains(",")) {
                userId = userId.split(",")[0].trim();
            }

            boolean isDeleted = processService.delete(id, userId.trim());

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "RJSC registration process deleted successfully");
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