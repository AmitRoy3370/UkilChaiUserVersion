package com.example.demo700.Services.ChatServices;

import java.util.List;

import com.example.demo700.DTOFiles.ChatResponse;
import com.example.demo700.Model.ChatModels.ChatMessage;

public interface ChatService {
	
	public ChatMessage saveMessage(ChatMessage message);
	public List<ChatMessage> getChatHistory(String user1, String user2);
	public boolean deleteChatMessage(String sender, String receiver, String chatId);
	public ChatMessage editChatMessage(String sender, String chatId, String newContent);
    public List<ChatResponse> getAllUsersChatList(String userId);
    public List<ChatResponse> getAllAdminsChatList(String userId);
    public List<ChatResponse> getAllCenterAdminChatList(String userId);
    public List<ChatResponse> getAllAdminsChatListFromDistrict(String district, String userId);
    
}