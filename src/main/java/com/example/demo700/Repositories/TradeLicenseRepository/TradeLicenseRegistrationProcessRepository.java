package com.example.demo700.Repositories.TradeLicenseRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.TradeLicenseModels.TradeLicenseRegistrationProcess;

@Repository
public interface TradeLicenseRegistrationProcessRepository extends MongoRepository<TradeLicenseRegistrationProcess, String> {

    @Query("{ 'userId': ?0 }")
    public List<TradeLicenseRegistrationProcess> findByUserId(String userId);
    
    @Query("{ 'advocateId': ?0 }")
    public List<TradeLicenseRegistrationProcess> findByAdvocateId(String advocateId);
    
    @Query("{ 'status': ?0 }")
    public List<TradeLicenseRegistrationProcess> findByStatus(boolean status);
    
    @Query("{ 'tradeLicenseId': ?0 }")
    public TradeLicenseRegistrationProcess findByTradeLicenseId(String tradeLicenseId);
    
    @Query("{ 'steps': { $regex: ?0, $options: 'i' } }")
    public List<TradeLicenseRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    
    public List<TradeLicenseRegistrationProcess> findByTradeLicenseIdIn(List<String> tradeLicensesId);
    
}