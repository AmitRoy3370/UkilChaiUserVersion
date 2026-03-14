package com.example.demo700.Repositories.CaseRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.CaseModels.CaseTracking;

@Repository
public interface CaseTrackingRepository extends MongoRepository<CaseTracking, String> {

	public List<CaseTracking> findByCaseId(String caseId);
	public List<CaseTracking> findByCaseStage(CasePayment caseStage);
	
	public CaseTracking findByCaseIdAndStageNumber(String caseId, int stageNumber);
	
	public CaseTracking findByCaseIdAndCaseStage(String caseId, CasePayment caseStage);
	
	public List<CaseTracking> findByCaseIdAndStageNumberGreaterThan(String caseId, int stageNumber);
	
}
