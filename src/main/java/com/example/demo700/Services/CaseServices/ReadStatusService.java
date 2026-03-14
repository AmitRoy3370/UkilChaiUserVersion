package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.CaseModels.ReadStatus;

public interface ReadStatusService {

	public ReadStatus addReadStatus(ReadStatus readStatus, String userId);

	public ReadStatus updateReadStatus(ReadStatus readStatus, String userId, String id);

	public List<ReadStatus> findAll();
	
	public List<ReadStatus> findByCaseId(String caseId);

	public List<ReadStatus> findByAdvocateId(String advocateId);

	public List<ReadStatus> findByStatusContainingIgnoreCase(String status);

	public List<ReadStatus> findByIssuedTimeBefore(Instant issuedTime);

	public List<ReadStatus> findByIssuedTimeAfter(Instant issuedTime);

	public List<ReadStatus> findByIssuedTime(Instant issuedTime);

	public boolean deleteReadStatus(String id, String userId);

}
