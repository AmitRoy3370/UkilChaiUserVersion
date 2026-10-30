package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;
import com.example.demo700.Model.AdvocateModels.AdvocateDocumentsJoinRequest;

public interface AdvocateDocumentsJoinRequestService {

	public AdvocateDocumentsJoinRequest addDocuments(String advocateId, MultipartFile files[]);
	public AdvocateDocumentsJoinRequest updateDocuments(String id, String userId,
			AdvocateDocumentsJoinRequest documents, MultipartFile files[]);

	public AdvocateDocumentsJoinRequest findById(String id);
	public List<AdvocateDocumentsJoinRequest> findAll();
	AdvocateDocumentsJoinRequest findByAdvocateId(String advocateId);
	List<AdvocateDocumentsJoinRequest> findByAttachmentsContainingIgnoreCase(String attachments);

	public boolean deleteDocuments(String id, String userId);

}
