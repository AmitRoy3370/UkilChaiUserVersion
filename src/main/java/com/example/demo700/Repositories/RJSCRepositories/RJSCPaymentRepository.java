package com.example.demo700.Repositories.RJSCRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.RJSCModels.RJSCPayment;

@Repository
public interface RJSCPaymentRepository extends MongoRepository<RJSCPayment, String> {

    @Query("{ 'senderUserId': ?0 }")
    public List<RJSCPayment> findBySenderUserId(String senderUserId);

    @Query("{ 'senderUserName': { $regex: ?0, $options: 'i' } }")
    public List<RJSCPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName);

    @Query("{ 'senderPhoneNumber': ?0 }")
    public List<RJSCPayment> findBySenderPhoneNumber(String senderPhoneNumber);

    @Query("{ 'receiverPhoneNumber': ?0 }")
    public List<RJSCPayment> findByReceiverPhoneNumber(String receiverPhoneNumber);

    @Query("{ 'transactionId': { $regex: ?0, $options: 'i' } }")
    public List<RJSCPayment> findByTransactionIdContainingIgnoreCase(String transactionId);

    @Query("{ 'transactionId': ?0 }")
    public RJSCPayment findByTransactionId(String transactionId);

    @Query("{ 'amount': { $gte: ?0 } }")
    public List<RJSCPayment> findByAmountGreaterThanEqual(double amount);

    @Query("{ 'amount': { $lte: ?0 } }")
    public List<RJSCPayment> findByAmountLessThanEquak(double amount);

    @Query("{ 'sendingTime': { $gte: ?0 } }")
    public List<RJSCPayment> findBySendingTimeAfter(Instant sendingTime);

    @Query("{ 'sendingTime': { $lte: ?0 } }")
    public List<RJSCPayment> findBySendingTimeBefore(Instant sendingTime);

    @Query("{ 'senderUserId': ?0, 'rjscId': ?1 }")
    public List<RJSCPayment> findBySenderUserIdAndRJSCId(String senderUserId, String rjscId);

    @Query("{ 'rjscId': ?0 }")
    public List<RJSCPayment> findByRJSCId(String rjscId);

}