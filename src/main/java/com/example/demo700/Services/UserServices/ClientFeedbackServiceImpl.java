package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.UserModels.ClientFeedback;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.UserRepositories.ClientFeedbackRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class ClientFeedbackServiceImpl implements ClientFeedbackService {

	@Autowired
	private ClientFeedbackRepository clientFeedbackRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "ClientFeedback";
	
	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public ClientFeedback addClientFeedback(ClientFeedback clientFeedback, String userId) {

		if (clientFeedback == null || userId == null) {

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

			if (clientFeedback.getFeedback().trim().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Client feedback must have some feedback...");

		}

		try {

			Case acceptedCase = caseRepository.findById(clientFeedback.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		clientFeedback = clientFeedbackRepository.save(clientFeedback);

		if (clientFeedback == null) {

			throw new ArithmeticException("No such feedback added at here...");

		}

		return clientFeedback;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public ClientFeedback updateCientFeedback(ClientFeedback clientFeedback, String userId, String clientFeedbackId) {

		if (clientFeedback == null || userId == null || clientFeedbackId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ClientFeedback feedback = clientFeedbackRepository.findById(clientFeedbackId).get();

			if (feedback == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such client feedback find at here...");

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

			if (clientFeedback.getFeedback().trim().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Client feedback must have some feedback...");

		}

		try {

			Case acceptedCase = caseRepository.findById(clientFeedback.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		clientFeedback.setId(clientFeedbackId);

		clientFeedback = clientFeedbackRepository.save(clientFeedback);

		if (clientFeedback == null) {

			throw new ArithmeticException("No such feedback added at here...");

		}

		return clientFeedback;
	}

	@Override
	@Cacheable(value=cacheValue, key = "'findByCaseId_' + #caseId" )
	public List<ClientFeedback> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<ClientFeedback> list = clientFeedbackRepository.findByCaseId(caseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such feedback find at here....");

		}

	}

	@Override
	@Cacheable(value=cacheValue, key = "'findByUserId_' + #userId" )
	public List<ClientFeedback> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<ClientFeedback> list = clientFeedbackRepository.findByUserId(userId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such feedback find at here...");

		}

	}

	@Override
	@Cacheable(value=cacheValue, key = "'findByFeedback_' + #feedback" )
	public List<ClientFeedback> findByFeedbackContainingIgnoreCase(String feedback) {

		if (feedback == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<ClientFeedback> list = clientFeedbackRepository.findByFeedbackContainingIgnoreCase(feedback);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such feedback find at here...");

		}

	}

	@Override
	@Cacheable(value=cacheValue, key = "'findAll'" )
	public List<ClientFeedback> findAll() {

		try {

			List<ClientFeedback> list = clientFeedbackRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such feedback find at here...");

		}

	}

	@Override
	@Cacheable(value=cacheValue, key = "'findById_' + #id" )
	public ClientFeedback findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ClientFeedback feedback = clientFeedbackRepository.findById(id).get();

			if (feedback == null) {

				throw new Exception();

			}

			return feedback;

		} catch (Exception e) {

			throw new NoSuchElementException("No such feedback find at here...");

		}

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "AdvocateRating", allEntries = true),
			//@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true),
			@CacheEvict(value = "CaseClose", allEntries = true),
			@CacheEvict(value = "CaseJudgement", allEntries = true),
			@CacheEvict(value = "Case", allEntries = true),
			@CacheEvict(value = "CaseTracking", allEntries = true),
			@CacheEvict(value = "DocumentDraft", allEntries = true),
			@CacheEvict(value = "Hearing", allEntries = true),
			@CacheEvict(value = "ReadStatus", allEntries = true),
			@CacheEvict(value = "PaymentDetails", allEntries = true),
			@CacheEvict(value = "UserActiveLocation", allEntries = true)
			})
	public boolean removeClientFeedback(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ClientFeedback feedback = clientFeedbackRepository.findById(id).get();

			if (feedback == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = clientFeedbackRepository.count();

					cleaner.removeClientFeedback(id);

					return count != clientFeedbackRepository.count();

				}

			} catch (Exception e) {

			}

			if (!feedback.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such client feedback find at here...");

		}

		long count = clientFeedbackRepository.count();

		cleaner.removeClientFeedback(id);

		return count != clientFeedbackRepository.count();
	}

}
