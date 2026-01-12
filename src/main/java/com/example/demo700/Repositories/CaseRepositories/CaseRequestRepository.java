package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.CaseRequest;

@Repository
public interface CaseRequestRepository extends MongoRepository<CaseRequest, String> {

	public CaseRequest findByCaseNameIgnoreCase(String caseName);
	
	public List<CaseRequest> findByCaseNameContainingIgnoreCase(String caseName);
	public List<CaseRequest> findByUserId(String userId);
	
	public List<CaseRequest> findByCaseType(AdvocateSpeciality caseType);
	
	public List<CaseRequest> findByRequestDateAfter(Instant issuedTime);
	
	public List<CaseRequest> findByRequestDateBefore(Instant issuedTime);
	
}
