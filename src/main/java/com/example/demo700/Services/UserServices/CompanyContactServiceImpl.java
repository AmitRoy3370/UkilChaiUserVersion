package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.CompanyContact;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyContactRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CompanyContactServiceImpl implements CompanyContactService {

	@Autowired
	private CompanyContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "CompanyContact";
	
	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true) })
	public CompanyContact addCompanyContact(CompanyContact companyContact, String userId) {

		if (companyContact == null || userId == null || companyContact.getCompanyId() == null
				|| companyContact.getContactPersonEmail() == null || companyContact.getContactPersonMobile() == null
				|| companyContact.getHowDidHear() == null) {

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

			CompanyInformation company = companyRepository.findById(companyContact.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company exist at here...");

		}

		companyContact = contactRepository.save(companyContact);

		return companyContact;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true) })
	public CompanyContact updateCompanyContact(CompanyContact companyContact, String userId, String id) {

		if (companyContact == null || userId == null || id == null || companyContact.getCompanyId() == null
				|| companyContact.getContactPersonEmail() == null || companyContact.getContactPersonMobile() == null
				|| companyContact.getHowDidHear() == null) {

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

			CompanyContact contact = contactRepository.findById(id).get();

			if (contact == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}

		try {

			CompanyInformation company = companyRepository.findById(companyContact.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company exist at here...");

		}

		companyContact.setId(id);

		companyContact = contactRepository.save(companyContact);

		return companyContact;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<CompanyContact> findAll() {

		try {

			List<CompanyContact> list = contactRepository.findAll();

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public CompanyContact findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CompanyContact contact = contactRepository.findById(id).get();

			if (contact == null) {

				throw new Exception();

			}

			return contact;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByContactPersonName_' + #contactPersonName")
	public List<CompanyContact> findByContactPersonNameContainingIgnoreCase(String contactPersonName) {

		if (contactPersonName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository
					.findByContactPersonNameContainingIgnoreCase(contactPersonName);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByCompanyId_' + #companyId")
	public List<CompanyContact> findByCompanyId(String companyId) {

		if (companyId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository.findByCompanyId(companyId);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByContactPersonMobile_' + #contactPersonMobile")
	public List<CompanyContact> findByContactPersonMobile(String contactPersonMobile) {

		if (contactPersonMobile == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository.findByContactPersonMobile(contactPersonMobile);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByContactPersonEmail_' + #contactPersonEmail")
	public List<CompanyContact> findByContactPersonEmail(String contactPersonEmail) {

		if (contactPersonEmail == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository.findByContactPersonEmail(contactPersonEmail);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByHowDidHear_' + #howDidHear")
	public List<CompanyContact> findByHowDidHear(String howDidHear) {

		if (howDidHear == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository.findByHowDidHearContainingIgnoreCase(howDidHear);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByAnyOtherMessage_' + #anyOtherMessage")
	public List<CompanyContact> findByAnyOtherMessageContainingIgnoreCase(String anyOtherMessage) {

		if (anyOtherMessage == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<CompanyContact> list = contactRepository.findByAnyOtherMessageContainingIgnoreCase(anyOtherMessage);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}
	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "ShareHolder", allEntries = true),
			@CacheEvict(value = "RegistrationProcess", allEntries = true),
			@CacheEvict(value = "Capital", allEntries = true), @CacheEvict(value = "Subscription", allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true) })
	public boolean deleteCompanyContact(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException();

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

			long count = contactRepository.count();

			cleaner.removeCompanyContact(id);

			return count != contactRepository.count();

		} catch (Exception e) {

		}

		try {

			CompanyContact contact = contactRepository.findById(id).get();

			if (contact == null) {

				throw new Exception();

			}

			CompanyInformation company = companyRepository.findById(contact.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company contact exist at here...");

		}

		long count = contactRepository.count();

		cleaner.removeCompanyContact(id);

		return count != contactRepository.count();

	}

}
