package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
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
	@CacheEvict(value = cacheValue, allEntries = true)
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
	@CacheEvict(value = cacheValue, allEntries = true)
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
	@CacheEvict(value = cacheValue, allEntries = true)
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
	public RegistrationProcess findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			RegistrationProcess process = processRepository.findById(id).get();

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<RegistrationProcess> findAll() {

		try {

			List<RegistrationProcess> process = processRepository.findAll();

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<RegistrationProcess> findByCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByCompanyId(companyId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<RegistrationProcess> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByAdvocateId(advocateId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<RegistrationProcess> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<RegistrationProcess> process = processRepository.findByUserId(userId);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByStatus_' + #status")
	public List<RegistrationProcess> findByStatus(boolean status) {

		try {

			List<RegistrationProcess> process = processRepository.findByStatus(status);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareValuePerShareLte_' + #shareValuePerShare")
	public List<RegistrationProcess> findByShareValuePerShareLte(double shareValuePerShare) {

		try {

			List<RegistrationProcess> process = processRepository.findByShareValuePerShareLte(shareValuePerShare);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareValuePerShareGte_' + #shareValuePerShare")
	public List<RegistrationProcess> findByShareValuePerShareGte(double shareValuePerShare) {

		try {

			List<RegistrationProcess> process = processRepository.findByShareValuePerShareGte(shareValuePerShare);

			if (process == null) {

				throw new Exception();

			}

			return process;

		} catch (Exception e) {

			throw new NoSuchElementException("No such process find at here...");

		}

	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
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

}
