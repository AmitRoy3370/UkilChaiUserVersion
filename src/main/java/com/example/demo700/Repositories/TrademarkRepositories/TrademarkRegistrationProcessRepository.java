package com.example.demo700.Repositories.TrademarkRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.Trademarkmodels.TrademarkRegistrationProcess;

@Repository
public interface TrademarkRegistrationProcessRepository extends MongoRepository<TrademarkRegistrationProcess, String> {

    @Query("{ 'userId': ?0 }")
    public List<TrademarkRegistrationProcess> findByUserId(String userId);
    
    @Query("{ 'advocateId': ?0 }")
    public List<TrademarkRegistrationProcess> findByAdvocateId(String advocateId);
    
    @Query("{ 'tradeMarkId': ?0 }")
    public TrademarkRegistrationProcess findByTradeMarkId(String tradeMarkId);
    
    @Query("{ 'status': ?0 }")
    public List<TrademarkRegistrationProcess> findByStatus(boolean status);
    
    @Query("{ 'steps': { $regex: ?0, $options: 'i' } }")
    public List<TrademarkRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    
    @Query("{ 'tradeMarkId': { $in: ?0 } }")
    public List<TrademarkRegistrationProcess> findByTrademarkIdIn(List<String> trademarksId);
    
}