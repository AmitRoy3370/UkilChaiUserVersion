package com.example.demo700.Services.CopyrightServices;

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
import com.example.demo700.DTOFiles.CopyrightRegistrationProcessResponse;
import com.example.demo700.DTOFiles.CopyrightResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CopyrightModels.Copyright;
import com.example.demo700.Model.CopyrightModels.CopyrightRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRegistrationProcessRepository;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Validators.EmailValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class CopyrightServiceImpl implements CopyrightService {

	@Autowired
	private CopyrightRepository copyrightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CopyrightRegistrationProcessRepository processRepository;

	@Autowired
	private Cleaner cleaner;

	private PhoneValidator phoneValidator;

	private EmailValidator emailValidator = new EmailValidator();

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String cacheValue = "Copyright";

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public Copyright addCopyright(Copyright copyright, String userId, MultipartFile documents[]) {

		if (copyright == null || userId == null || !copyright.getUserId().equals(userId)) {

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

			if (emailValidator.isValidEmail(copyright.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			phoneValidator = new PhoneValidator(copyright.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phon number is not valid...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(copyright.getEmail());

			if (right != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same email already exist...");

		} catch (Exception e) {

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(copyright.getMobileNumber());

			if (right != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same phone already exist...");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : copyright.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

		}

		copyright.setDocuments(attachmentsId);

		copyright = copyrightRepository.save(copyright);

		return copyright;
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public Copyright updateCopyright(Copyright copyright, String userId, String id, MultipartFile documents[]) {
		if (id == null || copyright == null || userId == null || !copyright.getUserId().equals(userId)) {

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

			if (emailValidator.isValidEmail(copyright.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			phoneValidator = new PhoneValidator(copyright.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phon number is not valid...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(copyright.getEmail());

			if (right != null) {

				if (!right.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same email already exist...");

		} catch (Exception e) {

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(copyright.getMobileNumber());

			if (right != null) {

				if (!right.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same phone already exist...");

		} catch (Exception e) {

		}

		Copyright _copyright = null;

		try {

			_copyright = copyrightRepository.findById(id).get();

			if (_copyright == null) {

				throw new Exception();

			}

			if (!_copyright.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such cpyright find at here...");

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : copyright.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

		}

		try {

			for (String i : _copyright.getDocuments()) {

				try {

					if (!copyright.getDocuments().contains(i)) {

						imageService.delete(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		copyright.setDocuments(attachmentsId);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", copyright.getUserId());
		update.set("author", copyright.getAuthor());
		update.set("typeOfWork", copyright.getTypeOfWork());
		update.set("yearOfCreation", copyright.getYearOfCreation());
		update.set("titleOfWork", copyright.getTitleOfWork());
		update.set("description", copyright.getDescription());
		update.set("applicationName", copyright.getApplicationName());
		update.set("mobileNumber", copyright.getMobileNumber());
		update.set("email", copyright.getEmail());
		update.set("adress", copyright.getAdress());
		update.set("documents", copyright.getDocuments());

		mongoTemplate.updateFirst(query, update, Copyright.class);

		copyright = mongoTemplate.findOne(query, Copyright.class);

		return copyright;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CopyrightResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findById(id).get();

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CopyrightResponse> findAll() {

		try {

			List<Copyright> right = copyrightRepository.findAll();

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<CopyrightResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByUserId(userId);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAuthorPrefix_' + #author")
	public List<CopyrightResponse> findByAuthorContainingIgnoreCase(String author) {

		if (author == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByAuthorContainingIgnoreCase(author);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTypeOfWork_' + #typeOfWork")
	public List<CopyrightResponse> findByTypeOfWorkContainingIgnoreCase(String typeOfWork) {

		if (typeOfWork == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByTypeOfWorkContainingIgnoreCase(typeOfWork);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfCreationAfter_' + #yearOfCreation")
	public List<CopyrightResponse> findByYearOfCreationAfter(Instant yearOfCreation) {

		if (yearOfCreation == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByYearOfCreationAfter(yearOfCreation);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfCreationBefore_' + #yearOfCreation")
	public List<CopyrightResponse> findByYearOfCreationBefore(Instant yearOfCreation) {

		if (yearOfCreation == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByYearOfCreationBefore(yearOfCreation);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTitleOfWork_' + #titleOfWork")
	public List<CopyrightResponse> findByTitleOfWorkContainingIgnoreCase(String titleOfWork) {

		if (titleOfWork == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByTitleOfWorkContainingIgnoreCase(titleOfWork);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDescription_' + #description")
	public List<CopyrightResponse> findByDescriptionContainingIgnoreCase(String description) {

		if (description == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByDescriptionContainingIgnoreCase(description);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByApplicationName_' + #applicationName")
	public List<CopyrightResponse> findByApplicationNameContainingIgnoreCase(String applicationName) {

		if (applicationName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByApplicationNameContainingIgnoreCase(applicationName);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMobileNumber_' + #mobileNumber")
	public CopyrightResponse findByMobileNumber(String mobileNumber) {

		if (mobileNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(mobileNumber);

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEmail_' + #email")
	public CopyrightResponse findByEmail(String email) {

		if (email == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(email);

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdress_' + #adress")
	public List<CopyrightResponse> findByAdressContainingIgnoreCase(String adress) {

		if (adress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByAdressContainingIgnoreCase(adress);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #documents")
	public List<CopyrightResponse> findByDocumentsContainingIgnoreCase(String documents) {

		if (documents == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByDocumentsContainingIgnoreCase(documents);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public boolean removeCopyright(String id, String userId) {
		if (userId == null || id == null) {

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

			long count = copyrightRepository.count();

			cleaner.removeCopyright(id);

			return count != copyrightRepository.count();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		Copyright copyright = null;

		try {

			copyright = copyrightRepository.findById(id).get();

			if (copyright == null) {

				throw new Exception();

			}

			if (!copyright.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such cpyright find at here...");

		}

		long count = copyrightRepository.count();

		cleaner.removeCopyright(id);

		return count != copyrightRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private CopyrightResponse getCopyrightResponse(Copyright copyright) {

		List<Copyright> list = new ArrayList<>();

		list.add(copyright);

		return getCopyrightResponse(list).get(0);

	}

	private List<CopyrightResponse> getCopyrightResponse(List<Copyright> list) {

		List<CopyrightResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> copyrightIdListFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Copyright::getId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, CopyrightRegistrationProcess>> processMapFuture = copyrightIdListFuture
				.thenApplyAsync(copyrightIds -> {

					if (copyrightIds.isEmpty()) {

						return new HashMap<>();

					}

					return processRepository.findAllById(copyrightIds).stream().collect(
							Collectors.toMap(CopyrightRegistrationProcess::getCopyrightId, Function.identity()));

				}, executor);

		CompletableFuture<List<CopyrightRegistrationProcess>> processListFuture = processMapFuture
				.thenApplyAsync(processMap -> {

					if (processMap.isEmpty()) {

						return new ArrayList<>();

					}

					return processMap.values().stream().collect(Collectors.toList());

				}, executor);

		CompletableFuture<List<String>> advocatesIdFuture = processListFuture.thenApplyAsync(processList -> {

			if (processList.isEmpty()) {

				return new ArrayList<>();

			}

			return processList.stream().map(CopyrightRegistrationProcess::getAdvocateId).distinct()
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<Advocate>> advocatesListFuture = advocatesIdFuture.thenApplyAsync(advocatesId -> {

			if (advocatesId.isEmpty()) {

				return new ArrayList<>();

			}

			return advocateRepository.findAllById(advocatesId).stream().collect(Collectors.toList());

		}, executor);

		CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesListFuture
				.thenApplyAsync(advocatesList -> {

					if (advocatesList.isEmpty()) {

						return new HashMap<>();

					}

					return advocatesList.stream().collect(Collectors.toMap(Advocate::getId, Function.identity()));

				}, executor);

		CompletableFuture<List<String>> advocatesUserIdListFuture = advocatesListFuture.thenApplyAsync(advocates -> {

			if (advocates.isEmpty()) {

				return new ArrayList<>();

			}

			return advocates.stream().map(Advocate::getUserId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdListFuture = processListFuture.thenApplyAsync(processList -> {

			if (processList.isEmpty()) {

				return new ArrayList<>();

			}

			return processList.stream().map(CopyrightRegistrationProcess::getUserId).distinct()
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminAndAdvocateUser = centerAdminsUserIdListFuture.thenCombine(
				advocatesUserIdListFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<List<String>> requestedUserId = CompletableFuture.supplyAsync(
				() -> list.stream().map(Copyright::getUserId).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> allUserId = requestedUserId.thenCombine(centerAdminAndAdvocateUser,
				(list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUserId.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture
				.allOf(copyrightIdListFuture, processMapFuture, processListFuture, advocatesIdFuture,
						advocatesListFuture, advocatesUserIdListFuture, centerAdminsUserIdListFuture,
						centerAdminAndAdvocateUser, requestedUserId, allUserId, userMapFuture, advocateMapFuture)
				.join();

		Map<String, User> userMap = userMapFuture.join();
		Map<String, Advocate> advocateMap = advocateMapFuture.join();
		Map<String, CopyrightRegistrationProcess> processMap = processMapFuture.join();

		for (Copyright copyright : list) {

			try {

				CopyrightResponse response = new CopyrightResponse();

				response.setId(copyright.getId());
				response.setUserId(copyright.getId());
				response.setAdress(copyright.getAdress());
				response.setApplicationName(copyright.getApplicationName());
				response.setAuthor(copyright.getAuthor());
				response.setDescription(copyright.getDescription());
				response.setDocuments(copyright.getDocuments());
				response.setEmail(copyright.getEmail());
				response.setMobileNumber(copyright.getMobileNumber());
				response.setTitleOfWork(copyright.getTitleOfWork());
				response.setTitleOfWork(copyright.getTypeOfWork());
				response.setYearOfCreation(copyright.getYearOfCreation());

				try {

					response.setUserName(userMap.get(copyright.getUserId()).getFullName() == null
							? userMap.get(copyright.getUserId()).getName()
							: userMap.get(copyright.getUserId()).getFullName());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					CopyrightRegistrationProcessResponse processResponse = new CopyrightRegistrationProcessResponse();

					CopyrightRegistrationProcess process = processMap.get(copyright.getId());

					processResponse.setId(process.getId());
					processResponse.setAdvocateId(process.getAdvocateId());
					processResponse.setUserId((process.getUserId()));
					processResponse.setStatus(process.isStatus());
					processResponse.setStpes(process.getStpes());
					processResponse.setCopyrightId(copyright.getId());

					try {

						processResponse.setAdvocateName(
								userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getFullName() == null
										? userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getName()
										: userMap.get(advocateMap.get(process.getAdvocateId()).getUserId())
												.getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					try {

						processResponse.setUserName(userMap.get(process.getUserId()).getFullName() == null
								? userMap.get(process.getUserId()).getName()
								: userMap.get(process.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					processResponse.setCopyright(copyright);

					response.setRegistrationProcess(processResponse);

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
