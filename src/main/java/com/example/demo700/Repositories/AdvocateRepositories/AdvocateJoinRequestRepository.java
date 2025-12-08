package com.example.demo700.Repositories.AdvocateRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;

@Repository
public interface AdvocateJoinRequestRepository extends MongoRepository<AdvocateJoinRequest, String> {

	public AdvocateJoinRequest findByUserId(String userId);

	public List<AdvocateJoinRequest> findByAdvocateSpeciality(com.example.demo700.ENums.AdvocateSpeciality advocateSpeciality);

	public AdvocateJoinRequest findByLicenseKey(String licenseKey);

	public List<AdvocateJoinRequest> findByExperienceGreaterThan(int experience);

	public List<AdvocateJoinRequest> findByDegreesContainingIgnoreCase(String degree);

	public List<AdvocateJoinRequest> findByWorkingExperiencesContainingIgnoreCase(String experience);

}
