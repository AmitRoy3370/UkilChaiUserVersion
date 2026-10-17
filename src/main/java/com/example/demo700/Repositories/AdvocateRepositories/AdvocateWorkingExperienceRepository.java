package com.example.demo700.Repositories.AdvocateRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;

@Repository
public interface AdvocateWorkingExperienceRepository extends MongoRepository<AdvocateWorkingExperience, String> {

	// ==================== BY ADVOCATE ID ====================
	@Query("{ 'advocateId': ?0 }")
	AdvocateWorkingExperience findByAdvocateId(String advocateId);
	
	List<AdvocateWorkingExperience> findByAdvocateIdIn(List<String> advocateId);

	// ==================== BAR ASSOCIATION (case-insensitive contains) ====================
	@Query("{ 'barAssociation': { $regex: ?0, $options: 'i' } }")
	List<AdvocateWorkingExperience> findByBarAssociationContainingIgnoreCase(String barAssociation);

	// ==================== BAR CONTROL ENROLMENT (case-insensitive contains) ====================
	@Query("{ 'barControllEnrolment': { $regex: ?0, $options: 'i' } }")
	List<AdvocateWorkingExperience> findByBarControllEnrolmentContainingIgnoreCase(String barControllEnrolment);

	// ==================== ENROLLMENT DATE AFTER ====================
	@Query("{ 'enrollmentDate': { $gte: ?0 } }")
	List<AdvocateWorkingExperience> findByEnrollmentDateAfter(Instant enrollmentDate);

	// ==================== ENROLLMENT DATE BEFORE ====================
	@Query("{ 'enrollmentDate': { $lte: ?0 } }")
	List<AdvocateWorkingExperience> findByEnrollmentDateBefore(Instant enrollmentDate);

	// ==================== PRACTICING COAT (case-insensitive contains) ====================
	@Query("{ 'practicingCoat': { $regex: ?0, $options: 'i' } }")
	List<AdvocateWorkingExperience> findByPracticingCoatContainingIgnoreCase(String practicingCoat);

	// ==================== PRIMARY PRACTICE (case-insensitive contains) ====================
	@Query("{ 'primaryPractice': { $regex: ?0, $options: 'i' } }")
	List<AdvocateWorkingExperience> findByPrimaryPracticeContainingIgnoreCase(String primaryPractice);

	// ==================== YEAR OF LEGAL PRACTICE >= ====================
	@Query("{ 'yearOfLegalPractice': { $gte: ?0 } }")
	List<AdvocateWorkingExperience> findByYearOfLegalPracticeGreaterThanEqual(int yearOfLegalPractice);

	// ==================== YEAR OF LEGAL PRACTICE <= ====================
	@Query("{ 'yearOfLegalPractice': { $lte: ?0 } }")
	List<AdvocateWorkingExperience> findByYearOfLegalPracticeLessThanEqual(int yearOfLegalPractice);

	// ==================== CURRENT CHAMBER NAME (case-insensitive contains) ====================
	@Query("{ 'currentChemberName': { $regex: ?0, $options: 'i' } }")
	List<AdvocateWorkingExperience> findByCurrentChemberNameContainingIgnoreCase(String currentChemberName);

	// ==================== PROFESSIONAL EXPERIENCE >= ====================
	@Query("{ 'professionalExperience': { $gte: ?0 } }")
	List<AdvocateWorkingExperience> findByProfessionalExperienceGreaterThanEqual(int professionalExperience);

	// ==================== PROFESSIONAL EXPERIENCE <= ====================
	@Query("{ 'professionalExperience': { $lte: ?0 } }")
	List<AdvocateWorkingExperience> findByProfessionalExperienceLessThanEqual(int professionalExperience);
}