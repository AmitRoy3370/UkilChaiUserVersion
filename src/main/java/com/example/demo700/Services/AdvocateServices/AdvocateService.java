package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.ENums.Gender;
import com.example.demo700.Model.AdvocateModels.Advocate;

public interface AdvocateService {
	
	public Advocate addVocate(Advocate advocate, String userId, MultipartFile file);
	public AdvocateResponse findById(String advocateId);
	public List<AdvocateResponse> seeAllAdvocate();
	public List<AdvocateResponse> seeAllAdvocate(List<String> list);
	public AdvocateResponse findByUserId(String userId);
	public List<AdvocateResponse> findByAdvocateSpeciality(AdvocateSpeciality AdvocateSpeciality);
	public List<AdvocateResponse> findByGender(Gender gender);
	public AdvocateResponse findByLicenseKey(String licenseKey);
	public List<AdvocateResponse> findByDistrict(String district);
	public List<AdvocateResponse> findByExperienceGreaterThan(int experience);
	public List<AdvocateResponse> findByDegreesContainingIgnoreCase(String degree);
	public List<AdvocateResponse> findByWorkingExperiencesContainingIgnoreCase(String experience);
	public Advocate updateAdvocate(Advocate advocate, String userId, String advocateId, MultipartFile file);
	public boolean deleteAdvocate(String userId, String advocateId);
	public List<AdvocateResponse> findByLocation(String location);


}
