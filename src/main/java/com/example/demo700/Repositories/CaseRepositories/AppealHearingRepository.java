package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.AppealHearings;

@Repository
public interface AppealHearingRepository extends MongoRepository<AppealHearings, String> {

	public AppealHearings findByHearingId(String hearingId);
	public List<AppealHearings> findByReasonContainingIgnoreCase(String reason);
	
	public List<AppealHearings> findByAppealHearingTimeBefore(Instant appealHearingTime);
	
	public List<AppealHearings> findByAppealHearingTimeAfter(Instant appealHearingTime);
	
}
