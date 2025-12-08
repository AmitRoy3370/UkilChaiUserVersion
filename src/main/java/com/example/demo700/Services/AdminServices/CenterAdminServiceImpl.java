package com.example.demo700.Services.AdminServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class CenterAdminServiceImpl implements CenterAdminService {

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public CenterAdmin addCenterAdmin(CenterAdmin centerAdmin, String userId) {

		if (centerAdmin == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!centerAdmin.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			if (!centerAdmin.getAdvocates().isEmpty()) {

				for (String i : centerAdmin.getAdvocates()) {

					Advocate advocate = advocateRepository.findById(i).get();

					if (advocate == null) {

						throw new Exception();

					}

					try {

						CenterAdmin _centerAdmin = centerAdminRepository.findByAdvocatesContainingIgnoreCase(i);

						if (_centerAdmin != null) {

							throw new ArithmeticException();

						}

					} catch (ArithmeticException e) {

						throw new Exception();

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid advocates credential...");

		}

		try {

			if (!centerAdmin.getAdmins().isEmpty()) {

				for (String i : centerAdmin.getAdmins()) {

					try {

						Admin admin = adminRepository.findById(i).get();

						if (admin == null) {

							throw new ArithmeticException();

						}

					} catch (Exception e) {

						throw new ArithmeticException();

					}

					try {

						CenterAdmin _centerAdmin = centerAdminRepository.findByAdminsContainingIgnoreCase(i);

						if (_centerAdmin != null) {

							throw new ArithmeticException();

						}

					} catch (ArithmeticException e) {

						throw new ArithmeticException();

					} catch (Exception e) {

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Invalid admin credintial...");

		} catch (Exception e) {

		}

		centerAdmin = centerAdminRepository.save(centerAdmin);

		return centerAdmin;
	}

	@Override
	public List<CenterAdmin> seeAll() {

		return centerAdminRepository.findAll();
	}

	@Override
	public CenterAdmin findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

			if (centerAdmin == null) {

				throw new Exception();

			}

			return centerAdmin;

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	public CenterAdmin findByAdminsContainingIgnoreCase(String admin) {

		if (admin == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findByAdminsContainingIgnoreCase(admin);

			if (centerAdmin == null) {

				throw new Exception();

			}

			return centerAdmin;

		} catch (Exception e) {

			throw new NoSuchElementException("No such center admin find at here...");

		}

	}

	@Override
	public List<CenterAdmin> findByDistrictsContainingIgnoreCase(String districts) {

		if (districts == null) {

			throw new NullPointerException("False request...");

		}

		List<CenterAdmin> list = centerAdminRepository.findByDistrictsContainingIgnoreCase(districts);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such center admin at here...");

		}

		return list;
	}

	@Override
	public CenterAdmin findByAdvocatesContainingIgnoreCase(String advocates) {

		if (advocates == null) {

			throw new NullPointerException("False request...");

		}

		CenterAdmin centerAdmin = centerAdminRepository.findByAdvocatesContainingIgnoreCase(advocates);

		if (centerAdmin == null) {

			throw new NoSuchElementException("No such center admin at here...");

		}

		return centerAdmin;
	}

	@Override
	public CenterAdmin updateCenterAdmin(CenterAdmin centerAdmin, String userId, String centerAdminId) {

		if (centerAdmin == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin _centerAdmin = centerAdminRepository.findById(centerAdminId).get();

			if (_centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such centerAdmin at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!centerAdmin.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			if (!centerAdmin.getAdvocates().isEmpty()) {

				for (String i : centerAdmin.getAdvocates()) {

					Advocate advocate = advocateRepository.findById(i).get();

					if (advocate == null) {

						throw new Exception();

					}

					try {

						CenterAdmin _centerAdmin = centerAdminRepository.findByAdvocatesContainingIgnoreCase(i);

						if (_centerAdmin != null) {

							if (!_centerAdmin.getId().equals(centerAdminId)) {

								throw new ArithmeticException();

							}

						}

					} catch (ArithmeticException e) {

						throw new Exception();

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid advocates credential...");

		}

		try {

			if (!centerAdmin.getAdmins().isEmpty()) {

				for (String i : centerAdmin.getAdmins()) {

					try {

						Admin admin = adminRepository.findById(i).get();

						if (admin == null) {

							throw new ArithmeticException();

						}

					} catch (Exception e) {

						throw new ArithmeticException();

					}

					try {

						CenterAdmin _centerAdmin = centerAdminRepository.findByAdminsContainingIgnoreCase(i);

						if (_centerAdmin != null) {

							if (!_centerAdmin.getId().equals(centerAdminId)) {

								throw new ArithmeticException();

							}

						}

					} catch (ArithmeticException e) {

						throw new ArithmeticException();

					} catch (Exception e) {

					}

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Invalid admin credintial...");

		} catch (Exception e) {

		}

		centerAdmin.setId(centerAdminId);
		
		centerAdmin = centerAdminRepository.save(centerAdmin);

		return centerAdmin;
	}

	@Override
	public boolean removeCentralAdmin(String centerAdminId, String userId) {

		if (centerAdminId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin _centerAdmin = centerAdminRepository.findById(centerAdminId).get();

			if (_centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such centerAdmin at here...");

		}

		long count = centerAdminRepository.count();
		
		cleaner.removeCenterAdmin(centerAdminId);

		return count != centerAdminRepository.count();
	}

}
