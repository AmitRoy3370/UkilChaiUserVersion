package com.example.demo700.Services.AdvocateServices;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateDocumentsRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class AdvocateDocumentsServiceImpl implements AdvocateDocumentsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateDocumentsRepository documentRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "AdvocateDocuments";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public AdvocateDocuments addDocuments(String advocateId, MultipartFile[] files) {

		if (advocateId == null) {

			throw new NullPointerException("False requst....");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findById(advocateId).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here....");

		}

		try {

			AdvocateDocuments advocateDocuments = documentRepository.findByAdvocateId(advocateId);

			if (advocateDocuments != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocates documents are already setted...");

		} catch (Exception e) {

		}

		List<String> attachments = new ArrayList<>();

		try {

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {

				} else {

					String fileId = imageService.upload(file);

					if (fileId != null) {

						attachments.add(fileId);

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Document's are not uploaded...");

		}

		AdvocateDocuments documents = new AdvocateDocuments(advocateId, attachments);

		documents = documentRepository.save(documents);

		if (documents == null) {

			throw new ArithmeticException("Document's not saved...");

		}

		return documents;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public AdvocateDocuments updateDocuments(String id, String userId, AdvocateDocuments documents,
			MultipartFile[] files) {

		if (id == null || userId == null || documents == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(documents.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			AdvocateDocuments advocateDocuments = documentRepository.findByAdvocateId(documents.getAdvocateId());

			if (advocateDocuments != null) {

				if (!advocateDocuments.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocates documents are already setted...");

		} catch (Exception e) {

		}

		AdvocateDocuments advocateDocuments = null;

		try {

			advocateDocuments = documentRepository.findById(id).get();

			if (advocateDocuments == null) {

				throw new Exception();

			}

			if (!advocateDocuments.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate documents find at here...");

		}

		List<String> attachments = new ArrayList<>();

		for (String i : advocateDocuments.getAttachments()) {

			if (documents.getAttachments().contains(i)) {

				attachments.add(i);

			} else {

				try {

					imageService.delete(i);

				} catch (Exception e) {

				}

			}

		}

		for (String i : documents.getAttachments()) {

			try {

				if (!attachments.contains(i) && imageService.attachmentExists(i)) {

					attachments.add(i);

				}

			} catch (Exception e) {

			}

		}

		try {

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {

				} else {

					String fileId = imageService.upload(file);

					if (fileId != null) {

						attachments.add(fileId);

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Document's are not uploaded...");

		}

		documents.setAttachments(attachments);

		documents.setId(id);

		Query query = new Query(Criteria.where("_id").is(documents.getId()));

		Update update = new Update();

		update.set("id", documents.getId());
		update.set("advocateId", documents.getAdvocateId());
		update.set("attachments", documents.getAttachments());

		mongoTemplate.updateFirst(query, update, AdvocateDocuments.class);

		documents = mongoTemplate.findOne(query, AdvocateDocuments.class);

		// documents = documentRepository.save(documents);

		return documents;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public AdvocateDocuments findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateDocuments documents = documentRepository.findById(id).get();

			if (documents == null) {

				throw new Exception();

			}

			return documents;

		} catch (Exception e) {

			throw new NoSuchElementException("No such documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<AdvocateDocuments> findAll() {

		try {

			List<AdvocateDocuments> documents = documentRepository.findAll();

			if (documents == null || documents.isEmpty()) {

				throw new Exception();

			}

			return documents;

		} catch (Exception e) {

			throw new NoSuchElementException("No such documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public AdvocateDocuments findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateDocuments documents = documentRepository.findByAdvocateId(advocateId);

			if (documents == null) {

				throw new Exception();

			}

			return documents;

		} catch (Exception e) {

			throw new NoSuchElementException("No such documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAttachmentsId_' + #attachments")
	public List<AdvocateDocuments> findByAttachmentsContainingIgnoreCase(String attachments) {

		if (attachments == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateDocuments> documents = documentRepository.findByAttachmentsContainingIgnoreCase(attachments);

			if (documents == null || documents.isEmpty()) {

				throw new Exception();

			}

			return documents;

		} catch (Exception e) {

			throw new NoSuchElementException("No such documents find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public boolean deleteDocuments(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		Advocate advocate = null;

		try {

			advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		AdvocateDocuments advocateDocuments = null;

		try {

			advocateDocuments = documentRepository.findById(id).get();

			if (advocateDocuments == null) {

				throw new ArithmeticException();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such data find at here......");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			if (admin.getAdvocates().contains(advocate.getId())) {

				long count = documentRepository.count();

				cleaner.removeAdvocateDocuments(id);

				return count != documentRepository.count();

			} else {

				if (!advocateDocuments.getAdvocateId().equals(advocate.getId())) {

					throw new ArithmeticException("You can update only your profile...");

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can remove only your advocate...");

		} catch (Exception e) {

		}

		if (!advocateDocuments.getAdvocateId().equals(advocate.getId())) {

			throw new ArithmeticException("You can update only your profile...");

		}

		long count = documentRepository.count();

		cleaner.removeAdvocateDocuments(id);

		return count != documentRepository.count();

	}

}
