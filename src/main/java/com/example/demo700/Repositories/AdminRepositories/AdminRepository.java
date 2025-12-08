package com.example.demo700.Repositories.AdminRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdminModels.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
	
	public Admin findByUserId(String userId);
	public List<Admin> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality);

}
