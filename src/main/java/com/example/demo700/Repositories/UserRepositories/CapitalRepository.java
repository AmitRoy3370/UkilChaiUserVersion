package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Capital;

@Repository
public interface CapitalRepository extends MongoRepository<Capital, String> {

	@Query("{ 'companyId' : ?0 }")
	public List<Capital> findByCompanyId(String companyId);

	@Query("{ 'authorizedCapital' : { $lte: ?0 } }")
	public List<Capital> findByAuthorizedCapitalLte(double authorizedCapital);

	@Query("{ 'authorizedCapital' : { $gte: ?0 } }")
	public List<Capital> findByAuthorizedCapitalGte(double authorizedCapital);

	@Query("{ 'totalShare' : { $lte: ?0 } }")
	public List<Capital> findByTotalShareLte(int totalShare);

	@Query("{ 'totalShare' : { $gte: ?0 } }")
	public List<Capital> findByTotalShareGte(int totalShare);

	@Query("{ 'numberOfShare' : { $lte: ?0 } }")
	public List<Capital> findByNumberOfShareLte(int numberOfShare);

	@Query("{ 'numberOfShare' : { $gte: ?0 } }")
	public List<Capital> findByNumberOfShareGte(int numberOfShare);

	@Query("{ 'shareValue' : { $lte: ?0 } }")
	public List<Capital> findByShareValueLte(double shareValue);

	@Query("{ 'shareValue' : { $gte: ?0 } }")
	public List<Capital> findByShareValueGte(double shareValue);

}