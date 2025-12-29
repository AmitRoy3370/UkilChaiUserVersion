package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.UserModels.AdvocateRating;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.AdvocateRatingRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdvocateRatingServiceImpl implements AdvocateRatingService {

	@Autowired
	private AdvocateRatingRepository advocateRatingRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Override
	public AdvocateRating giveAdvocateRating(AdvocateRating advocateRating, String userId) {

		if (advocateRating == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!advocateRating.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to give the rating...");

		}

		try {

			Advocate advocate = advocateRepository.findById(advocateRating.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here for rating...");

		}

		advocateRating = advocateRatingRepository.save(advocateRating);

		return advocateRating;
	}

	@Override
	public AdvocateRating updateAdvocateRating(AdvocateRating advocateRating, String userId, String advocateRatingId) {

		if (advocateRating == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateRating rating = advocateRatingRepository.findById(advocateRatingId).get();

			if (rating == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rating find at here to update...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!advocateRating.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to give the rating...");

		}

		try {

			Advocate advocate = advocateRepository.findById(advocateRating.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such advocate find at here for rating...");

		}

		advocateRating.setId(advocateRatingId);

		advocateRating = advocateRatingRepository.save(advocateRating);

		return advocateRating;
	}

	@Override
	public List<AdvocateRating> seeAllAdvocateRating() {

		List<AdvocateRating> list = advocateRatingRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such rating find at here...");

		}

		return list;
	}

	@Override
	public List<AdvocateRating> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateRating> list = advocateRatingRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such rating find at here...");

		}

		return list;
	}

	@Override
	public List<AdvocateRating> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocateRating> list = advocateRatingRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such rating find at here...");

		}

		return list;
	}

	@Override
	public AdvocateRating findByAdvocateRatingId(String advocateRatingId) {

		if (advocateRatingId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateRating advocateRating = advocateRatingRepository.findById(advocateRatingId).get();

			if (advocateRating == null) {

				throw new Exception();

			}

			return advocateRating;

		} catch (Exception e) {

			throw new NoSuchElementException("No such rating find at here...");

		}

	}

	@Override
	public List<AdvocateRating> findByRatingGreaterThan(int rating) {

		List<AdvocateRating> list = advocateRatingRepository.findByRatingGreaterThan(rating);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such rating find at here...");

		}

		return list;
	}

	@Override
	public boolean deleteAdvocateRating(String userId, String ratingId) {

		if (userId == null || ratingId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocateRating advocateRating = advocateRatingRepository.findById(ratingId).get();

			if (advocateRating == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such rating at here..");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = advocateRatingRepository.count();

					cleaner.removeAdvocateRating(ratingId);

					return count != advocateRatingRepository.count();

				}

			} catch (Exception e) {

			}

			AdvocateRating advocateRating = advocateRatingRepository.findById(ratingId).get();

			if (advocateRating == null) {

				throw new Exception();

			}

			if (!advocateRating.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		long count = advocateRatingRepository.count();

		cleaner.removeAdvocateRating(ratingId);

		return count != advocateRatingRepository.count();
	}

}
