package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.DirectorResponse;
import com.example.demo700.Model.UserModels.Director;

public interface DirectorService {

	public Director addDirector(Director director, String userId, MultipartFile nid);
	public Director updateDirector(Director director, String userId, String id, MultipartFile nid);
	
	public DirectorResponse findById(String id);
	public List<DirectorResponse> findAll();
	public List<DirectorResponse> findByUserId(String userId);
	public List<DirectorResponse> findByNid(String nid);
	public List<DirectorResponse> findByPosition(String position);
	public List<DirectorResponse> findByFullNameContainingIgnoreCase(String fullName);
	public List<DirectorResponse> findByFatherNameContainingIgnoreCase(String fatherName);
	public List<DirectorResponse> findByMotherNameContainingIgnoreCase(String motherName);
	public List<DirectorResponse> findByMobileNumberContainingIgnoreCase(String mobileNumber);
	public List<DirectorResponse> findByEmailContainingIgnoreCase(String email);
	
	public boolean removeDirector(String id, String userId);
	
}
