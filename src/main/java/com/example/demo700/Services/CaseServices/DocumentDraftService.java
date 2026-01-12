package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.CaseModels.DocumentDraft;

public interface DocumentDraftService {
	
	public DocumentDraft addDocumentDraft(DocumentDraft documentDraft, String userId, MultipartFile files[]);
	public DocumentDraft updateDocumentDraft(DocumentDraft documentDraft, String userId, String documentDraftId, MultipartFile files[]);
	
	public List<DocumentDraft> findByAdvocateId(String advocateId);
	public DocumentDraft findByCaseId(String caseId);
	
	public List<DocumentDraft> findByIssuedDateAfter(Instant issuedDate);
	public List<DocumentDraft> findByIssuedDateBefore(Instant issuedDate);
	
	public List<DocumentDraft> seeAll();
	
	public DocumentDraft findById(String id);
	
	public boolean removeDocumentDraft(String id, String userId);

}
