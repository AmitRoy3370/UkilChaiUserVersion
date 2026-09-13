package com.example.demo700.Services.CopyrightServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.CopyrightRegistrationProcessResponse;
import com.example.demo700.DTOFiles.CopyrightResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CopyrightModels.Copyright;
import com.example.demo700.Model.CopyrightModels.CopyrightRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRegistrationProcessRepository;
import com.example.demo700.Repositories.CopyrightRepositories.CopyrightRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Validators.EmailValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class CopyrightServiceImpl implements CopyrightService {

	@Autowired
	private CopyrightRepository copyrightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CopyrightRegistrationProcessRepository processRepository;

	@Autowired
	private Cleaner cleaner;

	private PhoneValidator phoneValidator;

	private EmailValidator emailValidator = new EmailValidator();

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String cacheValue = "Copyright";

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public Copyright addCopyright(Copyright copyright, String userId, MultipartFile documents[]) {

		if (copyright == null || userId == null || !copyright.getUserId().equals(userId)) {

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

		try {

			if (emailValidator.isValidEmail(copyright.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			phoneValidator = new PhoneValidator(copyright.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phon number is not valid...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(copyright.getEmail());

			if (right != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same email already exist...");

		} catch (Exception e) {

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(copyright.getMobileNumber());

			if (right != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same phone already exist...");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : copyright.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

		}

		copyright.setDocuments(attachmentsId);

		copyright = copyrightRepository.save(copyright);

		return copyright;
	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public Copyright updateCopyright(Copyright copyright, String userId, String id, MultipartFile documents[]) {
		if (id == null || copyright == null || userId == null || !copyright.getUserId().equals(userId)) {

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

		try {

			if (emailValidator.isValidEmail(copyright.getEmail())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Email is not valid...");

		}

		try {

			phoneValidator = new PhoneValidator(copyright.getMobileNumber());

			if (phoneValidator.isValid()) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Phon number is not valid...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(copyright.getEmail());

			if (right != null) {

				if (!right.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same email already exist...");

		} catch (Exception e) {

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(copyright.getMobileNumber());

			if (right != null) {

				if (!right.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Same phone already exist...");

		} catch (Exception e) {

		}

		Copyright _copyright = null;

		try {

			_copyright = copyrightRepository.findById(id).get();

			if (_copyright == null) {

				throw new Exception();

			}

			if (!_copyright.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such cpyright find at here...");

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : copyright.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {

			for (MultipartFile i : documents) {

				try {

					String attachmentId = imageService.upload(i);

					if (attachmentId != null) {

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

		} catch (Exception e) {

		}

		try {

			for (String i : _copyright.getDocuments()) {

				try {

					if (!copyright.getDocuments().contains(i)) {

						imageService.delete(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		copyright.setDocuments(attachmentsId);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", copyright.getUserId());
		update.set("author", copyright.getAuthor());
		update.set("typeOfWork", copyright.getTypeOfWork());
		update.set("yearOfCreation", copyright.getYearOfCreation());
		update.set("titleOfWork", copyright.getTitleOfWork());
		update.set("description", copyright.getDescription());
		update.set("applicationName", copyright.getApplicationName());
		update.set("mobileNumber", copyright.getMobileNumber());
		update.set("email", copyright.getEmail());
		update.set("adress", copyright.getAdress());
		update.set("documents", copyright.getDocuments());

		mongoTemplate.updateFirst(query, update, Copyright.class);

		copyright = mongoTemplate.findOne(query, Copyright.class);

		return copyright;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CopyrightResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findById(id).get();

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CopyrightResponse> findAll() {

		try {

			List<Copyright> right = copyrightRepository.findAll();

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public List<CopyrightResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByUserId(userId);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAuthorPrefix_' + #author")
	public List<CopyrightResponse> findByAuthorContainingIgnoreCase(String author) {

		if (author == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByAuthorContainingIgnoreCase(author);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTypeOfWork_' + #typeOfWork")
	public List<CopyrightResponse> findByTypeOfWorkContainingIgnoreCase(String typeOfWork) {

		if (typeOfWork == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByTypeOfWorkContainingIgnoreCase(typeOfWork);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfCreationAfter_' + #yearOfCreation")
	public List<CopyrightResponse> findByYearOfCreationAfter(Instant yearOfCreation) {

		if (yearOfCreation == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByYearOfCreationAfter(yearOfCreation);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByYearOfCreationBefore_' + #yearOfCreation")
	public List<CopyrightResponse> findByYearOfCreationBefore(Instant yearOfCreation) {

		if (yearOfCreation == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByYearOfCreationBefore(yearOfCreation);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTitleOfWork_' + #titleOfWork")
	public List<CopyrightResponse> findByTitleOfWorkContainingIgnoreCase(String titleOfWork) {

		if (titleOfWork == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByTitleOfWorkContainingIgnoreCase(titleOfWork);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDescription_' + #description")
	public List<CopyrightResponse> findByDescriptionContainingIgnoreCase(String description) {

		if (description == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByDescriptionContainingIgnoreCase(description);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByApplicationName_' + #applicationName")
	public List<CopyrightResponse> findByApplicationNameContainingIgnoreCase(String applicationName) {

		if (applicationName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByApplicationNameContainingIgnoreCase(applicationName);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMobileNumber_' + #mobileNumber")
	public CopyrightResponse findByMobileNumber(String mobileNumber) {

		if (mobileNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findByMobileNumber(mobileNumber);

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByEmail_' + #email")
	public CopyrightResponse findByEmail(String email) {

		if (email == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Copyright right = copyrightRepository.findByEmail(email);

			if (right == null) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdress_' + #adress")
	public List<CopyrightResponse> findByAdressContainingIgnoreCase(String adress) {

		if (adress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByAdressContainingIgnoreCase(adress);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #documents")
	public List<CopyrightResponse> findByDocumentsContainingIgnoreCase(String documents) {

		if (documents == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Copyright> right = copyrightRepository.findByDocumentsContainingIgnoreCase(documents);

			if (right == null || right.isEmpty()) {

				throw new Exception();

			}

			return getCopyrightResponse(right);

		} catch (Exception e) {

			throw new NoSuchElementException("No such copyright find at here...");

		}

	}

	@Override
	@Caching(evict = {

			@CacheEvict(value = "Copyright", allEntries = true),
			@CacheEvict(value = "CopyrightRegistrationProcess", allEntries = true),
			@CacheEvict(value = "CopyrightPayment", allEntries = true),

	})
	public boolean removeCopyright(String id, String userId) {
		if (userId == null || id == null) {

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

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			long count = copyrightRepository.count();

			cleaner.removeCopyright(id);

			return count != copyrightRepository.count();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		Copyright copyright = null;

		try {

			copyright = copyrightRepository.findById(id).get();

			if (copyright == null) {

				throw new Exception();

			}

			if (!copyright.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such cpyright find at here...");

		}

		long count = copyrightRepository.count();

		cleaner.removeCopyright(id);

		return count != copyrightRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private CopyrightResponse getCopyrightResponse(Copyright copyright) {

		List<Copyright> list = new ArrayList<>();

		list.add(copyright);

		return getCopyrightResponse(list).get(0);

	}

	private List<CopyrightResponse> getCopyrightResponse(List<Copyright> list) {

    System.out.println("═══════════════════════════════════════════════════════════════");
    System.out.println("=== START getCopyrightResponse ===");
    System.out.println("Input list size: " + (list != null ? list.size() : "null"));

    List<CopyrightResponse> responses = new ArrayList<>();

    if (list == null || list.isEmpty()) {
        System.out.println("⚠️ List is null or empty, returning empty list");
        System.out.println("=== END getCopyrightResponse ===");
        System.out.println("═══════════════════════════════════════════════════════════════");
        return responses;
    }

    System.out.println("STEP 1: Creating CompletableFutures...");

    // ==================== FUTURE 1: Extract Copyright IDs ====================
    CompletableFuture<List<String>> copyrightIdListFuture = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("  [Future-1] copyrightIdListFuture: Extracting Copyright IDs");
                try {
                    List<String> ids = list.stream().map(Copyright::getId).collect(Collectors.toList());
                    System.out.println("  [Future-1] ✅ Found " + ids.size() + " copyright IDs: " + ids);
                    return ids;
                } catch (Exception e) {
                    System.err.println("  [Future-1] ❌ ERROR extracting copyright IDs: " + e.getMessage());
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }, executor);

    // ==================== FUTURE 2: Fetch Process Map ====================
    CompletableFuture<Map<String, CopyrightRegistrationProcess>> processMapFuture = copyrightIdListFuture
            .thenApplyAsync(copyrightIds -> {
                System.out.println("  [Future-2] processMapFuture: Processing copyright IDs");
                try {
                    if (copyrightIds.isEmpty()) {
                        System.out.println("  [Future-2] ⚠️ Copyright IDs list is empty, returning empty map");
                        return new HashMap<>();
                    }

                    System.out.println("  [Future-2] Fetching processes for " + copyrightIds.size() + " copyright IDs");
                    List<CopyrightRegistrationProcess> processes = processRepository.findByCopyrightIdIn(copyrightIds);
                    System.out.println("  [Future-2] Found " + processes.size() + " processes from DB");

                    Map<String, CopyrightRegistrationProcess> map = processes.stream()
                            .collect(Collectors.toMap(
                                CopyrightRegistrationProcess::getCopyrightId,
                                Function.identity(),
                                (existing, replacement) -> {
                                    System.out.println("  [Future-2] ⚠️ Duplicate key found: "
                                        + existing.getCopyrightId() + " - keeping existing");
                                    return existing;
                                }
                            ));
                    System.out.println("  [Future-2] ✅ Created process map with " + map.size() + " entries");
                    System.out.println("  [Future-2] Process map keys: " + map.keySet());
                    return map;
                } catch (Exception e) {
                    System.err.println("  [Future-2] ❌ ERROR fetching processes: " + e.getMessage());
                    e.printStackTrace();
                    return new HashMap<>();
                }
            }, executor);

    // ==================== FUTURE 3: Convert Process Map to List ====================
    CompletableFuture<List<CopyrightRegistrationProcess>> processListFuture = processMapFuture
            .thenApplyAsync(processMap -> {
                System.out.println("  [Future-3] processListFuture: Converting process map to list");
                try {
                    if (processMap.isEmpty()) {
                        System.out.println("  [Future-3] ⚠️ Process map is empty, returning empty list");
                        return new ArrayList<>();
                    }
                    List<CopyrightRegistrationProcess> processList = processMap.values().stream()
                            .collect(Collectors.toList());
                    System.out.println("  [Future-3] ✅ Found " + processList.size() + " processes");
                    return processList;
                } catch (Exception e) {
                    System.err.println("  [Future-3] ❌ ERROR converting to list: " + e.getMessage());
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }, executor);

    // ==================== FUTURE 4: Extract Advocate IDs ====================
    CompletableFuture<List<String>> advocatesIdFuture = processListFuture.thenApplyAsync(processList -> {
        System.out.println("  [Future-4] advocatesIdFuture: Extracting advocate IDs");
        try {
            if (processList.isEmpty()) {
                System.out.println("  [Future-4] ⚠️ Process list is empty, returning empty list");
                return new ArrayList<>();
            }
            List<String> advocateIds = processList.stream()
                    .map(CopyrightRegistrationProcess::getAdvocateId)
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println("  [Future-4] ✅ Found " + advocateIds.size() + " distinct advocate IDs: " + advocateIds);
            return advocateIds;
        } catch (Exception e) {
            System.err.println("  [Future-4] ❌ ERROR extracting advocate IDs: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }, executor);

    // ==================== FUTURE 5: Fetch Advocates ====================
    CompletableFuture<List<Advocate>> advocatesListFuture = advocatesIdFuture.thenApplyAsync(advocatesId -> {
        System.out.println("  [Future-5] advocatesListFuture: Fetching advocates");
        try {
            if (advocatesId.isEmpty()) {
                System.out.println("  [Future-5] ⚠️ Advocate IDs list is empty, returning empty list");
                return new ArrayList<>();
            }
            System.out.println("  [Future-5] Fetching " + advocatesId.size() + " advocates from DB");
            List<Advocate> advocates = advocateRepository.findAllById(advocatesId).stream()
                    .collect(Collectors.toList());
            System.out.println("  [Future-5] ✅ Found " + advocates.size() + " advocates");
            return advocates;
        } catch (Exception e) {
            System.err.println("  [Future-5] ❌ ERROR fetching advocates: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }, executor);

    // ==================== FUTURE 6: Create Advocate Map ====================
    CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesListFuture
            .thenApplyAsync(advocatesList -> {
                System.out.println("  [Future-6] advocateMapFuture: Creating advocate map");
                try {
                    if (advocatesList.isEmpty()) {
                        System.out.println("  [Future-6] ⚠️ Advocate list is empty, returning empty map");
                        return new HashMap<>();
                    }
                    Map<String, Advocate> map = advocatesList.stream()
                            .collect(Collectors.toMap(Advocate::getId, Function.identity()));
                    System.out.println("  [Future-6] ✅ Created advocate map with " + map.size() + " entries");
                    return map;
                } catch (Exception e) {
                    System.err.println("  [Future-6] ❌ ERROR creating advocate map: " + e.getMessage());
                    e.printStackTrace();
                    return new HashMap<>();
                }
            }, executor);

    // ==================== FUTURE 7: Extract Advocate User IDs ====================
    CompletableFuture<List<String>> advocatesUserIdListFuture = advocatesListFuture.thenApplyAsync(advocates -> {
        System.out.println("  [Future-7] advocatesUserIdListFuture: Extracting advocate user IDs");
        try {
            if (advocates.isEmpty()) {
                System.out.println("  [Future-7] ⚠️ Advocates list is empty, returning empty list");
                return new ArrayList<>();
            }
            List<String> userIds = advocates.stream()
                    .map(Advocate::getUserId)
                    .collect(Collectors.toList());
            System.out.println("  [Future-7] ✅ Found " + userIds.size() + " advocate user IDs: " + userIds);
            return userIds;
        } catch (Exception e) {
            System.err.println("  [Future-7] ❌ ERROR extracting advocate user IDs: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }, executor);

    // ==================== FUTURE 8: Extract Center Admin User IDs ====================
    CompletableFuture<List<String>> centerAdminsUserIdListFuture = processListFuture.thenApplyAsync(processList -> {
        System.out.println("  [Future-8] centerAdminsUserIdListFuture: Extracting center admin user IDs");
        try {
            if (processList.isEmpty()) {
                System.out.println("  [Future-8] ⚠️ Process list is empty, returning empty list");
                return new ArrayList<>();
            }
            List<String> userIds = processList.stream()
                    .map(CopyrightRegistrationProcess::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println("  [Future-8] ✅ Found " + userIds.size() + " distinct center admin user IDs: " + userIds);
            return userIds;
        } catch (Exception e) {
            System.err.println("  [Future-8] ❌ ERROR extracting center admin user IDs: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }, executor);

    // ==================== FUTURE 9: Combine Center Admin + Advocate User IDs ====================
    CompletableFuture<List<String>> centerAdminAndAdvocateUser = centerAdminsUserIdListFuture
            .thenCombine(advocatesUserIdListFuture, (list1, list2) -> {
                System.out.println("  [Future-9] centerAdminAndAdvocateUser: Combining center admin + advocate user IDs");
                try {
                    List<String> safe1 = list1 != null ? list1 : new ArrayList<>();
                    List<String> safe2 = list2 != null ? list2 : new ArrayList<>();
                    List<String> combined = Stream.concat(safe1.stream(), safe2.stream()).toList();
                    System.out.println("  [Future-9] ✅ Combined " + combined.size() + " user IDs: " + combined);
                    return combined;
                } catch (Exception e) {
                    System.err.println("  [Future-9] ❌ ERROR combining user IDs: " + e.getMessage());
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            });

    // ==================== FUTURE 10: Extract Requested User IDs ====================
    CompletableFuture<List<String>> requestedUserId = CompletableFuture.supplyAsync(() -> {
        System.out.println("  [Future-10] requestedUserId: Extracting requested (copyright owner) user IDs");
        try {
            List<String> userIds = list.stream()
                    .map(Copyright::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println("  [Future-10] ✅ Found " + userIds.size() + " requested user IDs: " + userIds);
            return userIds;
        } catch (Exception e) {
            System.err.println("  [Future-10] ❌ ERROR extracting requested user IDs: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }, executor);

    // ==================== FUTURE 11: Combine All User IDs ====================
    CompletableFuture<List<String>> allUserId = requestedUserId.thenCombine(centerAdminAndAdvocateUser,
            (list1, list2) -> {
                System.out.println("  [Future-11] allUserId: Combining all user IDs");
                try {
                    List<String> safe1 = list1 != null ? list1 : new ArrayList<>();
                    List<String> safe2 = list2 != null ? list2 : new ArrayList<>();
                    List<String> combined = Stream.concat(safe1.stream(), safe2.stream())
                            .distinct()
                            .toList();
                    System.out.println("  [Future-11] ✅ Combined " + combined.size() + " total user IDs: " + combined);
                    return combined;
                } catch (Exception e) {
                    System.err.println("  [Future-11] ❌ ERROR combining all user IDs: " + e.getMessage());
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            });

    // ==================== FUTURE 12: Fetch User Map ====================
    CompletableFuture<Map<String, User>> userMapFuture = allUserId.thenApplyAsync(usersId -> {
        System.out.println("  [Future-12] userMapFuture: Fetching users");
        try {
            if (usersId.isEmpty()) {
                System.out.println("  [Future-12] ⚠️ User IDs list is empty, returning empty map");
                return new HashMap<>();
            }
            System.out.println("  [Future-12] Fetching " + usersId.size() + " users from DB");
            Map<String, User> userMap = userRepository.findAllById(usersId).stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
            System.out.println("  [Future-12] ✅ Found " + userMap.size() + " users");
            System.out.println("  [Future-12] User map keys: " + userMap.keySet());
            return userMap;
        } catch (Exception e) {
            System.err.println("  [Future-12] ❌ ERROR fetching users: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }, executor);

    // ==================== WAIT FOR ALL FUTURES ====================
    System.out.println("STEP 2: Waiting for all CompletableFutures to complete...");
    try {
        CompletableFuture.allOf(
                copyrightIdListFuture, processMapFuture, processListFuture,
                advocatesIdFuture, advocatesListFuture, advocatesUserIdListFuture,
                centerAdminsUserIdListFuture, centerAdminAndAdvocateUser,
                requestedUserId, allUserId, userMapFuture, advocateMapFuture
        ).join();
        System.out.println("✅ All CompletableFutures completed successfully");
    } catch (Exception e) {
        System.err.println("❌ ERROR: CompletableFuture.allOf failed: " + e.getMessage());
        e.printStackTrace();
        return new ArrayList<>();
    }

    // ==================== GET RESULTS FROM FUTURES ====================
    System.out.println("STEP 3: Getting results from futures...");
    Map<String, User> userMap = userMapFuture.join();
    System.out.println("  - userMap size: " + userMap.size());

    Map<String, Advocate> advocateMap = advocateMapFuture.join();
    System.out.println("  - advocateMap size: " + advocateMap.size());

    Map<String, CopyrightRegistrationProcess> processMap = processMapFuture.join();
    System.out.println("  - processMap size: " + processMap.size());

    // ==================== BUILD RESPONSES ====================
    System.out.println("STEP 4: Building response DTOs for " + list.size() + " copyrights...");
    int processedCount = 0;
    int failedCount = 0;

    for (Copyright copyright : list) {
        try {
            System.out.println("  ───────────────────────────────────────");
            System.out.println("  Processing Copyright ID: " + copyright.getId());
            System.out.println("    - User ID: " + copyright.getUserId());

            CopyrightResponse response = new CopyrightResponse();

            // Set basic fields
            response.setId(copyright.getId());
            response.setUserId(copyright.getId());  // ⚠️ WARNING: This sets userId = copyrightId! Bug?
            System.out.println("    ⚠️ Setting userId to copyright.getId(): " + copyright.getId());

            response.setAdress(copyright.getAdress());
            response.setApplicationName(copyright.getApplicationName());
            response.setAuthor(copyright.getAuthor());
            response.setDescription(copyright.getDescription());
            response.setDocuments(copyright.getDocuments());
            response.setEmail(copyright.getEmail());
            response.setMobileNumber(copyright.getMobileNumber());
            response.setTitleOfWork(copyright.getTitleOfWork());
            response.setTypeOfWork(copyright.getTypeOfWork());

            // ⚠️ WARNING: typeOfWork being set to titleOfWork field!
             response.setTitleOfWork(copyright.getTitleOfWork());
            System.out.println("    ⚠️ Setting titleOfWork to typeOfWork: " + copyright.getTypeOfWork());

            response.setYearOfCreation(copyright.getYearOfCreation());
            System.out.println("    ✅ Basic fields set");

            // ==================== Set User Name ====================
            try {
                System.out.println("    - Looking up user name for userId: " + copyright.getUserId());
                User user = userMap.get(copyright.getUserId());

                if (user != null) {
                    String name = user.getFullName() == null ? user.getName() : user.getFullName();
                    response.setUserName(name);
                    System.out.println("    ✅ User name set to: " + name);
                } else {
                    System.out.println("    ⚠️ WARNING: User not found in userMap for userId: " + copyright.getUserId());
                    System.out.println("    Available user IDs: " + userMap.keySet());
                }
            } catch (Exception e) {
                System.err.println("    ❌ ERROR setting user name: " + e.getMessage());
                e.printStackTrace();
            }

            // ==================== Set Registration Process ====================
            try {
                System.out.println("    - Looking up registration process for copyrightId: " + copyright.getId());
                CopyrightRegistrationProcess process = processMap.get(copyright.getId());
                System.out.println("    - Process found: " + (process != null ? "Yes" : "No"));

                if (process != null) {
                    CopyrightRegistrationProcessResponse processResponse = new CopyrightRegistrationProcessResponse();

                    processResponse.setId(process.getId());
                    processResponse.setAdvocateId(process.getAdvocateId());
                    processResponse.setUserId(process.getUserId());
                    processResponse.setStatus(process.isStatus());
                    processResponse.setStpes(process.getStpes());
                    processResponse.setCopyrightId(copyright.getId());
                    System.out.println("    - Process basic fields set");
                    System.out.println("      * processId: " + process.getId());
                    System.out.println("      * advocateId: " + process.getAdvocateId());
                    System.out.println("      * processUserId: " + process.getUserId());

                    // ==================== Set Advocate Name ====================
                    try {
                        System.out.println("    - Looking up advocate for advocateId: " + process.getAdvocateId());
                        Advocate advocate = advocateMap.get(process.getAdvocateId());

                        if (advocate != null) {
                            System.out.println("    - Advocate found: " + advocate.getId());
                            System.out.println("    - Advocate userId: " + advocate.getUserId());

                            User advocateUser = userMap.get(advocate.getUserId());
                            if (advocateUser != null) {
                                String name = advocateUser.getFullName() == null
                                        ? advocateUser.getName()
                                        : advocateUser.getFullName();
                                processResponse.setAdvocateName(name);
                                System.out.println("    ✅ Advocate name set to: " + name);
                            } else {
                                System.out.println("    ⚠️ WARNING: Advocate user not found for userId: " + advocate.getUserId());
                                System.out.println("    Available user IDs: " + userMap.keySet());
                            }
                        } else {
                            System.out.println("    ⚠️ WARNING: Advocate not found for advocateId: " + process.getAdvocateId());
                            System.out.println("    Available advocate IDs: " + advocateMap.keySet());
                        }
                    } catch (Exception e) {
                        System.err.println("    ❌ ERROR setting advocate name: " + e.getMessage());
                        e.printStackTrace();
                    }

                    // ==================== Set Process User Name ====================
                    try {
                        System.out.println("    - Looking up process user name for userId: " + process.getUserId());
                        User processUser = userMap.get(process.getUserId());

                        if (processUser != null) {
                            String name = processUser.getFullName() == null
                                    ? processUser.getName()
                                    : processUser.getFullName();
                            processResponse.setUserName(name);
                            System.out.println("    ✅ Process user name set to: " + name);
                        } else {
                            System.out.println("    ⚠️ WARNING: Process user not found for userId: " + process.getUserId());
                        }
                    } catch (Exception e) {
                        System.err.println("    ❌ ERROR setting process user name: " + e.getMessage());
                        e.printStackTrace();
                    }

                    // ⚠️ WARNING: Setting full Copyright entity inside DTO - potential circular reference!
                    System.out.println("    ⚠️ Setting Copyright entity inside processResponse (potential circular ref)");
                    processResponse.setCopyright(copyright);

                    response.setRegistrationProcess(processResponse);
                    System.out.println("    ✅ Registration process set successfully");
                } else {
                    System.out.println("    - No registration process found for this copyright");
                    System.out.println("    Available process map keys: " + processMap.keySet());
                }
            } catch (Exception e) {
                System.err.println("    ❌ ERROR processing registration process: " + e.getMessage());
                e.printStackTrace();
            }

            responses.add(response);
            processedCount++;
            System.out.println("  ✅ Successfully processed Copyright ID: " + copyright.getId());

        } catch (Exception e) {
            failedCount++;
            System.err.println("  ❌ ERROR processing Copyright ID " + copyright.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    System.out.println("=== END getCopyrightResponse ===");
    System.out.println("Total copyrights processed: " + list.size());
    System.out.println("Successfully processed: " + processedCount);
    System.out.println("Failed to process: " + failedCount);
    System.out.println("Response size: " + responses.size());
    System.out.println("═══════════════════════════════════════════════════════════════");

    return responses;
}
}
