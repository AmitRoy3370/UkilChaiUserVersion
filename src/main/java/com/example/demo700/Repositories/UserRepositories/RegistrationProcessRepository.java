package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.RegistrationProcess;

@Repository
public interface RegistrationProcessRepository extends MongoRepository<RegistrationProcess, String> {

	@Query("{ 'companyId' : ?0 }")
	public List<RegistrationProcess> findByCompanyId(String companyId);
	
	@Query("{ 'advocateId' : ?0 }")
	public List<RegistrationProcess> findByAdvocateId(String advocateId);
	
	@Query("{ 'userId' : ?0 }")
	public List<RegistrationProcess> findByUserId(String userId);
	
	@Query("{ 'status' : ?0 }")
	public List<RegistrationProcess> findByStatus(boolean status);
	
	@Query("{ 'shareValuePerShare' : { $lte: ?0 } }")
	public List<RegistrationProcess> findByShareValuePerShareLte(double shareValuePerShare);
	
	@Query("{ 'shareValuePerShare' : { $gte: ?0 } }")
	public List<RegistrationProcess> findByShareValuePerShareGte(double shareValuePerShare);
	
}