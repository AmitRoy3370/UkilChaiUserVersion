package com.example.demo700.Services.RJSCServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.RJSCModels.RJSCPayment;

public interface RJSCPaymentService {

	public RJSCPayment addPayment(RJSCPayment payment, String userId);
	public RJSCPayment updatePayment(RJSCPayment payment, String userId, String id);
	
	public RJSCPayment findById(String id);
	public List<RJSCPayment> findAll();
	public List<RJSCPayment> findBySenderUserId(String senderUserId);
    public List<RJSCPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName);
    public List<RJSCPayment> findBySenderPhoneNumber(String senderPhoneNumber);
    public List<RJSCPayment> findByReceiverPhoneNumber(String receiverPhoneNumber);
    public List<RJSCPayment> findByTransactionIdContainingIgnoreCase(String transactionId);
    public RJSCPayment findByTransactionId(String transactionId);
    public List<RJSCPayment> findByAmountGreaterThanEqual(double amount);
    public List<RJSCPayment> findByAmountLessThanEquak(double amount);
    public List<RJSCPayment> findBySendingTimeAfter(Instant sendingTime);
    public List<RJSCPayment> findBySendingTimeBefore(Instant sendingTime);
    public List<RJSCPayment> findBySenderUserIdAndRJSCId(String senderUserId, String rjscId);
    public List<RJSCPayment> findByRJSCId(String rjscId);
    
    public boolean delete(String id, String userId);
	
}
