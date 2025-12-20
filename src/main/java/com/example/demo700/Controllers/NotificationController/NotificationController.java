package com.example.demo700.Controllers.NotificationController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.Model.NotificationModel.Notification;
import com.example.demo700.Services.NotificationServices.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 🔹 Send notification (via REST API)
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(
            @RequestParam String userId,
            @RequestParam String message) {

        try {
            notificationService.sendNotification(userId, message);
            return new ResponseEntity<>("Notification sent successfully!", HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error sending notification: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🔹 Get unread notifications for a user
    @GetMapping("/unread/{userId}")
    public ResponseEntity<?> getUnreadNotifications(@PathVariable String userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            if (notifications.isEmpty()) {
                return new ResponseEntity<>("No unread notifications found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🔹 Delete a specific notification
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable String notificationId) {
        try {
            boolean deleted = notificationService.deleteNotification(notificationId);
            if (deleted) {
                return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Notification not found or not deleted", HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting notification", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🔹 WebSocket endpoint — real-time send from frontend
    @MessageMapping("/notify.send")
    public void sendNotificationSocket(@Payload Notification notification) {
        try {
            notificationService.sendNotification(notification.getUserId(), notification.getMessage());
            messagingTemplate.convertAndSendToUser(
                    notification.getUserId(),
                    "/queue/notifications",
                    notification
            );
        } catch (Exception e) {
            System.out.println("Error sending WebSocket notification: " + e.getMessage());
        }
    }

    // 🔹 Optional: Mark notification as read
    @PutMapping("/mark-read/{notificationId}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable String notificationId) {
        try {
            // You can implement this logic inside NotificationServiceImpl if not yet done
            Notification notification = notificationService.markAsRead(notificationId);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error marking notification as read", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

