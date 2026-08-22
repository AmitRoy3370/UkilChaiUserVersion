package com.example.demo700.Services.UserServices;

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
import com.example.demo700.DTOFiles.RegistrationProcessResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.RegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.RegistrationProcessRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class RegistrationProcessServiceImpl implements RegistrationProcessService {

	@Autowired
	private RegistrationProcessRepository processRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "RegistrationProcess";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true), @CacheEvict(value = "CompanyPayment", allEntries = true) })
	public RegistrationProcess addRegistrationProcess(RegistrationProcess process, String userId) {

		if (process == null || userId == null || process.getShareValuePerShare() <= 0
				|| process.getCompanyId() == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (user.getId().equals(process.getUserId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		CenterAdmin centerAdmin = null;

		try {

			centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only center admin can add process");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		try {

			if (centerAdmin.getAdvocates().contains(advocate.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("You can add only your under advocates...");

		}

		CompanyInformation company = null;

		try {

			company = companyRepository.findById(process.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company request exist at here...");

		}

		try {

			List<RegistrationProcess> list = processRepository.findByCompanyId(company.getId());

			if (list == null || list.isEmpty()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("This companies registration process is already added...");

		}

		process = processRepository.save(process);

		return process;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true), @CacheEvict(value = "CompanyPayment", allEntries = true) })
	public RegistrationProcess updateRegistrationprocess(RegistrationProcess process, String userId, String id) {

		if (process == null || userId == null || process.getShareValuePerShare() <= 0
				|| process.getCompanyId() == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (user.getId().equals(process.getUserId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		CenterAdmin centerAdmin = null;

		try {

			centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only center admin can add process");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		try {

			if (centerAdmin.getAdvocates().contains(advocate.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("You can add only your under advocates...");

		}

		CompanyInformation company = null;

		try {

			company = companyRepository.findById(process.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company request exist at here...");

		}

		try {

			List<RegistrationProcess> list = processRepository.findByCompanyId(company.getId());

			if (list == null || list.isEmpty()) {

			} else {

				if (list.size() == 1) {

					if (!list.get(0).getId().equals(id)) {

						throw new Exception();

					}

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("This companies registration process is already added...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("companyId", process.getCompanyId());
		update.set("status", process.isStatus());
		update.set("shareValuePerShare", process.getShareValuePerShare());
		update.set("steps", process.getSteps());

		mongoTemplate.updateFirst(query, update, RegistrationProcess.class);

		return process = mongoTemplate.findOne(query, RegistrationProcess.class);

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true), @CacheEvict(value = "CompanyPayment", allEntries = true) })
	public RegistrationProcess addSteps(String id, String step, String userId) {

		if (id == null || step == null | userId == null) {

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

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		RegistrationProcess process = null;

		try {

			process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			if (process.getAdvocateId().equals(advocate.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process exist at here...");

		}

		if (step != null) {

			if (process.getSteps() == null) {

				process.setSteps(new ArrayList<>());

			}

			List<String> list = process.getSteps();

			list.add(step);

			process.setSteps(list);

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("companyId", process.getCompanyId());
		update.set("status", process.isStatus());
		update.set("shareValuePerShare", process.getShareValuePerShare());
		update.set("steps", process.getSteps());

		mongoTemplate.updateFirst(query, update, RegistrationProcess.class);

		return process = mongoTemplate.findOne(query, RegistrationProcess.class);

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public RegistrationProcessResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<RegistrationProcessResponse> findAll() {

		try {

			List<RegistrationProcess> process = processRepository.findAll();

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<RegistrationProcessResponse> findByCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByCompanyId(companyId);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<RegistrationProcessResponse> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByAdvocateId(advocateId);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<RegistrationProcessResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByUserId(userId);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStatus_' + #status")
	public List<RegistrationProcessResponse> findByStatus(boolean status) {

		try {

			List<RegistrationProcess> process = processRepository.findByStatus(status);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareValuePerShareLte_' + #shareValuePerShare")
	public List<RegistrationProcessResponse> findByShareValuePerShareLte(double shareValuePerShare) {

		try {

			List<RegistrationProcess> process = processRepository.findByShareValuePerShareLte(shareValuePerShare);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareValuePerShareGte_' + #shareValuePerShare")
	public List<RegistrationProcessResponse> findByShareValuePerShareGte(double shareValuePerShare) {

		try {

			List<RegistrationProcess> process = processRepository.findByShareValuePerShareGte(shareValuePerShare);

			if (process == null) {

				throw new Exception();

			}

			return getRegistrationProcessResponse(process);

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true), @CacheEvict(value = "CompanyPayment", allEntries = true) })
	public boolean deleteRegistrationProcess(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			RegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			if (!process.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such process exist at here...");

		}

		long count = processRepository.count();

		cleaner.removeRegistrationProcess(id);

		return count != processRepository.count();

	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private RegistrationProcessResponse getRegistrationProcessResponse(RegistrationProcess process) {

		List<RegistrationProcess> list = new ArrayList<>();

		list.add(process);

		return getRegistrationProcessResponse(list).get(0);

	}

	private List<RegistrationProcessResponse> getRegistrationProcessResponse(List<RegistrationProcess> processes) {

		List<RegistrationProcessResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> advocateIdFuture = CompletableFuture.supplyAsync(
				() -> processes.stream().map(RegistrationProcess::getAdvocateId).collect(Collectors.toList()),
				executor);

		CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocateIdFuture.thenApplyAsync(advocatesId -> {

			if (advocatesId.isEmpty()) {

				return new HashMap<>();

			}

			return advocateRepository.findAllById(advocatesId).stream()
					.collect(Collectors.toMap(Advocate::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<String>> companyIdFuture = CompletableFuture.supplyAsync(
				() -> processes.stream().map(RegistrationProcess::getCompanyId).distinct().collect(Collectors.toList()),
				executor);

		CompletableFuture<Map<String, CompanyInformation>> companyMapFuture = companyIdFuture
				.thenApplyAsync(companiesId -> {

					if (companiesId.isEmpty()) {

						return new HashMap<>();

					}

					return companyRepository.findAllById(companiesId).stream()
							.collect(Collectors.toMap(CompanyInformation::getId, Function.identity()));

				}, executor);

		CompletableFuture<List<String>> advocateUserIdFuture = advocateMapFuture.thenApplyAsync(advocates -> {

			if (advocates.isEmpty()) {

				return new ArrayList<>();

			}

			return advocates.values().stream().map(Advocate::getUserId).collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> userIdFuture = CompletableFuture
				.supplyAsync(() -> processes.stream().map(RegistrationProcess::getUserId).collect(Collectors.toList()),
						executor)
				.thenCombineAsync(advocateUserIdFuture, (userIds, advocateUsersId) -> {

					List<String> list = new ArrayList<>(userIds);

					list.addAll(advocateUsersId);

					return list;

				}, executor);

		CompletableFuture<Map<String, User>> userMapFuture = userIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(advocateIdFuture, advocateMapFuture, companyIdFuture, companyMapFuture,
				advocateUserIdFuture, userIdFuture, userMapFuture).join();

		Map<String, Advocate> advocateMap = advocateMapFuture.join();

		Map<String, User> userMap = userMapFuture.join();

		Map<String, CompanyInformation> companyMap = companyMapFuture.join();

		for (RegistrationProcess process : processes) {

			try {

				RegistrationProcessResponse response = new RegistrationProcessResponse();

				response.setId(process.getId());
				response.setCompanyId(process.getCompanyId());
				response.setAdvocateId(process.getAdvocateId());
				response.setUserId(process.getUserId());
				response.setStatus(process.isStatus());
				response.setSteps(process.getSteps());
				response.setShareValuePerShare(process.getShareValuePerShare());

				try {

					response.setUserName(userMap.get(process.getUserId()).getFullName() == null
							? userMap.get(process.getUserId()).getName()
							: userMap.get(process.getUserId()).getFullName());

				} catch (Exception e) {

				}

				try {

					response.setAdvocateName(
							userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getFullName() == null
									? userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getName()
									: userMap.get(advocateMap.get(process.getAdvocateId()).getUserId()).getFullName());

				} catch (Exception e) {

				}

				try {

					response.setCompanyName(companyMap.get(process.getCompanyId()).getCompanyName());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
