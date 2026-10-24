package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.CaseTrackingResponse;
import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseTracking;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseTrackingRepository;
import com.example.demo700.Repositories.NotificationRepository.NotificationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.ChatServices.ChatService;
import com.example.demo700.Services.NotificationServices.NotificationService;
import org.springframework.scheduling.annotation.Async; // 🟢 Import this
import java.time.LocalDate;
import java.time.ZoneId;

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
	private NotificationService notificationService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "CaseTracking";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CaseTracking addCaseTracking(CaseTracking caseTracking, String userId) {

		if (caseTracking == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		String advocateName, userName, advocateUserId, userFullName;

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

			advocateName = user.getFullName() == null ? user.getName() : user.getFullName();
			advocateUserId = user.getId();

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

		if (caseTracking.isVisibility()) {

			Case _case = caseRepository.findById(caseTracking.getCaseId()).get();

			User caseUser = userRepository.findById(_case.getUserId()).get();

			userName = caseUser.getName();
			userFullName = caseUser.getFullName();

			String message = "Hi " + userName + ", your case " + _case.getCaseName() + " case stage "
					+ caseTracking.getCaseStage() + " will be setted at " + caseTracking.getTrackingTime() + " by the "
					+ advocateName;

			List<String> destinations = new ArrayList<>();

			destinations.add("CaseRelatedPages");
			destinations.add("CaseDetailsPage");

			Map<String, String> map = new HashMap<>();

			map.put("userId", _case.getUserId());
			map.put("caseId", _case.getId());
			map.put("caseName", _case.getCaseName());
			map.put("advocateId", _case.getAdvocateId());
			map.put("issuedTime", _case.getIssuedTime().toString());
			map.put("caseLawyer", advocateName);
			map.put("advocateUserId", advocateUserId);
			map.put("userName", userFullName);

			notificationService.sendNotification(caseUser.getId(), message, destinations, map);
			chatService.saveMessage(new ChatMessage(userId, caseUser.getId(), message));

		}

		return caseTracking;

	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CaseTracking updateCaseTracking(CaseTracking caseTracking, String userId, String id) {

		if (caseTracking == null || userId == null || id == null) {

			throw new NullPointerException("False request....");

		}

		String userName, advocateName, advocateUserId, userFullName;

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

			advocateName = user.getName();
			advocateUserId = user.getId();

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

		if (caseTracking.isVisibility()) {

			Case _case = caseRepository.findById(caseTracking.getCaseId()).get();

			User caseUser = userRepository.findById(_case.getUserId()).get();

			userName = caseUser.getName();
			userFullName = caseUser.getFullName();

			String message = "Hi " + userName + ", your case " + _case.getCaseName() + " case stage "
					+ caseTracking.getCaseStage() + " will be setted at " + caseTracking.getTrackingTime() + " by the "
					+ advocateName;

			List<String> destinations = new ArrayList<>();

			destinations.add("CaseRelatedPages");
			destinations.add("CaseDetailsPage");

			Map<String, String> map = new HashMap<>();

			map.put("userId", _case.getUserId());
			map.put("caseId", _case.getId());
			map.put("caseName", _case.getCaseName());
			map.put("advocateId", _case.getAdvocateId());
			map.put("issuedTime", _case.getIssuedTime().toString());
			map.put("caseLawyer", advocateName);
			map.put("advocateUserId", advocateUserId);
			map.put("userName", userFullName);

			notificationService.sendNotification(caseUser.getId(), message, destinations, map);
			chatService.saveMessage(new ChatMessage(userId, caseUser.getId(), message));

		}

		return caseTracking;
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Dhaka")
	public void processScheduledNotifications() {

		try {

			ZoneId zone = ZoneId.of("Asia/Dhaka");

			// "Today" as a Dhaka-local midnight Instant (not UTC)
			Instant today = LocalDate.now(zone).atStartOfDay(zone).toInstant();

			// Fetch all trackings whose time is within the last 3 days (safety range)
			List<CaseTracking> list = caseTrackingRepository.findByTrackingTimeAfter(today.minus(3, ChronoUnit.DAYS));

			if (list == null || list.isEmpty()) {
				return;
			}

			for (CaseTracking tracking : list) {

				try {

					if (tracking == null || tracking.getTrackingTime() == null || !tracking.isVisibility()) {
						continue;
					}

					// Normalize tracking time to Dhaka-local midnight
					Instant trackingDay = tracking.getTrackingTime().atZone(zone).toLocalDate().atStartOfDay(zone)
							.toInstant();

					Instant oneDayBefore = trackingDay.minus(1, ChronoUnit.DAYS);
					Instant twoDaysBefore = trackingDay.minus(2, ChronoUnit.DAYS);

					// Send only when today == tracking day, 1 day before, or 2 days before
					if (today.equals(trackingDay) || today.equals(oneDayBefore) || today.equals(twoDaysBefore)) {
						sendTrackingAlert(tracking);
					}

				} catch (Exception inner) {
					// One bad record must not kill the whole batch
					inner.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendTrackingAlert(CaseTracking tracking) {

		if (tracking == null || !tracking.isVisibility()) {
			return;
		}

		Case _case = caseRepository.findById(tracking.getCaseId()).orElse(null);

		if (_case == null) {
			return;
		}

		User caseUser = userRepository.findById(_case.getUserId()).orElse(null);

		if (caseUser == null) {
			return;
		}

		// Advocate info
		String advocateName = null;
		String advocateUserId = null;

		try {
			Advocate advocate = advocateRepository.findById(_case.getAdvocateId()).orElse(null);
			if (advocate != null) {
				advocateUserId = advocate.getUserId();
				User advUser = userRepository.findById(advocate.getUserId()).orElse(null);
				if (advUser != null) {
					advocateName = advUser.getFullName() == null ? advUser.getName() : advUser.getFullName();
				}
			}
		} catch (Exception e) {
		}

		List<String> destinations = new ArrayList<>();
		destinations.add("CaseRelatedPages");
		destinations.add("CaseDetailsPage");

		Map<String, String> map = new HashMap<>();
		map.put("userId", _case.getUserId());
		map.put("caseId", _case.getId());
		map.put("caseName", _case.getCaseName());
		map.put("advocateId", _case.getAdvocateId());
		map.put("issuedTime", _case.getIssuedTime() != null ? _case.getIssuedTime().toString() : "");
		map.put("caseLawyer", advocateName);
		map.put("advocateUserId", advocateUserId);
		map.put("userName", caseUser.getFullName());

		String message = "Hi " + caseUser.getName() + ", your case " + _case.getCaseName() + "'s "
				+ tracking.getCaseStage() + " stage will be organized at " + tracking.getTrackingTime();

		// 📩 Notification
		notificationService.sendNotification(caseUser.getId(), message, destinations, map);

		// 💬 Chat — from advocate user to case user
		if (advocateUserId != null) {
			chatService.saveMessage(new ChatMessage(advocateUserId, caseUser.getId(), message));
		}
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public List<CaseTrackingResponse> swapOrder(String caseTrackingId1, String caseTrackingId2) {

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

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new ArithmeticException("Case tracking is not swaped...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CaseTrackingResponse> findAll() {

		try {

			List<CaseTracking> list = caseTrackingRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByVisibility_' + #visibility")
	public List<CaseTrackingResponse> findByVisibility(boolean visibility) {

		try {

			List<CaseTracking> list = caseTrackingRepository.findByVisibility(visibility);

			if (list.isEmpty()) {

				throw new NoSuchElementException("No such case tracking present at here...");

			}

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseId_' + #caseId")
	public List<CaseTrackingResponse> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseId(caseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCasePayment_' + #caseStage")
	public List<CaseTrackingResponse> findByCaseStage(CasePayment caseStage) {

		if (caseStage == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseStage(caseStage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseIdAndStageNumber_' + #caseId + '_' + #stageNumber")
	public CaseTrackingResponse findByCaseIdAndStageNumber(String caseId, int stageNumber) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseTracking caseTracking = caseTrackingRepository.findByCaseIdAndStageNumber(caseId, stageNumber);

			if (caseTracking == null) {

				throw new Exception();

			}

			return getCaseTrackingResponse(caseTracking);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseIdAndCaseStage_' + #caseId + '_' + #caseStage")
	public CaseTrackingResponse findByCaseIdAndCaseStage(String caseId, CasePayment caseStage) {

		if (caseId == null || caseStage == null) {

			throw new NullPointerException("False request....");

		}

		try {

			CaseTracking caseTracking = caseTrackingRepository.findByCaseIdAndCaseStage(caseId, caseStage);

			if (caseTracking == null) {

				throw new Exception();

			}

			return getCaseTrackingResponse(caseTracking);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case stage is setted at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseIdAndStageNumberGreaterThan_' + #caseId + '_' + #stageNumber")
	public List<CaseTrackingResponse> findByCaseIdAndStageNumberGreaterThan(String caseId, int stageNumber) {

		if (caseId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CaseTracking> list = caseTrackingRepository.findByCaseIdAndStageNumberGreaterThan(caseId, stageNumber);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCaseTrackingResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case tracking stage find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "AdvocateRating", allEntries = true),
			@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true), @CacheEvict(value = "CaseClose", allEntries = true),
			@CacheEvict(value = "CaseJudgement", allEntries = true), @CacheEvict(value = "Case", allEntries = true),
			// @CacheEvict(value = "CaseTracking", allEntries = true),
			@CacheEvict(value = "DocumentDraft", allEntries = true), @CacheEvict(value = "Hearing", allEntries = true),
			@CacheEvict(value = "ReadStatus", allEntries = true),
			@CacheEvict(value = "PaymentDetails", allEntries = true) })
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

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private CaseTrackingResponse getCaseTrackingResponse(CaseTracking caseTracking) {

		List<CaseTracking> list = new ArrayList<>();

		list.add(caseTracking);

		return getCaseTrackingResponse(list).get(0);

	}

	private List<CaseTrackingResponse> getCaseTrackingResponse(List<CaseTracking> list) {

		List<CaseTrackingResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> caseIdFuture = CompletableFuture.supplyAsync(() -> list.stream().distinct()
				.filter(Objects::nonNull).map(CaseTracking::getCaseId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Case>> caseMapFuture = caseIdFuture.thenApplyAsync(casesId -> {

			if (casesId == null || casesId.isEmpty()) {

				return new HashMap<>();

			}

			return caseRepository.findAllById(casesId).stream()
					.collect(Collectors.toMap(Case::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(caseIdFuture, caseMapFuture).join();

		Map<String, Case> caseMap = caseMapFuture.join();

		for (CaseTracking tracking : list) {

			try {

				CaseTrackingResponse response = new CaseTrackingResponse();

				response.setId(tracking.getId());
				response.setCaseId(tracking.getCaseId());
				response.setCaseStage(tracking.getCaseStage());
				response.setStageNumber(tracking.getStageNumber());
				response.setTrackingTime(tracking.getTrackingTime());
				response.setVisibility(tracking.isVisibility());

				try {

					response.setTrackingCase(caseMap.get(tracking.getCaseId()));

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				responses.add(response);

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		return responses;

	}

}
