package com.example.demo700.Services.AdminServices;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.example.demo700.DTOFiles.AdminDTO;
import com.example.demo700.DTOFiles.AdminJoinRequestDTO;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;
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
	public List<AdminDTO> seeAll() {

		List<Admin> list = adminRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such admin find at here...");

		}

		return getAdminResponse(list);
	}

	@Override
	public AdminDTO findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			Admin admin = adminRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			return getAdminResponse(admin);

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}

	}

	@Override
	public List<AdminDTO> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<Admin> list = adminRepository.findByAdvocateSpecialityContainingIgnoreCase(advocateSpeciality);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAdminResponse(list);

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}

	}

	@Override
	public List<AdminDTO> seeAll(List<String> list) {
		
		List<Admin> admins = adminRepository.findAllById(list);
		
		return getAdminResponse(admins);
		
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

		admin.setId(adminId);

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

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private AdminDTO getAdminResponse(Admin admin) {

		List<Admin> list = new ArrayList<>();

		list.add(admin);

		return getAdminResponse(list).get(0);

	}

	private List<AdminDTO> getAdminResponse(List<Admin> list) {

		CompletableFuture<List<String>> allUserIdFuture = CompletableFuture.supplyAsync(() -> list.stream()
				.map(Admin::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList()), executors);

		CompletableFuture<Map<String, User>> userNameFuture = allUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executors);

		CompletableFuture.allOf(allUserIdFuture, userNameFuture).join();

		List<String> allUserId = allUserIdFuture.join();

		Map<String, User> userMap = userNameFuture.join();

		List<AdminDTO> responses = new ArrayList<>();

		for (Admin admin : list) {

			try {

				AdminDTO adminDTO = new AdminDTO();

				adminDTO.setId(admin.getId());
				adminDTO.setUserId(admin.getUserId());
				adminDTO.setAdvocateSpeciality(admin.getAdvocateSpeciality());
				adminDTO.setUserName(userMap.get(admin.getUserId()).getName());
				adminDTO.setFullName(userMap.get(admin.getUserId()).getFullName());

				try {
					
					adminDTO.setProfileImageId(userMap.get(admin.getUserId()).getProfileImageId());
					
				} catch(Exception e) {
					
				}
				
				responses.add(adminDTO);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
