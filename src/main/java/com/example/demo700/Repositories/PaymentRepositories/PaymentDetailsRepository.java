package com.example.demo700.Repositories.PaymentRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.PaymentModels.PaymentDetails;

@Repository
public interface PaymentDetailsRepository extends MongoRepository<PaymentDetails, String> {

	public List<PaymentDetails> findByPaymentFor(CasePayment paymentFor);
	public List<PaymentDetails> findByPriceGreaterThanEqual(double price);
	public List<PaymentDetails> findByPriceLessThanEqual(double price);
	public List<PaymentDetails> findByUserId(String userId);
	public List<PaymentDetails> findByCaseId(String caseId);
	public List<PaymentDetails> findByCaseIdAndPaymentFor(String caseId, CasePayment paymentFor);
	
}
