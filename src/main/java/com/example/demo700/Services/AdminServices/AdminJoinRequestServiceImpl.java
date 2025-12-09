package com.example.demo700.Services.AdminServices;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.ENums.RequestResponse;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminJoinRequestRepository;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdminJoinRequestServiceImpl implements AdminJoinRequestService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private AdminJoinRequestRepository adminJoinRequestRepository;

	@Autowired
	private AdminService adminService;

	@Override
	public AdminJoinRequest addAdmin(AdminJoinRequest admin, String userId) {

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

		try {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findByUserId(userId);

			if (adminJoinRequest != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You already requested as an admin...");

		} catch (Exception e) {

		}

		admin = adminJoinRequestRepository.save(admin);

		return admin;
	}

	@Override
	public List<AdminJoinRequest> seeAll() {

		return adminJoinRequestRepository.findAll();

	}

	@Override
	public AdminJoinRequest findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AdminJoinRequest admin = adminJoinRequestRepository.findByUserId(userId);

			if (admin == null) {

				throw new Exception();

			}

			return admin;

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}
	}

	@Override
	public List<AdminJoinRequest> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality) {

		if (advocateSpeciality == null) {

			throw new NullPointerException("False request...");

		}

		try {

			List<AdminJoinRequest> list = adminJoinRequestRepository
					.findByAdvocateSpecialityContainingIgnoreCase(advocateSpeciality);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin find at here...");

		}

	}

	@Override
	public AdminJoinRequest updateAdmin(AdminJoinRequest admin, String userId, String adminId) {

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

		try {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findByUserId(userId);

			if (adminJoinRequest != null) {

				if (!adminJoinRequest.getId().equals(adminId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You already requested as an admin...");

		} catch (Exception e) {

		}

		admin.setId(adminId);

		admin = adminJoinRequestRepository.save(admin);

		return admin;
	}

	@Override
	public boolean deleteAdmin(String adminId, String userId) {

		if (adminId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdminJoinRequest admin = adminJoinRequestRepository.findById(adminId).get();

			if (admin == null) {

				throw new ArithmeticException();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin != null) {

					long count = adminJoinRequestRepository.count();

					cleaner.removeAdminJoinRequest(adminId);

					return adminJoinRequestRepository.count() != count;

				}

			} catch (ArithmeticException e) {

				throw new ArithmeticException("No such admin join request present at here...");

			} catch (Exception e) {

			}

			if (!admin.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("No such admin find at here...");

		}

		long count = adminJoinRequestRepository.count();

		cleaner.removeAdminJoinRequest(adminId);

		return adminJoinRequestRepository.count() != count;

	}

	@Override
	public Admin handleAdminJoinRequest(String userId, String adminJoinRequestId, String response) {

		if (userId == null || adminJoinRequestId == null || response == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findById(adminJoinRequestId).get();

			if (adminJoinRequest == null) {

				throw new Exception();

			}

			if (!adminJoinRequest.getUserId().equals(userId)) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can't accept yourself as the admin...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such admin request at here..");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user exist at here...");

		}

		try {

			RequestResponse requestResponse = RequestResponse.valueOf(response);

			if (requestResponse == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("In valid source request...");

		}

		RequestResponse requestResponse = RequestResponse.valueOf(response);

		if (requestResponse == RequestResponse.ACCEPT) {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findById(adminJoinRequestId).get();

			Admin admin = new Admin();

			admin.setId(adminJoinRequest.getId());
			admin.setAdvocateSpeciality(adminJoinRequest.getAdvocateSpeciality());
			admin.setUserId(adminJoinRequest.getUserId());

			try {

				admin = adminService.addAdmin(admin, admin.getUserId());

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);
				
				if(centerAdmin.getAdmins().isEmpty()) {
					
					List<String> list = new ArrayList<>();
					
					list.add(admin.getId());
					
					centerAdmin.setAdmins(list);
					
				} else {
					
					centerAdmin.getAdmins().add(admin.getId());
					
				}
				
				centerAdminRepository.save(centerAdmin);
				
				cleaner.removeAdminJoinRequest(adminJoinRequestId);

				return admin;

			} catch (Exception e) {

				throw new ArithmeticException(e.getMessage());

			}

		} else if (requestResponse == RequestResponse.REJECT) {

			cleaner.removeAdminJoinRequest(adminJoinRequestId);

			throw new ArithmeticException("Admin join request denied...");

		} else {

			throw new ArithmeticException("False request...");

		}

	}

}
