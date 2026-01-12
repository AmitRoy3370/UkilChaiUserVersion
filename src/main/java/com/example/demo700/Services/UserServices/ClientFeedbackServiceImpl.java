package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
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
