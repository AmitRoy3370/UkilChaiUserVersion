package com.example.demo700.Services.PaymentServices;

import java.util.List;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.PaymentModels.PaymentDetails;

public interface PaymentDetailsService {

	public PaymentDetails addPaymentDetails(PaymentDetails paymentDetails, String userId);

	public PaymentDetails updatePaymentDetails(PaymentDetails paymentDetails, String userId, String paymentDetailsId);

	public PaymentDetails findById(String id);

	public List<PaymentDetails> findAll();

	public List<PaymentDetails> findByPaymentFor(CasePayment paymentFor);

	public List<PaymentDetails> findByPriceGreaterThanEqual(double price);

	public List<PaymentDetails> findByPriceLessThanEqual(double price);

	public List<PaymentDetails> findByUserId(String userId);

	public List<PaymentDetails> findByCaseId(String caseId);

	public List<PaymentDetails> findByCaseIdAndPaymentFor(String caseId, CasePayment paymentFor);

	public boolean deletePaymentDetails(String id, String userId);

}
