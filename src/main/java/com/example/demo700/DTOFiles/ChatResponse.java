package com.example.demo700.DTOFiles;

import java.time.Instant;

public class ChatResponse {

	private String id;
	private String senderId;
	private String senderName;
	private SenderInfo senderInfo;
	private ReceiverInfo receiverInfo;
	private Instant timeStamp;
	
	// Nested DTO for sender information
	public static class SenderInfo {
		private String receiverName;
		private String receiverId;
		private String message;
		private boolean readChat;

		// Constructors
		public SenderInfo() {
		}

		public SenderInfo(String receiverName, String receiverId, String message, boolean readChat) {
			this.receiverName = receiverName;
			this.receiverId = receiverId;
			this.message = message;
			this.readChat = readChat;
		}

		// Getters and Setters
		public String getReceiverName() {
			return receiverName;
		}

		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}

		public String getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(String receiverId) {
			this.receiverId = receiverId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	// Nested DTO for receiver information
	public static class ReceiverInfo {
		private String senderId;
		private String senderName;
		private String message;
		private boolean readChat;

		// Constructors
		public ReceiverInfo() {
		}

		public ReceiverInfo(String senderId, String senderName, String message, boolean readChat) {
			this.senderId = senderId;
			this.senderName = senderName;
			this.message = message;
			this.readChat = readChat;
		}

		// Getters and Setters
		public String getSenderId() {
			return senderId;
		}

		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}

		public String getSenderName() {
			return senderName;
		}

		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "ReceiverInfo [senderId=" + senderId + ", senderName=" + senderName + ", message=" + message + "]";
		}

	}

	// Constructors for ChatResponse
	public ChatResponse() {
	}

	public ChatResponse(String id, String senderId, String senderName, SenderInfo senderInfo, ReceiverInfo receiverInfo,
			Instant timeStamp) {
		this.id = id;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderInfo = senderInfo;
		this.receiverInfo = receiverInfo;
		this.timeStamp = timeStamp;
	}

	// Getters and Setters for ChatResponse fields
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public SenderInfo getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(SenderInfo senderInfo) {
		this.senderInfo = senderInfo;
	}

	public ReceiverInfo getReceiverInfo() {
		return receiverInfo;
	}

	public void setReceiverInfo(ReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "ChatResponse [id=" + id + ", senderId=" + senderId + ", senderName=" + senderName + ", senderInfo="
				+ senderInfo + ", receiverInfo=" + receiverInfo + ", timeStamp=" + timeStamp + "]";
	}

}