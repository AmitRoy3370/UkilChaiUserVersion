package com.example.demo700.Services.TrademarkServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
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

@Service
public class TrademarkRegistrationProcessServiceImpl implements TrademarkRegistrationProcessService {

	@Autowired
	private TrademarkRegistrationProcessRepository processRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private TrademarkRepository trademarkRepository;

	@Autowired
	private MongoTemplate mongoTempalte;

	@Autowired
	private Cleaner cleaner;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public TrademarkRegistrationProcess addTrademarkRegistrationProcess(TrademarkRegistrationProcess process,
			String userId) {

		if (process == null || userId == null) {

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

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such center admin find at here...");

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

		Trademark mark = null;

		try {

			mark = trademarkRepository.findById(process.getTradeMarkId()).get();

			if (mark == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			TrademarkRegistrationProcess _process = processRepository.findByTradeMarkId(mark.getId());

			if (_process != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trademark's process is already running....");

		} catch (Exception e) {

		}

		process = processRepository.save(process);

		return process;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public TrademarkRegistrationProcess updateTrademarkRegistrationProcess(TrademarkRegistrationProcess process,
			String userId, String id) {

		if (id == null || process == null || userId == null) {

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

		Advocate advocate = null;

		boolean isAdvocate = false;

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

			admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				if (!isAdvocate) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such center admin find at here...");

		}

		TrademarkRegistrationProcess registrationProcess = null;

		try {

			registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			if (isAdvocate) {

				if (!process.getUserId().equals(registrationProcess.getUserId())) {

					throw new Exception();

				} else if (!process.getAdvocateId().equals(registrationProcess.getAdvocateId())) {

					throw new Exception();

				} else if (!process.getTradeMarkId().equals(registrationProcess.getTradeMarkId())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Advocate can change on steps and status...");

		}

		Trademark mark = null;

		try {

			mark = trademarkRepository.findById(process.getTradeMarkId()).get();

			if (mark == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		try {

			TrademarkRegistrationProcess _process = processRepository.findByTradeMarkId(mark.getId());

			if (_process != null) {

				if (!_process.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trademark's process is already running....");

		} catch (Exception e) {

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("advocateId", process.getAdvocateId());
		update.set("userId", process.getUserId());
		update.set("steps", process.getSteps());
		update.set("trademarkId", process.getTradeMarkId());
		update.set("status", process.isStatus());

		mongoTempalte.updateFirst(query, update, TrademarkRegistrationProcess.class);

		process = mongoTempalte.findOne(query, TrademarkRegistrationProcess.class);

		return process;
	}

	@Override
	public TrademarkRegistrationProcess findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			TrademarkRegistrationProcess list = processRepository.findById(id).get();

			if (list == null) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	public List<TrademarkRegistrationProcess> findAll() {

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	public List<TrademarkRegistrationProcess> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findByUserId(userId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	public List<TrademarkRegistrationProcess> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findByAdvocateId(advocateId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	public TrademarkRegistrationProcess findByTradeMarkId(String tradeMarkId) {

		if (tradeMarkId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			TrademarkRegistrationProcess list = processRepository.findByTradeMarkId(tradeMarkId);

			if (list == null) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	public List<TrademarkRegistrationProcess> findByStatus(boolean status) {

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findByStatus(status);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}
	}

	@Override
	public List<TrademarkRegistrationProcess> findByStepsContainingIgnoreCase(String steps) {

		if (steps == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findByStepsContainingIgnoreCase(steps);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	public List<TrademarkRegistrationProcess> findByTrademarkIdIn(List<String> trademarksId) {

		if (trademarksId == null || trademarksId.isEmpty()) {

			throw new NullPointerException("False request....");

		}

		try {

			List<TrademarkRegistrationProcess> list = processRepository.findByTrademarkIdIn(trademarksId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NullPointerException("No such process find at here...");

		}

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Trademark", allEntries = true),
			@CacheEvict(value = "TrademarkRegistrationProcess", allEntries = true),
			@CacheEvict(value = "TrademarkPayment", allEntries = true)
			
	})
	public boolean deleteTrademarkRegistrationProcess(String id, String userId) {

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

		CenterAdmin admin = null;

		try {

			admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such center admin find at here...");

		}

		TrademarkRegistrationProcess registrationProcess = null;

		try {

			registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (registrationProcess.getUserId().equals(user.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such trademark find at here...");

		}

		long count = processRepository.count();

		cleaner.removeTrademarkRegistrationProcess(id);

		return count != processRepository.count();
	}

}
