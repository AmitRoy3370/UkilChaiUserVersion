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

	private boolean read;

	public ReadableChat(String chatId, boolean read) {
		super();
		this.chatId = chatId;
		this.read = read;
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
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public String toString() {
		return "ReadableChat [id=" + id + ", chatId=" + chatId + ", isRead=" + read + "]";
	}

}
