package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.ReadStatus;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.ReadStatusRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class ReadStatusServiceImpl implements ReadStatusService {

	@Autowired
	private ReadStatusRepository readStatusRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public ReadStatus addReadStatus(ReadStatus readStatus, String userId) {

		if (readStatus == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findById(readStatus.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(readStatus.getAdvocateId())) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(readStatus.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("User is not fit to add status....");

		}

		try {

			if (readStatus.getStatus().isBlank() || readStatus.getStatus().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NullPointerException("There is no status at here...");

		}

		readStatus = readStatusRepository.save(readStatus);

		if (readStatus == null) {

			throw new ArithmeticException("Status is not stored at here....");

		}

		return readStatus;

	}

	@Override
	public ReadStatus updateReadStatus(ReadStatus readStatus, String userId, String id) {

		if (readStatus == null || userId == null || id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ReadStatus status = readStatusRepository.findById(id).get();

			if (status == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No Such existing case status find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(readStatus.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(readStatus.getAdvocateId())) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(readStatus.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

			ReadStatus status = readStatusRepository.findById(id).get();

			if (status == null) {

				throw new Exception();

			}

			if (!status.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("User is not fit to add status....");

		}

		try {

			if (readStatus.getStatus().isBlank() || readStatus.getStatus().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NullPointerException("There is no status at here...");

		}

		readStatus.setId(id);

		readStatus = readStatusRepository.save(readStatus);

		if (readStatus == null) {

			throw new ArithmeticException("Status is not stored at here....");

		}

		return readStatus;

	}

	@Override
	public List<ReadStatus> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByCaseId(caseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public List<ReadStatus> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByAdvocateId(advocateId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public List<ReadStatus> findByStatusContainingIgnoreCase(String status) {

		if (status == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByStatusContainingIgnoreCase(status);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public List<ReadStatus> findByIssuedTimeBefore(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByIssuedTimeBefore(issuedTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public List<ReadStatus> findByIssuedTimeAfter(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByIssuedTimeAfter(issuedTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public List<ReadStatus> findByIssuedTime(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("Fasle reqeust....");

		}

		try {

			List<ReadStatus> list = readStatusRepository.findByIssuedTime(issuedTime);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case status find at here....");

		}

	}

	@Override
	public boolean deleteReadStatus(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ReadStatus status = readStatusRepository.findById(id).get();

			if (status == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No Such existing case status find at here....");

		}

		try {

			ReadStatus status = readStatusRepository.findById(id).get();

			if (status == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = readStatusRepository.count();

					cleaner.removeReadStatus(id);

					return count != readStatusRepository.count();

				}

			} catch (Exception e) {

			}

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(status.getAdvocateId())) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(status.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

			if (!status.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("User is not fit to add status....");

		}

		long count = readStatusRepository.count();

		cleaner.removeReadStatus(id);

		return count != readStatusRepository.count();

	}

	@Override
	public List<ReadStatus> findAll() {

		try {

			List<ReadStatus> list = readStatusRepository.findAll();

			if (!list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such read status submitted yet...");

		}

	}

}
