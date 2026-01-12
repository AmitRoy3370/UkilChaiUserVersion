package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.CaseModels.AppealHearings;

public interface AppealHearingService {
	
	public AppealHearings addAppealHearings(AppealHearings appealHearings, String userId);

	public AppealHearings updateAppealHearings(AppealHearings appealHearings, String userId, String appealHearingsId);
	
	public AppealHearings findByHearingId(String hearingId);
	public List<AppealHearings> findByReasonContainingIgnoreCase(String reason);
	
	public List<AppealHearings> findByAppealHearingTimeBefore(Instant appealHearingTime);
	
	public List<AppealHearings> findByAppealHearingTimeAfter(Instant appealHearingTime);
	
	public List<AppealHearings> seeAll();
	
	public AppealHearings findById(String id);
	
	public boolean removeAppealHearings(String id, String userId);
	
}
