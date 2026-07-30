package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseJudgment;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseJudgementRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class CaseJudgmentServiceImpl implements CaseJudgmentService {

	@Autowired
	private CaseJudgementRepository caseJudgmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;
	
	private static final String cacheValue = "CaseJudgement";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CaseJudgment addCaseJudgment(CaseJudgment caseJudgment, String userId, MultipartFile file) {

		if (caseJudgment == null || userId == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseJudgment.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		try {

			CaseJudgment judgment = caseJudgmentRepository.findByCaseId(caseJudgment.getCaseId());

			if (judgment != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This case judgment is already published....");

		} catch (Exception e) {

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseJudgment.getCaseId()).get();

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

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			if (caseJudgment.getResult().trim().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such case judgment will happend without result....");

		}

		try {

			if (file != null) {

				String id = imageService.upload(file);

				if (id != null) {

					caseJudgment.setJudgmentAttachmentId(id);

				}

			}

		} catch (Exception e) {

		}

		caseJudgment = caseJudgmentRepository.save(caseJudgment);

		if (caseJudgment == null) {

			throw new ArithmeticException("No such judgment added at here...");

		}

		return caseJudgment;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CaseJudgment updateCaseJudgment(CaseJudgment caseJudgment, String userId, String caseJudgmentId,
			MultipartFile file) {

		if (caseJudgment == null || userId == null || caseJudgmentId == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			CaseJudgment judgment = caseJudgmentRepository.findById(caseJudgmentId).get();

			if (judgment == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(judgment.getCaseId()).get();

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

			throw new NoSuchElementException("No such case judgment find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(caseJudgment.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		try {

			CaseJudgment judgment = caseJudgmentRepository.findByCaseId(caseJudgment.getCaseId());

			if (judgment != null) {

				if (!judgment.getId().equals(caseJudgmentId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This case judgment is already published....");

		} catch (Exception e) {

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(caseJudgment.getCaseId()).get();

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

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			if (caseJudgment.getResult().trim().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such case judgment will happend without result....");

		}

		try {

			if (file != null) {

				if (caseJudgment.getJudgmentAttachmentId() != null) {

					String id = imageService.update(caseJudgment.getJudgmentAttachmentId(), file);

					if (id != null) {

						caseJudgment.setJudgmentAttachmentId(id);

					}

				} else {

					String id = imageService.upload(file);

					if (id != null) {

						caseJudgment.setJudgmentAttachmentId(id);

					}

				}

			}

		} catch (Exception e) {

		}

		caseJudgment.setId(caseJudgmentId);

		caseJudgment = caseJudgmentRepository.save(caseJudgment);

		if (caseJudgment == null) {

			throw new ArithmeticException("No such judgment added at here...");

		}

		return caseJudgment;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseId_' + #caseId")
	public CaseJudgment findByCaseId(String caseId) {

		try {

			CaseJudgment caseJudgment = caseJudgmentRepository.findByCaseId(caseId);

			if (caseJudgment == null) {

				throw new Exception();

			}

			return caseJudgment;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case judgment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByResult_' + #result")
	public List<CaseJudgment> findByResultContainingIgnoreCase(String result) {

		if (result == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseJudgment> list = caseJudgmentRepository.findByResultContainingIgnoreCase(result);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case judgment find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateAfter_' + #date")
	public List<CaseJudgment> findByDateAfter(Instant date) {

		if (date == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CaseJudgment> list = caseJudgmentRepository.findByDateAfter(date);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case judgment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateBefore_' + #date")
	public List<CaseJudgment> findByDateBefore(Instant date) {

		if (date == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CaseJudgment> list = caseJudgmentRepository.findByDateBefore(date);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CaseJudgment> findAll() {

		try {

			List<CaseJudgment> list = caseJudgmentRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case judgment find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CaseJudgment findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseJudgment caseJudgment = caseJudgmentRepository.findById(id).get();

			if (caseJudgment == null) {

				throw new Exception();

			}

			return caseJudgment;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case judgment find at here...");

		}

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "AdvocateRating", allEntries = true),
			@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true),
			@CacheEvict(value = "CaseClose", allEntries = true),
			//@CacheEvict(value = "CaseJudgement", allEntries = true),
			@CacheEvict(value = "Case", allEntries = true),
			@CacheEvict(value = "CaseTracking", allEntries = true),
			@CacheEvict(value = "DocumentDraft", allEntries = true),
			@CacheEvict(value = "Hearing", allEntries = true),
			@CacheEvict(value = "ReadStatus", allEntries = true),
			@CacheEvict(value = "PaymentDetails", allEntries = true),
			@CacheEvict(value = "UserActiveLocation", allEntries = true)
			})
	public boolean removeCaseJudgment(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseJudgment judgment = caseJudgmentRepository.findById(id).get();

			if (judgment == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(judgment.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = caseJudgmentRepository.count();

					cleaner.removeCaseJudgment(id);

					return count != caseJudgmentRepository.count();

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

			throw new NoSuchElementException("No such case judgment find at here...");

		}

		long count = caseJudgmentRepository.count();

		cleaner.removeCaseJudgment(id);

		return count != caseJudgmentRepository.count();
	}

}
