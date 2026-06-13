package com.example.demo700.Controllers.ChatControllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.DTOFiles.ChatResponse;
import com.example.demo700.Model.ChatModels.ChatMessage; // ✅ Fixed: Model → Models
import com.example.demo700.Services.ChatServices.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * ✅ WebSocket endpoint for sending live chat messages
	 */
	@MessageMapping("/chat.send")
	public void sendMessage(@Payload ChatMessage message) {
		try {
			ChatMessage savedMessage = chatService.saveMessage(message);

			String topic =

					getTopic(

							message.getSender(),

							message.getReceiver()

					);

			messagingTemplate.convertAndSend(

					"/topic/chat/"

							+ topic +

							"/messages",

					savedMessage

			);

			// messagingTemplate.convertAndSendToUser(message.getReceiver(),
			// "/queue/messages", savedMessage);
		} catch (Exception e) {
			System.out.println("Error sending message: " + e.getMessage());
		}
	}

	/**
	 * ✅ REST API: Save a chat message
	 */
	@PostMapping("/send")
	public ResponseEntity<?> saveMessage(@RequestBody ChatMessage message) {
		try {
			ChatMessage saved = chatService.saveMessage(message);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Unexpected error occurred while sending message",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ REST API: Get chat history between two users
	 */
	@GetMapping("/history/{user1}/{user2}")
	public ResponseEntity<?> getChatHistory(@PathVariable String user1, @PathVariable String user2) {
		try {
			List<ChatMessage> history = chatService.getChatHistory(user1, user2);
			if (history.isEmpty()) {
				return new ResponseEntity<>("No chat history found between users", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(history, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching chat history", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ REST API: Delete specific chat message by ID (with WebSocket notification)
	 */
	@DeleteMapping("/delete/{sender}/{receiver}/{chatId}")
	public ResponseEntity<?> deleteChatMessage(@PathVariable String sender, @PathVariable String receiver,
			@PathVariable String chatId) {

		try {
			boolean deleted = chatService.deleteChatMessage(sender, receiver, chatId);
			if (deleted) {
				// ✅ FIXED: Send WebSocket notification to both users
				Map<String, String> deleteInfo = new HashMap<>();
				deleteInfo.put("messageId", chatId);
				deleteInfo.put("deletedBy", sender);

				// messagingTemplate.convertAndSendToUser(receiver, "/queue/delete",
				// deleteInfo);
				// messagingTemplate.convertAndSendToUser(sender, "/queue/delete", deleteInfo);

				String topic = getTopic(sender, receiver);

				messagingTemplate.convertAndSend(

						"/topic/chat/" + topic + "/delete",

						deleteInfo

				);

				return new ResponseEntity<>("Chat message deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No message deleted", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Unexpected error occurred while deleting message",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ WebSocket endpoint for deleting messages (real-time)
	 */
	@MessageMapping("/chat.delete")
	public void deleteMessageWebSocket(@Payload ChatMessage message) {
		try {
			boolean deleted = chatService.deleteChatMessage(message.getSender(), message.getReceiver(),
					message.getId());

			if (deleted) {
				// ✅ FIXED: Send Map instead of String
				Map<String, String> deleteInfo = new HashMap<>();
				deleteInfo.put("messageId", message.getId());
				deleteInfo.put("deletedBy", message.getSender());

				/*
				 * messagingTemplate.convertAndSendToUser(message.getReceiver(),
				 * "/queue/delete", deleteInfo);
				 * 
				 * messagingTemplate.convertAndSendToUser(message.getSender(), "/queue/delete",
				 * deleteInfo);
				 */

				String topic =

						getTopic(

								message.getSender(),

								message.getReceiver()

						);

				messagingTemplate.convertAndSend(

						"/topic/chat/"

								+ topic +

								"/delete",

						deleteInfo

				);

			}
		} catch (Exception e) {
			System.out.println("Error deleting message via websocket: " + e.getMessage());
		}
	}

	/**
	 * ✅ REST API: Edit chat message
	 */
	@PutMapping("/edit/{sender}/{chatId}")
	public ResponseEntity<?> editChatMessage(@PathVariable String sender, @PathVariable String chatId,
			@RequestParam String newContent) {

		try {
			ChatMessage updated = chatService.editChatMessage(sender, chatId, newContent);

			// ✅ FIXED: Notify BOTH sender and receiver
			// messagingTemplate.convertAndSendToUser(updated.getReceiver(), "/queue/edit",
			// updated);
			// messagingTemplate.convertAndSendToUser(updated.getSender(), "/queue/edit",
			// updated);

			String topic = getTopic(updated.getSender(), updated.getReceiver());

			messagingTemplate.convertAndSend(

					"/topic/chat/" + topic + "/edit",

					updated

			);

			return new ResponseEntity<>(updated, HttpStatus.OK);

		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Unexpected error editing message", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ WebSocket endpoint for editing messages (real-time)
	 */
	@MessageMapping("/chat.edit")
	public void editMessageWebSocket(@Payload ChatMessage message) {
		try {
			ChatMessage updated = chatService.editChatMessage(message.getSender(), message.getId(),
					message.getContent());

			// Send update to both sender & receiver
			// messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/edit",
			// updated);
			// messagingTemplate.convertAndSendToUser(message.getSender(), "/queue/edit",
			// updated);

			String topic =

					getTopic(

							updated.getSender(),

							updated.getReceiver()

					);

			messagingTemplate.convertAndSend(

					"/topic/chat/"

							+ topic +

							"/edit",

					updated

			);

		} catch (Exception e) {
			System.out.println("Error editing message via WebSocket: " + e.getMessage());
		}
	}

	/**
	 * ✅ REST API: Get all users chat list for a specific user
	 */
	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getAllUsersChatList(@PathVariable String userId) {
		try {
			List<ChatResponse> chatList = chatService.getAllUsersChatList(userId);
			if (chatList.isEmpty()) {
				return new ResponseEntity<>("No chat history found for this user", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(chatList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching chat list: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ REST API: Get all admins chat list for a specific user
	 */
	@GetMapping("/admins/{userId}")
	public ResponseEntity<?> getAllAdminsChatList(@PathVariable String userId) {
		try {
			List<ChatResponse> chatList = chatService.getAllAdminsChatList(userId);
			if (chatList.isEmpty()) {
				return new ResponseEntity<>("No admin chat history found for this user", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(chatList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching admin chat list: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ REST API: Get all center admins chat list for a specific user
	 */
	@GetMapping("/center-admins/{userId}")
	public ResponseEntity<?> getAllCenterAdminChatList(@PathVariable String userId) {
		try {
			List<ChatResponse> chatList = chatService.getAllCenterAdminChatList(userId);
			if (chatList.isEmpty()) {
				return new ResponseEntity<>("No center admin chat history found for this user", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(chatList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching center admin chat list: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ✅ REST API: Get all admins chat list for a specific user based on district
	 */
	@GetMapping("/admins/{userId}/district/{district}")
	public ResponseEntity<?> getAllAdminByDistrictChatList(@PathVariable String district, @PathVariable String userId) {
		try {
			return ResponseEntity.status(200).body(chatService.getAllAdminsChatListFromDistrict(district, userId));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	private String getTopic(String a, String b) {

		return

		a.compareTo(b) < 0

				?

				a + "_" + b

				:

				b + "_" + a;

	}

}