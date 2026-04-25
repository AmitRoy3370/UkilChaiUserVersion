package com.example.demo700.Services.CaseServices;

import java.time.Instant;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.bson.types.ObjectId;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.CaseRequestResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRequestRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdvocateServices.CVUploadService;
import com.example.demo700.Services.NotificationServices.NotificationService;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class CaseRequestServiceimpl implements CaseRequestService {

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CaseRequestRepository caseRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private ImageService fileUpload;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private Cleaner cleaner;

	@Override
	public CaseRequest addCaseRequest(CaseRequest caseRequest, String userId, MultipartFile files[]) {

		if (caseRequest == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(caseRequest.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here...");

		}

		try {

			CaseRequest caseRequest1 = caseRequestRepository.findByCaseNameIgnoreCase(caseRequest.getCaseName());

			if (caseRequest1 != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case name already exist at here...");

		} catch (Exception e) {

		}

		try {

			Case acceptedCase = caseRepository.findByCaseNameIgnoreCase(caseRequest.getCaseName());

			if (acceptedCase != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case name already exist at here...");

		} catch (Exception e) {

		}

		try {

			if (caseRequest.getRequestedAdvocateId() != null) {

				Advocate advocate = advocateRepository.findById(caseRequest.getRequestedAdvocateId().trim()).get();

				if (advocate == null) {

					throw new Exception();

				}

				if (!advocate.getAdvocateSpeciality().contains(caseRequest.getCaseType())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such requested advocate find at here....");

		}

		try {

			List<String> list = new ArrayList<>();

			for (MultipartFile file : files) {

				try {

					String id = fileUpload.upload(file);

					if (id != null) {

						list.add(id);

					}

				} catch (Exception e) {

				}

			}

			String s[] = new String[list.size()];

			int index = 0;

			for (String i : list) {

				s[index++] = i;

			}

			caseRequest.setAttachmentId(s);

		} catch (Exception e) {

		}

		caseRequest = caseRequestRepository.save(caseRequest);

		return caseRequest;
	}

	@Override
	public CaseRequest updateCaseRequest(CaseRequest caseRequest, String userId, String caseRequestId,
			MultipartFile files[]) {

		if (caseRequest == null || userId == null || caseRequestId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseRequest _caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (_caseRequest == null) {

				throw new Exception();

			}

			if (!_caseRequest.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case request find at here...");

		}

		try {

			User user = userRepository.findById(caseRequest.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here...");

		}

		try {

			CaseRequest caseRequest1 = caseRequestRepository.findByCaseNameIgnoreCase(caseRequest.getCaseName());

			if (caseRequest1 != null) {

				if (!caseRequest1.getId().equals(caseRequestId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case name already exist at here...");

		} catch (Exception e) {

		}

		try {

			Case acceptedCase = caseRepository.findByCaseNameIgnoreCase(caseRequest.getCaseName());

			if (acceptedCase != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case name already exist at here...");

		} catch (Exception e) {

		}

		try {

			if (caseRequest.getRequestedAdvocateId() != null) {

				Advocate advocate = advocateRepository.findById(caseRequest.getRequestedAdvocateId().trim()).get();

				if (advocate == null) {

					throw new Exception();

				}

				if (!advocate.getAdvocateSpeciality().contains(caseRequest.getCaseType())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such requested advocate find at here....");

		}

		try {

			List<String> list = new ArrayList<>();

			for (String i : caseRequest.getAttachmentId()) {

				try {

					if (fileUpload.attachmentExists(i)) {
						list.add(i);
					}

				} catch (Exception e) {

				}

			}

			CaseRequest oldCaseRequest = caseRequestRepository.findById(caseRequestId).get();

			for (String i : oldCaseRequest.getAttachmentId()) {

				if (!list.contains(i)) {

					try {

						fileUpload.delete(i);

					} catch (Exception e) {

					}

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

						System.out.println("exception in update case case request service :- " + e.getMessage());

					}

					++index;

				}

			}

			String s[] = new String[list.size()];

			int index = 0;

			System.out.println("All attachments :- ");

			for (String i : list) {

				s[index++] = i;

				System.out.println(i);

			}

			caseRequest.setAttachmentId(s);

		} catch (Exception e) {

		}

		caseRequest.setId(caseRequestId);

		caseRequest = caseRequestRepository.save(caseRequest);

		return caseRequest;
	}

	@Override
	public List<CaseRequestResponse> findByCaseNameContainingIgnoreCase(String caseName) {

		if (caseName == null) {

			throw new NullPointerException("False request...");

		}

		List<CaseRequest> list = caseRequestRepository.findByCaseNameContainingIgnoreCase(caseName);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public List<CaseRequestResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		List<CaseRequest> list = caseRequestRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public List<CaseRequestResponse> findByCaseType(AdvocateSpeciality caseType) {

		if (caseType == null) {

			throw new NullPointerException("False request....");

		}

		List<CaseRequest> list = caseRequestRepository.findByCaseType(caseType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public List<CaseRequestResponse> findByIssuedTimeAfter(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("False request....");

		}

		List<CaseRequest> list = caseRequestRepository.findByRequestDateAfter(issuedTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public List<CaseRequestResponse> findByIssuedTimeBefore(Instant issuedTime) {

		if (issuedTime == null) {

			throw new NullPointerException("False request...");

		}

		List<CaseRequest> list = caseRequestRepository.findByRequestDateBefore(issuedTime);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public CaseRequestResponse findById(String caseRequestId) {

		if (caseRequestId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			return getCaseRequestResponseFromCaseRequest(caseRequestRepository.findById(caseRequestId).get());

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

	}

	@Override
	public List<CaseRequestResponse> allCaseRequest() {

		List<CaseRequest> list = caseRequestRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such case find at here....");

		}

		return getCaseRequestResponseFromCaseRequest(list);
	}

	@Override
	public boolean removeCaseRequest(String caseRequestId, String userId) {

		if (caseRequestId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseRequest _caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (_caseRequest == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = caseRequestRepository.count();

					cleaner.removeCaseRequest(caseRequestId);

					return count != caseRequestRepository.count();

				}

			} catch (Exception e) {

			}

			if (!_caseRequest.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case request find at here...");

		}

		long count = caseRequestRepository.count();

		cleaner.removeCaseRequest(caseRequestId);

		return count != caseRequestRepository.count();
	}

	@Override
	public Case handleCaseRequest(String caseRequestId, String userId) {

		if (caseRequestId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CaseRequest _caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (_caseRequest == null) {

				throw new Exception();

			}

			Case oldCase = caseRepository.findByCaseNameIgnoreCase(_caseRequest.getCaseName());

			if (oldCase != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case name already exist at here...");

		} catch (Exception e) {

		}

		Case acceptedCase = new Case();

		try {

			CaseRequest _caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (_caseRequest == null) {

				throw new Exception();

			}

			acceptedCase.setCaseName(_caseRequest.getCaseName());

			String s[] = _caseRequest.getAttachmentId();

			_caseRequest.setAttachmentId(null);

			acceptedCase.setAttachmentId(s);
			acceptedCase.setCaseType(_caseRequest.getCaseType());
			acceptedCase.setIssuedTime(_caseRequest.getRequestDate());
			acceptedCase.setUserId(_caseRequest.getUserId());

			caseRequestRepository.save(_caseRequest);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case request find at here...");

		}

		try {

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			CaseRequest _caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (_caseRequest == null) {

				throw new Exception();

			}

			if (!advocate.getAdvocateSpeciality().contains(_caseRequest.getCaseType())) {

				throw new Exception();

			}

			if (_caseRequest.getRequestedAdvocateId() != null) {

				if (!_caseRequest.getRequestedAdvocateId().equals(advocate.getId())) {

					throw new Exception();

				}

			}

			acceptedCase.setAdvocateId(advocate.getId());

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here who can accept this case......");

		}

		cleaner.removeCaseRequest(caseRequestId);

		acceptedCase = caseRepository.save(acceptedCase);

		if (acceptedCase == null) {

			throw new ArithmeticException("No such case added at here...");

		}

		User user = userRepository.findById(acceptedCase.getUserId()).get();

		String name = user.getName();
		String requestedUserId = user.getId();

		Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

		user = userRepository.findById(advocate.getUserId()).get();

		String advocateName = user.getName();

		notificationService.sendNotification(requestedUserId, name + " your case is accepted by the " + advocateName);

		return acceptedCase;
	}

	@Override
	public List<CaseRequestResponse> findByRequestedAdvocateId(String requestedAdvocateId) {

		if (requestedAdvocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CaseRequest> list = caseRequestRepository.findByRequestedAdvocateId(requestedAdvocateId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCaseRequestResponseFromCaseRequest(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such case request find at here...");

		}

	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private CaseRequestResponse getCaseRequestResponseFromCaseRequest(CaseRequest caseRequest) {

		List<CaseRequest> list = new ArrayList<>();

		list.add(caseRequest);

		return getCaseRequestResponseFromCaseRequest(list).get(0);

	}

	private List<CaseRequestResponse> getCaseRequestResponseFromCaseRequest(List<CaseRequest> requests) {

		List<CaseRequestResponse> responses = new ArrayList<>();

		List<String> allUserId = requests.stream().map(CaseRequest::getUserId).collect(Collectors.toList());

		List<String> allAdvocateId = requests.stream().filter(Objects::nonNull)
				.filter(caseRequest -> caseRequest.getRequestedAdvocateId() != null)
				.map(CaseRequest::getRequestedAdvocateId).collect(Collectors.toList());

		List<Advocate> advocates = advocateRepository.findAllById(allAdvocateId);

		for (Advocate advocate : advocates) {

			if (!allUserId.contains(advocate.getUserId())) {

				allUserId.add(advocate.getUserId());

			}

		}

		CompletableFuture<Map<String, String>> advocateFuture = CompletableFuture
				.supplyAsync(
						() -> advocates.isEmpty() ? new HashMap<>()
								: advocates.stream().collect(Collectors.toMap(Advocate::getId,
										advocate -> advocate.getUserId(), (existing, replacement) -> existing)),
						executor);

		List<User> users = userRepository.findAllById(allUserId);

		CompletableFuture<Map<String, User>> userFuture = CompletableFuture.supplyAsync(() -> users.isEmpty()
				? new HashMap<>()
				: users.stream().collect(
						Collectors.toMap(User::getId, Function.identity(), (existing, replacement) -> existing)),
				executor);

		CompletableFuture.allOf(advocateFuture, userFuture).join();

		Map<String, User> userMap = userFuture.join();
		Map<String, String> advocateMap = advocateFuture.join();

		for (CaseRequest request : requests) {

			try {

				CaseRequestResponse response = new CaseRequestResponse();

				response.setId(request.getId());
				response.setCaseName(request.getCaseName());
				response.setCaseType(request.getCaseType());
				response.setRequestDate(request.getRequestDate());

				if (request.getRequestedAdvocateId() != null) {

					response.setRequestedAdvocateId(request.getRequestedAdvocateId());
					response.setRequestAdvocateName(
							userMap.get(advocateMap.get(request.getRequestedAdvocateId())).getName());

				}

				try {

					response.setUserId(request.getUserId());
					response.setUserName(userMap.get(request.getUserId()).getName());

				} catch (Exception e) {

				}

				response.setAttachmentId(request.getAttachmentId());

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
