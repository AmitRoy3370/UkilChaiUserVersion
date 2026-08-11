package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Shareholder;

@Repository
public interface ShareholderRepository extends MongoRepository<Shareholder, String> {

	public Shareholder findByUserId(String userId);

	public List<Shareholder> findByNid(String nid);

	public List<Shareholder> findByTin(String tin);

	@Query("{ 'sharePercentage.?0': { $exists: true } }")
	public List<Shareholder> findByShareCompanyId(String companyId);

	@Query("{ 'sharePercentage.?0': ?1 }")
	public List<Shareholder> findByShareCompanyIdAndPercentage(String companyId, Double percentage);

	@Query("{ 'sharePercentage.?0': { $elemMatch: { $gte: ?1 } } }")
	public List<Shareholder> findByShareCompanyIdAndPercentageGte(String companyId, Double percentage);

	@Query("{ 'sharePercentage.?0': { $elemMatch: { $lte: ?1 } } }")
	public List<Shareholder> findByShareCompanyIdAndPercentageLte(String companyId, Double percentage);

	@Query("{ 'sharePercentage.?0': { $elemMatch: { $gte: ?1, $lte: ?2 } } }")
	public List<Shareholder> findByShareCompanyIdAndPercentageBetween(String companyId, Double minPercentage,
			Double maxPercentage);

}