package com.example.demo700.Services.CopyrightServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.example.demo700.Model.CopyrightModels.CopyrightPayment;

public interface CopyrightPaymentService {

	public CopyrightPayment addPayment(CopyrightPayment payment, String userId);
	public CopyrightPayment updatePayment(CopyrightPayment payment, String userId, String id);
	
	public CopyrightPayment findById(String id);
	public List<CopyrightPayment> findAll();
	public List<CopyrightPayment> findBySenderUserId(String senderUserId);
    public List<CopyrightPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName);
    public List<CopyrightPayment> findBySenderPhoneNumber(String senderPhoneNumber);
    public CopyrightPayment findByTransactionId(String transactionId);
    public List<CopyrightPayment> findByCopyrightId(String copyrightId);
    public List<CopyrightPayment> findByCopyrightIdAndSenderUserId(String copyrightId, String senderUserId);
    public List<CopyrightPayment> findBySendingTimeAfter(Instant sendingTime);
    public List<CopyrightPayment> findBySendingTimeBefore(Instant sendingTime);
    public List<CopyrightPayment> findByReceiverPhoneNumber(String receiverPhoneNumber);
    public List<CopyrightPayment> findByAmountGreaterThanEqual(double amount);
    public List<CopyrightPayment> findByAmountLessThanEqual(double amount);
   
    public boolean deletePayment(String id, String userId);
	
}
