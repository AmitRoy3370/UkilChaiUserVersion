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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.AdvocateJoinRequestDTO;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
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

	@Override
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
	public List<AdvocateJoinRequestDTO> seeAllAdvocate() {

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate request at here...");

		}

		return getAdvocateJoinRequestResponse(list);
	}

	@Override
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
	public List<AdvocateJoinRequestDTO> findByDistrict(String district) {
		
		if(district == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByDistrictContainingIgnoreCase(district);
			
			if(list.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getAdvocateJoinRequestResponse(list);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such advocate request find at here...");
			
		}
		
	}
	
	
	@Override
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
		advocate.setUserId(advocateJoinRequest.getUserId());
		advocate.setWorkingExperiences(advocateJoinRequest.getWorkingExperiences());
		advocate.setId(advocateJoinRequest.getId());

		try {

			advocate = advocateRepository.save(advocate);

			if (advocate != null) {

				cleaner.removeAdvocateJoinRequest(advocateJoinRequestId);

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

							notificationService.sendNotification(advocate.getUserId(),
									name + " you are accepted as an advocate....");

						}

						System.out.println("updated center admin :- " + centerAdmin.toString());

					} else {

						centerAdmin.getAdvocates().add(advocate.getId());

						centerAdmin = centerAdminRepositroy.save(centerAdmin);

						if (centerAdmin != null) {

							User user = userRepository.findById(advocate.getUserId()).get();

							String name = user.getName();

							notificationService.sendNotification(advocate.getUserId(),
									name + " you are accepted as an advocate....");

						}

						System.out.println("udated center admin :- " + centerAdmin.toString());
						;

					}

				}

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

		CompletableFuture.allOf(allUserIdFuture, userNameFuture, contactInfoFuture, locationFuture).join();

		List<String> allUserId = allUserIdFuture.join();

		Map<String, User> userMap = userNameFuture.join();

		Map<String, UserContactInfo> contactMap = contactInfoFuture.join();

		Map<String, UserLocation> locationMap = locationFuture.join();
		
		List<AdvocateJoinRequestDTO> responses = new ArrayList<>();

		for (AdvocateJoinRequest request : list) {

			try {

				AdvocateJoinRequestDTO response = new AdvocateJoinRequestDTO();

				response.setId(request.getId());
				response.setAdvocateSpeciality(request.getAdvocateSpeciality());
				response.setCvHexKey(request.getCvHexKey());
				response.setDegrees(request.getDegrees());
				response.setExperience(request.getExperience());
				response.setUserId(request.getUserId());
				response.setDistrict(request.getDistrict());
				//response.setProfileImageId(userMap.get(request.getUserId()).getProfileImageId());
				response.setUserName(userMap.get(request.getUserId()).getName());

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
					response.setEmail(contactInfo.getEmail());
					response.setPhone(contactInfo.getPhone());

				} catch (Exception e) {

				}

				try {
					
					UserLocation location = locationMap.get(request.getUserId());
					
					if(location == null) {
						
						throw new Exception();
						
					}
					
					response.setUserLocationId(location.getId());
					response.setLocationName(location.getLocationName());
					response.setLattitude(location.getLattitude());
					response.setLogitude(location.getLongitude());
					
				} catch(Exception e) {
					
				}
				
				response.setLicenseKey(request.getLicenseKey());
				response.setWorkingExperiences(request.getWorkingExperiences());

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
