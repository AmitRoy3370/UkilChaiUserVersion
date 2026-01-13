package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.DocumentDraft;
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

	@Override
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

			for (MultipartFile file : files) {

				try {

					if (documentDraft.getAttachmentsId() != null && index < documentDraft.getAttachmentsId().length
							&& documentDraft.getAttachmentsId()[index] != null) {

						String id = fileUpload.update(documentDraft.getAttachmentsId()[index], file);

						if (id != null) {

							list.add(id);

						}

					} else {

						String id = fileUpload.upload(file);

						if (id != null) {

							list.add(id);

						}

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
	public List<DocumentDraft> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByAdvocateId(advocateId);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return drafts;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	public DocumentDraft findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findByCaseId(caseId);

			if (draft == null) {

				throw new Exception();

			}

			return draft;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here....");

		}

	}

	@Override
	public List<DocumentDraft> findByIssuedDateAfter(Instant issuedDate) {

		if (issuedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByIssuedDateAfter(issuedDate);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return drafts;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	public List<DocumentDraft> findByIssuedDateBefore(Instant issuedDate) {

		if (issuedDate == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findByIssuedDateBefore(issuedDate);

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return drafts;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	public List<DocumentDraft> seeAll() {

		try {

			List<DocumentDraft> drafts = documentDraftRepository.findAll();

			if (drafts.isEmpty()) {

				throw new Exception();

			}

			return drafts;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document draft find at here...");

		}

	}

	@Override
	public DocumentDraft findById(String id) {

		if (id == null) {

			throw new NullPointerException("False requets....");

		}

		try {

			DocumentDraft draft = documentDraftRepository.findById(id).get();

			if (draft == null) {

				throw new Exception();

			}

			return draft;

		} catch (Exception e) {

			throw new NoSuchElementException("No such document drafts find at here...");

		}

	}

	@Override
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

}
