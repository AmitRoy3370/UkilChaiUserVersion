package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;

public interface CaseService {

	public Case updateCase(Case acceptedCase, String userId, String caseRequestId, MultipartFile files[]);
	public Case findByCaseNameIgnoreCase(String caseName);

	public List<Case> findByCaseNameContainingIgnoreCase(String caseName);

	public List<Case> findByUserId(String userId);

	public List<Case> findByCaseType(AdvocateSpeciality caseType);

	public List<Case> findByAdvocateId(String advocateId);

	public List<Case> findByIssuedTimeAfter(Instant issuedTime);

	public List<Case> findByIssuedTimeBefore(Instant issuedTime);
	
	public List<Case> findAllCase();
	
	public Case findById(String caseId);
	
	public boolean removeCase(String caseId, String userId);
	
}
