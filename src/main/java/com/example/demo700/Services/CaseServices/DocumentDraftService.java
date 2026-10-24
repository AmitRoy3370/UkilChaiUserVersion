package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.DocumentDraftResponse;
import com.example.demo700.Model.CaseModels.DocumentDraft;

public interface DocumentDraftService {
	
	public DocumentDraft addDocumentDraft(DocumentDraft documentDraft, String userId, MultipartFile files[]);
	public DocumentDraft updateDocumentDraft(DocumentDraft documentDraft, String userId, String documentDraftId, MultipartFile files[]);
	
	public List<DocumentDraftResponse> findByAdvocateId(String advocateId);
	public DocumentDraftResponse findByCaseId(String caseId);
	
	public List<DocumentDraftResponse> findByIssuedDateAfter(Instant issuedDate);
	public List<DocumentDraftResponse> findByIssuedDateBefore(Instant issuedDate);
	
	public List<DocumentDraftResponse> seeAll();
	
	public DocumentDraftResponse findById(String id);
	
	public boolean removeDocumentDraft(String id, String userId);

}
