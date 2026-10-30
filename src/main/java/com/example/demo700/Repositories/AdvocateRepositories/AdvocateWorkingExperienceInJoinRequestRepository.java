package com.example.demo700.Repositories.AdvocateRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperienceJoinRequest;

@Repository
public interface AdvocateWorkingExperienceInJoinRequestRepository extends MongoRepository<AdvocateWorkingExperienceJoinRequest, String> {

	// ==================== BY ADVOCATE ID ====================
		@Query("{ 'advocateId': ?0 }")
		AdvocateWorkingExperienceJoinRequest findByAdvocateId(String advocateId);
		
		List<AdvocateWorkingExperienceJoinRequest> findByAdvocateIdIn(List<String> advocateId);

		// ==================== BAR ASSOCIATION (case-insensitive contains) ====================
		@Query("{ 'barAssociation': { $regex: ?0, $options: 'i' } }")
		List<AdvocateWorkingExperienceJoinRequest> findByBarAssociationContainingIgnoreCase(String barAssociation);

		// ==================== BAR CONTROL ENROLMENT (case-insensitive contains) ====================
		@Query("{ 'barControllEnrolment': { $regex: ?0, $options: 'i' } }")
		List<AdvocateWorkingExperienceJoinRequest> findByBarControllEnrolmentContainingIgnoreCase(String barControllEnrolment);

		// ==================== ENROLLMENT DATE AFTER ====================
		@Query("{ 'enrollmentDate': { $gte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateAfter(Instant enrollmentDate);

		// ==================== ENROLLMENT DATE BEFORE ====================
		@Query("{ 'enrollmentDate': { $lte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateBefore(Instant enrollmentDate);

		// ==================== PRACTICING COAT (case-insensitive contains) ====================
		@Query("{ 'practicingCoat': { $regex: ?0, $options: 'i' } }")
		List<AdvocateWorkingExperienceJoinRequest> findByPracticingCoatContainingIgnoreCase(String practicingCoat);

		// ==================== PRIMARY PRACTICE (case-insensitive contains) ====================
		@Query("{ 'primaryPractice': { $regex: ?0, $options: 'i' } }")
		List<AdvocateWorkingExperienceJoinRequest> findByPrimaryPracticeContainingIgnoreCase(String primaryPractice);

		// ==================== YEAR OF LEGAL PRACTICE >= ====================
		@Query("{ 'yearOfLegalPractice': { $gte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeGreaterThanEqual(int yearOfLegalPractice);

		// ==================== YEAR OF LEGAL PRACTICE <= ====================
		@Query("{ 'yearOfLegalPractice': { $lte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeLessThanEqual(int yearOfLegalPractice);

		// ==================== CURRENT CHAMBER NAME (case-insensitive contains) ====================
		@Query("{ 'currentChemberName': { $regex: ?0, $options: 'i' } }")
		List<AdvocateWorkingExperienceJoinRequest> findByCurrentChemberNameContainingIgnoreCase(String currentChemberName);

		// ==================== PROFESSIONAL EXPERIENCE >= ====================
		@Query("{ 'professionalExperience': { $gte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceGreaterThanEqual(int professionalExperience);

		// ==================== PROFESSIONAL EXPERIENCE <= ====================
		@Query("{ 'professionalExperience': { $lte: ?0 } }")
		List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceLessThanEqual(int professionalExperience);
	
}
