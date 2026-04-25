package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CaseRequestResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseRequest;

public interface CaseRequestService {

	public CaseRequest addCaseRequest(CaseRequest caseRequest, String userId, MultipartFile files[]);

	public CaseRequest updateCaseRequest(CaseRequest caseRequest, String userId, String caseRequestId,
			MultipartFile files[]);

	public List<CaseRequestResponse> findByCaseNameContainingIgnoreCase(String caseName);

	public List<CaseRequestResponse> findByUserId(String userId);

	public List<CaseRequestResponse> findByCaseType(AdvocateSpeciality caseType);

	public List<CaseRequestResponse> findByIssuedTimeAfter(Instant issuedTime);

	public List<CaseRequestResponse> findByIssuedTimeBefore(Instant issuedTime);

	public CaseRequestResponse findById(String caseRequestId);

	public List<CaseRequestResponse> allCaseRequest();

	public List<CaseRequestResponse> findByRequestedAdvocateId(String requestedAdvocateId);

	public boolean removeCaseRequest(String caseRequestId, String userId);

	public Case handleCaseRequest(String caseRequestId, String userId);

}
