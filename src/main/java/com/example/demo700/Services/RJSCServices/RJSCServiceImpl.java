package com.example.demo700.Services.RJSCServices;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.RJSCRegistrationProcessResponseDTO;
import com.example.demo700.DTOFiles.RJSCResponseDTO;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.RJSCModels.RJSC;
import com.example.demo700.Model.RJSCModels.RJSCRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.RJSCRepositories.RJSCRegistrationProcessRepository;
import com.example.demo700.Repositories.RJSCRepositories.RJSCRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Validators.EmailValidator;

@Service
public class RJSCServiceImpl implements RJSCService {

	@Autowired
	private RJSCRepository rjscRepository;

	@Autowired
	private RJSCRegistrationProcessRepository processRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private ImageService imageService;

	private EmailValidator emailValidator = new EmailValidator();

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSC addRJSC(RJSC rjsc, String userId, MultipartFile[] documents) {

		if (userId == null || rjsc == null || !rjsc.getUserId().equals(userId)) {

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

			if (emailValidator.isValidEmail(rjsc.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			RJSC _rjsc = rjscRepository.findByEmail(rjsc.getEmail());

			if (_rjsc != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This mail already exist....");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : rjsc.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				if (i != null && !i.isEmpty()) {

					String attachmentId = imageService.upload(i);

					attachmentsId.add(attachmentId);

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		rjsc.setDocuments(attachmentsId);

		rjsc = rjscRepository.save(rjsc);

		return rjsc;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSC updateRJSC(RJSC rjsc, String userId, String id, MultipartFile[] documents) {

		if (id == null || userId == null || rjsc == null || !rjsc.getUserId().equals(userId)) {

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

			if (emailValidator.isValidEmail(rjsc.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			RJSC _rjsc = rjscRepository.findByEmail(rjsc.getEmail());

			if (_rjsc != null) {

				if (!_rjsc.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This mail already exist....");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		RJSC _rjsc = null;

		try {

			_rjsc = rjscRepository.findById(id).get();

			if (_rjsc == null) {

				throw new Exception();

			}

			if (!_rjsc.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}

		try {

			for (String i : _rjsc.getDocuments()) {

				try {

					if (!rjsc.getDocuments().contains(i)) {

						imageService.delete(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (String i : rjsc.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				if (i != null && !i.isEmpty()) {

					String attachmentId = imageService.upload(i);

					attachmentsId.add(attachmentId);

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		rjsc.setDocuments(attachmentsId);

		rjsc.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", rjsc.getUserId());
		update.set("compilenceService", rjsc.getCompilenceService());
		update.set("registrationNo", rjsc.getRegistrationNo());
		update.set("email", rjsc.getEmail());
		update.set("companyName", rjsc.getCompanyName());
		update.set("year", rjsc.getYear());
		update.set("documents", rjsc.getDocuments());

		mongoTemplate.updateFirst(query, update, RJSC.class);

		rjsc = mongoTemplate.findOne(query, RJSC.class);

		return rjsc;

	}

	@Override
	@Cacheable(value = "RJSC", key = "'findById_' + #id")
	public RJSCResponseDTO findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RJSC list = rjscRepository.findById(id).get();

			if (list == null) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findAll'")
	public List<RJSCResponseDTO> findAll() {

		try {

			List<RJSC> list = rjscRepository.findAll();

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByUserId_' + #userId")
	public List<RJSCResponseDTO> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByUserId(userId);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByCompilenceService_' + #compilenceService")
	public List<RJSCResponseDTO> findByCompilenceServiceContainingIgnoreCase(String compilenceService) {

		if (compilenceService == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByCompilenceServiceContainingIgnoreCase(compilenceService);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByRegistrationNo_' + #registrationNo")
	public List<RJSCResponseDTO> findByRegistrationNoContainingIgnoreCase(String registrationNo) {

		if (registrationNo == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByRegistrationNoContainingIgnoreCase(registrationNo);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByEmail_' + #email")
	public RJSCResponseDTO findByEmail(String email) {

		if (email == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RJSC list = rjscRepository.findByEmail(email);

			if (list == null) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByCompanyName_' + #companyName")
	public List<RJSCResponseDTO> findByCompanyNameContainingIgnoreCase(String companyName) {

		if (companyName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByCompanyNameContainingIgnoreCase(companyName);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByYearAfter_' + #year")
	public List<RJSCResponseDTO> findByYearAfter(Instant year) {

		if (year == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByYearAfter(year);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByYearBefore_' + #year")
	public List<RJSCResponseDTO> findByYearBefore(Instant year) {

		if (year == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByYearBefore(year);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}

	}

	@Override
	@Cacheable(value = "RJSC", key = "'findByDocuments_' + #documents")
	public List<RJSCResponseDTO> findByDocuments(String documents) {

		if (documents == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSC> list = rjscRepository.findByDocuments(documents);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getRJSCResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

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

			throw new NullPointerException("False request");

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

			long count = rjscRepository.count();

			cleaner.removeRJSC(id);

			return count != rjscRepository.count();

		} catch (Exception e) {

		}

		RJSC _rjsc = null;

		try {

			_rjsc = rjscRepository.findById(id).get();

			if (_rjsc == null) {

				throw new Exception();

			}

			if (!_rjsc.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}

		long count = rjscRepository.count();

		cleaner.removeRJSC(id);

		return count != rjscRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private RJSCResponseDTO getRJSCResponse(RJSC rjsc) {

		List<RJSC> list = new ArrayList<>();

		list.add(rjsc);

		return getRJSCResponse(list).get(0);

	}

	private List<RJSCResponseDTO> getRJSCResponse(List<RJSC> list) {

		List<RJSCResponseDTO> responses = new ArrayList<>();

		CompletableFuture<List<String>> rjscIdListFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(RJSC::getId).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<List<RJSCRegistrationProcess>> processListFuture = rjscIdListFuture.thenApplyAsync(rjscId -> {

			if (rjscId.isEmpty()) {

				return new ArrayList<>();

			}

			return processRepository.findByRjscIdIn(rjscId).stream().distinct().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesIdListFuture = processListFuture.thenApplyAsync(processes -> {

			if (processes.isEmpty()) {

				return new ArrayList<>();

			}

			return processes.stream().map(RJSCRegistrationProcess::getAdvocateId).distinct()
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

		CompletableFuture<List<String>> advocatesUserIdFuture = advocateMapFuture.thenApplyAsync(advocatesMap -> {

			if (advocatesMap.isEmpty()) {

				return new ArrayList<>();

			}

			return advocatesMap.values().stream().collect(Collectors.toList()).stream().map(Advocate::getUserId)
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdListFuture = processListFuture.thenApplyAsync(processes -> {

			if (processes.isEmpty()) {

				return new ArrayList<>();

			}

			return processes.stream().map(RJSCRegistrationProcess::getUserId).distinct().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdAndAdvocatesUserIdListFuture = centerAdminsUserIdListFuture
				.thenCombine(advocatesUserIdFuture,
						(list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<List<String>> requestedUserIdListFuture = CompletableFuture.supplyAsync(
				() -> list.stream().map(RJSC::getUserId).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> allUserIdListFuture = requestedUserIdListFuture.thenCombine(
				centerAdminsUserIdAndAdvocatesUserIdListFuture,
				(list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUserIdListFuture.thenApplyAsync(allUserId -> {

			if (allUserId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(allUserId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, RJSCRegistrationProcess>> processMapFuture = processListFuture
				.thenApplyAsync(processList -> {

					if (processList.isEmpty()) {

						return new HashMap<>();

					}

					return processList.stream()
							.collect(Collectors.toMap(RJSCRegistrationProcess::getRjscId, Function.identity()));

				}, executor);

		CompletableFuture.allOf(rjscIdListFuture, processListFuture, advocatesIdListFuture, advocateMapFuture,
				advocatesUserIdFuture, centerAdminsUserIdListFuture, centerAdminsUserIdAndAdvocatesUserIdListFuture,
				requestedUserIdListFuture, allUserIdListFuture, userMapFuture, processMapFuture).join();

		Map<String, Advocate> advocateMap = advocateMapFuture.join();
		Map<String, User> userMap = userMapFuture.join();
		Map<String, RJSCRegistrationProcess> processMap = processMapFuture.join();

		for (RJSC rjsc : list) {

			try {

				RJSCResponseDTO response = new RJSCResponseDTO();

				response.setId(rjsc.getId());
				response.setUserId(rjsc.getUserId());
				response.setCompanyName(rjsc.getCompanyName());
				response.setCompilenceService(rjsc.getCompilenceService());
				response.setEmail(rjsc.getEmail());
				response.setRegistrationNo(rjsc.getRegistrationNo());
				response.setYear(rjsc.getYear());
				response.setDocuments(rjsc.getDocuments());

				try {

					response.setUserName(userMap.get(rjsc.getUserId()).getFullName() == null
							? userMap.get(rjsc.getUserId()).getName()
							: userMap.get(rjsc.getUserId()).getFullName());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					RJSCRegistrationProcessResponseDTO processResponse = new RJSCRegistrationProcessResponseDTO();

					RJSCRegistrationProcess process = processMap.get(rjsc.getId());

					processResponse.setId(process.getId());
					processResponse.setUserId(process.getUserId());
					processResponse.setAdvocateId(process.getAdvocateId());
					processResponse.setStatus(process.isStatus());
					processResponse.setSteps(process.getSteps());
					processResponse.setRjsc(rjsc);

					try {

						processResponse.setUserName(userMap.get(process.getUserId()).getFullName() == null
								? userMap.get(process.getUserId()).getName()
								: userMap.get(process.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					try {

						processResponse.setAdvocateName(
								userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getFullName() == null
										? userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getName()
										: userMap.get(advocateMap.get(process.getAdvocateId()).getUserId())
												.getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

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
