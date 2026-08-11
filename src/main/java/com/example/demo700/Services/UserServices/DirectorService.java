package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.Director;

public interface DirectorService {

	public Director addDirector(Director director, String userId, MultipartFile nid);
	public Director updateDirector(Director director, String userId, String id, MultipartFile nid);
	
	public Director findById(String id);
	public List<Director> findAll();
	public Director findByUserId(String userId);
	public List<Director> findByNid(String nid);
	public List<Director> findByPosition(String position);
	
	public boolean removeDirector(String id, String userId);
	
}
