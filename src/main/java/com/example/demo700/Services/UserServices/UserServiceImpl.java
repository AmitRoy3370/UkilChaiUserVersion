package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.JwtResponse;
import com.example.demo700.DTOFiles.LoginRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private Cleaner userCleaner;

	@Autowired
	private ImageService imageService;

	@Override
	public JwtResponse addUser(User user, MultipartFile file) {

		if (user == null) {

			throw new NullPointerException("False request...");

		}

		if (user.getName().isEmpty() || user.getPassword().isEmpty()) {

			throw new NullPointerException("Have to put all the data perfectly at here...");

		}

		try {

			User _user = userRepository.findByNameIgnoreCase(user.getName().trim());

			if (_user != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This username is already exist at here...");

		} catch (Exception e) {

		}

		try {

			if (file == null || file.isEmpty()) {

				throw new Exception();

			}

			String contentType = file.getContentType();

			if (contentType != null && contentType.startsWith("image/")) {

				String hexCode = imageService.upload(file);

				if (hexCode != null) {

					user.setProfileImageId(hexCode);

				}

			}

		} catch (Exception e) {

		}

		System.out.println("before encrypted :- " + user.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		System.out.println("after encrypted :- " + user.getPassword());

		user.setName(user.getName().trim());
		
		user = userRepository.save(user);

		if (user == null) {

			return null;

		}

		String token = jwtUtil.generateToken(user.getName());
		return new JwtResponse(token, user.getId());

	}

	@Override
	public List<User> seeAllUsers() {

		List<User> list = userRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		return list;
	}

	@Override
	public User updateUser(User user, String userId, MultipartFile file) {

		if (user == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		if (user.getName().isEmpty() || user.getPassword().isEmpty()) {

			throw new NullPointerException("Have to put all the data perfectly at here...");

		}
		
		user.setName(user.getName().trim());

		try {

			User _user = userRepository.findById(userId).get();

			if (_user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("There is no such user exist at here...");

		}

		try {

			User _user = userRepository.findByNameIgnoreCase(user.getName().trim());

			if (_user != null) {

				if (!_user.getId().equals(userId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can only update yourself....");

		} catch (Exception e) {

		}

		try {

			if (imageService.getFile(user.getProfileImageId()) != null) {

				if (file == null || file.isEmpty()) {

					throw new Exception();

				}

				String contentType = file.getContentType();

				if (contentType != null && contentType.startsWith("image/")) {

					String hexCode = imageService.update(user.getProfileImageId(), file);

					user.setProfileImageId(hexCode);

				}

			} else {

				try {

					if (file == null || file.isEmpty()) {

						throw new Exception();

					}

					String contentType = file.getContentType();

					if (contentType != null && contentType.startsWith("image/")) {

						String hexCode = imageService.upload(file);

						if (hexCode != null) {

							user.setProfileImageId(hexCode);

						}

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

		user.setId(userId);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user = userRepository.save(user);

		return user;
	}

	@Override
	public boolean deleteUser(String userId, String tryingToDelete) {

		if (userId == null || tryingToDelete == null) {

			throw new NullPointerException("False request...");

		}

		if (!userId.equals(tryingToDelete)) {

			throw new ArithmeticException("You can only delete yourself....");

		}

		long count = userRepository.count();

		userCleaner.removeUser(userId);

		return count != userRepository.count();
	}

	@Override
	public User findByName(String name) {

		if (name == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findByNameIgnoreCase(name.trim());

			if (user == null) {

				throw new Exception();

			}

			return user;

		} catch (Exception e) {

			throw new NoSuchElementException("No user with this name exist at here...");

		}

	}

	@Override
	public List<User> findByProfileImageId(String profileImageId) {

		if (profileImageId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<User> list = userRepository.findByProfileImageId(profileImageId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user At here..");

		}

	}

	@Override
	public JwtResponse LogIn(LoginRequest loginRequest) {

		if (loginRequest == null || loginRequest.getUserName() == null || loginRequest.getPassword() == null) {

			throw new NullPointerException("False request...");

		}

		String userName = loginRequest.getUserName();
		String password = loginRequest.getPassword();

		try {

			User user = userRepository.findByNameIgnoreCase(userName.trim());

			if (user == null) {

				throw new Exception();

			}

			System.out.println("existing user password :- " + user.getPassword() + " password :- "
					+ passwordEncoder.encode(password));

			if (!passwordEncoder.matches(password, user.getPassword())) {

				System.out.println("Exception thrown from here...");

				throw new RuntimeException("Invalid credentials");
			}

			String token = jwtUtil.generateToken(user.getName());
			return new JwtResponse(token, user.getId());

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	public User searchUser(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			return user;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

	}

}
