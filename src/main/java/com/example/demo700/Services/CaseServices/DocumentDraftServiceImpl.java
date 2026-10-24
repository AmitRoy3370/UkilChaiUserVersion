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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.bson.types.ObjectId;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.DocumentDraftResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.DocumentDraft;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.DocumentDraftRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Model.CaseModels.Case;

@Service
public class DocumentDraftServiceImpl implements DocumentDraftService {

	@Autowired
	private DocumentDraftRepository documentDraftRepository;

	@Autowired
	private ImageService fileUpload;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "DocumentDraft";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public DocumentDraft addDocumentDraft(DocumentDraft documentDraft, String userId, MultipartFile files[]) {

		if (documentDraft == null || userId == null) {

			throw new NullPointerException("False request....");

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

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(documentDraft.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getAdvocateId().equals(documentDraft.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findByCaseId(documentDraft.getCaseId());

			if (draft != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already document drafted....");

		} catch (Exception e) {

		}

		try {

			List<String> list = new ArrayList<>();

			if (documentDraft.getAttachmentsId() != null) {

				for (String i : documentDraft.getAttachmentsId()) {

					if (fileUpload.attachmentExists(i)) {

						list.add(i);

					}

				}

			}

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

			documentDraft.setAttachmentsId(s);

		} catch (Exception e) {

		}

		documentDraft = documentDraftRepository.save(documentDraft);

		if (documentDraft == null) {

			throw new ArithmeticException("document is not added at here....");

		}

		return documentDraft;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public DocumentDraft updateDocumentDraft(DocumentDraft documentDraft, String userId, String documentDraftId,
			MultipartFile files[]) {

		if (documentDraft == null || userId == null || documentDraftId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findById(documentDraftId).get();

			if (draft == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(draft.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(draft.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(userId) || !advocate.getId().equals(acceptedCase.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

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

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(documentDraft.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			if (!acceptedCase.getAdvocateId().equals(documentDraft.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here...");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findByCaseId(documentDraft.getCaseId());

			if (draft != null) {

				if (!draft.getId().equals(documentDraftId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is already document drafted....");

		} catch (Exception e) {

		}

		try {

			int index = 0;

			List<String> list = new ArrayList<>();

			for (String i : documentDraft.getAttachmentsId()) {
				if (fileUpload.attachmentExists(i)) {
					list.add(i);
				}

			}

			DocumentDraft oldDraft = documentDraftRepository.findById(documentDraftId).get();

			for (String i : oldDraft.getAttachmentsId()) {

				try {

					if (!list.contains(i)) {

						fileUpload.delete(i);

					}

				} catch (Exception e) {

				}

			}

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

			String s[] = new String[list.size()];

			index = 0;

			for (String i : list) {

				s[index++] = i;

			}

			documentDraft.setAttachmentsId(s);

		} catch (Exception e) {

		}

		documentDraft.setId(documentDraftId);

		documentDraft = documentDraftRepository.save(documentDraft);

		if (documentDraft == null) {

			throw new ArithmeticException("document is not added at here....");

		}

		return documentDraft;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public List<DocumentDraftResponse> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByAdvocateId(advocateId);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return getDocumentDraftResponse(drafts);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCaseId_' + #caseId")
	public DocumentDraftResponse findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findByCaseId(caseId);

			if (draft == null) {

				throw new Exception();

			}

			return getDocumentDraftResponse(draft);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here....");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByIssuedDateAfter_' + #issuedDate")
	public List<DocumentDraftResponse> findByIssuedDateAfter(Instant issuedDate) {

		if (issuedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByIssuedDateAfter(issuedDate);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return getDocumentDraftResponse(drafts);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByIssuedDateBefore_' + #issuedDate")
	public List<DocumentDraftResponse> findByIssuedDateBefore(Instant issuedDate) {

		if (issuedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByIssuedDateBefore(issuedDate);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return getDocumentDraftResponse(drafts);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<DocumentDraftResponse> seeAll() {

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findAll();

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return getDocumentDraftResponse(drafts);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public DocumentDraftResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False requets....");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findById(id).get();

			if (draft == null) {

				throw new Exception();

			}

			return getDocumentDraftResponse(draft);

		} catch (Exception e) {

			throw new NoSuchElementException("No such document drafts find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = "AdvocateRating", allEntries = true),
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ClientFeedback", allEntries = true),
			@CacheEvict(value = "PostReaction", allEntries = true),
			@CacheEvict(value = "AppealHearing", allEntries = true),
			@CacheEvict(value = "CaseAppeal", allEntries = true), @CacheEvict(value = "CaseClose", allEntries = true),
			@CacheEvict(value = "CaseJudgement", allEntries = true), @CacheEvict(value = "Case", allEntries = true),
			@CacheEvict(value = "CaseTracking", allEntries = true),
			// @CacheEvict(value = "DocumentDraft", allEntries = true),
			@CacheEvict(value = "Hearing", allEntries = true), @CacheEvict(value = "ReadStatus", allEntries = true),
			@CacheEvict(value = "PaymentDetails", allEntries = true) })
	public boolean removeDocumentDraft(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

			if (centerAdmin != null) {

				long count = documentDraftRepository.count();

				cleaner.removeDocumentDraft(id);

				return count != documentDraftRepository.count();

			}

		} catch (Exception e) {

		}

		try {

			DocumentDraft draft = documentDraftRepository.findById(id).get();

			if (draft == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(draft.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(draft.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getUserId().equals(userId) || !advocate.getId().equals(acceptedCase.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

		long count = documentDraftRepository.count();

		cleaner.removeDocumentDraft(id);

		return count != documentDraftRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private DocumentDraftResponse getDocumentDraftResponse(DocumentDraft documentDraft) {

		List<DocumentDraft> list = new ArrayList<>();

		list.add(documentDraft);

		return getDocumentDraftResponse(list).get(0);

	}

	private List<DocumentDraftResponse> getDocumentDraftResponse(List<DocumentDraft> list) {

		List<DocumentDraftResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> caseIdFuture = CompletableFuture.supplyAsync(() -> list.stream().distinct()
				.filter(Objects::nonNull).map(DocumentDraft::getCaseId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Case>> caseMapFuture = caseIdFuture.thenApplyAsync(casesId -> {

			if (casesId == null || casesId.isEmpty()) {

				return new HashMap<>();

			}

			return caseRepository.findAllById(casesId).stream()
					.collect(Collectors.toMap(Case::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(caseIdFuture, caseMapFuture).join();

		Map<String, Case> caseMap = caseMapFuture.join();

		for (DocumentDraft draft : list) {

			try {

				DocumentDraftResponse response = new DocumentDraftResponse();

				response.setId(draft.getId());
				response.setAdvocateId(draft.getAdvocateId());
				response.setAttachmentsId(draft.getAttachmentsId());
				response.setCaseId(draft.getCaseId());
				response.setIssuedDate(draft.getIssuedDate());

				try {

					response.setDocumentDraftedCase(caseMap.get(draft.getCaseId()));

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