package com.example.demo700.Controllers.AdvocateControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Services.AdvocateServices.AdvocateJoinRequestService;
import com.example.demo700.Utils.FileHexConverter;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/advocateJoinRequest")
public class AdvocateJoinRequestController {

	@Autowired
	private AdvocateJoinRequestService advocateJoinRequestService;

	// --------------------------------------------------------
	// 1. Add Advocate Join Request
	// --------------------------------------------------------

	// ------------------------ ADD REQUEST ---------------------------------------
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<?> addAdvocate(@RequestPart("userId") String userId,
			@RequestPart("advocateSpeciality") String advocateSpeciality, @RequestPart("experience") String experience,
			@RequestPart("licenseKey") String licenseKey, @RequestPart("degrees") String degreesJson,
			@RequestPart("workingExperiences") String workJson,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		AdvocateJoinRequest request = new AdvocateJoinRequest();
		request.setUserId(userId);

		ObjectMapper mapper = new ObjectMapper();
		String[] degrees = mapper.readValue(degreesJson, String[].class);
		String[] workingExperiences = mapper.readValue(workJson, String[].class);

		try {

			request.setAdvocateSpeciality(com.example.demo700.ENums.AdvocateSpeciality.valueOf(advocateSpeciality).toString());

		} catch (Exception e) {

			return ResponseEntity.status(400).body("in correct speciality...");

		}

		try {

			request.setExperience(Integer.parseInt(experience));

		} catch (Exception e) {

			request.setExperience(0);

		}

		request.setLicenseKey(licenseKey);
		request.setDegrees(degrees);
		request.setWorkingExperiences(workingExperiences);

		try {

			AdvocateJoinRequest saved = advocateJoinRequestService.addVocate(request, userId, file);

			if (saved == null) {

				return ResponseEntity.status(500).body("request is not added...");

			}

			return ResponseEntity.status(201).body(saved);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// --------------------------------------------------------
	// 2. Update Advocate Join Request
	// --------------------------------------------------------

	// ------------------------ UPDATE REQUEST -------------------------------------
	@PutMapping(value = "/{advocateId}", consumes = { "multipart/form-data" })
	public ResponseEntity<?> updateAdvocate(@PathVariable("advocateId") String advocateId,
			@RequestPart("userId") String userId, @RequestPart("advocateSpeciality") String advocateSpeciality,
			@RequestPart("experience") String experience, @RequestPart("licenseKey") String licenseKey,
			@RequestPart("degrees") String degreesJson, @RequestPart("workingExperiences") String workJson,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		AdvocateJoinRequest request = new AdvocateJoinRequest();
		request.setUserId(userId);

		ObjectMapper mapper = new ObjectMapper();
		String[] degrees = mapper.readValue(degreesJson, String[].class);
		String[] workingExperiences = mapper.readValue(workJson, String[].class);

		try {

			request.setAdvocateSpeciality(com.example.demo700.ENums.AdvocateSpeciality.valueOf(advocateSpeciality).toString());

		} catch (Exception e) {

			return ResponseEntity.status(400).body("in correct speciality...");

		}

		try {

			request.setExperience(Integer.parseInt(experience));

		} catch (Exception e) {

		}

		request.setLicenseKey(licenseKey);
		request.setDegrees(degrees);
		request.setWorkingExperiences(workingExperiences);

		try {

			AdvocateJoinRequest updated = advocateJoinRequestService.updateAdvocate(request, userId, advocateId, file);

			return ResponseEntity.status(200).body(updated);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ DELETE REQUEST -------------------------------------
	@DeleteMapping("/delete/{advocateId}")
	public ResponseEntity<?> deleteRequest(@PathVariable String advocateId, @RequestParam("userId") String userId) {

		boolean deleted = advocateJoinRequestService.deleteAdvocate(userId, advocateId);

		return ResponseEntity.ok("Deleted = " + deleted);
	}

	// ----------------------- GET ALL REQUESTS
	// -------------------------------------
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {

		try {

			List<AdvocateJoinRequest> list = advocateJoinRequestService.seeAllAdvocate();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return ResponseEntity.ok(list);

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY USER ID
	// -------------------------------------
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> findByUserId(@PathVariable String userId) {

		try {

			AdvocateJoinRequest user = advocateJoinRequestService.findByUserId(userId);

			if (user == null) {

				throw new Exception();

			}

			return ResponseEntity.ok(user);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY SPECIALITY
	// -----------------------------------
	@GetMapping("/speciality/{speciality}")
	public ResponseEntity<?> findBySpeciality(@PathVariable String speciality) {

		System.out.println("speciality :- " + speciality);

		try {

			return ResponseEntity.ok(advocateJoinRequestService.findByAdvocateSpeciality(speciality));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY LICENSE KEY
	// -----------------------------------
	@GetMapping("/license/{licenseKey}")
	public ResponseEntity<?> findByLicense(@PathVariable String licenseKey) {

		try {

			return ResponseEntity.ok(advocateJoinRequestService.findByLicenseKey(licenseKey));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY EXPERIENCE
	// -------------------------------------
	@GetMapping("/experience/{year}")
	public ResponseEntity<?> findByExperience(@PathVariable int year) {

		try {

			return ResponseEntity.ok(advocateJoinRequestService.findByExperienceGreaterThan(year));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY DEGREE
	// -----------------------------------------
	@GetMapping("/degree/{degree}")
	public ResponseEntity<?> findByDegree(@PathVariable String degree) {

		try {

			return ResponseEntity.ok(advocateJoinRequestService.findByDegreesContainingIgnoreCase(degree));

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ FIND BY WORKING EXPERIENCE
	// -----------------------------
	@GetMapping("/work/{experience}")
	public ResponseEntity<?> findByWorkExp(@PathVariable String experience) {

		try {

			List<AdvocateJoinRequest> list = advocateJoinRequestService
					.findByWorkingExperiencesContainingIgnoreCase(experience);

			return ResponseEntity.ok(list);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}
	}

	// ------------------------ DOWNLOAD CV
	// -------------------------------------------
	@GetMapping("/cv/{advocateId}")
	public ResponseEntity<?> downloadCv(@PathVariable String advocateId) {

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestService.findByUserId(advocateId);

			if (advocate.getCvHexKey() == null)
				return ResponseEntity.badRequest().body("No CV uploaded");

			byte[] fileBytes = FileHexConverter.hexToFile(advocate.getCvHexKey());

			String fileName = "advocate_cv.pdf"; // Or .doc/.docx if needed

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.contentType(MediaType.APPLICATION_PDF).body(fileBytes);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

}
