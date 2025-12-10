package com.example.demo700.Services.AdvocateServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdvocateJoinRequestServiceImpl implements AdvocateJoinRequestService {

	@Autowired
	private AdvocateJoinRequestRepository advocateJoinRequestRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateService advocateService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CVUploadService cvUpload;

	@Autowired
	private CenterAdminRepository centerAdminRepositroy;

	@Autowired
	private AdminRepository adminRepository;

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

				String hex = cvUpload.uploadCv(file);

				advocateJoinRequest.setCvHexKey(hex);

			}

		} catch (Exception e) {

		}

		advocateJoinRequest = advocateJoinRequestRepository.save(advocateJoinRequest);

		return advocateJoinRequest;
	}

	@Override
	public List<AdvocateJoinRequest> seeAllAdvocate() {

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate request at here...");

		}

		return list;
	}

	@Override
	public AdvocateJoinRequest findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			return advocate;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here....");

		}

	}

	@Override
	public List<AdvocateJoinRequest> findByAdvocateSpeciality(String advocateSpeciality) {

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

			return list;

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

	}

	@Override
	public AdvocateJoinRequest findByLicenseKey(String licenseKey) {

		if (licenseKey == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateJoinRequest advocate = advocateJoinRequestRepository.findByLicenseKey(licenseKey);

			if (advocate == null) {

				throw new Exception();

			}

			return advocate;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate request find at here...");

		}

	}

	@Override
	public List<AdvocateJoinRequest> findByExperienceGreaterThan(int experience) {

		try {

			List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByExperienceGreaterThan(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

	}

	@Override
	public List<AdvocateJoinRequest> findByDegreesContainingIgnoreCase(String degree) {

		if (degree == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository.findByDegreesContainingIgnoreCase(degree);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		return list;
	}

	@Override
	public List<AdvocateJoinRequest> findByWorkingExperiencesContainingIgnoreCase(String experience) {
		if (experience == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateJoinRequest> list = advocateJoinRequestRepository
				.findByWorkingExperiencesContainingIgnoreCase(experience);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		return list;
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

				String hex = cvUpload.uploadCv(file);

				advocateJoinRequest.setCvHexKey(hex);

			}

		} catch (Exception e) {

		}

		advocateJoinRequest.setId(advocateId);

		advocateJoinRequest = advocateJoinRequestRepository.save(advocateJoinRequest);

		return advocateJoinRequest;
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

			advocate = advocateService.addVocate(advocate, advocate.getUserId());

			if (advocate != null) {

				Admin admin = adminRepository.findByUserId(userId);

				CenterAdmin centerAdmin = centerAdminRepositroy.findByAdminsContainingIgnoreCase(admin.getId());

				if (centerAdmin != null) {

					if (centerAdmin.getAdvocates().isEmpty()) {

						List<String> set = new ArrayList<>();

						set.add(advocate.getId());

						centerAdmin.setAdvocates(set);

						centerAdminRepositroy.save(centerAdmin);

					} else {

						centerAdmin.getAdvocates().add(advocate.getId());

						centerAdminRepositroy.save(centerAdmin);

					}

				}

				cleaner.removeAdvocateJoinRequest(advocateJoinRequestId);

			}

			return advocate;

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

	}

}
