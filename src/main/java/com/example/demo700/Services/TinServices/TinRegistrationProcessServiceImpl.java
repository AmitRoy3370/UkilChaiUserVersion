package com.example.demo700.Services.TinServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.TinModels.Tin;
import com.example.demo700.Model.TinModels.TinRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.TinRepositories.TinRegistrationRepository;
import com.example.demo700.Repositories.TinRepositories.TinRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;


@Service
public class TinRegistrationProcessServiceImpl implements TinRegistrationProcessService {

	@Autowired
	private TinRegistrationRepository processRepository;

	@Autowired
	private TinRepository tinRepository;

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

	private static final String cacheValue = "TinRegistrationProcess";
	
	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Tin", allEntries = true)
	})
	public TinRegistrationProcess addTinRegistrationProcess(TinRegistrationProcess process, String userId) {

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

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			if (!admin.getId().equals(process.getCenterAdminId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("NO such center admin find at here....");

		}

		try {

			Advocate advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		Tin tin = null;

		try {

			tin = tinRepository.findById(process.getTinId()).get();

			if (tin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

		try {

			TinRegistrationProcess registrationProcess = processRepository.findByTinId(tin.getId());

			if (registrationProcess != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This tin process is already running...");

		} catch (Exception e) {

		}

		process = processRepository.save(process);

		if (process == null) {

			throw new ArithmeticException("False request...");

		}

		return process;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Tin", allEntries = true)
	})
	public TinRegistrationProcess updateTinRegistrationProcess(TinRegistrationProcess process, String userId,
			String id) {

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

		CenterAdmin admin = null;
		
		try {

			admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			if (!admin.getId().equals(process.getCenterAdminId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("NO such center admin find at here....");

		}

		try {
			
			TinRegistrationProcess registrationProcess = processRepository.findById(id).get();
			
			if(registrationProcess == null) {
				
				throw new Exception();
				
			}
			
			if(!registrationProcess.getId().equals(admin.getId())) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
		
		try {

			Advocate advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		Tin tin = null;

		try {

			tin = tinRepository.findById(process.getTinId()).get();

			if (tin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

		try {

			TinRegistrationProcess registrationProcess = processRepository.findByTinId(tin.getId());

			if (registrationProcess != null) {

				if (!registrationProcess.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This tin process is already running...");

		} catch (Exception e) {

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();
		
		update.set("id", id);
		update.set("centerAdminId", process.getCenterAdminId());
		update.set("advocateId", process.getAdvocateId());
		update.set("tinId", process.getTinId());
		update.set("steps", process.getSteps());
		
		mongoTemplate.updateFirst(query, update, TinRegistrationProcess.class);
		
		process = mongoTemplate.findOne(query, TinRegistrationProcess.class);
		
		return process;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TinRegistrationProcess findById(String id) {
		
		if(id == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			TinRegistrationProcess process = processRepository.findById(id).get();
			
			if(process == null) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TinRegistrationProcess> findAll() {

		try {
			
			List<TinRegistrationProcess> process = processRepository.findAll();
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCenterAdminId_' + #centerAdminId")
	public List<TinRegistrationProcess> findByCenterAdminId(String centerAdminId) {

		if(centerAdminId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<TinRegistrationProcess> process = processRepository.findByCenterAdminId(centerAdminId);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<TinRegistrationProcess> findByAdvocateId(String advocateId) {

		if(advocateId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<TinRegistrationProcess> process = processRepository.findByAdvocateId(advocateId);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTinId_' + #tinId")
	public TinRegistrationProcess findByTinId(String tinId) {

		if(tinId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			TinRegistrationProcess process = processRepository.findByTinId(tinId);
			
			if(process == null ) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStep_' + #steps")
	public List<TinRegistrationProcess> findByStepsContainingIgnoreCase(String steps) {

		if(steps == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<TinRegistrationProcess> process = processRepository.findByStepsContainingIgnoreCase(steps);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here.....");
			
		}
		
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Tin", allEntries = true)
	})
	public boolean removeTinRegistrationProcess(String id, String userId) {
		
		if(id == null || userId == null) {
			
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

		} catch (Exception e) {

			throw new NoSuchElementException("NO such center admin find at here....");

		}

		try {
			
			TinRegistrationProcess registrationProcess = processRepository.findById(id).get();
			
			if(registrationProcess == null) {
				
				throw new Exception();
				
			}
			
			if(!registrationProcess.getId().equals(admin.getId())) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
		
		long count = processRepository.count();
		
		cleaner.removeTinRegistrationProcess(id);
		
		return count != processRepository.count();
	}

}
