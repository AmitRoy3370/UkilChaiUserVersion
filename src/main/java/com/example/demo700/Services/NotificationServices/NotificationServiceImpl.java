package com.example.demo700.Services.NotificationServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.NotificationModel.Notification;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.NotificationRepository.NotificationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public void sendNotification(String userId, String message) {

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here....");

		}

		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setMessage(message);
		notification.setRead(false);
		notification.setTimeStamp(Instant.now());
		notificationRepository.save(notification);

		messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", notification);

	}

	@Override
	public List<Notification> getUnreadNotifications(String userId) {

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here....");

		}

		return notificationRepository.findByUserIdAndReadFalse(userId);

	}

	@Override
	public boolean deleteNotification(String notificationId) {

		if (notificationId == null) {

			throw new NullPointerException("False request...");

		}

		long count = notificationRepository.count();

		cleaner.removeNotification(notificationId);

		return count != notificationRepository.count();
	}

	public Notification markAsRead(String notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new NoSuchElementException("Notification not found..."));
		notification.setRead(true);
		notification.setTimeStamp(Instant.now());
		return notificationRepository.save(notification);
	}

}

