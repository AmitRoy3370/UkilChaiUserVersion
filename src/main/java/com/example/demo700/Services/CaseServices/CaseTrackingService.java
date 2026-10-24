package com.example.demo700.Services.CaseServices;

import java.util.List;

import com.example.demo700.DTOFiles.CaseTrackingResponse;
import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.CaseModels.CaseTracking;

public interface CaseTrackingService {

	public CaseTracking addCaseTracking(CaseTracking caseTracking, String userId);

	public CaseTracking updateCaseTracking(CaseTracking caseTracking, String userId, String id);

	public List<CaseTrackingResponse> swapOrder(String caseTrackingId1, String caseTrackingId2);
	
	public List<CaseTrackingResponse> findAll();
	
	public List<CaseTrackingResponse> findByVisibility(boolean visibility);

	public List<CaseTrackingResponse> findByCaseId(String caseId);

	public List<CaseTrackingResponse> findByCaseStage(CasePayment caseStage);

	public CaseTrackingResponse findByCaseIdAndStageNumber(String caseId, int stageNumber);

	public CaseTrackingResponse findByCaseIdAndCaseStage(String caseId, CasePayment caseStage);

	public List<CaseTrackingResponse> findByCaseIdAndStageNumberGreaterThan(String caseId, int stageNumber);

	public void processScheduledNotifications();
	
	public boolean removeCaseTracking(String id, String userId);

}
