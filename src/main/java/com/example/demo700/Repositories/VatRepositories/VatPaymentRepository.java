package com.example.demo700.Repositories.VatRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.VatModels.VatPayment;

@Repository
public interface VatPaymentRepository extends MongoRepository<VatPayment, String> {

    // ==================== BASIC QUERIES ====================

    @Query("{ 'senderUserId': ?0 }")
    public List<VatPayment> findBySenderUserId(String senderUserId);

    @Query("{ 'senderPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<VatPayment> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);

    @Query("{ 'receiverPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<VatPayment> findByReceiverPhoneNumberContainingIgnoreCase(String receiverPhoneNumber);

    // ==================== VAT ID QUERIES ====================

    @Query("{ 'vatId': ?0 }")
    public List<VatPayment> findByVatId(String vatId);

    @Query("{ 'vatId': ?0, 'senderUserId': ?1 }")
    public List<VatPayment> findByVatIdAndSenderUserId(String vatId, String senderUserId);

    // ==================== AMOUNT QUERIES ====================

    @Query("{ 'amount': { $gte: ?0 } }")
    public List<VatPayment> findByAmountGreaterThanEqual(double amount);

    @Query("{ 'amount': { $lte: ?0 } }")
    public List<VatPayment> findByAmountLessThanEqual(double amount);

    // ==================== TRANSACTION QUERIES ====================

    @Query("{ 'transactionId': { $regex: ?0, $options: 'i' } }")
    public List<VatPayment> findByTransactionIdContainingIgnoreCase(String transactionId);

    @Query("{ 'transactionId': ?0 }")
    public VatPayment findByTransactionId(String transactionId);

    // ==================== DATE QUERIES ====================

    @Query("{ 'sendingTime': { $gte: ?0 } }")
    public List<VatPayment> findBySendingTimeAfter(Instant sendingTime);

    @Query("{ 'sendingTime': { $lte: ?0 } }")
    public List<VatPayment> findBySendingTimeBefore(Instant sendingTime);

}