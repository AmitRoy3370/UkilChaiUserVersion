package com.example.demo700.Services.RJSCServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.RJSCResponseDTO;
import com.example.demo700.Model.RJSCModels.RJSC;

public interface RJSCService {

	public RJSC addRJSC(RJSC rjsc, String userId, MultipartFile documents[]);
	public RJSC updateRJSC(RJSC rjsc, String userId, String id, MultipartFile documents[]);

	public RJSCResponseDTO findById(String id);
	public List<RJSCResponseDTO> findAll();
	public List<RJSCResponseDTO> findByUserId(String userId);
	public List<RJSCResponseDTO> findByCompilenceServiceContainingIgnoreCase(String compilenceService);
	public List<RJSCResponseDTO> findByRegistrationNoContainingIgnoreCase(String registrationNo);
	public RJSCResponseDTO findByEmail(String email);
	public List<RJSCResponseDTO> findByCompanyNameContainingIgnoreCase(String companyName);
	public List<RJSCResponseDTO> findByYearAfter(Instant year);
	public List<RJSCResponseDTO> findByYearBefore(Instant year);
	public List<RJSCResponseDTO> findByDocuments(String documents);

	public boolean delete(String id, String userId);
	
}
