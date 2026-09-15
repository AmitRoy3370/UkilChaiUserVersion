package com.example.demo700.Services.VatServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.VatModels.VatPayment;

public interface VatPaymentService {

	public VatPayment addPayment(VatPayment payment, String userId);
	public VatPayment updatePayment(VatPayment payment, String userId, String id);
	
	public VatPayment findById(String id);
	public List<VatPayment> findAll();
	public List<VatPayment> findBySenderUserId(String senderUserId);
    public List<VatPayment> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);
    public List<VatPayment> findByReceiverPhoneNumberContainingIgnoreCase(String receiverPhoneNumber);
    public List<VatPayment> findByVatId(String vatId);
    public List<VatPayment> findByVatIdAndSenderUserId(String vatId, String senderUserId);
    public List<VatPayment> findByAmountGreaterThanEqual(double amount);
    public List<VatPayment> findByAmountLessThanEqual(double amount);
    public List<VatPayment> findByTransactionIdContainingIgnoreCase(String transactionId);
    public VatPayment findByTransactionId(String transactionId);
    public List<VatPayment> findBySendingTimeAfter(Instant sendingTime);
    public List<VatPayment> findBySendingTimeBefore(Instant sendingTime);

    public boolean deletePayment(String id, String userId);
	
}
