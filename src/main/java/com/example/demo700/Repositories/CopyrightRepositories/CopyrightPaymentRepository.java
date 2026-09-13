package com.example.demo700.Repositories.CopyrightRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CopyrightModels.CopyrightPayment;

@Repository
public interface CopyrightPaymentRepository extends MongoRepository<CopyrightPayment, String> {

    @Query("{ 'senderUserId': ?0 }")
    public List<CopyrightPayment> findBySenderUserId(String senderUserId);

    @Query("{ 'senderUserName': { $regex: ?0, $options: 'i' } }")
    public List<CopyrightPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName);

    @Query("{ 'senderPhoneNumber': ?0 }")
    public List<CopyrightPayment> findBySenderPhoneNumber(String senderPhoneNumber);

    @Query("{ 'transactionId': ?0 }")
    public CopyrightPayment findByTransactionId(String transactionId);

    @Query("{ 'copyrightId': ?0 }")
    public List<CopyrightPayment> findByCopyrightId(String copyrightId);

    @Query("{ 'copyrightId': ?0, 'senderUserId': ?1 }")
    public List<CopyrightPayment> findByCopyrightIdAndSenderUserId(String copyrightId, String senderUserId);

    @Query("{ 'sendingTime': { $gte: ?0 } }")
    public List<CopyrightPayment> findBySendingTimeAfter(Instant sendingTime);

    @Query("{ 'sendingTime': { $lte: ?0 } }")
    public List<CopyrightPayment> findBySendingTimeBefore(Instant sendingTime);

    @Query("{ 'receiverPhoneNumber': ?0 }")
    public List<CopyrightPayment> findByReceiverPhoneNumber(String receiverPhoneNumber);

    @Query("{ 'amount': { $gte: ?0 } }")
    public List<CopyrightPayment> findByAmountGreaterThanEqual(double amount);

    @Query("{ 'amount': { $lte: ?0 } }")
    public List<CopyrightPayment> findByAmountLessThanEqual(double amount);

}