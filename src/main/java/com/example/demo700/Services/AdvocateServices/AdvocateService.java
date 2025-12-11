package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.Advocate;

public interface AdvocateService {
	
	public Advocate addVocate(Advocate advocate, String userId, MultipartFile file);
	public List<Advocate> seeAllAdvocate();
	public Advocate findByUserId(String userId);
	public List<Advocate> findByAdvocateSpeciality(AdvocateSpeciality AdvocateSpeciality);
	public Advocate findByLicenseKey(String licenseKey);
	public List<Advocate> findByExperienceGreaterThan(int experience);
	public List<Advocate> findByDegreesContainingIgnoreCase(String degree);
	public List<Advocate> findByWorkingExperiencesContainingIgnoreCase(String experience);
	public Advocate updateAdvocate(Advocate advocate, String userId, String advocateId, MultipartFile file);
	public boolean deleteAdvocate(String userId, String advocateId);


}
