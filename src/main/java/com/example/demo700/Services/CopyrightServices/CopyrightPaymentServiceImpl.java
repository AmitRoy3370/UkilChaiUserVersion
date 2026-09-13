package com.example.demo700.Services.CopyrightServices;

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
import com.example.demo700.Model.CopyrightModels.Copyright;
import com.example.demo700.Model.CopyrightModels.CopyrightPayment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightPaymentRepository;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CopyrightPaymentServiceImpl implements CopyrightPaymentService {

	@Autowired
	private CopyrightPaymentRepository paymentRepository;

	@Autowired
	private CopyrightRepository copyrightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "CopyrightPayment";

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public CopyrightPayment addPayment(CopyrightPayment payment, String userId) {

		if (payment == null || userId == null || !payment.getSenderUserId().equals(userId)) {

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

		Copyright copyright = null;

		try {

			copyright = copyrightRepository.findById(payment.getCopyrightId()).get();

			if (copyright == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such copy right find at here...");

		}

		try {

			CopyrightPayment rightPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (rightPayment != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist...");

		}

		payment = paymentRepository.save(payment);

		return payment;

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public CopyrightPayment updatePayment(CopyrightPayment payment, String userId, String id) {

		if (id == null || payment == null || userId == null || !payment.getSenderUserId().equals(userId)) {

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

			CopyrightPayment rightPayment = paymentRepository.findById(id).get();

			if (rightPayment == null) {

				throw new Exception();

			}

			if (!rightPayment.getSenderUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		Copyright copyright = null;

		try {

			copyright = copyrightRepository.findById(payment.getCopyrightId()).get();

			if (copyright == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such copy right find at here...");

		}

		try {

			CopyrightPayment rightPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (rightPayment != null) {

				if (rightPayment.getId().equals(id)) {

				} else {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist...");

		}

		payment.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", payment.getId());
		update.set("senderUserId", payment.getSenderUserId());
		update.set("senderUserName", payment.getSenderUserName());
		update.set("senderPhoneNumber", payment.getSenderPhoneNumber());
		update.set("receiverPhoneNumber", payment.getReceiverPhoneNumber());
		update.set("sendingTime", payment.getSendingTime());
		update.set("transactionId", payment.getTransactionId());
		update.set("copyrightId", payment.getCopyrightId());
		update.set("amount", payment.getAmount());

		mongoTemplate.updateFirst(query, update, CopyrightPayment.class);

		payment = mongoTemplate.findOne(query, CopyrightPayment.class);

		return payment;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CopyrightPayment findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CopyrightPayment payment = paymentRepository.findById(id).get();

			if (payment == null) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CopyrightPayment> findAll() {

		try {

			List<CopyrightPayment> list = paymentRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserId_' + #senderUserId")
	public List<CopyrightPayment> findBySenderUserId(String senderUserId) {

		if (senderUserId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findBySenderUserId(senderUserId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserNamePrefix_' + #senderUserName")
	public List<CopyrightPayment> findBySenderUserNameContainingIgnoreCase(String senderUserName) {

		if (senderUserName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findBySenderUserNameContainingIgnoreCase(senderUserName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderPhoneNumber_' + #senderPhoneNumber")
	public List<CopyrightPayment> findBySenderPhoneNumber(String senderPhoneNumber) {

		if (senderPhoneNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findBySenderPhoneNumber(senderPhoneNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionId_' + #transactionId")
	public CopyrightPayment findByTransactionId(String transactionId) {

		if (transactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CopyrightPayment payment = paymentRepository.findByTransactionId(transactionId);

			if (payment == null) {

				throw new Exception();

			}

			return payment;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCopyrightId_' + #copyrightId")
	public List<CopyrightPayment> findByCopyrightId(String copyrightId) {

		if (copyrightId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findByCopyrightId(copyrightId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCopyrightId_' + #copyrightId + '_userId_' + #senderUserId")
	public List<CopyrightPayment> findByCopyrightIdAndSenderUserId(String copyrightId, String senderUserId) {

		if (copyrightId == null || senderUserId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findByCopyrightIdAndSenderUserId(copyrightId, senderUserId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<CopyrightPayment> findBySendingTimeAfter(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findBySendingTimeAfter(sendingTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<CopyrightPayment> findBySendingTimeBefore(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findBySendingTimeBefore(sendingTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByReceiverPhoneNumber_' + #receiverPhoneNumber")
	public List<CopyrightPayment> findByReceiverPhoneNumber(String receiverPhoneNumber) {

		if (receiverPhoneNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CopyrightPayment> list = paymentRepository.findByReceiverPhoneNumber(receiverPhoneNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountGTE_' + #amount")
	public List<CopyrightPayment> findByAmountGreaterThanEqual(double amount) {

		try {

			List<CopyrightPayment> list = paymentRepository.findByAmountGreaterThanEqual(amount);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountLTE_' + #amount")
	public List<CopyrightPayment> findByAmountLessThanEqual(double amount) {

		try {

			List<CopyrightPayment> list = paymentRepository.findByAmountLessThanEqual(amount);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here..");

		}
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public boolean deletePayment(String id, String userId) {

		if (id == null || userId == null) {

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

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			long count = paymentRepository.count();

			cleaner.removeCopyrightPayment(id);

			return count != paymentRepository.count();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			CopyrightPayment rightPayment = paymentRepository.findById(id).get();

			if (rightPayment == null) {

				throw new Exception();

			}

			if (!rightPayment.getSenderUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		long count = paymentRepository.count();

		cleaner.removeCopyrightPayment(id);

		return count != paymentRepository.count();
	}

}
