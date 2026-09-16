package com.example.demo700.Controllers.VatControllers;

import java.time.Instant;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.VatResponseDTO;
import com.example.demo700.Model.VatModels.Vat;
import com.example.demo700.Services.VatServices.VatService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/vat")
public class VatController {

    @Autowired
    private VatService vatService;

    @Autowired
    private ObjectMapper objectMapper;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> addVat(
            @RequestParam("userId") String userId,
            @RequestParam("adress") String adress,
            @RequestParam("tinNo") String tinNo,
            @RequestParam("buisnessName") String buisnessName,
            @RequestParam("tradeLicenseNo") String tradeLicenseNo,
            @RequestParam("annualTurnOver") String annualTurnOver,
            @RequestParam("mainProduct") String mainProduct,
            @RequestParam("natureOfBuisness") String natureOfBuisness,
            @RequestParam("numberOfBuisness") int numberOfBuisness,
            @RequestParam("numberOfEmployee") int numberOfEmployee,
            @RequestParam(value = "attachmentsId", required = false) String attachmentsId,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (tinNo == null || tinNo.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "TIN number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (tradeLicenseNo == null || tradeLicenseNo.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade license number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Vat vat = new Vat();
            vat.setUserId(userId.trim());
            vat.setAdress(adress);
            vat.setTinNo(tinNo.trim());
            vat.setBuisnessName(buisnessName);
            vat.setTradeLicenseNo(tradeLicenseNo.trim());
            vat.setAnnualTurnOver(annualTurnOver);
            vat.setMainProduct(mainProduct);
            vat.setNatureOfBuisness(natureOfBuisness);
            vat.setNumberOfBuisness(numberOfBuisness);
            vat.setNumberOfEmployee(numberOfEmployee);

            // Parse attachmentsId from JSON string to List<String>
            if (attachmentsId != null && !attachmentsId.trim().isEmpty()) {
                try {
                    List<String> attachmentList = objectMapper.readValue(
                            attachmentsId.trim(),
                            new TypeReference<List<String>>() {
                            });
                    vat.setDocuments(attachmentList);
                    System.out.println("✅ Parsed " + attachmentList.size() + " attachments from attachmentsId");
                } catch (Exception e) {
                    System.err.println("❌ Invalid attachmentsId format: " + e.getMessage());
                    response.put("status", "error");
                    response.put("message",
                            "Invalid attachmentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                vat.setDocuments(new java.util.ArrayList<>());
            }

            Vat savedVat = vatService.addVat(vat, userId, documents);

            response.put("status", "success");
            response.put("message", "VAT added successfully");
            response.put("data", savedVat);
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
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateVat(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId,
            @RequestParam(required = false) String adress,
            @RequestParam(required = false) String tinNo,
            @RequestParam(required = false) String buisnessName,
            @RequestParam(required = false) String tradeLicenseNo,
            @RequestParam(required = false) String annualTurnOver,
            @RequestParam(required = false) String mainProduct,
            @RequestParam(required = false) String natureOfBuisness,
            @RequestParam(required = false) Integer numberOfBuisness,
            @RequestParam(required = false) Integer numberOfEmployee,
            @RequestParam(required = false) String attachmentsId,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "VAT ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Vat vat = new Vat();
            vat.setUserId(userId.trim());

            // Set optional fields only if provided (non-null)
            if (adress != null && !adress.trim().isEmpty()) {
                vat.setAdress(adress.trim());
            }
            if (tinNo != null && !tinNo.trim().isEmpty()) {
                vat.setTinNo(tinNo.trim());
            }
            if (buisnessName != null && !buisnessName.trim().isEmpty()) {
                vat.setBuisnessName(buisnessName.trim());
            }
            if (tradeLicenseNo != null && !tradeLicenseNo.trim().isEmpty()) {
                vat.setTradeLicenseNo(tradeLicenseNo.trim());
            }
            if (annualTurnOver != null && !annualTurnOver.trim().isEmpty()) {
                vat.setAnnualTurnOver(annualTurnOver.trim());
            }
            if (mainProduct != null && !mainProduct.trim().isEmpty()) {
                vat.setMainProduct(mainProduct.trim());
            }
            if (natureOfBuisness != null && !natureOfBuisness.trim().isEmpty()) {
                vat.setNatureOfBuisness(natureOfBuisness.trim());
            }
            if (numberOfBuisness != null) {
                vat.setNumberOfBuisness(numberOfBuisness);
            }
            if (numberOfEmployee != null) {
                vat.setNumberOfEmployee(numberOfEmployee);
            }

            // ✅ Parse attachmentsId from JSON string to List<String> using ObjectMapper
            if (attachmentsId != null && !attachmentsId.trim().isEmpty()) {
                try {
                    List<String> attachmentList = objectMapper.readValue(
                            attachmentsId.trim(),
                            new TypeReference<List<String>>() {
                            });
                    vat.setDocuments(attachmentList);
                    System.out.println("✅ Parsed " + attachmentList.size() + " attachments from attachmentsId");
                } catch (Exception e) {
                    System.err.println("❌ Invalid attachmentsId format: " + e.getMessage());
                    response.put("status", "error");
                    response.put("message",
                            "Invalid attachmentsId format. Must be a JSON array: [\"id1\", \"id2\"]");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                // If no attachmentsId provided, use empty list
                // The service layer will handle merging with existing documents
                vat.setDocuments(new java.util.ArrayList<>());
            }

            Vat updatedVat = vatService.updateVat(id, vat, userId, documents);

            response.put("status", "success");
            response.put("message", "VAT updated successfully");
            response.put("data", updatedVat);
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
                response.put("message", "VAT ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            VatResponseDTO vat = vatService.findById(id);

            if (vat == null) {
                response.put("status", "error");
                response.put("message", "VAT not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("status", "success");
            response.put("data", vat);
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
            List<VatResponseDTO> vats = vatService.findAll();

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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
            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            System.out.println("sending user id :- " + userId);

            List<VatResponseDTO> vats = vatService.findByUserId(userId.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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
    public ResponseEntity<Map<String, Object>> findByAdress(@RequestParam String adress) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (adress == null || adress.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Address is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByAdressContainingIgnoreCase(adress.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY TIN NO ====================
    @GetMapping("/search/tinNo")
    public ResponseEntity<Map<String, Object>> findByTinNo(@RequestParam String tinNo) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tinNo == null || tinNo.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "TIN number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByTinNoContainingIgnoreCase(tinNo.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY BUSINESS NAME ====================
    @GetMapping("/search/buisnessName")
    public ResponseEntity<Map<String, Object>> findByBuisnessName(
            @RequestParam String buisnessName) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (buisnessName == null || buisnessName.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Business name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByBuisnessNameContainingIgnoreCase(buisnessName.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY TRADE LICENSE NO ====================
    @GetMapping("/search/tradeLicenseNo")
    public ResponseEntity<Map<String, Object>> findByTradeLicenseNo(
            @RequestParam String tradeLicenseNo) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (tradeLicenseNo == null || tradeLicenseNo.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade license number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByTradeLicenseNoContainingIgnoreCase(tradeLicenseNo.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY ANNUAL TURNOVER ====================
    @GetMapping("/search/annualTurnOver")
    public ResponseEntity<Map<String, Object>> findByAnnualTurnOver(
            @RequestParam String annualTurnOver) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (annualTurnOver == null || annualTurnOver.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Annual turnover is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByAnnualTurnOverContainingIgnoreCase(annualTurnOver.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY MAIN PRODUCT ====================
    @GetMapping("/search/mainProduct")
    public ResponseEntity<Map<String, Object>> findByMainProduct(
            @RequestParam String mainProduct) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (mainProduct == null || mainProduct.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Main product is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByMainProductContainingIgnoreCase(mainProduct.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY NATURE OF BUSINESS ====================
    @GetMapping("/search/natureOfBuisness")
    public ResponseEntity<Map<String, Object>> findByNatureOfBuisness(
            @RequestParam String natureOfBuisness) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (natureOfBuisness == null || natureOfBuisness.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Nature of business is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService
                    .findByNatureOfBuisnessContainingIgnoreCase(natureOfBuisness.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY NUMBER OF BUSINESS >= ====================
    @GetMapping("/search/numberOfBuisness/greaterThanEqual")
    public ResponseEntity<Map<String, Object>> findByNumberOfBuisnessGreaterThanEqual(
            @RequestParam int numberOfBuisness) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<VatResponseDTO> vats = vatService.findByNumberOfBuisnessGreaterThanEqual(numberOfBuisness);

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY NUMBER OF BUSINESS <= ====================
    @GetMapping("/search/numberOfBuisness/lessThanEqual")
    public ResponseEntity<Map<String, Object>> findByNumberOfBuisnessLessThanEqual(
            @RequestParam int numberOfBuisness) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<VatResponseDTO> vats = vatService.findByNumberOfBuisnessLessThanEqual(numberOfBuisness);

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY NUMBER OF EMPLOYEE >= ====================
    @GetMapping("/search/numberOfEmployee/greaterThanEqual")
    public ResponseEntity<Map<String, Object>> findByNumberOfEmployeeGreaterThanEqual(
            @RequestParam int numberOfEmployee) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<VatResponseDTO> vats = vatService.findByNumberOfEmployeeGreaterThanEqual(numberOfEmployee);

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY NUMBER OF EMPLOYEE <= ====================
    @GetMapping("/search/numberOfEmployee/lessThanEqual")
    public ResponseEntity<Map<String, Object>> findByNumberOfEmployeeLessThanEqual(
            @RequestParam int numberOfEmployee) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<VatResponseDTO> vats = vatService.findByNumberOfEmployeeLessThanEqual(numberOfEmployee);

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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

    // ==================== FIND BY DOCUMENTS ====================
    @GetMapping("/search/documents")
    public ResponseEntity<Map<String, Object>> findByDocuments(@RequestParam String documents) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (documents == null || documents.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Document is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<VatResponseDTO> vats = vatService.findByDocuments(documents.trim());

            response.put("status", "success");
            response.put("message", "VATs retrieved successfully");
            response.put("count", vats.size());
            response.put("data", vats);
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
    public ResponseEntity<Map<String, Object>> deleteVat(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "VAT ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            boolean isDeleted = vatService.deleteVat(id, userId);

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "VAT deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "VAT could not be deleted");
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
