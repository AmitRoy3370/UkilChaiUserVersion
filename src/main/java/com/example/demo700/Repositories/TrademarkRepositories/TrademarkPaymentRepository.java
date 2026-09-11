package com.example.demo700.Repositories.TrademarkRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.Trademarkmodels.TrademarkPayment;

@Repository
public interface TrademarkPaymentRepository extends MongoRepository<TrademarkPayment, String> {

    @Query("{ 'senderUserId': ?0 }")
    public List<TrademarkPayment> findBySenderUserId(String senderUserId);

    @Query("{ 'senderPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<TrademarkPayment> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);

    @Query("{ 'receiverPhoneNumber': { $regex: ?0, $options: 'i' } }")
    public List<TrademarkPayment> findByReceiverPhoneNumberContaingingIgnoreCase(String receiverPhoneNumber);

    @Query("{ 'tradeMarkId': ?0, 'senderUserId': ?1 }")
    public List<TrademarkPayment> findBySenderUserIdAndTrademarkId(String trademarkId, String senderUserId);

    @Query("{ 'tradeMarkId': ?0 }")
    public List<TrademarkPayment> findByTradeMarkId(String tradeMarkId);

    @Query("{ 'transactionId': ?0 }")
    public TrademarkPayment findByTransactionId(String transactionId);

    @Query("{ 'transactionId': { $regex: ?0, $options: 'i' } }")
    public List<TrademarkPayment> findByTransactionIdContainingIgnoreCase(String transactionId);

    @Query("{ 'amount': { $gte: ?0 } }")
    public List<TrademarkPayment> findByAmountGreaterThanEqual(double amount);

    @Query("{ 'amount': { $lte: ?0 } }")
    public List<TrademarkPayment> findByAmountLessThanEqual(double amount);

    @Query("{ 'sendingTime': { $lte: ?0 } }")
    public List<TrademarkPayment> findBySendingTimeBefore(Instant sendingTime);

    @Query("{ 'sendingTime': { $gte: ?0 } }")
    public List<TrademarkPayment> findBySendingTimeAfter(Instant sendingTime);

}
