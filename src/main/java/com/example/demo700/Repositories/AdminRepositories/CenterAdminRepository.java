package com.example.demo700.Repositories.AdminRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdminModels.CenterAdmin;

@Repository
public interface CenterAdminRepository extends MongoRepository<CenterAdmin, String> {

	public CenterAdmin findByUserId(String userId);
	public CenterAdmin findByAdminsContainingIgnoreCase(String admin);
	public List<CenterAdmin> findByDistrictsContainingIgnoreCase(String districts);
	public CenterAdmin findByAdvocatesContainingIgnoreCase(String advocates);
	
}
