package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperience;
import com.example.demo700.Model.AdvocateModels.AdvocateWorkingExperienceJoinRequest;

public interface AdvocateWorkingExperienceJoinRequestService {

	public AdvocateWorkingExperienceJoinRequest addWorkingExperience(AdvocateWorkingExperienceJoinRequest experience, String userId);
	public AdvocateWorkingExperienceJoinRequest updateWorkingExperience(AdvocateWorkingExperienceJoinRequest experience, String userId, String id);
	
	public AdvocateWorkingExperienceJoinRequest findById(String id);
	public List<AdvocateWorkingExperienceJoinRequest> findAll();
	public AdvocateWorkingExperienceJoinRequest findByAdvocateId(String advocateId);
	public List<AdvocateWorkingExperienceJoinRequest> findByBarAssociationContainingIgnoreCase(String barAssociation);
	public List<AdvocateWorkingExperienceJoinRequest> findByBarControllEnrolmentContainingIgnoreCase(String barControllEnrolment);
	public List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateAfter(Instant enrollmentDate);
	public List<AdvocateWorkingExperienceJoinRequest> findByEnrollmentDateBefore(Instant enrollmentDate);
	public List<AdvocateWorkingExperienceJoinRequest> findByPracticingCoatContainingIgnoreCase(String practicingCoat);
	public List<AdvocateWorkingExperienceJoinRequest> findByPrimaryPracticeContainingIgnoreCase(String primaryPractice);
	public List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeGreaterThanEqual(int yearOfLegalPractice);
	public List<AdvocateWorkingExperienceJoinRequest> findByYearOfLegalPracticeLessThanEqual(int yearOfLegalPractice);
	public List<AdvocateWorkingExperienceJoinRequest> findByCurrentChemberNameContainingIgnoreCase(String currentChemberName);
	public List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceGreaterThanEqual(int professionalExperience);
	public List<AdvocateWorkingExperienceJoinRequest> findByProfessionalExperienceLessThanEqual(int professionalExperience);
	
	public boolean deleteAdvocateWorkingExperience(String id, String userId);
	
	
}
