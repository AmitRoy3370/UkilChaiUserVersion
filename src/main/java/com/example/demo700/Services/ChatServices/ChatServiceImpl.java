package com.example.demo700.Services.ChatServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.ChatResponse;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Model.ChatModels.ReadableChat;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.ChatRepositories.ChatMessageRepository;
import com.example.demo700.Repositories.ChatRepositories.ReadableChatRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdminServices.CenterAdminService;
import com.example.demo700.Services.NotificationServices.NotificationService;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ReadableChatRepository readChatRepository;
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private CenterAdminService centerAdminService;

	@Override
	public ChatMessage saveMessage(ChatMessage message) {

		if (message == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User sender = userRepository.findById(message.getSender()).get();

			if (sender == null) {

				throw new Exception();

			}

			User receiver = userRepository.findById(message.getReceiver()).get();

			if (receiver == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("In valid sender or receiver user...");

		}

		message.setTimeStamp(Instant.now());
		ChatMessage saved = chatMessageRepository.save(message);

		User user = userRepository.findById(message.getSender()).get();

		String name = user.getName();

		// নোটিফিকেশন পাঠানো
		notificationService.sendNotification(message.getReceiver(), "New message from " + name);
		return saved;
	}

	@Override
	public List<ChatMessage> getChatHistory(String user1, String user2) {
		List<ChatMessage> sent = chatMessageRepository.findBySenderAndReceiver(user1, user2);
		List<ChatMessage> received = chatMessageRepository.findByReceiverAndSender(user1, user2);
		sent.addAll(received);
		sent.sort((m1, m2) -> m1.getTimeStamp().compareTo(m2.getTimeStamp()));
		return sent;
	}

	@Override
	public boolean deleteChatMessage(String sender, String receiver, String chatId) {

		if (sender == null || receiver == null || chatId == null) {

			throw new NullPointerException("False request...");

		}

		long count = chatMessageRepository.count();

		try {

			List<ChatMessage> chatMessasge1 = chatMessageRepository.findBySenderAndReceiver(sender, receiver);

			if (chatMessasge1.isEmpty()) {

				throw new Exception();

			}

			List<ChatMessage> chatMessage2 = chatMessageRepository.findByReceiverAndSender(receiver, sender);

			if (chatMessage2.isEmpty()) {

				throw new Exception();

			}

			List<String> chatIds = new ArrayList<>();

			for (ChatMessage i : chatMessasge1) {

				chatIds.add(i.getId());

			}

			if (!chatIds.contains(chatId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("In valid sender or receiver data...");

		}

		cleaner.removeChatMessage(chatId);

		return count != chatMessageRepository.count();
	}

	@Override
	public ChatMessage editChatMessage(String sender, String chatId, String newContent) {

		if (sender == null || chatId == null || newContent == null) {
			throw new NullPointerException("False request...");
		}

		// Find the message
		ChatMessage message = chatMessageRepository.findById(chatId)
				.orElseThrow(() -> new NoSuchElementException("Chat message not found..."));

		// Check sender ownership
		if (!message.getSender().equals(sender)) {
			throw new NoSuchElementException("You can edit only your own messages...");
		}

		// Update message
		message.setContent(newContent);
		message.setTimeStamp(Instant.now()); // update timestamp for last edit

		ChatMessage updated = chatMessageRepository.save(message);

		User user = userRepository.findById(message.getSender()).get();

		String name = user.getName();

		// Optional: Notify receiver about edit
		notificationService.sendNotification(message.getReceiver(), "Message edited by " + name);

		return updated;
	}

	@Override
	public List<ChatResponse> getAllUsersChatList(String userId) {

		try {

			List<ChatResponse> list = getChatResponseFromUser(userId);

			if (list.isEmpty()) {

				throw new Exception("No such user find at here...");

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	public List<ChatResponse> getAllAdminsChatList(String userId) {

		try {

			List<Admin> admins = adminRepository.findAll();

			List<String> allUsersId = admins.stream().map(Admin::getUserId).collect(Collectors.toList());

			List<User> users = userRepository.findAllById(allUsersId);

			List<ChatResponse> list = getChatResponseFromListUser(userId, users);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

	}

	@Override
	public List<ChatResponse> getAllAdminsChatListFromDistrict(String district, String userId) {

		try {

			List<Admin> admins = centerAdminService.findAdminByDistricts(district);

			if (admins.isEmpty()) {

				throw new Exception("No such admin present at here...");

			}

			List<String> allUserIds = admins.stream().filter(Objects::nonNull).map(Admin::getUserId)
					.collect(Collectors.toList());

			List<User> users = userRepository.findAllById(allUserIds);

			List<ChatResponse> list = getChatResponseFromListUser(userId, users);

			if (list.isEmpty()) {

				throw new Exception("No such element find at here...");

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	@Override
	public List<ChatResponse> getAllCenterAdminChatList(String userId) {

		try {

			List<CenterAdmin> centerAdmins = centerAdminRepository.findAll();

			List<String> allUserId = centerAdmins.stream().map(CenterAdmin::getUserId).collect(Collectors.toList());

			List<User> allUsers = userRepository.findAllById(allUserId);

			List<ChatResponse> list = getChatResponseFromListUser(userId, allUsers);

			if (list.isEmpty()) {

				throw new Exception("No such user find at here...");

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException(e.getMessage());

		}

	}

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private List<ChatResponse> getChatResponseFromListUser(String userId, List<User> allUsers) {

		List<ChatResponse> responses = new ArrayList<>();


		CompletableFuture<Map<String, Boolean>> readChatFuture = CompletableFuture.supplyAsync(() -> readChatRepository.findByRead(false).stream().collect(Collectors.toMap(ReadableChat::getChatId, readChat -> readChat.isRead(), (existing, replacement) -> existing)) , executors);
		
		Map<String, Boolean> readChat = readChatFuture.join();
		
		
		CompletableFuture<Map<String, User>> nameFutures = CompletableFuture
				.supplyAsync(
						() -> allUsers
								.isEmpty()
										? new HashMap<>()
										: allUsers.stream().filter(Objects::nonNull).collect(Collectors.toMap(
												User::getId, Function.identity(), (existing, replacement) -> existing)),
						executors);

		CompletableFuture<List<ChatMessage>> senderMessagesFuture = CompletableFuture
				.supplyAsync(() -> chatMessageRepository.findBySender(userId), executors);

		CompletableFuture<List<ChatMessage>> receiverMessagesFuture = CompletableFuture
				.supplyAsync(() -> chatMessageRepository.findByReceiver(userId), executors);

		CompletableFuture<Map<String, ChatMessage>> latestMessageFuture = CompletableFuture.supplyAsync(() -> {
			List<ChatMessage> messages = chatMessageRepository.findAllConversationsWithLatestMessage(userId);
			if (messages == null || messages.isEmpty()) {
				return new HashMap<>();
			}

			return messages.stream().filter(Objects::nonNull).filter(msg -> {
				// Check for null sender or receiver
				if (msg.getSender() == null || msg.getReceiver() == null) {
					System.err.println("Skipping message with null sender/receiver: " + msg.getId());
					return false;
				}
				return true;
			}).collect(Collectors.toMap(msg -> {
				// Safe equals check with null handling
				String sender = msg.getSender();
				String receiver = msg.getReceiver();
				String otherUserId;

				if (userId != null && userId.equals(sender)) {
					otherUserId = receiver;
				} else {
					otherUserId = sender;
				}

				return otherUserId != null ? otherUserId : "unknown_" + System.currentTimeMillis();
			}, Function.identity(), (existing, replacement) -> {
				// Keep the existing message (or you can compare timestamps)
				return existing;
			}));
		}, executors);

		CompletableFuture.allOf(nameFutures, senderMessagesFuture, receiverMessagesFuture, latestMessageFuture).join();

		Map<String, User> userMap = nameFutures.join();
		Map<String, ChatMessage> latestMessageMap = latestMessageFuture.join();
		List<ChatMessage> senderMessages = senderMessagesFuture.join();
		List<ChatMessage> receiverMessages = receiverMessagesFuture.join();

		// Build a composite key map for O(1) lookups: "sender_receiver" or
		// "receiver_sender"
		Map<String, ChatMessage> messageLookupMap = buildMessageLookupMap(senderMessages, receiverMessages);

		List<String> allUserId = allUsers.stream().filter(Objects::nonNull).map(User::getId)
				.collect(Collectors.toList());

		for (String otherUserId : allUserId) {

			try {

				User otherUser = userMap.get(otherUserId);
				if (otherUser == null)
					continue;

				ChatResponse response = new ChatResponse();

				User currentUser = userMap.get(userId);

				try {

					response.setSenderId(userId);
					response.setSenderName(currentUser != null ? currentUser.getName() : "Unknown");

				} catch (Exception e) {

				}

				// O(1) lookup instead of O(n) iteration
				ChatMessage latestMessage = getLatestMessageFromMap(userId, otherUserId, messageLookupMap,
						latestMessageMap);

				if (latestMessage != null) {
					response.setId(latestMessage.getId());
					response.setTimeStamp(latestMessage.getTimeStamp());

					try {

						boolean isCurrentUserSender = latestMessage.getSender().equals(userId);

						if (isCurrentUserSender) {
							response.setSenderInfo(new ChatResponse.SenderInfo(otherUser.getName(), otherUserId,
									latestMessage.getContent(), readChat.getOrDefault(latestMessage.getId(), true)));
							response.setReceiverInfo(null);
						} else {
							response.setSenderInfo(null);
							response.setReceiverInfo(new ChatResponse.ReceiverInfo(otherUserId, otherUser.getName(),
									latestMessage.getContent(), readChat.getOrDefault(latestMessage.getId(), true)));
						}

					} catch (Exception e) {

					}

				} else {

					try {

						response.setId(null);
						response.setTimeStamp(null);
						response.setSenderInfo(new ChatResponse.SenderInfo(otherUser.getName(), otherUserId, "", true));
						response.setReceiverInfo(null);

					} catch (Exception e) {

					}

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		try {

			responses.sort((r1, r2) -> {
				if (r1.getTimeStamp() == null && r2.getTimeStamp() == null)
					return 0;
				if (r1.getTimeStamp() == null)
					return 1;
				if (r2.getTimeStamp() == null)
					return -1;
				return r2.getTimeStamp().compareTo(r1.getTimeStamp());
			});

		} catch (Exception e) {

		}

		return responses;

	}

	private List<ChatResponse> getChatResponseFromUser(String userId) {
		List<ChatResponse> responses = new ArrayList<>();

		List<User> allUsers = userRepository.findAll();

		CompletableFuture<Map<String, Boolean>> readChatFuture = CompletableFuture.supplyAsync(() -> readChatRepository.findByRead(false).stream().collect(Collectors.toMap(ReadableChat::getChatId, readChat -> readChat.isRead(), (existing, replacement) -> existing)) , executors);
		
		Map<String, Boolean> readChat = readChatFuture.join();
		
		CompletableFuture<Map<String, User>> nameFutures = CompletableFuture
				.supplyAsync(
						() -> allUsers
								.isEmpty()
										? new HashMap<>()
										: allUsers.stream().filter(Objects::nonNull).collect(Collectors.toMap(
												User::getId, Function.identity(), (existing, replacement) -> existing)),
						executors);

		CompletableFuture<List<ChatMessage>> senderMessagesFuture = CompletableFuture
				.supplyAsync(() -> chatMessageRepository.findBySender(userId), executors);

		CompletableFuture<List<ChatMessage>> receiverMessagesFuture = CompletableFuture
				.supplyAsync(() -> chatMessageRepository.findByReceiver(userId), executors);

		
		CompletableFuture<Map<String, ChatMessage>> latestMessageFuture = CompletableFuture.supplyAsync(() -> {
			List<ChatMessage> messages = chatMessageRepository.findAllConversationsWithLatestMessage(userId);
			if (messages == null || messages.isEmpty()) {
				return new HashMap<>();
			}

			return messages.stream().filter(Objects::nonNull).filter(msg -> {
				// Check for null sender or receiver
				if (msg.getSender() == null || msg.getReceiver() == null) {
					System.err.println("Skipping message with null sender/receiver: " + msg.getId());
					return false;
				}
				return true;
			}).collect(Collectors.toMap(msg -> {
				// Safe equals check with null handling
				String sender = msg.getSender();
				String receiver = msg.getReceiver();
				String otherUserId;

				if (userId != null && userId.equals(sender)) {
					otherUserId = receiver;
				} else {
					otherUserId = sender;
				}

				return otherUserId != null ? otherUserId : "unknown_" + System.currentTimeMillis();
			}, Function.identity(), (existing, replacement) -> {
				// Keep the existing message (or you can compare timestamps)
				return existing;
			}));
		}, executors);

		CompletableFuture.allOf(nameFutures, senderMessagesFuture, receiverMessagesFuture, latestMessageFuture).join();

		Map<String, User> userMap = nameFutures.join();
		Map<String, ChatMessage> latestMessageMap = latestMessageFuture.join();
		List<ChatMessage> senderMessages = senderMessagesFuture.join();
		List<ChatMessage> receiverMessages = receiverMessagesFuture.join();

		// Build a composite key map for O(1) lookups: "sender_receiver" or
		// "receiver_sender"
		Map<String, ChatMessage> messageLookupMap = buildMessageLookupMap(senderMessages, receiverMessages);

		List<String> allUserId = allUsers.stream().map(User::getId).collect(Collectors.toList());

		for (String otherUserId : allUserId) {

			try {

				User otherUser = userMap.get(otherUserId);
				if (otherUser == null)
					continue;

				ChatResponse response = new ChatResponse();

				User currentUser = userMap.get(userId);

				try {

					response.setSenderId(userId);
					response.setSenderName(currentUser != null ? currentUser.getName() : "Unknown");

				} catch (Exception e) {

				}

				// O(1) lookup instead of O(n) iteration
				ChatMessage latestMessage = getLatestMessageFromMap(userId, otherUserId, messageLookupMap,
						latestMessageMap);

				if (latestMessage != null) {

					try {

						response.setId(latestMessage.getId());
						response.setTimeStamp(latestMessage.getTimeStamp());

					} catch (Exception e) {

					}

					try {

						boolean isCurrentUserSender = latestMessage.getSender().equals(userId);

						if (isCurrentUserSender) {
							response.setSenderInfo(new ChatResponse.SenderInfo(otherUser.getName(), otherUserId,
									latestMessage.getContent(), readChat.getOrDefault(latestMessage.getId(), readChat.getOrDefault(latestMessage.getId(), true))));
							response.setReceiverInfo(null);
						} else {
							response.setSenderInfo(null);
							response.setReceiverInfo(new ChatResponse.ReceiverInfo(otherUserId, otherUser.getName(),
									latestMessage.getContent(), readChat.getOrDefault(latestMessage.getId(), readChat.getOrDefault(latestMessage.getId(), true))));
						}

					} catch (Exception e) {

					}

				} else {

					try {

						response.setId(null);
						response.setTimeStamp(null);
						response.setSenderInfo(new ChatResponse.SenderInfo(otherUser.getName(), otherUserId, "", true));
						response.setReceiverInfo(null);

					} catch (Exception e) {

					}

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		try {

			responses.sort((r1, r2) -> {
				if (r1.getTimeStamp() == null && r2.getTimeStamp() == null)
					return 0;
				if (r1.getTimeStamp() == null)
					return 1;
				if (r2.getTimeStamp() == null)
					return -1;
				return r2.getTimeStamp().compareTo(r1.getTimeStamp());
			});

		} catch (Exception e) {

		}

		return responses;
	}

	// Build a map with composite key for O(1) lookups
	private Map<String, ChatMessage> buildMessageLookupMap(List<ChatMessage> senderMessages,
			List<ChatMessage> receiverMessages) {
		Map<String, ChatMessage> lookupMap = new HashMap<>();

		// Add sender messages with composite key "sender_receiver"
		for (ChatMessage msg : senderMessages) {
			String key = msg.getSender() + "_" + msg.getReceiver();
			ChatMessage existing = lookupMap.get(key);
			if (existing == null || msg.getTimeStamp().isAfter(existing.getTimeStamp())) {
				lookupMap.put(key, msg);
			}
		}

		// Add receiver messages with composite key "sender_receiver" (swap to maintain
		// consistency)
		for (ChatMessage msg : receiverMessages) {
			String key = msg.getSender() + "_" + msg.getReceiver();
			ChatMessage existing = lookupMap.get(key);
			if (existing == null || msg.getTimeStamp().isAfter(existing.getTimeStamp())) {
				lookupMap.put(key, msg);
			}
		}

		return lookupMap;
	}

	// O(1) lookup using composite key
	private ChatMessage getLatestMessageFromMap(String userId, String otherUserId,
			Map<String, ChatMessage> messageLookupMap, Map<String, ChatMessage> latestMessageMap) {
		// First try to get from pre-computed latestMessageMap (most efficient)
		ChatMessage latestMessage = latestMessageMap.get(otherUserId);
		if (latestMessage != null) {
			return latestMessage;
		}

		// Fallback to composite key lookup
		String key1 = userId + "_" + otherUserId;
		String key2 = otherUserId + "_" + userId;

		ChatMessage msg1 = messageLookupMap.get(key1);
		ChatMessage msg2 = messageLookupMap.get(key2);

		if (msg1 == null)
			return msg2;
		if (msg2 == null)
			return msg1;

		// Return the most recent one
		return msg1.getTimeStamp().isAfter(msg2.getTimeStamp()) ? msg1 : msg2;
	}

}
