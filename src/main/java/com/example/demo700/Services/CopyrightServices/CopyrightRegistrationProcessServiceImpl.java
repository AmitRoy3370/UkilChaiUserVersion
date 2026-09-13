package com.example.demo700.Services.CopyrightServices;

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
import com.example.demo700.Model.CopyrightModels.Copyright;
import com.example.demo700.Model.CopyrightModels.CopyrightRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRegistrationProcessRepository;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CopyrightRegistrationProcessServiceImpl implements CopyrightRegistrationProcessService {

	@Autowired
	private CopyrightRegistrationProcessRepository processRepository;

	@Autowired
	private CopyrightRepository copyrightRepository;

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

	private static final String cacheValue = "CopyrightRegistrationProcess";

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public CopyrightRegistrationProcess addRegistrationProcess(CopyrightRegistrationProcess process, String userId) {

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

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			if (process.getUserId().equals(admin.getUserId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Only center admin can add process...");

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

		Copyright right = null;

		try {

			right = copyrightRepository.findById(process.getCopyrightId()).get();

			if (right == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

		try {

			CopyrightRegistrationProcess _copyrightRegistrationProcess = processRepository
					.findByCopyrightId(process.getCopyrightId());

			if (_copyrightRegistrationProcess != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This copy right process is already running...");

		} catch (Exception e) {

		}

		process = processRepository.save(process);

		return process;

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public CopyrightRegistrationProcess updateRegistrationProcess(CopyrightRegistrationProcess process, String userId,
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

		Advocate advocate = null;

		boolean isAdvocate = false;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			isAdvocate = advocate.getUserId().equals(userId);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(process.getUserId());

			if (admin == null) {

				if (!isAdvocate) {

					throw new Exception();

				}

			}

			if (isAdvocate) {

			} else {

				if (process.getUserId().equals(admin.getUserId())) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Only center admin can add process...");

		}

		Copyright right = null;

		try {

			right = copyrightRepository.findById(process.getCopyrightId()).get();

			if (right == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

		try {

			CopyrightRegistrationProcess _copyrightRegistrationProcess = processRepository
					.findByCopyrightId(process.getCopyrightId());

			if (_copyrightRegistrationProcess != null) {

				if (!_copyrightRegistrationProcess.getId().equals(id)) {

					if (!_copyrightRegistrationProcess.getId().equals(id)) {

						throw new ArithmeticException();

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This copy right process is already running...");

		} catch (Exception e) {

		}

		CopyrightRegistrationProcess registrationProcess = null;

		try {

			registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (!isAdvocate) {

				if (!registrationProcess.getUserId().equals(process.getUserId())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

		try {

			if (isAdvocate) {

				if (!registrationProcess.getUserId().equals(process.getUserId())) {

					throw new Exception();

				} else if (!registrationProcess.getAdvocateId().equals(process.getAdvocateId())) {

					throw new Exception();

				} else if (!registrationProcess.getCopyrightId().equals(process.getCopyrightId())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Advocate can change only status and steps...");

		}

		process.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", process.getId());
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("copyrightId", process.getCopyrightId());
		update.set("status", process.isStatus());
		update.set("stpes", process.getStpes());

		mongoTemplate.updateFirst(query, update, CopyrightRegistrationProcess.class);

		process = mongoTemplate.findOne(query, CopyrightRegistrationProcess.class);

		return process;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CopyrightRegistrationProcess findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CopyrightRegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CopyrightRegistrationProcess> findAll() {

		try {

			List<CopyrightRegistrationProcess> process = processRepository.findAll();

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override@Cacheable(value = cacheValue, key = "'findByCopyrightId_' + #copyrightId")
	public CopyrightRegistrationProcess findByCopyrightId(String copyrightId) {

		if (copyrightId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CopyrightRegistrationProcess process = processRepository.findByCopyrightId(copyrightId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<CopyrightRegistrationProcess> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CopyrightRegistrationProcess> process = processRepository.findByUserId(userId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<CopyrightRegistrationProcess> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CopyrightRegistrationProcess> process = processRepository.findByAdvocateId(advocateId);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStepsPrefix_' + #steps")
	public List<CopyrightRegistrationProcess> findByStpesContainingIgnoreCase(String steps) {

		if (steps == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CopyrightRegistrationProcess> process = processRepository.findByStpesContainingIgnoreCase(steps);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStatus_' + #status")
	public List<CopyrightRegistrationProcess> findByStatus(boolean status) {

		try {

			List<CopyrightRegistrationProcess> process = processRepository.findByStatus(status);

			if (process == null || process.isEmpty()) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright registration process find at here...");

		}
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public boolean deleteProcess(String id, String userId) {

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

		CopyrightRegistrationProcess registrationProcess = null;

		try {

			registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (!registrationProcess.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

		long count = processRepository.count();

		cleaner.removeCopyrightRegistrationProcess(id);

		return count != processRepository.count();

	}

}
