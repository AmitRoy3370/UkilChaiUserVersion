package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.Shareholder;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.ShareholderRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class ShareholderServiceImpl implements ShareholderService {

	@Autowired
	private UserRepository userRepository;

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
	@CacheEvict(value = cacheValue, allEntries = true)
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
	@CacheEvict(value = cacheValue, allEntries = true)
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
			@CacheEvict(value = "CompanyInformation", allEntries = true) })
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
	public Shareholder findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Shareholder holder = holderRepository.findById(id).get();

			if (holder == null) {

				throw new Exception();

			}

			return holder;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	public List<Shareholder> findAll() {

		try {

			List<Shareholder> list = holderRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}

	}

	@Override
	public Shareholder findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Shareholder holder = holderRepository.findByUserId(userId);

			if (holder == null) {

				throw new Exception();

			}

			return holder;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	public List<Shareholder> findByNid(String nid) {

		if (nid == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByNid(nid);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	public List<Shareholder> findByTin(String tin) {

		if (tin == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByTin(tin);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<Shareholder> findByShareCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyId(companyId);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such share holder exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentage_' + #companyId + '_' + #percentage")
	public List<Shareholder> findByShareCompanyIdAndPercentage(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentage(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageGTE_' + #companyId + '_' + #percentage")
	public List<Shareholder> findByShareCompanyIdAndPercentageGte(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentageGte(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageLTE_' + #companyId + '_' + #percentage")
	public List<Shareholder> findByShareCompanyIdAndPercentageLte(String companyId, Double percentage) {

		if (companyId == null || percentage <= 0.0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Shareholder> list = holderRepository.findByShareCompanyIdAndPercentageLte(companyId, percentage);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndPercentageBetween_' + #companyId + '_' + #minPercentage + '_' + #maxPercentage")
	public List<Shareholder> findByShareCompanyIdAndPercentageBetween(String companyId, Double minPercentage,
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

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such kind of shareholder exist...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true) })
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

}
