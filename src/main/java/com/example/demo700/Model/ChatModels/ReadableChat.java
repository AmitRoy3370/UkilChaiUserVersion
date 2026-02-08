package com.example.demo700.Model.ChatModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "ChatReadablity")
public class ReadableChat {

	@Id
	private String id;

	@NonNull
	private String chatId;

	private boolean isRead;

	public ReadableChat(String chatId, boolean isRead) {
		super();
		this.chatId = chatId;
		this.isRead = isRead;
	}

	public ReadableChat() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "ReadableChat [id=" + id + ", chatId=" + chatId + ", isRead=" + isRead + "]";
	}

}
