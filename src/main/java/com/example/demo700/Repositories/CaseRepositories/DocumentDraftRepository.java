package com.example.demo700.Repositories.CaseRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CaseModels.DocumentDraft;

@Repository
public interface DocumentDraftRepository extends MongoRepository<DocumentDraft, String> {

	public List<DocumentDraft> findByAdvocateId(String advocateId);
	public DocumentDraft findByCaseId(String caseId);
	
	public List<DocumentDraft> findByIssuedDateAfter(Instant issuedDate);
	public List<DocumentDraft> findByIssuedDateBefore(Instant issuedDate);
	
}
