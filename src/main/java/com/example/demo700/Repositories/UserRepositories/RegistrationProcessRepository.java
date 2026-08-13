package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.RegistrationProcess;

@Repository
public interface RegistrationProcessRepository extends MongoRepository<RegistrationProcess, String> {

	public List<RegistrationProcess> findByCompanyId(String companyId);
	public List<RegistrationProcess> findByAdvocateId(String advocateId);
	public List<RegistrationProcess> findByUserId(String userId);
	public List<RegistrationProcess> findByStatus(boolean status);
	public List<RegistrationProcess> findByShareValuePerShareLte(double shareValuePerShare);
	public List<RegistrationProcess> findByShareValuePerShareGte(double shareValuePerShare);
	
}
