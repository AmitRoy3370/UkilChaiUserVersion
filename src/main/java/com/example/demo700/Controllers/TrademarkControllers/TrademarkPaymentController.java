package com.example.demo700.Controllers.TrademarkControllers;

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

import com.example.demo700.DTOFiles.TrademarkPaymentResponse;
import com.example.demo700.Model.Trademarkmodels.TrademarkPayment;
import com.example.demo700.Services.TrademarkServices.TrademarkPaymentService;

@RestController
@RequestMapping("/api/trademark-payment")
public class TrademarkPaymentController {

	@Autowired
	private TrademarkPaymentService trademarkPaymentService;

	// ==================== CREATE ====================
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> addTrademarkPayment(@RequestBody TrademarkPayment payment,
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

			if (payment.getTradeMarkId() == null || payment.getTradeMarkId().trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (payment.getTransactionId() == null || payment.getTransactionId().trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Transaction ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TrademarkPayment savedPayment = trademarkPaymentService.addTrademarkPayment(payment, userId);

			response.put("status", "success");
			response.put("message", "Trademark payment added successfully");
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
	public ResponseEntity<Map<String, Object>> updateTrademarkPayment(@PathVariable("id") String id,
			@RequestBody TrademarkPayment payment, @RequestParam("userId") String userId) {

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

			TrademarkPayment updatedPayment = trademarkPaymentService.updateTrademarkPayment(payment, userId, id);

			response.put("status", "success");
			response.put("message", "Trademark payment updated successfully");
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

			TrademarkPaymentResponse payment = trademarkPaymentService.findById(id);

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
			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findAll();

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
	public ResponseEntity<Map<String, Object>> findBySenderUserId(@RequestParam("senderUserId") String senderUserId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (senderUserId == null || senderUserId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Sender User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findBySenderUserId(senderUserId.trim());

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
	public ResponseEntity<Map<String, Object>> findBySenderPhoneNumber(
			@RequestParam("senderPhoneNumber") String senderPhoneNumber) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (senderPhoneNumber == null || senderPhoneNumber.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Sender Phone Number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService
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

	// ==================== FIND BY RECEIVER PHONE NUMBER ====================
	@GetMapping("/search/receiverPhoneNumber")
	public ResponseEntity<Map<String, Object>> findByReceiverPhoneNumber(
			@RequestParam("receiverPhoneNumber") String receiverPhoneNumber) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (receiverPhoneNumber == null || receiverPhoneNumber.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Receiver Phone Number is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService
					.findByReceiverPhoneNumberContaingingIgnoreCase(receiverPhoneNumber.trim());

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

	// ==================== FIND BY SENDER USER ID AND TRADEMARK ID
	// ====================
	@GetMapping("/search/senderUserIdAndTrademarkId")
	public ResponseEntity<Map<String, Object>> findBySenderUserIdAndTrademarkId(
			@RequestParam("trademarkId") String trademarkId, @RequestParam("senderUserId") String senderUserId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (trademarkId == null || trademarkId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (senderUserId == null || senderUserId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Sender User ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService
					.findBySenderUserIdAndTrademarkId(trademarkId.trim(), senderUserId.trim());

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

	// ==================== FIND BY TRADEMARK ID ====================
	@GetMapping("/search/trademarkId")
	public ResponseEntity<Map<String, Object>> findByTrademarkId(@RequestParam("trademarkId") String trademarkId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (trademarkId == null || trademarkId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Trademark ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findByTradeMarkId(trademarkId.trim());

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
	@GetMapping("/search/transactionId")
	public ResponseEntity<Map<String, Object>> findByTransactionId(
			@RequestParam("transactionId") String transactionId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (transactionId == null || transactionId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Transaction ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			TrademarkPaymentResponse payment = trademarkPaymentService.findByTransactionId(transactionId.trim());

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

	// ==================== FIND BY TRANSACTION ID CONTAINING ====================
	@GetMapping("/search/transactionId/containing")
	public ResponseEntity<Map<String, Object>> findByTransactionIdContaining(
			@RequestParam("transactionId") String transactionId) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (transactionId == null || transactionId.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Transaction ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService
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

	// ==================== FIND BY AMOUNT GREATER THAN EQUAL ====================
	@GetMapping("/search/amount/greaterThanEqual")
	public ResponseEntity<Map<String, Object>> findByAmountGreaterThanEqual(@RequestParam("amount") double amount) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (amount < 0) {
				response.put("status", "error");
				response.put("message", "Amount cannot be negative");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findByAmountGreaterThanEqual(amount);

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

	// ==================== FIND BY AMOUNT LESS THAN EQUAL ====================
	@GetMapping("/search/amount/lessThanEqual")
	public ResponseEntity<Map<String, Object>> findByAmountLessThanEqual(@RequestParam("amount") double amount) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (amount < 0) {
				response.put("status", "error");
				response.put("message", "Amount cannot be negative");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findByAmountLessThanEqual(amount);

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

	// ==================== FIND BY SENDING TIME BEFORE ====================
	@GetMapping("/search/sendingTime/before")
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

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findBySendingTimeBefore(time);

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
	@GetMapping("/search/sendingTime/after")
	public ResponseEntity<Map<String, Object>> findBySendingTimeAfter(@RequestParam("sendingTime") String sendingTime) {

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

			List<TrademarkPaymentResponse> payments = trademarkPaymentService.findBySendingTimeAfter(time);

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
	public ResponseEntity<Map<String, Object>> deleteTrademarkPayment(@PathVariable("id") String id) {

		Map<String, Object> response = new HashMap<>();

		try {
			if (id == null || id.trim().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Payment ID is required");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			boolean isDeleted = trademarkPaymentService.deleteTrademarkPayment(id);

			if (isDeleted) {
				response.put("status", "success");
				response.put("message", "Trademark payment deleted successfully");
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