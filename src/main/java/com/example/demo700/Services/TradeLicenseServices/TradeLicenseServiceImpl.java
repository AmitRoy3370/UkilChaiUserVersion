package com.example.demo700.Services.TradeLicenseServices;

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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.TradeLicenseRegistrationProcessResponseDTO;
import com.example.demo700.DTOFiles.TradeLicenseResponseDTO;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.TradeLicenseModels.TradeLicense;
import com.example.demo700.Model.TradeLicenseModels.TradeLicenseRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicensePaymentRepository;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicenseRegistrationProcessRepository;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicenseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Validators.EmailValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class TradeLicenseServiceImpl implements TradeLicenseService {

	@Autowired
	private TradeLicenseRepository tradeLicenseRepository;

	@Autowired
	private TradeLicenseRegistrationProcessRepository registrationProcessRepository;

	@Autowired
	private TradeLicensePaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;

	private PhoneValidator phoneValidator;

	private EmailValidator emailValidator;

	private static final String cacheValue = "TradeLicense";

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public TradeLicense addTradeLicense(TradeLicense tradeLicense, String userId, MultipartFile[] documents) {

		if (tradeLicense == null || userId == null || !tradeLicense.getUserId().equals(userId)) {

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

			phoneValidator = new PhoneValidator(tradeLicense.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phone number is not valid...");

		}

		try {

			emailValidator = new EmailValidator();

			if (emailValidator.isValidEmail(tradeLicense.getEmailAdress())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			TradeLicense license = tradeLicenseRepository
					.findByEmailAdressContainingIgnoreCase(tradeLicense.getEmailAdress()).get(0);

			if (license != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same mail already exist at here...");

		} catch (Exception e) {

		}

		try {

			TradeLicense license = tradeLicenseRepository
					.findByMobileNumberContainingIgnoreCase(tradeLicense.getMobileNumber()).get(0);

			if (license != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same number already exist at here...");

		} catch (Exception e) {

		}

		try {

			List<String> attachmentsId = new ArrayList<>();

			for (MultipartFile file : documents) {

				try {

					if (file != null && !file.isEmpty()) {

						String id = imageService.upload(file);

						if (id != null) {

							attachmentsId.add(id);

						}

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

			tradeLicense.setDocuments(attachmentsId);

		} catch (Exception e) {

			throw new RuntimeException("File's are not saved...");

		}

		tradeLicense = tradeLicenseRepository.save(tradeLicense);

		if (tradeLicense == null) {

			throw new ArithmeticException("Trade license not saved...");

		}

		return tradeLicense;

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public TradeLicense updateTradeLicense(TradeLicense tradeLicense, String userId, String id,
			MultipartFile[] documents) {

		if (id == null || tradeLicense == null || userId == null || !tradeLicense.getUserId().equals(userId)) {

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

			TradeLicense license = tradeLicenseRepository.findById(id).get();

			if (license == null) {

				throw new Exception();

			}

			if (!license.getUserId().equals(userId)) {

				throw new Exception();

			}

			for (String i : license.getDocuments()) {

				try {

					if (!tradeLicense.getDocuments().contains(i)) {

						imageService.delete(i);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		try {

			phoneValidator = new PhoneValidator(tradeLicense.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phone number is not valid...");

		}

		try {

			emailValidator = new EmailValidator();

			if (emailValidator.isValidEmail(tradeLicense.getEmailAdress())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			TradeLicense license = tradeLicenseRepository
					.findByEmailAdressContainingIgnoreCase(tradeLicense.getEmailAdress()).get(0);

			if (license != null) {

				if (!license.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same mail already exist at here...");

		} catch (Exception e) {

		}

		try {

			TradeLicense license = tradeLicenseRepository
					.findByMobileNumberContainingIgnoreCase(tradeLicense.getMobileNumber()).get(0);

			if (license != null) {

				if (!license.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same number already exist at here...");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		for (String i : tradeLicense.getDocuments()) {

			try {

				if (imageService.attachmentExists(i)) {

					attachmentsId.add(i);

				}

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		try {

			for (MultipartFile file : documents) {

				try {

					if (file != null && !file.isEmpty()) {

						String newUploadedAttachmentId = imageService.upload(file);

						if (newUploadedAttachmentId != null) {

							attachmentsId.add(newUploadedAttachmentId);

						}

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			throw new RuntimeException("File's are not saved...");

		}

		tradeLicense.setDocuments(attachmentsId);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", tradeLicense.getUserId());
		update.set("buisnessName", tradeLicense.getBuisnessName());
		update.set("mobileNumber", tradeLicense.getMobileNumber());
		update.set("emailAdress", tradeLicense.getEmailAdress());
		update.set("buisnessType", tradeLicense.getBuisnessType());
		update.set("buisnessCategory", tradeLicense.getBuisnessCategory());
		update.set("documents", tradeLicense.getDocuments());

		mongoTemplate.updateFirst(query, update, TradeLicense.class);

		tradeLicense = mongoTemplate.findOne(query, TradeLicense.class);

		return tradeLicense;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TradeLicenseResponseDTO findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			TradeLicense license = tradeLicenseRepository.findById(id).get();

			if (license == null) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TradeLicenseResponseDTO> findAll() {

		try {

			List<TradeLicense> license = tradeLicenseRepository.findAll();

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<TradeLicenseResponseDTO> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByUserId(userId);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByBuisnessName_' + #buisnessName")
	public List<TradeLicenseResponseDTO> findByBuisnessNameContainingIgnoreCase(String buisnessName) {

		if (buisnessName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByBuisnessNameContainingIgnoreCase(buisnessName);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMobileNumber_' + #mobileNumber")
	public List<TradeLicenseResponseDTO> findByMobileNumberContainingIgnoreCase(String mobileNumber) {

		if (mobileNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByMobileNumberContainingIgnoreCase(mobileNumber);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEmail_' + #emailAdress")
	public List<TradeLicenseResponseDTO> findByEmailAdressContainingIgnoreCase(String emailAdress) {

		if (emailAdress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByEmailAdressContainingIgnoreCase(emailAdress);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByBuisnessType_' + #buisnessType")
	public List<TradeLicenseResponseDTO> findByBuisnessTypeContainingIgnoreCase(String buisnessType) {

		if (buisnessType == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByBuisnessTypeContainingIgnoreCase(buisnessType);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByBuisnessCategory_' + #documents")
	public List<TradeLicenseResponseDTO> findByBuisnessCategoryContainingIgnoreCase(String buisnessCategory) {

		if (buisnessCategory == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository
					.findByBuisnessCategoryContainingIgnoreCase(buisnessCategory);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocument_' + #documents")
	public List<TradeLicenseResponseDTO> findByDocumentsContainingIgnoreCase(String documents) {

		if (documents == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<TradeLicense> license = tradeLicenseRepository.findByDocumentsContainingIgnoreCase(documents);

			if (license == null || license.isEmpty()) {

				throw new Exception();

			}

			return getTradeLicenseResponse(license);

		} catch (Exception e) {

			throw new NullPointerException("No such trade license find at here...");

		}

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicenseRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public boolean removeTradeLicense(String id, String userId) {

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

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			long count = tradeLicenseRepository.count();

			cleaner.removeTradeLicense(id);

			return count != tradeLicenseRepository.count();

		} catch (Exception e) {

		}

		try {

			TradeLicense license = tradeLicenseRepository.findById(id).get();

			if (license == null) {

				throw new Exception();

			}

			if (!license.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		long count = tradeLicenseRepository.count();

		cleaner.removeTradeLicense(id);

		return count != tradeLicenseRepository.count();

	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private TradeLicenseResponseDTO getTradeLicenseResponse(TradeLicense tradeLicense) {

		List<TradeLicense> list = new ArrayList<>();

		list.add(tradeLicense);

		return getTradeLicenseResponse(list).get(0);

	}

	private List<TradeLicenseResponseDTO> getTradeLicenseResponse(List<TradeLicense> list) {

		CompletableFuture<List<String>> tradeLicenseIdListFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(TradeLicense::getId).collect(Collectors.toList()), executor);

		CompletableFuture<List<TradeLicenseRegistrationProcess>> processListFuture = tradeLicenseIdListFuture
				.thenApplyAsync(ids -> {

					if (ids.isEmpty()) {

						return new ArrayList<>();

					}

					return registrationProcessRepository.findByTradeLicenseIdIn(ids);

				}, executor);

		CompletableFuture<Map<String, TradeLicenseRegistrationProcess>> processMapFuture = processListFuture
				.thenApplyAsync(processes -> {

					if (processes.isEmpty()) {

						return new HashMap<>();

					}

					return processes.stream().collect(
							Collectors.toMap(TradeLicenseRegistrationProcess::getTradeLicenseId, Function.identity()));

				}, executor);

		CompletableFuture<List<String>> advocatesIdListFuture = processListFuture.thenApplyAsync(processList -> {

			if (processList.isEmpty()) {

				return new ArrayList<>();

			}

			return processList.stream().map(TradeLicenseRegistrationProcess::getAdvocateId)
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesIdListFuture
				.thenApplyAsync(advocatesId -> {

					if (advocatesId.isEmpty()) {

						return new HashMap<>();

					}

					return advocateRepository.findAllById(advocatesId).stream()
							.collect(Collectors.toMap(Advocate::getId, Function.identity()));

				}, executor);

		CompletableFuture<List<Advocate>> advocatesListFuture = advocateMapFuture.thenApplyAsync(advocatesMap -> {

			if (advocatesMap.isEmpty()) {

				return new ArrayList<>();

			}

			return advocatesMap.values().stream().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserId = processListFuture.thenApplyAsync(processList -> {

			if (processList.isEmpty()) {

				return new ArrayList<>();

			}

			return processList.stream().map(TradeLicenseRegistrationProcess::getUserId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesUserId = advocatesListFuture.thenApplyAsync(advocates -> {

			if (advocates.isEmpty()) {

				return new ArrayList<>();

			}

			return advocates.stream().map(Advocate::getUserId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesUserIdAndCenterAdminUserId = advocatesUserId.thenCombine(
				centerAdminsUserId, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<List<String>> requestedUserIdListFuture = processListFuture.thenApplyAsync(processes -> {

			if (list.isEmpty()) {

				return new ArrayList<>();

			}

			return list.stream().map(TradeLicense::getUserId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> allUserIdListFuture = advocatesUserIdAndCenterAdminUserId.thenCombine(
				requestedUserIdListFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUserIdListFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(tradeLicenseIdListFuture, processListFuture, processMapFuture, advocatesIdListFuture,
				advocateMapFuture, advocatesListFuture, centerAdminsUserId, advocatesUserId,
				advocatesUserIdAndCenterAdminUserId, requestedUserIdListFuture, allUserIdListFuture, userMapFuture);

		Map<String, User> userMap = userMapFuture.join();
		Map<String, Advocate> advocateMap = advocateMapFuture.join();
		Map<String, TradeLicenseRegistrationProcess> processMap = processMapFuture.join();

		List<TradeLicenseResponseDTO> responses = new ArrayList<>();

		for (TradeLicense tradeLicense : list) {

			try {

				TradeLicenseResponseDTO response = new TradeLicenseResponseDTO();

				response.setId(tradeLicense.getId());
				response.setBuisnessName(tradeLicense.getBuisnessName());
				response.setBuisnessCategory(tradeLicense.getBuisnessCategory());
				response.setBuisnessType(tradeLicense.getBuisnessType());
				response.setEmailAdress(tradeLicense.getEmailAdress());
				response.setMobileNumber(tradeLicense.getMobileNumber());
				response.setUserId(tradeLicense.getUserId());
				response.setDocuments(tradeLicense.getDocuments());

				try {

					response.setUserName(userMap.get(tradeLicense.getUserId()).getFullName() == null
							? userMap.get(tradeLicense.getUserId()).getName()
							: userMap.get(tradeLicense.getUserId()).getFullName());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					TradeLicenseRegistrationProcess process = processMap.get(tradeLicense.getId());

					if (process == null) {

						throw new Exception();

					}

					TradeLicenseRegistrationProcessResponseDTO dto = new TradeLicenseRegistrationProcessResponseDTO();

					dto.setId(process.getId());
					dto.setAdvocateId(process.getAdvocateId());
					dto.setUserId(process.getUserId());
					dto.setStatus(process.isStatus());
					dto.setSteps(process.getSteps());
					dto.setTradeLicense(tradeLicense);
					dto.setTradeLicenseId(tradeLicense.getId());

					try {

						dto.setUserName(userMap.get(process.getUserId()).getFullName() == null
								? userMap.get(process.getUserId()).getName()
								: userMap.get(process.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					try {

						dto.setAdvocateName(
								userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getFullName() == null
										? userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getName()
										: userMap.get(advocateMap.get(process.getAdvocateId()).getUserId())
												.getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					response.setTradeLicenseRegistrationProcess(dto);

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				responses.add(response);

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		return responses;

	}

}
