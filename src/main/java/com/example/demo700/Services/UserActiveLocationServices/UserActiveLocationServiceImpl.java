package com.example.demo700.Services.UserActiveLocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.UserLiveLocationDataResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.LiveLocations.LiveLocationData;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserLiveLocationRepositories.UserLiveLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class UserActiveLocationServiceImpl implements UserActiveLocationService {

	@Autowired
	private UserLiveLocationRepository userLiveLocationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public LiveLocationData addLocation(LiveLocationData liveLocation, String userId) {

		if (liveLocation == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception("No such user find at here...");

			}

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

		try {

			if (liveLocation.getUserId() == null || !liveLocation.getUserId().equals(userId)) {

				throw new Exception("You can add only your location...");

			}

			if (liveLocation.getAdvocateId() != null) {

				Advocate advocate = advocateRepository.findById(liveLocation.getAdvocateId()).get();

				if (advocate == null) {

					throw new Exception("No such advocate find at here...");

				}

				if (!advocate.getUserId().equals(userId)) {

					throw new Exception("You can add only your location....");

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findByUserId(userId);

			if (data != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This users location already exist...");

		} catch (Exception e) {

		}

		liveLocation = userLiveLocationRepository.save(liveLocation);

		if (liveLocation == null) {

			throw new ArithmeticException("No such live location find at here...");

		}

		return liveLocation;

	}

	@Override
	public LiveLocationData updateLocation(LiveLocationData liveLocation, String userId, String id) {

		if (liveLocation == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception("No such user find at here...");

			}

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findById(id).get();

			if (data == null) {

				throw new Exception();

			}

			if (!data.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such active user data find at here...");

		}

		try {

			if (liveLocation.getUserId() == null || !liveLocation.getUserId().equals(userId)) {

				throw new Exception("You can add only your location...");

			}

			if (liveLocation.getAdvocateId() != null) {

				Advocate advocate = advocateRepository.findById(liveLocation.getAdvocateId()).get();

				if (advocate == null) {

					throw new Exception("No such advocate find at here...");

				}

				if (!advocate.getUserId().equals(userId)) {

					throw new Exception("You can add only your location....");

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException(e.getMessage());

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findByUserId(userId);

			if (data != null) {

				if (!data.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This users location already exist...");

		} catch (Exception e) {

		}

		liveLocation.setId(id);

		liveLocation = userLiveLocationRepository.save(liveLocation);

		if (liveLocation == null) {

			throw new ArithmeticException("No such live location find at here...");

		}

		return liveLocation;

	}

	@Override
	public List<UserLiveLocationDataResponse> seeAll() {

		try {

			List<LiveLocationData> list = userLiveLocationRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such active user location find at here...");

		}

	}

	@Override
	public UserLiveLocationDataResponse getById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findById(id).get();

			if (data == null) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public UserLiveLocationDataResponse findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findByAdvocateId(advocateId);

			if (data == null) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public UserLiveLocationDataResponse findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findByUserId(userId);

			if (data == null) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public List<UserLiveLocationDataResponse> findByLocationNameContainingIgnoreCase(String locationName) {

		if (locationName == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<LiveLocationData> data = userLiveLocationRepository
					.findByLocationNameContainingIgnoreCase(locationName);

			if (data == null || data.isEmpty()) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public List<UserLiveLocationDataResponse> findByAdvocateIdIn(List<String> advocatesId) {

		if (advocatesId == null || advocatesId.isEmpty()) {

			throw new NullPointerException("False request...");

		}

		try {

			List<LiveLocationData> data = userLiveLocationRepository.findByAdvocateIdIn(advocatesId);

			if (data == null || data.isEmpty()) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public List<UserLiveLocationDataResponse> findByUserIdIn(List<String> usersId) {

		if (usersId == null || usersId.isEmpty()) {

			throw new NullPointerException("False request...");

		}

		try {

			List<LiveLocationData> data = userLiveLocationRepository.findByUserIdIn(usersId);

			if (data == null || data.isEmpty()) {

				throw new Exception();

			}

			return getUserLiveLocationResponse(data);

		} catch (Exception e) {

			throw new NoSuchElementException("No such user active location find at here...");

		}

	}

	@Override
	public boolean deleteLiveLocation(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception("No such user find at here...");

			}

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

		try {

			LiveLocationData data = userLiveLocationRepository.findById(id).get();

			if (data == null) {

				throw new Exception("No such live location data find at here...");

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin == null) {

					throw new Exception();

				}

				long count = userLiveLocationRepository.count();

				cleaner.removeUserLiveLocation(id);

				return count != userLiveLocationRepository.count();

			} catch (Exception e) {

			}

			if (!data.getUserId().equals(userId)) {

				throw new Exception("You can delete only your own data...");

			}

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

		long count = userLiveLocationRepository.count();

		cleaner.removeUserLiveLocation(id);

		return count != userLiveLocationRepository.count();

	}

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public UserLiveLocationDataResponse getUserLiveLocationResponse(LiveLocationData data) {

		List<LiveLocationData> list = new ArrayList<>();

		list.add(data);

		return getUserLiveLocationResponse(list).get(0);

	}

	public List<UserLiveLocationDataResponse> getUserLiveLocationResponse(List<LiveLocationData> list) {

		List<UserLiveLocationDataResponse> responses = new ArrayList<>();

		List<String> usersId = list.stream().map(LiveLocationData::getUserId).collect(Collectors.toList());

		CompletableFuture<Map<String, User>> userFuture = CompletableFuture.supplyAsync(() -> userRepository
				.findAllById(usersId).stream().filter(Objects::nonNull).filter(user -> user.getName() != null)
				.collect(Collectors.toMap(User::getId, Function.identity())), executors);

		CompletableFuture.allOf(userFuture).join();

		Map<String, User> userMap = userFuture.join();

		for (LiveLocationData data : list) {

			try {

				UserLiveLocationDataResponse response = new UserLiveLocationDataResponse();

				response.setId(data.getId());
				response.setAdvocateId(data.getAdvocateId());
				response.setUserId(data.getUserId());
				response.setLattitude(data.getLattitude());
				response.setLongitude(data.getLongitude());
				response.setLocationName(data.getLocationName());

				try {

					response.setUserName(userMap.get(data.getUserId()).getName());

				} catch (Exception e) {

					response.setUserName("Un Named");

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
