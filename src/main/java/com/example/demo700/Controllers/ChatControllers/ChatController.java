package com.example.demo700.Controllers.ChatControllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Services.ChatServices.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * ✅ WebSocket endpoint for sending live chat messages This method listens to
	 * messages sent from the client via /app/chat.send and forwards them to the
	 * receiver’s private queue (/user/{receiver}/queue/messages)
	 */
	@MessageMapping("/chat.send")
	public void sendMessage(@Payload ChatMessage message) {
		try {
			// Save to DB
			ChatMessage savedMessage = chatService.saveMessage(message);

			// Send to specific receiver via WebSocket
			messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", savedMessage);

		} catch (Exception e) {
			System.out.println("Error sending message: " + e.getMessage());
		}
	}

	/**
	 * ✅ REST API: Save a chat message (non-realtime HTTP version) Useful for
	 * fallback or REST testing (like Postman)
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
	 * ✅ REST API: Delete specific chat message by ID
	 */
	@DeleteMapping("/delete/{sender}/{receiver}/{chatId}")
	public ResponseEntity<?> deleteChatMessage(@PathVariable String sender, @PathVariable String receiver,
			@PathVariable String chatId) {

		try {
			boolean deleted = chatService.deleteChatMessage(sender, receiver, chatId);
			if (deleted) {
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

	// 🔹 REST edit chat message
	@PutMapping("/edit/{sender}/{chatId}")
	public ResponseEntity<?> editChatMessage(@PathVariable String sender, @PathVariable String chatId,
			@RequestParam String newContent) {

		try {
			ChatMessage updated = chatService.editChatMessage(sender, chatId, newContent);

			// Notify receiver via WebSocket
			messagingTemplate.convertAndSendToUser(updated.getReceiver(), "/queue/edit", updated);

			return new ResponseEntity<>(updated, HttpStatus.OK);

		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Unexpected error editing message", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 🔹 WebSocket edit message in real time
	@MessageMapping("/chat.edit")
	public void editMessageWebSocket(@Payload ChatMessage message) {
		try {
			ChatMessage updated = chatService.editChatMessage(message.getSender(), message.getId(),
					message.getContent());

			// send update to both sender & receiver
			messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/edit", updated);
			messagingTemplate.convertAndSendToUser(message.getSender(), "/queue/edit", updated);

		} catch (Exception e) {
			System.out.println("Error editing message via WebSocket: " + e.getMessage());
		}
	}

}
