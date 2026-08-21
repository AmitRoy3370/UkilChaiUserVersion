package com.example.demo700.Controllers.UserControllers;

import java.util.List;

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

import com.example.demo700.DTOFiles.CompanyResponse;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Services.UserServices.CompanyInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/company")
public class CompanyInformationController {

	@Autowired
	private CompanyInformationService companyInformationService;

	@Autowired
	private ObjectMapper objectMapper;

	// ==========================================
	// 1. CREATE NEW COMPANY
	// ==========================================
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createCompany(@RequestParam("userId") String userId,
			@RequestParam("companyData") String companyDataJson,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		try {
			CompanyInformation companyInformation = objectMapper.readValue(companyDataJson, CompanyInformation.class);
			CompanyInformation savedInfo = companyInformationService.addCompanyInformation(companyInformation, userId,
					files);
			return new ResponseEntity<>(savedInfo, HttpStatus.CREATED);

		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Error creating company: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ==========================================
	// 2. UPDATE COMPANY BY ID
	// ==========================================
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateCompany(@PathVariable String id, @RequestParam("userId") String userId,
			@RequestParam("companyData") String companyDataJson,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		try {
			CompanyInformation companyInformation = objectMapper.readValue(companyDataJson, CompanyInformation.class);
			CompanyInformation updatedInfo = companyInformationService.updateCompanyInformation(companyInformation, id,
					userId, files);
			return new ResponseEntity<>(updatedInfo, HttpStatus.OK);

		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating company: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addDirector")
	public ResponseEntity<?> addDirector(@RequestParam String id, @RequestParam String directorId,
			@RequestParam String userId) {

		try {

			return ResponseEntity.status(200).body(addDirector(id, directorId, userId));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	@PostMapping("/addShareholder")
	public ResponseEntity<?> addShareholder(@RequestParam String id, @RequestParam String holderId,
			@RequestParam String userId) {

		try {

			return ResponseEntity.status(200).body(addShareholder(id, holderId, userId));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ==========================================
	// 3. GET ALL COMPANY INFORMATION
	// ==========================================
	@GetMapping("/all")
	public ResponseEntity<?> getAllCompanies() {
		try {
			List<CompanyResponse> list = companyInformationService.findAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ==========================================
	// 4. GET SINGLE COMPANY BY ID
	// ==========================================
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable String id) {
		try {
			CompanyResponse info = companyInformationService.findById(id);
			return new ResponseEntity<>(info, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ==========================================
	// 5. DELETE COMPANY
	// ==========================================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable String id, @RequestParam("userId") String userId) {
		try {
			boolean deleted = companyInformationService.deleteCompanyInformation(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Company could not be deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
	}

	// ==========================================
	// 6. SEPARATE GET APIs FOR EACH SEARCH FIELD
	// ==========================================

	@GetMapping("/search/by-company-name")
	public ResponseEntity<?> getByCompanyName(@RequestParam("companyName") String companyName) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByCompanyNameContainingIgnoreCase(companyName);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// ==========================================
	// 7. DELETE COMPANY
	// ==========================================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable String id, @RequestParam("userId") String userId) {
		try {
			boolean deleted = companyInformationService.deleteCompanyInformation(id, userId);
			if (deleted) {
				return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Company could not be deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ArithmeticException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/search/by-type")
	public ResponseEntity<?> getByType(@RequestParam("type") String type) {
		try {
			List<CompanyResponse> result = companyInformationService.findByTypeContainingIgnoreCase(type);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-nature-of-business")
	public ResponseEntity<?> getByNatureOfBusiness(@RequestParam("natureOfBuisness") String natureOfBuisness) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByNatureOfBuisnessContainingIgnoreCase(natureOfBuisness);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-category")
	public ResponseEntity<?> getByCategory(@RequestParam("category") String category) {
		try {
			List<CompanyResponse> result = companyInformationService.findByCategoryContainingIgnoreCase(category);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-office-registry")
	public ResponseEntity<?> getByOfficeRegistryId(@RequestParam("officeRegistryId") String officeRegistryId) {
		try {
			List<CompanyResponse> result = companyInformationService.findByOfficeRegistryId(officeRegistryId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-shareholder-id")
	public ResponseEntity<?> getByShareHolderId(@RequestParam("shareHoldersId") String shareHoldersId) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByShareHoldersContainingIgnoreCase(shareHoldersId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-document-id")
	public ResponseEntity<?> getByDocumentId(@RequestParam("documentsId") String documentsId) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByDocumentsContainingIgnoreCase(documentsId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-director-id")
	public ResponseEntity<?> getByDirectorId(@RequestParam("directorsId") String directorsId) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByDirectorsIdContainingIgnoreCase(directorsId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-authorized")
	public ResponseEntity<?> getByAuthorized(@RequestParam("authorized") String authorized) {
		try {
			List<CompanyResponse> result = companyInformationService
					.findByAuthorizedContainingIgnoreCase(authorized);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/by-capital")
	public ResponseEntity<?> getByCapital(@RequestParam("capital") String capital) {
		try {
			List<CompanyResponse> result = companyInformationService.findByCapital(capital);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
