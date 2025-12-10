package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;

public interface AdvocateJoinRequestService {

	public AdvocateJoinRequest addVocate(AdvocateJoinRequest advocate, String userId, MultipartFile file);

	public List<AdvocateJoinRequest> seeAllAdvocate();

	public AdvocateJoinRequest findByUserId(String userId);

	public List<AdvocateJoinRequest> findByAdvocateSpeciality(String AdvocateSpeciality);

	public AdvocateJoinRequest findByLicenseKey(String licenseKey);

	public List<AdvocateJoinRequest> findByExperienceGreaterThan(int experience);

	public List<AdvocateJoinRequest> findByDegreesContainingIgnoreCase(String degree);

	public List<AdvocateJoinRequest> findByWorkingExperiencesContainingIgnoreCase(String experience);

	public AdvocateJoinRequest updateAdvocate(AdvocateJoinRequest advocate, String userId, String advocateId, MultipartFile file);

	public Advocate handleJoinRequest(String userId, String advocateJoinRequestId);
	
	public boolean deleteAdvocate(String userId, String advocateId);

}
