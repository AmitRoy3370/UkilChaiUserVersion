package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;

public interface AdvocateWorkingExperienceService {

	public AdvocateWorkingExperience addWorkingExperience(AdvocateWorkingExperience experience, String userId);
	public AdvocateWorkingExperience updateWorkingExperience(AdvocateWorkingExperience experience, String userId, String id);
	
	public AdvocateWorkingExperience findById(String id);
	public List<AdvocateWorkingExperience> findAll();
	public AdvocateWorkingExperience findByAdvocateId(String advocateId);
	public List<AdvocateWorkingExperience> findByBarAssociationContainingIgnoreCase(String barAssociation);
	public List<AdvocateWorkingExperience> findByBarControllEnrolmentContainingIgnoreCase(String barControllEnrolment);
	public List<AdvocateWorkingExperience> findByEnrollmentDateAfter(Instant enrollmentDate);
	public List<AdvocateWorkingExperience> findByEnrollmentDateBefore(Instant enrollmentDate);
	public List<AdvocateWorkingExperience> findByPracticingCoatContainingIgnoreCase(String practicingCoat);
	public List<AdvocateWorkingExperience> findByPrimaryPracticeContainingIgnoreCase(String primaryPractice);
	public List<AdvocateWorkingExperience> findByYearOfLegalPracticeGreaterThanEqual(int yearOfLegalPractice);
	public List<AdvocateWorkingExperience> findByYearOfLegalPracticeLessThanEqual(int yearOfLegalPractice);
	public List<AdvocateWorkingExperience> findByCurrentChemberNameContainingIgnoreCase(String currentChemberName);
	public List<AdvocateWorkingExperience> findByProfessionalExperienceGreaterThanEqual(int professionalExperience);
	public List<AdvocateWorkingExperience> findByProfessionalExperienceLessThanEqual(int professionalExperience);
	
	public boolean deleteAdvocateWorkingExperience(String id, String userId);
	
}
