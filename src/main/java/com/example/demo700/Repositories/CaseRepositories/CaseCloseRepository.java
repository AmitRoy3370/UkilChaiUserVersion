package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.CaseClose;

@Repository
public interface CaseCloseRepository extends MongoRepository<CaseClose, String> {

	public CaseClose findByCaseId(String caseId);
	public List<CaseClose> findByClosedDateBefore(Instant closedDate);
	public List<CaseClose> findByClosedDateAfter(Instant closedDate);
	public List<CaseClose> findByClosedDate(Instant closedDate);
	
	public List<CaseClose> findByOpen(boolean open);
	
}
