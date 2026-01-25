package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.bson.types.ObjectId;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.HearingRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class HearingServiceImpl implements HearingService {

	@Autowired
	private HearingRepository hearingRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private ImageService attachmentService;

	@Autowired
	private Cleaner cleaner;

	@Override
	public Hearing addHearing(Hearing hearing, String userId, MultipartFile files[]) {

		if (hearing == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Case acceptedCase = caseRepository.findById(hearing.getCaseId()).get();

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

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(hearing.getCaseId()).get();

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

			throw new NoSuchElementException("Only advocate can give the hearing update....");

		}

		try {

			if (hearing.getHearningNumber() <= 0) {

				throw new Exception();

			}

			List<Hearing> list = hearingRepository.findByCaseId(hearing.getCaseId());

			int num = hearing.getHearningNumber();

			if (list.size() != num - 1) {

				throw new Exception();

			}

			Set<Integer> set = new HashSet<>();

			for (Hearing i : list) {

				set.add(i.getHearningNumber());

			}

			if (set.size() != num - 1) {

				throw new Exception();

			}

			for (int i : set) {

				if (i >= num) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such valid hearing number exist at here...");

		}

		try {

			if (true) {

				List<String> list = new ArrayList<>();

				int index = 0;

				for (MultipartFile file : files) {

					try {

						String id = attachmentService.upload(file);

						if (id != null) {

							list.add(id);

						}

					} catch (Exception e) {

					}

				}

				String s[] = new String[list.size()];

				index = 0;

				for (String i : list) {

					s[index++] = i;

				}

				hearing.setAttachmentsId(s);

			}

		} catch (Exception e) {

		}

		hearing = hearingRepository.save(hearing);

		if (hearing == null) {

			throw new ArithmeticException("No hearing add at here....");

		}

		return hearing;
	}

	@Override
	public Hearing updateHearing(Hearing hearing, String userId, String hearingId, MultipartFile files[]) {

		if (hearing == null || userId == null || hearingId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Hearing hearing1 = hearingRepository.findById(hearingId).get();

			if (hearing1 == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such hearing find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(hearing.getCaseId()).get();

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

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here...");

		}

		try {

			Case acceptedCase = caseRepository.findById(hearing.getCaseId()).get();

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

			throw new NoSuchElementException("Only advocate can give the hearing update....");

		}

		try {

			if (hearing.getHearningNumber() <= 0) {

				throw new Exception();

			}

			List<Hearing> list = hearingRepository.findByCaseId(hearing.getCaseId());

			Set<Integer> set = new HashSet<>();

			for (Hearing i : list) {

				set.add(i.getHearningNumber());

			}

			if (!set.contains(hearing.getHearningNumber())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such valid hearing number exist at here...");

		}

		try {

			List<String> list = new ArrayList<>();

			for (String i : hearing.getAttachmentsId()) {

				try {
					if (attachmentService.attachmentExists(i)) {
						list.add(i);
					}

				} catch (Exception e) {

				}

			}

			Hearing oldHearing = hearingRepository.findById(hearingId).get();

			for (String i : oldHearing.getAttachmentsId()) {

				try {

					if (!list.contains(i)) {

						attachmentService.delete(i);

					}

				} catch (Exception e) {

				}

			}

			if (files != null) {

				int index = 0;

				for (MultipartFile file : files) {

					try {

						String id = attachmentService.upload(file);

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

			hearing.setAttachmentsId(s);

		} catch (Exception e) {

		}

		hearing.setId(hearingId);

		hearing = hearingRepository.save(hearing);

		if (hearing == null) {

			throw new ArithmeticException("No hearing add at here....");

		}

		return hearing;
	}

	@Override
	public List<Hearing> findByHearningNumber(int hearningNumber) {

		List<Hearing> list = hearingRepository.findByHearningNumber(hearningNumber);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

	@Override
	public List<Hearing> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		List<Hearing> list = hearingRepository.findByCaseId(caseId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

	@Override
	public List<Hearing> findByIssuedDateAfter(Instant date) {

		if (date == null) {

			throw new NullPointerException("False request....");

		}

		List<Hearing> list = hearingRepository.findByIssuedDateAfter(date);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

	@Override
	public List<Hearing> findByIssuedDateBefore(Instant date) {

		if (date == null) {

			throw new NullPointerException("False request....");

		}

		List<Hearing> list = hearingRepository.findByIssuedDateBefore(date);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

	@Override
	public List<Hearing> seeAll() {

		List<Hearing> list = hearingRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

	@Override
	public Hearing findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Hearing hearing = hearingRepository.findById(id).get();

			if (hearing == null) {

				throw new Exception();

			}

			return hearing;

		} catch (Exception e) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

	}

	@Override
	public boolean removeHearing(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Hearing hearing1 = hearingRepository.findById(id).get();

			if (hearing1 == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such hearing find at here...");

		}

		long count = hearingRepository.count();

		cleaner.removeHearing(id);

		return count != hearingRepository.count();
	}

	@Override
	public Hearing findByCaseIdAndHearingNumber(String caseId, int hearingNumber) {

		if (caseId == null || hearingNumber <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			Hearing hearing = hearingRepository.findByCaseIdAndHearningNumber(caseId, hearingNumber);

			if (hearing == null) {

				throw new Exception();

			}

			return hearing;

		} catch (Exception e) {

			throw new NoSuchElementException("No such hearing find at here...");

		}

	}

	@Override
	public List<Hearing> findByCaseIdAndHearingNumberLessThanEqual(String caseId, int hearingNumber) {

		if (caseId == null || hearingNumber <= 0) {

			throw new NullPointerException("False request.....");

		}

		List<Hearing> list = hearingRepository.findByCaseIdAndHearningNumberLessThanEqual(caseId, hearingNumber);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such hearing find at here....");

		}

		return list;
	}

}
