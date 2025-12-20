package com.example.demo700.Model.ChatModels;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "chat_messages")
@Data
public class ChatMessage {

	@Id
	private String id;

	private String sender;
	private String receiver;
	private String content;

	private Instant timeStamp;

	public ChatMessage(String sender, String receiver, String content) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.timeStamp = Instant.now();
	}

	public ChatMessage() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content
				+ ", timeStamp=" + timeStamp + "]";
	}

}
