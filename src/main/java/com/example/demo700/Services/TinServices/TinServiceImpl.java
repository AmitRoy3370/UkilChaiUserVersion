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

		List<TinResponseDTO> responses = new ArrayList<>();

		CompletableFuture<List<String>> tinIdListFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Tin::getId).collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> usersIdListFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Tin::getUserId).collect(Collectors.toList()), executor);

		CompletableFuture<List<TinRegistrationProcess>> tinRegistrationProcessListFuture = tinIdListFuture
				.thenApplyAsync(tinsId -> {

					if (tinsId.isEmpty()) {

						return new ArrayList<>();

					}

					return processRepository.findByTinIdIn(tinsId);

				}, executor);

		CompletableFuture<List<String>> advocatesIdFuture = tinRegistrationProcessListFuture
				.thenApplyAsync(tinRegistrationProcess -> {

					if (tinRegistrationProcess.isEmpty()) {

						return new ArrayList<>();

					}

					return tinRegistrationProcess.stream().map(TinRegistrationProcess::getAdvocateId)
							.collect(Collectors.toList());

				}, executor);

		CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesIdFuture.thenApplyAsync(advocatesId -> {

			if (advocatesId.isEmpty()) {

				return new HashMap<>();

			}

			return advocateRepository.findAllById(advocatesId).stream()
					.collect(Collectors.toMap(Advocate::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<Advocate>> advocatesListFuture = advocateMapFuture.thenApplyAsync(advocatesMap -> {

			if (advocatesMap.isEmpty()) {

				return new ArrayList<>();

			}

			List<Advocate> advocates = advocatesMap.values().stream().collect(Collectors.toList());

			return advocates;

		}, executor);

		CompletableFuture<List<String>> advocatesUserIdFuture = advocatesListFuture.thenApplyAsync(advocatesList -> {

			if (advocatesList.isEmpty()) {

				return new ArrayList<>();

			}

			List<String> usersId = advocatesList.stream().map(Advocate::getUserId).collect(Collectors.toList());

			return usersId;

		}, executor);

		CompletableFuture<List<String>> advocatesUserIdWithRequestedUsersId = usersIdListFuture
				.thenCombine(advocatesUserIdFuture, (usersIdListFuture1, advocatesUserIdFuture1) -> Stream
						.concat(advocatesUserIdFuture1.stream(), usersIdListFuture1.stream()).toList());

		CompletableFuture<List<String>> centerAdminsIdFuture = tinRegistrationProcessListFuture
				.thenApplyAsync(tinRegistrationProcess -> {

					if (tinRegistrationProcess.isEmpty()) {

						return new ArrayList<>();

					}

					return tinRegistrationProcess.stream().map(TinRegistrationProcess::getCenterAdminId)
							.collect(Collectors.toList());

				}, executor);

		CompletableFuture<Map<String, CenterAdmin>> centerAdminMapFuture = centerAdminsIdFuture
				.thenApplyAsync(centerAdminsId -> {

					if (centerAdminsId.isEmpty()) {

						return new HashMap<>();

					}

					return centerAdminRepository.findAllById(centerAdminsId).stream()
							.collect(Collectors.toMap(CenterAdmin::getId, Function.identity()));

				}, executor);

		CompletableFuture<List<CenterAdmin>> centerAdminListFuture = centerAdminMapFuture
				.thenApplyAsync(centerAdminsMap -> {

					if (centerAdminsMap.isEmpty()) {

						return new ArrayList<>();

					}

					return centerAdminsMap.values().stream().collect(Collectors.toList());

				}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdFuture = centerAdminListFuture
				.thenApplyAsync(centerAdmins -> {

					if (centerAdmins.isEmpty()) {

						return new ArrayList<>();

					}

					return centerAdmins.stream().map(CenterAdmin::getUserId).collect(Collectors.toList());

				}, executor);

		CompletableFuture<List<String>> allUsersIdFuture = advocatesUserIdWithRequestedUsersId.thenCombine(
				centerAdminsUserIdFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUsersIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, TinRegistrationProcess>> processMapFuture = tinRegistrationProcessListFuture
				.thenApplyAsync(processes -> {

					if (processes.isEmpty()) {

						return new HashMap<>();

					}

					return processes.stream()
							.collect(Collectors.toMap(TinRegistrationProcess::getTinId, Function.identity()));

				}, executor);

		CompletableFuture.allOf(tinIdListFuture, usersIdListFuture, tinRegistrationProcessListFuture, advocatesIdFuture,
				centerAdminsIdFuture, userMapFuture, centerAdminMapFuture, processMapFuture, advocateMapFuture,
				advocatesListFuture, advocatesUserIdFuture, advocatesUserIdWithRequestedUsersId, centerAdminListFuture,
				centerAdminsUserIdFuture, allUsersIdFuture).join();

		Map<String, User> userMap = userMapFuture.join();
		Map<String, Advocate> advocateMap = advocateMapFuture.join();
		Map<String, CenterAdmin> centerAdminMap = centerAdminMapFuture.join();
		Map<String, TinRegistrationProcess> processMap = processMapFuture.join();

		for (Tin tin : list) {

			try {

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

				try {

					response.setUserName(
							userMap.get(tin.getUserId()).getFullName() == null ? userMap.get(tin.getUserId()).getName()
									: userMap.get(tin.getUserId()).getFullName());

				} catch (Exception e) {

				}

				try {

					TinRegistrationProcess process = processMap.getOrDefault(tin.getId(), null);

					if (process == null) {

						throw new Exception();

					}

					TinRegistrationProcessDTO processResponse = new TinRegistrationProcessDTO();

					processResponse.setId(process.getId());
					processResponse.setTinId(process.getTinId());
					processResponse.setAdvocateId(process.getAdvocateId());
					processResponse.setCenterAdminId(process.getCenterAdminId());
					processResponse.setRequestedUserId(tin.getUserId());
					processResponse.setRequestedUserName(
							userMap.get(tin.getUserId()).getFullName() == null ? userMap.get(tin.getUserId()).getName()
									: userMap.get(tin.getUserId()).getFullName());

					Advocate advocate = advocateMap.get(process.getAdvocateId());

					processResponse.setAdvocateName(userMap.get(advocate.getUserId()).getFullName() == null
							? userMap.get(advocate.getUserId()).getName()
							: userMap.get(advocate.getUserId()).getFullName());

					CenterAdmin centerAdmin = centerAdminMap.get(process.getCenterAdminId());

					processResponse.setCenterAdminName(userMap.get(centerAdmin.getUserId()).getFullName() == null
							? userMap.get(centerAdmin.getUserId()).getName()
							: userMap.get(centerAdmin.getUserId()).getFullName());

					processResponse.setTin(tin);

					response.setRegistrationProcess(processResponse);

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

		}

		return responses;

	}

}
