package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.CaseModels.AppealCase;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseJudgment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseAppealRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseJudgementRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CaseAppealServiceImpl implements CaseAppealService {

	@Autowired
	private CaseAppealRepository caseAppealRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CaseJudgementRepository caseJudgmentRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public AppealCase addAppealCase(AppealCase caseAppeal, String userId) {

		if (caseAppeal == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new NoSuchElementException("No such case find at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		if (caseAppeal.getReason().isEmpty()) {

			throw new ArithmeticException("Case appeal must have a reason....");

		}

		try {

			AppealCase appealCase = caseAppealRepository.findByCaseId(caseAppeal.getCaseId());

			if (appealCase != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already appealed....");

		} catch (Exception e) {

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			CaseJudgment judgement = caseJudgmentRepository.findByCaseId(acceptedCase.getId());

			if (judgement == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Case judgment have to be published due to appeal it.....");

		}

		caseAppeal = caseAppealRepository.save(caseAppeal);

		if (caseAppeal == null) {

			throw new ArithmeticException("Case appeal is not added at here...");

		}

		return caseAppeal;
	}

	@Override
	public AppealCase updateAppealCase(AppealCase caseAppeal, String userId, String appealCaseId) {

		if (caseAppeal == null || userId == null || appealCaseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealCase appeal = caseAppealRepository.findById(appealCaseId).get();

			if (appeal == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			AppealCase appeal = caseAppealRepository.findByCaseId(acceptedCase.getId());

			if (appeal == null) {

				throw new Exception();

			}

			if (!appeal.getId().equals(appealCaseId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new NoSuchElementException("No such case find at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		if (caseAppeal.getReason().isEmpty()) {

			throw new ArithmeticException("Case appeal must have a reason....");

		}

		try {

			AppealCase appealCase = caseAppealRepository.findByCaseId(caseAppeal.getCaseId());

			if (appealCase == null || !appealCase.getId().equals(appealCaseId)) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already appealed....");

		} catch (Exception e) {

		}

		try {

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			CaseJudgment judgement = caseJudgmentRepository.findByCaseId(acceptedCase.getId());

			if (judgement == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Case judgment have to be published due to appeal it.....");

		}

		caseAppeal.setId(appealCaseId);

		caseAppeal = caseAppealRepository.save(caseAppeal);

		if (caseAppeal == null) {

			throw new ArithmeticException("Case appeal is not added at here...");

		}

		return caseAppeal;
	}

	@Override
	public AppealCase findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealCase appeal = caseAppealRepository.findByCaseId(caseId);

			if (appeal == null) {

				throw new Exception();

			}

			return appeal;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case appeal find at here....");

		}

	}

	@Override
	public List<AppealCase> findByReasonContaingingIgnoreCase(String reason) {

		if (reason == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealCase> list = caseAppealRepository.findByReasonContainingIgnoreCase(reason.trim());

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealCase> findByAppealDateBefore(Instant appealDate) {

		if (appealDate == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealCase> list = caseAppealRepository.findByAppealDateBefore(appealDate);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealCase> findByAppealDateAfter(Instant appealDate) {

		if (appealDate == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealCase> list = caseAppealRepository.findByAppealDateAfter(appealDate);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealCase> seeAll() {

		List<AppealCase> list = caseAppealRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		return list;
	}

	@Override
	public AppealCase findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealCase appeal = caseAppealRepository.findById(id).get();

			if (appeal == null) {

				throw new Exception();

			}

			return appeal;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case appeal find at here....");

		}

	}

	@Override
	public boolean removeAppealCase(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealCase appeal = caseAppealRepository.findById(id).get();

			if (appeal == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case appeal find at here...");

		}

		try {

			AppealCase caseAppeal = caseAppealRepository.findById(id).get();

			if (caseAppeal == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseAppeal.getCaseId()).get();

			if (acceptedCase == null) {

				throw new ArithmeticException();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = caseAppealRepository.count();

					cleaner.removeCaseAppeal(id);

					return count != caseAppealRepository.count();

				}

			} catch (Exception e) {

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new NoSuchElementException("No such case find at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		long count = caseAppealRepository.count();

		cleaner.removeCaseAppeal(id);

		return count != caseAppealRepository.count();
	}

}
