package com.example.demo700.Services.ChatServices;

import java.util.List;

import com.example.demo700.Model.ChatModels.ReadableChat;

public interface ReadableChatService {

	public ReadableChat addReadability(ReadableChat readableChat, String userId);

	public ReadableChat updateReadability(ReadableChat readableChat, String userId, String id);

	public ReadableChat findById(String id);

	public List<ReadableChat> seeAll();

	public ReadableChat findByChatId(String chatId);

	public List<ReadableChat> findByIsRead(boolean isRead);

	public boolean removeReadability(String id, String userId);

}
