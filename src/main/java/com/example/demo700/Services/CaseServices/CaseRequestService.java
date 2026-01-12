package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseRequest;

public interface CaseRequestService {

	public CaseRequest addCaseRequest(CaseRequest caseRequest, String userId, MultipartFile files[]);
	public CaseRequest updateCaseRequest(CaseRequest caseRequest, String userId, String caseRequestId, MultipartFile files[]);
	public List<CaseRequest> findByCaseNameContainingIgnoreCase(String caseName);
	public List<CaseRequest> findByUserId(String userId);
	
	public List<CaseRequest> findByCaseType(AdvocateSpeciality caseType);
	
	public List<CaseRequest> findByIssuedTimeAfter(Instant issuedTime);
	
	public List<CaseRequest> findByIssuedTimeBefore(Instant issuedTime);
	
	public CaseRequest findById(String caseRequestId);
	
	public List<CaseRequest> allCaseRequest();
	
	public boolean removeCaseRequest(String caseRequestId, String userId);
	
	public Case handleCaseRequest(String caseRequestId, String userId);
	
}
