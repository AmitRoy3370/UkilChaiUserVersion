package com.example.demo700.Repositories.UserRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.CompanyRequestPayment;

@Repository
public interface CompanyPaymentRepository extends MongoRepository<CompanyRequestPayment, String> {

	@Query("{ 'companyId' : ?0 }")
	public List<CompanyRequestPayment> findByCompanyId(String companyId);
	
	@Query("{ 'senderUserId' : ?0 }")
	public List<CompanyRequestPayment> findBySenderUserId(String senderUserId);
	
	@Query("{ 'senderPhoneNumber' : ?0 }")
	public List<CompanyRequestPayment> findBySenderPhoneNumber(String senderPhoneNumber);
	
	@Query("{ 'receiverPhoneNumber' : ?0 }")
	public List<CompanyRequestPayment> findByReceiverPhoneNumber(String receiverPhoneNumber);
	
	@Query("{ 'transactionId' : ?0 }")
	public List<CompanyRequestPayment> findByTransactionId(String transactionId);
	
	@Query("{ 'amount' : { $gte: ?0 } }")
	public List<CompanyRequestPayment> findByAmountGreaterThanEqual(double amount);
	
	@Query("{ 'amount' : { $lte: ?0 } }")
	public List<CompanyRequestPayment> findByAmountLessThanEqual(double amount);
	
	@Query("{ 'sendingTime' : { $gte: ?0 } }")
	public List<CompanyRequestPayment> findBySendingTimeAfter(Instant sendingTime);
	
	@Query("{ 'sendingTime' : { $lte: ?0 } }")
	public List<CompanyRequestPayment> findBySendingTimeBefore(Instant sendingTime);
	
	@Query("{ 'sendingTime' : ?0 }")
	public List<CompanyRequestPayment> findBySendingTime(Instant sendingTime);
	
}