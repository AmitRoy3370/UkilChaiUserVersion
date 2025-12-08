package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Validators.AddressValidator;

@Service
public class UserLocationServiceImpl implements UserlocationService {

	@Autowired
	private UserLocationRepository userLocationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Cleaner userLocationCleaner;

	private AddressValidator adressValidator;

	@Override
	public UserLocation addUserLocation(UserLocation userLocation) {

		if (userLocation == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userLocation.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Invalid user...");

		}

		try {

			UserLocation _userLocation = userLocationRepository.findByUserId(userLocation.getUserId());

			if (_userLocation != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user information already exist at here...");

		} catch (Exception e) {

		}

		adressValidator = new AddressValidator();

		try {

			if (!adressValidator.isValidAddress(userLocation.getLocationName())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid location...");

		}

		userLocation = userLocationRepository.save(userLocation);

		return userLocation;
	}

	@Override
	public List<UserLocation> seeAllUserLocation() {

		List<UserLocation> list = userLocationRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		return list;
	}

	@Override
	public UserLocation updateUserLocation(UserLocation userLocation, String userId, String userLocationId) {

		if (userLocation == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userLocation.getUserId()).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Invalid user...");

		}
		
		try {

			UserLocation _userLocation = userLocationRepository.findByUserId(userLocation.getUserId());

			if (_userLocation != null) {

				if (!_userLocation.getId().equals(userLocationId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user information already exist at here...");

		} catch (Exception e) {

		}

		adressValidator = new AddressValidator();

		try {

			if (!adressValidator.isValidAddress(userLocation.getLocationName())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Invalid location...");

		}

		try {

			UserLocation _userLocation = userLocationRepository.findById(userLocationId).get();

			if (_userLocation == null) {

				throw new Exception();

			}

			if (!_userLocation.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("invalid credential...");

		}

		userLocation.setId(userLocationId);

		userLocation = userLocationRepository.save(userLocation);

		return userLocation;
	}

	@Override
	public boolean deleteUserLocation(String userLocationId, String userId) {

		if (userLocationId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Invalid user...");

		}

		try {

			UserLocation userLocation = userLocationRepository.findById(userLocationId).get();

			if (userLocation == null) {

				throw new Exception();

			}

			if (!userLocation.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Not find any valid user location");

		}

		long count = userLocationRepository.count();

		userLocationCleaner.removeUserLocation(userLocationId);

		return count != userLocationRepository.count();
	}

	@Override
	public UserLocation findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		UserLocation userLocation = userLocationRepository.findByUserId(userId);

		return userLocation;
	}

	@Override
	public List<UserLocation> findByLocationName(String locationName) {

		if (locationName == null) {

			throw new NullPointerException("False request...");

		}

		List<UserLocation> userLocations = userLocationRepository.findByLocationName(locationName);

		if (userLocations.isEmpty()) {

			throw new NoSuchElementException("No such user find at here...");

		}

		return userLocations;
	}

	@Override
	public List<UserLocation> findByLattitudeAndLongitude(double lattitude, double longitude) {

		List<UserLocation> list = userLocationRepository.findByLattitudeAndLongitude(lattitude, longitude);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		return list;
	}

}
