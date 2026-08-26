package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.example.demo700.DTOFiles.ShareholderResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.Shareholder;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.ShareholderRepository;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class ShareholderServiceImpl implements ShareholderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserContactInfoRepository contactRepository;

	@Autowired
	private UserLocationRepository locationRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ShareholderRepository holderRepository;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String cacheValue = "ShareHolder";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true), @CacheEvict(value = "Capital", allEntries = true),
			@CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public Shareholder addShareholder(Shareholder holder, String userId, MultipartFile nid, MultipartFile tin) {

		if (holder == null || userId == null || !holder.getUserId().equals(userId)) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			Shareholder shareHolder = holderRepository.findByUserId(user.getId());

			if (shareHolder != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already added as a shareholder....");

		} catch (Exception e) {

		}

		try {

			if (!holder.getSharePercentage().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No share holder can share percentage in time of creation...");

		}

		try {

			if (nid != null && !nid.isEmpty()) {

				String fileName = nid.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(nid);

				if (nidId == null) {

					throw new ArithmeticException();

				}

				holder.setNid(nidId);

			}

		} catch (Exception e) {

			throw new ArithmeticException("NID card is not uploaded...");

		}

		try {

			if (tin != null && !tin.isEmpty()) {

				String fileName = tin.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(tin);

				if (nidId == null) {

					throw new ArithmeticException();

				}

				holder.setTin(nidId);

			}

		} catch (Exception e) {

			throw new ArithmeticException("NID card is not uploaded...");

		}

		holder = holderRepository.save(holder);

		return holder;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true), @CacheEvict(value = "Capital", allEntries = true),
			@CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public Shareholder updateShareholder(Shareholder holder, String userId, String id, MultipartFile nid,
			MultipartFile tin) {

		if (holder == null || userId == null || !holder.getUserId().equals(userId)) {

			throw new NullPointerException("False request...");

		}

		try {

			Shareholder shareHolder = holderRepository.findById(id).get();

			if (shareHolder == null) {

				throw new Exception();

			}

			if (!shareHolder.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			Shareholder shareHolder = holderRepository.findByUserId(user.getId());

			if (shareHolder != null) {

				if (!shareHolder.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already added as a shareholder....");

		} catch (Exception e) {

		}

		try {

			if (!holder.getSharePercentage().isEmpty()) {

				Set<String> companies = holder.getSharePercentage().keySet();

				List<String> list = new ArrayList<>(companies);

				List<CompanyInformation> informations = companyRepository.findAllById(list);

				if (informations == null || informations.isEmpty() || list.size() != informations.size()) {

					throw new Exception();

				}

				for (CompanyInformation i : informations) {

					if (!i.getShareHolders().contains(id)) {

						throw new Exception();

					}

				}

				for (String i : list) {

					List<Double> percentages = holder.getSharePercentage().get(i);

					for (double j : percentages) {

						if (j <= 0.0) {

							throw new Exception();

						}

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Shared company percentage is not valid...");

		}

		try {

			if (nid != null && !nid.isEmpty()) {

				String fileName = nid.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(nid);

				if (nidId == null) {

					throw new ArithmeticException();

				}

				holder.setNid(nidId);

			} else if (holder.getNid() != null) {

				if (!imageService.attachmentExists(holder.getNid())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("NID card is not uploaded...");

		}

		try {

			if (tin != null && !tin.isEmpty()) {

				String fileName = tin.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(tin);

				if (nidId == null) {

					throw new ArithmeticException();

				}

				holder.setTin(nidId);

			} else if (holder.getTin() != null) {

				if (!imageService.attachmentExists(holder.getTin())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("NID card is not uploaded...");

		}

		holder.setId(id);

		Query query = new Query(Criteria.where("_id").is(holder.getId()));

		Update update = new Update();

		update.set("id", holder.getId());
		update.set("userId", holder.getUserId());
		update.set("nid", holder.getNid());
		update.set("tin", holder.getTin());
		update.set("sharePercentage", holder.getSharePercentage());

		mongoTemplate.updateFirst(query, update, Shareholder.class);

		return mongoTemplate.findOne(query, Shareholder.class);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true), @CacheEvict(value = "Capital", allEntries = true),
			@CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public Shareholder shareProfit(String companyId, double percentage, String holderId, String userId) {

		if (companyId == null || percentage <= 0.0 || holderId == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		Shareholder holder = null;

		try {

			holder = holderRepository.findById(holderId).get();

			if (holder == null) {

				throw new NoSuchElementException();

			}

			if (!holder.getUserId().equals(userId)) {

				throw new NoSuchElementException();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here....");

		}

		try {

			Shareholder shareHolder = holderRepository.findByUserId(userId);

			if (shareHolder == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("You are not registered as a shareholder....");

		}

		try {

			CompanyInformation information = companyRepository.findById(companyId).get();

			if (information == null) {

				throw new Exception();

			}

			if (!information.getShareHolders().contains(holderId)) {

				throw new Exception();

			}

		} catch (Exception e) {

		}

		try {

			Map<String, List<Double>> map = holder.getSharePercentage();

			if (map == null || map.isEmpty()) {

				map = new HashMap<>();

			}

			if (map.containsKey(companyId)) {

				map.get(companyId).add(percentage);

			} else {

				map.put(companyId, List.of(percentage));

			}

			holder.setSharePercentage(map);

		} catch (Exception e) {

			throw new ArithmeticException("Share is not shared in this company");

		}

		Query query = new Query(Criteria.where("_id").is(holder.getId()));

		Update update = new Update();

		update.set("id", holder.getId());
		update.set("userId", holder.getUserId());
		update.set("nid", holder.getNid());
		update.set("tin", holder.getTin());
		update.set("sharePercentage", holder.getSharePercentage());

		mongoTemplate.updateFirst(query, update, Shareholder.class);

		return holder = mongoTemplate.findOne(query, Shareholder.class);

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public ShareholderResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Shareholder holder = holderRepository.findById(id).get();

			if (holder == null) {

				throw new Exception();

			}

			return getShareholderResponse(holder);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<ShareholderResponse> findAll() {

		try {

			List<Shareholder> list = holderRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public ShareholderResponse findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Shareholder holder = holderRepository.findByUserId(userId);

			if (holder == null) {

				throw new Exception();

			}

			return getShareholderResponse(holder);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNid_' + #nid")
	public List<ShareholderResponse> findByNid(String nid) {

		if (nid == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByNid(nid);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByTin_' + #tin")
	public List<ShareholderResponse> findByTin(String tin) {

		if (tin == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByTin(tin);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<ShareholderResponse> findByShareCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyId(companyId);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentage_' + #companyId + '_' + #percentage")
	public List<ShareholderResponse> findByShareCompanyIdAndPercentage(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentage(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageGTE_' + #companyId + '_' + #percentage")
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageGte(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentageGte(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageLTE_' + #companyId + '_' + #percentage")
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageLte(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentageLte(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageBetween_' + #companyId + '_' + #minPercentage + '_' + #maxPercentage")
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageBetween(String companyId, Double minPercentage,
			Double maxPercentage) {

		if (companyId == null || minPercentage <= 0.0 || maxPercentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentageBetween(companyId, minPercentage,
					maxPercentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getShareholderResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true), @CacheEvict(value = "Capital", allEntries = true),
			@CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public boolean removeShareholder(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			long count = holderRepository.count();

			cleaner.removeShareholder(id);

			return count != holderRepository.count();

		} catch (Exception e) {

			System.out.println(e);

		}

		try {

			Shareholder shareHolder = holderRepository.findByUserId(user.getId());

			if (shareHolder != null) {

				if (!shareHolder.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already added as a shareholder....");

		} catch (Exception e) {

		}

		long count = holderRepository.count();

		cleaner.removeShareholder(id);

		return count != holderRepository.count();
	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private ShareholderResponse getShareholderResponse(Shareholder holder) {

		List<Shareholder> list = new ArrayList<>();

		list.add(holder);

		return getShareholderResponse(list).get(0);

	}

	private List<ShareholderResponse> getShareholderResponse(List<Shareholder> shareHolders) {

		List<ShareholderResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> shareHoldersIdFuture = CompletableFuture.supplyAsync(
				() -> shareHolders.stream().map(Shareholder::getId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Set<CompanyInformation>>> companyMapFuture = shareHoldersIdFuture
				.thenApplyAsync(holdersId -> {

					Map<String, Set<CompanyInformation>> map = new HashMap<>();

					List<CompanyInformation> companies = companyRepository.findByShareHoldersIn(holdersId);

					for (CompanyInformation company : companies) {

						List<String> holders = company.getShareHolders();

						for (String i : holders) {

							if (map.containsKey(i)) {

								map.get(i).add(company);

							} else {

								map.put(i, new HashSet<>());

								map.get(i).add(company);

							}

						}

					}

					return map;

				}, executor);

		CompletableFuture<List<String>> userIdFuture = CompletableFuture.supplyAsync(
				() -> shareHolders.stream().map(Shareholder::getUserId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, User>> userNameMapFuture = userIdFuture.thenApplyAsync(usersId -> {

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, UserContactInfo>> contactFuture = userIdFuture.thenApplyAsync(usersId -> {

			return contactRepository.findByUserIdIn(usersId).stream()
					.collect(Collectors.toMap(UserContactInfo::getUserId, Function.identity()));

		}, executor);

		CompletableFuture<Map<String, UserLocation>> locationFuture = userIdFuture.thenApplyAsync(usersId -> {

			return locationRepository.findByUserIdIn(usersId).stream()
					.collect(Collectors.toMap(UserLocation::getUserId, Function.identity()));

		}, executor);

		CompletableFuture<List<String>> sharedCompanyIdFuture = shareHoldersIdFuture.thenApplyAsync(ids -> {

			List<Map<String, List<Double>>> shares = shareHolders.stream().map(Shareholder::getSharePercentage)
					.collect(Collectors.toList());

			Set<String> set = new HashSet<>();

			for (Map<String, List<Double>> map : shares) {

				Set<String> list = map.keySet();

				for (String i : list) {

					set.add(i);

				}

			}

			return new ArrayList<>(set);

		}, executor);

		CompletableFuture<Map<String, CompanyInformation>> sharedCompanyMapFuture = sharedCompanyIdFuture
				.thenApplyAsync(ids -> {

					return companyRepository.findAllById(ids).stream()
							.collect(Collectors.toMap(CompanyInformation::getId, Function.identity()));

				}, executor);

		CompletableFuture.allOf(shareHoldersIdFuture, companyMapFuture, userIdFuture, userNameMapFuture, contactFuture,
				locationFuture, sharedCompanyIdFuture).join();

		Map<String, User> userNameMap = userNameMapFuture.join();
		Map<String, UserLocation> locationMap = locationFuture.join();
		Map<String, Set<CompanyInformation>> companyMap = companyMapFuture.join();
		Map<String, CompanyInformation> sharedCompanyInformation = sharedCompanyMapFuture.join();
		Map<String, UserContactInfo> contactMap = contactFuture.join();

		for (Shareholder holder : shareHolders) {

			try {

				ShareholderResponse response = new ShareholderResponse();

				response.setId(holder.getId());
				response.setUserId(holder.getUserId());
				response.setNid(holder.getNid());
				response.setTin(holder.getTin());
				response.setUserName(userNameMap.get(holder.getUserId()).getFullName() == null
						? userNameMap.get(holder.getUserId()).getName()
						: userNameMap.get(holder.getUserId()).getFullName());
				response.setProfileImageId(userNameMap.get(holder.getUserId()).getProfileImageId());

				try {

					response.setCompanies(new ArrayList<>(companyMap.get(holder.getId())));

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					UserContactInfo contact = contactMap.get(holder.getUserId());

					response.setContactInfoId(contact.getId());
					response.setEmail(contact.getEmail());
					response.setPhone(contact.getPhone());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					UserLocation location = locationMap.get(holder.getUserId());

					response.setLocationId(location.getId());
					response.setLocationName(location.getLocationName());
					response.setLattitude(location.getLattitude());
					response.setLongititude(location.getLongitude());

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				try {

					Map<String, List<Double>> sharedCompanies = holder.getSharePercentage();

					Map<String, List<Double>> map = new HashMap<>();

					for (String i : sharedCompanies.keySet()) {

						String companyName = sharedCompanyInformation.get(i).getCompanyName();

						map.put(companyName, sharedCompanies.get(i));

					}

					response.setSharePercentageWithCompanyName(map);

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
