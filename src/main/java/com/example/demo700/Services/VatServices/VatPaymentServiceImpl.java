package com.example.demo700.Services.VatServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.VatModels.Vat;
import com.example.demo700.Model.VatModels.VatPayment;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Repositories.VatRepositories.VatPaymentRepository;
import com.example.demo700.Repositories.VatRepositories.VatRepository;

@Service
public class VatPaymentServiceImpl implements VatPaymentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository adminRepository;

	@Autowired
	private VatRepository vatRepository;

	@Autowired
	private VatPaymentRepository paymentRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public VatPayment addPayment(VatPayment payment, String userId) {

		if (payment == null || userId == null || !payment.getSenderUserId().equals(userId)) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Vat vat = null;

		try {

			vat = vatRepository.findById(payment.getVatId()).get();

			if (vat == null) {

				throw new Exception();

			}

			if (!vat.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		try {

			VatPayment vatPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (vatPayment != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist...");

		} catch (Exception e) {

		}

		payment = paymentRepository.save(payment);

		return payment;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public VatPayment updatePayment(VatPayment payment, String userId, String id) {

		if (id == null || payment == null || userId == null || !payment.getSenderUserId().equals(userId)) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Vat vat = null;

		try {

			vat = vatRepository.findById(payment.getVatId()).get();

			if (vat == null) {

				throw new Exception();

			}

			if (!vat.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		try {

			VatPayment vatPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (vatPayment != null) {

				if (vatPayment.getId().equals(id)) {

				} else {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist...");

		} catch (Exception e) {

		}

		try {

			VatPayment vatPayment = paymentRepository.findById(id).get();

			if (vatPayment == null) {

				throw new Exception();

			}

			if (!vatPayment.getSenderUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		payment.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("senderUserId", payment.getSenderUserId());
		update.set("senderPhoneNumber", payment.getSenderPhoneNumber());
		update.set("receiverPhoneNumber", payment.getReceiverPhoneNumber());
		update.set("vatId", payment.getVatId());
		update.set("transactionId", payment.getTransactionId());
		update.set("sendingTime", payment.getSendingTime());
		update.set("amount", payment.getAmount());
		update.set("senderUserName", payment.getSenderUserName());

		mongoTemplate.updateFirst(query, update, VatPayment.class);

		payment = mongoTemplate.findOne(query, VatPayment.class);

		return payment;

	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findById_' + #id")
	public VatPayment findById(String id) {
		
		if(id == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			VatPayment payment = paymentRepository.findById(id).get();
			
			if(payment == null) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
		
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findAll'")
	public List<VatPayment> findAll() {

		try {
			
			List<VatPayment> payment = paymentRepository.findAll();
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findBySenderUserId_' + #senderUserId")
	public List<VatPayment> findBySenderUserId(String senderUserId) {

		if(senderUserId == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findBySenderUserId(senderUserId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findBySenderPhoneNumber_' + #senderPhoneNumber")
	public List<VatPayment> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber) {

		if(senderPhoneNumber == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findBySenderPhoneNumberContainingIgnoreCase(senderPhoneNumber);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByReceiverPhoneNumber_' + #receiverPhoneNumber")
	public List<VatPayment> findByReceiverPhoneNumberContainingIgnoreCase(String receiverPhoneNumber) {

		if(receiverPhoneNumber == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findByReceiverPhoneNumberContainingIgnoreCase(receiverPhoneNumber);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByVatId_' + #vatId")
	public List<VatPayment> findByVatId(String vatId) {

		if(vatId == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findByVatId(vatId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByVatIdAndUserId_' + #vatId + '_' + #senderUserId")
	public List<VatPayment> findByVatIdAndSenderUserId(String vatId, String senderUserId) {

		if(vatId == null || senderUserId == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findByVatIdAndSenderUserId(vatId, senderUserId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByAmountGTE_' + #amount")
	public List<VatPayment> findByAmountGreaterThanEqual(double amount) {

		try {
			
			List<VatPayment> payment = paymentRepository.findByAmountGreaterThanEqual(amount);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByAmountLTE_' + #amount")
	public List<VatPayment> findByAmountLessThanEqual(double amount) {

		try {
			
			List<VatPayment> payment = paymentRepository.findByAmountLessThanEqual(amount);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByTransactionIdPrefix_' + #transactionId")
	public List<VatPayment> findByTransactionIdContainingIgnoreCase(String transactionId) {

		if(transactionId == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findByTransactionIdContainingIgnoreCase(transactionId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findByTransactionId_' + #transactionId")
	public VatPayment findByTransactionId(String transactionId) {

		if(transactionId == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			VatPayment payment = paymentRepository.findByTransactionId(transactionId);
			
			if(payment == null) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<VatPayment> findBySendingTimeAfter(Instant sendingTime) {

		if(sendingTime == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findBySendingTimeAfter(sendingTime);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatPayment", key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<VatPayment> findBySendingTimeBefore(Instant sendingTime) {

		if(sendingTime == null) {
			
			throw new NullPointerException("False request....");
			
		}
		
		try {
			
			List<VatPayment> payment = paymentRepository.findBySendingTimeBefore(sendingTime);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return payment;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public boolean deletePayment(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			CenterAdmin admin = adminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			long count = paymentRepository.count();

			cleaner.removeVatPayment(id);

			return count != paymentRepository.count();

		} catch (Exception e) {

		}

		try {

			VatPayment vatPayment = paymentRepository.findById(id).get();

			if (vatPayment == null) {

				throw new Exception();

			}

			if (!vatPayment.getSenderUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		long count = paymentRepository.count();

		cleaner.removeVatPayment(id);

		return count != paymentRepository.count();
	}

}
