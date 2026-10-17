package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
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

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateAdditionalDocumentsRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Validators.AddressValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class AdvocateAdditionalInfoServiceImpl implements AdvocateAdditionalInfoService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateAdditionalDocumentsRepository additionalRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	private PhoneValidator phoneValidator;

	private AddressValidator addressValidator = new AddressValidator();

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "AdvocateAdditionalInfo";

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public AdvocateAdditionalInfo addAdditional(AdvocateAdditionalInfo info, String userId) {

		if (info == null || userId == null) {

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

			if (!advocate.getId().equals(info.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			AdvocateAdditionalInfo additionalInfo = additionalRepository.findByAdvocateId(advocate.getId());

			if (additionalInfo != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocate's additional info is already saved...");

		} catch (Exception e) {

		}

		try {

			if (info.getWhatsAppNumber() != null) {

				phoneValidator = new PhoneValidator(info.getWhatsAppNumber());

				if (phoneValidator.isValid()) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {
			throw new ArithmeticException("Advocate's whatsapp is not valid...");
		}

		try {

			if (info.getWhatsAppNumber() != null) {

				AdvocateAdditionalInfo additionalInfo = additionalRepository
						.findByWhatsAppNumber(info.getWhatsAppNumber());

				if (additionalInfo != null) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocate's additional info is already saved...");

		} catch (Exception e) {

		}

		try {

			if (info.getParmanentAdress() != null) {

				if (addressValidator.isValidAddress(info.getParmanentAdress())) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Advocate's permanent adress is not valid...");

		}

		try {

			if (info.getNidNumber() != null) {

				AdvocateAdditionalInfo additionalInfo = additionalRepository.findByNidNumber(info.getNidNumber());

				if (additionalInfo != null) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This nid number is already exist...");

		} catch (Exception e) {

		}

		info = additionalRepository.save(info);

		if (info == null) {

			throw new ArithmeticException("Additional info not saved...");

		}

		return info;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public AdvocateAdditionalInfo updateAdditional(AdvocateAdditionalInfo info, String userId, String id) {

		if (info == null || userId == null) {

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

			if (!advocate.getId().equals(info.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here...");

		}

		try {

			AdvocateAdditionalInfo additionalInfo = additionalRepository.findByAdvocateId(advocate.getId());

			if (additionalInfo != null) {

				if (!additionalInfo.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocate's additional info is already saved...");

		} catch (Exception e) {

		}

		try {

			if (info.getWhatsAppNumber() != null) {

				phoneValidator = new PhoneValidator(info.getWhatsAppNumber());

				if (phoneValidator.isValid()) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {
			throw new ArithmeticException("Advocate's whatsapp is not valid...");
		}

		try {

			if (info.getWhatsAppNumber() != null) {

				AdvocateAdditionalInfo additionalInfo = additionalRepository
						.findByWhatsAppNumber(info.getWhatsAppNumber());

				if (additionalInfo != null) {

					if (!additionalInfo.getId().equals(id)) {

						throw new ArithmeticException();

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This advocate's additional info is already saved...");

		} catch (Exception e) {

		}

		try {

			if (info.getParmanentAdress() != null) {

				if (addressValidator.isValidAddress(info.getParmanentAdress())) {

				} else {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Advocate's permanent adress is not valid...");

		}

		try {

			if (info.getNidNumber() != null) {

				AdvocateAdditionalInfo additionalInfo = additionalRepository.findByNidNumber(info.getNidNumber());

				if (additionalInfo != null) {

					if (!additionalInfo.getId().equals(id)) {

						throw new ArithmeticException();

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This nid number is already exist...");

		} catch (Exception e) {

		}

		try {

			AdvocateAdditionalInfo additionalInfo = additionalRepository.findById(id).get();

			if (additionalInfo == null) {

				throw new Exception();

			}

			if (!additionalInfo.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional info find at here...");

		}

		info.setId(id);

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = new Update();

		update.set("id", info.getId());
		update.set("advocateId", info.getAdvocateId());

		if (info.getParmanentAdress() != null) {

			update.set("parmanentAdress", info.getParmanentAdress());

		}

		if (info.getCityOfUpozila() != null) {

			update.set("cityOfUpozila", info.getCityOfUpozila());

		}

		if (info.getNidNumber() != null) {

			update.set("nidNumber", info.getNidNumber());

		}

		if (info.getDateOfBirth() != null) {

			update.set("dateOfBirth", info.getDateOfBirth());

		}

		if (info.getWhatsAppNumber() != null) {

			update.set("whatsAppNumber", info.getWhatsAppNumber());

		}

		mongoTemplate.updateFirst(query, update, AdvocateAdditionalInfo.class);

		info = mongoTemplate.findOne(query, AdvocateAdditionalInfo.class);

		// info = additionalRepository.save(info);

		if (info == null) {

			throw new ArithmeticException("Additional info not saved...");

		}

		return info;

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public AdvocateAdditionalInfo findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateAdditionalInfo info = additionalRepository.findById(id).get();

			if (info == null) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<AdvocateAdditionalInfo> findAll() {

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository.findAll();

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAdvocateId_' + #advocateId")
	public AdvocateAdditionalInfo findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateAdditionalInfo info = additionalRepository.findByAdvocateId(advocateId);

			if (info == null) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByParmanentAdress_' + #parmanentAdress")
	public List<AdvocateAdditionalInfo> findByParmanentAdressContainingIgnoreCase(String parmanentAdress) {

		if (parmanentAdress == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository
					.findByParmanentAdressContainingIgnoreCase(parmanentAdress);

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCityOfUpozila_' + #cityOfUpozila")
	public List<AdvocateAdditionalInfo> findByCityOfUpozilaContainingIgnoreCase(String cityOfUpozila) {

		if (cityOfUpozila == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository
					.findByCityOfUpozilaContainingIgnoreCase(cityOfUpozila);

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNidNumberPrefix_' + #nidNumber")
	public List<AdvocateAdditionalInfo> findByNidNumberContainingIgnoreCase(String nidNumber) {

		if (nidNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository.findByNidNumberContainingIgnoreCase(nidNumber);

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNidNumber_' + #nidNumber")
	public AdvocateAdditionalInfo findByNidNumber(String nidNumber) {

		if (nidNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateAdditionalInfo info = additionalRepository.findByNidNumber(nidNumber);

			if (info == null) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateOfBirthAfter_' + #dateOfBirth")
	public List<AdvocateAdditionalInfo> findByDateOfBirthAfter(Instant dateOfBirth) {

		if (dateOfBirth == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository.findByDateOfBirthAfter(dateOfBirth);

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByDateOfBirthBefore_' + #dateOfBirth")
	public List<AdvocateAdditionalInfo> findByDateOfBirthBefore(Instant dateOfBirth) {

		if (dateOfBirth == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdvocateAdditionalInfo> info = additionalRepository.findByDateOfBirthBefore(dateOfBirth);

			if (info == null || info.isEmpty()) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByWhatsAppNumber_' + #whatsAppNumber")
	public AdvocateAdditionalInfo findByWhatsAppNumber(String whatsAppNumber) {

		if (whatsAppNumber == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateAdditionalInfo info = additionalRepository.findByWhatsAppNumber(whatsAppNumber);

			if (info == null) {

				throw new Exception();

			}

			return info;

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional documents find at here...");

		}

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "Advocate", allEntries = true)

	})
	public boolean deleteAdditionalInfo(String id, String userId) {

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

		AdvocateAdditionalInfo additionalInfo = null;

		try {

			additionalInfo = additionalRepository.findById(id).get();

			if (additionalInfo == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional info find at here...");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(userId);

			if (admin != null) {

				if (admin.getAdvocates().contains(advocate.getId())) {

					long count = additionalRepository.count();

					cleaner.removeAdvocateAdditionalInfo(id);

					return count != additionalRepository.count();

				} else {

					if (!additionalInfo.getAdvocateId().equals(advocate.getId())) {

						throw new ArithmeticException();

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can remove only your advocate data...");

		} catch (Exception e) {

		}

		try {

			if (!additionalInfo.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such additional info find at here...");

		}

		long count = additionalRepository.count();

		cleaner.removeAdvocateAdditionalInfo(id);

		return count != additionalRepository.count();
	}

}
