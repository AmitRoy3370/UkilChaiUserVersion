package com.example.demo700.Services.AdvocateServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;

import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Utils.FileHexConverter;

@Service
public class AdvocateServiceImpl implements AdvocateService {

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private UserContactInfoRepository contactInfoRepository;

	@Autowired
	private UserLocationRepository locationRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private CVUploadService cvUpload;

	@Override
	public Advocate addVocate(Advocate advocate, String userId, MultipartFile file) {

		if (userId == null || advocate == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Advocate _advocate = advocateRepository.findByUserId(userId);

			if (_advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already approved as the advocate...");

		} catch (Exception e) {

		}

		try {

			Advocate _advocate = advocateRepository.findByLicenseKey(advocate.getLicenseKey());

			if (_advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			if (file != null && !file.isEmpty()) {

				String hex = cvUpload.uploadCv(file);

				advocate.setCvHexKey(hex);

			}

		} catch (Exception e) {

		}

		advocate = advocateRepository.save(advocate);

		return advocate;
	}

	@Override
	public List<AdvocateResponse> seeAllAdvocate() {

		return getAdvocateResponseFromAdvocateList(advocateRepository.findAll());
	}

	@Override
	public AdvocateResponse findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			return getAdvocateResponse(advocate);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

	}

	@Override
	public List<AdvocateResponse> findByAdvocateSpeciality(AdvocateSpeciality advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByAdvocateSpeciality(advocateSpeciality);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateResponseFromAdvocateList(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public AdvocateResponse findByLicenseKey(String licenseKey) {

		if (licenseKey == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate advocate = advocateRepository.findByLicenseKey(licenseKey);

			if (advocate == null) {

				throw new Exception();

			}

			return getAdvocateResponse(advocate);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<AdvocateResponse> findByExperienceGreaterThan(int experience) {

		try {

			List<Advocate> list = advocateRepository.findByExperienceGreaterThan(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateResponseFromAdvocateList(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<AdvocateResponse> findByDegreesContainingIgnoreCase(String degree) {

		if (degree == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByDegreesContainingIgnoreCase(degree);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateResponseFromAdvocateList(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<AdvocateResponse> findByWorkingExperiencesContainingIgnoreCase(String experience) {

		if (experience == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByWorkingExperiencesContainingIgnoreCase(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateResponseFromAdvocateList(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public Advocate updateAdvocate(Advocate advocate, String userId, String advocateId, MultipartFile file) {

		if (userId == null || advocate == null || advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate _advocate = advocateRepository.findById(advocateId).get();

			if (_advocate == null) {

				throw new Exception();

			}

			if (!_advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Advocate _advocate = advocateRepository.findByUserId(userId);

			if (_advocate != null) {

				if (!_advocate.getId().equals(advocateId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already approved as the advocate...");

		} catch (Exception e) {

		}

		try {

			Advocate _advocate = advocateRepository.findByLicenseKey(advocate.getLicenseKey());

			if (_advocate != null) {

				if (!_advocate.getId().equals(advocateId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			if (file != null && !file.isEmpty()) {

				String hex = cvUpload.uploadCv(file);

				advocate.setCvHexKey(hex);

			}

		} catch (Exception e) {

		}

		try {

			if (file != null && !file.isEmpty()) {

				Advocate _advocate = advocateRepository.findById(advocateId).get();

				if (_advocate == null) {

					throw new Exception();

				}

				if (!advocate.getCvHexKey().isEmpty()) {

					cvUpload.deleteCV(advocate.getCvHexKey());

				}

			}

		} catch (Exception e) {

		}

		advocate.setId(advocateId);

		advocate = advocateRepository.save(advocate);

		return advocate;
	}

	@Override
	public boolean deleteAdvocate(String userId, String advocateId) {

		if (userId == null || advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate advocate = advocateRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(user.getId())) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = advocateRepository.count();

					cleaner.removeAdvocate(advocateId);

					return count != advocateRepository.count();

				}

			} catch (Exception e) {

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		long count = advocateRepository.count();

		cleaner.removeAdvocate(advocateId);

		return count != advocateRepository.count();
	}

	@Override
	public AdvocateResponse findById(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Advocate advocate = advocateRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

			return getAdvocateResponse(advocate);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here....");

		}

	}

	public List<AdvocateResponse> findByLocation(String location) {

		if (location == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<UserLocation> list = locationRepository.findByLocationNameContainingIgnoreCase(location);

			if (list.isEmpty()) {

				throw new Exception();

			}

			List<String> allUserId = list.stream().map(UserLocation::getUserId).collect(Collectors.toList());

			List<Advocate> advocates = advocateRepository.findByUserIdIn(allUserId);

			return getAdvocateResponseFromAdvocateList(advocates);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate present at here...");

		}

	}

	private AdvocateResponse getAdvocateResponse(Advocate advocate) {

		List<Advocate> advocateInfo = new ArrayList<>();

		advocateInfo.add(advocate);

		return getAdvocateResponseFromAdvocateList(advocateInfo).get(0);

	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private List<AdvocateResponse> getAdvocateResponseFromAdvocateList(List<Advocate> advocates) {

		List<AdvocateResponse> responses = new ArrayList<>();

		List<String> allUserId = advocates.stream().map(Advocate::getUserId).collect(Collectors.toList());

		CompletableFuture<Map<String, User>> nameFuture = CompletableFuture
				.supplyAsync(
						() -> userRepository.findAllById(allUserId)
								.isEmpty()
										? new HashMap<>()
										: userRepository.findAllById(allUserId).stream().collect(Collectors.toMap(
												User::getId, Function.identity(), (existing, replacement) -> existing)),
						executor);
		;

		CompletableFuture<Map<String, UserContactInfo>> contactInfoFuture = CompletableFuture.supplyAsync(
				() -> contactInfoRepository.findByUserIdIn(allUserId).isEmpty() ? new HashMap<>()
						: contactInfoRepository.findByUserIdIn(allUserId).stream().collect(Collectors.toMap(
								UserContactInfo::getUserId, Function.identity(), (existing, replacement) -> existing)),
				executor);

		CompletableFuture<Map<String, UserLocation>> locationFuture = CompletableFuture.supplyAsync(
				() -> locationRepository.findByUserIdIn(allUserId).isEmpty() ? new HashMap<>()
						: locationRepository.findByUserIdIn(allUserId).stream().collect(Collectors.toMap(
								UserLocation::getUserId, Function.identity(), (existing, replacement) -> existing)),
				executor);

		CompletableFuture.allOf(nameFuture, contactInfoFuture, locationFuture).join();

		Map<String, User> userMap = nameFuture.join();
		Map<String, UserContactInfo> contactMap = contactInfoFuture.join();
		Map<String, UserLocation> locationMap = locationFuture.join();

		for (Advocate advocate : advocates) {

			AdvocateResponse response = new AdvocateResponse();

			try {

				response.setId(advocate.getId());
				response.setAdvocateSpeciality(advocate.getAdvocateSpeciality());
				response.setDegrees(advocate.getDegrees());
				response.setWorkingExperiences(advocate.getWorkingExperiences());
				response.setExperience(advocate.getExperience());
				response.setCvHexKey(advocate.getCvHexKey());
				response.setLicenseKey(advocate.getLicenseKey());
				response.setUserId(advocate.getUserId());

			} catch (Exception e) {

			}

			try {

				response.setName(userMap.get(advocate.getUserId()).getName());
				response.setProfileImageId(userMap.get(advocate.getUserId()).getProfileImageId());

			} catch (Exception e) {

			}

			try {

				response.setContactInfoId(contactMap.get(advocate.getUserId()).getId());
				response.setEmail(contactMap.get(advocate.getUserId()).getEmail());
				response.setPhone(contactMap.get(advocate.getUserId()).getPhone());

			} catch (Exception e) {

			}

			try {

				response.setLocationId(locationMap.get(advocate.getUserId()).getId());
				response.setLocationName(locationMap.get(advocate.getUserId()).getLocationName());
				response.setLattitude(locationMap.get(advocate.getUserId()).getLattitude());
				response.setLongitude(locationMap.get(advocate.getUserId()).getLongitude());

			} catch (Exception e) {

			}

			responses.add(response);

		}

		return responses;

	}

}
