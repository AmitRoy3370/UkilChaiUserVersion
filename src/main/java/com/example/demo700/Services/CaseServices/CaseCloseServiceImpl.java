package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseClose;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseCloseRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CaseCloseServiceImpl implements CaseCloseService {

	@Autowired
	private CaseCloseRepository caseCloseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public CaseClose addCaseClose(CaseClose caseClose, String userId) {

		if (caseClose == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseClose.getCaseId()).get();

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

			Case acceptedCase = caseRepository.findById(caseClose.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to close or reopen this case...");

		}

		try {

			CaseClose closedCase = caseCloseRepository.findByCaseId(caseClose.getCaseId());

			if (closedCase != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already added at here...");

		} catch (Exception e) {

		}

		try {

			if (caseClose.isOpen()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("First time you must have to close the case to open this database....");

		}

		caseClose = caseCloseRepository.save(caseClose);

		if (caseClose == null) {

			throw new ArithmeticException("case is not closed....");

		}

		return caseClose;

	}

	@Override
	public CaseClose updateCaseClose(CaseClose caseClose, String userId, String closedCaseId) {

		if (caseClose == null || userId == null || closedCaseId != null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseClose closedCase = caseCloseRepository.findById(closedCaseId).get();

			if (closedCase == null) {

				throw new Exception();

			}

			if (!closedCase.getCaseId().equals(caseClose.getCaseId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed case find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseClose.getCaseId()).get();

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

			Case acceptedCase = caseRepository.findById(caseClose.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to close or reopen this case...");

		}

		try {

			CaseClose closedCase = caseCloseRepository.findByCaseId(caseClose.getCaseId());

			if (closedCase != null) {

				if (!closedCase.getId().equals(closedCaseId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already added at here...");

		} catch (Exception e) {

		}

		caseClose.setId(closedCaseId);

		caseClose = caseCloseRepository.save(caseClose);

		if (caseClose == null) {

			throw new ArithmeticException("case is not closed....");

		}

		return caseClose;
	}

	@Override
	public CaseClose findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseClose closedCase = caseCloseRepository.findById(id).get();

			if (closedCase == null) {

				throw new Exception();

			}

			return closedCase;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}

	}

	@Override
	public List<CaseClose> findAll() {

		try {

			List<CaseClose> list = caseCloseRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}

	}

	@Override
	public CaseClose findByCaseId(String caseId) {
		
		if(caseId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			CaseClose closedCase = caseCloseRepository.findByCaseId(caseId);
			
			if(closedCase == null) {
				
				throw new Exception();
				
			}
			
			return closedCase;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such closed or reopen case find at here...");

			
		}
		
	}

	@Override
	public List<CaseClose> findByClosedDateBefore(Instant closedDate) {

		if (closedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseClose> list = caseCloseRepository.findByClosedDateBefore(closedDate);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}
	}

	@Override
	public List<CaseClose> findByClosedDateAfter(Instant closedDate) {

		if (closedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseClose> list = caseCloseRepository.findByClosedDateAfter(closedDate);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}
	}

	@Override
	public List<CaseClose> findByClosedDate(Instant closedDate) {

		if (closedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseClose> list = caseCloseRepository.findByClosedDate(closedDate);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}
	}

	@Override
	public List<CaseClose> findByOpen(boolean open) {

		try {

			List<CaseClose> list = caseCloseRepository.findByOpen(open);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed or reopen case find at here...");

		}
	}

	@Override
	public boolean deleteClosedCase(String closedCaseId, String userId) {

		if (closedCaseId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseClose closedCase = caseCloseRepository.findById(closedCaseId).get();

			if (closedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such closed case find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = caseCloseRepository.count();

					cleaner.removeCaseClose(closedCaseId);

					return caseCloseRepository.count() != count;

				}

			} catch (Exception e) {

			}

			CaseClose caseClose = caseCloseRepository.findById(closedCaseId).get();

			if (caseClose == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseClose.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to close or reopen this case...");

		}

		long count = caseCloseRepository.count();

		cleaner.removeCaseClose(closedCaseId);

		return caseCloseRepository.count() != count;

	}

}
