package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.AdvocateJoinRequestDTO;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;

public interface AdvocateJoinRequestService {

	public AdvocateJoinRequest addVocate(AdvocateJoinRequest advocate, String userId, MultipartFile file);

	public List<AdvocateJoinRequestDTO> seeAllAdvocate();

	public AdvocateJoinRequestDTO findByUserId(String userId);

	public List<AdvocateJoinRequestDTO> findByAdvocateSpeciality(String AdvocateSpeciality);

	public AdvocateJoinRequestDTO findByLicenseKey(String licenseKey);

	public List<AdvocateJoinRequestDTO> findByExperienceGreaterThan(int experience);

	public List<AdvocateJoinRequestDTO> findByDegreesContainingIgnoreCase(String degree);

	public List<AdvocateJoinRequestDTO> findByWorkingExperiencesContainingIgnoreCase(String experience);

	public AdvocateJoinRequest updateAdvocate(AdvocateJoinRequest advocate, String userId, String advocateId, MultipartFile file);

	public Advocate handleJoinRequest(String userId, String advocateJoinRequestId);
	
	public boolean deleteAdvocate(String userId, String advocateId);

}
