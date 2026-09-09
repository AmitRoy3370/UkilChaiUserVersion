package com.example.demo700.Services.TradeLicenseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.example.demo700.DTOFiles.CompanyPaymentResponse;
import com.example.demo700.DTOFiles.TradeLicensePaymentResponseDTO;
import com.example.demo700.Model.TradeLicenseModels.TradeLicensePayment;

public interface TradeLicensePaymentService {

	public TradeLicensePayment addPayment(TradeLicensePayment payment, String userId);
	public TradeLicensePayment updatePayment(TradeLicensePayment payment, String id, String userId);
	
	public TradeLicensePaymentResponseDTO findById(String id);
	public List<TradeLicensePaymentResponseDTO> findAll(); 
	public List<TradeLicensePaymentResponseDTO> findBySenderUserId(String senderUserId);
	public List<TradeLicensePaymentResponseDTO> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber);
	public List<TradeLicensePaymentResponseDTO> findByReceiverPhoneNumberContainingIgnoreCase(String receiverPhoneNumber);
	public List<TradeLicensePaymentResponseDTO> findByTransactionIdContainingIgnoreCase(String transactionId);
	public List<TradeLicensePaymentResponseDTO> findByAmountGreaterThanEqual(double amount);
	public List<TradeLicensePaymentResponseDTO> findByAmountLessThanEqual(double amount);
	public List<TradeLicensePaymentResponseDTO> findByTradeLicenseId(String tradeLicenseId);
	public List<TradeLicensePaymentResponseDTO> findBySendingTimeBefore(Instant sendingTime);
	public List<TradeLicensePaymentResponseDTO> findBySendingTimeAfter(Instant sendingTime);
	
	public boolean delete(String id, String userId);
	
}
