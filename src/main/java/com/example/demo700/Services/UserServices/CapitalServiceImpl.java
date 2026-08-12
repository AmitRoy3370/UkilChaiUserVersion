package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.Capital;
import com.example.demo700.Model.UserModels.CompanyInformation;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.CapitalRepository;
import com.example.demo700.Repositories.UserRepositories.CompanyInformationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CapitalServiceImpl implements CapitalService {

	@Autowired
	private CapitalRepository capitalRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyInformationRepository companyRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public Capital addCapital(Capital capital, String userId) {

		if (capital == null || userId == null || capital.getAuthorizedCapital() <= 0 || capital.getNumberOfShare() <= 0
				|| capital.getTotalShare() <= 0 || capital.getShareValue() <= 0 || capital.getCompanyId() == null) {

			throw new NullPointerException("False request...");

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

			CompanyInformation company = companyRepository.findById(capital.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company exist at here...");

		}

		capital = capitalRepository.save(capital);

		return capital;

	}

	@Override
	public Capital updateCapital(Capital capital, String id, String userId) {

		if (capital == null || userId == null || capital.getAuthorizedCapital() <= 0 || capital.getNumberOfShare() <= 0
				|| capital.getTotalShare() <= 0 || capital.getShareValue() <= 0 || capital.getCompanyId() == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Capital _capital = capitalRepository.findById(id).get();

			if (_capital == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

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

			CompanyInformation company = companyRepository.findById(capital.getCompanyId()).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company exist at here...");

		}

		capital.setId(id);

		capital = capitalRepository.save(capital);

		return capital;
	}

	@Override
	public Capital findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Capital capital = capitalRepository.findById(id).get();

			if (capital == null) {

				throw new Exception();

			}

			return capital;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}

	}

	@Override
	public List<Capital> findAll() {

		try {

			List<Capital> list = capitalRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByCompanyId(String companyId) {
		if (companyId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByCompanyId(companyId);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByAuthorizedCapitalLte(double authorizedCapital) {
		if (authorizedCapital <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByAuthorizedCapitalLte(authorizedCapital);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByAuthorizedCapitalGte(double authorizedCapital) {
		if (authorizedCapital <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByAuthorizedCapitalGte(authorizedCapital);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByTotalShareLte(int totalShare) {
		if (totalShare <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByTotalShareLte(totalShare);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByTotalShareGte(int totalShare) {
		if (totalShare <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByTotalShareGte(totalShare);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByNumberOfShareLte(int numberOfShare) {
		if (numberOfShare <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByNumberOfShareLte(numberOfShare);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByNumberOfShareGte(int numberOfShare) {
		if (numberOfShare <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByNumberOfShareGte(numberOfShare);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByShareValueLte(double shareValue) {
		if (shareValue <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByShareValueLte(shareValue);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public List<Capital> findByShareValueGte(double shareValue) {
		if (shareValue <= 0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Capital> list = capitalRepository.findByShareValueGte(shareValue);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such capital exist at here...");

		}
	}

	@Override
	public boolean deleteCapital(String id, String userId) {

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

			throw new NoSuchElementException("No such user exist at here...");

		}

		try {

			CenterAdmin admin = centerAdminRepository.findByUserId(user.getId());

			if (admin == null) {

				throw new Exception();

			}

			long count = capitalRepository.count();

			cleaner.removeCapital(id);

			return count != capitalRepository.count();

		} catch (Exception e) {

		}

		try {

			CompanyInformation company = companyRepository.findById(id).get();

			if (company == null) {

				throw new Exception();

			}

			if (!company.getCreatorId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such company exist at here...");

		}

		long count = capitalRepository.count();

		cleaner.removeCapital(id);

		return count != capitalRepository.count();

	}

}
