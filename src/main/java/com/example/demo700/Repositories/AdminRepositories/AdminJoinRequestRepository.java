package com.example.demo700.Repositories.AdminRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdminModels.AdminJoinRequest;

@Repository
public interface AdminJoinRequestRepository extends MongoRepository<AdminJoinRequest, String> {

	public AdminJoinRequest findByUserId(String userId);
	public List<AdminJoinRequest> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality);

	
}
