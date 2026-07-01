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
import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.DTOFiles.CenterAdminDTO;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdvocateServices.AdvocateService;

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
	private AdminService adminService;

	@Autowired
	private AdvocateService advocateService;

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
	public List<CenterAdminDTO> seeAll() {

		return getCenterAdminResponse(centerAdminRepository.findAll());
	}

	@Override
	public CenterAdminDTO findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

			if (centerAdmin == null) {

				throw new Exception();

			}

			return getCenterAdminResponse(centerAdmin);

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	public CenterAdminDTO findByAdminsContainingIgnoreCase(String admin) {

		if (admin == null) {

			throw new NullPointerException("False request...");

		}

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findByAdminsContainingIgnoreCase(admin);

			if (centerAdmin == null) {

				throw new Exception();

			}

			return getCenterAdminResponse(centerAdmin);

		} catch (Exception e) {

			throw new NoSuchElementException("No such center admin find at here...");

		}

	}

	@Override
	public List<CenterAdminDTO> findByDistrictsContainingIgnoreCase(String districts) {

		if (districts == null) {

			throw new NullPointerException("False request...");

		}

		List<CenterAdmin> list = centerAdminRepository.findByDistrictsContainingIgnoreCase(districts);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such center admin at here...");

		}

		return getCenterAdminResponse(list);
	}

	@Override
	public CenterAdminDTO findByAdvocatesContainingIgnoreCase(String advocates) {

		if (advocates == null) {

			throw new NullPointerException("False request...");

		}

		CenterAdmin centerAdmin = centerAdminRepository.findByAdvocatesContainingIgnoreCase(advocates);

		if (centerAdmin == null) {

			throw new NoSuchElementException("No such center admin at here...");

		}

		return getCenterAdminResponse(centerAdmin);
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

	@Override
	public List<Admin> findAdminByDistricts(String district) {

		if (district == null) {

			throw new NullPointerException("False request....");

		}

		List<Admin> list = new ArrayList<>();

		List<CenterAdmin> centerAdmins = centerAdminRepository.findByDistrictsContainingIgnoreCase(district);

		if (centerAdmins.isEmpty()) {

			throw new NoSuchElementException("No such admin present in that districts....");

		}

		for (CenterAdmin i : centerAdmins) {

			List<String> admins = i.getAdmins();

			for (String j : admins) {

				try {

					Admin admin = adminRepository.findById(j).get();

					if (admin == null) {

						throw new Exception();

					}

					list.add(admin);

				} catch (Exception e) {

				}

			}

		}

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such admin find at here...");

		}

		return list;
	}

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private CenterAdminDTO getCenterAdminResponse(CenterAdmin centerAdmin) {

		List<CenterAdmin> list = new ArrayList<>();

		list.add(centerAdmin);

		return getCenterAdminResponse(list).get(0);

	}

	private List<CenterAdminDTO> getCenterAdminResponse(List<CenterAdmin> list) {

		List<CenterAdminDTO> responses = new ArrayList<>();

		CompletableFuture<List<String>> allUserIdFuture = CompletableFuture.supplyAsync(() -> list.stream()
				.map(CenterAdmin::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList()),
				executors);

		CompletableFuture<Map<String, User>> userNameFuture = allUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream()
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executors);

		CompletableFuture<Map<String, List<AdminDTO>>> adminFullNameFuture = CompletableFuture.supplyAsync(() -> {

			if (list == null || list.isEmpty()) {
				return new HashMap<String, List<AdminDTO>>();
			}

			return list.stream().filter(Objects::nonNull)
					.filter(admin -> admin.getAdmins() != null && !admin.getAdmins().isEmpty())
					.collect(Collectors.toMap(CenterAdmin::getId, // Key: admin ID
							admin -> {
								try {
									// Pass List<String> to seeAllAdvocate
									List<AdminDTO> advocateResponses = adminService.seeAll(admin.getAdmins());

									/*
									 * return advocateResponses.stream().filter(Objects::nonNull)
									 * .map(AdminDTO::getFullName).filter(Objects::nonNull)
									 * .collect(Collectors.toList());
									 */

									return advocateResponses;

								} catch (Exception e) {
									return new ArrayList<AdminDTO>();
								}
							}));

		}, executors);

		// If seeAllAdvocate() returns List<AdvocateResponse>
		CompletableFuture<Map<String, List<AdvocateResponse>>> advocateFuture = CompletableFuture.supplyAsync(() -> {

			if (list == null || list.isEmpty()) {
				return new HashMap<String, List<AdvocateResponse>>();
			}

			return list.stream().filter(Objects::nonNull)
					.filter(admin -> admin.getAdvocates() != null && !admin.getAdvocates().isEmpty())
					.collect(Collectors.toMap(CenterAdmin::getId, // Key: admin ID
							admin -> {
								try {
									// Pass List<String> to seeAllAdvocate
									List<AdvocateResponse> advocateResponses = advocateService
											.seeAllAdvocate(admin.getAdvocates());
									/*
									 * return advocateResponses.stream().filter(Objects::nonNull)
									 * .map(AdvocateResponse::getName).filter(Objects::nonNull)
									 * .collect(Collectors.toList());
									 * 
									 */

									return advocateResponses;

								} catch (Exception e) {
									return new ArrayList<AdvocateResponse>();
								}
							}));

		}, executors);

		CompletableFuture.allOf(allUserIdFuture, userNameFuture, adminFullNameFuture, advocateFuture).join();

		Map<String, List<AdminDTO>> adminFullNameMap = adminFullNameFuture.join();
		Map<String, List<AdvocateResponse>> advocateMap = advocateFuture.join();

		List<String> allUserId = allUserIdFuture.join();
		Map<String, User> userMap = userNameFuture.join();

		for (CenterAdmin centerAdmin : list) {

			try {

				CenterAdminDTO response = new CenterAdminDTO();

				response.setId(centerAdmin.getId());
				response.setUserId(centerAdmin.getUserId());
				response.setUserName(userMap.get(centerAdmin.getUserId()).getName());

				response.setFullName(userMap.get(centerAdmin.getUserId()).getFullName());

				try {

					response.setProfileImageId(userMap.get(centerAdmin.getUserId()).getProfileImageId());

				} catch (Exception e) {

				}

				response.setAdmins(centerAdmin.getAdmins());
				response.setAdvocates(centerAdmin.getAdvocates());
				response.setDistricts(centerAdmin.getDistricts());

				try {

					response.setAdvocatesName(advocateMap.get(centerAdmin.getId()).stream()
							.map(AdvocateResponse::getName).collect(Collectors.toList()));

				} catch (Exception e) {

				}

				try {

					response.setAdvocatesFullName(advocateMap.get(centerAdmin.getId()).stream()
							.map(AdvocateResponse::getFullName).collect(Collectors.toList()));

				} catch (Exception e) {

				}

				try {

					response.setAdminsName(adminFullNameMap.get(centerAdmin.getId()).stream().map(AdminDTO::getUserName)
							.collect(Collectors.toList()));

				} catch (Exception e) {

				}

				try {

					response.setAdminsFullName(adminFullNameMap.get(centerAdmin.getId()).stream()
							.map(AdminDTO::getFullName).collect(Collectors.toList()));

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
