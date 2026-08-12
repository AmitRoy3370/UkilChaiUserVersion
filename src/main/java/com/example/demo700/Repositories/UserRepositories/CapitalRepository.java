package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Capital;

@Repository
public interface CapitalRepository extends MongoRepository<Capital, String> {

	public List<Capital> findByCompanyId(String companyId);

	public List<Capital> findByAuthorizedCapitalLte(double authorizedCapital);

	public List<Capital> findByAuthorizedCapitalGte(double authorizedCapital);

	public List<Capital> findByTotalShareLte(int totalShare);

	public List<Capital> findByTotalShareGte(int totalShare);

	public List<Capital> findByNumberOfShareLte(int numberOfShare);

	public List<Capital> findByNumberOfShareGte(int numberOfShare);

	public List<Capital> findByShareValueLte(double shareValue);

	public List<Capital> findByShareValueGte(double shareValue);

}
