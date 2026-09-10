package com.example.demo700.Services.TradeLicenseServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.example.demo700.DTOFiles.TradeLicensePaymentResponseDTO;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.TradeLicenseModels.TradeLicense;
import com.example.demo700.Model.TradeLicenseModels.TradeLicensePayment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicensePaymentRepository;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicenseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class TradeLicensePaymentServiceImpl implements TradeLicensePaymentService {

	@Autowired
	private TradeLicensePaymentRepository paymentRepository;

	@Autowired
	private TradeLicenseRepository tradeLicenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String cacheValue = "TradeLicensePayment";

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true), @CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true)

	})
	public TradeLicensePayment addPayment(TradeLicensePayment payment, String userId) {

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

		try {

			TradeLicensePayment _payment = paymentRepository.findByTransactionIdIgnoreCase(payment.getTransactionId());

			if (_payment != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist at here...");

		} catch (Exception e) {

		}

		try {

			TradeLicense license = tradeLicenseRepository.findById(payment.getTradeLicenseId()).get();

			if (license == null) {

				throw new Exception();

			}

			if (!license.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		payment = paymentRepository.save(payment);

		return payment;
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true), @CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true)

	})
	public TradeLicensePayment updatePayment(TradeLicensePayment payment, String id, String userId) {

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

			TradeLicensePayment _payment = paymentRepository.findByTransactionIdIgnoreCase(payment.getTransactionId());

			if (_payment != null) {

				if (!_payment.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This transaction id already exist at here...");

		} catch (Exception e) {

		}

		try {

			TradeLicense license = tradeLicenseRepository.findById(payment.getTradeLicenseId()).get();

			if (license == null) {

				throw new Exception();

			}

			if (!license.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("senderUserId", payment.getSenderUserId());
		update.set("senderPhoneNumber", payment.getSenderPhoneNumber());
		update.set("receiverPhoneNumber", payment.getReceiverPhoneNumber());
		update.set("amount", payment.getAmount());
		update.set("sendingTime", payment.getSendingTime());
		update.set("tradeLicenseId", payment.getTradeLicenseId());
		update.set("transactionId", payment.getTransactionId());

		mongoTemplate.updateFirst(query, update, TradeLicensePayment.class);

		payment = mongoTemplate.findOne(query, TradeLicensePayment.class);

		return payment;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TradeLicensePaymentResponseDTO findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			TradeLicensePayment list = paymentRepository.findById(id).get();

			if (list == null) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TradeLicensePaymentResponseDTO> findAll() {

		try {

			List<TradeLicensePayment> list = paymentRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserId_' + #senderUserId")
	public List<TradeLicensePaymentResponseDTO> findBySenderUserId(String senderUserId) {

		if (senderUserId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository.findBySenderUserId(senderUserId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderPhoneNumberPrefix_' + #senderPhoneNumber")
	public List<TradeLicensePaymentResponseDTO> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber) {

		if (senderPhoneNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository
					.findBySenderPhoneNumberContainingIgnoreCase(senderPhoneNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByReceiverPhoneNumberPrefix_' + #receiverPhoneNumber")
	public List<TradeLicensePaymentResponseDTO> findByReceiverPhoneNumberContainingIgnoreCase(
			String receiverPhoneNumber) {

		if (receiverPhoneNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository
					.findByReceiverPhoneNumberContainingIgnoreCase(receiverPhoneNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionIdPrefix_' + #transactionId")
	public List<TradeLicensePaymentResponseDTO> findByTransactionIdContainingIgnoreCase(String transactionId) {

		if (transactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository.findByTransactionIdContainingIgnoreCase(transactionId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountGreaterThanEqual_' + #amount")
	public List<TradeLicensePaymentResponseDTO> findByAmountGreaterThanEqual(double amount) {

		try {

			List<TradeLicensePayment> list = paymentRepository.findByAmountGreaterThanEqual(amount);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountLessThanEqual_' + #amount")
	public List<TradeLicensePaymentResponseDTO> findByAmountLessThanEqual(double amount) {

		try {

			List<TradeLicensePayment> list = paymentRepository.findByAmountLessThanEqual(amount);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTradeLicenseId_' + #tradeLicenseId")
	public List<TradeLicensePaymentResponseDTO> findByTradeLicenseId(String tradeLicenseId) {

		if (tradeLicenseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository.findByTradeLicenseId(tradeLicenseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<TradeLicensePaymentResponseDTO> findBySendingTimeBefore(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository.findBySendingTimeBefore(sendingTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<TradeLicensePaymentResponseDTO> findBySendingTimeAfter(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicensePayment> list = paymentRepository.findBySendingTimeAfter(sendingTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicensePaymentResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true), @CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true)

	})
	public boolean delete(String id, String userId) {

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

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			long count = paymentRepository.count();

			cleaner.removeTradeLicensePayment(id);

			return count != paymentRepository.count();

		} catch (Exception e) {

		}

		TradeLicensePayment payment = null;

		try {

			payment = paymentRepository.findById(id).get();

			if (payment == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		try {

			if (payment.getSenderUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Only the sender of this payment can remove payment...");

		}

		long count = paymentRepository.count();

		cleaner.removeTradeLicensePayment(id);

		return count != paymentRepository.count();
	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private TradeLicensePaymentResponseDTO getTradeLicensePaymentResponse(TradeLicensePayment payment) {

		List<TradeLicensePayment> list = new ArrayList<>();

		list.add(payment);

		return getTradeLicensePaymentResponse(list).get(0);

	}

	private List<TradeLicensePaymentResponseDTO> getTradeLicensePaymentResponse(List<TradeLicensePayment> list) {

		List<TradeLicensePaymentResponseDTO> responses = new ArrayList<>();

		CompletableFuture<List<String>> allSenderUserIdFuture = CompletableFuture.supplyAsync(
				() -> list.stream().map(TradeLicensePayment::getSenderUserId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, User>> userMapFuture = allSenderUserIdFuture.thenApplyAsync(allUserId -> {

			if (allUserId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(allUserId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(allSenderUserIdFuture, userMapFuture).join();

		Map<String, User> userMap = userMapFuture.join();

		for (TradeLicensePayment i : list) {

			try {

				TradeLicensePaymentResponseDTO response = new TradeLicensePaymentResponseDTO();

				response.setId(i.getId());
				response.setAmount(i.getAmount());
				response.setSenderPhoneNumber(i.getSenderPhoneNumber());
				response.setSendingTime(i.getSendingTime());
				response.setTradeLicenseId(i.getTradeLicenseId());
				response.setSendingTime(i.getSendingTime());
				response.setSenderUserId(i.getSenderUserId());
				response.setTransactionId(i.getTransactionId());
				response.setSenderUserName(userMap.get(i.getSenderUserId()).getFullName() == null
						? userMap.get(i.getSenderUserId()).getName()
						: userMap.get(i.getSenderUserId()).getFullName());

				response.setReceiverPhoneNumber(i.getReceiverPhoneNumber());
				responses.add(response);

			} catch (Exception e) {

			}

		}

		CompletableFuture.allOf(allSenderUserIdFuture).join();

		return responses;

	}

}
