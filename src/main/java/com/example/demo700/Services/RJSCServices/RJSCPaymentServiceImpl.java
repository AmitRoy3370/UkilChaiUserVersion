package com.example.demo700.Services.RJSCServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.RJSCModels.RJSC;
import com.example.demo700.Model.RJSCModels.RJSCPayment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.RJSCRepositories.RJSCPaymentRepository;
import com.example.demo700.Repositories.RJSCRepositories.RJSCRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class RJSCPaymentServiceImpl implements RJSCPaymentService {

	@Autowired
	private RJSCRepository rjscRepository;

	@Autowired
	private RJSCPaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;
	
	private static final String cacheValue = "RJSCPayment";

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSCPayment addPayment(RJSCPayment payment, String userId) {

		if (payment == null || userId == null || !payment.getSenderUserId().equals(userId)
				|| payment.getAmount() <= 0) {

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

		try {

			RJSCPayment rjscPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (rjscPayment != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same transaction id already exist...");

		} catch (Exception e) {

		}

		try {

			RJSC rjsc = rjscRepository.findById(payment.getRjscId()).get();

			if (rjsc == null) {

				throw new Exception();

			}

			if (!rjsc.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here....");

		}

		payment = paymentRepository.save(payment);

		return payment;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSCPayment updatePayment(RJSCPayment payment, String userId, String id) {

		if (id == null || payment == null || userId == null || !payment.getSenderUserId().equals(userId)
				|| payment.getAmount() <= 0) {

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

		try {

			RJSCPayment rjscPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (rjscPayment != null) {

				if (!rjscPayment.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same transaction id already exist...");

		} catch (Exception e) {

		}

		try {

			RJSC rjsc = rjscRepository.findById(payment.getRjscId()).get();

			if (rjsc == null) {

				throw new Exception();

			}

			if (!rjsc.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here....");

		}

		try {

			RJSCPayment rjscPayment = paymentRepository.findById(id).get();

			if (rjscPayment == null) {

				throw new Exception();

			}

			if (!rjscPayment.getSenderUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("senderUserId", payment.getSenderUserId());
		update.set("senderPhoneNumber", payment.getSenderPhoneNumber());
		update.set("receiverPhoneNumber", payment.getReceiverPhoneNumber());
		update.set("senderUserName", payment.getSenderUserName());
		update.set("sendingTime", payment.getSendingTime());
		update.set("transactionId", payment.getTransactionId());
		update.set("amount", payment.getAmount());
		update.set("rjscId", payment.getRjscId());

		mongoTemplate.updateFirst(query, update, RJSCPayment.class);

		payment = mongoTemplate.findOne(query, RJSCPayment.class);

		return payment;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public RJSCPayment findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RJSCPayment payment = paymentRepository.findById(id).get();

			if (payment == null) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<RJSCPayment> findAll() {

		try {

			List<RJSCPayment> payment = paymentRepository.findAll();

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserId_' + #senderUserId")
	public List<RJSCPayment> findBySenderUserId(String senderUserId) {

		if (senderUserId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySenderUserId(senderUserId);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserName_' + #senderUserName")
	public List<RJSCPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName) {

		if (senderUserName == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySenderUserNameContainingIgnoreCase(senderUserName);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderPhoneNumber_' + #senderPhoneNumber")
	public List<RJSCPayment> findBySenderPhoneNumber(String senderPhoneNumber) {

		if (senderPhoneNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySenderPhoneNumber(senderPhoneNumber);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByReceiverPhoneNumber_' + #receiverPhoneNumber")
	public List<RJSCPayment> findByReceiverPhoneNumber(String receiverPhoneNumber) {

		if (receiverPhoneNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findByReceiverPhoneNumber(receiverPhoneNumber);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionIdPrefix_' + #transactionId")
	public List<RJSCPayment> findByTransactionIdContainingIgnoreCase(String transactionId) {

		if (transactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findByTransactionIdContainingIgnoreCase(transactionId);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionId_' + #transactionId")
	public RJSCPayment findByTransactionId(String transactionId) {

		if (transactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			RJSCPayment payment = paymentRepository.findByTransactionId(transactionId);

			if (payment == null) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountGTE_' + #amount")
	public List<RJSCPayment> findByAmountGreaterThanEqual(double amount) {

		try {

			List<RJSCPayment> payment = paymentRepository.findByAmountGreaterThanEqual(amount);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountLTE_' + #amount")
	public List<RJSCPayment> findByAmountLessThanEquak(double amount) {

		try {

			List<RJSCPayment> payment = paymentRepository.findByAmountLessThanEquak(amount);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<RJSCPayment> findBySendingTimeAfter(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySendingTimeAfter(sendingTime);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<RJSCPayment> findBySendingTimeBefore(Instant sendingTime) {
		if (sendingTime == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySendingTimeBefore(sendingTime);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserId_' + #senderUserId + '_' + #rjscId")
	public List<RJSCPayment> findBySenderUserIdAndRJSCId(String senderUserId, String rjscId) {
		if (senderUserId == null || rjscId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findBySenderUserIdAndRJSCId(senderUserId, rjscId);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByRJSCId_' + #rjscId")
	public List<RJSCPayment> findByRJSCId(String rjscId) {
		if (rjscId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<RJSCPayment> payment = paymentRepository.findByRJSCId(rjscId);

			if (payment == null || payment.isEmpty()) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("False request....");

		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public boolean delete(String id, String userId) {

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

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			long count = paymentRepository.count();

			cleaner.removeRJSCPayment(id);

			return count != paymentRepository.count();

		} catch (Exception e) {

		}

		try {

			RJSCPayment rjscPayment = paymentRepository.findById(id).get();

			if (rjscPayment == null) {

				throw new Exception();

			}

			if (!rjscPayment.getSenderUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		long count = paymentRepository.count();

		cleaner.removeRJSCPayment(id);

		return count != paymentRepository.count();

	}

}
