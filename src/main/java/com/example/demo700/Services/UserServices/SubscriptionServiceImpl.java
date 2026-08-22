package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.Subscription;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.SubscriptionRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Cleaner cleaner;

	private static final String[] ALLOWED_MIME_TYPES = { "image/jpeg", "image/png", "image/gif", "image/webp",
			"image/bmp", "image/svg+xml" };

	private static final String cacheValue = "Subscription";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public Subscription addSubscription(Subscription subscription, String userId, MultipartFile signature) {

		if (subscription == null || userId == null || subscription.getNumberOfShare() <= 0) {

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

		try {

			CompanyInformation company = companyRepository.findById(subscription.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Np such company exist at here...");

		}

		try {

			if (signature != null && !signature.isEmpty()) {

				String contentType = signature.getContentType();
				boolean isValidMime = false;
				for (String allowedMime : ALLOWED_MIME_TYPES) {
					if (allowedMime.equals(contentType)) {
						isValidMime = true;
						break;
					}
				}

				if (!isValidMime) {

					throw new Exception();

				} else {

					String fileId = imageService.upload(signature);

					if (fileId != null) {

						subscription.setSignatureId(fileId);

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Signature not added...");

		}

		subscription = subscriptionRepository.save(subscription);

		return subscription;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public Subscription updateSubscription(Subscription subscription, String userId, String id,
			MultipartFile signature) {

		if (subscription == null || userId == null || subscription.getNumberOfShare() <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			Subscription sub = subscriptionRepository.findById(id).get();

			if (sub == null) {

				throw new Exception();

			}

			if (signature != null && !signature.isEmpty()) {

				if (imageService.attachmentExists(sub.getSignatureId())) {

					imageService.delete(sub.getSignatureId());

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

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

		try {

			CompanyInformation company = companyRepository.findById(subscription.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Np such company exist at here...");

		}

		try {

			if (signature != null && !signature.isEmpty()) {

				String contentType = signature.getContentType();
				boolean isValidMime = false;
				for (String allowedMime : ALLOWED_MIME_TYPES) {
					if (allowedMime.equals(contentType)) {
						isValidMime = true;
						break;
					}
				}

				if (!isValidMime) {

					throw new Exception();

				} else {

					String fileId = imageService.upload(signature);

					if (fileId != null) {

						subscription.setSignatureId(fileId);

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Signature not added...");

		}

		subscription.setId(id);

		subscription = subscriptionRepository.save(subscription);

		return subscription;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + $id")
	public Subscription findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Subscription sub = subscriptionRepository.findById(id).get();

			if (sub == null) {

				throw new Exception();

			}

			return sub;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<Subscription> findAll() {

		try {

			List<Subscription> list = subscriptionRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<Subscription> findByCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Subscription> list = subscriptionRepository.findByCompanyId(companyId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNamePrefix_' + $subscriberName")
	public List<Subscription> findBySubscriberNameContainingIgnoreCase(String subscriberName) {

		if (subscriberName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Subscription> sub = subscriptionRepository.findBySubscriberNameContainingIgnoreCase(subscriberName);

			if (sub == null || sub.isEmpty()) {

				throw new Exception();

			}

			return sub;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndNumberOfShareLte_' + $companyId + '_' + #numberOfShare")
	public List<Subscription> findByCompanyIdAndNumberOfShareLte(String companyId, int numberOfShare) {

		if (companyId == null || numberOfShare <= 0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Subscription> sub = subscriptionRepository.findByCompanyIdAndNumberOfShareLte(companyId,
					numberOfShare);

			if (sub == null || sub.isEmpty()) {

				throw new Exception();

			}

			return sub;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyIdAndNumberOfShareGte_' + $companyId + '_' + #numberOfShare")
	public List<Subscription> findByCompanyIdAndNumberOfShareGte(String companyId, int numberOfShare) {

		if (companyId == null || numberOfShare <= 0) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Subscription> sub = subscriptionRepository.findByCompanyIdAndNumberOfShareGte(companyId,
					numberOfShare);

			if (sub == null || sub.isEmpty()) {

				throw new Exception();

			}

			return sub;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findBySignatureId_' + $signatureId")
	public List<Subscription> findBySignatureId(String signatureId) {

		if (signatureId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Subscription> sub = subscriptionRepository.findBySignatureId(signatureId);

			if (sub == null || sub.isEmpty()) {

				throw new Exception();

			}

			return sub;

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true),
			@CacheEvict(value = "Director", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyContact", allEntries = true),
			@CacheEvict(value = "CompanyPayment", allEntries = true) })
	public boolean removeSubscription(String id, String userId) {

		if (id == null || userId == null) {

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

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			long count = subscriptionRepository.count();

			cleaner.removeSubscription(id);

			return count != subscriptionRepository.count();

		} catch (Exception e) {

		}

		Subscription sub = null;

		try {

			sub = subscriptionRepository.findById(id).get();

			if (sub == null) {

				throw new Exception();

			}

			CompanyInformation company = companyRepository.findById(sub.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such subscription exist at here...");

		}

		long count = subscriptionRepository.count();

		cleaner.removeSubscription(id);

		return count != subscriptionRepository.count();

	}

}
