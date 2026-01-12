package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.CaseModels.AppealHearings;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;

import com.example.demo700.Repositories.CaseRepositories.AppealHearingRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.HearingRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AppealHearingServiceImpl implements AppealHearingService {

	@Autowired
	private AppealHearingRepository appealHearingRepository;

	@Autowired
	private HearingRepository hearingRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public AppealHearings addAppealHearings(AppealHearings appealHearings, String userId) {

		if (appealHearings == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealHearings appeal = appealHearingRepository.findByHearingId(appealHearings.getHearingId());

			if (appeal != null) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Already applied for this hearing...");

		} catch (Exception e) {

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

			Hearing hearings = hearingRepository.findById(appealHearings.getHearingId()).get();

			if (hearings == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(hearings.getCaseId()).get();

			if (!acceptedCase.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such Hearing find at here...");

		}

		if (appealHearings.getReason().isEmpty() || appealHearings.getReason().isBlank()) {

			throw new ArithmeticException("For appeal the hearings there must have a valid reason....");

		}

		appealHearings = appealHearingRepository.save(appealHearings);

		if (appealHearings == null) {

			throw new ArithmeticException("No such appeal added at here....");

		}

		return appealHearings;
	}

	@Override
	public AppealHearings updateAppealHearings(AppealHearings appealHearings, String userId, String appealHearingsId) {

		if (appealHearings == null || userId == null || appealHearingsId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealHearings appeal = appealHearingRepository.findByHearingId(appealHearings.getHearingId());

			if (appeal != null) {

				if (!appeal.getId().equals(appealHearingsId)) {

					throw new Exception();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Already applied for this hearing...");

		} catch (Exception e) {

		}

		try {

			AppealHearings appeal = appealHearingRepository.findById(appealHearingsId).get();

			if (appeal == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such appeal find at here...");

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

			Hearing hearings = hearingRepository.findById(appealHearings.getHearingId()).get();

			if (hearings == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(hearings.getCaseId()).get();

			if (!acceptedCase.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such Hearing find at here...");

		}

		if (appealHearings.getReason().isEmpty() || appealHearings.getReason().isBlank()) {

			throw new ArithmeticException("For appeal the hearings there must have a valid reason....");

		}

		appealHearings.setId(appealHearingsId);

		appealHearings = appealHearingRepository.save(appealHearings);

		if (appealHearings == null) {

			throw new ArithmeticException("No such appeal added at here....");

		}

		return appealHearings;
	}

	@Override
	public AppealHearings findByHearingId(String hearingId) {

		if (hearingId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealHearings appeal = appealHearingRepository.findByHearingId(hearingId);

			if (appeal == null) {

				throw new Exception();

			}

			return appeal;

		} catch (Exception e) {

			throw new NoSuchElementException("No such appeal hearing find at here....");

		}

	}

	@Override
	public List<AppealHearings> findByReasonContainingIgnoreCase(String reason) {

		if (reason == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealHearings> list = appealHearingRepository.findByReasonContainingIgnoreCase(reason);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealHearings> findByAppealHearingTimeBefore(Instant appealHearingTime) {

		if (appealHearingTime == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealHearings> list = appealHearingRepository.findByAppealHearingTimeBefore(appealHearingTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealHearings> findByAppealHearingTimeAfter(Instant appealHearingTime) {

		if (appealHearingTime == null) {

			throw new NullPointerException("False request....");

		}

		List<AppealHearings> list = appealHearingRepository.findByAppealHearingTimeAfter(appealHearingTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

		return list;
	}

	@Override
	public List<AppealHearings> seeAll() {

		List<AppealHearings> list = appealHearingRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

		return list;
	}

	@Override
	public AppealHearings findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			return appealHearingRepository.findById(id).get();

		} catch (Exception e) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

	}

	@Override
	public boolean removeAppealHearings(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AppealHearings appeal = appealHearingRepository.findById(id).get();

			if (appeal == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such appeal find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = appealHearingRepository.count();

					cleaner.removeAppealHearing(id);

					return count != appealHearingRepository.count();

				}

			} catch (Exception e) {

			}

			AppealHearings appeal = appealHearingRepository.findById(id).get();

			if (appeal == null) {

				throw new Exception();

			}

			Hearing hearings = hearingRepository.findById(appeal.getHearingId()).get();

			if (hearings == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(hearings.getCaseId()).get();

			if (!acceptedCase.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		long count = appealHearingRepository.count();

		cleaner.removeAppealHearing(id);

		return count != appealHearingRepository.count();
	}

}
