package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.CompanyResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.Capital;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.Director;
import com.example.demo700.Model.UserModels.RegistrationProcess;
import com.example.demo700.Model.UserModels.Shareholder;
import com.example.demo700.Model.UserModels.Subscription;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CapitalRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.DirectorRepository;
import com.example.demo700.Repositories.UserRepositories.RegistrationProcessRepository;
import com.example.demo700.Repositories.UserRepositories.ShareholderRepository;
import com.example.demo700.Repositories.UserRepositories.SubscriptionRepository;
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
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CapitalRepository capitalRepository;

	@Autowired
	private RegistrationProcessRepository registrationProcessRepository;

	private static final String cacheValue = "CompanyInformation";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
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

			if (companyInformation.getOfficeRegistryId() != null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such registration process can be open at the time of creation....");

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
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
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

		  if(companyInformation.getOfficeRegistryId() != null) {

			RegistrationProcess process = registrationProcessRepository.findByCompanyId(id).get(0);

			if (process == null) {

				throw new Exception();

			}

			if(!process.getId().equals(companyInformation.getOfficeRegistryId())) {

                throw new Exception();

			}

		  }

		} catch (Exception e) {

			throw new NoSuchElementException("Registration process information is not valid...");

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

					    List<String> ids = new ArrayList<>(set);

						List<Capital> inputedCapitals = capitalRepository.findAllById(ids);

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

			} else {

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

				    List<Capital> _capitals = capitalRepository.findByCompanyId(id);

                    List<String> removalId = new ArrayList<>();

                    for(Capital i : _capitals) {

                        removalId.add(i.getId());

                    }

                    capitalRepository.deleteAllById(removalId);

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
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
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
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
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
public List<CompanyResponse> findAll() {
    try {
        System.out.println("========== DEBUG START ==========");
        System.out.println("1. Calling companyInformationRepository.findAll()");

        List<CompanyInformation> list = companyInformationRepository.findAll();

        System.out.println("2. Found " + list.size() + " companies in database");

        if (list.isEmpty()) {
            System.out.println("3. List is EMPTY - throwing exception");
            throw new Exception();
        }

        System.out.println("3. List has data, proceeding to getCompanyResponse()");
        System.out.println("4. First company ID: " + list.get(0).getId());
        System.out.println("4. First company Name: " + list.get(0).getCompanyName());

        List<CompanyResponse> response = getCompanyResponse(list);

        System.out.println("5. getCompanyResponse() completed successfully!");
        System.out.println("5. Response size: " + response.size());
        System.out.println("========== DEBUG END ==========");

        return response;

    } catch (Exception e) {
        System.err.println("========== ERROR OCCURRED ==========");
        System.err.println("Exception type: " + e.getClass().getName());
        System.err.println("Exception message: " + e.getMessage());
        e.printStackTrace();
        System.err.println("====================================");
        throw new NoSuchElementException("No such company information exist at here...");
    }
}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CompanyResponse findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CompanyInformation information = companyInformationRepository.findById(id).get();

			if (information == null) {

				throw new Exception();

			}

			return getCompanyResponse(information);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyName_' + #companyName")
	public List<CompanyResponse> findByCompanyNameContainingIgnoreCase(String companyName) {

		if (companyName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByCompanyNameContainingIgnoreCase(companyName);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByType_' + #type")
	public List<CompanyResponse> findByTypeContainingIgnoreCase(String type) {

		if (type == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByTypeContainingIgnoreCase(type);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNatureOfBuisness_' + #natureOfBuisness")
	public List<CompanyResponse> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness) {

		if (natureOfBuisness == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByNatureOfBuisnessContainingIgnoreCase(natureOfBuisness);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCategory_' + #category")
	public List<CompanyResponse> findByCategoryContainingIgnoreCase(String category) {

		if (category == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByCategoryContainingIgnoreCase(category);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByOfficeRegistryId_' + #officeRegistryId")
	public List<CompanyResponse> findByOfficeRegistryId(String officeRegistryId) {

		if (officeRegistryId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByOfficeRegistryId(officeRegistryId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByShareHolder_' + #shareHoldersId")
	public List<CompanyResponse> findByShareHoldersContainingIgnoreCase(String shareHoldersId) {

		if (shareHoldersId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByShareHoldersContainingIgnoreCase(shareHoldersId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDocuments_' + #documentsId")
	public List<CompanyResponse> findByDocumentsContainingIgnoreCase(String documentsId) {

		if (documentsId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByDocumentsContainingIgnoreCase(documentsId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDirectorsId_' + #directorsId")
	public List<CompanyResponse> findByDirectorsIdContainingIgnoreCase(String directorsId) {

		if (directorsId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByDirectorsIdContainingIgnoreCase(directorsId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAuthorized_' + #authorized")
	public List<CompanyResponse> findByAuthorizedContainingIgnoreCase(String authorized) {

		if (authorized == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository
					.findByAuthorizedContainingIgnoreCase(authorized);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCapital_' + #capital")
	public List<CompanyResponse> findByCapital(String capital) {

		if (capital == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyInformation> list = companyInformationRepository.findByCapitalContainingIgnoreCase(capital);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getCompanyResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such company information exist at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true), @CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
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

	private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

	private CompanyResponse getCompanyResponse(CompanyInformation company) {

		List<CompanyInformation> list = new ArrayList<>();

		list.add(company);

		return getCompanyResponse(list).get(0);

	}

	private List<CompanyResponse> getCompanyResponse(List<CompanyInformation> companies) {

		List<CompanyResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> companyIdFuture = CompletableFuture.supplyAsync(
				() -> companies.stream().map(CompanyInformation::getId).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, List<Subscription>>> subscriptionMapFuture = companyIdFuture
				.thenApplyAsync(companyIds -> {

					if (companyIds.isEmpty()) {

						return new HashMap<>();

					}

					Map<String, Subscription> map = subscriptionRepository.findByCompanyIdIn(companyIds).stream()
							.collect(Collectors.toMap(Subscription::getId, Function.identity()));

					Map<String, List<Subscription>> responseMap = new HashMap<>();

					for (Subscription sub : map.values()) {

						String companyId = sub.getCompanyId();

						if (responseMap.containsKey(companyId)) {

							responseMap.get(companyId).add(sub);

						} else {

							responseMap.put(companyId, new ArrayList<>());
							responseMap.get(companyId).add(sub);

						}

					}

					return responseMap;

				}, executor);

		CompletableFuture<List<String>> shareholdersIdFuture = CompletableFuture
				.supplyAsync(() -> companies.stream().map(CompanyInformation::getShareHolders).filter(Objects::nonNull)
						.distinct().flatMap(Collection::stream).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Shareholder>> shareHolderMapFuture = shareholdersIdFuture
				.thenApplyAsync(holdersId -> {

					if (holdersId.isEmpty()) {

						return new HashMap<>();

					}

					return holderRepository.findAllById(holdersId).stream()
							.collect(Collectors.toMap(Shareholder::getId, Function.identity()));

				}, executor);

		CompletableFuture<List<String>> directorsIdFuture = CompletableFuture
				.supplyAsync(() -> companies.stream().map(CompanyInformation::getDirectorsId).filter(Objects::nonNull)
						.distinct().flatMap(Collection::stream).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Director>> directorMapFuture = directorsIdFuture.thenApplyAsync(directorsId -> {

			if (directorsId.isEmpty()) {

				return new HashMap<>();

			}

			return directorRepository.findAllById(directorsId).stream()
					.collect(Collectors.toMap(Director::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<String>> capitalsIdFuture = CompletableFuture
				.supplyAsync(() -> companies.stream().map(CompanyInformation::getCapital).filter(Objects::nonNull)
						.distinct().flatMap(Collection::stream).collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, Capital>> capitalMapFuture = capitalsIdFuture.thenApplyAsync(capitalsId -> {

			if (capitalsId.isEmpty()) {

				return new HashMap<>();

			}

			return capitalRepository.findAllById(capitalsId).stream()
					.collect(Collectors.toMap(Capital::getId, Function.identity()));

		}, executor);

		CompletableFuture<List<String>> registrationIdsFuture = CompletableFuture
				.supplyAsync(() -> companies.stream().map(CompanyInformation::getOfficeRegistryId)
						.filter(Objects::nonNull).distinct().collect(Collectors.toList()), executor);

		CompletableFuture<Map<String, RegistrationProcess>> registrationProcessMapFuture = registrationIdsFuture
				.thenApplyAsync(registrationIds -> {

					if (registrationIds.isEmpty()) {

						return new HashMap<>();

					}

					return registrationProcessRepository.findAllById(registrationIds).stream()
							.collect(Collectors.toMap(RegistrationProcess::getId, Function.identity()));

				}, executor);

		// First, extract the user IDs from the maps
		CompletableFuture<List<String>> directorUserIds = directorMapFuture.thenApplyAsync(directorsMap -> {
			if (directorsMap.isEmpty()) {
				return new ArrayList<>();
			}
			return directorsMap.values().stream().map(Director::getUserId).collect(Collectors.toList());
		}, executor);

		CompletableFuture<List<String>> shareholderUserIds = shareHolderMapFuture.thenApplyAsync(holdersMap -> {
			if (holdersMap.isEmpty()) {
				return new ArrayList<>();
			}
			return holdersMap.values().stream().map(Shareholder::getUserId).collect(Collectors.toList());
		}, executor);

		// Then combine all three
		CompletableFuture<List<String>> userIds = CompletableFuture.supplyAsync(
				() -> companies.stream().map(CompanyInformation::getCreatorId).collect(Collectors.toList()), executor)
				.thenCombineAsync(directorUserIds, (creatorIds, directorIds) -> {
					List<String> combined = new ArrayList<>(creatorIds);
					combined.addAll(directorIds);
					return combined;
				}, executor).thenCombineAsync(shareholderUserIds, (combinedIds, shareholderIds) -> {
					combinedIds.addAll(shareholderIds);
					return combinedIds;
				}, executor);

		CompletableFuture<Map<String, User>> userMapFuture = userIds.thenApplyAsync(userId -> {

			if (userId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(userId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executor);

		CompletableFuture.allOf(shareholdersIdFuture, directorsIdFuture, capitalsIdFuture, registrationIdsFuture,
				shareHolderMapFuture, registrationProcessMapFuture, companyIdFuture, subscriptionMapFuture,
				capitalMapFuture, directorMapFuture, userIds, userMapFuture, directorUserIds, shareholderUserIds)
				.join();

		Map<String, User> userMap = userMapFuture.join();

		Map<String, Shareholder> holderMap = shareHolderMapFuture.join();

		Map<String, RegistrationProcess> registrationProcessMap = registrationProcessMapFuture.join();

		Map<String, List<Subscription>> subscriptionMap = subscriptionMapFuture.join();

		Map<String, Capital> capitalMap = capitalMapFuture.join();

		Map<String, Director> directorMap = directorMapFuture.join();

		for (CompanyInformation company : companies) {

			try {

				CompanyResponse response = new CompanyResponse();

				response.setId(company.getId());
				response.setCompanyName(company.getCompanyName());
				response.setType(company.getType());
				response.setNatureOfBuisness(company.getNatureOfBuisness());
				response.setCategory(company.getCategory());
				response.setOfficeRegistryId(company.getOfficeRegistryId());
				response.setDocuments(company.getDocuments());
				response.setShareHolders(company.getShareHolders());
				response.setDirectorsId(company.getDirectorsId());
				response.setCreatorId(company.getCreatorId());
				response.setCreatorName(userMap.get(company.getCreatorId()).getFullName() == null
						? userMap.get(company.getCreatorId()).getName()
						: userMap.get(company.getCreatorId()).getFullName());
				response.setCapital(company.getCapital());
				response.setAuthorized(company.getAuthorized());

				try {

					response.setRegistrationProcess(registrationProcessMap.get(company.getOfficeRegistryId()));

				} catch (Exception e) {

				}

				try {

					response.setSubscriptions(subscriptionMap.get(company.getId()));

				} catch (Exception e) {

				}

				try {

					List<String> directorsName = new ArrayList<>();

					for (String i : company.getDirectorsId()) {

						try {

							directorsName.add(userMap.get(directorMap.get(i).getUserId()).getFullName() == null
									? userMap.get(directorMap.get(i).getUserId()).getName()
									: userMap.get(directorMap.get(i).getUserId()).getFullName());

						} catch (Exception e) {

						}

					}

					response.setDirectorsName(directorsName);

				} catch (Exception e) {

				}

				try {

					List<String> shareHoldersName = new ArrayList<>();

					for (String i : company.getShareHolders()) {

						try {

							shareHoldersName.add(userMap.get(holderMap.get(i).getUserId()).getFullName() == null
									? userMap.get(holderMap.get(i).getUserId()).getName()
									: userMap.get(holderMap.get(i).getUserId()).getFullName());

						} catch (Exception e) {

						}

					}

					response.setShareHoldersName(shareHoldersName);

				} catch (Exception e) {

				}

				try {

					List<Capital> capitals = new ArrayList<>();

					for (String i : company.getCapital()) {

						try {

							capitals.add(capitalMap.get(i));

						} catch (Exception e) {

						}

					}

					response.setCapitals(capitals);

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
