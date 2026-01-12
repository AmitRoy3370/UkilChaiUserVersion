package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.CaseModels.Hearing;

public interface HearingService {

	public Hearing addHearing(Hearing hearing, String userId, MultipartFile files[]);
	public Hearing updateHearing(Hearing hearing, String userId, String hearingId, MultipartFile files[]);
	
	public List<Hearing> findByHearningNumber(int hearningNumber);
	public List<Hearing> findByCaseId(String caseId);
	
	public List<Hearing> findByIssuedDateAfter(Instant date);
	
	public List<Hearing> findByIssuedDateBefore(Instant date);
	public Hearing findByCaseIdAndHearingNumber(String caseId, int hearingNumber);
	public List<Hearing> findByCaseIdAndHearingNumberLessThanEqual(String caseId, int hearingNumber);
	
	public List<Hearing> seeAll();
	
	public Hearing findById(String id);
	
	public boolean removeHearing(String id, String userId);
	
}
