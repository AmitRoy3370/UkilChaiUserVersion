package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.HearingResponse;
import com.example.demo700.Model.CaseModels.Hearing;

public interface HearingService {

	public Hearing addHearing(Hearing hearing, String userId, MultipartFile files[]);
	public Hearing updateHearing(Hearing hearing, String userId, String hearingId, MultipartFile files[]);
	
	public List<HearingResponse> findByHearningNumber(int hearningNumber);
	public List<HearingResponse> findByCaseId(String caseId);
	
	public List<HearingResponse> findByIssuedDateAfter(Instant date);
	
	public List<HearingResponse> findByIssuedDateBefore(Instant date);
	public HearingResponse findByCaseIdAndHearingNumber(String caseId, int hearingNumber);
	public List<HearingResponse> findByCaseIdAndHearingNumberLessThanEqual(String caseId, int hearingNumber);
	
	public List<HearingResponse> seeAll();
	
	public HearingResponse findById(String id);
	
	public boolean removeHearing(String id, String userId);
	
}
