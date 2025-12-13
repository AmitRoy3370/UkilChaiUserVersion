package com.example.demo700.Services.AdvocateServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;

import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
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
	public List<Advocate> seeAllAdvocate() {

		return advocateRepository.findAll();
	}

	@Override
	public Advocate findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			return advocate;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

	}

	@Override
	public List<Advocate> findByAdvocateSpeciality(AdvocateSpeciality advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByAdvocateSpeciality(advocateSpeciality);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public Advocate findByLicenseKey(String licenseKey) {

		if (licenseKey == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Advocate advocate = advocateRepository.findByLicenseKey(licenseKey);

			if (advocate == null) {

				throw new Exception();

			}

			return advocate;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<Advocate> findByExperienceGreaterThan(int experience) {

		try {

			List<Advocate> list = advocateRepository.findByExperienceGreaterThan(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<Advocate> findByDegreesContainingIgnoreCase(String degree) {

		if (degree == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByDegreesContainingIgnoreCase(degree);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

	}

	@Override
	public List<Advocate> findByWorkingExperiencesContainingIgnoreCase(String experience) {

		if (experience == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Advocate> list = advocateRepository.findByWorkingExperiencesContainingIgnoreCase(experience);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

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
	
}
