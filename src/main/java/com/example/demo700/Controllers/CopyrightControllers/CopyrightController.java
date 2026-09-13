package com.example.demo700.Controllers.CopyrightControllers;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CopyrightResponse;
import com.example.demo700.Model.CopyrightModels.Copyright;
import com.example.demo700.Services.CopyrightServices.CopyrightService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/copyright")
public class CopyrightController {

    @Autowired
    private CopyrightService copyrightService;

    @Autowired
    private ObjectMapper objectMapper;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Map<String, Object>> addCopyright(
            @RequestParam("userId") String userId,
            @RequestParam("author") String author,
            @RequestParam("typeOfWork") String typeOfWork,
            @RequestParam("yearOfCreation") String yearOfCreation,
            @RequestParam("titleOfWork") String titleOfWork,
            @RequestParam("description") String description,
            @RequestParam("applicationName") String applicationName,
            @RequestParam("mobileNumber") String mobileNumber,
            @RequestParam("email") String email,
            @RequestParam("adress") String adress,
            @RequestParam(value = "documentsId", required = false) String documentsId,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (author == null || author.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Author is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (titleOfWork == null || titleOfWork.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Title of work is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (email == null || email.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Email is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Mobile number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Instant year;
            try {
                year = Instant.parse(yearOfCreation.trim());
            } catch (DateTimeParseException e) {
                response.put("status", "error");
                response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Parse existing document IDs using ObjectMapper
            List<String> existingDocumentsId = new ArrayList<>();
            if (documentsId != null && !documentsId.trim().isEmpty()) {
                try {
                    existingDocumentsId = objectMapper.readValue(
                            documentsId.trim(),
                            new TypeReference<List<String>>() {
                            });
                } catch (Exception e) {
                    response.put("status", "error");
                    response.put("message", "Invalid documentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            Copyright copyright = new Copyright();
            copyright.setUserId(userId.trim());
            copyright.setAuthor(author.trim());
            copyright.setTypeOfWork(typeOfWork != null ? typeOfWork.trim() : null);
            copyright.setYearOfCreation(year);
            copyright.setTitleOfWork(titleOfWork.trim());
            copyright.setDescription(description != null ? description.trim() : null);
            copyright.setApplicationName(applicationName != null ? applicationName.trim() : null);
            copyright.setMobileNumber(mobileNumber.trim());
            copyright.setEmail(email.trim());
            copyright.setAdress(adress != null ? adress.trim() : null);
            copyright.setDocuments(existingDocumentsId);

            Copyright savedCopyright = copyrightService.addCopyright(copyright, userId.trim(), documents);

            response.put("status", "success");
            response.put("message", "Copyright added successfully");
            response.put("data", savedCopyright);
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
    @PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Map<String, Object>> updateCopyright(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "typeOfWork", required = false) String typeOfWork,
            @RequestParam(value = "yearOfCreation", required = false) String yearOfCreation,
            @RequestParam(value = "titleOfWork", required = false) String titleOfWork,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "applicationName", required = false) String applicationName,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "adress", required = false) String adress,
            @RequestParam(value = "documentsId", required = false) String documentsId,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Copyright ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Copyright copyright = new Copyright();
            copyright.setUserId(userId.trim());

            if (author != null && !author.trim().isEmpty()) {
                copyright.setAuthor(author.trim());
            }
            if (typeOfWork != null && !typeOfWork.trim().isEmpty()) {
                copyright.setTypeOfWork(typeOfWork.trim());
            }
            if (yearOfCreation != null && !yearOfCreation.trim().isEmpty()) {
                try {
                    copyright.setYearOfCreation(Instant.parse(yearOfCreation.trim()));
                } catch (DateTimeParseException e) {
                    response.put("status", "error");
                    response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }
            if (titleOfWork != null && !titleOfWork.trim().isEmpty()) {
                copyright.setTitleOfWork(titleOfWork.trim());
            }
            if (description != null && !description.trim().isEmpty()) {
                copyright.setDescription(description.trim());
            }
            if (applicationName != null && !applicationName.trim().isEmpty()) {
                copyright.setApplicationName(applicationName.trim());
            }
            if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
                copyright.setMobileNumber(mobileNumber.trim());
            }
            if (email != null && !email.trim().isEmpty()) {
                copyright.setEmail(email.trim());
            }
            if (adress != null && !adress.trim().isEmpty()) {
                copyright.setAdress(adress.trim());
            }

            // ✅ Parse old document IDs using ObjectMapper
            if (documentsId != null && !documentsId.trim().isEmpty()) {
                try {
                    List<String> existingDocumentsId = objectMapper.readValue(
                            documentsId.trim(),
                            new TypeReference<List<String>>() {
                            });
                    copyright.setDocuments(existingDocumentsId);
                } catch (Exception e) {
                    response.put("status", "error");
                    response.put("message", "Invalid documentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            Copyright updatedCopyright = copyrightService.updateCopyright(copyright, userId.trim(), id.trim(), documents);

            response.put("status", "success");
            response.put("message", "Copyright updated successfully");
            response.put("data", updatedCopyright);
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
                response.put("message", "Copyright ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            CopyrightResponse copyright = copyrightService.findById(id.trim());

            if (copyright == null) {
                response.put("status", "error");
                response.put("message", "Copyright not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("status", "success");
            response.put("data", copyright);
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
            List<CopyrightResponse> copyrights = copyrightService.findAll();

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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
    public ResponseEntity<Map<String, Object>> findByUserId(@RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByUserId(userId.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY AUTHOR ====================
    @GetMapping("/search/author")
    public ResponseEntity<Map<String, Object>> findByAuthor(@RequestParam("author") String author) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (author == null || author.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Author is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByAuthorContainingIgnoreCase(author.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY TYPE OF WORK ====================
    @GetMapping("/search/typeOfWork")
    public ResponseEntity<Map<String, Object>> findByTypeOfWork(@RequestParam("typeOfWork") String typeOfWork) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (typeOfWork == null || typeOfWork.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Type of work is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByTypeOfWorkContainingIgnoreCase(typeOfWork.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY YEAR OF CREATION AFTER ====================
    @GetMapping("/search/yearOfCreation/after")
    public ResponseEntity<Map<String, Object>> findByYearOfCreationAfter(
            @RequestParam("yearOfCreation") String yearOfCreation) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (yearOfCreation == null || yearOfCreation.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Year of creation is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Instant year;
            try {
                year = Instant.parse(yearOfCreation.trim());
            } catch (DateTimeParseException e) {
                response.put("status", "error");
                response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByYearOfCreationAfter(year);

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY YEAR OF CREATION BEFORE ====================
    @GetMapping("/search/yearOfCreation/before")
    public ResponseEntity<Map<String, Object>> findByYearOfCreationBefore(
            @RequestParam("yearOfCreation") String yearOfCreation) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (yearOfCreation == null || yearOfCreation.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Year of creation is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Instant year;
            try {
                year = Instant.parse(yearOfCreation.trim());
            } catch (DateTimeParseException e) {
                response.put("status", "error");
                response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByYearOfCreationBefore(year);

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY TITLE OF WORK ====================
    @GetMapping("/search/titleOfWork")
    public ResponseEntity<Map<String, Object>> findByTitleOfWork(@RequestParam("titleOfWork") String titleOfWork) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (titleOfWork == null || titleOfWork.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Title of work is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByTitleOfWorkContainingIgnoreCase(titleOfWork.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY DESCRIPTION ====================
    @GetMapping("/search/description")
    public ResponseEntity<Map<String, Object>> findByDescription(@RequestParam("description") String description) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (description == null || description.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Description is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByDescriptionContainingIgnoreCase(description.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY APPLICATION NAME ====================
    @GetMapping("/search/applicationName")
    public ResponseEntity<Map<String, Object>> findByApplicationName(
            @RequestParam("applicationName") String applicationName) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (applicationName == null || applicationName.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Application name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService
                    .findByApplicationNameContainingIgnoreCase(applicationName.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY MOBILE NUMBER ====================
    @GetMapping("/search/mobileNumber")
    public ResponseEntity<Map<String, Object>> findByMobileNumber(@RequestParam("mobileNumber") String mobileNumber) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Mobile number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            CopyrightResponse copyright = copyrightService.findByMobileNumber(mobileNumber.trim());

            if (copyright == null) {
                response.put("status", "error");
                response.put("message", "Copyright not found with mobile number: " + mobileNumber);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("status", "success");
            response.put("data", copyright);
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

    // ==================== FIND BY EMAIL ====================
    @GetMapping("/search/email")
    public ResponseEntity<Map<String, Object>> findByEmail(@RequestParam("email") String email) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (email == null || email.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Email is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            CopyrightResponse copyright = copyrightService.findByEmail(email.trim());

            if (copyright == null) {
                response.put("status", "error");
                response.put("message", "Copyright not found with email: " + email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("status", "success");
            response.put("data", copyright);
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

    // ==================== FIND BY ADDRESS ====================
    @GetMapping("/search/adress")
    public ResponseEntity<Map<String, Object>> findByAdress(@RequestParam("adress") String adress) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (adress == null || adress.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Address is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByAdressContainingIgnoreCase(adress.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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

    // ==================== FIND BY DOCUMENTS ====================
    @GetMapping("/search/documents")
    public ResponseEntity<Map<String, Object>> findByDocuments(@RequestParam("documents") String documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (documents == null || documents.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Document ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<CopyrightResponse> copyrights = copyrightService.findByDocumentsContainingIgnoreCase(documents.trim());

            response.put("status", "success");
            response.put("message", "Copyrights retrieved successfully");
            response.put("count", copyrights.size());
            response.put("data", copyrights);
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
    public ResponseEntity<Map<String, Object>> deleteCopyright(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Copyright ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            boolean isDeleted = copyrightService.removeCopyright(id.trim(), userId.trim());

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Copyright deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Copyright could not be deleted");
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