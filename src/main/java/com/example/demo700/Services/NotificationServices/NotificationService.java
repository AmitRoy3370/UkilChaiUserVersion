package com.example.demo700.Services.NotificationServices;

import java.util.List;

import com.example.demo700.Model.NotificationModel.Notification;

public interface NotificationService {
	
	public void sendNotification(String userId, String message);
	public List<Notification> getUnreadNotifications(String userId);
	public boolean deleteNotification(String notificationId);
	Notification markAsRead(String notificationId);


}

