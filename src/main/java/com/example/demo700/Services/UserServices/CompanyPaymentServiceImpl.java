package com.example.demo700.Services.UserServices;

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

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.CompanyPaymentResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.CompanyRequestPayment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyPaymentRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class CompanyPaymentServiceImpl implements CompanyPaymentService {

	@Autowired
	private CompanyPaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	private PhoneValidator validator;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String cacheValue = "CompanyPayment";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true) })
	public CompanyRequestPayment addCompanyPayment(CompanyRequestPayment companyPayment) {

		if (companyPayment == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(companyPayment.getSenderUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such sender find at here...");

		}

		try {

			CompanyInformation company = companyRepository.findById(companyPayment.getCmpanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(companyPayment.getSenderUserId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company find at here...");

		}

		try {

			List<CompanyRequestPayment> payments = paymentRepository
					.findByTransactionId(companyPayment.getTransactionId());

			if (payments == null || payments.isEmpty()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Two payment must has differen transaction id...");

		}

		try {

			validator = new PhoneValidator(companyPayment.getSenderPhoneNumber());

			if (!validator.isValid()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phone number is not valid...");

		}

		companyPayment = paymentRepository.save(companyPayment);

		return companyPayment;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true) })
	public CompanyRequestPayment updateCompanyPayment(String id, CompanyRequestPayment companyPayment, String userId) {

		if (companyPayment == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CompanyRequestPayment payment = paymentRepository.findById(id).get();

			if (!payment.getTransactionId().equals(companyPayment.getTransactionId())
					|| !payment.getSenderUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find...");

		}

		try {

			User user = userRepository.findById(companyPayment.getSenderUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such sender find at here...");

		}

		try {

			CompanyInformation company = companyRepository.findById(companyPayment.getCmpanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company find at here...");

		}

		try {

			validator = new PhoneValidator(companyPayment.getSenderPhoneNumber());

			if (!validator.isValid()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phone number is not valid...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("companyId", companyPayment.getCmpanyId());
		update.set("senderUserId", companyPayment.getSenderUserId());
		update.set("senderPhoneNumber", companyPayment.getSenderPhoneNumber());
		update.set("receiverPhoneNumber", companyPayment.getReceiverPhoneNumber());
		update.set("transactionId", companyPayment.getTransactionId());
		update.set("amount", companyPayment.getAmount());

		mongoTemplate.updateFirst(query, update, CompanyRequestPayment.class);

		return mongoTemplate.findOne(query, CompanyRequestPayment.class);
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CompanyPaymentResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findById(id).get());

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CompanyPaymentResponse> findAll() {

		try {

			return getCompanyPaymentResponse(paymentRepository.findAll());

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<CompanyPaymentResponse> findByCompanyId(String companyId) {
		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findByCompanyId(companyId));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderUserID_' + #senderUserId")
	public List<CompanyPaymentResponse> findBySenderUserId(String senderUserId) {
		if (senderUserId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findBySenderUserId(senderUserId));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySenderPhoneNumber_' + #senderPhoneNumber")
	public List<CompanyPaymentResponse> findBySenderPhoneNumber(String senderPhoneNumber) {
		if (senderPhoneNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findBySenderPhoneNumber(senderPhoneNumber));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByReceiverPhoneNumber_' + #receiverPhoneNumber")
	public List<CompanyPaymentResponse> findByReceiverPhoneNumber(String receiverPhoneNumber) {
		if (receiverPhoneNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findByReceiverPhoneNumber(receiverPhoneNumber));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTransactionId_' + #transactionId")
	public List<CompanyPaymentResponse> findByTransactionId(String transactionId) {
		if (transactionId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findByTransactionId(transactionId));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountGreaterThanEqual_' + #amount")
	public List<CompanyPaymentResponse> findByAmountGreaterThanEqual(double amount) {

		try {

			return getCompanyPaymentResponse(paymentRepository.findByAmountGreaterThanEqual(amount));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAmountLessThanEqual_' + #amount")
	public List<CompanyPaymentResponse> findByAmountLessThanEqual(double amount) {

		try {

			return getCompanyPaymentResponse(paymentRepository.findByAmountLessThanEqual(amount));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeAfter_' + #sendingTime")
	public List<CompanyPaymentResponse> findBySendingTimeAfter(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findBySendingTimeAfter(sendingTime));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySendingTimeBefore_' + #sendingTime")
	public List<CompanyPaymentResponse> findBySendingTimeBefore(Instant sendingTime) {

		if (sendingTime == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return getCompanyPaymentResponse(paymentRepository.findBySendingTimeBefore(sendingTime));

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find at here...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true) })
	public boolean removeCompanyPayment(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		CompanyRequestPayment companyPayment;

		try {

			companyPayment = paymentRepository.findById(id).get();

			if (companyPayment == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment find...");

		}

		try {

			User user = userRepository.findById(companyPayment.getSenderUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such sender find at here...");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin != null) {

				long count = paymentRepository.count();

				cleaner.removeCompanyPayment(id);

				return paymentRepository.count() != count;

			}

		} catch (Exception e) {

		}

		try {

			CompanyInformation company = companyRepository.findById(companyPayment.getCmpanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company find at here...");

		}

		long count = paymentRepository.count();

		cleaner.removeCompanyPayment(id);

		return paymentRepository.count() != count;
	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private CompanyPaymentResponse getCompanyPaymentResponse(CompanyRequestPayment payment) {

		List<CompanyRequestPayment> list = new ArrayList<>();

		list.add(payment);

		return getCompanyPaymentResponse(list).get(0);

	}

	private List<CompanyPaymentResponse> getCompanyPaymentResponse(List<CompanyRequestPayment> list) {

		List<CompanyPaymentResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> sendersUserIdFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(CompanyRequestPayment::getSenderUserId).distinct()
						.filter(userId -> !userId.isEmpty()).collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> companyIdsFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(CompanyRequestPayment::getCmpanyId).distinct()
						.filter(companyId -> !companyId.isEmpty()).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, User>> userNameMapFuture = sendersUserIdFuture.thenApplyAsync(userIds -> {

			if (userIds.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(userIds).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, CompanyInformation>> companyMapFuture = companyIdsFuture
				.thenApplyAsync(companyIds -> {

					if (companyIds.isEmpty()) {

						return new HashMap<>();

					}

					return companyRepository.findAllById(companyIds).stream()
							.collect(Collectors.toMap(CompanyInformation::getId, Function.identity()));

				}, executor);

		CompletableFuture.allOf(sendersUserIdFuture, companyIdsFuture, userNameMapFuture, companyMapFuture).join();

		Map<String, User> userNameMap = userNameMapFuture.join();

		Map<String, CompanyInformation> companyMap = companyMapFuture.join();

		for (CompanyRequestPayment payment : list) {

			try {

				CompanyPaymentResponse response = new CompanyPaymentResponse();

				response.setId(payment.getId());
				response.setAmount(payment.getAmount());
				response.setSenderPhoneNumber(payment.getSenderPhoneNumber());
				response.setCmpanyId(payment.getCmpanyId());
				response.setSendingTime(payment.getSendingTime());
				response.setTransactionId(payment.getTransactionId());
				response.setSenderUserName(userNameMap.get(payment.getSenderUserId()).getFullName() == null
						? userNameMap.get(payment.getSenderUserId()).getName()
						: userNameMap.get(payment.getSenderUserId()).getFullName());
				response.setCompanyName(companyMap.get(payment.getCmpanyId()).getCompanyName());

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
