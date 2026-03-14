package com.example.demo700.Services.CaseServices;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseTracking;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseTrackingRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CaseTrackingServiceImpl implements CaseTrackingService {

	@Autowired
	private CaseTrackingRepository caseTrackingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public CaseTracking addCaseTracking(CaseTracking caseTracking, String userId) {

		if (caseTracking == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseTracking.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseTracking.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to add this case tracing or case stage...");

		}

		try {

			CaseTracking tracking = caseTrackingRepository.findByCaseIdAndCaseStage(caseTracking.getCaseId(),
					caseTracking.getCaseStage());

			if (tracking != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Duplicate stage add...");

		} catch (Exception e) {

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseId(caseTracking.getCaseId());

			caseTracking.setStageNumber(list.size() + 1);

		} catch (Exception e) {

		}

		caseTracking = caseTrackingRepository.save(caseTracking);

		if (caseTracking == null) {

			throw new ArithmeticException("Case tracking is not added at here....");

		}

		return caseTracking;

	}

	@Override
	public CaseTracking updateCaseTracking(CaseTracking caseTracking, String userId, String id) {

		if (caseTracking == null || userId == null || id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseTracking tracking = caseTrackingRepository.findById(id).get();

			if (tracking == null) {

				throw new Exception();

			}

			caseTracking.setStageNumber(tracking.getStageNumber());

		} catch (Exception e) {

			throw new NoSuchElementException("No such case tracking find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseTracking.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseTracking.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to add this case tracing or case stage...");

		}

		try {

			CaseTracking tracking = caseTrackingRepository.findByCaseIdAndCaseStage(caseTracking.getCaseId(),
					caseTracking.getCaseStage());

			if (tracking != null) {

				if (!tracking.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Duplicate stage add...");

		} catch (Exception e) {

		}

		caseTracking.setId(id);

		caseTracking = caseTrackingRepository.save(caseTracking);

		if (caseTracking == null) {

			throw new ArithmeticException("Case tracking is not added at here....");

		}

		return caseTracking;
	}

	@Override
	public List<CaseTracking> swapOrder(String caseTrackingId1, String caseTrackingId2) {

		if (caseTrackingId1 == null || caseTrackingId2 == null) {

			throw new NullPointerException("False request....");

		}

		CaseTracking caseTracking1, caseTracking2;

		try {

			caseTracking1 = caseTrackingRepository.findById(caseTrackingId1).get();

			if (caseTracking1 == null) {

				throw new Exception();

			}

			caseTracking2 = caseTrackingRepository.findById(caseTrackingId2).get();

			if (caseTracking2 == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Your given case tracking id is not valid...");

		}

		try {

			int firstOrder = caseTracking1.getStageNumber();
			int secondOrder = caseTracking2.getStageNumber();

			caseTracking1.setStageNumber(secondOrder);
			caseTracking2.setStageNumber(firstOrder);

			caseTracking1 = caseTrackingRepository.save(caseTracking1);

			if (caseTracking1 == null) {

				throw new Exception();

			}

			caseTracking2 = caseTrackingRepository.save(caseTracking2);

			if (caseTracking2 == null) {

				throw new Exception();

			}

			List<CaseTracking> list = new ArrayList<>();

			list.add(caseTracking1);
			list.add(caseTracking2);

			return list;

		} catch (Exception e) {

			throw new ArithmeticException("Case tracking is not swaped...");

		}

	}

	@Override
	public List<CaseTracking> findAll() {

		try {

			List<CaseTracking> list = caseTrackingRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	public List<CaseTracking> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseId(caseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	public List<CaseTracking> findByCaseStage(CasePayment caseStage) {

		if (caseStage == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseStage(caseStage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	public CaseTracking findByCaseIdAndStageNumber(String caseId, int stageNumber) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseTracking caseTracking = caseTrackingRepository.findByCaseIdAndStageNumber(caseId, stageNumber);

			if (caseTracking == null) {

				throw new Exception();

			}

			return caseTracking;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	public CaseTracking findByCaseIdAndCaseStage(String caseId, CasePayment caseStage) {

		if (caseId == null || caseStage == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseTracking caseTracking = caseTrackingRepository.findByCaseIdAndCaseStage(caseId, caseStage);

			if (caseTracking == null) {

				throw new Exception();

			}

			return caseTracking;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	public List<CaseTracking> findByCaseIdAndStageNumberGreaterThan(String caseId, int stageNumber) {

		if (caseId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseIdAndStageNumberGreaterThan(caseId, stageNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case tracking stage find at here...");

		}

	}

	@Override
	public boolean removeCaseTracking(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseTracking tracking = caseTrackingRepository.findById(id).get();

			if (tracking == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case tracking find at here...");

		}

		try {

			CaseTracking caseTracking = caseTrackingRepository.findById(id).get();

			if (caseTracking == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseTracking.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = caseTrackingRepository.count();

					cleaner.removeCaseTracking(id);

					return caseTrackingRepository.count() != count;

				}

			} catch (Exception e) {

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to add this case tracing or case stage...");

		}

		long count = caseTrackingRepository.count();

		cleaner.removeCaseTracking(id);

		return caseTrackingRepository.count() != count;

	}

}
