package com.example.demo700.Services.UserServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.DTOFiles.CompanyPaymentResponse;
import com.example.demo700.Model.UserModels.CompanyRequestPayment;

public interface CompanyPaymentService {

	public CompanyRequestPayment addCompanyPayment(CompanyRequestPayment companyPayment);
	public CompanyRequestPayment updateCompanyPayment(String id, CompanyRequestPayment companyPayment, String userId);
	
	public CompanyPaymentResponse findById(String id);
	public List<CompanyPaymentResponse> findAll();
	public List<CompanyPaymentResponse> findByCompanyId(String companyId);
	public List<CompanyPaymentResponse> findBySenderUserId(String senderUserId);
	public List<CompanyPaymentResponse> findBySenderPhoneNumber(String senderPhoneNumber);
	public List<CompanyPaymentResponse> findByReceiverPhoneNumber(String receiverPhoneNumber);
	public List<CompanyPaymentResponse> findByTransactionId(String transactionId);
	public List<CompanyPaymentResponse> findByAmountGreaterThanEqual(double amount);
	public List<CompanyPaymentResponse> findByAmountLessThanEqual(double amount);
	public List<CompanyPaymentResponse> findBySendingTimeAfter(Instant sendingTime);
	public List<CompanyPaymentResponse> findBySendingTimeBefore(Instant sendingTime);
	
	public boolean removeCompanyPayment(String id, String userId);
	
}
