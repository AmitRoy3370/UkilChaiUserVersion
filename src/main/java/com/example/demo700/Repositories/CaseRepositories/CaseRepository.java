package com.example.demo700.Repositories.CaseRepositories;

import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CaseRepository extends MongoRepository<Case, String> {

	public Case findByCaseNameIgnoreCase(String caseName);

	public List<Case> findByCaseNameContainingIgnoreCase(String caseName);

	public List<Case> findByUserId(String userId);

	public List<Case> findByCaseType(AdvocateSpeciality caseType);

	public List<Case> findByAdvocateId(String advocateId);

	public List<Case> findByIssuedTimeAfter(Instant issuedTime);

	public List<Case> findByIssuedTimeBefore(Instant issuedTime);

}
