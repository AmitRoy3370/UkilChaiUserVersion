package com.example.demo700.Services.TrademarkServices;

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
import com.example.demo700.DTOFiles.CompanyPaymentResponse;
import com.example.demo700.DTOFiles.TrademarkPaymentResponse;
import com.example.demo700.Model.TradeLicenseModels.TradeLicensePayment;
import com.example.demo700.Model.Trademarkmodels.Trademark;
import com.example.demo700.Model.Trademarkmodels.TrademarkPayment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.TrademarkRepositories.TrademarkPaymentRepository;
import com.example.demo700.Repositories.TrademarkRepositories.TrademarkRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class TrademarkPaymentServiceImpl implements TrademarkPaymentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TrademarkPaymentRepository paymentRepository;

	@Autowired
	private TrademarkRepository trademarkRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "TrademarkPayment";
	
	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public TrademarkPayment addTrademarkPayment(TrademarkPayment payment, String userId) {

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

		Trademark mark = null;

		try {

			mark = trademarkRepository.findById(payment.getTradeMarkId()).get();

			if (mark == null) {

				throw new Exception();

			}

			if (mark.getUserId().equals(payment.getSenderUserId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			TrademarkPayment markPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (markPayment == null) {

			} else {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new NoSuchElementException("This transaction id is already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		payment = paymentRepository.save(payment);

		return payment;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public TrademarkPayment updateTrademarkPayment(TrademarkPayment payment, String userId, String id) {

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

		Trademark mark = null;

		try {

			mark = trademarkRepository.findById(payment.getTradeMarkId()).get();

			if (mark == null) {

				throw new Exception();

			}

			if (mark.getUserId().equals(payment.getSenderUserId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			TrademarkPayment markPayment = paymentRepository.findByTransactionId(payment.getTransactionId());

			if (markPayment == null) {

			} else {

				if (!markPayment.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new NoSuchElementException("This transaction id is already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			TrademarkPayment markPayment = paymentRepository.findById(id).get();

			if (markPayment == null) {

				throw new Exception();

			}

			if (markPayment.getSenderUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		payment.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", payment.getId());
		update.set("senderUserId", payment.getSenderUserId());
		update.set("transactionId", payment.getTransactionId());
		update.set("trademarkId", payment.getTradeMarkId());
		update.set("receiverPhoneNumber", payment.getReceiverPhoneNumber());
		update.set("senderPhoneNumber", payment.getSenderPhoneNumber());
		update.set("sendingTime", payment.getSendingTime());
		update.set("amount", payment.getAmount());

		mongoTemplate.updateFirst(query, update, TrademarkPayment.class);

		payment = mongoTemplate.findOne(query, TrademarkPayment.class);

		return payment;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TrademarkPaymentResponse findById(String id) {
		
		if(id == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			TrademarkPayment payment = paymentRepository.findById(id).get();
			
			if(payment == null) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TrademarkPaymentResponse> findAll() {

		try {
		
			List<TrademarkPayment> payment = paymentRepository.findAll();
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserId_' + #senderUserId")
	public List<TrademarkPaymentResponse> findBySenderUserId(String senderUserId) {

		if(senderUserId == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findBySenderUserId(senderUserId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderPhoneNumber_' + #senderPhoneNumber")
	public List<TrademarkPaymentResponse> findBySenderPhoneNumberContainingIgnoreCase(String senderPhoneNumber) {

		if(senderPhoneNumber == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findBySenderPhoneNumberContainingIgnoreCase(senderPhoneNumber);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByReceiverPhoneNumber_' + #receiverPhoneNumber")
	public List<TrademarkPaymentResponse> findByReceiverPhoneNumberContaingingIgnoreCase(String receiverPhoneNumber) {

		if(receiverPhoneNumber == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findByReceiverPhoneNumberContaingingIgnoreCase(receiverPhoneNumber);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserIdAndTrademarkId_' + #senderUserId + '_' + #trademarkId")
	public List<TrademarkPaymentResponse> findBySenderUserIdAndTrademarkId(String trademarkId, String senderUserId) {

		if(trademarkId == null || senderUserId == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findBySenderUserIdAndTrademarkId(trademarkId, senderUserId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTrademarkId_' + #trademarkId")
	public List<TrademarkPaymentResponse> findByTradeMarkId(String trademarkId) {

		if(trademarkId == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findByTradeMarkId(trademarkId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionId_' + #transactionId")
	public TrademarkPaymentResponse findByTransactionId(String transactionId) {

		if(transactionId == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			TrademarkPayment payment = paymentRepository.findByTransactionId(transactionId);
			
			if(payment == null) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionIdPrefix_' + #transactionId")
	public List<TrademarkPaymentResponse> findByTransactionIdContainingIgnoreCase(String transactionId) {

		if(transactionId == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findByTransactionIdContainingIgnoreCase(transactionId);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountGTE_' + #amount")
	public List<TrademarkPaymentResponse> findByAmountGreaterThanEqual(double amount) {

		try {
		
			List<TrademarkPayment> payment = paymentRepository.findByAmountGreaterThanEqual(amount);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountLTE_' + #amount")
	public List<TrademarkPaymentResponse> findByAmountLessThanEqual(double amount) {

		try {
		
			List<TrademarkPayment> payment = paymentRepository.findByAmountLessThanEqual(amount);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<TrademarkPaymentResponse> findBySendingTimeBefore(Instant sendingTime) {

		if(sendingTime == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findBySendingTimeBefore(sendingTime);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<TrademarkPaymentResponse> findBySendingTimeAfter(Instant sendingTime) {

		if(sendingTime == null) {
			
			throw new NullPointerException("No such payment find at here...");
			
		}
		
		try {
		
			List<TrademarkPayment> payment = paymentRepository.findBySendingTimeAfter(sendingTime);
			
			if(payment == null || payment.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPaymentResponse(payment);
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such payment find at here...");
			
		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public boolean deleteTrademarkPayment(String id) {

		if (id == null) {

			throw new NullPointerException();

		}

		try {

			TrademarkPayment markPayment = paymentRepository.findById(id).get();

			if (markPayment == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

		long count = paymentRepository.count();

		cleaner.removeTrademarkPayment(id);

		return count != paymentRepository.count();
	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private TrademarkPaymentResponse getPaymentResponse(TrademarkPayment payment) {

		List<TrademarkPayment> list = new ArrayList<>();

		list.add(payment);

		return getPaymentResponse(list).get(0);

	}

	private List<TrademarkPaymentResponse> getPaymentResponse(List<TrademarkPayment> list) {

		List<TrademarkPaymentResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> sendersUserIdFuture = CompletableFuture.supplyAsync(
				() -> list.stream().map(TrademarkPayment::getSenderUserId).distinct().collect(Collectors.toList()),
				executor);

		CompletableFuture<Map<String, User>> userMapFuture = sendersUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, Trademark>> trademarkMapFuture = CompletableFuture.supplyAsync(() -> trademarkRepository.findAllById(list.stream().map(TrademarkPayment::getTradeMarkId).distinct().collect(Collectors.toList())).stream().collect(Collectors.toMap(Trademark::getId, Function.identity())), executor);
		
		CompletableFuture.allOf(sendersUserIdFuture, userMapFuture, trademarkMapFuture).join();

		Map<String, User> userMap = userMapFuture.join();
		Map<String, Trademark> trademarkMap = trademarkMapFuture.join();

		for (TrademarkPayment i : list) {

			try {

				TrademarkPaymentResponse response = new TrademarkPaymentResponse();

				response.setId(i.getId());
				response.setSenderUserId(i.getSenderUserId());
				response.setSenderPhoneNumber(i.getSenderPhoneNumber());
				response.setSendingTime(i.getSendingTime());
				response.setAmount(i.getAmount());
				response.setTrademarkId(i.getTradeMarkId());
				response.setTransactionId(i.getTransactionId());
				response.setReceiverPhoneNumber(i.getReceiverPhoneNumber());
				response.setTrademark(trademarkMap.get(i.getTradeMarkId()));

				try {

					response.setSenderUserName(userMap.get(i.getSenderUserId()).getFullName() == null
							? userMap.get(i.getSenderUserId()).getName()
							: userMap.get(i.getSenderUserId()).getFullName());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		return responses;

	}

}
