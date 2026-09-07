package com.example.demo700.Services.TinServices;

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
import com.example.demo700.DTOFiles.RegistrationProcessResponse;
import com.example.demo700.DTOFiles.TinRegistrationProcessDTO;
import com.example.demo700.DTOFiles.TinResponseDTO;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.TinModels.Tin;
import com.example.demo700.Model.TinModels.TinRegistrationProcess;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.TinRepositories.TinRegistrationProcessRepository;
import com.example.demo700.Repositories.TinRepositories.TinRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class TinServiceImpl implements TinService {

	@Autowired
	private TinRepository tinRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private Cleaner cleaner;

	private TinRegistrationProcessRepository processRepository;

	private static final String cacheValue = "Tin";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TinRegistrationProcess", allEntries = true) })
	public Tin addTin(Tin tin, String userId, MultipartFile documents[]) {

		if (tin == null || userId == null || !tin.getUserId().equals(userId)) {

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

			Tin _tin = tinRepository.findByPhone(tin.getPhone());

			if (_tin != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This phone number already exist at here...");

		} catch (Exception e) {

		}

		try {

			List<String> documentsId = new ArrayList<>();

			for (MultipartFile i : documents) {

				try {

					if (i != null && !i.isEmpty()) {

						String fileId = imageService.upload(i);

						documentsId.add(fileId);

					}

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

			}

			tin.setDocuments(documentsId);

		} catch (Exception e) {

			throw new ArithmeticException("Document's are not uploaded...");

		}

		tin = tinRepository.save(tin);

		if (tin == null) {

			throw new ArithmeticException("Tin is not registered....");

		}

		return tin;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TinRegistrationProcess", allEntries = true) })
	public Tin updateTin(Tin tin, String userId, String id, MultipartFile documents[]) {

		if (id == null || tin == null || userId == null || !tin.getUserId().equals(userId)) {

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

			Tin _tin = tinRepository.findByPhone(tin.getPhone());

			if (_tin != null) {

				if (!_tin.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This phone number already exist at here...");

		} catch (Exception e) {

		}

		List<String> documentsId = new ArrayList<>();

		Tin existingTin = null;

		try {

			existingTin = tinRepository.findById(id).get();

			if (existingTin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

		for (String i : existingTin.getDocuments()) {

			try {

				if (tin.getDocuments().contains(i)) {

				} else {

					imageService.delete(i);

				}

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		for (String i : tin.getDocuments()) {

			try {

				if (imageService.attachmentExists(i)) {

					documentsId.add(i);

				}

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		try {

			if (documents != null) {

				for (MultipartFile i : documents) {

					try {

						if (i != null && !i.isEmpty()) {

							String fileId = imageService.upload(i);

							documentsId.add(fileId);

						}

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

				}

			}

			tin.setDocuments(documentsId);

		} catch (Exception e) {

			throw new ArithmeticException("Document's are not uploaded...");

		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", tin.getUserId());

		if (tin.getFatherName() != null) {

			update.set("fatherName", tin.getFatherName());

		}

		if (tin.getMotherName() != null) {

			update.set("motherName", tin.getMotherName());

		}

		if (tin.getDateOfBirth() != null) {

			update.set("dateOfBirth", tin.getDateOfBirth());

		}

		if (tin.getPermanentAdress() != null) {

			update.set("parmanentAdress", tin.getPermanentAdress());

		}

		if (tin.getPresentAdress() != null) {

			update.set("presentAdress", tin.getPresentAdress());

		}

		if (tin.getPhone() != null) {

			update.set("phone", tin.getPhone());

		}

		if (!tin.getDocuments().isEmpty()) {

			update.set("documents", tin.getDocuments());

		}

		mongoTemplate.updateFirst(query, update, Tin.class);

		tin = mongoTemplate.findOne(query, Tin.class);

		return tin;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public TinResponseDTO findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Tin tin = tinRepository.findById(id).get();

			if (tin == null) {

				throw new Exception();

			}

			return getTinResponse(tin);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<TinResponseDTO> findAll() {

		try {

			List<Tin> list = tinRepository.findAll();

			System.out.println(list);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByFullNamePrefix_' + #fullName")
	public List<TinResponseDTO> findByFullNameContainingIgnoreCase(String fullName) {

		if (fullName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByFullNameContainingIgnoreCase(fullName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByFatherNamePrefix_' + #fatherName")
	public List<TinResponseDTO> findByFatherNameContainingIgnoreCase(String fatherName) {

		if (fatherName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByFatherNameContainingIgnoreCase(fatherName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByMotherNamePrefix_' + #motherName")
	public List<TinResponseDTO> findByMotherNameContainingIgnoreCase(String motherName) {

		if (motherName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByMotherNameContainingIgnoreCase(motherName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPhonePrefix_' + #phone")
	public List<TinResponseDTO> findByPhoneContainingIgnoreCase(String phone) {

		if (phone == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByPhoneContainingIgnoreCase(phone);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateOfBirthBefore_' + #dateOfBirth")
	public List<TinResponseDTO> findByDateOfBirthBefore(Instant dateOfBirth) {

		if (dateOfBirth == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByDateOfBirthBefore(dateOfBirth);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateOfBirthAfter_' + #dateOfBirth")
	public List<TinResponseDTO> findByDateOfBirthAfter(Instant dateOfBirth) {

		if (dateOfBirth == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByDateOfBirthAfter(dateOfBirth);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPresentAdress_' + #presentAdress")
	public List<TinResponseDTO> findByPresentAdressContainingIgnoreCase(String presentAdress) {

		if (presentAdress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByPresentAdressContainingIgnoreCase(presentAdress);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPermanentAdress_' + #permanentAdress")
	public List<TinResponseDTO> findByPermanentAdressContainingIgnoreCase(String permanentAdress) {

		if (permanentAdress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByPermanentAdressContainingIgnoreCase(permanentAdress);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #document")
	public List<TinResponseDTO> findByDocumentsContainingIgnoreCase(String document) {

		if (document == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Tin> list = tinRepository.findByDocumentsContainingIgnoreCase(document);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getTinResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "TinRegistrationProcess", allEntries = true) })
	public boolean removeTin(String id, String userId) {

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

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin != null) {

				long count = tinRepository.count();

				cleaner.removeTin(id);

				return tinRepository.count() != count;

			}

		} catch (Exception e) {

		}

		Tin existingTin = null;

		try {

			existingTin = tinRepository.findById(id).get();

			if (existingTin == null) {

				throw new Exception();

			}

			if (!existingTin.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such tin find at here...");

		}

		long count = tinRepository.count();

		cleaner.removeTin(id);

		return tinRepository.count() != count;

	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private TinResponseDTO getTinResponse(Tin tin) {

		List<Tin> list = new ArrayList<>();

		list.add(tin);

		return getTinResponse(list).get(0);

	}

	private List<TinResponseDTO> getTinResponse(List<Tin> list) {

    System.out.println("=== START getTinResponse ===");
    System.out.println("Input list size: " + (list != null ? list.size() : "null"));

    if (list == null || list.isEmpty()) {
        System.out.println("List is null or empty, returning empty list");
        return new ArrayList<>();
    }

    List<TinResponseDTO> responses = new ArrayList<>();

    System.out.println("Creating CompletableFutures...");

    CompletableFuture<List<String>> tinIdListFuture = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("tinIdListFuture: Extracting Tin IDs");
                List<String> tinIds = list.stream().map(Tin::getId).collect(Collectors.toList());
                System.out.println("tinIdListFuture: Found " + tinIds.size() + " tin IDs");
                return tinIds;
            }, executor);

    CompletableFuture<List<String>> usersIdListFuture = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("usersIdListFuture: Extracting User IDs");
                List<String> userIds = list.stream().map(Tin::getUserId).collect(Collectors.toList());
                System.out.println("usersIdListFuture: Found " + userIds.size() + " user IDs");
                return userIds;
            }, executor);

    CompletableFuture<List<TinRegistrationProcess>> tinRegistrationProcessListFuture = tinIdListFuture
            .thenApplyAsync(tinsId -> {
                System.out.println("tinRegistrationProcessListFuture: Processing tin IDs");
                if (tinsId.isEmpty()) {
                    System.out.println("tinRegistrationProcessListFuture: Tin ID list is empty");
                    return new ArrayList<>();
                }
                System.out.println("tinRegistrationProcessListFuture: Fetching processes for " + tinsId.size() + " tin IDs");
                List<TinRegistrationProcess> processes = processRepository.findByTinIdIn(tinsId);
                System.out.println("tinRegistrationProcessListFuture: Found " + processes.size() + " processes");
                return processes;
            }, executor);

    CompletableFuture<List<String>> advocatesIdFuture = tinRegistrationProcessListFuture
            .thenApplyAsync(tinRegistrationProcess -> {
                System.out.println("advocatesIdFuture: Processing registration processes");
                if (tinRegistrationProcess.isEmpty()) {
                    System.out.println("advocatesIdFuture: No registration processes found");
                    return new ArrayList<>();
                }
                List<String> advocateIds = tinRegistrationProcess.stream()
                        .map(TinRegistrationProcess::getAdvocateId)
                        .collect(Collectors.toList());
                System.out.println("advocatesIdFuture: Found " + advocateIds.size() + " advocate IDs");
                return advocateIds;
            }, executor);

    CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesIdFuture.thenApplyAsync(advocatesId -> {
        System.out.println("advocateMapFuture: Processing advocate IDs");
        if (advocatesId.isEmpty()) {
            System.out.println("advocateMapFuture: No advocate IDs found");
            return new HashMap<>();
        }
        System.out.println("advocateMapFuture: Fetching advocates for " + advocatesId.size() + " IDs");
        Map<String, Advocate> advocateMap = advocateRepository.findAllById(advocatesId).stream()
                .collect(Collectors.toMap(Advocate::getId, Function.identity()));
        System.out.println("advocateMapFuture: Found " + advocateMap.size() + " advocates");
        return advocateMap;
    }, executor);

    CompletableFuture<List<Advocate>> advocatesListFuture = advocateMapFuture.thenApplyAsync(advocatesMap -> {
        System.out.println("advocatesListFuture: Converting advocate map to list");
        if (advocatesMap.isEmpty()) {
            System.out.println("advocatesListFuture: Advocate map is empty");
            return new ArrayList<>();
        }
        List<Advocate> advocates = advocatesMap.values().stream().collect(Collectors.toList());
        System.out.println("advocatesListFuture: Found " + advocates.size() + " advocates");
        return advocates;
    }, executor);

    CompletableFuture<List<String>> advocatesUserIdFuture = advocatesListFuture.thenApplyAsync(advocatesList -> {
        System.out.println("advocatesUserIdFuture: Extracting advocate user IDs");
        if (advocatesList.isEmpty()) {
            System.out.println("advocatesUserIdFuture: No advocates found");
            return new ArrayList<>();
        }
        List<String> userIds = advocatesList.stream()
                .map(Advocate::getUserId)
                .collect(Collectors.toList());
        System.out.println("advocatesUserIdFuture: Found " + userIds.size() + " advocate user IDs");
        return userIds;
    }, executor);

    CompletableFuture<List<String>> advocatesUserIdWithRequestedUsersId = usersIdListFuture
            .thenCombine(advocatesUserIdFuture, (usersIdListFuture1, advocatesUserIdFuture1) -> {
                System.out.println("advocatesUserIdWithRequestedUsersId: Combining user IDs");
                System.out.println("  - Requested user IDs count: " + (usersIdListFuture1 != null ? usersIdListFuture1.size() : "null"));
                System.out.println("  - Advocate user IDs count: " + (advocatesUserIdFuture1 != null ? advocatesUserIdFuture1.size() : "null"));

                if (usersIdListFuture1 == null) {
                    System.out.println("  - usersIdListFuture1 is null");
                    return new ArrayList<>();
                }
                if (advocatesUserIdFuture1 == null) {
                    System.out.println("  - advocatesUserIdFuture1 is null");
                    return new ArrayList<>();
                }

                List<String> combined = Stream.concat(advocatesUserIdFuture1.stream(), usersIdListFuture1.stream())
                        .collect(Collectors.toList());
                System.out.println("  - Combined count: " + combined.size());
                return combined;
            });

    CompletableFuture<List<String>> centerAdminsIdFuture = tinRegistrationProcessListFuture
            .thenApplyAsync(tinRegistrationProcess -> {
                System.out.println("centerAdminsIdFuture: Extracting center admin IDs");
                if (tinRegistrationProcess.isEmpty()) {
                    System.out.println("centerAdminsIdFuture: No registration processes found");
                    return new ArrayList<>();
                }
                List<String> centerAdminIds = tinRegistrationProcess.stream()
                        .map(TinRegistrationProcess::getCenterAdminId)
                        .collect(Collectors.toList());
                System.out.println("centerAdminsIdFuture: Found " + centerAdminIds.size() + " center admin IDs");
                return centerAdminIds;
            }, executor);

    CompletableFuture<Map<String, CenterAdmin>> centerAdminMapFuture = centerAdminsIdFuture
            .thenApplyAsync(centerAdminsId -> {
                System.out.println("centerAdminMapFuture: Processing center admin IDs");
                if (centerAdminsId.isEmpty()) {
                    System.out.println("centerAdminMapFuture: No center admin IDs found");
                    return new HashMap<>();
                }
                System.out.println("centerAdminMapFuture: Fetching center admins for " + centerAdminsId.size() + " IDs");
                Map<String, CenterAdmin> centerAdminMap = centerAdminRepository.findAllById(centerAdminsId).stream()
                        .collect(Collectors.toMap(CenterAdmin::getId, Function.identity()));
                System.out.println("centerAdminMapFuture: Found " + centerAdminMap.size() + " center admins");
                return centerAdminMap;
            }, executor);

    CompletableFuture<List<CenterAdmin>> centerAdminListFuture = centerAdminMapFuture
            .thenApplyAsync(centerAdminsMap -> {
                System.out.println("centerAdminListFuture: Converting center admin map to list");
                if (centerAdminsMap.isEmpty()) {
                    System.out.println("centerAdminListFuture: Center admin map is empty");
                    return new ArrayList<>();
                }
                List<CenterAdmin> centerAdmins = centerAdminsMap.values().stream().collect(Collectors.toList());
                System.out.println("centerAdminListFuture: Found " + centerAdmins.size() + " center admins");
                return centerAdmins;
            }, executor);

    CompletableFuture<List<String>> centerAdminsUserIdFuture = centerAdminListFuture
            .thenApplyAsync(centerAdmins -> {
                System.out.println("centerAdminsUserIdFuture: Extracting center admin user IDs");
                if (centerAdmins.isEmpty()) {
                    System.out.println("centerAdminsUserIdFuture: No center admins found");
                    return new ArrayList<>();
                }
                List<String> userIds = centerAdmins.stream()
                        .map(CenterAdmin::getUserId)
                        .collect(Collectors.toList());
                System.out.println("centerAdminsUserIdFuture: Found " + userIds.size() + " center admin user IDs");
                return userIds;
            }, executor);

    CompletableFuture<List<String>> allUsersIdFuture = advocatesUserIdWithRequestedUsersId
            .thenCombine(centerAdminsUserIdFuture, (list1, list2) -> {
                System.out.println("allUsersIdFuture: Combining all user IDs");
                System.out.println("  - List1 (advocates + requested) count: " + (list1 != null ? list1.size() : "null"));
                System.out.println("  - List2 (center admins) count: " + (list2 != null ? list2.size() : "null"));

                if (list1 == null && list2 == null) {
                    System.out.println("  - Both lists are null");
                    return new ArrayList<>();
                }
                if (list1 == null) {
                    System.out.println("  - List1 is null, returning list2");
                    return list2 != null ? list2 : new ArrayList<>();
                }
                if (list2 == null) {
                    System.out.println("  - List2 is null, returning list1");
                    return list1;
                }

                List<String> combined = Stream.concat(list1.stream(), list2.stream())
                        .collect(Collectors.toList());
                System.out.println("  - Combined count: " + combined.size());
                return combined;
            });

    CompletableFuture<Map<String, User>> userMapFuture = allUsersIdFuture.thenApplyAsync(usersId -> {
        System.out.println("userMapFuture: Fetching users");
        if (usersId.isEmpty()) {
            System.out.println("userMapFuture: No user IDs found");
            return new HashMap<>();
        }
        System.out.println("userMapFuture: Fetching " + usersId.size() + " users");
        Map<String, User> userMap = userRepository.findAllById(usersId).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        System.out.println("userMapFuture: Found " + userMap.size() + " users");
        return userMap;
    }, executor);

    CompletableFuture<Map<String, TinRegistrationProcess>> processMapFuture = tinRegistrationProcessListFuture
            .thenApplyAsync(processes -> {
                System.out.println("processMapFuture: Creating process map");
                if (processes.isEmpty()) {
                    System.out.println("processMapFuture: No processes found");
                    return new HashMap<>();
                }
                Map<String, TinRegistrationProcess> processMap = processes.stream()
                        .collect(Collectors.toMap(TinRegistrationProcess::getTinId, Function.identity()));
                System.out.println("processMapFuture: Created map with " + processMap.size() + " entries");
                return processMap;
            }, executor);

    System.out.println("Waiting for all CompletableFutures to complete...");
    try {
        CompletableFuture.allOf(tinIdListFuture, usersIdListFuture, tinRegistrationProcessListFuture, advocatesIdFuture,
                centerAdminsIdFuture, userMapFuture, centerAdminMapFuture, processMapFuture, advocateMapFuture,
                advocatesListFuture, advocatesUserIdFuture, advocatesUserIdWithRequestedUsersId, centerAdminListFuture,
                centerAdminsUserIdFuture, allUsersIdFuture).join();
        System.out.println("All CompletableFutures completed successfully");
    } catch (Exception e) {
        System.err.println("ERROR: CompletableFuture.allOf failed: " + e.getMessage());
        e.printStackTrace();
        return new ArrayList<>();
    }

    System.out.println("Getting results from futures...");
    Map<String, User> userMap = userMapFuture.join();
    System.out.println("  - userMap size: " + userMap.size());

    Map<String, Advocate> advocateMap = advocateMapFuture.join();
    System.out.println("  - advocateMap size: " + advocateMap.size());

    Map<String, CenterAdmin> centerAdminMap = centerAdminMapFuture.join();
    System.out.println("  - centerAdminMap size: " + centerAdminMap.size());

    Map<String, TinRegistrationProcess> processMap = processMapFuture.join();
    System.out.println("  - processMap size: " + processMap.size());

    System.out.println("Building response DTOs for " + list.size() + " tins...");
    int processedCount = 0;
    int failedCount = 0;

    for (Tin tin : list) {
        try {
            System.out.println("  Processing tin ID: " + tin.getId());

            TinResponseDTO response = new TinResponseDTO();

            response.setId(tin.getId());
            response.setUserId(tin.getUserId());
            response.setDateOfBirth(tin.getDateOfBirth());
            response.setDocuments(tin.getDocuments());
            response.setFatherName(tin.getFatherName());
            response.setMotherName(tin.getMotherName());
            response.setFullName(tin.getFullName());
            response.setPermanentAdress(tin.getPermanentAdress());
            response.setPresentAdress(tin.getPresentAdress());
            response.setPhone(tin.getPhone());

            // Set user name
            try {
                User user = userMap.get(tin.getUserId());
                if (user != null) {
                    response.setUserName(user.getFullName() != null ? user.getFullName() : user.getName());
                    System.out.println("    - User name set to: " + response.getUserName());
                } else {
                    System.out.println("    - WARNING: User not found for userId: " + tin.getUserId());
                }
            } catch (Exception e) {
                System.err.println("    - ERROR setting user name: " + e.getMessage());
                e.printStackTrace();
            }

            // Set registration process
            try {
                TinRegistrationProcess process = processMap.get(tin.getId());
                System.out.println("    - Process found: " + (process != null ? "Yes" : "No"));

                if (process != null) {
                    TinRegistrationProcessDTO processResponse = new TinRegistrationProcessDTO();

                    processResponse.setId(process.getId());
                    processResponse.setTinId(process.getTinId());
                    processResponse.setAdvocateId(process.getAdvocateId());
                    processResponse.setCenterAdminId(process.getCenterAdminId());
                    processResponse.setRequestedUserId(tin.getUserId());

                    // Set requested user name
                    try {
                        User requestedUser = userMap.get(tin.getUserId());
                        if (requestedUser != null) {
                            processResponse.setRequestedUserName(
                                    requestedUser.getFullName() != null ? requestedUser.getFullName()
                                            : requestedUser.getName());
                            System.out.println("    - Requested user name: " + processResponse.getRequestedUserName());
                        }
                    } catch (Exception e) {
                        System.err.println("    - ERROR setting requested user name: " + e.getMessage());
                    }

                    // Set advocate name
                    try {
                        Advocate advocate = advocateMap.get(process.getAdvocateId());
                        if (advocate != null) {
                            User advocateUser = userMap.get(advocate.getUserId());
                            if (advocateUser != null) {
                                processResponse.setAdvocateName(
                                        advocateUser.getFullName() != null ? advocateUser.getFullName()
                                                : advocateUser.getName());
                                System.out.println("    - Advocate name: " + processResponse.getAdvocateName());
                            } else {
                                System.out.println("    - WARNING: Advocate user not found for userId: " + advocate.getUserId());
                            }
                        } else {
                            System.out.println("    - WARNING: Advocate not found for advocateId: " + process.getAdvocateId());
                        }
                    } catch (Exception e) {
                        System.err.println("    - ERROR setting advocate name: " + e.getMessage());
                    }

                    // Set center admin name
                    try {
                        CenterAdmin centerAdmin = centerAdminMap.get(process.getCenterAdminId());
                        if (centerAdmin != null) {
                            User adminUser = userMap.get(centerAdmin.getUserId());
                            if (adminUser != null) {
                                processResponse.setCenterAdminName(
                                        adminUser.getFullName() != null ? adminUser.getFullName()
                                                : adminUser.getName());
                                System.out.println("    - Center admin name: " + processResponse.getCenterAdminName());
                            } else {
                                System.out.println("    - WARNING: Center admin user not found for userId: " + centerAdmin.getUserId());
                            }
                        } else {
                            System.out.println("    - WARNING: Center admin not found for centerAdminId: " + process.getCenterAdminId());
                        }
                    } catch (Exception e) {
                        System.err.println("    - ERROR setting center admin name: " + e.getMessage());
                    }

                    processResponse.setTin(tin);
                    response.setRegistrationProcess(processResponse);
                    System.out.println("    - Registration process set successfully");
                } else {
                    System.out.println("    - No registration process found for this tin");
                }

            } catch (Exception e) {
                System.err.println("    - ERROR processing registration process: " + e.getMessage());
                e.printStackTrace();
            }

            responses.add(response);
            processedCount++;
            System.out.println("  - Successfully processed tin ID: " + tin.getId());

        } catch (Exception e) {
            failedCount++;
            System.err.println("  - ERROR processing tin ID " + tin.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    System.out.println("=== END getTinResponse ===");
    System.out.println("Total tins processed: " + list.size());
    System.out.println("Successfully processed: " + processedCount);
    System.out.println("Failed to process: " + failedCount);
    System.out.println("Response size: " + responses.size());

    return responses;
}
}
