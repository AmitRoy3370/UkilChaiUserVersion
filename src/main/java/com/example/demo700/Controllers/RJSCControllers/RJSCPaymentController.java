package com.example.demo700.Controllers.RJSCControllers;

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

import com.example.demo700.Model.RJSCModels.RJSCPayment;
import com.example.demo700.Services.RJSCServices.RJSCPaymentService;

@RestController
@RequestMapping("/api/rjsc-payment")
public class RJSCPaymentController {

    @Autowired
    private RJSCPaymentService paymentService;

    // ==================== CREATE ====================
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addPayment(
            @RequestBody RJSCPayment payment,
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

            if (payment.getSenderUserId() == null || payment.getSenderUserId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getRjscId() == null || payment.getRjscId().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "RJSC ID is required");
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

            RJSCPayment savedPayment = paymentService.addPayment(payment, userId.trim());

            response.put("status", "success");
            response.put("message", "RJSC payment added successfully");
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== UPDATE ====================
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updatePayment(
            @PathVariable("id") String id,
            @RequestBody RJSCPayment payment,
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

            if (payment.getAmount() <= 0) {
                response.put("status", "error");
                response.put("message", "Amount must be greater than 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RJSCPayment updatedPayment = paymentService.updatePayment(payment, userId.trim(), id);

            response.put("status", "success");
            response.put("message", "RJSC payment updated successfully");
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
                response.put("message", "Payment ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RJSCPayment payment = paymentService.findById(id);

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
            List<RJSCPayment> payments = paymentService.findAll();

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
    @GetMapping("/search/senderUserId")
    public ResponseEntity<Map<String, Object>> findBySenderUserId(@RequestParam String senderUserId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize duplicates
            if (senderUserId != null && senderUserId.contains(",")) {
                senderUserId = senderUserId.split(",")[0].trim();
            }

            if (senderUserId == null || senderUserId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findBySenderUserId(senderUserId.trim());

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

    // ==================== FIND BY SENDER USER NAME ====================
    @GetMapping("/search/senderUserName")
    public ResponseEntity<Map<String, Object>> findBySenderUserName(@RequestParam String senderUserName) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (senderUserName == null || senderUserName.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender user name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService
                    .findBySenderUserNameContainingIgnoreCase(senderUserName.trim());

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

    // ==================== FIND BY SENDER PHONE NUMBER ====================
    @GetMapping("/search/senderPhoneNumber")
    public ResponseEntity<Map<String, Object>> findBySenderPhoneNumber(@RequestParam String senderPhoneNumber) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (senderPhoneNumber == null || senderPhoneNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findBySenderPhoneNumber(senderPhoneNumber.trim());

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

    // ==================== FIND BY RECEIVER PHONE NUMBER ====================
    @GetMapping("/search/receiverPhoneNumber")
    public ResponseEntity<Map<String, Object>> findByReceiverPhoneNumber(@RequestParam String receiverPhoneNumber) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (receiverPhoneNumber == null || receiverPhoneNumber.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Receiver phone number is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findByReceiverPhoneNumber(receiverPhoneNumber.trim());

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

    // ==================== FIND BY TRANSACTION ID (EXACT) ====================
    @GetMapping("/search/transactionId")
    public ResponseEntity<Map<String, Object>> findByTransactionId(@RequestParam String transactionId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize duplicates
            if (transactionId != null && transactionId.contains(",")) {
                transactionId = transactionId.split(",")[0].trim();
            }

            if (transactionId == null || transactionId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Transaction ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RJSCPayment payment = paymentService.findByTransactionId(transactionId.trim());

            if (payment == null) {
                response.put("status", "error");
                response.put("message", "Payment not found with transaction ID: " + transactionId);
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

    // ==================== FIND BY TRANSACTION ID (CONTAINING) ====================
    @GetMapping("/search/transactionId/containing")
    public ResponseEntity<Map<String, Object>> findByTransactionIdContaining(
            @RequestParam String transactionId) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (transactionId == null || transactionId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Transaction ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService
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

    // ==================== FIND BY AMOUNT >= ====================
    @GetMapping("/search/amount/greaterThanEqual")
    public ResponseEntity<Map<String, Object>> findByAmountGreaterThanEqual(@RequestParam double amount) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (amount < 0) {
                response.put("status", "error");
                response.put("message", "Amount cannot be negative");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findByAmountGreaterThanEqual(amount);

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

    // ==================== FIND BY AMOUNT <= ====================
    @GetMapping("/search/amount/lessThanEqual")
    public ResponseEntity<Map<String, Object>> findByAmountLessThanEqual(@RequestParam double amount) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (amount < 0) {
                response.put("status", "error");
                response.put("message", "Amount cannot be negative");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findByAmountLessThanEquak(amount);

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

    // ==================== FIND BY SENDING TIME AFTER ====================
    @GetMapping("/search/sendingTime/after")
    public ResponseEntity<Map<String, Object>> findBySendingTimeAfter(@RequestParam String sendingTime) {

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
                response.put("message",
                        "Invalid date format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findBySendingTimeAfter(time);

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
    @GetMapping("/search/sendingTime/before")
    public ResponseEntity<Map<String, Object>> findBySendingTimeBefore(@RequestParam String sendingTime) {

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
                response.put("message",
                        "Invalid date format. Use ISO-8601 (e.g., 2024-01-01T00:00:00Z)");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService.findBySendingTimeBefore(time);

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

    // ==================== FIND BY SENDER USER ID AND RJSC ID ====================
    @GetMapping("/search/senderUserIdAndRjscId")
    public ResponseEntity<Map<String, Object>> findBySenderUserIdAndRjscId(
            @RequestParam String senderUserId,
            @RequestParam String rjscId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Normalize duplicates
            if (senderUserId != null && senderUserId.contains(",")) {
                senderUserId = senderUserId.split(",")[0].trim();
            }
            if (rjscId != null && rjscId.contains(",")) {
                rjscId = rjscId.split(",")[0].trim();
            }

            if (senderUserId == null || senderUserId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Sender User ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (rjscId == null || rjscId.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "RJSC ID is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<RJSCPayment> payments = paymentService
                    .findBySenderUserIdAndRJSCId(senderUserId.trim(), rjscId.trim());

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

            List<RJSCPayment> payments = paymentService.findByRJSCId(rjscId.trim());

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

            // ✅ Normalize userId
            if (userId.contains(",")) {
                userId = userId.split(",")[0].trim();
            }

            boolean isDeleted = paymentService.delete(id, userId.trim());

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "RJSC payment deleted successfully");
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

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}