package com.example.demo700.Services.VatServices;

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
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.VatModels.Vat;
import com.example.demo700.Model.VatModels.VatRegistrationProcess;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Repositories.VatRepositories.VatRegistrationProcessRepository;
import com.example.demo700.Repositories.VatRepositories.VatRepository;

@Service
public class VatRegistrationProcessServiceImpl implements VatRegistrationProcessService {

	@Autowired
	private VatRegistrationProcessRepository processRepository;

	@Autowired
	private VatRepository vatRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository adminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public VatRegistrationProcess addProcess(VatRegistrationProcess process, String userId) {

		if (process == null || userId == null || !process.getUserId().equals(userId)) {

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

		CenterAdmin centerAdmin = null;

		try {

			centerAdmin = adminRepository.findByUserId(userId);

			if (centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only centeradmin can add process...");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(process.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here....");

		}

		Vat vat = null;

		try {

			vat = vatRepository.findById(process.getVatId()).get();

			if (vat == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		try {

			VatRegistrationProcess registrationProcess = processRepository.findByVatId(process.getVatId());

			if (registrationProcess != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This vat process is already running...");

		} catch (Exception e) {

		}

		process = processRepository.save(process);

		return process;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public VatRegistrationProcess updateProcess(VatRegistrationProcess process, String userId, String id) {

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

			isAdvocate = advocate.getUserId().equals(userId);

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here....");

		}

		CenterAdmin centerAdmin = null;

		try {

			centerAdmin = adminRepository.findByUserId(userId);

			if (centerAdmin == null) {

				if (!isAdvocate) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Only centeradmin can add process...");

		}

		Vat vat = null;

		try {

			vat = vatRepository.findById(process.getVatId()).get();

			if (vat == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		try {

			VatRegistrationProcess registrationProcess = processRepository.findByVatId(process.getVatId());

			if (registrationProcess != null) {

				if (registrationProcess.getId().equals(id)) {

				} else {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This vat process is already running...");

		} catch (Exception e) {

		}

		VatRegistrationProcess registrationProcess = null;

		try {

			registrationProcess = processRepository.findById(id).get();

			if (registrationProcess == null) {

				throw new Exception();

			}

			if (!isAdvocate) {

				if (!registrationProcess.getUserId().equals(userId)) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

		try {

			if (isAdvocate) {

				if (!process.getUserId().equals(registrationProcess.getAdvocateId())) {

					throw new ArithmeticException("Advocate can change only steps and status");

				} else if (!process.getAdvocateId().equals(registrationProcess.getAdvocateId())) {

					throw new ArithmeticException("Advocate can change only steps and status");

				} else if (!process.getVatId().equals(registrationProcess.getVatId())) {

					throw new ArithmeticException("Advocate can change only steps and status");

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

		process.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", process.getUserId());
		update.set("advocateId", process.getAdvocateId());
		update.set("steps", process.getSteps());
		update.set("status", process.isStatus());
		update.set("vatId", process.getVatId());

		mongoTemplate.updateFirst(query, update, VatRegistrationProcess.class);

		process = mongoTemplate.findOne(query, VatRegistrationProcess.class);

		return process;
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findById_' + #id")
	public VatRegistrationProcess findById(String id) {
		
		if(id == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			VatRegistrationProcess process = processRepository.findById(id).get();
			
			if(process == null) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
		
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findAll'")
	public List<VatRegistrationProcess> findAll() {

		try {
			
			List<VatRegistrationProcess> process = processRepository.findAll();
			
			if(process == null) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByVatId_' + #vatId")
	public VatRegistrationProcess findByVatId(String vatId) {

		if(vatId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			VatRegistrationProcess process = processRepository.findByVatId(vatId);
			
			if(process == null) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByVatsId_' + #vatsId")
	public List<VatRegistrationProcess> findByVatIdIn(List<String> vatsId) {

		if(vatsId == null || vatsId.isEmpty()) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<VatRegistrationProcess> process = processRepository.findByVatIdIn(vatsId);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByUserId_' + #userId")
	public List<VatRegistrationProcess> findByUserId(String userId) {

		if(userId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<VatRegistrationProcess> process = processRepository.findByUserId(userId);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByAdvocateId_' + #advocateId")
	public List<VatRegistrationProcess> findByAdvocateId(String advocateId) {

		if(advocateId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<VatRegistrationProcess> process = processRepository.findByAdvocateId(advocateId);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByStatus_' + #status")
	public List<VatRegistrationProcess> findByStatus(boolean status) {

		try {
			
			List<VatRegistrationProcess> process = processRepository.findByStatus(status);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Cacheable(value = "VatRegistrationProcess", key = "'findByUserId_' + #steps")
	public List<VatRegistrationProcess> findByStepsContainingIgnoreCase(String steps) {

		if(steps == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			List<VatRegistrationProcess> process = processRepository.findByStepsContainingIgnoreCase(steps);
			
			if(process == null || process.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return process;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such process find at here...");
			
		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),
			
	})
	public boolean deleteProcess(String id, String userId) {

		if (id == null || userId == null) {

			throw new NoSuchElementException("False request...");

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

		VatRegistrationProcess registrationProcess = null;

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

		cleaner.removeVatRegistrationProcess(id);

		return count != processRepository.count();
	}

}
