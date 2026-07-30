package com.example.demo700.Model.ChatModels;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "group_messages")
@Data
public class GroupMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 94L;

	@Id
	private String id;

	@Indexed
	private String groupId;

	@Indexed
	private String senderId;

	private String content;

	@Indexed
	private Instant timestamp;

	private Set<String> readBy = new HashSet<>(); // যারা মেসেজ পড়েছেন তাদের আইডি

	private boolean edited = false;
	private Instant editedAt;

	public GroupMessage() {
		this.timestamp = Instant.now();
	}

	public GroupMessage(String groupId, String senderId, String content) {
		this.groupId = groupId;
		this.senderId = senderId;
		this.content = content;
		this.timestamp = Instant.now();
		this.readBy = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Set<String> getReadBy() {
		return readBy;
	}

	public void setReadBy(Set<String> readBy) {
		this.readBy = readBy;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public Instant getEditedAt() {
		return editedAt;
	}

	public void setEditedAt(Instant editedAt) {
		this.editedAt = editedAt;
	}

	@Override
	public String toString() {
		return "GroupMessage [id=" + id + ", groupId=" + groupId + ", senderId=" + senderId + ", content=" + content
				+ ", timestamp=" + timestamp + ", readBy=" + readBy + ", edited=" + edited + ", editedAt=" + editedAt
				+ "]";
	}

}