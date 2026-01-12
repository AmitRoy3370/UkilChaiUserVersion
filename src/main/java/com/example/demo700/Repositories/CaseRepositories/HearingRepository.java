package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.Hearing;

@Repository
public interface HearingRepository extends MongoRepository<Hearing, String> {

	public List<Hearing> findByHearningNumber(int hearningNumber);
	public List<Hearing> findByCaseId(String caseId);
	
	public Hearing findByCaseIdAndHearningNumber(String caseId, int hearingNumber);
	
	public List<Hearing> findByCaseIdAndHearningNumberLessThanEqual(String caseId, int hearingNumber);
	
	public List<Hearing> findByIssuedDateAfter(Instant date);
	
	public List<Hearing> findByIssuedDateBefore(Instant date);
	
}
