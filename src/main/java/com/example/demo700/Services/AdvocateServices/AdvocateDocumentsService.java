package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;

public interface AdvocateDocumentsService {

	public AdvocateDocuments addDocuments(String advocateId, MultipartFile files[]);
	public AdvocateDocuments updateDocuments(String id, String userId, AdvocateDocuments documents, MultipartFile files[]);
	
	public AdvocateDocuments findById(String id);
	public List<AdvocateDocuments> findAll();
	AdvocateDocuments findByAdvocateId(String advocateId);
	List<AdvocateDocuments> findByAttachmentsContainingIgnoreCase(String attachments);
	
	public boolean deleteDocuments(String id, String userId);
	
}
