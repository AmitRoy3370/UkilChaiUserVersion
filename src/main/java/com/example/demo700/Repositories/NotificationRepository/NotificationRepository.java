package com.example.demo700.Repositories.NotificationRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.NotificationModel.Notification;


@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
	
	List<Notification> findByUserIdAndReadFalse(String userId);
	List<Notification> findByUserId(String userId);

}

