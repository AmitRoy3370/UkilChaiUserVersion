package com.example.demo700.Repositories.TrademarkRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.Trademarkmodels.Trademark;

@Repository
public interface TrademarkRepository extends MongoRepository<Trademark, String> {

    @Query("{ 'legalProtection': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByLegalProtectionContainingIgnoreCase(String legalProtection);
    
    @Query("{ 'nationWiseValidity': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByNationWiseValidityContainingIgnoreCase(String nationWiseValidity);
    
    @Query("{ 'applicationType': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByApplicationTypeContainingIgnoreCase(String applicationType);
    
    @Query("{ 'applicationName': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByApplicationNameContainingIgnoreCase(String applicationName);
    
    @Query("{ 'governmentFee': { $gte: ?0 } }")
    public List<Trademark> findByGovernmentFeeGreaterThanEqual(double governmentFee);
    
    @Query("{ 'governmentFee': { $lte: ?0 } }")
    public List<Trademark> findByGovernmentFeeLessThanEqual(double governmentFee);
    
    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    public Trademark findByEmailIgnoreCase(String email);
    
    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByEmailContainingIgnoreCase(String email);
    
    @Query("{ 'mobileNumber': { $regex: ?0, $options: 'i' } }")
    public Trademark findByMobileNumberIgnoreCase(String mobileNumber);
    
    @Query("{ 'mobileNumber': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByMobileNumberContainingIgnoreCase(String mobileNumber);
    
    @Query("{ 'documents': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByDocumentsContainingIgnoreCase(String documents);
    
    @Query("{ 'userId': ?0 }")
    public List<Trademark> findByUserId(String userId);
    
    @Query("{ 'adress': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByAdressContainingIgnoreCase(String adress);
    
    @Query("{ 'trademarkName': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByTrademarkNameContainingIgnoreCase(String trademarkName);
    
    @Query("{ 'trademarkType': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByTrademarkTypeContainingIgnoreCase(String trademarkType);
    
    @Query("{ 'classOfGoods': { $regex: ?0, $options: 'i' } }")
    public List<Trademark> findByClassOfGoodsContainingIgnoreCase(String classOfGoods);
    
}