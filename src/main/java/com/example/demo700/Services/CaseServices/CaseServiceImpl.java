package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.bson.types.ObjectId;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdvocateServices.CVUploadService;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private ImageService fileUpload;

	@Override
	public Case updateCase(Case acceptedCase, String userId, String caseRequestId, MultipartFile[] files) {

		if (caseRequestId == null || userId == null || acceptedCase == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Case case1 = caseRepository.findById(caseRequestId).get();

			if (case1 == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			User user = userRepository.findById(acceptedCase.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vaid user find at here....");

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

			if (!advocate.getId().equals(acceptedCase.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid advocate find at here....");

		}

		try {

			List<String> list = new ArrayList<>();

			for (String i : acceptedCase.getAttachmentsId()) {
				if (fileUpload.attachmentExists(i)) {
				    list.add(i);
				}

			}

			Case oldCase = caseRepository.findById(caseRequestId).get();

			for (String i : oldCase.getAttachmentsId()) {

				try {

					if (!list.contains(i)) {

						fileUpload.delete(i);

					}

				} catch (Exception e) {

				}

			}

			if (files != null) {

				int index = 0;

				for (MultipartFile file : files) {

					try {

						String id = fileUpload.upload(file);

						if (id != null) {

							list.add(id);

						}

					} catch (Exception e) {

					}

					++index;

				}

			}

			String s[] = new String[list.size()];

			int index = 0;

			for (String i : list) {

				s[index++] = i;

			}

			acceptedCase.setAttachmentId(s);

		} catch (Exception e) {

		}

		acceptedCase.setId(caseRequestId);

		acceptedCase = caseRepository.save(acceptedCase);

		return acceptedCase;
	}

	@Override
	public Case findByCaseNameIgnoreCase(String caseName) {

		if (caseName == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findByCaseNameIgnoreCase(caseName);

			if (acceptedCase == null) {

				throw new Exception();

			}

			return acceptedCase;

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

	}

	@Override
	public List<Case> findByCaseNameContainingIgnoreCase(String caseName) {

		if (caseName == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByCaseNameContainingIgnoreCase(caseName);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public List<Case> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;

	}

	@Override
	public List<Case> findByCaseType(AdvocateSpeciality caseType) {

		if (caseType == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByCaseType(caseType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public List<Case> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public List<Case> findByIssuedTimeAfter(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByIssuedTimeAfter(issuedTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public List<Case> findByIssuedTimeBefore(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("False request....");

		}

		List<Case> list = caseRepository.findByIssuedTimeBefore(issuedTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public List<Case> findAllCase() {

		List<Case> list = caseRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here...");

		}

		return list;
	}

	@Override
	public Case findById(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			return caseRepository.findById(caseId).get();

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

	}

	@Override
	public boolean removeCase(String caseId, String userId) {

		if (caseId == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case case1 = caseRepository.findById(caseId).get();

			if (case1 == null) {

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

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin == null) {

					throw new Exception();

				}

				long count = caseRepository.count();

				cleaner.removeCase(caseId);

				return count != caseRepository.count();

			} catch (Exception e) {

			}

			Case case1 = caseRepository.findById(caseId).get();

			if (case1 == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				if (!case1.getUserId().equals(userId)) {

					throw new Exception();

				}

			}

			if (!case1.getAdvocateId().equals(advocate.getId()) && !case1.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such valid user find at here...");

		}

		long count = caseRepository.count();

		cleaner.removeCase(caseId);

		return count != caseRepository.count();
	}

}
