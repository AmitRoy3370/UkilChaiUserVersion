package com.example.demo700.Controllers.AdvocateControllers;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Services.AdvocateServices.AdvocateJoinRequestService;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Utils.FileHexConverter;
import com.mongodb.client.gridfs.model.GridFSFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/advocateJoinRequest")
public class AdvocateJoinRequestController {

	@Autowired
	private AdvocateJoinRequestService advocateJoinRequestService;

	@Autowired
	private ImageService imageService;

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

		try {

			String[] degrees = mapper.readValue(degreesJson, String[].class);

			request.setDegrees(degrees);

		} catch (Exception e) {

		}

		try {

			String[] workingExperiences = mapper.readValue(workJson, String[].class);

			request.setWorkingExperiences(workingExperiences);

		} catch (Exception e) {

		}

		try {

			String _advocateSpeciality[] = mapper.readValue(advocateSpeciality, String[].class);

			Set<AdvocateSpeciality> set = new HashSet<>();

			for (String i : _advocateSpeciality) {

				set.add(AdvocateSpeciality.valueOf(i));

			}

			request.setAdvocateSpeciality(set);

		} catch (Exception e) {

			return ResponseEntity.status(400).body("in correct speciality...");

		}

		try {

			request.setExperience(Integer.parseInt(experience));

		} catch (Exception e) {

			request.setExperience(0);

		}

		request.setLicenseKey(licenseKey);
		// request.setDegrees(degrees);
		// request.setWorkingExperiences(workingExperiences);

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

		try {

			String[] degrees = mapper.readValue(degreesJson, String[].class);

			request.setDegrees(degrees);

		} catch (Exception e) {

		}

		try {

			String[] workingExperiences = mapper.readValue(workJson, String[].class);

			request.setWorkingExperiences(workingExperiences);

		} catch (Exception e) {

		}

		try {

			String[] specialities = mapper.readValue(advocateSpeciality, String[].class);

			Set<AdvocateSpeciality> set = new HashSet<>();

			for (String i : specialities) {

				set.add(AdvocateSpeciality.valueOf(i));

			}

			request.setAdvocateSpeciality(set);

		} catch (Exception e) {

			return ResponseEntity.status(400).body("in correct speciality...");

		}

		try {

			request.setExperience(Integer.parseInt(experience));

		} catch (Exception e) {

		}

		request.setLicenseKey(licenseKey);
		// request.setDegrees(degrees);
		//request.setWorkingExperiences(workingExperiences);

		try {

			AdvocateJoinRequest updated = advocateJoinRequestService.updateAdvocate(request, userId, advocateId, file);

			return ResponseEntity.status(200).body(updated);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ----------------------- handle advocate join request-------------------------

	@PutMapping("/handleJoinRequest")
	public ResponseEntity<?> handleAdvocateJoinRequest(@RequestParam String userId,
			@RequestParam String advocateJoinRequestId) {

		try {

			Advocate advocate = advocateJoinRequestService.handleJoinRequest(userId, advocateJoinRequestId);

			if (advocate == null) {

				return ResponseEntity.status(500).body("request is not accepted...");

			}

			return ResponseEntity.status(200).body(advocate);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// ------------------------ DELETE REQUEST -------------------------------------
	@DeleteMapping("/delete/{advocateId}")
	public ResponseEntity<?> deleteRequest(@PathVariable String advocateId, @RequestParam("userId") String userId) {

		try {

			boolean deleted = advocateJoinRequestService.deleteAdvocate(userId, advocateId);

			return ResponseEntity.ok("Deleted = " + deleted);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

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

			String attachmentId = advocate.getCvHexKey();

			GridFSFile file = imageService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
			}

			InputStream stream = imageService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));
		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

	// -------------- view the attachment ---------------------

	@GetMapping("/attachment/view/{attachmentId}")
	public ResponseEntity<?> viewAttachment(@PathVariable String attachmentId) {
		try {
			GridFSFile file = imageService.getFile(attachmentId);

			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
			}

			InputStream stream = imageService.getStream(file);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMetadata().get("type").toString()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
					.body(new InputStreamResource(stream));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load file");
		}
	}

}
