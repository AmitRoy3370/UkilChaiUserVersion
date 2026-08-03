package com.example.demo700.Services.ChatServices;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.GroupMessageResponse;
import com.example.demo700.DTOFiles.GroupResponse;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.ChatModels.Group;
import com.example.demo700.Model.ChatModels.GroupMessage;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.ChatRepositories.GroupMessageRepository;
import com.example.demo700.Repositories.ChatRepositories.GroupRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.NotificationServices.NotificationService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupMessageRepository groupMessageRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private Cleaner cleaner;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	private static final String cacheValue = "ChatGroup";

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public Group createGroup(Group group, String creatorId) {
		if (group == null || creatorId == null) {
			throw new NullPointerException("Invalid request data");
		}

		// ক্রিয়েটর ইউজার ভেরিফাই
		User creator = userRepository.findById(creatorId)
				.orElseThrow(() -> new NoSuchElementException("Creator user not found"));

		// গ্রুপ সেটআপ
		group.setCreatedBy(creatorId);
		group.setCreatedAt(Instant.now());

		// ক্রিয়েটরকে সদস্য হিসেবে যোগ করা
		if (group.getMembers() == null) {
			group.setMembers(new ArrayList<>());
		}
		if (!group.getMembers().contains(creatorId)) {
			group.getMembers().add(creatorId);
		}

		Group savedGroup = groupRepository.save(group);

		List<String> destinations = new ArrayList<>();

		destinations.add("GroupChat");
		destinations.add("GroupChatScreen");
		destinations.add("GroupChatScreen");

		Map<String, User> memberMap = userRepository.findAllById(savedGroup.getMembers()).stream()
				.collect(Collectors.toMap(User::getId, Function.identity()));

		// গ্রুপ ক্রিয়েশনের নোটিফিকেশন সব সদস্যকে পাঠানো
		for (String memberId : savedGroup.getMembers()) {

			Map<String, String> map = new HashMap<>();

			User sender = memberMap.get(memberId);

			map.put("groupId", group.getId());
			map.put("groupName", group.getGroupName());
			map.put("currentUserId", memberId);
			map.put("currentUserName", sender.getFullName() == null ? sender.getName() : sender.getFullName());
			map.put("isAdmin", "false");

			notificationService.sendNotification(memberId, "You have been added to group: " + savedGroup.getGroupName(),
					destinations, map);
		}

		return savedGroup;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public GroupMessage sendGroupMessage(GroupMessage message) {
		if (message == null || message.getGroupId() == null || message.getSenderId() == null) {
			throw new NullPointerException("Invalid message data");
		}

		// গ্রুপ ভেরিফাই
		Group group = groupRepository.findById(message.getGroupId())
				.orElseThrow(() -> new NoSuchElementException("Group not found"));

		// সেন্ডার গ্রুপের মেম্বার কিনা চেক
		if (!group.getMembers().contains(message.getSenderId())) {
			throw new SecurityException("User is not a member of this group");
		}

		// মেসেজ সেভ
		message.setTimestamp(Instant.now());
		message.setReadBy(new HashSet<>());
		message.getReadBy().add(message.getSenderId()); // সেন্ডার নিজের মেসেজ নিজে পড়েছে

		GroupMessage savedMessage = groupMessageRepository.save(message);

		// ওয়েবসকেটের মাধ্যমে সব সদস্যকে মেসেজ পাঠানো
		User sender = userRepository.findById(message.getSenderId()).get();

		List<String> destinations = new ArrayList<>();

		destinations.add("GroupChat");
		destinations.add("GroupChatScreen");
		destinations.add("GroupChatScreen");

		Map<String, User> memberMap = userRepository.findAllById(group.getMembers()).stream()
				.collect(Collectors.toMap(User::getId, Function.identity()));

		for (String memberId : group.getMembers()) {
			if (!memberId.equals(message.getSenderId())) {
				// পার্সোনাল নোটিফিকেশন
				messagingTemplate.convertAndSendToUser(memberId, "/queue/group-messages", savedMessage);

				Map<String, String> map = new HashMap<>();

				try {

					User _sender = memberMap.get(memberId);

					map.put("groupId", group.getId());
					map.put("groupName", group.getGroupName());
					map.put("currentUserId", memberId);
					map.put("currentUserName",
							_sender.getFullName() == null ? _sender.getName() : _sender.getFullName());
					map.put("isAdmin", "false");

				} catch (Exception e) {

					System.out.println(e);

				}

				// পুশ নোটিফিকেশন (অপশনাল)
				notificationService.sendNotification(memberId,
						"New message from " + sender.getName() + " in " + group.getGroupName(), destinations, map);
			}
		}

		// গ্রুপ টপিকেও মেসেজ পাঠানো (যারা অনলাইন)
		messagingTemplate.convertAndSend("/topic/group/" + message.getGroupId(), savedMessage);

		return savedMessage;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'getGroupMessage_' + #groupId + '_' + #userId + '_' + #page + '_' + #size")
	public List<GroupMessageResponse> getGroupMessages(String groupId, String userId, int page, int size) {
		if (groupId == null || userId == null) {
			throw new NullPointerException("Invalid request");
		}

		// গ্রুপ অ্যাক্সেস চেক
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new NoSuchElementException("Group not found"));

		if (!group.getMembers().contains(userId)) {
			throw new SecurityException("You are not a member of this group");
		}

		// পেজিনেশন সেটআপ
		Pageable pageable = PageRequest.of(page, size);
		List<GroupMessage> messages = groupMessageRepository.findMessagesByGroupIdWithPagination(groupId, pageable);

		// ইউজারের নামের ম্যাপ তৈরি
		List<GroupMessageResponse> responses = new ArrayList<>();

		for (GroupMessage msg : messages) {
			User sender = userRepository.findById(msg.getSenderId()).orElse(null);

			GroupMessageResponse response = new GroupMessageResponse();
			response.setId(msg.getId());
			response.setGroupId(msg.getGroupId());
			response.setSenderId(msg.getSenderId());
			response.setSenderName(sender != null ? sender.getName() : "Unknown");
			response.setContent(msg.getContent());
			response.setTimestamp(msg.getTimestamp());
			response.setReadBy(msg.getReadBy());
			response.setReadCount(msg.getReadBy().size());
			response.setTotalMembers(group.getMembers().size());
			response.setEdited(msg.isEdited());

			responses.add(response);
		}

		return responses;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public void markMessageAsRead(String messageId, String userId) {
		GroupMessage message = groupMessageRepository.findById(messageId)
				.orElseThrow(() -> new NoSuchElementException("Message not found"));

		if (!message.getReadBy().contains(userId)) {
			message.getReadBy().add(userId);
			groupMessageRepository.save(message);

			// রিড রিসিপ্ট আপডেট ওয়েবসকেটের মাধ্যমে
			messagingTemplate.convertAndSend("/topic/group/" + message.getGroupId() + "/read",
					Map.of("messageId", messageId, "readBy", userId));
		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'getUnreadCount_' + #groupId + '_' + #userId")
	public int getUnreadCount(String groupId, String userId) {
		List<GroupMessage> unreadMessages = groupMessageRepository.findUnreadMessagesByGroupId(groupId, userId);
		return unreadMessages.size();
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public boolean addMemberToGroup(String groupId, String memberId, String adminId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new NoSuchElementException("Group not found"));

		// শুধুমাত্র গ্রুপ ক্রিয়েটর বা অ্যাডমিন নতুন মেম্বার যোগ করতে পারবে
		if (!group.getCreatedBy().equals(adminId)) {
			throw new SecurityException("Only group creator can add members");
		}

		User newMember = userRepository.findById(memberId)
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		if (!group.getMembers().contains(memberId)) {
			group.getMembers().add(memberId);
			group = groupRepository.save(group);

			List<String> destinations = new ArrayList<>();

			destinations.add("GroupChat");
			destinations.add("GroupChatScreen");
			destinations.add("GroupChatScreen");

			Map<String, String> map = new HashMap<>();

			map.put("groupId", group.getId());
			map.put("groupName", group.getGroupName());
			map.put("currentUserId", memberId);
			map.put("currentUserName", newMember.getFullName() == null ? newMember.getName() : newMember.getFullName());
			map.put("isAdmin", "false");

			// নোটিফিকেশন পাঠানো
			notificationService.sendNotification(memberId, "You have been added to group: " + group.getGroupName(),
					destinations, map);

			return true;
		}

		return false;
	}

	@Override
	@Cacheable(value = cacheValue, key = "'getUserGroups_' + #userId")
	public List<GroupResponse> getUserGroups(String userId) {
		return getGroupResponse(groupRepository.findGroupsByMemberId(userId));
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public Group updateGroup(String groupId, Group group, String userId) {

		if (groupId == null | group == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			Group _group = groupRepository.findById(groupId).get();

			if (_group == null) {

				throw new Exception();

			}

			if (!_group.getCreatedBy().equals(userId)) {

				throw new Exception();

			}

			group.setCreatedBy(userId);

		} catch (Exception e) {

			throw new NoSuchElementException("No such group find at here....");

		}

		try {

			List<String> members = group.getMembers();

			for (String i : members) {

				User user = userRepository.findById(i).get();

				if (user == null) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("Group members info are not valid...");

		}

		group.setId(groupId);

		group = groupRepository.save(group);

		if (group == null) {

			throw new ArithmeticException("group not updated...");

		}

		return group;

	}

	@Override
	@Caching(evict = { @CacheEvict(value = cacheValue, allEntries = true),
	// @CacheEvict(value = "GroupChat", allEntries = true)
	})
	public boolean deleteGroup(String groupId, String userId) {

		if (userId == null || groupId == null) {

			throw new NullPointerException("False request...");

		}

		Group group = null;

		try {

			group = groupRepository.findById(groupId).get();

			if (group == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such group find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin == null) {

					throw new Exception();

				}

				if (centerAdmin != null) {

					long count = groupRepository.count();

					cleaner.removeGroup(groupId);

					return count != groupRepository.count();

				}

			} catch (Exception e) {

			}

			if (!user.getId().equals(group.getCreatedBy())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to delete this group...");

		}

		long count = groupRepository.count();

		cleaner.removeGroup(groupId);

		return count != groupRepository.count();

	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByGroupId_' + #groupId")
	public GroupResponse getGroupById(String groupId) {

		if (groupId == null) {

			throw new NullPointerException("False request....");

		}
		try {

			Group group = groupRepository.findById(groupId).get();

			if (group == null) {

				throw new Exception();

			}

			return getGroupResponse(group);

		} catch (Exception e) {

			throw new NoSuchElementException("No such group find at here....");

		}
	}

	@Override
	@Cacheable(value = cacheValue, key = "'findByGroupMessageId_' + #id")
	public GroupMessage getGroupMessageById(String id) {

		if (id == null) {

			throw new NullPointerException("False request....");

		}

		try {

			GroupMessage message = groupMessageRepository.findById(id).get();

			if (message == null) {

				throw new Exception();

			}

			return message;

		} catch (Exception e) {

			throw new NoSuchElementException("No such message exist at here...");

		}

	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public boolean removeMemberFromGroup(String groupId, String memberId, String adminId) {

		if (groupId == null || memberId == null || adminId == null) {

			throw new NullPointerException("False request...");

		}

		Group group = null;

		try {

			group = groupRepository.findById(groupId).get();

			if (group == null) {

				throw new Exception();

			}

			if (!group.getCreatedBy().equals(adminId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such group find at here....");

		}

		if (group.getMembers().contains(memberId)) {

			group.getMembers().remove(memberId);

		} else {

			throw new ArithmeticException("No such member present in that group...");

		}

		group = groupRepository.save(group);

		return group != null;

	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public GroupMessage editGroupMessage(String messageId, String newContent, String userId) {

		if (messageId == null || newContent == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		GroupMessage groupMessage = null;

		try {

			groupMessage = groupMessageRepository.findById(messageId).get();

			if (groupMessage == null) {

				throw new Exception();

			}

			if (!groupMessage.getSenderId().equals(userId)) {

				throw new Exception();

			}

			groupMessage.setContent(newContent);

		} catch (Exception e) {

			throw new NoSuchElementException("No such group message find at here...");

		}

		groupMessage = groupMessageRepository.save(groupMessage);

		if (groupMessage == null) {

			throw new ArithmeticException("group message is not edited...");

		}

		return groupMessage;
	}

	@Override
	@CacheEvict(value = cacheValue, allEntries = true)
	public boolean deleteGroupMessage(String messageId, String userId) {

		if (messageId == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		GroupMessage groupMessage = null;

		try {

			groupMessage = groupMessageRepository.findById(messageId).get();

			if (groupMessage == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such group message find at here...");

		}

		User user = null;

		try {

			user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin == null) {

					throw new Exception();

				}

				if (centerAdmin != null) {

					long count = groupMessageRepository.count();

					cleaner.removeGroupMessage(messageId);

					return count != groupMessageRepository.count();

				}

			} catch (Exception e) {

			}

			if (!user.getId().equals(groupMessage.getSenderId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to delete this message...");

		}

		long count = groupMessageRepository.count();

		cleaner.removeGroupMessage(messageId);

		return count != groupMessageRepository.count();

	}

	private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private GroupResponse getGroupResponse(Group group) {

		List<Group> list = new ArrayList<>();

		list.add(group);

		return getGroupResponse(list).get(0);

	}

	private List<GroupResponse> getGroupResponse(List<Group> list) {

		List<GroupResponse> responses = new ArrayList<>();

		CompletableFuture<List<String>> membersUserIdFuture = CompletableFuture.supplyAsync(
				() -> list.stream().filter(Objects::nonNull)
						.filter(group -> group.getMembers() != null && !group.getMembers().isEmpty())
						.flatMap(group -> group.getMembers().stream()).distinct().collect(Collectors.toList()),
				executors);

		CompletableFuture<List<String>> groupCreatorsUserIdFuture = CompletableFuture
				.supplyAsync(() -> list.stream().filter(Objects::nonNull).filter(group -> group.getCreatedBy() != null)
						.map(Group::getCreatedBy).collect(Collectors.toList()), executors);

		CompletableFuture<Map<String, User>> membersFuture = membersUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream().filter(Objects::nonNull)
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executors);

		CompletableFuture<Map<String, User>> groupCreatorFuture = groupCreatorsUserIdFuture.thenApplyAsync(usersId -> {

			if (usersId.isEmpty()) {

				return new HashMap<>();

			}

			return userRepository.findAllById(usersId).stream().filter(Objects::nonNull)
					.collect(Collectors.toMap(User::getId, Function.identity()));

		}, executors);

		CompletableFuture.allOf(membersUserIdFuture, groupCreatorsUserIdFuture, membersFuture, groupCreatorFuture)
				.join();

		Map<String, User> membersMap = membersFuture.getNow(new HashMap<>());
		Map<String, User> groupCreatorMap = groupCreatorFuture.getNow(new HashMap<>());

		for (Group group : list) {

			try {

				GroupResponse response = new GroupResponse();

				response.setId(group.getId());
				response.setCreatedAt(group.getCreatedAt());
				response.setCreatedBy(group.getCreatedBy());
				response.setCreatorName(groupCreatorMap.get(group.getCreatedBy()).getName());
				response.setGroupName(group.getGroupName());
				response.setGroupIcon(group.getGroupIcon());
				response.setMembers(group.getMembers());

				List<String> names = new ArrayList();

				for (String i : group.getMembers()) {

					names.add(membersMap.get(i).getName());

				}

				response.setMembersName(names);

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}