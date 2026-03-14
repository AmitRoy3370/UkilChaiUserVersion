package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.ReadStatus;

@Repository
public interface ReadStatusRepository extends MongoRepository<ReadStatus, String> {

	public List<ReadStatus> findByCaseId(String caseId);

	public List<ReadStatus> findByAdvocateId(String advocateId);

	public List<ReadStatus> findByStatusContainingIgnoreCase(String status);

	public List<ReadStatus> findByIssuedTimeBefore(Instant issuedTime);

	public List<ReadStatus> findByIssuedTimeAfter(Instant issuedTime);

	public List<ReadStatus> findByIssuedTime(Instant issuedTime);

}
