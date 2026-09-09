package com.example.demo700.Controllers.TradeLicenseControllers;

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

import com.example.demo700.Model.TradeLicenseModels.TradeLicenseRegistrationProcess;
import com.example.demo700.Services.TradeLicenseServices.TradeLicenseRegistrationProcessService;

@RestController
@RequestMapping("/api/trade-license-registration")
public class TradeLicenseRegistrationProcessController {

    @Autowired
    private TradeLicenseRegistrationProcessService processService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addProcess(
            @RequestBody TradeLicenseRegistrationProcess process,
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
            if (process.getUserId() == null || process.getUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID in process is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getTradeLicenseId() == null || process.getTradeLicenseId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicenseRegistrationProcess savedProcess = processService.addProcess(process, userId);

            response.put("status", "success");
            response.put("message", "Trade license registration process added successfully");
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
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateProcess(
            @PathVariable("id") String id,
            @RequestBody TradeLicenseRegistrationProcess process,
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
            if (process.getUserId() == null || process.getUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID in process is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getTradeLicenseId() == null || process.getTradeLicenseId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicenseRegistrationProcess updatedProcess = processService.updateProcess(process, userId, id);

            response.put("status", "success");
            response.put("message", "Trade license registration process updated successfully");
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

            TradeLicenseRegistrationProcess process = processService.findById(id);

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
            List<TradeLicenseRegistrationProcess> processes = processService.findAll();

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found");
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

            response.put("status", "success");
            response.put("message", "Processes retrieved successfully");
            response.put("count", processes.size());
            response.put("data", processes);
            return ResponseEntity.ok(response);

        } catch (NullPointerException e) {
            response.put("status", "error");
            response.put("message", "No processes found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== FIND BY USER ID ====================
    @GetMapping("/search/user")
    public ResponseEntity<Map<String, Object>> findByUserId(
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicenseRegistrationProcess> processes = processService.findByUserId(userId.trim());

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found for user: " + userId);
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

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

            List<TradeLicenseRegistrationProcess> processes = processService.findByAdvocateId(advocateId.trim());

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found for advocate: " + advocateId);
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

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
    public ResponseEntity<Map<String, Object>> findByStatus(
            @RequestParam("status") boolean status) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<TradeLicenseRegistrationProcess> processes = processService.findByStatus(status);

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found with status: " + status);
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

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

    // ==================== FIND BY TRADE LICENSE ID ====================
    @GetMapping("/search/trade-license")
    public ResponseEntity<Map<String, Object>> findByTradeLicenseId(
            @RequestParam("tradeLicenseId") String tradeLicenseId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tradeLicenseId == null || tradeLicenseId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicenseRegistrationProcess process = processService.findByTradeLicenseId(tradeLicenseId.trim());

            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process not found for trade license: " + tradeLicenseId);
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

            List<TradeLicenseRegistrationProcess> processes = processService.findByStepsContainingIgnoreCase(steps.trim());

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found with steps containing: " + steps);
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

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

    // ==================== FIND BY TRADE LICENSE ID IN LIST ====================
    @GetMapping("/search/trade-licenses")
    public ResponseEntity<Map<String, Object>> findByTradeLicenseIdIn(
            @RequestParam("tradeLicenseIds") List<String> tradeLicenseIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tradeLicenseIds == null || tradeLicenseIds.isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License IDs list is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Filter out empty/null values
            List<String> validIds = tradeLicenseIds.stream()
                    .filter(id -> id != null && !id.trim().isEmpty())
                    .map(String::trim)
                    .collect(java.util.stream.Collectors.toList());

            if (validIds.isEmpty()) {
                response.put("status", "error");
                response.put("message", "No valid Trade License IDs provided");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicenseRegistrationProcess> processes = processService.findByTradeLicenseIdIn(validIds);

            if (processes.isEmpty()) {
                response.put("status", "success");
                response.put("message", "No processes found for the given trade license IDs");
                response.put("count", 0);
                response.put("data", processes);
                return ResponseEntity.ok(response);
            }

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

            boolean isDeleted = processService.removeTradeLicenseRegistrationProcess(id, userId);

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Trade license registration process deleted successfully");
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
}