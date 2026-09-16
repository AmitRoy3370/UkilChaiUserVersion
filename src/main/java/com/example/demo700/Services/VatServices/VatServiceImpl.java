package com.example.demo700.Services.VatServices;

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
import com.example.demo700.DTOFiles.VatRegistrationProcessResponseDTO;
import com.example.demo700.DTOFiles.VatResponseDTO;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.VatModels.Vat;
import com.example.demo700.Model.VatModels.VatRegistrationProcess;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Repositories.VatRepositories.VatRegistrationProcessRepository;
import com.example.demo700.Repositories.VatRepositories.VatRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class VatServiceImpl implements VatService {

	@Autowired
	private VatRepository vatRepository;

	@Autowired
	private VatRegistrationProcessRepository processRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),

	})
	public Vat addVat(Vat vat, String userId, MultipartFile[] documents) {

		if (vat == null || userId == null || !vat.getUserId().equals(userId)) {

			throw new NullPointerException("False request....");

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

			List<Vat> list = vatRepository.findByTinNoContainingIgnoreCase(vat.getTinNo());

			if (!list.isEmpty()) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This tin no already exist at here...");

		} catch (Exception e) {

		}

		try {

			List<Vat> list = vatRepository.findByTradeLicenseNoContainingIgnoreCase(vat.getTradeLicenseNo());

			if (!list.isEmpty()) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trade license no already exist...");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : vat.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		try {

			for (MultipartFile i : documents) {

				try {

					if (i != null && !i.isEmpty()) {

						String attachmentId = imageService.upload(i);

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		vat.setDocuments(attachmentsId);

		vat = vatRepository.save(vat);

		return vat;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),

	})
	public Vat updateVat(String id, Vat vat, String userId, MultipartFile[] documents) {

		if (id == null || vat == null || userId == null || !vat.getUserId().equals(userId)) {

			throw new NullPointerException("False request....");

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

		Vat _vat = null;

		try {

			_vat = vatRepository.findById(id).get();

			if (_vat == null) {

				throw new Exception();

			}

			if (!_vat.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		try {

			List<Vat> list = vatRepository.findByTinNoContainingIgnoreCase(vat.getTinNo());

			if (!list.isEmpty()) {

				if (list.size() > 1 || !list.get(0).getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This tin no already exist at here...");

		} catch (Exception e) {

		}

		try {

			List<Vat> list = vatRepository.findByTradeLicenseNoContainingIgnoreCase(vat.getTradeLicenseNo());

			if (!list.isEmpty()) {

				if (list.size() > 1 || !list.get(0).getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This trade license no already exist...");

		} catch (Exception e) {

		}

		List<String> attachmentsId = new ArrayList<>();

		try {

			for (String i : vat.getDocuments()) {

				try {

					if (imageService.attachmentExists(i)) {

						attachmentsId.add(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		try {

			for (MultipartFile i : documents) {

				try {

					if (i != null && !i.isEmpty()) {

						String attachmentId = imageService.upload(i);

						attachmentsId.add(attachmentId);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		try {

			for (String i : _vat.getDocuments()) {

				try {

					if (!vat.getDocuments().contains(i)) {

						imageService.delete(i);

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		vat.setDocuments(attachmentsId);

		vat.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", id);
		update.set("userId", vat.getUserId());
		update.set("adress", vat.getAdress());
		update.set("tinNo", vat.getTinNo());
		update.set("buisnessName", vat.getBuisnessName());
		update.set("tradeLicenseNo", vat.getTradeLicenseNo());
		update.set("annualTurnOver", vat.getAnnualTurnOver());
		update.set("mainProduct", vat.getMainProduct());
		update.set("natureOfBuisness", vat.getNatureOfBuisness());
		update.set("numberOfBuisness", vat.getNatureOfBuisness());
		update.set("numberOfEmployee", vat.getNumberOfBuisness());
		update.set("documents", vat.getDocuments());

		mongoTemplate.updateFirst(query, update, Vat.class);

		vat = mongoTemplate.findOne(query, Vat.class);

		return vat;
	}

	@Override
	@Cacheable(value = "Vat", key = "'findById_' + #id")
	public VatResponseDTO findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Vat vat = vatRepository.findById(id).get();

			if (vat == null) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

	}

	@Override
	@Cacheable(value = "Vat", key = "'findAll'")
	public List<VatResponseDTO> findAll() {

		try {

			List<Vat> vat = vatRepository.findAll();

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

	}

	@Override
	@Cacheable(value = "Vat", key = "'findByUserId_' + #userId")
	public List<VatResponseDTO> findByUserId(String userId) {

		if(userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByUserId(userId);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByAdress_' + #adress")
	public List<VatResponseDTO> findByAdressContainingIgnoreCase(String adress) {

		if(adress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByAdressContainingIgnoreCase(adress);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByTinNo_' + #tinNo")
	public List<VatResponseDTO> findByTinNoContainingIgnoreCase(String tinNo) {

		if(tinNo == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByTinNoContainingIgnoreCase(tinNo);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByBuisnessName_' + #buisnessName")
	public List<VatResponseDTO> findByBuisnessNameContainingIgnoreCase(String buisnessName) {

		if(buisnessName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByBuisnessNameContainingIgnoreCase(buisnessName);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByTradeLicenseNo_' + #tradeLicenseNo")
	public List<VatResponseDTO> findByTradeLicenseNoContainingIgnoreCase(String tradeLicenseNo) {

		if(tradeLicenseNo == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByTradeLicenseNoContainingIgnoreCase(tradeLicenseNo);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByAnnualTurnOver_' + #annualTurnOver")
	public List<VatResponseDTO> findByAnnualTurnOverContainingIgnoreCase(String annualTurnOver) {

		if(annualTurnOver == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByAnnualTurnOverContainingIgnoreCase(annualTurnOver);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByMainProduct_' + #mainProduct")
	public List<VatResponseDTO> findByMainProductContainingIgnoreCase(String mainProduct) {

		if(mainProduct == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByMainProductContainingIgnoreCase(mainProduct);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByNatureOfBuisness_' + #natureOfBuisness")
	public List<VatResponseDTO> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness) {

		if(natureOfBuisness == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByNatureOfBuisnessContainingIgnoreCase(natureOfBuisness);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByNumberOfBuisnessGreaterThanEqual_' + #numberOfBuisness")
	public List<VatResponseDTO> findByNumberOfBuisnessGreaterThanEqual(int numberOfBuisness) {

		try {

			List<Vat> vat = vatRepository.findByNumberOfBuisnessGreaterThanEqual(numberOfBuisness);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByNumberOfBuisnessLessThanEqual_' + #numberOfBuisness")
	public List<VatResponseDTO> findByNumberOfBuisnessLessThanEqual(int numberOfBuisness) {
		try {

			List<Vat> vat = vatRepository.findByNumberOfBuisnessLessThanEqual(numberOfBuisness);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByNumberOfEmployeeGreaterThanEqual_' + #numberOfEmployee")
	public List<VatResponseDTO> findByNumberOfEmployeeGreaterThanEqual(int numberOfEmployee) {
		try {

			List<Vat> vat = vatRepository.findByNumberOfEmployeeGreaterThanEqual(numberOfEmployee);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByNumberOfEmployeeLessThanEqual_' + #numberOfEmployee")
	public List<VatResponseDTO> findByNumberOfEmployeeLessThanEqual(int numberOfEmployee) {
		try {

			List<Vat> vat = vatRepository.findByNumberOfEmployeeLessThanEqual(numberOfEmployee);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Cacheable(value = "Vat", key = "'findByDocument_' + #documents")
	public List<VatResponseDTO> findByDocuments(String documents) {

		if(documents == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Vat> vat = vatRepository.findByDocuments(documents);

			if (vat == null || vat.isEmpty()) {

				throw new NullPointerException();

			}

			return getVatResponse(vat);

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "Vat", allEntries = true),
			@CacheEvict(value = "VatRegistrationProcess", allEntries = true),
			@CacheEvict(value = "VatPayment", allEntries = true),

	})
	public boolean deleteVat(String id, String userId) {

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

			CenterAdmin admin = centerAdminRepository.findById(id).get();

			if (admin == null) {

				throw new Exception();

			}

			long count = vatRepository.count();

			cleaner.removeVat(id);

			return count != vatRepository.count();

		} catch (Exception e) {

		}

		Vat _vat = null;

		try {

			_vat = vatRepository.findById(id).get();

			if (_vat == null) {

				throw new Exception();

			}

			if (!_vat.getUserId().equals(id)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such vat find at here...");

		}

		long count = vatRepository.count();

		cleaner.removeVat(id);

		return count != vatRepository.count();
	}

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private VatResponseDTO getVatResponse(Vat vat) {

		List<Vat> list = new ArrayList<>();

		return getVatResponse(list).get(0);

	}

	private List<VatResponseDTO> getVatResponse(List<Vat> list) {

		List<VatResponseDTO> responses = new ArrayList<>();

		CompletableFuture<List<String>> vatsIdFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Vat::getId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, VatRegistrationProcess>> processMapFuture = vatsIdFuture
				.thenApplyAsync(vatsId -> {

					if (vatsId.isEmpty()) {

						return new HashMap<>();

					}

					return processRepository.findByVatIdIn(vatsId).stream()
							.collect(Collectors.toMap(VatRegistrationProcess::getVatId, Function.identity()));

				}, executor);

		CompletableFuture<List<VatRegistrationProcess>> processListFuture = processMapFuture
				.thenApplyAsync(processMap -> {

					if (processMap.isEmpty()) {

						return new ArrayList<>();

					}

					return processMap.values().stream().collect(Collectors.toList());

				}, executor);

		CompletableFuture<List<String>> advocatesIdFuture = processListFuture.thenApplyAsync(processes -> {

			if (processes.isEmpty()) {

				return new ArrayList<>();

			}

			return processes.stream().map(VatRegistrationProcess::getAdvocateId).distinct()
					.collect(Collectors.toList());

		}, executor);

		CompletableFuture<Map<String, Advocate>> advocateMapFuture = advocatesIdFuture.thenApplyAsync(advocatesId -> {

			if (advocatesId.isEmpty()) {

				return new HashMap<>();

			}

			return advocateRepository.findAllById(advocatesId).stream()
					.collect(Collectors.toMap(Advocate::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<Advocate>> advocateListFuture = advocateMapFuture.thenApplyAsync(advocatesMap -> {

			if (advocatesMap.isEmpty()) {

				return new ArrayList<>();

			}

			return advocatesMap.values().stream().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> advocatesUserIdFuture = advocateListFuture.thenApplyAsync(advocates -> {

			if (advocates.isEmpty()) {

				return new ArrayList<>();

			}

			return advocates.stream().map(Advocate::getUserId).distinct().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminsUserIdFuture = processListFuture.thenApplyAsync(processes -> {

			if (processes.isEmpty()) {

				return new ArrayList<>();

			}

			return processes.stream().map(VatRegistrationProcess::getUserId).distinct().collect(Collectors.toList());

		}, executor);

		CompletableFuture<List<String>> centerAdminUserIdAndAdvocateUserId = centerAdminsUserIdFuture.thenCombine(
				advocatesUserIdFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<List<String>> requestedUserIdFuture = CompletableFuture
				.supplyAsync(() -> list.stream().map(Vat::getUserId).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<List<String>> allUserIdFuture = centerAdminUserIdAndAdvocateUserId.thenCombine(
				requestedUserIdFuture, (list1, list2) -> Stream.concat(list1.stream(), list2.stream()).toList());

		CompletableFuture<Map<String, User>> userMapFuture = allUserIdFuture.thenApplyAsync(allUsersId -> {

			if (allUsersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(allUsersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture
				.allOf(vatsIdFuture, processMapFuture, processListFuture, advocatesIdFuture, advocateMapFuture,
						advocateListFuture, advocatesUserIdFuture, centerAdminsUserIdFuture,
						centerAdminUserIdAndAdvocateUserId, requestedUserIdFuture, allUserIdFuture, userMapFuture)
				.join();

		Map<String, VatRegistrationProcess> processMap = processMapFuture.join();
		Map<String, Advocate> advocateMap = advocateMapFuture.join();
		Map<String, User> userMap = userMapFuture.join();

		for (Vat vat : list) {

			try {

				VatResponseDTO response = new VatResponseDTO();

				response.setId(vat.getId());
				response.setUserId(vat.getUserId());
				response.setTinNo(vat.getTinNo());
				response.setAdress(vat.getAdress());
				response.setAnnualTurnOver(vat.getAnnualTurnOver());
				response.setBuisnessName(vat.getBuisnessName());
				response.setTradeLicenseNo(vat.getTradeLicenseNo());
				response.setMainProduct(vat.getMainProduct());
				response.setNumberOfBuisness(vat.getNumberOfBuisness());
				response.setNatureOfBuisness(vat.getNatureOfBuisness());
				response.setDocuments(vat.getDocuments());
				response.setNumberOfEmployee(vat.getNumberOfEmployee());

				try {

					response.setUserName(
							userMap.get(vat.getUserId()).getFullName() == null ? userMap.get(vat.getUserId()).getName()
									: userMap.get(vat.getUserId()).getFullName());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					VatRegistrationProcessResponseDTO process = new VatRegistrationProcessResponseDTO();

					VatRegistrationProcess registrationProcess = processMap.get(vat.getId());

					process.setId(registrationProcess.getId());
					process.setUserId(registrationProcess.getUserId());
					process.setAdvocateId(registrationProcess.getAdvocateId());
					process.setStatus(registrationProcess.isStatus());
					process.setSteps(registrationProcess.getSteps());
					process.setVatId(vat.getId());
					process.setVat(vat);

					try {

						process.setUserName(userMap.get(registrationProcess.getUserId()).getFullName() == null
								? userMap.get(registrationProcess.getUserId()).getName()
								: userMap.get(registrationProcess.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					try {

						process.setAdvocateName(
								userMap.get(advocateMap.get(registrationProcess.getAdvocateId()).getUserId())
										.getFullName() == null ? userMap
												.get(advocateMap.get(registrationProcess.getAdvocateId()).getUserId())
												.getName()
												: userMap.get(advocateMap.get(registrationProcess.getAdvocateId())
														.getUserId()).getFullName());

					} catch (Exception e) {

						System.out.println(e.getMessage());

					}

					response.setVatRegistrationProcessResponseDTO(process);

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
