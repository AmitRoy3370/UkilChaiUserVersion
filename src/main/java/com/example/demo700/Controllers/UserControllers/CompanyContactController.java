package com.example.demo700.Controllers.UserControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo700.Model.UserModels.CompanyContact;
import com.example.demo700.Services.UserServices.CompanyContactService;

@RestController
@RequestMapping("/api/company-contacts")
public class CompanyContactController {

	@Autowired
	private CompanyContactService companyContactService;

	// ==================== ADD COMPANY CONTACT (POST) ====================
	@PostMapping("/add")
	public ResponseEntity<?> addCompanyContact(@RequestBody CompanyContact companyContact,
			@RequestParam("userId") String userId) {

		try {
			CompanyContact created = companyContactService.addCompanyContact(companyContact, userId);
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error adding company contact: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== UPDATE COMPANY CONTACT (PUT) ====================
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCompanyContact(@PathVariable String id, @RequestBody CompanyContact companyContact,
			@RequestParam("userId") String userId) {

		try {
			CompanyContact updated = companyContactService.updateCompanyContact(companyContact, userId, id);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating company contact: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET ALL COMPANY CONTACTS ====================
	@GetMapping("/all")
	public ResponseEntity<?> getAllCompanyContacts() {
		try {
			List<CompanyContact> list = companyContactService.findAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching contacts", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET COMPANY CONTACT BY ID ====================
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompanyContactById(@PathVariable String id) {
		try {
			CompanyContact contact = companyContactService.findById(id);
			return new ResponseEntity<>(contact, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching contact", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== GET CONTACTS BY COMPANY ID ====================
	@GetMapping("/company/{companyId}")
	public ResponseEntity<?> getContactsByCompanyId(@PathVariable String companyId) {
		try {
			List<CompanyContact> list = companyContactService.findByCompanyId(companyId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching contacts by company", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SEARCH CONTACTS BY NAME ====================
	@GetMapping("/search/name")
	public ResponseEntity<?> searchContactsByName(@RequestParam("name") String name) {
		try {
			List<CompanyContact> list = companyContactService.findByContactPersonNameContainingIgnoreCase(name);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching contacts by name", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SEARCH CONTACTS BY MOBILE ====================
	@GetMapping("/search/mobile")
	public ResponseEntity<?> searchContactsByMobile(@RequestParam("mobile") String mobile) {
		try {
			List<CompanyContact> list = companyContactService.findByContactPersonMobile(mobile);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching contacts by mobile", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SEARCH CONTACTS BY EMAIL ====================
	@GetMapping("/search/email")
	public ResponseEntity<?> searchContactsByEmail(@RequestParam("email") String email) {
		try {
			List<CompanyContact> list = companyContactService.findByContactPersonEmail(email);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching contacts by email", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SEARCH CONTACTS BY "HOW DID HEAR" ====================
	@GetMapping("/search/how-did-hear")
	public ResponseEntity<?> searchContactsByHowDidHear(@RequestParam("query") String query) {
		try {
			List<CompanyContact> list = companyContactService.findByHowDidHear(query);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching contacts by source", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== SEARCH CONTACTS BY MESSAGE ====================
	@GetMapping("/search/message")
	public ResponseEntity<?> searchContactsByMessage(@RequestParam("message") String message) {
		try {
			List<CompanyContact> list = companyContactService.findByAnyOtherMessageContainingIgnoreCase(message);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error searching contacts by message", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ==================== DELETE COMPANY CONTACT (DELETE) ====================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCompanyContact(@PathVariable String id, @RequestParam("userId") String userId) {

		try {
			boolean deleted = companyContactService.deleteCompanyContact(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Company contact deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Could not delete contact", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException e) {
			return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting contact: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}