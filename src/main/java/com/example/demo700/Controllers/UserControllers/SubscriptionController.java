package com.example.demo700.Controllers.UserControllers;

import java.util.List;
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

import com.example.demo700.Model.UserModels.Subscription;
import com.example.demo700.Services.UserServices.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	// ==================== ADD SUBSCRIPTION (POST) ====================
	// Accepts JSON data for the Subscription and a MultipartFile for the Signature
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> addSubscription(@RequestPart("subscription") Subscription subscription,
			@RequestParam("userId") String userId,
			@RequestPart(value = "signature", required = false) MultipartFile signature) {

		try {
			Subscription createdSubscription = subscriptionService.addSubscription(subscription, userId, signature);
			return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error adding subscription: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== UPDATE SUBSCRIPTION (PUT) ====================
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateSubscription(@PathVariable String id,
			@RequestPart("subscription") Subscription subscription, @RequestParam("userId") String userId,
			@RequestPart(value = "signature", required = false) MultipartFile signature) {

		try {
			Subscription updatedSubscription = subscriptionService.updateSubscription(subscription, userId, id,
					signature);
			return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating subscription: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET ALL SUBSCRIPTIONS ====================
	@GetMapping
	public ResponseEntity<?> getAllSubscriptions() {
		try {
			List<Subscription> subscriptions = subscriptionService.findAll();
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching subscriptions", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET SUBSCRIPTION BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> getSubscriptionById(@PathVariable String id) {
		try {
			Subscription subscription = subscriptionService.findById(id);
			return new ResponseEntity<>(subscription, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching subscription", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET SUBSCRIPTIONS BY COMPANY ID ====================
	@GetMapping("/company/{companyId}")
	public ResponseEntity<?> getSubscriptionsByCompanyId(@PathVariable String companyId) {
		try {
			List<Subscription> subscriptions = subscriptionService.findByCompanyId(companyId);
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching subscriptions by company", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET SUBSCRIPTIONS BY SUBSCRIBER NAME
	// ====================
	@GetMapping("/subscriber/search")
	public ResponseEntity<?> getSubscriptionsBySubscriberName(@RequestParam("name") String subscriberName) {
		try {
			List<Subscription> subscriptions = subscriptionService
					.findBySubscriberNameContainingIgnoreCase(subscriberName);
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching subscriptions by name", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET SUBSCRIPTIONS BY SIGNATURE ID ====================
	@GetMapping("/signature/{signatureId}")
	public ResponseEntity<?> getSubscriptionBySignatureId(@PathVariable String signatureId) {
		try {
			List<Subscription> subscriptions = subscriptionService.findBySignatureId(signatureId);
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching subscriptions by signature", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET SUBSCRIPTIONS BY SHARE RANGE (LTE & GTE)
	// ====================
	@GetMapping("/company/{companyId}/shares")
	public ResponseEntity<?> getSubscriptionsByShareRange(@PathVariable String companyId,
			@RequestParam(value = "lte", required = false) Double lteShares,
			@RequestParam(value = "gte", required = false) Double gteShares) {

		try {
			if (lteShares != null) {
				List<Subscription> subscriptions = subscriptionService.findByCompanyIdAndNumberOfShareLte(companyId,
						lteShares);
				return new ResponseEntity<>(subscriptions, HttpStatus.OK);
			} else if (gteShares != null) {
				List<Subscription> subscriptions = subscriptionService.findByCompanyIdAndNumberOfShareGte(companyId,
						gteShares);
				return new ResponseEntity<>(subscriptions, HttpStatus.OK);
			} else {
				// If no range params provided, just return all for the company
				return getSubscriptionsByCompanyId(companyId);
			}
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching subscriptions by share range",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ====================DELETE SUBSCRIPTION ====================
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubscription(@PathVariable String id, @RequestParam("userId") String userId) {

		try {
			boolean deleted = subscriptionService.removeSubscription(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Subscription deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Subscription could not be deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting subscription: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}