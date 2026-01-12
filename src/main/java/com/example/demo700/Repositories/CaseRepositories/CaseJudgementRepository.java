package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.CaseJudgment;

@Repository
public interface CaseJudgementRepository extends MongoRepository<CaseJudgment, String> {

	public CaseJudgment findByCaseId(String caseId);
	public List<CaseJudgment> findByResultContainingIgnoreCase(String result);
	
	public List<CaseJudgment> findByDateAfter(Instant date);
	public List<CaseJudgment> findByDateBefore(Instant date);
	
}
