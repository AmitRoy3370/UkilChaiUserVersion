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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.Shareholder;
import com.example.demo700.Services.UserServices.ShareholderService;

@RestController
@RequestMapping("/api/shareholders")
public class ShareholderController {

	@Autowired
	private ShareholderService shareholderService;

	// ==================== ADD SHAREHOLDER ====================
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> addShareholder(@RequestPart("shareholder") Shareholder shareholder,
			@RequestParam("userId") String userId, @RequestPart(value = "nid", required = false) MultipartFile nid,
			@RequestPart(value = "tin", required = false) MultipartFile tin) {

		try {
			Shareholder created = shareholderService.addShareholder(shareholder, userId, nid, tin);
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error adding shareholder: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== UPDATE SHAREHOLDER ====================
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateShareholder(@PathVariable String id,
			@RequestPart("shareholder") Shareholder shareholder, @RequestParam("userId") String userId,
			@RequestPart(value = "nid", required = false) MultipartFile nid,
			@RequestPart(value = "tin", required = false) MultipartFile tin) {

		try {
			Shareholder updated = shareholderService.updateShareholder(shareholder, userId, id, nid, tin);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating shareholder: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SHARE PROFIT (ADD PERCENTAGE TO COMPANY)
	// ====================
	@PostMapping("/share-profit")
	public ResponseEntity<?> shareProfit(@RequestParam String companyId, @RequestParam Double percentage,
			@RequestParam String holderId, @RequestParam String userId) {

		try {
			Shareholder updated = shareholderService.shareProfit(companyId, percentage, holderId, userId);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error sharing profit: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET ALL SHAREHOLDERS ====================
	@GetMapping
	public ResponseEntity<?> getAllShareholders() {
		try {
			List<Shareholder> list = shareholderService.findAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching shareholders", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> getShareholderById(@PathVariable String id) {
		try {
			Shareholder holder = shareholderService.findById(id);
			return new ResponseEntity<>(holder, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching shareholder", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET BY USER ID ====================
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getShareholderByUserId(@PathVariable String userId) {
		try {
			Shareholder holder = shareholderService.findByUserId(userId);
			return new ResponseEntity<>(holder, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching shareholder by user ID", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET BY NID ====================
	@GetMapping("/nid/{nid}")
	public ResponseEntity<?> getShareholdersByNid(@PathVariable String nid) {
		try {
			List<Shareholder> list = shareholderService.findByNid(nid);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching shareholders by NID", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET BY TIN ====================
	@GetMapping("/tin/{tin}")
	public ResponseEntity<?> getShareholdersByTin(@PathVariable String tin) {
		try {
			List<Shareholder> list = shareholderService.findByTin(tin);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching shareholders by TIN", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== ADVANCED SEARCH (COMPANY ID) ====================
	@GetMapping("/search/company/{companyId}")
	public ResponseEntity<?> getShareholdersByCompanyId(@PathVariable String companyId) {
		try {
			List<Shareholder> list = shareholderService.findByShareCompanyId(companyId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching by company ID", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== ADVANCED SEARCH (EXACT PERCENTAGE) ====================
	@GetMapping("/search/company/{companyId}/percentage/{percentage}")
	public ResponseEntity<?> getShareholdersByCompanyAndPercentage(@PathVariable String companyId,
			@PathVariable Double percentage) {
		try {
			List<Shareholder> list = shareholderService.findByShareCompanyIdAndPercentage(companyId, percentage);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching by company and exact percentage",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== ADVANCED SEARCH (GREATER THAN OR EQUAL)
	// ====================
	@GetMapping("/search/company/{companyId}/gte/{percentage}")
	public ResponseEntity<?> getShareholdersByCompanyAndPercentageGte(@PathVariable String companyId,
			@PathVariable Double percentage) {
		try {
			List<Shareholder> list = shareholderService.findByShareCompanyIdAndPercentageGte(companyId, percentage);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching by company and >= percentage",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== ADVANCED SEARCH (LESS THAN OR EQUAL)
	// ====================
	@GetMapping("/search/company/{companyId}/lte/{percentage}")
	public ResponseEntity<?> getShareholdersByCompanyAndPercentageLte(@PathVariable String companyId,
			@PathVariable Double percentage) {
		try {
			List<Shareholder> list = shareholderService.findByShareCompanyIdAndPercentageLte(companyId, percentage);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching by company and <= percentage",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== ADVANCED SEARCH (BETWEEN) ====================
	@GetMapping("/search/company/{companyId}/between")
	public ResponseEntity<?> getShareholdersByCompanyAndPercentageBetween(@PathVariable String companyId,
			@RequestParam Double minPercentage, @RequestParam Double maxPercentage) {
		try {
			List<Shareholder> list = shareholderService.findByShareCompanyIdAndPercentageBetween(companyId,
					minPercentage, maxPercentage);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching by company and between percentages",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== DELETE SHAREHOLDER ====================
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteShareholder(@PathVariable String id, @RequestParam String userId) {

		try {
			boolean deleted = shareholderService.removeShareholder(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Shareholder deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Shareholder could not be deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting shareholder: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}