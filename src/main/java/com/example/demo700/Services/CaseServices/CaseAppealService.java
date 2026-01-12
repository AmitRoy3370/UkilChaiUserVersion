package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.CaseModels.AppealCase;

public interface CaseAppealService {

	public AppealCase addAppealCase(AppealCase caseAppeal, String userId);
	public AppealCase updateAppealCase(AppealCase appealCase, String userId, String appealCaseId);
	public AppealCase findByCaseId(String caseId);
	public List<AppealCase> findByReasonContaingingIgnoreCase(String reason);
	
	public List<AppealCase> findByAppealDateBefore(Instant appealDate);
	public List<AppealCase> findByAppealDateAfter(Instant appealDate);
	public List<AppealCase> seeAll();
	public AppealCase findById(String id);
	
	public boolean removeAppealCase(String id, String userId);
	
}
