package com.example.demo700.Services.AdminServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public Admin addAdmin(Admin admin, String userId) {

		if (admin == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(admin.getUserId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Admin _admin = adminRepository.findByUserId(userId);

			if (_admin != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You already add as an admin...");

		} catch (Exception e) {

		}

		try {

			if (admin.getAdvocateSpeciality() != null) {

				for (AdvocateSpeciality i : admin.getAdvocateSpeciality()) {

					AdvocateSpeciality advocateSpeciality = AdvocateSpeciality.valueOf(i.toString());

					if (advocateSpeciality == null) {

						throw new Exception();

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid speciality uploaded at here...");

		}

		admin = adminRepository.save(admin);

		return admin;
	}

	@Override
	public List<Admin> seeAll() {

		List<Admin> list = adminRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such admin find at here...");

		}

		return list;
	}

	@Override
	public Admin findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Admin admin = adminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			return admin;

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}

	}

	@Override
	public List<Admin> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Admin> list = adminRepository.findByAdvocateSpecialityContainingIgnoreCase(advocateSpeciality);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}

	}

	@Override
	public Admin updateAdmin(Admin admin, String userId, String adminId) {

		if (admin == null || userId == null || adminId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!user.getId().equals(admin.getUserId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			Admin _admin = adminRepository.findByUserId(userId);

			if (_admin != null) {

				if (!_admin.getId().equals(adminId)) {

					throw new ArithmeticException();

				}

			} else if (_admin == null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You already add as an admin...");

		} catch (Exception e) {

		}

		try {

			if (admin.getAdvocateSpeciality() != null) {

				for (AdvocateSpeciality i : admin.getAdvocateSpeciality()) {

					AdvocateSpeciality advocateSpeciality = AdvocateSpeciality.valueOf(i.toString());

					if (advocateSpeciality == null) {

						throw new Exception();

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid speciality uploaded at here...");

		}

		admin = adminRepository.save(admin);

		return admin;
	}

	@Override
	public boolean deleteAdmin(String adminId, String userId) {

		if (adminId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Admin admin = adminRepository.findById(adminId).get();

			if (admin == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = adminRepository.count();

					cleaner.removeAdmin(adminId);

					return adminRepository.count() != count;

				}

			} catch (Exception e) {

			}

			if (!admin.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such admin find at here...");

		}

		long count = adminRepository.count();

		cleaner.removeAdmin(adminId);

		return adminRepository.count() != count;

	}

}
