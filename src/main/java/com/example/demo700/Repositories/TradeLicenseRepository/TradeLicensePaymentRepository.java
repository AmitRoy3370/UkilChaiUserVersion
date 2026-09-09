package com.example.demo700.Repositories.TradeLicenseRepository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.TradeLicenseModels.TradeLicensePayment;

@Repository
public interface TradeLicensePaymentRepository extends MongoRepository<TradeLicensePayment, String> {

    @Query("{ 'senderUserId': ?0 }")
    public List<TradeLicensePayment> findBySenderUserId(String senderUserId);
    
    @Query("{ 'senderPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<TradeLicensePayment> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);
    
    @Query("{ 'receiverPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<TradeLicensePayment> findByReceiverPhoneNumberContainingIgnoreCase(String receiverPhoneNumber);
    
    @Query("{ 'transactionId': { $regex: ?0, $options: 'i' } }")
    public List<TradeLicensePayment> findByTransactionIdContainingIgnoreCase(String transactionId);
    
    public TradeLicensePayment findByTransactionIdIgnoreCase(String transactionId);
    
    @Query("{ 'amount': { $gte: ?0 } }")
    public List<TradeLicensePayment> findByAmountGreaterThanEqual(double amount);
    
    @Query("{ 'amount': { $lte: ?0 } }")
    public List<TradeLicensePayment> findByAmountLessThanEqual(double amount);
    
    @Query("{ 'tradeLicenseId': ?0 }")
    public List<TradeLicensePayment> findByTradeLicenseId(String tradeLicenseId);
    
    @Query("{ 'sendingTime': { $lte: ?0 } }")
    public List<TradeLicensePayment> findBySendingTimeBefore(Instant sendingTime);
    
    @Query("{ 'sendingTime': { $gte: ?0 } }")
    public List<TradeLicensePayment> findBySendingTimeAfter(Instant sendingTime);
    
}