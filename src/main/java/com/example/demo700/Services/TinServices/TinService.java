package com.example.demo700.Services.TinServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.TinResponseDTO;
import com.example.demo700.Model.TinModels.Tin;

public interface TinService {

	public Tin addTin(Tin tin, String userId, MultipartFile documents[]);
	public Tin updateTin(Tin tin, String userId, String id, MultipartFile documents[]);
	
	public TinResponseDTO findById(String id);
	public List<TinResponseDTO> findAll();
	public List<TinResponseDTO> findByFullNameContainingIgnoreCase(String fullName);
	public List<TinResponseDTO> findByFatherNameContainingIgnoreCase(String fatherName);
	public List<TinResponseDTO> findByMotherNameContainingIgnoreCase(String motherName);
	public List<TinResponseDTO> findByPhoneContainingIgnoreCase(String phone);
	public List<TinResponseDTO> findByDateOfBirthBefore(Instant dateOfBirth);
	public List<TinResponseDTO> findByDateOfBirthAfter(Instant dateOfBirth);
	public List<TinResponseDTO> findByPresentAdressContainingIgnoreCase(String presentAdress);
	public List<TinResponseDTO> findByPermanentAdressContainingIgnoreCase(String permanentAdress);
	public List<TinResponseDTO> findByDocumentsContainingIgnoreCase(String document);
	
	public boolean removeTin(String id, String userId);
	
}
