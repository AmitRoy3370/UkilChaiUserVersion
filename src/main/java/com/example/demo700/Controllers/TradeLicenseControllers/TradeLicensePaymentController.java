package com.example.demo700.Controllers.TradeLicenseControllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.DTOFiles.TradeLicensePaymentResponseDTO;
import com.example.demo700.Model.TradeLicenseModels.TradeLicensePayment;
import com.example.demo700.Services.TradeLicenseServices.TradeLicensePaymentService;

@RestController
@RequestMapping("/api/trade-license-payment")
public class TradeLicensePaymentController {

    @Autowired
    private TradeLicensePaymentService paymentService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addPayment(
            @RequestBody TradeLicensePayment payment,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (payment == null) {
                response.put("status", "error");
                response.put("message", "Payment data is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validate payment fields
            if (payment.getSenderUserId() == null || payment.getSenderUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getSenderPhoneNumber() == null || payment.getSenderPhoneNumber().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getTransactionId() == null || payment.getTransactionId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Transaction ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getAmount() <= 0) {
                response.put("status", "error");
                response.put("message", "Amount must be greater than 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getTradeLicenseId() == null || payment.getTradeLicenseId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicensePayment savedPayment = paymentService.addPayment(payment, userId);

            response.put("status", "success");
            response.put("message", "Payment added successfully");
            response.put("data", savedPayment);
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
    public ResponseEntity<Map<String, Object>> updatePayment(
            @PathVariable("id") String id,
            @RequestBody TradeLicensePayment payment,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Payment ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment == null) {
                response.put("status", "error");
                response.put("message", "Payment data is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validate payment fields
            if (payment.getSenderUserId() == null || payment.getSenderUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getSenderPhoneNumber() == null || payment.getSenderPhoneNumber().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getTransactionId() == null || payment.getTransactionId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Transaction ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getAmount() <= 0) {
                response.put("status", "error");
                response.put("message", "Amount must be greater than 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getTradeLicenseId() == null || payment.getTradeLicenseId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Trade License ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicensePayment updatedPayment = paymentService.updatePayment(payment, id, userId);

            response.put("status", "success");
            response.put("message", "Payment updated successfully");
            response.put("data", updatedPayment);
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
                response.put("message", "Payment ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TradeLicensePaymentResponseDTO payment = paymentService.findById(id);

            if (payment == null) {
                response.put("status", "error");
                response.put("message", "Payment not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("status", "success");
            response.put("data", payment);
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
            List<TradeLicensePaymentResponseDTO> payments = paymentService.findAll();

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY SENDER USER ID ====================
    @GetMapping("/search/sender-user")
    public ResponseEntity<Map<String, Object>> findBySenderUserId(
            @RequestParam("senderUserId") String senderUserId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (senderUserId == null || senderUserId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService.findBySenderUserId(senderUserId.trim());

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY SENDER PHONE ====================
    @GetMapping("/search/sender-phone")
    public ResponseEntity<Map<String, Object>> findBySenderPhone(
            @RequestParam("senderPhoneNumber") String senderPhoneNumber) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (senderPhoneNumber == null || senderPhoneNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService
                    .findBySenderPhoneNumberContainingIgnoreCase(senderPhoneNumber.trim());

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY RECEIVER PHONE ====================
    @GetMapping("/search/receiver-phone")
    public ResponseEntity<Map<String, Object>> findByReceiverPhone(
            @RequestParam("receiverPhoneNumber") String receiverPhoneNumber) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (receiverPhoneNumber == null || receiverPhoneNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Receiver phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService
                    .findByReceiverPhoneNumberContainingIgnoreCase(receiverPhoneNumber.trim());

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY TRANSACTION ID ====================
    @GetMapping("/search/transaction")
    public ResponseEntity<Map<String, Object>> findByTransactionId(
            @RequestParam("transactionId") String transactionId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (transactionId == null || transactionId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Transaction ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService
                    .findByTransactionIdContainingIgnoreCase(transactionId.trim());

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY AMOUNT GREATER THAN OR EQUAL ====================
    @GetMapping("/search/amount-gte")
    public ResponseEntity<Map<String, Object>> findByAmountGreaterThanEqual(
            @RequestParam("amount") double amount) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (amount < 0) {
                response.put("status", "error");
                response.put("message", "Amount cannot be negative");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService.findByAmountGreaterThanEqual(amount);

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY AMOUNT LESS THAN OR EQUAL ====================
    @GetMapping("/search/amount-lte")
    public ResponseEntity<Map<String, Object>> findByAmountLessThanEqual(
            @RequestParam("amount") double amount) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (amount < 0) {
                response.put("status", "error");
                response.put("message", "Amount cannot be negative");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService.findByAmountLessThanEqual(amount);

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

            List<TradeLicensePaymentResponseDTO> payments = paymentService
                    .findByTradeLicenseId(tradeLicenseId.trim());

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY SENDING TIME BEFORE ====================
    @GetMapping("/search/time-before")
    public ResponseEntity<Map<String, Object>> findBySendingTimeBefore(
            @RequestParam("sendingTime") String sendingTime) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (sendingTime == null || sendingTime.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sending time is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Instant time;
            try {
                time = Instant.parse(sendingTime.trim());
            } catch (DateTimeParseException e) {
                response.put("status", "error");
                response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService.findBySendingTimeBefore(time);

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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

    // ==================== FIND BY SENDING TIME AFTER ====================
    @GetMapping("/search/time-after")
    public ResponseEntity<Map<String, Object>> findBySendingTimeAfter(
            @RequestParam("sendingTime") String sendingTime) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (sendingTime == null || sendingTime.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sending time is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Instant time;
            try {
                time = Instant.parse(sendingTime.trim());
            } catch (DateTimeParseException e) {
                response.put("status", "error");
                response.put("message", "Invalid date format. Use ISO-8601 format (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<TradeLicensePaymentResponseDTO> payments = paymentService.findBySendingTimeAfter(time);

            response.put("status", "success");
            response.put("message", "Payments retrieved successfully");
            response.put("count", payments.size());
            response.put("data", payments);
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
    public ResponseEntity<Map<String, Object>> deletePayment(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (id == null || id.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Payment ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            boolean isDeleted = paymentService.delete(id, userId);

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Payment deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Payment could not be deleted");
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}