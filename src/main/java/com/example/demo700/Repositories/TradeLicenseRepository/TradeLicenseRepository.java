package com.example.demo700.Repositories.TradeLicenseRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.TradeLicenseModels.TradeLicense;

@Repository
public interface TradeLicenseRepository extends MongoRepository<TradeLicense, String> {

	@Query("{ 'userId': ?0 }")
	public List<TradeLicense> findByUserId(String userId);

	@Query("{ 'buisnessName': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByBuisnessNameContainingIgnoreCase(String buisnessName);

	@Query("{ 'mobileNumber': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByMobileNumberContainingIgnoreCase(String mobileNumber);

	@Query("{ 'emailAdress': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByEmailAdressContainingIgnoreCase(String emailAdress);

	@Query("{ 'buisnessType': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByBuisnessTypeContainingIgnoreCase(String buisnessType);

	@Query("{ 'buisnessCategory': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByBuisnessCategoryContainingIgnoreCase(String buisnessCategory);

	@Query("{ 'documents': { $regex: ?0, $options: 'i' } }")
	public List<TradeLicense> findByDocumentsContainingIgnoreCase(String documents);

}