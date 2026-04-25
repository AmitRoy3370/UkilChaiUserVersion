package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CaseResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseTracking;

public interface CaseService {

	public Case updateCase(Case acceptedCase, String userId, String caseRequestId, MultipartFile files[]);

	public CaseResponse findByCaseNameIgnoreCase(String caseName);

	public List<CaseResponse> findByCaseNameContainingIgnoreCase(String caseName);

	public List<CaseResponse> findByUserId(String userId);

	public List<CaseResponse> findByCaseType(AdvocateSpeciality caseType);

	public List<CaseResponse> findByAdvocateId(String advocateId);

	public List<CaseResponse> findByIssuedTimeAfter(Instant issuedTime);

	public List<CaseResponse> findByIssuedTimeBefore(Instant issuedTime);

	public List<CaseResponse> findAllCase();

	public CaseResponse findById(String caseId);

	public boolean removeCase(String caseId, String userId);

}
