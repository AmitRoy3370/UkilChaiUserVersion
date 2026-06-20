package com.example.demo700.Controllers.AdvocateControllers;

import java.io.InputStream;
import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.ENums.AdvocateSpeciality;

import com.example.demo700.Model.AdvocateModels.Advocate;

import com.example.demo700.Services.AdvocateServices.AdvocateService;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Utils.FileHexConverter;
import com.mongodb.client.gridfs.model.GridFSFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/advocate")
public class AdvocateController {

	@Autowired
	private AdvocateService advocateService;

	@Autowired
	private ImageService imageService;

	// --------------------- ADD ADVOCATE ---------------------
	@PostMapping(value = "/add/{usersId}", consumes = { "multipart/form-data" })
	public ResponseEntity<?> addAdvocate(@PathVariable("usersId") String usersId, @RequestPart("userId") String userId,
			@RequestPart("advocateSpeciality") String advocateSpeciality, @RequestPart("experience") String experience,
			@RequestPart("licenseKey") String licenseKey, @RequestPart("degrees") String degreesJson,
			@RequestPart("workingExperiences") String workJson,
			@RequestPart(value="district", required=false) String district,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			Advocate request = new Advocate();
			request.setUserId(userId);

			ObjectMapper mapper = new ObjectMapper();
			String[] degrees = mapper.readValue(degreesJson, String[].class);
			String[] workingExperiences = mapper.readValue(workJson, String[].class);

			String _advocateSpeciality[] = mapper.readValue(advocateSpeciality, String[].class);

			try {

				Set<AdvocateSpeciality> set = new HashSet<>();

				for (String i : _advocateSpeciality) {

					set.add(AdvocateSpeciality.valueOf(i));

				}

				request.setAdvocateSpeciality(set);

			} catch (Exception e) {

				return ResponseEntity.status(400).body("in correct speciality...");

			}

			try {

				request.setDistrict(district);

			} catch(Exception e) {

			}

			try {

				request.setExperience(Integer.parseInt(experience));

			} catch (Exception e) {

				request.setExperience(0);

			}

			request.setLicenseKey(licenseKey);
			request.setDegrees(degrees);
			request.setWorkingExperiences(workingExperiences);

			Advocate saved = advocateService.addVocate(request, usersId, file);
			return ResponseEntity.ok(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// -------------------- find by id --------------------

	@GetMapping("/{advocateId}")
	public ResponseEntity<?> findByIf(@PathVariable String advocateId) {

		try {

			return ResponseEntity.status(200).body(advocateService.findById(advocateId));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// --------------------- VIEW ALL ---------------------
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.ok(advocateService.seeAllAdvocate());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// --------------------- FIND BY USER ID ---------------------
	@GetMapping("/findByUser/{userId}")
	public ResponseEntity<?> getByUserId(@PathVariable String userId) {
		try {
			return ResponseEntity.ok(advocateService.findByUserId(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// --------------------- SEARCH BY SPECIALITY ---------------------
	@GetMapping("/search/speciality/{speciality}")
	public ResponseEntity<?> getBySpeciality(@PathVariable AdvocateSpeciality speciality) {
		try {
			return ResponseEntity.ok(advocateService.findByAdvocateSpeciality(speciality));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// --------------------- FIND BY LICENSE KEY ---------------------
	@GetMapping("/findByLicense/{licenseKey}")
	public ResponseEntity<?> getByLicenseKey(@PathVariable String licenseKey) {
		try {
			return ResponseEntity.ok(advocateService.findByLicenseKey(licenseKey));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// --------------------- EXPERIENCE FILTER ---------------------
	@GetMapping("/experienceMoreThan/{experience}")
	public ResponseEntity<?> findByExperienceMoreThan(@PathVariable int experience) {
		try {
			return ResponseEntity.ok(advocateService.findByExperienceGreaterThan(experience));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// --------------------- SEARCH BY DEGREE ---------------------
	@GetMapping("/search/degree/{degree}")
	public ResponseEntity<?> searchByDegree(@PathVariable String degree) {
		try {
			return ResponseEntity.ok(advocateService.findByDegreesContainingIgnoreCase(degree));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/find/district/{district}")
	public ResponseEntity<?> findByDistrict(@PathVariable String district) {

		try {

			return ResponseEntity.status(200).body(advocateService.findByDistrict(district));

		} catch(Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}

	}

	// --------------------- SEARCH BY EXPERIENCE TEXT ---------------------
	@GetMapping("/search/experience/{exp}")
	public ResponseEntity<?> searchByExperience(@PathVariable String exp) {
		try {
			return ResponseEntity.ok(advocateService.findByWorkingExperiencesContainingIgnoreCase(exp));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// --------------------- UPDATE ---------------------
	@PutMapping("/update/{advocateId}/{usersId}")
	public ResponseEntity<?> updateAdvocate(@PathVariable String advocateId, @PathVariable String usersId,
			@RequestPart("userId") String userId, @RequestPart("advocateSpeciality") String advocateSpeciality,
			@RequestPart("experience") String experience, @RequestPart("licenseKey") String licenseKey,
			@RequestPart("degrees") String degreesJson, @RequestPart("workingExperiences") String workJson,
			@RequestPart(value="district", required=false) String district,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			Advocate request = new Advocate();
			request.setUserId(userId);

			ObjectMapper mapper = new ObjectMapper();
			String[] degrees = mapper.readValue(degreesJson, String[].class);
			String[] workingExperiences = mapper.readValue(workJson, String[].class);

			String _advocateSpeciality[] = mapper.readValue(advocateSpeciality, String[].class);

			try {

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

			try {

				request.setDistrict(district);

			} catch(Exception e) {

			}

			request.setLicenseKey(licenseKey);
			request.setDegrees(degrees);
			request.setWorkingExperiences(workingExperiences);

			Advocate saved = advocateService.updateAdvocate(request, usersId, advocateId, file);
			return ResponseEntity.ok(saved);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// ------------------------ DOWNLOAD CV
	// -------------------------------------------
	@GetMapping("/cv/{advocateId}")
	public ResponseEntity<?> downloadCv(@PathVariable String advocateId) {

		try {

			AdvocateResponse advocate = advocateService.findByUserId(advocateId);

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

	@GetMapping("/location/{location}")
	public ResponseEntity<?> findByLocation(@PathVariable String location) {

		try {

			return ResponseEntity.status(200).body(advocateService.findByLocation(location));

		} catch (Exception e) {

			return ResponseEntity.status(404).body(e.getMessage());

		}

	}

	// --------------------- DELETE ---------------------
	@DeleteMapping("/delete/{advocateId}/{userId}")
	public ResponseEntity<?> deleteAdvocate(@PathVariable String advocateId, @PathVariable String userId) {
		try {
			boolean deleted = advocateService.deleteAdvocate(userId, advocateId);
			return ResponseEntity.ok(deleted ? "Delete success" : "Delete failed");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
