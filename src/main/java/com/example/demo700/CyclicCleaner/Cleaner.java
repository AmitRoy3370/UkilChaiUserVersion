package com.example.demo700.CyclicCleaner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Model.NotificationModel.Notification;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;

import com.example.demo700.Repositories.AdminRepositories.AdminJoinRequestRepository;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.ChatRepositories.ChatMessageRepository;
import com.example.demo700.Repositories.NotificationRepository.NotificationRepository;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class Cleaner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserContactInfoRepository userContactInfoRepository;

	@Autowired
	private UserLocationRepository userLocationRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private AdvocateJoinRequestRepository advocateJoinRequestRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdminJoinRequestRepository adminJoinRequestRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;

	public void removeUser(String userId) {

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			long count = userRepository.count();

			userRepository.deleteById(user.getId());

			if (count != userRepository.count()) {

				try {

					imageService.delete(user.getProfileImageId());

				} catch (Exception e) {

				}

				try {

					UserContactInfo userContactInfo = userContactInfoRepository.findByUserId(user.getId());

					if (userContactInfo == null) {

						throw new Exception();

					}

					removeUserContactInfo(userContactInfo.getId());

				} catch (Exception e) {

				}

				try {

					UserLocation userLocation = userLocationRepository.findByUserId(user.getId());

					if (userLocation == null) {

						throw new Exception();

					}

					removeUserLocation(userLocation.getId());

				} catch (Exception e) {

				}

				try {

					AdvocateJoinRequest advocateJoinRequest = advocateJoinRequestRepository.findByUserId(user.getId());

					if (advocateJoinRequest != null) {

						removeAdvocateJoinRequest(advocateJoinRequest.getId());

					}

				} catch (Exception e) {

				}

				try {

					Advocate advocate = advocateRepository.findByUserId(user.getId());

					removeAdvocate(advocate.getId());

				} catch (Exception e) {

				}

				try {

					Admin admin = adminRepository.findByUserId(user.getId());

					if (admin != null) {

						removeAdmin(admin.getId());

					}

				} catch (Exception e) {

				}

				try {

					CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

					removeCenterAdmin(centerAdmin.getId());

				} catch (Exception e) {

				}
				
				try {
					
					AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findByUserId(user.getId());
					
					if(adminJoinRequest != null) {
						
						removeAdminJoinRequest(adminJoinRequest.getId());
						
					}
					
				} catch(Exception e) {
					
				}
				
				try {
					
					List<ChatMessage> list = chatMessageRepository.findByReceiverOrSender(userId, userId);
					
					for(ChatMessage i : list) {
						
						removeChatMessage(i.getId());
						
					}
					
				} catch(Exception e) {
					
					
				}
				
				try {
					
					List<Notification> list = notificationRepository.findByUserId(userId);
					
					for(Notification i : list) {
						
						removeNotification(i.getId());
						
					}
					
				} catch(Exception e) {
					
				}

			}

		} catch (Exception e) {

		}

	}

	public void removeUserContactInfo(String contactInfoId) {

		try {

			UserContactInfo userContactInfo = userContactInfoRepository.findById(contactInfoId).get();

			if (userContactInfo == null) {

				throw new Exception();

			}

			long count = userContactInfoRepository.count();

			userContactInfoRepository.deleteById(contactInfoId);

			if (count != userContactInfoRepository.count()) {

				removeUser(userContactInfo.getUserId());

			}

		} catch (Exception e) {

		}

	}

	public void removeUserLocation(String userLocationId) {

		try {

			UserLocation userLocation = userLocationRepository.findById(userLocationId).get();

			if (userLocation == null) {

				throw new Exception();

			}

			long count = userLocationRepository.count();

			userLocationRepository.deleteById(userLocationId);

			if (count != userLocationRepository.count()) {

				removeUser(userLocation.getUserId());

			}

		} catch (Exception e) {

		}

	}

	public void removeAdvocateJoinRequest(String advocateJoinRequestId) {

		try {

			long count = advocateJoinRequestRepository.count();

			advocateJoinRequestRepository.deleteById(advocateJoinRequestId);

			if (count != advocateJoinRequestRepository.count()) {

			}

		} catch (Exception e) {

		}

	}

	public void removeAdvocate(String advocateId) {

		try {

			Advocate advocate = advocateRepository.findById(advocateId).get();

			if (advocate != null) {

				long count = advocateRepository.count();

				advocateRepository.deleteById(advocateId);

				if (count != advocateRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAdmin(String adminId) {

		try {

			Admin admin = adminRepository.findById(adminId).get();

			if (admin != null) {

				long count = adminRepository.count();

				adminRepository.deleteById(adminId);

				if (count != adminRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeCenterAdmin(String centerAdminId) {

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findById(centerAdminId).get();

			if (centerAdmin != null) {

				long count = centerAdminRepository.count();

				centerAdminRepository.deleteById(centerAdminId);

				if (count != centerAdminRepository.count()) {

					for (String i : centerAdmin.getAdmins()) {

						removeAdmin(i);

					}

					for (String i : centerAdmin.getAdvocates()) {

						removeAdvocate(i);

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAdminJoinRequest(String adminJoinRequestId) {

		try {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findById(adminJoinRequestId).get();

			if (adminJoinRequest != null) {

				long count = adminJoinRequestRepository.count();

				adminJoinRequestRepository.deleteById(adminJoinRequestId);

				if (count != adminJoinRequestRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeChatMessage(String id) {
		
		try {
			
			ChatMessage chatMessage = chatMessageRepository.findById(id).get();
			
			if(chatMessage != null) {
				
				long count = chatMessageRepository.count();
				
				chatMessageRepository.deleteById(id);
				
				if(count != chatMessageRepository.count()) {
					
				}
				
			}
				
					
			
		} catch(Exception e) {
			
		}
		
	}
	
	public void removeNotification(String id) {
		
		try {
			
			Notification notification = notificationRepository.findById(id).get();
			
			if(notification != null) {
				
				long count = notificationRepository.count();
				
				notificationRepository.deleteById(id);
				
				if(count != notificationRepository.count()) {
					
				}
				
			}
			
		} catch(Exception e) {
			
		}
		
	}
	
}
