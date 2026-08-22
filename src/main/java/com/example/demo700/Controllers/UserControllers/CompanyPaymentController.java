package com.example.demo700.Controllers.UserControllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.DTOFiles.CompanyPaymentResponse;
import com.example.demo700.Model.UserModels.CompanyRequestPayment;
import com.example.demo700.Services.UserServices.CompanyPaymentService;

@RestController
@RequestMapping("/api/company-payments")
public class CompanyPaymentController {

    @Autowired
    private CompanyPaymentService companyPaymentService;

    // Create a new company payment
    @PostMapping("/add")
    public ResponseEntity<?> addCompanyPayment(@RequestBody CompanyRequestPayment companyPayment) {
        try {
            CompanyRequestPayment savedPayment = companyPaymentService.addCompanyPayment(companyPayment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing company payment
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompanyPayment(
            @PathVariable String id,
            @RequestBody CompanyRequestPayment companyPayment,
            @RequestHeader("userId") String userId) {
        try {
            CompanyRequestPayment updatedPayment = companyPaymentService.updateCompanyPayment(id, companyPayment, userId);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable String id) {
        try {
            CompanyPaymentResponse payment = companyPaymentService.findById(id);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all payments
    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments() {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findAll();
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get payments by company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getPaymentsByCompanyId(@PathVariable String companyId) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findByCompanyId(companyId);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by sender user ID
    @GetMapping("/sender/user/{senderUserId}")
    public ResponseEntity<?> getPaymentsBySenderUserId(@PathVariable String senderUserId) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findBySenderUserId(senderUserId);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by sender phone number
    @GetMapping("/sender/phone/{senderPhoneNumber}")
    public ResponseEntity<?> getPaymentsBySenderPhoneNumber(@PathVariable String senderPhoneNumber) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findBySenderPhoneNumber(senderPhoneNumber);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by receiver phone number
    @GetMapping("/receiver/phone/{receiverPhoneNumber}")
    public ResponseEntity<?> getPaymentsByReceiverPhoneNumber(@PathVariable String receiverPhoneNumber) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findByReceiverPhoneNumber(receiverPhoneNumber);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by transaction ID
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getPaymentsByTransactionId(@PathVariable String transactionId) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findByTransactionId(transactionId);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by amount greater than or equal
    @GetMapping("/amount/gte")
    public ResponseEntity<?> getPaymentsByAmountGreaterThanEqual(@RequestParam double amount) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findByAmountGreaterThanEqual(amount);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by amount less than or equal
    @GetMapping("/amount/lte")
    public ResponseEntity<?> getPaymentsByAmountLessThanEqual(@RequestParam double amount) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findByAmountLessThanEqual(amount);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by sending time after
    @GetMapping("/time/after")
    public ResponseEntity<?> getPaymentsBySendingTimeAfter(@RequestParam Instant sendingTime) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findBySendingTimeAfter(sendingTime);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by sending time before
    @GetMapping("/time/before")
    public ResponseEntity<?> getPaymentsBySendingTimeBefore(@RequestParam Instant sendingTime) {
        try {
            List<CompanyPaymentResponse> payments = companyPaymentService.findBySendingTimeBefore(sendingTime);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete a payment
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(
            @PathVariable String id,
            @RequestHeader("userId") String userId) {
        try {
            boolean deleted = companyPaymentService.removeCompanyPayment(id, userId);
            if (deleted) {
                return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Payment not deleted", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get payments by amount range (between min and max)
    @GetMapping("/amount/range")
    public ResponseEntity<?> getPaymentsByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount) {
        try {
            // You can implement this method in service if needed
            // This is a combination of two queries
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
