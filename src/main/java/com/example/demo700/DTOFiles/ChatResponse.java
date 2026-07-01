package com.example.demo700.DTOFiles;

import java.time.Instant;

public class ChatResponse {

	private String id;
	private String senderId;
	private String senderName, senderFullName;
	private SenderInfo senderInfo;
	private ReceiverInfo receiverInfo;
	private Instant timeStamp;

	// Nested DTO for sender information
	public static class SenderInfo {
		private String receiverName, receiverFullName;
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

		public SenderInfo(String receiverName, String receiverFullName, String receiverId, String message,
				boolean readChat) {
			super();
			this.receiverName = receiverName;
			this.receiverFullName = receiverFullName;
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

		public boolean isReadChat() {
			return readChat;
		}

		public void setReadChat(boolean readChat) {
			this.readChat = readChat;
		}

		@Override
		public String toString() {
			return "SenderInfo [receiverName=" + receiverName + ", receiverFullName=" + receiverFullName
					+ ", receiverId=" + receiverId + ", message=" + message + ", readChat=" + readChat + "]";
		}

	}

	// Nested DTO for receiver information
	public static class ReceiverInfo {
		private String senderId;
		private String senderName, senderFullName;
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

		public ReceiverInfo(String senderId, String senderName, String senderFullName, String message,
				boolean readChat) {
			super();
			this.senderId = senderId;
			this.senderName = senderName;
			this.senderFullName = senderFullName;
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

		public boolean isReadChat() {
			return readChat;
		}

		public void setReadChat(boolean readChat) {
			this.readChat = readChat;
		}

		public String getSenderFullName() {
			return senderFullName;
		}

		public void setSenderFullName(String senderFullName) {
			this.senderFullName = senderFullName;
		}

		@Override
		public String toString() {
			return "ReceiverInfo [senderId=" + senderId + ", senderName=" + senderName + ", senderFullName="
					+ senderFullName + ", message=" + message + ", readChat=" + readChat + "]";
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

	public ChatResponse(String id, String senderId, String senderName, String senderFullName, SenderInfo senderInfo,
			ReceiverInfo receiverInfo, Instant timeStamp) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderFullName = senderFullName;
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

	public String getSenderFullName() {
		return senderFullName;
	}

	public void setSenderFullName(String senderFullName) {
		this.senderFullName = senderFullName;
	}

	@Override
	public String toString() {
		return "ChatResponse [id=" + id + ", senderId=" + senderId + ", senderName=" + senderName + ", senderFullName="
				+ senderFullName + ", senderInfo=" + senderInfo + ", receiverInfo=" + receiverInfo + ", timeStamp="
				+ timeStamp + "]";
	}

}