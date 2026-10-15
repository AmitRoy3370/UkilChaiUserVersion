package com.example.demo700.Repositories.CaseRepositories;

import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CaseRepository extends MongoRepository<Case, String> {

	public Case findByCaseNameIgnoreCase(String caseName);
	
	public Page<Case> findByCaseNameIgnoreCase(String caseName, Pageable pageable);

	public List<Case> findByCaseNameContainingIgnoreCase(String caseName);

	public Page<Case> findByCaseNameContainingIgnoreCase(String caseName, Pageable pageable);
	
	public List<Case> findByUserId(String userId);
	
	public Page<Case> findByUserId(String userId, Pageable pageable);

	public List<Case> findByCaseType(AdvocateSpeciality caseType);
	
	public Page<Case> findByCaseType(AdvocateSpeciality caseType, Pageable pageable);

	public List<Case> findByAdvocateId(String advocateId);
	
	public Page<Case> findByAdvocateId(String advocateId, Pageable pageable);

	public List<Case> findByIssuedTimeAfter(Instant issuedTime);
	
	public Page<Case> findByIssuedTimeAfter(Instant issuedTime, Pageable pageable);

	public List<Case> findByIssuedTimeBefore(Instant issuedTime);
	
	public Page<Case> findByIssuedTimeBefore(Instant issuedTime, Pageable pageable);

}
