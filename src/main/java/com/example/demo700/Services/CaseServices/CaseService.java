package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CaseListResponseDTO;
import com.example.demo700.DTOFiles.CaseResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseTracking;

public interface CaseService {

	public Case updateCase(Case acceptedCase, String userId, String caseRequestId, MultipartFile files[]);

	public CaseResponse findByCaseNameIgnoreCase(String caseName);

	public List<CaseResponse> findByCaseNameContainingIgnoreCase(String caseName);
	
	public CaseListResponseDTO findByCaseNameContainingIgnoreCase(String caseName, Pageable pageable);

	public List<CaseResponse> findByUserId(String userId);
	
	public CaseListResponseDTO findByUserId(String userId, Pageable pageable);

	public List<CaseResponse> findByCaseType(AdvocateSpeciality caseType);
	
	public CaseListResponseDTO findByCaseType(AdvocateSpeciality caseType, Pageable pageable);

	public List<CaseResponse> findByAdvocateId(String advocateId);
	
	public CaseListResponseDTO findByAdvocateId(String advocateId, Pageable pageable);

	public List<CaseResponse> findByIssuedTimeAfter(Instant issuedTime);
	
	public CaseListResponseDTO findByIssuedTimeAfter(Instant issuedTime, Pageable pageable);

	public List<CaseResponse> findByIssuedTimeBefore(Instant issuedTime);
	
	public CaseListResponseDTO findByIssuedTimeBefore(Instant issuedTime, Pageable pageable);

	public List<CaseResponse> findAllCase();
	
	public CaseListResponseDTO findAllCase(Pageable pageable);

	public CaseResponse findById(String caseId);

	public boolean removeCase(String caseId, String userId);

}
