package com.example.demo700.Repositories.AdvocateRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.Advocate;

@Repository
public interface AdvocateRepositories extends MongoRepository<Advocate, String> {
	
	public Advocate findByUserId(String userId);
	public List<Advocate> findByAdvocateSpeciality(AdvocateSpeciality AdvocateSpeciality);
	public Advocate findByLicenseKey(String licenseKey);
	public List<Advocate> findByExperienceGreaterThan(int experience);
	public List<Advocate> findByDegreesContainingIgnoreCase(String degree);
	public List<Advocate> findByWorkingExperiencesContainingIgnoreCase(String experience);

}
