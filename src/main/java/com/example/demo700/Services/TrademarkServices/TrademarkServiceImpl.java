package com.example.demo700.Services.TrademarkServices;

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
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.TrademarkRegistrationProcessResponse;
import com.example.demo700.DTOFiles.TrademarkResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.Trademarkmodels.Trademark;
import com.example.demo700.Model.Trademarkmodels.TrademarkRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.TrademarkRepositories.TrademarkRegistrationProcessRepository;
import com.example.demo700.Repositories.TrademarkRepositories.TrademarkRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Validators.EmailValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class TrademarkServiceImpl implements TrademarkService {

	@Autowired
	private TrademarkRepository trademarkRepository;

	@Autowired
	private TrademarkRegistrationProcessRepository processRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	private EmailValidator emailValidator = new EmailValidator();

	private PhoneValidator phoneValidator;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "Trademark";

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)

	})
	public Trademark addTrademark(Trademark trademark, String userId, MultipartFile[] documents) {

		if (trademark == null || userId == null || !trademark.getUserId().equals(userId)) {

			throw new NullPointerException("False request.....");

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

			if (emailValidator.isValidEmail(trademark.getEmail())) {

			} else {

				throw new ArithmeticException();

			}

			Trademark mark = trademarkRepository.findByEmailIgnoreCase(trademark.getEmail());

			if (mark != null) {

				throw new RuntimeException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Email is not valid...");

		} catch (RuntimeException e) {

			throw new RuntimeException("Same email already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			phoneValidator = new PhoneValidator(trademark.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new ArithmeticException();

			}

			Trademark mark = trademarkRepository.findByMobileNumberIgnoreCase(trademark.getMobileNumber());

			if (mark != null) {

				throw new RuntimeException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Phone number is not valid...");

		} catch (RuntimeException e) {

			throw new RuntimeException("Same number already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			List<String> attachments = new ArrayList<>();

			for (String i : trademark.getDocuments()) {

				if (imageService.attachmentExists(i)) {

					attachments.add(i);

				} else {

					//throw new Exception();

				}

			}

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachments.add(attachmentId);

					}

				} catch (Exception e) {

				}

			}

			trademark.setDocuments(attachments);

		} catch (Exception e) {

			throw new ArithmeticException("document's are not valid...");

		}

		trademark = trademarkRepository.save(trademark);

		if (trademark == null) {

			throw new ArithmeticException("Trademark not saved...");

		}

		return trademark;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)

	})
	public Trademark updateTrademark(Trademark trademark, String userId, String id, MultipartFile[] documents) {

		if (id == null || trademark == null || userId == null || !trademark.getUserId().equals(userId)) {

			throw new NullPointerException("False request.....");

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

			mark = trademarkRepository.findById(id).get();

			if (mark == null) {

				throw new Exception();

			}

			if (!mark.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			if (emailValidator.isValidEmail(trademark.getEmail())) {

			} else {

				throw new ArithmeticException();

			}

			Trademark mark1 = trademarkRepository.findByEmailIgnoreCase(trademark.getEmail());

			if (mark1 != null) {

				if (!mark1.getId().equals(id)) {

					throw new RuntimeException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Email is not valid...");

		} catch (RuntimeException e) {

			throw new RuntimeException("Same email already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			phoneValidator = new PhoneValidator(trademark.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new ArithmeticException();

			}

			Trademark mark1 = trademarkRepository.findByMobileNumberIgnoreCase(trademark.getMobileNumber());

			if (mark1 != null) {

				if (!mark1.getId().equals(id)) {

					throw new RuntimeException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Phone number is not valid...");

		} catch (RuntimeException e) {

			throw new RuntimeException("Same number already exist...");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			List<String> attachments = new ArrayList<>();

			for (String i : mark.getDocuments()) {

				if (!trademark.getDocuments().contains(i)) {

					imageService.delete(i);

				}

			}

			for (String i : trademark.getDocuments()) {

				if (imageService.attachmentExists(i)) {

					attachments.add(i);

				} else {

					//throw new Exception();

				}

			}

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachments.add(attachmentId);

					}

				} catch (Exception e) {

				}

			}

			trademark.setDocuments(attachments);

		} catch (Exception e) {

			throw new ArithmeticException("document's are not valid...");

		}

		trademark = trademarkRepository.save(trademark);

		if (trademark == null) {

			throw new ArithmeticException("Trademark not saved...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("legalProtection", trademark.getLegalProtection());
		update.set("nationWiseValidity", trademark.getNationWiseValidity());
		update.set("applicationType", trademark.getApplicationType());
		update.set("applicationName", trademark.getApplicationName());
		update.set("governmentFee", trademark.getGovernmentFee());
		update.set("organaizationalName", trademark.getOrganaizationalName());
		update.set("trademarkName", trademark.getTrademarkName());
		update.set("trademarkType", trademark.getTrademarkType());
		update.set("classOfGoods", trademark.getClassOfGoods());
		update.set("adress", trademark.getAdress());
		update.set("email", trademark.getEmail());
		update.set("mobileNumber", trademark.getMobileNumber());
		update.set("documents", trademark.getDocuments());
		update.set("userId", trademark.getUserId());

		mongoTemplate.updateFirst(query, update, Trademark.class);

		trademark = mongoTemplate.findOne(query, Trademark.class);

		return trademark;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TrademarkResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Trademark list = trademarkRepository.findById(id).get();

			if (list == null) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TrademarkResponse> findAll() {

		try {

			List<Trademark> list = trademarkRepository.findAll();

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByLegalProtection_' + #legalProtection")
	public List<TrademarkResponse> findByLegalProtectionContainingIgnoreCase(String legalProtection) {

		if (legalProtection == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByLegalProtectionContainingIgnoreCase(legalProtection);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNationWiseValidityPrefix_' + #nationWiseValidity")
	public List<TrademarkResponse> findByNationWiseValidityContainingIgnoreCase(String nationWiseValidity) {

		if (nationWiseValidity == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByNationWiseValidityContainingIgnoreCase(nationWiseValidity);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByApplicationTypePrefix_' + #applicationType")
	public List<TrademarkResponse> findByApplicationTypeContainingIgnoreCase(String applicationType) {

		if (applicationType == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByApplicationTypeContainingIgnoreCase(applicationType);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByApplicationNamePrefix_' + #applicationName")
	public List<TrademarkResponse> findByApplicationNameContainingIgnoreCase(String applicationName) {

		if (applicationName == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByApplicationNameContainingIgnoreCase(applicationName);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByGovernmentFeeGTE_' + #governmentFee")
	public List<TrademarkResponse> findByGovernmentFeeGreaterThanEqual(double governmentFee) {

		try {

			List<Trademark> list = trademarkRepository.findByGovernmentFeeGreaterThanEqual(governmentFee);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByGovernmentFeeLTE_' + #governmentFee")
	public List<TrademarkResponse> findByGovernmentFeeLessThanEqual(double governmentFee) {

		try {

			List<Trademark> list = trademarkRepository.findByGovernmentFeeLessThanEqual(governmentFee);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEmail_' + #email")
	public TrademarkResponse findByEmailIgnoreCase(String email) {

		if (email == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Trademark list = trademarkRepository.findByEmailIgnoreCase(email);

			if (list == null) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEmailPrefix_' + #email")
	public List<TrademarkResponse> findByEmailContainingIgnoreCase(String email) {

		if (email == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByEmailContainingIgnoreCase(email);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMobileNumber_' + #mobileNumber")
	public TrademarkResponse findByMobileNumberIgnoreCase(String mobileNumber) {

		if (mobileNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Trademark list = trademarkRepository.findByMobileNumberIgnoreCase(mobileNumber);

			if (list == null) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMobileNumberPrefix_' + #mobileNumber")
	public List<TrademarkResponse> findByMobileNumberContainingIgnoreCase(String mobileNumber) {

		if (mobileNumber == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByMobileNumberContainingIgnoreCase(mobileNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #documents")
	public List<TrademarkResponse> findByDocumentsContainingIgnoreCase(String documents) {

		if (documents == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByDocumentsContainingIgnoreCase(documents);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<TrademarkResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByUserId(userId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdressPrefix_' + #adress")
	public List<TrademarkResponse> findByAdressContainingIgnoreCase(String adress) {

		if (adress == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByAdressContainingIgnoreCase(adress);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTrademarkNamePrefix_' + #trademarkName")
	public List<TrademarkResponse> findByTrademarkNameContainingIgnoreCase(String trademarkName) {

		if (trademarkName == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByTrademarkNameContainingIgnoreCase(trademarkName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTrademarkTypePrefix_' + #trademarkType")
	public List<TrademarkResponse> findByTrademarkTypeContainingIgnoreCase(String trademarkType) {

		if (trademarkType == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByTrademarkTypeContainingIgnoreCase(trademarkType);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByClassOfGoodsPrefix_' + #classOfGoods")
	public List<TrademarkResponse> findByClassOfGoodsContainingIgnoreCase(String classOfGoods) {

		if (classOfGoods == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Trademark> list = trademarkRepository.findByClassOfGoodsContainingIgnoreCase(classOfGoods);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTrademarkResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here....");

		}

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)

	})
	public boolean deleteTrademark(String id, String userId) {

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

			long count = trademarkRepository.count();

			cleaner.removeTrademark(id);

			return count != trademarkRepository.count();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		Trademark mark = null;

		try {

			mark = trademarkRepository.findById(id).get();

			if (mark == null) {

				throw new Exception();

			}

			if (!mark.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		long count = trademarkRepository.count();

		cleaner.removeTrademark(id);

		return count != trademarkRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private TrademarkResponse getTrademarkResponse(Trademark trademark) {

		List<Trademark> list = new ArrayList<>();

		list.add(trademark);

		return getTrademarkResponse(list).get(0);

	}

	private List<TrademarkResponse> getTrademarkResponse(List<Trademark> list) {

		List<TrademarkResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> trademarkIdsFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Trademark::getId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, TrademarkRegistrationProcess>> processMapFuture = trademarkIdsFuture
				.thenApplyAsync(trademarksId -> {

					if (trademarksId.isEmpty()) {

						return new HashMap<>();

					}

					return processRepository.findByTrademarkIdIn(trademarksId).stream().collect(
							Collectors.toMap(TrademarkRegistrationProcess::getTradeMarkId, Function.identity()));

				}, executor);

		CompletableFuture<List<TrademarkRegistrationProcess>> trademarkListFuture = processMapFuture
				.thenApplyAsync(processMap -> {

					if (processMap.isEmpty()) {

						return new ArrayList<>();

					}

					return processMap.values().stream().collect(Collectors.toList());

				}, executor);

		CompletableFuture<List<String>> advocatesId = trademarkListFuture.thenApplyAsync(trademarks -> {

			if (trademarks.isEmpty()) {

				return new ArrayList<>();

			}

			return trademarks.stream().map(TrademarkRegistrationProcess::getAdvocateId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<Map<String, Advocate>> advocatesMapFuture = advocatesId.thenApplyAsync(advocateIds -> {

			if (advocateIds.isEmpty()) {

				return new HashMap<>();

			}

			return advocateRepository.findAllById(advocateIds).stream()
					.collect(Collectors.toMap(Advocate::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<Advocate>> advocateListFuture = advocatesMapFuture.thenApplyAsync(advocatesMap -> {

			if (advocatesMap.isEmpty()) {

				return new ArrayList<>();

			}

			return advocatesMap.values().stream().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesUserId = advocateListFuture.thenApplyAsync(advocates -> {

			if (advocates.isEmpty()) {

				return new ArrayList<>();

			}

			return advocates.stream().map(Advocate::getUserId).distinct().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdFuture = trademarkListFuture.thenApplyAsync(trademarks -> {

			if (trademarks.isEmpty()) {

				return new ArrayList<>();

			}

			return trademarks.stream().map(TrademarkRegistrationProcess::getUserId).distinct()
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesUserAndCenterAdminsUserIdFuture = advocatesUserId.thenCombine(
				centerAdminsUserIdFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<List<String>> requestedUsersId = CompletableFuture.supplyAsync(
				() -> list.stream().map(Trademark::getUserId).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> allUserId = requestedUsersId.thenCombine(
				advocatesUserAndCenterAdminsUserIdFuture,
				(list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUserId.thenApplyAsync(allUsers -> {

			if (allUsers.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(allUsers).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(trademarkIdsFuture, processMapFuture, trademarkListFuture, advocatesId,
				advocateListFuture, advocatesUserId, centerAdminsUserIdFuture, advocatesUserAndCenterAdminsUserIdFuture,
				requestedUsersId, allUserId, userMapFuture, advocatesMapFuture).join();

		Map<String, User> userMap = userMapFuture.join();
		Map<String, Advocate> advocateMap = advocatesMapFuture.join();
		Map<String, TrademarkRegistrationProcess> processMap = processMapFuture.join();

		for (Trademark trademark : list) {

			try {

				TrademarkResponse response = new TrademarkResponse();

				response.setId(trademark.getId());
				response.setApplicationName(trademark.getApplicationName());
				response.setApplicationType(trademark.getApplicationType());
				response.setGovernmentFee(trademark.getGovernmentFee());
				response.setLegalProtection(trademark.getLegalProtection());
				response.setNationWiseValidity(trademark.getNationWiseValidity());
				response.setEmail(trademark.getEmail());
				response.setMobileNumber(trademark.getMobileNumber());
				response.setOrganaizationalName(trademark.getOrganaizationalName());
				response.setUserId(trademark.getUserId());
				response.setClassOfGoods(trademark.getClassOfGoods());
				response.setAdress(trademark.getAdress());
				response.setTrademarkName(trademark.getTrademarkName());
				response.setTrademarkType(trademark.getTrademarkType());
				response.setDocuments(trademark.getDocuments());

				try {

					response.setUserName(userMap.get(trademark.getUserId()).getFullName() == null
							? userMap.get(trademark.getUserId()).getName()
							: userMap.get(trademark.getUserId()).getFullName());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					TrademarkRegistrationProcessResponse process = new TrademarkRegistrationProcessResponse();

					TrademarkRegistrationProcess _process = processMap.get(trademark.getId());

					process.setId(_process.getId());
					process.setAdvocateId(_process.getAdvocateId());

					try {

						process.setAdvocateName(
								userMap.get(advocateMap.get(_process.getAdvocateId()).getUserId()).getFullName() == null
										? userMap.get(advocateMap.get(_process.getAdvocateId()).getUserId()).getName()
										: userMap.get(advocateMap.get(_process.getAdvocateId()).getUserId())
												.getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					process.setCenterAdminUserId(_process.getUserId());

					try {

						process.setCenterAdminUserName(userMap.get(_process.getUserId()).getFullName() == null
								? userMap.get(_process.getUserId()).getName()
								: userMap.get(_process.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					process.setStatus(_process.isStatus());
					process.setSteps(_process.getSteps());
					process.setTradeMark(trademark);
					process.setTradeMarkId(trademark.getId());

					response.setRegistrationProcess(process);

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
