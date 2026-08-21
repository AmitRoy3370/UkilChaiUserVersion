package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.Director;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.DirectorRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class DirectorServiceImpl implements DirectorService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Cleaner cleaner;

	private static final String cacheValue = "Director";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public Director addDirector(Director director, String userId, MultipartFile nid) {

		if (director == null || userId == null || !director.getUserId().equals(userId)) {

			throw new NullPointerException("False request.....");

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

			Director _director = directorRepository.findByUserId(user.getId());

			if (_director != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already added as a director...");

		} catch (Exception e) {

		}

		try {

			if (nid != null && !nid.isEmpty()) {

				String fileName = nid.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(nid);

				if (nidId == null) {

					throw new ArithmeticException();

				} else {

					director.setNid(nidId);

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Added document is not valid....");

		} catch (Exception e) {

		}

		director = directorRepository.save(director);

		return director;

	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true)
	})
	public Director updateDirector(Director director, String userId, String id, MultipartFile nid) {
		if (director == null || userId == null || !director.getUserId().equals(userId)) {

			throw new NullPointerException("False request.....");

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

			Director _director = directorRepository.findById(id).get();

			if (_director == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}

		try {

			Director _director = directorRepository.findByUserId(user.getId());

			if (_director != null) {

				if (!_director.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This user is already added as a director...");

		} catch (Exception e) {

		}

		try {

			if (director.getNid() != null) {

				if (!imageService.attachmentExists(director.getNid())) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new NoSuchElementException("Given nid info is not valid...");

		}

		try {

			if (nid != null && !nid.isEmpty()) {

				String fileName = nid.getOriginalFilename().toLowerCase();

				if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {

					throw new ArithmeticException();

				}

				String nidId = imageService.upload(nid);

				if (nidId == null) {

					throw new ArithmeticException();

				} else {

					director.setNid(nidId);

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Added document is not valid....");

		} catch (Exception e) {

		}

		Query query = new Query(Criteria.where("_id"));

		Update update = new Update();

		update.set("id", id);
		update.set("userID", director.getUserId());

		if (director.getNid() != null) {

			update.set("nid", director.getNid());

		}

		mongoTemplate.updateFirst(query, update, Director.class);

		return mongoTemplate.findOne(query, Director.class);

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findById_' + #id")
	public Director findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Director director = directorRepository.findById(id).get();

			if (director == null) {

				throw new Exception();

			}

			return director;

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByPosition_' + #position")
	public List<Director> findByPosition(String position) {

		if (position == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Director> list = directorRepository.findByPositionContainingIgnoreCase(position);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findAll'")
	public List<Director> findAll() {

		try {

			List<Director> list = directorRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
	public Director findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Director director = directorRepository.findByUserId(userId);

			if (director == null) {

				throw new Exception();

			}

			return director;

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByNid_' + #nid")
	public List<Director> findByNid(String nid) {

		if (nid == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<Director> list = directorRepository.findByNid(nid);

			if (list == null || list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = cacheValue, allEntries = true),
			@CacheEvict(value = "CompanyInformation", allEntries = true)
	})
	public boolean removeDirector(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin admin = centerAdminRepository.findByUserId(userId);

				if (admin == null) {

					throw new Exception();

				}

				long count = directorRepository.count();

				cleaner.removeDirector(id);

				return count != directorRepository.count();

			} catch (Exception e) {

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user exist at here...");

		}

		try {

			Director _director = directorRepository.findById(id).get();

			if (_director == null) {

				throw new Exception();

			}

			if (!_director.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such director exist at here...");

		}

		long count = directorRepository.count();

		cleaner.removeDirector(id);

		return count != directorRepository.count();
	}

}
