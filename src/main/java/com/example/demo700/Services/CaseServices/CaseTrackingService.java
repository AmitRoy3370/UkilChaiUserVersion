package com.example.demo700.Services.CaseServices;

import java.util.List;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.CaseModels.CaseTracking;

public interface CaseTrackingService {
	
	public CaseTracking addCaseTracking(CaseTracking caseTracking, String userId);

	public CaseTracking updateCaseTracking(CaseTracking caseTracking, String userId, String id);
	
	public List<CaseTracking> findAll();
	
	public List<CaseTracking> findByCaseId(String caseId);
	
	public List<CaseTracking> findByCaseStage(CasePayment caseStage);
	
	public CaseTracking findByCaseIdAndStageNumber(String caseId, int stageNumber);
	
	public CaseTracking findByCaseIdAndCaseStage(String caseId, CasePayment caseStage);
	
	public boolean removeCaseTracking(String id, String userId);
	
}
