package com.example.demo700.Services.TrademarkServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.DTOFiles.TrademarkPaymentResponse;
import com.example.demo700.Model.Trademarkmodels.TrademarkPayment;

public interface TrademarkPaymentService {

	public TrademarkPayment addTrademarkPayment(TrademarkPayment payment, String userId);
	public TrademarkPayment updateTrademarkPayment(TrademarkPayment payment, String userId, String id);
	
	public TrademarkPaymentResponse findById(String id);
	public List<TrademarkPaymentResponse> findAll();
	public List<TrademarkPaymentResponse> findBySenderUserId(String senderUserId);
    public List<TrademarkPaymentResponse> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);
    public List<TrademarkPaymentResponse> findByReceiverPhoneNumberContaingingIgnoreCase(String receiverPhoneNumber);
    public List<TrademarkPaymentResponse> findBySenderUserIdAndTrademarkId(String trademarkId, String senderUserId);
    public List<TrademarkPaymentResponse> findByTradeMarkId(String tradeMarkId);
    public TrademarkPaymentResponse findByTransactionId(String transactionId);
    public List<TrademarkPaymentResponse> findByTransactionIdContainingIgnoreCase(String transactionId);
    public List<TrademarkPaymentResponse> findByAmountGreaterThanEqual(double amount);
    public List<TrademarkPaymentResponse> findByAmountLessThanEqual(double amount);
    public List<TrademarkPaymentResponse> findBySendingTimeBefore(Instant sendingTime);
    public List<TrademarkPaymentResponse> findBySendingTimeAfter(Instant sendingTime);

    public boolean deleteTrademarkPayment(String id);
    
}
