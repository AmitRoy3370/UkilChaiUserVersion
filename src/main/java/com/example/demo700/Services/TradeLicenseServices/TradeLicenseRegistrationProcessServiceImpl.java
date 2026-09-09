package com.example.demo700.Services.TradeLicenseServices;

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
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.TradeLicenseModels.TradeLicense;
import com.example.demo700.Model.TradeLicenseModels.TradeLicenseRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicenseRegistrationProcessRepository;
import com.example.demo700.Repositories.TradeLicenseRepository.TradeLicenseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class TradeLicenseRegistrationProcessServiceImpl implements TradeLicenseRegistrationProcessService {

	@Autowired
	private TradeLicenseRegistrationProcessRepository processRepository;

	@Autowired
	private TradeLicenseRepository tradeLicenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;
	
	private static final String cacheValue = "TradeLicenseRegistrationProcess";

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public TradeLicenseRegistrationProcess addProcess(TradeLicenseRegistrationProcess process, String userId) {

		if (process == null || userId == null) {

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

		boolean isAdvocate = false;

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			isAdvocate = advocate.getUserId().equals(user.getId());

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(process.getUserId());

			if (admin == null) {

				throw new Exception();

			}

			if (!admin.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such centeradmin find at here...");

		}

		TradeLicense tradeLicense = null;

		try {

			tradeLicense = tradeLicenseRepository.findById(process.getTradeLicenseId()).get();

			if (tradeLicense == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		try {

			TradeLicenseRegistrationProcess registrationProcess = processRepository
					.findByTradeLicenseId(tradeLicense.getId());

			if (registrationProcess != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trade license process is already running...");

		}

		process = processRepository.save(process);

		return process;
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public TradeLicenseRegistrationProcess updateProcess(TradeLicenseRegistrationProcess process, String userId,
			String id) {

		if (id == null || process == null || userId == null) {

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

		boolean isAdvocate = false;

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			isAdvocate = advocate.getUserId().equals(user.getId());

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(process.getUserId());

			if (admin == null) {

				throw new Exception();

			}

			if (!admin.getUserId().equals(user.getId())) {

				if (isAdvocate) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such centeradmin find at here...");

		}

		TradeLicense tradeLicense = null;

		try {

			tradeLicense = tradeLicenseRepository.findById(process.getTradeLicenseId()).get();

			if (tradeLicense == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trade license find at here...");

		}

		try {

			TradeLicenseRegistrationProcess registrationProcess = processRepository
					.findByTradeLicenseId(tradeLicense.getId());

			if (registrationProcess != null) {

				if (registrationProcess.getId().equals(id)) {

				} else {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trade license process is already running...");

		} catch (Exception e) {

		}

		try {

			TradeLicenseRegistrationProcess registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (isAdvocate) {

				if (!registrationProcess.getAdvocateId().equals(process.getAdvocateId())) {

					throw new ArithmeticException();

				} else if (!registrationProcess.getUserId().equals(process.getUserId())) {

					throw new ArithmeticException();

				} else if (!registrationProcess.getTradeLicenseId().equals(process.getTradeLicenseId())) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Advocate can change only steps and status...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such registration process find at here..");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("status", process.isStatus());
		update.set("steps", process.getSteps());
		update.set("tradeLicenseId", process.getTradeLicenseId());

		mongoTemplate.updateFirst(query, update, TradeLicenseRegistrationProcess.class);

		process = mongoTemplate.findOne(query, TradeLicenseRegistrationProcess.class);

		return process;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TradeLicenseRegistrationProcess findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		TradeLicenseRegistrationProcess process = null;

		try {

			process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

		return process;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TradeLicenseRegistrationProcess> findAll() {

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findAll();

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<TradeLicenseRegistrationProcess> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findByUserId(userId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<TradeLicenseRegistrationProcess> findByAdvocateId(String advocateId) {
		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findByAdvocateId(advocateId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStatus_' + #status")
	public List<TradeLicenseRegistrationProcess> findByStatus(boolean status) {

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findByStatus(status);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTradeLicensId_' + #tradeLicenseId")
	public TradeLicenseRegistrationProcess findByTradeLicenseId(String tradeLicenseId) {
		if (tradeLicenseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			TradeLicenseRegistrationProcess process = processRepository.findByTradeLicenseId(tradeLicenseId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySteps_' + #steps")
	public List<TradeLicenseRegistrationProcess> findByStepsContainingIgnoreCase(String steps) {
		if (steps == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findByStepsContainingIgnoreCase(steps);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTradeLicensesId_' + #tradeLicensesId")
	public List<TradeLicenseRegistrationProcess> findByTradeLicenseIdIn(List<String> tradeLicensesId) {
		if (tradeLicensesId == null || tradeLicensesId.isEmpty()) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TradeLicenseRegistrationProcess> process = processRepository.findByTradeLicenseIdIn(tradeLicensesId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TradeLicense", allEntries = true),
			@CacheEvict(value = "TradeLicensePayment", allEntries = true)

	})
	public boolean removeTradeLicenseRegistrationProcess(String id, String userId) {

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

			TradeLicenseRegistrationProcess registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (!registrationProcess.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Advocate can change only steps and status...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such registration process find at here..");

		}

		long count = processRepository.count();

		cleaner.removeTradeLicenseRegistrationProcess(id);

		return processRepository.count() != count;

	}

}
