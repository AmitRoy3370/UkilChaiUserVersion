package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperienceJoinRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateWorkingExperienceInJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateWorkingExperienceRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdvocateWorkingExperienceJoinRequestServiceImpl implements AdvocateWorkingExperienceJoinRequestService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateJoinRequestRepository advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateWorkingExperienceInJoinRequestRepository workingRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "AdvocateWorkingExperienceInJoinRequest";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true),
          @CacheEvict(value = "AdvocateJoinRequest", allEntries = true) })
	public AdvocateWorkingExperienceJoinRequest addWorkingExperience(AdvocateWorkingExperienceJoinRequest experience,
			String userId) {

		if (experience == null || userId == null || experience.getProfessionalExperience() < 0
				|| experience.getYearOfLegalPractice() < 0) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		AdvocateJoinRequest advocate = null;

		try {

			advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			AdvocateWorkingExperienceJoinRequest workingExperience = workingRepository
					.findByAdvocateId(advocate.getId());

			if (workingExperience != null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("This advocate experience is already setted...");

		}

		experience = workingRepository.save(experience);

		if (experience == null) {

			throw new ArithmeticException("experience is not setted...");

		}

		return experience;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true),
          @CacheEvict(value = "AdvocateJoinRequest", allEntries = true) })
	public AdvocateWorkingExperienceJoinRequest updateWorkingExperience(AdvocateWorkingExperienceJoinRequest experience,
			String userId, String id) {

		if (id == null || experience == null || userId == null || experience.getProfessionalExperience() < 0
				|| experience.getYearOfLegalPractice() < 0) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		AdvocateJoinRequest advocate = null;

		try {

			advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			AdvocateWorkingExperienceJoinRequest workingExperience = workingRepository.findById(id).get();

			if (workingExperience == null) {

				throw new Exception();

			}

			if (workingExperience.getAdvocateId().equals(experience.getAdvocateId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}

		try {

			AdvocateWorkingExperienceJoinRequest workingExperience = workingRepository
					.findByAdvocateId(advocate.getId());

			if (workingExperience != null) {

				if (!workingExperience.getId().equals(id)) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("This advocate experience is already setted...");

		}

		experience.setId(id);

		Query query = new Query(Criteria.where("_id").is(experience.getId()));

		Update update = new Update();

		update.set("id", experience.getId());
		update.set("advocateId", experience.getAdvocateId());

		if (experience.getBarAssociation() != null) {

			update.set("barAssociation", experience.getBarAssociation());

		}

		if (experience.getBarControllEnrolment() != null) {

			update.set("barControllEnrolment", experience.getBarControllEnrolment());

		}

		if (experience.getEnrollmentDate() != null) {

			update.set("enrollmentDate", experience.getEnrollmentDate());

		}

		if (experience.getPracticingCoat() != null) {

			update.set("practicingCoat", experience.getPracticingCoat());

		}

		update.set("professionalExperience", experience.getProfessionalExperience());

		if (experience.getPrimaryPractice() != null) {

			update.set("primaryPractice", experience.getPrimaryPractice());

		}

		update.set("yearOfLegalPractice", experience.getYearOfLegalPractice());

		if (experience.getCurrentChemberName() != null) {

			update.set("currentChemberName", experience.getCurrentChemberName());

		}

		mongoTemplate.updateFirst(query, update, AdvocateWorkingExperienceJoinRequest.class);

		experience = mongoTemplate.findOne(query, AdvocateWorkingExperienceJoinRequest.class);

		// experience = workingRepository.save(experience);

		if (experience == null) {

			throw new ArithmeticException("experience is not setted...");

		}

		return experience;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public AdvocateWorkingExperienceJoinRequest findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateWorkingExperienceJoinRequest experience = workingRepository.findById(id).get();

			if (experience == null) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<AdvocateWorkingExperienceJoinRequest> findAll() {

		try {

			List<AdvocateWorkingExperienceJoinRequest> list = workingRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public AdvocateWorkingExperienceJoinRequest findByAdvocateId(String advocateId) {
		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateWorkingExperienceJoinRequest experience = workingRepository.findByAdvocateId(advocateId);

			if (experience == null) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByBarAssociation_' + #barAssociation")
	public List<AdvocateWorkingExperienceJoinRequest> findByBarAssociationContainingIgnoreCase(String barAssociation) {
		if (barAssociation == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByBarAssociationContainingIgnoreCase(barAssociation);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByBarControllEnrolment_' + #barControllEnrolment")
	public List<AdvocateWorkingExperienceJoinRequest> findByBarControllEnrolmentContainingIgnoreCase(
			String barControllEnrolment) {
		if (barControllEnrolment == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByBarControllEnrolmentContainingIgnoreCase(barControllEnrolment);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEnrollmentDateAfter_' + #enrollmentDate")
	public List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateAfter(Instant enrollmentDate) {
		if (enrollmentDate == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByEnrollmentDateAfter(enrollmentDate);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEnrollmentDateBefore_' + #enrollmentDate")
	public List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateBefore(Instant enrollmentDate) {
		if (enrollmentDate == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByEnrollmentDateBefore(enrollmentDate);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPracticingCoat_' + #practicingCoat")
	public List<AdvocateWorkingExperienceJoinRequest> findByPracticingCoatContainingIgnoreCase(String practicingCoat) {
		if (practicingCoat == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByPracticingCoatContainingIgnoreCase(practicingCoat);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPrimaryPractice_' + #primaryPractice")
	public List<AdvocateWorkingExperienceJoinRequest> findByPrimaryPracticeContainingIgnoreCase(
			String primaryPractice) {
		if (primaryPractice == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByPrimaryPracticeContainingIgnoreCase(primaryPractice);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfLegalPracticeGTE_' + #yearOfFLegalPractice")
	public List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeGreaterThanEqual(
			int yearOfLegalPractice) {

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByYearOfLegalPracticeGreaterThanEqual(yearOfLegalPractice);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfLegalPracticeLTE_' + #yearOfFLegalPractice")
	public List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeLessThanEqual(int yearOfLegalPractice) {

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByYearOfLegalPracticeLessThanEqual(yearOfLegalPractice);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCurrentChemberName_' + #currentChemberName")
	public List<AdvocateWorkingExperienceJoinRequest> findByCurrentChemberNameContainingIgnoreCase(
			String currentChemberName) {
		if (currentChemberName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByCurrentChemberNameContainingIgnoreCase(currentChemberName);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByProfessionalExperienceGTE_' + #professionalExperience")
	public List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceGreaterThanEqual(
			int professionalExperience) {

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByProfessionalExperienceGreaterThanEqual(professionalExperience);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByProfessionalExperienceLTE_' + #professionalExperience")
	public List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceLessThanEqual(
			int professionalExperience) {

		try {

			List<AdvocateWorkingExperienceJoinRequest> experience = workingRepository
					.findByProfessionalExperienceLessThanEqual(professionalExperience);

			if (experience == null || experience.isEmpty()) {

				throw new Exception();

			}

			return experience;

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true) })
	public boolean deleteAdvocateWorkingExperience(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		AdvocateJoinRequest advocate = null;

		try {

			advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		AdvocateWorkingExperienceJoinRequest workingExperience = null;

		try {

			workingExperience = workingRepository.findById(id).get();

			if (workingExperience == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			if (admin.getAdvocates().contains(workingExperience.getAdvocateId())) {

				long count = workingRepository.count();

				cleaner.removeAdvocateWorkingExperience(id);

				return count != workingRepository.count();

			} else {

				if (workingExperience.getAdvocateId().equals(advocate.getId())) {

				} else {

					throw new ArithmeticException("You can remove only your advocate....");

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can remove only your advocate...");

		} catch (Exception e) {

		}

		try {

			if (workingExperience.getAdvocateId().equals(advocate.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate experience find at here...");

		}

		long count = workingRepository.count();

		cleaner.removeAdvocateWorkingExperienceJoinRequest(id);

		return count != workingRepository.count();
	}

}
