package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.AppealCase;

@Repository
public interface CaseAppealRepository extends MongoRepository<AppealCase, String> {

	public AppealCase findByCaseId(String caseId);
	public List<AppealCase> findByReasonContainingIgnoreCase(String reason);
	
	public List<AppealCase> findByAppealDateBefore(Instant appealDate);
	public List<AppealCase> findByAppealDateAfter(Instant appealDate);
	
}
