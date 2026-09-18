package com.example.demo700.Services.RJSCServices;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo700.CyclicCleaner.Cleaner;
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

@Service
public class RJSCRegistrationProcessServiceImpl implements RJSCRegistrationProcessService {

	@Autowired
	private RJSCRegistrationProcessRepository processRepository;

	@Autowired
	private RJSCRepository rjscRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSCRegistrationProcess addProcess(RJSCRegistrationProcess process, String userId) {

		if (process == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (process.getUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only centeradmin can add process...");

		}

		RJSC rjsc = null;

		try {

			rjsc = rjscRepository.findById(process.getRjscId()).get();

			if (rjsc == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}

		process = processRepository.save(process);

		return process;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "RJSC", allEntries = true),
			@CacheEvict(value = "RJSCRegistrationProcess", allEntries = true),
			@CacheEvict(value = "RJSCPayment", allEntries = true),
	})
	public RJSCRegistrationProcess updateProcess(RJSCRegistrationProcess process, String userId, String id) {

		if (id == null || process == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (process.getUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				// throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only centeradmin can add process...");

		}

		boolean isAdvocate = admin == null && advocate.getUserId().equals(userId);

		RJSC rjsc = null;

		try {

			rjsc = rjscRepository.findById(process.getRjscId()).get();

			if (rjsc == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rjsc find at here...");

		}

		RJSCRegistrationProcess oldProcess = null;

		try {

			oldProcess = processRepository.findById(id).get();

			if (oldProcess == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here.....");

		}

		try {

			if (isAdvocate) {

				if (!oldProcess.getUserId().equals(process.getUserId())) {

					throw new Exception();

				} else if (!oldProcess.getAdvocateId().equals(process.getAdvocateId())) {

					throw new Exception();

				} else if (!oldProcess.getRjscId().equals(process.getRjscId())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Advocate can change only status and status...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("status", process.isStatus());
		update.set("steps", process.getSteps());

		mongoTemplate.updateFirst(query, update, RJSCRegistrationProcess.class);

		process = mongoTemplate.findOne(query, RJSCRegistrationProcess.class);

		return process;
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findById_' + #id")
	public RJSCRegistrationProcess findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RJSCRegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findAll'")
	public List<RJSCRegistrationProcess> findAll() {

		try {

			List<RJSCRegistrationProcess> process = processRepository.findAll();

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findByUserId_' + #userId")
	public List<RJSCRegistrationProcess> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSCRegistrationProcess> process = processRepository.findByUserId(userId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findByAdvocateId_' + #advocateId")
	public List<RJSCRegistrationProcess> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSCRegistrationProcess> process = processRepository.findByAdvocateId(advocateId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findByStatus_' + #status")
	public List<RJSCRegistrationProcess> findByStatus(boolean status) {

		try {

			List<RJSCRegistrationProcess> process = processRepository.findByStatus(status);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findByRJSCId_' + #rjscId")
	public RJSCRegistrationProcess findByRjscId(String rjscId) {

		if (rjscId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RJSCRegistrationProcess process = processRepository.findByRjscId(rjscId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findByRJSCIds_' + #rjscIds")
	public List<RJSCRegistrationProcess> findByRjscIdIn(List<String> rjscIds) {

		if (rjscIds == null || rjscIds.isEmpty()) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSCRegistrationProcess> process = processRepository.findByRjscIdIn(rjscIds);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}
	}

	@Override
	@Cacheable(value = "RJSCRegistrationProcess", key = "'findBySteps_' + #steps")
	public List<RJSCRegistrationProcess> findByStepsContainingIgnoreCase(String steps) {

		if (steps == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RJSCRegistrationProcess> process = processRepository.findByStepsContainingIgnoreCase(steps);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

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

			RJSCRegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			if (process.getUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

		long count = processRepository.count();

		cleaner.removeRJSCRegistrationProcess(id);

		return processRepository.count() != count;
	}

}
