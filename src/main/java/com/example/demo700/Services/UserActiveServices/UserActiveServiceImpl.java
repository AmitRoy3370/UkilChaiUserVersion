package com.example.demo700.Services.UserActiveServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserActiveModel.UserActive;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserActiveRepositories.UserActiveRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class UserActiveServiceImpl implements UserActiveService {

	@Autowired
	private UserActiveRepository userActiveRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public UserActive addUserActive(UserActive userActive) {

		if (userActive == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userActive.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here....");

		}

		try {

			UserActive active = userActiveRepository.findByUserId(userActive.getUserId());

			if (active != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("No such user exist at here....");

		} catch (Exception e) {

		}

		userActive = userActiveRepository.save(userActive);

		if (userActive == null) {

			throw new ArithmeticException("User activeness is not added at here....");

		}

		return userActive;

	}

	@Override
	public UserActive updateUserActive(UserActive userActive, String userId, String id) {

		if (userActive == null || userId == null || id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userActive.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here....");

		}

		try {

			UserActive active = userActiveRepository.findByUserId(userActive.getUserId());

			if (active != null) {

				if (!active.getId().equals(id)) {

					throw new ArithmeticException();

				}

			} else {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("No such user exist at here....");

		} catch (Exception e) {

			throw new NoSuchElementException("Your updating activeness of user not find at here....");

		}

		try {

			UserActive active = userActiveRepository.findById(id).get();

			if (active == null) {

				throw new Exception();

			}

			if (!userActive.getUserId().equals(active.getUserId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here...");

		}

		userActive.setId(id);

		userActive = userActiveRepository.save(userActive);

		if (userActive == null) {

			throw new ArithmeticException("User activeness is not added at here....");

		}

		return userActive;
	}

	@Override
	public UserActive findById(String id) {

		if (id == null) {

			throw new NullPointerException("False requqest...");

		}

		try {

			UserActive userActive = userActiveRepository.findById(id).get();

			if (userActive == null) {

				throw new Exception();

			}

			return userActive;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here....");

		}

	}

	@Override
	public List<UserActive> findAll() {

		try {

			List<UserActive> list = userActiveRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here....");

		}

	}

	@Override
	public UserActive findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False requqest...");

		}

		try {

			UserActive userActive = userActiveRepository.findByUserId(userId);

			if (userActive == null) {

				throw new Exception();

			}

			return userActive;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here....");

		}

	}

	@Override
	public List<UserActive> findByActive(boolean active) {

		try {

			List<UserActive> list = userActiveRepository.findByActive(active);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here....");

		}

	}

	@Override
	public boolean removeUserActive(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin != null) {

				long count = userActiveRepository.count();

				cleaner.removeUserActive(id);

				return count != userActiveRepository.count();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here....");

		}

		try {

			UserActive active = userActiveRepository.findById(id).get();

			if (active == null) {

				throw new Exception();

			}

			if (!userId.equals(active.getUserId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user activeness find at here...");

		}

		long count = userActiveRepository.count();

		cleaner.removeUserActive(id);

		return count != userActiveRepository.count();

	}

}
