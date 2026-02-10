package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.CaseModels.CaseClose;

public interface CaseCloseService {

	public CaseClose addCaseClose(CaseClose caseClose, String userId);

	public CaseClose updateCaseClose(CaseClose caseClose, String userId, String closedCaseId);

	public CaseClose findById(String id);

	public List<CaseClose> findAll();

	public CaseClose findByCaseId(String caseId);

	public List<CaseClose> findByClosedDateBefore(Instant closedDate);

	public List<CaseClose> findByClosedDateAfter(Instant closedDate);

	public List<CaseClose> findByClosedDate(Instant closedDate);

	public boolean deleteClosedCase(String closedCaseId, String userId);
	
	public List<CaseClose> findByOpen(boolean open);

}
