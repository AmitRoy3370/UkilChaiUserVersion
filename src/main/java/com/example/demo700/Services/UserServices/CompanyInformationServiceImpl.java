package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.Capital;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.Director;
import com.example.demo700.Model.UserModels.Shareholder;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CapitalRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.DirectorRepository;
import com.example.demo700.Repositories.UserRepositories.ShareholderRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CompanyInformationServiceImpl implements CompanyInformationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyInformationRepository companyInformationRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private ShareholderRepository holderRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CapitalRepository capitalRepository;

	private static final String cacheValue = "CompanyInformation";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CompanyInformation addCompanyInformation(CompanyInformation companyInformation, String userId,
			MultipartFile files[]) {

		if (companyInformation == null || userId == null || !companyInformation.getCreatorId().equals(userId)) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Director director = directorRepository.findByUserId(userId);

			if (director == null) {

				throw new Exception();

			}

			if (companyInformation.getDirectorsId() != null && !companyInformation.getDirectorsId().isEmpty()
					&& companyInformation.getDirectorsId().contains(director.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("The actioned user must be a director");

		}

		try {

			CompanyInformation information = companyInformationRepository
					.findByCompanyNameIgnoreCase(companyInformation.getCompanyName());

			if (information != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This company name is already exist...");

		} catch (Exception e) {

		}

		try {

			if (companyInformation.getDirectorsId() != null && !companyInformation.getDirectorsId().isEmpty()) {

				List<Director> list = directorRepository.findAllById(companyInformation.getDirectorsId());

				if (list == null || list.isEmpty() || list.size() != companyInformation.getDirectorsId().size()) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Director's information are not valid...");

		}

		try {

			if (companyInformation.getShareHolders() != null && !companyInformation.getShareHolders().isEmpty()) {

				List<Shareholder> list = holderRepository.findAllById(companyInformation.getShareHolders());

				if (list == null || list.isEmpty() || list.size() != companyInformation.getShareHolders().size()) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Share holder's information are not valid...");

		}

		try {

			if (!companyInformation.getCapital().isEmpty()) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No capital can have a company in time of creation...");

		}

		try {

			List<String> fileIds = new ArrayList<>();

			for (MultipartFile i : files) {

				try {

					String id = imageService.upload(i);

					fileIds.add(id);

				} catch (Exception e) {

					System.out.println("error in file upload of company information :- " + e.getMessage());

				}

			}

			companyInformation.setDocuments(fileIds);

		} catch (Exception e) {

		}

		companyInformation = companyInformationRepository.save(companyInformation);

		return companyInformation;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CompanyInformation updateCompanyInformation(CompanyInformation companyInformation, String id, String userId,
			MultipartFile files[]) {

		if (companyInformation == null || userId == null || !companyInformation.getCreatorId().equals(userId)) {

			throw new NullPointerException("False request....");

		}

		CompanyInformation information = null;

		try {

			information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

			if (!information.getCreatorId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Director director = directorRepository.findByUserId(userId);

			if (director == null) {

				throw new Exception();

			}

			if (companyInformation.getDirectorsId() != null && !companyInformation.getDirectorsId().isEmpty()
					&& companyInformation.getDirectorsId().contains(director.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("The actioned user must be a director");

		}

		try {

			CompanyInformation _information = companyInformationRepository
					.findByCompanyNameIgnoreCase(companyInformation.getCompanyName());

			if (_information != null) {

				if (!_information.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This company name is already exist...");

		} catch (Exception e) {

		}

		try {

			if (companyInformation.getDirectorsId() != null && !companyInformation.getDirectorsId().isEmpty()) {

				Set<String> set = new HashSet<>(companyInformation.getDirectorsId());

				if (set.size() != companyInformation.getDirectorsId().size()) {

					throw new Exception();

				}

				List<Director> list = directorRepository.findAllById(companyInformation.getDirectorsId());

				if (list == null || list.isEmpty() || list.size() != companyInformation.getDirectorsId().size()) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Director's information are not valid...");

		}

		try {

			if (companyInformation.getShareHolders() != null && !companyInformation.getShareHolders().isEmpty()) {

				Set<String> set = new HashSet<>(companyInformation.getShareHolders());

				if (set.size() != companyInformation.getShareHolders().size()) {

					throw new Exception();

				}

				List<Shareholder> list = holderRepository.findAllById(companyInformation.getShareHolders());

				if (list == null || list.isEmpty() || list.size() != companyInformation.getShareHolders().size()) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Share holder's information are not valid...");

		}

		try {

			if (!companyInformation.getCapital().isEmpty()) {

				Set<String> set = new HashSet<>(companyInformation.getCapital());

				if (set.size() != companyInformation.getCapital().size()) {

					throw new Exception();

				} else {

					List<Capital> capitals = capitalRepository.findByCompanyId(id);

					if (capitals == null || capitals.isEmpty()) {

						throw new Exception();

					} else {

						List<Capital> inputedCapitals = capitalRepository.findAllById(set);

						for (Capital i : inputedCapitals) {

							if (i.getCompanyId().equals(id)) {

							} else {

								throw new Exception();

							}

						}

						List<String> removedCapital = new ArrayList<>();

						for (Capital i : capitals) {

							if (!inputedCapitals.contains(i.getId())) {

								removedCapital.add(i.getId());

							}

						}

						if (!removedCapital.isEmpty()) {

							capitalRepository.deleteAllById(removedCapital);

						}

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid capital information...");

		}

		List<String> fileIds = new ArrayList<>();

		try {

			for (String i : information.getDocuments()) {

				if (imageService.attachmentExists(i)) {

					if (!companyInformation.getDocuments().contains(i)) {

						imageService.delete(i);

					} else {

						fileIds.add(i);

					}

				} else {

				}

			}

		} catch (Exception e) {

		}

		try {

			for (MultipartFile i : files) {

				try {

					String _id = imageService.upload(i);

					fileIds.add(_id);

				} catch (Exception e) {

				}

			}

			companyInformation.setDocuments(fileIds);

		} catch (Exception e) {

		}

		companyInformation.setId(id);

		Query query = new Query(Criteria.where("_id").is(companyInformation.getId()));
		Update update = new Update();

		// Update ONLY the fields sent by Flutter
		if (companyInformation.getCompanyName() != null)
			update.set("companyName", companyInformation.getCompanyName());
		if (companyInformation.getType() != null)
			update.set("type", companyInformation.getType());
		if (companyInformation.getNatureOfBuisness() != null)
			update.set("natureOfBuisness", companyInformation.getNatureOfBuisness());
		if (companyInformation.getCategory() != null)
			update.set("category", companyInformation.getCategory());
		if (companyInformation.getOfficeRegistryId() != null)
			update.set("officeRegistryId", companyInformation.getOfficeRegistryId());
		if (companyInformation.getDirectorsId() != null)
			update.set("directorsId", companyInformation.getDirectorsId());
		if (companyInformation.getShareHolders() != null)
			update.set("shareHolders", companyInformation.getShareHolders());
		if (companyInformation.getDocuments() != null)
			update.set("documents", companyInformation.getDocuments());
		if (companyInformation.getAuthorized() != null)
			update.set("authorized", companyInformation.getAuthorized());
		if (companyInformation.getCapital() != null)
			update.set("capital", companyInformation.getCapital());

		update.set("creatorId", companyInformation.getCreatorId());

		mongoTemplate.updateFirst(query, update, CompanyInformation.class);

		return mongoTemplate.findOne(query, CompanyInformation.class);
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CompanyInformation addDirector(String id, String directorId, String userId) {

		if (id == null || directorId == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		Director actionedDirector = null;

		try {

			actionedDirector = directorRepository.findByUserId(user.getId());

			if (actionedDirector == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException(
					"You have to be the director of this company to add another director at here...");

		}

		CompanyInformation information = null;

		List<Director> directors = new ArrayList<>();
		List<String> directorsId = new ArrayList<>();

		try {

			information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

			if (!information.getCreatorId().equals(userId)) {

				throw new Exception();

			}

			directorsId = information.getDirectorsId();

			if (!directorsId.contains(actionedDirector.getId())) {

				throw new Exception();

			}

			directors = directorRepository.findAllById(directorsId);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

		try {

			if (directorsId.contains(directorId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("This director is already added in your computer...");

		}

		Director newDirector = null;

		try {

			newDirector = directorRepository.findById(directorId).get();

			if (newDirector == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Your given new director is not valid...");

		}

		directors.add(newDirector);

		information.setDirectorsId(directors.stream().map(Director::getId).collect(Collectors.toList()));

		Query query = new Query(Criteria.where("_id").is(information.getId()));
		Update update = new Update();

		// Update ONLY the fields sent by Flutter
		if (information.getCompanyName() != null)
			update.set("companyName", information.getCompanyName());
		if (information.getType() != null)
			update.set("type", information.getType());
		if (information.getNatureOfBuisness() != null)
			update.set("natureOfBuisness", information.getNatureOfBuisness());
		if (information.getCategory() != null)
			update.set("category", information.getCategory());
		if (information.getOfficeRegistryId() != null)
			update.set("officeRegistryId", information.getOfficeRegistryId());
		if (information.getDirectorsId() != null)
			update.set("directorsId", information.getDirectorsId());
		if (information.getShareHolders() != null)
			update.set("shareHolders", information.getShareHolders());
		if (information.getDocuments() != null)
			update.set("documents", information.getDocuments());
		if (information.getAuthorized() != null)
			update.set("authorized", information.getAuthorized());
		if (information.getCapital() != null)
			update.set("capital", information.getCapital());

		update.set("creatorId", information.getCreatorId());

		mongoTemplate.updateFirst(query, update, CompanyInformation.class);

		information = mongoTemplate.findOne(query, CompanyInformation.class);

		return information;

	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public CompanyInformation addShareHolder(String id, String holderId, String userId) {

		if (id == null || holderId == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		Director actionedDirector = null;

		try {

			actionedDirector = directorRepository.findByUserId(user.getId());

			if (actionedDirector == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException(
					"You have to be the director of this company to add another director at here...");

		}

		CompanyInformation information = null;

		List<Shareholder> holders = new ArrayList<>();

		List<String> holdersId = new ArrayList<>();

		try {

			information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

			if (!information.getCreatorId().equals(userId)) {

				throw new Exception();

			}

			holdersId = information.getShareHolders();

			List<String> directorsId = information.getDirectorsId();

			if (!directorsId.contains(actionedDirector.getId())) {

				throw new Exception();

			}

			holders = holderRepository.findAllById(holdersId);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

		try {

			if (holdersId.contains(holderId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("This shareholder is already added in your group...");

		}

		Shareholder newHolder = null;

		try {

			newHolder = holderRepository.findById(holderId).get();

			if (newHolder == null) {

				throw new Exception();

			}

			holders.add(newHolder);

		} catch (Exception e) {

			throw new ArithmeticException("Your given new share holder is not valid...");

		}

		information.setShareHolders(holders.stream().map(Shareholder::getId).collect(Collectors.toList()));

		Query query = new Query(Criteria.where("_id").is(information.getId()));
		Update update = new Update();

		// Update ONLY the fields sent by Flutter
		if (information.getCompanyName() != null)
			update.set("companyName", information.getCompanyName());
		if (information.getType() != null)
			update.set("type", information.getType());
		if (information.getNatureOfBuisness() != null)
			update.set("natureOfBuisness", information.getNatureOfBuisness());
		if (information.getCategory() != null)
			update.set("category", information.getCategory());
		if (information.getOfficeRegistryId() != null)
			update.set("officeRegistryId", information.getOfficeRegistryId());
		if (information.getDirectorsId() != null)
			update.set("directorsId", information.getDirectorsId());
		if (information.getShareHolders() != null)
			update.set("shareHolders", information.getShareHolders());
		if (information.getDocuments() != null)
			update.set("documents", information.getDocuments());
		if (information.getAuthorized() != null)
			update.set("authorized", information.getAuthorized());
		if (information.getCapital() != null)
			update.set("capital", information.getCapital());

		update.set("creatorId", information.getCreatorId());

		mongoTemplate.updateFirst(query, update, CompanyInformation.class);

		information = mongoTemplate.findOne(query, CompanyInformation.class);

		return information;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CompanyInformation> findAll() {

		try {

			List<CompanyInformation> list = companyInformationRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CompanyInformation findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CompanyInformation information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

			return information;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyName_' + #companyName")
	public List<CompanyInformation> findByCompanyNameContainingIgnoreCase(String companyName) {

		if (companyName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByCompanyNameContainingIgnoreCase(companyName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByType_' + #type")
	public List<CompanyInformation> findByTypeContainingIgnoreCase(String type) {

		if (type == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByTypeContainingIgnoreCase(type);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNatureOfBuisness_' + #natureOfBuisness")
	public List<CompanyInformation> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness) {

		if (natureOfBuisness == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByNatureOfBuisnessContainingIgnoreCase(natureOfBuisness);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCategory_' + #category")
	public List<CompanyInformation> findByCategoryContainingIgnoreCase(String category) {

		if (category == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByCategoryContainingIgnoreCase(category);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByOfficeRegistryId_' + #officeRegistryId")
	public List<CompanyInformation> findByOfficeRegistryId(String officeRegistryId) {

		if (officeRegistryId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByOfficeRegistryId(officeRegistryId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareHolder_' + #shareHoldersId")
	public List<CompanyInformation> findByShareHoldersContainingIgnoreCase(String shareHoldersId) {

		if (shareHoldersId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByShareHoldersContainingIgnoreCase(shareHoldersId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #documentsId")
	public List<CompanyInformation> findByDocumentsContainingIgnoreCase(String documentsId) {

		if (documentsId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByDocumentsContainingIgnoreCase(documentsId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDirectorsId_' + #directorsId")
	public List<CompanyInformation> findByDirectorsIdContainingIgnoreCase(String directorsId) {

		if (directorsId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByDirectorsIdContainingIgnoreCase(directorsId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAuthorized_' + #authorized")
	public List<CompanyInformation> findByAuthorizedContainingIgnoreCase(String authorized) {

		if (authorized == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByAuthorizedContainingIgnoreCase(authorized);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCapital_' + #capital")
	public List<CompanyInformation> findByCapital(String capital) {

		if (capital == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByCapitalContainingIgnoreCase(capital);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true) })
	public boolean deleteCompanyInformation(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		CompanyInformation information = null;

		try {

			information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

			if (centerAdmin != null) {

				long count = companyInformationRepository.count();

				cleaner.removeCompanyInformation(id);

				return count != companyInformationRepository.count();

			}

		} catch (Exception e) {

		}

		try {

			Director director = directorRepository.findByUserId(userId);

			if (director == null) {

				throw new Exception();

			}

			if (information.getDirectorsId() != null && !information.getDirectorsId().isEmpty()
					&& information.getDirectorsId().contains(director.getId())) {

			} else {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("The actioned user must be a director");

		}

		long count = companyInformationRepository.count();

		cleaner.removeCompanyInformation(id);

		return count != companyInformationRepository.count();

	}

}
