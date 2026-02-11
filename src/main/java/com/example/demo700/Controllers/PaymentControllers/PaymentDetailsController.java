package com.example.demo700.Controllers.PaymentControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.PaymentModels.PaymentDetails;
import com.example.demo700.Services.PaymentServices.PaymentDetailsService;

@RestController
@RequestMapping("/api/payment")
public class PaymentDetailsController {

	@Autowired
	private PaymentDetailsService paymentDetailsService;

	// ================= ADD =================
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addPayment(@RequestBody PaymentDetails paymentDetails, @PathVariable String userId) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(paymentDetailsService.addPaymentDetails(paymentDetails, userId));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// ================= UPDATE =================
	@PutMapping("/update/{paymentId}/{userId}")
	public ResponseEntity<?> updatePayment(@RequestBody PaymentDetails paymentDetails, @PathVariable String paymentId,
			@PathVariable String userId) {

		try {
			return ResponseEntity.ok(paymentDetailsService.updatePaymentDetails(paymentDetails, userId, paymentId));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// ================= FIND BY ID =================
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {

		try {
			return ResponseEntity.ok(paymentDetailsService.findById(id));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= FIND ALL =================
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {

		try {
			return ResponseEntity.ok(paymentDetailsService.findAll());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= FIND BY ENUM (STRING) =================
	@GetMapping("/type/{paymentFor}")
	public ResponseEntity<?> findByPaymentFor(@PathVariable String paymentFor) {

		try {
			CasePayment paymentEnum = CasePayment.valueOf(paymentFor);
			return ResponseEntity.ok(paymentDetailsService.findByPaymentFor(paymentEnum));

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid payment type");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= PRICE FILTER =================
	@GetMapping("/price-greater/{price}")
	public ResponseEntity<?> priceGreater(@PathVariable double price) {

		try {
			return ResponseEntity.ok(paymentDetailsService.findByPriceGreaterThanEqual(price));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/price-less/{price}")
	public ResponseEntity<?> priceLess(@PathVariable double price) {

		try {
			return ResponseEntity.ok(paymentDetailsService.findByPriceLessThanEqual(price));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// ================= FIND BY USER =================
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> findByUser(@PathVariable String userId) {

		try {
			return ResponseEntity.ok(paymentDetailsService.findByUserId(userId));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= FIND BY CASE =================
	@GetMapping("/case/{caseId}")
	public ResponseEntity<?> findByCase(@PathVariable String caseId) {

		try {
			return ResponseEntity.ok(paymentDetailsService.findByCaseId(caseId));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= FIND BY CASE + ENUM =================
	@GetMapping("/case/{caseId}/type/{paymentFor}")
	public ResponseEntity<?> findByCaseAndType(@PathVariable String caseId, @PathVariable String paymentFor) {

		try {
			CasePayment paymentEnum = CasePayment.valueOf(paymentFor);

			return ResponseEntity.ok(paymentDetailsService.findByCaseIdAndPaymentFor(caseId, paymentEnum));

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid payment type");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ================= DELETE =================
	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<?> deletePayment(@PathVariable String id, @PathVariable String userId) {

		try {
			boolean deleted = paymentDetailsService.deletePaymentDetails(id, userId);

			return ResponseEntity.ok(deleted);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
