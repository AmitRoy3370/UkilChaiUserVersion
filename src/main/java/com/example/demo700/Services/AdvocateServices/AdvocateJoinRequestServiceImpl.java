package com.example.demo700.Services.AdvocateServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.AdvocateJoinRequestDTO;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;
import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfoJoinRequest;
import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;
import com.example.demo700.Model.AdvocateModels.AdvocateDocumentsJoinRequest;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperienceJoinRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateAdditionalDocumentsRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateAdditionalInfoJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateDocumentsJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateDocumentsRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateWorkingExperienceInJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateWorkingExperienceRepository;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.NotificationServices.NotificationService;

@Service
public class AdvocateJoinRequestServiceImpl implements AdvocateJoinRequestService {

	@Autowired
	private AdvocateJoinRequestRepository advocateJoinRequestRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateWorkingExperienceRepository workingExperienceRepository;

	@Autowired
	private AdvocateDocumentsRepository documentsRepository;

	@Autowired
	private AdvocateAdditionalDocumentsRepository additionalInfoRepository;

	@Autowired
	private AdvocateWorkingExperienceInJoinRequestRepository workingExperienceInJoinRequestRepository;

	@Autowired
	private AdvocateDocumentsJoinRequestRepository documentsInJoinRequestRepository;

	@Autowired
	private AdvocateAdditionalInfoJoinRequestRepository additionalInfoInJoinRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserContactInfoRepository userContactInfoRepository;

	@Autowired
	private UserLocationRepository userLocationRepository;

	@Autowired
	private CVUploadService cvUpload;

	@Autowired
	private CenterAdminRepository centerAdminRepositroy;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "AdvocateJoinRequest";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public AdvocateJoinRequest addVocate(AdvocateJoinRequest advocateJoinRequest, String userId, MultipartFile file) {

		if (advocateJoinRequest == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(advocateJoinRequest.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(userId)) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can send request for only yourself....");

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already approved as the advocate...");

		} catch (Exception e) {

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByUserId(userId);

			if (advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already requested to join as the advocate...");

		} catch (Exception e) {

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository
					.findByLicenseKey(advocateJoinRequest.getLicenseKey());

			if (advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			Advocate advocate = advocateRepository.findByLicenseKey(advocateJoinRequest.getLicenseKey());

			if (advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			if (file != null && !file.isEmpty()) {

				String hex = cvUpload.upload(file);

				advocateJoinRequest.setCvHexKey(hex);

			}

		} catch (Exception e) {

			throw new ArithmeticException("cv is not uploaded...." + e.getMessage());

		}

		advocateJoinRequest = advocateJoinRequestRepository.save(advocateJoinRequest);

		return advocateJoinRequest;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'SeeAll'")
	public List<AdvocateJoinRequestDTO> seeAllAdvocate() {

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate request at here...");

		}

		return getAdvocateJoinRequestResponse(list);
	}

	@Override
	@Cacheable(value = cacheValue, key = "#userId")
	public AdvocateJoinRequestDTO findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			return getAdvocateJoinRequestResponse(advocate);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "#advocateSpeciality")
	public List<AdvocateJoinRequestDTO> findByAdvocateSpeciality(String advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateSpeciality speciality = AdvocateSpeciality.valueOf(advocateSpeciality);

			if (speciality == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("In correct speciality request...");

		}

		try {

			System.out.println("advocate scpeciality at service :- " + advocateSpeciality);

			com.example.demo700.ENums.AdvocateSpeciality speciality = com.example.demo700.ENums.AdvocateSpeciality
					.valueOf(advocateSpeciality);

			System.out.println("find at :- " + speciality.toString());

			List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByAdvocateSpeciality(speciality);

			if (list.isEmpty()) {

				throw new NoSuchElementException("No such request find at here...");

			}

			return getAdvocateJoinRequestResponse(list);

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "#licenseKey")
	public AdvocateJoinRequestDTO findByLicenseKey(String licenseKey) {

		if (licenseKey == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByLicenseKey(licenseKey);

			if (advocate == null) {

				throw new Exception();

			}

			return getAdvocateJoinRequestResponse(advocate);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate request find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'experienceGreaterThan_' + #experience")
	public List<AdvocateJoinRequestDTO> findByExperienceGreaterThan(int experience) {

		try {

			List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByExperienceGreaterThan(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateJoinRequestResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "#degree")
	public List<AdvocateJoinRequestDTO> findByDegreesContainingIgnoreCase(String degree) {

		if (degree == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByDegreesContainingIgnoreCase(degree);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		return getAdvocateJoinRequestResponse(list);
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByWorkingExperience_' + #experience")
	public List<AdvocateJoinRequestDTO> findByWorkingExperiencesContainingIgnoreCase(String experience) {
		if (experience == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository
				.findByWorkingExperiencesContainingIgnoreCase(experience);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		return getAdvocateJoinRequestResponse(list);
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public AdvocateJoinRequest updateAdvocate(AdvocateJoinRequest advocateJoinRequest, String userId, String advocateId,
			MultipartFile file) {

		if (advocateJoinRequest == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advoacte request find at here...");

		}

		try {

			User user = userRepository.findById(advocateJoinRequest.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(userId)) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can send request for only yourself....");

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already approved as the advocate...");

		} catch (Exception e) {

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByUserId(userId);

			if (advocate != null) {

				if (!advocate.getId().equals(advocateId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already requested to join as the advocate...");

		} catch (Exception e) {

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository
					.findByLicenseKey(advocateJoinRequest.getLicenseKey());

			if (advocate != null) {

				if (!advocate.getId().equals(advocateId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			Advocate advocate = advocateRepository.findByLicenseKey(advocateJoinRequest.getLicenseKey());

			if (advocate != null) {

				if (!advocate.getId().equals(advocateId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Two advocates license id can't be the same...");

		} catch (Exception e) {

		}

		try {

			if (file != null && !file.isEmpty()) {

				String hex = cvUpload.upload(file);

				advocateJoinRequest.setCvHexKey(hex);

			}

		} catch (Exception e) {

			throw new ArithmeticException("cv is not uploaded...." + e.getMessage());

		}

		try {

			if (file != null && !file.isEmpty()) {

				AdvocateJoinRequest advocate = advocateJoinRequestRepository.findById(advocateId).get();

				if (advocate == null) {

					throw new Exception();

				}

				if (!advocate.getCvHexKey().isEmpty()) {

					cvUpload.delete(advocate.getCvHexKey());

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advoacte request find at here...");

		}

		advocateJoinRequest.setId(advocateId);

		advocateJoinRequest = advocateJoinRequestRepository.save(advocateJoinRequest);

		return advocateJoinRequest;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDistrict_' + #district")
	public List<AdvocateJoinRequestDTO> findByDistrict(String district) {

		if (district == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByDistrictContainingIgnoreCase(district);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdvocateJoinRequestResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate request find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Admin", allEntries = true), @CacheEvict(value = "Advocate", allEntries = true),
			@CacheEvict(value = "AdvocateRating", allEntries = true),
			@CacheEvict(value = "AdvocatePost", allEntries = true),
			@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true), @CacheEvict(value = "CaseClose", allEntries = true),
			@CacheEvict(value = "CaseJudgement", allEntries = true), @CacheEvict(value = "Case", allEntries = true),
			@CacheEvict(value = "CaseTracking", allEntries = true),
			@CacheEvict(value = "DocumentDraft", allEntries = true), @CacheEvict(value = "Hearing", allEntries = true),
			@CacheEvict(value = "ReadStatus", allEntries = true), @CacheEvict(value = "Answer", allEntries = true),
			@CacheEvict(value = "Question", allEntries = true), })
	public boolean deleteAdvocate(String userId, String advocateId) {

		if (userId == null || advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepositroy.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = advocateJoinRequestRepository.count();

					cleaner.removeAdvocateJoinRequest(advocateId);

					return count != advocateJoinRequestRepository.count();

				}

			} catch (Exception e) {

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate request find at here...");

		}

		long count = advocateJoinRequestRepository.count();

		cleaner.removeAdvocateJoinRequest(advocateId);

		return count != advocateJoinRequestRepository.count();
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Admin", allEntries = true), @CacheEvict(value = "Advocate", allEntries = true),
			@CacheEvict(value = "AdvocateAdditionalInfoInJoinRequest", allEntries = true),
			@CacheEvict(value = "AdvocateAdditionalInfo", allEntries = true),
			@CacheEvict(value = "AdvocateWorkingExperienceInJoinRequest", allEntries = true),
			@CacheEvict(value = "AdvocateWorkingExperience", allEntries = true),
			@CacheEvict(value = "AdvocateDocumentsInJoinRequest", allEntries = true),
			@CacheEvict(value = "AdvocateDocuments", allEntries = true),
			@CacheEvict(value = "AdvocateRating", allEntries = true),
			@CacheEvict(value = "AdvocatePost", allEntries = true),
			@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true), @CacheEvict(value = "CaseClose", allEntries = true),
			@CacheEvict(value = "CaseJudgement", allEntries = true), @CacheEvict(value = "Case", allEntries = true),
			@CacheEvict(value = "CaseTracking", allEntries = true),
			@CacheEvict(value = "DocumentDraft", allEntries = true), @CacheEvict(value = "Hearing", allEntries = true),
			@CacheEvict(value = "ReadStatus", allEntries = true), @CacheEvict(value = "Answer", allEntries = true),
			@CacheEvict(value = "Question", allEntries = true), })
	public Advocate handleJoinRequest(String userId, String advocateJoinRequestId) {

		if (userId == null || advocateJoinRequestId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findById(advocateJoinRequestId).get();

			if (advocate == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Admin admin = adminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			for (AdvocateSpeciality i : advocate.getAdvocateSpeciality()) {

				if (!admin.getAdvocateSpeciality().contains(i)) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid credential...");

		}

		Advocate advocate = new Advocate();

		AdvocateJoinRequest advocateJoinRequest = advocateJoinRequestRepository.findById(advocateJoinRequestId).get();

		advocate.setAdvocateSpeciality(advocateJoinRequest.getAdvocateSpeciality());
		advocate.setCvHexKey(advocateJoinRequest.getCvHexKey());
		advocate.setDegrees(advocateJoinRequest.getDegrees());
		advocate.setExperience(advocateJoinRequest.getExperience());
		advocate.setLicenseKey(advocateJoinRequest.getLicenseKey());
		advocate.setDistrict(advocateJoinRequest.getDistrict());
		advocate.setUserId(advocateJoinRequest.getUserId());
		advocate.setWorkingExperiences(advocateJoinRequest.getWorkingExperiences());
		advocate.setId(advocateJoinRequest.getId());

		try {

			advocate = advocateRepository.save(advocate);

			if (advocate != null) {

				System.out.println("advocate :- " + advocate.toString());

				Admin admin = adminRepository.findByUserId(userId);

				System.out.println("admin :- " + admin.toString());

				CenterAdmin centerAdmin = centerAdminRepositroy.findByAdminsContainingIgnoreCase(admin.getId());

				System.out.println("hosting center admin :- " + centerAdmin.toString());

				if (centerAdmin != null) {

					if (centerAdmin.getAdvocates().isEmpty()) {

						List<String> set = new ArrayList<>();

						set.add(advocate.getId());

						centerAdmin.setAdvocates(set);

						centerAdmin = centerAdminRepositroy.save(centerAdmin);

						if (centerAdmin != null) {

							User user = userRepository.findById(advocate.getUserId()).get();

							String name = user.getName();

							List<String> destinations = new ArrayList<>();

							destinations.add("ProfilePage");

							Map<String, String> map = new HashMap<>();

							notificationService.sendNotification(advocate.getUserId(),
									name + " you are accepted as an advocate....", destinations, map);

						}

						System.out.println("updated center admin :- " + centerAdmin.toString());

					} else {

						centerAdmin.getAdvocates().add(advocate.getId());

						centerAdmin = centerAdminRepositroy.save(centerAdmin);

						if (centerAdmin != null) {

							User user = userRepository.findById(advocate.getUserId()).get();

							String name = user.getName();

							List<String> destinations = new ArrayList<>();

							destinations.add("ProfilePage");
							destinations.add("SeeMyProfile");

							Map<String, String> map = new HashMap<>();

							notificationService.sendNotification(advocate.getUserId(),
									name + " you are accepted as an advocate....", destinations, map);

						}

						System.out.println("udated center admin :- " + centerAdmin.toString());
						;

					}

				}

				try {

					AdvocateWorkingExperience workingExperience = new AdvocateWorkingExperience();

					AdvocateWorkingExperienceJoinRequest workingExperienceInJoinRequest = workingExperienceInJoinRequestRepository
							.findByAdvocateId(advocateJoinRequestId);

					if (workingExperienceInJoinRequest != null) {

						cleaner.removeAdvocateWorkingExperienceJoinRequest(workingExperienceInJoinRequest.getId());

					}

					workingExperience.setId(workingExperienceInJoinRequest.getId());
					workingExperience.setAdvocateId(advocate.getId());
					workingExperience.setBarAssociation(workingExperienceInJoinRequest.getBarAssociation());
					workingExperience.setBarControllEnrolment(workingExperienceInJoinRequest.getBarControllEnrolment());
					workingExperience.setCurrentChemberName(workingExperienceInJoinRequest.getCurrentChemberName());
					workingExperience.setEnrollmentDate(workingExperienceInJoinRequest.getEnrollmentDate());
					workingExperience.setPracticingCoat(workingExperienceInJoinRequest.getPracticingCoat());
					workingExperience.setPrimaryPractice(workingExperienceInJoinRequest.getPrimaryPractice());
					workingExperience
							.setProfessionalExperience(workingExperienceInJoinRequest.getProfessionalExperience());
					workingExperience.setYearOfLegalPractice(workingExperienceInJoinRequest.getYearOfLegalPractice());

					workingExperience = workingExperienceRepository.save(workingExperience);

					if (workingExperience != null) {

						System.out.println(workingExperience.toString());

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					AdvocateDocuments advocateDocuments = new AdvocateDocuments();

					AdvocateDocumentsJoinRequest advocateDocumentsInJoinRequest = documentsInJoinRequestRepository
							.findByAdvocateId(advocateJoinRequestId);

					if (advocateDocumentsInJoinRequest != null) {

						advocateDocuments.setId(advocateDocumentsInJoinRequest.getId());
						advocateDocuments.setAdvocateId(advocate.getId());
						advocateDocuments.setAttachments(advocateDocumentsInJoinRequest.getAttachments());

						advocateDocuments = documentsRepository.save(advocateDocuments);

						if (advocateDocuments != null) {

							cleaner.removeAdvocateDocumentsJoinRequest(advocateDocumentsInJoinRequest.getId());

							System.out.println(advocateDocuments.toString());

						}

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					AdvocateAdditionalInfo additionalInfo = new AdvocateAdditionalInfo();

					AdvocateAdditionalInfoJoinRequest advocateAdditionalInfoInJoinRequest = additionalInfoInJoinRequestRepository
							.findByAdvocateId(advocateJoinRequestId);

					if (advocateAdditionalInfoInJoinRequest != null) {

						additionalInfo.setId(advocateAdditionalInfoInJoinRequest.getId());
						additionalInfo.setAdvocateId(advocate.getId());
						additionalInfo.setCityOfUpozila(advocateAdditionalInfoInJoinRequest.getCityOfUpozila());
						additionalInfo.setDateOfBirth(advocateAdditionalInfoInJoinRequest.getDateOfBirth());
						additionalInfo.setNidNumber(advocateAdditionalInfoInJoinRequest.getNidNumber());
						additionalInfo.setParmanentAdress(advocateAdditionalInfoInJoinRequest.getParmanentAdress());
						additionalInfo.setWhatsAppNumber(advocateAdditionalInfoInJoinRequest.getWhatsAppNumber());

						additionalInfo = additionalInfoRepository.save(additionalInfo);

						if (additionalInfo != null) {

							cleaner.removeAdvocateAdditionalInfoJoinRequest(
									advocateAdditionalInfoInJoinRequest.getId());

							System.out.println(additionalInfo.toString());

						}

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				cleaner.removeAdvocateJoinRequest(advocateJoinRequestId);

			}

			return advocate;

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

	}

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private AdvocateJoinRequestDTO getAdvocateJoinRequestResponse(AdvocateJoinRequest advocateJoinRequest) {

		List<AdvocateJoinRequest> list = new ArrayList<>();

		list.add(advocateJoinRequest);

		return getAdvocateJoinRequestResponse(list).get(0);

	}

	private List<AdvocateJoinRequestDTO> getAdvocateJoinRequestResponse(List<AdvocateJoinRequest> list) {

		CompletableFuture<List<String>> allUserIdFuture = CompletableFuture.supplyAsync(() -> list.stream()
				.map(AdvocateJoinRequest::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList()),
				executors);

		CompletableFuture<Map<String, User>> userNameFuture = allUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executors);

		CompletableFuture<Map<String, UserContactInfo>> contactInfoFuture = allUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userContactInfoRepository.findByUserIdIn(usersId).stream()
					.collect(Collectors.toMap(UserContactInfo::getUserId, Function.identity()));

		}, executors);

		CompletableFuture<Map<String, UserLocation>> locationFuture = allUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userLocationRepository.findByUserIdIn(usersId).stream()
					.collect(Collectors.toMap(UserLocation::getUserId, Function.identity()));

		}, executors);

		CompletableFuture<List<String>> allJoinRequestIdFuture = CompletableFuture.supplyAsync(() -> list.stream()
				.filter(Objects::nonNull).map(AdvocateJoinRequest::getId).collect(Collectors.toList()), executors);

		CompletableFuture<Map<String, AdvocateWorkingExperienceJoinRequest>> workingExperienceFuture = allJoinRequestIdFuture
				.thenApplyAsync(ids -> {

					if (ids.isEmpty()) {

						return new HashMap<>();

					}

					return workingExperienceInJoinRequestRepository.findByAdvocateIdIn(ids).stream()
							.filter(Objects::nonNull).collect(Collectors
									.toMap(AdvocateWorkingExperienceJoinRequest::getAdvocateId, Function.identity()));

				}, executors);

		CompletableFuture<Map<String, AdvocateDocumentsJoinRequest>> documentsFuture = allJoinRequestIdFuture
				.thenApplyAsync(ids -> {

					if (ids.isEmpty()) {

						return new HashMap<>();

					}

					return documentsInJoinRequestRepository.findByAdvocateIdIn(ids).stream().filter(Objects::nonNull)
							.collect(
									Collectors.toMap(AdvocateDocumentsJoinRequest::getAdvocateId, Function.identity()));

				}, executors);

		CompletableFuture<Map<String, AdvocateAdditionalInfoJoinRequest>> additionalInfoFuture = allJoinRequestIdFuture
				.thenApplyAsync(ids -> {

					if (ids.isEmpty()) {

						return new HashMap<>();

					}

					return additionalInfoInJoinRequestRepository.findByAdvocateIdIn(ids).stream()
							.filter(Objects::nonNull).collect(Collectors
									.toMap(AdvocateAdditionalInfoJoinRequest::getAdvocateId, Function.identity()));

				}, executors);

		CompletableFuture.allOf(additionalInfoFuture, documentsFuture, workingExperienceFuture, allJoinRequestIdFuture,
				allUserIdFuture, userNameFuture, contactInfoFuture, locationFuture).join();

		Map<String, AdvocateAdditionalInfoJoinRequest> additionalInfoMap = additionalInfoFuture.join();

		Map<String, AdvocateDocumentsJoinRequest> documentsMap = documentsFuture.join();

		Map<String, AdvocateWorkingExperienceJoinRequest> workingExperienceMap = workingExperienceFuture.join();

		List<String> allUserId = allUserIdFuture.join();

		Map<String, User> userMap = userNameFuture.join();

		Map<String, UserContactInfo> contactMap = contactInfoFuture.join();

		Map<String, UserLocation> locationMap = locationFuture.join();

		List<AdvocateJoinRequestDTO> responses = new ArrayList<>();

		for (AdvocateJoinRequest request : list) {

			try {

				AdvocateJoinRequestDTO response = new AdvocateJoinRequestDTO();

				response.setId(request.getId());

				try {

					response.setAdvocateSpeciality(request.getAdvocateSpeciality());

				} catch (Exception e) {

				}

				try {

					response.setCvHexKey(request.getCvHexKey());

				} catch (Exception e) {

				}

				try {

					AdvocateWorkingExperienceJoinRequest workingExperience = workingExperienceMap.get(request.getId());

					response.setWorkingExperience(workingExperience);

				} catch (Exception e) {

				}

				try {

					AdvocateAdditionalInfoJoinRequest additionalInfo = additionalInfoMap.get(request.getId());

					response.setAdditionalInfo(additionalInfo);

				} catch (Exception e) {

				}

				try {

					AdvocateDocumentsJoinRequest documents = documentsMap.get(request.getId());

					response.setDocuments(documents);

				} catch (Exception e) {

				}

				try {

					response.setDegrees(request.getDegrees());

				} catch (Exception e) {

				}

				try {

					response.setExperience(request.getExperience());

				} catch (Exception e) {

				}

				try {

					response.setUserId(request.getUserId());

				} catch (Exception e) {

				}

				try {

					response.setDistrict(request.getDistrict());

				} catch (Exception e) {

				}

				try {

					// response.setProfileImageId(userMap.get(request.getUserId()).getProfileImageId());
					response.setUserName(userMap.get(request.getUserId()).getName());
					response.setFullName(userMap.get(request.getUserId()).getFullName());

				} catch (Exception e) {

				}

				try {

					response.setProfileImageId(userMap.get(request.getUserId()).getProfileImageId());

				} catch (Exception e) {

				}

				try {

					UserContactInfo contactInfo = contactMap.get(request.getUserId());

					if (contactInfo == null) {

						throw new Exception();

					}

					response.setUserContactInfoId(contactInfo.getId());

					try {

						response.setEmail(contactInfo.getEmail());

					} catch (Exception e) {

					}

					response.setPhone(contactInfo.getPhone());

				} catch (Exception e) {

				}

				try {

					UserLocation location = locationMap.get(request.getUserId());

					if (location == null) {

						throw new Exception();

					}

					response.setUserLocationId(location.getId());

					try {

						response.setLocationName(location.getLocationName());

					} catch (Exception e) {

					}

					response.setLattitude(location.getLattitude());
					response.setLogitude(location.getLongitude());

				} catch (Exception e) {

				}

				try {

					response.setLicenseKey(request.getLicenseKey());

				} catch (Exception e) {

				}

				try {

					response.setWorkingExperiences(request.getWorkingExperiences());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

				System.out.println(e);

			}

		}

		return responses;

	}

}
