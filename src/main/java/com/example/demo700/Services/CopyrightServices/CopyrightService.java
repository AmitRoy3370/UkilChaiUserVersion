package com.example.demo700.Services.CopyrightServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CopyrightResponse;
import com.example.demo700.Model.CopyrightModels.Copyright;

public interface CopyrightService {

	public Copyright addCopyright(Copyright copyright, String userId, MultipartFile documents[]);
	public Copyright updateCopyright(Copyright copyright, String userId, String id, MultipartFile documents[]);
	
	public CopyrightResponse findById(String id);
	public List<CopyrightResponse> findAll();
	public List<CopyrightResponse> findByUserId(String userId);
    public List<CopyrightResponse> findByAuthorContainingIgnoreCase(String author);
    public List<CopyrightResponse> findByTypeOfWorkContainingIgnoreCase(String typeOfWork);
    public List<CopyrightResponse> findByYearOfCreationAfter(Instant yearOfCreation);
    public List<CopyrightResponse> findByYearOfCreationBefore(Instant yearOfCreation);
    public List<CopyrightResponse> findByTitleOfWorkContainingIgnoreCase(String titleOfWork);
    public List<CopyrightResponse> findByDescriptionContainingIgnoreCase(String description);
    public List<CopyrightResponse> findByApplicationNameContainingIgnoreCase(String applicationName);
    public CopyrightResponse findByMobileNumber(String mobileNumber);
    public CopyrightResponse findByEmail(String email);
    public List<CopyrightResponse> findByAdressContainingIgnoreCase(String adress);
    public List<CopyrightResponse> findByDocumentsContainingIgnoreCase(String documents);
	
    public boolean removeCopyright(String id, String userId);
    
}
