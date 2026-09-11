package com.example.demo700.Controllers.TrademarkControllers;

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

import com.example.demo700.Model.Trademarkmodels.TrademarkRegistrationProcess;
import com.example.demo700.Services.TrademarkServices.TrademarkRegistrationProcessService;

@RestController
@RequestMapping("/api/trademark-registration-process")
public class TrademarkRegistrationProcessController {

    @Autowired
    private TrademarkRegistrationProcessService processService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> addTrademarkRegistrationProcess(
            @RequestBody TrademarkRegistrationProcess process,
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
                response.put("message", "Process userId is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getTradeMarkId() == null || process.getTradeMarkId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trademark ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TrademarkRegistrationProcess savedProcess = processService
                    .addTrademarkRegistrationProcess(process, userId);

            response.put("status", "success");
            response.put("message", "Trademark registration process added successfully");
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
    @PutMapping(value = "/update/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> updateTrademarkRegistrationProcess(
            @PathVariable("id") String id,
            @RequestBody TrademarkRegistrationProcess process,
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

            TrademarkRegistrationProcess updatedProcess = processService
                    .updateTrademarkRegistrationProcess(process, userId, id);

            response.put("status", "success");
            response.put("message", "Trademark registration process updated successfully");
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

            TrademarkRegistrationProcess process = processService.findById(id);

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
            List<TrademarkRegistrationProcess> processes = processService.findAll();

            response.put("status", "success");
            response.put("message", processes.isEmpty() ? "No processes found" : "Processes retrieved successfully");
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

    // ==================== FIND BY USER ID ====================
    @GetMapping("/search/userId")
    public ResponseEntity<Map<String, Object>> findByUserId(
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TrademarkRegistrationProcess> processes = processService.findByUserId(userId.trim());

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
    public ResponseEntity<Map<String, Object>> findByAdvocateId(
            @RequestParam("advocateId") String advocateId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (advocateId == null || advocateId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TrademarkRegistrationProcess> processes = processService.findByAdvocateId(advocateId.trim());

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

    // ==================== FIND BY TRADEMARK ID ====================
    @GetMapping("/search/tradeMarkId")
    public ResponseEntity<Map<String, Object>> findByTradeMarkId(
            @RequestParam("tradeMarkId") String tradeMarkId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tradeMarkId == null || tradeMarkId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trademark ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TrademarkRegistrationProcess process = processService.findByTradeMarkId(tradeMarkId.trim());

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

    // ==================== FIND BY STATUS ====================
    @GetMapping("/search/status")
    public ResponseEntity<Map<String, Object>> findByStatus(
            @RequestParam("status") boolean status) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<TrademarkRegistrationProcess> processes = processService.findByStatus(status);

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
    public ResponseEntity<Map<String, Object>> findBySteps(
            @RequestParam("steps") String steps) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (steps == null || steps.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Steps is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TrademarkRegistrationProcess> processes = processService
                    .findByStepsContainingIgnoreCase(steps.trim());

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

    // ==================== FIND BY TRADEMARK IDS (IN) ====================
    @GetMapping("/search/tradeMarkIds")
    public ResponseEntity<Map<String, Object>> findByTrademarkIdIn(
            @RequestParam("tradeMarkIds") List<String> tradeMarkIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tradeMarkIds == null || tradeMarkIds.isEmpty()) {
                response.put("status", "error");
                response.put("message", "At least one Trademark ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TrademarkRegistrationProcess> processes = processService.findByTrademarkIdIn(tradeMarkIds);

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
    public ResponseEntity<Map<String, Object>> deleteTrademarkRegistrationProcess(
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

            boolean isDeleted = processService.deleteTrademarkRegistrationProcess(id, userId);

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Trademark registration process deleted successfully");
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