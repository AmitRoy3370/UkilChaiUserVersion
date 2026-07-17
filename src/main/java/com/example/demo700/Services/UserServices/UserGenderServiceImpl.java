package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.Gender;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserGender;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.UserGenderRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class UserGenderServiceImpl implements UserGenderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGenderRepository userGenderRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public UserGender addUserGender(UserGender userGender, String userId) {

		if (userGender == null || userId == null || userGender.getUserId() == null
				|| !userGender.getUserId().equals(userId)) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user data exist at here....");

		}

		try {

			UserGender gender = userGenderRepository.findByUserId(userId);

			if (gender != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user's gender is already setted...");

		} catch (Exception e) {

		}

		userGender = userGenderRepository.save(userGender);

		if (userGender == null) {

			throw new ArithmeticException("User's gender data is not saved...");

		}

		return userGender;

	}

	@Override
	public UserGender updateUserGender(UserGender userGender, String userId, String id) {

		if (id == null || userGender == null || userId == null || userGender.getUserId() == null
				|| !userGender.getUserId().equals(userId)) {

			throw new NullPointerException("False request....");

		}

		try {

			UserGender gender = userGenderRepository.findById(id).get();

			if (gender == null) {

				throw new Exception();

			}

			if (!gender.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user gender find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user data exist at here....");

		}

		try {

			UserGender gender = userGenderRepository.findByUserId(userId);

			if (gender != null) {

				if (!gender.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user's gender is already setted...");

		} catch (Exception e) {

		}

		userGender.setId(id);

		userGender = userGenderRepository.save(userGender);

		if (userGender == null) {

			throw new ArithmeticException("User's gender data is not saved...");

		}

		return userGender;

	}

	@Override
	public UserGender findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			UserGender gender = userGenderRepository.findById(id).get();

			if (gender == null) {

				throw new Exception();

			}

			return gender;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user gender exist at here...");

		}

	}

	@Override
	public List<UserGender> findAll() {

		try {

			List<UserGender> list = userGenderRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user gender exist at here...");

		}

	}

	@Override
	public UserGender findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			UserGender gender = userGenderRepository.findByUserId(userId);

			if (gender == null) {

				throw new Exception();

			}

			return gender;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

	}

	@Override
	public List<UserGender> findByGender(Gender gender) {

		if (gender == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<UserGender> list = userGenderRepository.findByGender(gender);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user gender find at here...");

		}

	}

	@Override
	public List<UserGender> findByUserIdIn(List<String> usersId) {

		if (usersId == null || usersId.isEmpty()) {

			throw new NullPointerException("False request...");

		}

		try {

			List<UserGender> list = userGenderRepository.findByUserIdIn(usersId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such users gender exist at here...");

		}

	}

	@Override
	public boolean removeUserGender(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		UserGender userGender = null;

		try {

			userGender = userGenderRepository.findById(id).get();

			if (userGender == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user gender exist at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin admin = centerAdminRepository.findByUserId(userId);

				if (admin == null) {

					throw new Exception();

				}

				long count = userGenderRepository.count();

				cleaner.removeUserGender(id);

				return count != userGenderRepository.count();

			} catch (Exception e) {

			}

			if (!userGender.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to remove this gender info....");

		}

		long count = userGenderRepository.count();

		cleaner.removeUserGender(id);

		return count != userGenderRepository.count();
	}

}
