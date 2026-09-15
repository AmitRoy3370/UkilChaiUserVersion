package com.example.demo700.Controllers.VatControllers;

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

import com.example.demo700.Model.VatModels.VatRegistrationProcess;
import com.example.demo700.Services.VatServices.VatRegistrationProcessService;

@RestController
@RequestMapping("/api/vat-registration")
public class VatRegistrationProcessController {

    @Autowired
    private VatRegistrationProcessService processService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addProcess(
            @RequestBody VatRegistrationProcess process,
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

            if (process.getVatId() == null || process.getVatId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "VAT ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (process.getAdvocateId() == null || process.getAdvocateId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            VatRegistrationProcess savedProcess = processService.addProcess(process, userId);

            response.put("status", "success");
            response.put("message", "VAT registration process added successfully");
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
            @RequestBody VatRegistrationProcess process,
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

            VatRegistrationProcess updatedProcess = processService.updateProcess(process, userId, id);

            response.put("status", "success");
            response.put("message", "VAT registration process updated successfully");
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

            VatRegistrationProcess process = processService.findById(id);

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
            List<VatRegistrationProcess> processes = processService.findAll();

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

    // ==================== FIND BY VAT ID ====================
    @GetMapping("/search/vatId")
    public ResponseEntity<Map<String, Object>> findByVatId(@RequestParam("vatId") String vatId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (vatId == null || vatId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "VAT ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            VatRegistrationProcess process = processService.findByVatId(vatId.trim());

            if (process == null) {
                response.put("status", "error");
                response.put("message", "Process not found with VAT ID: " + vatId);
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

    // ==================== FIND BY VAT ID IN ====================
    @GetMapping("/search/vatIdIn")
    public ResponseEntity<Map<String, Object>> findByVatIdIn(@RequestParam("vatsId") List<String> vatsId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (vatsId == null || vatsId.isEmpty()) {
                response.put("status", "error");
                response.put("message", "VAT ID list is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatRegistrationProcess> processes = processService.findByVatIdIn(vatsId);

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

    // ==================== FIND BY USER ID ====================
    @GetMapping("/search/userId")
    public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatRegistrationProcess> processes = processService.findByUserId(userId.trim());

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
    public ResponseEntity<Map<String, Object>> findByAdvocateId(@RequestParam("advocateId") String advocateId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (advocateId == null || advocateId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Advocate ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatRegistrationProcess> processes = processService.findByAdvocateId(advocateId.trim());

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
    public ResponseEntity<Map<String, Object>> findByStatus(@RequestParam("status") boolean status) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<VatRegistrationProcess> processes = processService.findByStatus(status);

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

    // ==================== FIND BY STEPS CONTAINING ====================
    @GetMapping("/search/steps")
    public ResponseEntity<Map<String, Object>> findBySteps(@RequestParam("steps") String steps) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (steps == null || steps.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Steps is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatRegistrationProcess> processes = processService.findByStepsContainingIgnoreCase(steps.trim());

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

            boolean isDeleted = processService.deleteProcess(id, userId);

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "VAT registration process deleted successfully");
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