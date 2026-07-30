package com.example.demo700.Controllers.ChatControllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import com.example.demo700.Services.ChatServices.GroupService;
import com.example.demo700.DTOFiles.GroupMessageResponse;
import com.example.demo700.DTOFiles.GroupResponse;
import com.example.demo700.Model.ChatModels.Group;
import com.example.demo700.Model.ChatModels.GroupMessage;

@RestController
@RequestMapping("/api/group-chat")
public class GroupChatController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ==================== গ্রুপ ম্যানেজমেন্ট এন্ডপয়েন্ট ====================

    /**
     * ✅ ১. নতুন গ্রুপ তৈরি করা
     * URL: POST /api/group-chat/create?creatorId={userId}
     * Body: গ্রুপের তথ্য (groupName, members ইত্যাদি)
     */
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group, 
                                        @RequestParam String creatorId) {
        try {
            Group created = groupService.createGroup(group, creatorId);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while creating group", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ২. নির্দিষ্ট গ্রুপের তথ্য দেখা
     * URL: GET /api/group-chat/{groupId}
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable String groupId) {
        try {
        	GroupResponse group = groupService.getGroupById(groupId);
            return ResponseEntity.ok(group);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while fetching group", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৩. একজন ইউজারের সব গ্রুপ দেখা
     * URL: GET /api/group-chat/my-groups/{userId}
     */
    @GetMapping("/my-groups/{userId}")
    public ResponseEntity<?> getUserGroups(@PathVariable String userId) {
        try {
            List<GroupResponse> groups = groupService.getUserGroups(userId);
            if (groups.isEmpty()) {
                return new ResponseEntity<>("No groups found for this user", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(groups);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while fetching user groups", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৪. গ্রুপের তথ্য আপডেট করা (শুধু গ্রুপ ক্রিয়েটর পারবেন)
     * URL: PUT /api/group-chat/update/{groupId}?userId={userId}
     * Body: আপডেট করা গ্রুপের তথ্য
     */
    @PutMapping("/update/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable String groupId,
                                        @RequestBody Group group,
                                        @RequestParam String userId) {
        try {
            Group updated = groupService.updateGroup(groupId, group, userId);
            return ResponseEntity.ok(updated);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while updating group", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৫. গ্রুপ ডিলিট করা (অ্যাডমিন বা গ্রুপ ক্রিয়েটর পারবেন)
     * URL: DELETE /api/group-chat/delete/{groupId}?userId={userId}
     */
    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupId,
                                        @RequestParam String userId) {
        try {
            boolean deleted = groupService.deleteGroup(groupId, userId);
            if (deleted) {
                // ওয়েবসকেটের মাধ্যমে গ্রুপ ডিলিট তথ্য সব সদস্যকে জানানো
                messagingTemplate.convertAndSend("/topic/group/deleted", Map.of("groupId", groupId));
                return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to delete group", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while deleting group", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৬. গ্রুপে নতুন সদস্য যোগ করা (শুধু গ্রুপ ক্রিয়েটর)
     * URL: POST /api/group-chat/{groupId}/add-member?memberId={memberId}&adminId={adminId}
     */
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<?> addMemberToGroup(@PathVariable String groupId,
                                             @RequestParam String memberId,
                                             @RequestParam String adminId) {
        try {
            boolean added = groupService.addMemberToGroup(groupId, memberId, adminId);
            if (added) {
                // ওয়েবসকেটের মাধ্যমে নতুন সদস্য যোগের তথ্য সবাইকে জানানো
                messagingTemplate.convertAndSend("/topic/group/" + groupId + "/member-added", 
                    Map.of("groupId", groupId, "newMemberId", memberId));
                return new ResponseEntity<>("Member added successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Member already in group", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while adding member", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৭. গ্রুপ থেকে সদস্য সরানো (শুধু গ্রুপ ক্রিয়েটর)
     * URL: DELETE /api/group-chat/{groupId}/remove-member?memberId={memberId}&adminId={adminId}
     */
    @DeleteMapping("/{groupId}/remove-member")
    public ResponseEntity<?> removeMemberFromGroup(@PathVariable String groupId,
                                                  @RequestParam String memberId,
                                                  @RequestParam String adminId) {
        try {
            boolean removed = groupService.removeMemberFromGroup(groupId, memberId, adminId);
            if (removed) {
                // ওয়েবসকেটের মাধ্যমে সদস্য সরানোর তথ্য সবাইকে জানানো
                messagingTemplate.convertAndSend("/topic/group/" + groupId + "/member-removed", 
                    Map.of("groupId", groupId, "removedMemberId", memberId));
                return new ResponseEntity<>("Member removed successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to remove member", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while removing member", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== গ্রুপ মেসেজিং এন্ডপয়েন্ট ====================

    /**
     * ✅ ৮. গ্রুপে মেসেজ পাঠানো (REST API - HTTP)
     * URL: POST /api/group-chat/send-message
     * Body: মেসেজের তথ্য (groupId, senderId, content)
     */
    @PostMapping("/send-message")
    public ResponseEntity<?> sendGroupMessage(@RequestBody GroupMessage message) {
        try {
            GroupMessage saved = groupService.sendGroupMessage(message);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while sending message", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ৯. ওয়েবসকেটের মাধ্যমে গ্রুপ মেসেজ পাঠানো (রিয়েল-টাইম)
     * ক্লায়েন্ট থেকে পাঠানোর এন্ডপয়েন্ট: /app/group.send
     */
    @MessageMapping("/group.send")
    public void sendGroupMessageWebSocket(@Payload GroupMessage message) {
        try {
            GroupMessage saved = groupService.sendGroupMessage(message);
            // মেসেজ ইতিমধ্যে সার্ভিসে সব সদস্যের কাছে ব্রডকাস্ট হয়ে গেছে
            System.out.println("Group message sent via WebSocket to group: " + message.getGroupId());
        } catch (Exception e) {
            System.out.println("Error sending group message via WebSocket: " + e.getMessage());
        }
    }

    /**
     * ✅ ১০. গ্রুপের সব মেসেজ দেখা (পেজিনেশন সহ)
     * URL: GET /api/group-chat/messages/{groupId}?userId={userId}&page=0&size=20
     */
    @GetMapping("/messages/{groupId}")
    public ResponseEntity<?> getGroupMessages(@PathVariable String groupId,
                                             @RequestParam String userId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        try {
            List<GroupMessageResponse> messages = groupService.getGroupMessages(groupId, userId, page, size);
            if (messages.isEmpty()) {
                return new ResponseEntity<>("No messages found in this group", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(messages);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while fetching messages", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ১১. গ্রুপ মেসেজ এডিট করা (REST API)
     * URL: PUT /api/group-chat/edit-message/{messageId}?userId={userId}&newContent={newContent}
     */
    @PutMapping("/edit-message/{messageId}")
    public ResponseEntity<?> editGroupMessage(@PathVariable String messageId,
                                             @RequestParam String userId,
                                             @RequestParam String newContent) {
        try {
            GroupMessage edited = groupService.editGroupMessage(messageId, newContent, userId);
            
            // ওয়েবসকেটের মাধ্যমে এডিট করা মেসেজ সব সদস্যকে জানানো
            messagingTemplate.convertAndSend("/topic/group/" + edited.getGroupId() + "/edit", edited);
            
            return ResponseEntity.ok(edited);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while editing message", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ১২. ওয়েবসকেটের মাধ্যমে গ্রুপ মেসেজ এডিট করা (রিয়েল-টাইম)
     * ক্লায়েন্ট থেকে পাঠানোর এন্ডপয়েন্ট: /app/group.edit
     */
    @MessageMapping("/group.edit")
    public void editGroupMessageWebSocket(@Payload GroupMessage message) {
        try {
            GroupMessage edited = groupService.editGroupMessage(
                message.getId(), 
                message.getContent(), 
                message.getSenderId()
            );
            
            // এডিট করা মেসেজ সব সদস্যকে জানানো
            messagingTemplate.convertAndSend("/topic/group/" + edited.getGroupId() + "/edit", edited);
            
        } catch (Exception e) {
            System.out.println("Error editing group message via WebSocket: " + e.getMessage());
        }
    }

    /**
     * ✅ ১৩. গ্রুপ মেসেজ ডিলিট করা (REST API)
     * URL: DELETE /api/group-chat/delete-message/{messageId}?userId={userId}
     */
    @DeleteMapping("/delete-message/{messageId}")
    public ResponseEntity<?> deleteGroupMessage(@PathVariable String messageId,
                                               @RequestParam String userId) {
        try {
            // ডিলিট করার আগে মেসেজের গ্রুপ আইডি জানার জন্য মেসেজ ফেচ করা
            GroupMessage message = groupService.getGroupMessageById(messageId);
            String groupId = message.getGroupId();
            
            boolean deleted = groupService.deleteGroupMessage(messageId, userId);
            if (deleted) {
                // ওয়েবসকেটের মাধ্যমে ডিলিট তথ্য সব সদস্যকে জানানো
                messagingTemplate.convertAndSend("/topic/group/" + groupId + "/delete", 
                                                 Map.of("messageId", messageId));
                return new ResponseEntity<>("Message deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to delete message", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while deleting message", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ১৪. ওয়েবসকেটের মাধ্যমে গ্রুপ মেসেজ ডিলিট করা (রিয়েল-টাইম)
     * ক্লায়েন্ট থেকে পাঠানোর এন্ডপয়েন্ট: /app/group.delete
     * ক্লায়েন্ট থেকে পাঠাতে হবে: { "messageId": "xxx", "userId": "xxx" }
     */
    @MessageMapping("/group.delete")
    public void deleteGroupMessageWebSocket(@Payload Map<String, String> deleteRequest) {
        try {
            String messageId = deleteRequest.get("messageId");
            String userId = deleteRequest.get("userId");
            
            // ডিলিট করার আগে মেসেজের গ্রুপ আইডি জানার জন্য মেসেজ ফেচ করা
            GroupMessage message = groupService.getGroupMessageById(messageId);
            String groupId = message.getGroupId();
            
            boolean deleted = groupService.deleteGroupMessage(messageId, userId);
            if (deleted) {
                // ওয়েবসকেটের মাধ্যমে ডিলিট তথ্য সব সদস্যকে জানানো
                messagingTemplate.convertAndSend("/topic/group/" + groupId + "/delete", 
                                                 Map.of("messageId", messageId));
                System.out.println("Group message deleted via WebSocket: " + messageId);
            }
        } catch (Exception e) {
            System.out.println("Error deleting group message via WebSocket: " + e.getMessage());
        }
    }

    // ==================== রিড রিসিপ্ট এন্ডপয়েন্ট ====================

    /**
     * ✅ ১৫. মেসেজ রিড মার্ক করা (REST API)
     * URL: PUT /api/group-chat/mark-read/{messageId}?userId={userId}
     */
    @PutMapping("/mark-read/{messageId}")
    public ResponseEntity<?> markMessageAsRead(@PathVariable String messageId,
                                              @RequestParam String userId) {
        try {
            groupService.markMessageAsRead(messageId, userId);
            return ResponseEntity.ok(Map.of("message", "Message marked as read", 
                                           "messageId", messageId, 
                                           "userId", userId));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while marking message as read", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ১৬. ওয়েবসকেটের মাধ্যমে মেসেজ রিড মার্ক করা (রিয়েল-টাইম)
     * ক্লায়েন্ট থেকে পাঠানোর এন্ডপয়েন্ট: /app/group.mark-read
     */
    @MessageMapping("/group.mark-read")
    public void markMessageAsReadWebSocket(@Payload Map<String, String> readRequest) {
        try {
            String messageId = readRequest.get("messageId");
            String userId = readRequest.get("userId");
            
            groupService.markMessageAsRead(messageId, userId);
            // রিড রিসিপ্ট ইতিমধ্যে সার্ভিসে ব্রডকাস্ট হয়ে গেছে
            
        } catch (Exception e) {
            System.out.println("Error marking message as read via WebSocket: " + e.getMessage());
        }
    }

    /**
     * ✅ ১৭. গ্রুপের আনরিড মেসেজ কাউন্ট দেখা
     * URL: GET /api/group-chat/unread-count/{groupId}/{userId}
     */
    @GetMapping("/unread-count/{groupId}/{userId}")
    public ResponseEntity<?> getUnreadCount(@PathVariable String groupId,
                                           @PathVariable String userId) {
        try {
            int count = groupService.getUnreadCount(groupId, userId);
            return ResponseEntity.ok(Map.of(
                "groupId", groupId,
                "userId", userId,
                "unreadCount", count
            ));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while getting unread count", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== বাড়তি ইউটিলিটি এন্ডপয়েন্ট ====================

    /**
     * ✅ ১৮. একাধিক গ্রুপের আনরিড কাউন্ট একসাথে দেখা
     * URL: POST /api/group-chat/unread-counts/{userId}
     * Body: গ্রুপ আইডির লিস্ট
     */
    @PostMapping("/unread-counts/{userId}")
    public ResponseEntity<?> getMultipleUnreadCounts(@PathVariable String userId,
                                                     @RequestBody List<String> groupIds) {
        try {
            Map<String, Integer> unreadCounts = groupIds.stream()
                .collect(Collectors.toMap(
                    groupId -> groupId,
                    groupId -> groupService.getUnreadCount(groupId, userId)
                ));
            return ResponseEntity.ok(unreadCounts);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while getting unread counts", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ✅ ১৯. নির্দিষ্ট গ্রুপ মেসেজ দেখা (আইডি দিয়ে)
     * URL: GET /api/group-chat/message/{messageId}
     * নোট: এই মেথডটি সার্ভিসে যোগ করতে হবে
     */
    @GetMapping("/message/{messageId}")
    public ResponseEntity<?> getGroupMessageById(@PathVariable String messageId) {
        try {
            GroupMessage message = groupService.getGroupMessageById(messageId);
            return ResponseEntity.ok(message);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error while fetching message", 
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}