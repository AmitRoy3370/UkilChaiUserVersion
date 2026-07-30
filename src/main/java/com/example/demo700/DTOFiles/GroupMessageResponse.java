package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class GroupMessageResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 95L;
	private String id;
	private String groupId;
	private String senderId;
	private String senderName;
	private String content;
	private Instant timestamp;
	private Set<String> readBy;
	private int readCount;
	private int totalMembers;
	private boolean edited;

	public GroupMessageResponse(String id, String groupId, String senderId, String senderName, String content,
			Instant timestamp, Set<String> readBy, int readCount, int totalMembers, boolean edited) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.senderId = senderId;
		this.senderName = senderName;
		this.content = content;
		this.timestamp = timestamp;
		this.readBy = readBy;
		this.readCount = readCount;
		this.totalMembers = totalMembers;
		this.edited = edited;
	}

	public GroupMessageResponse() {
		super();
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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getTotalMembers() {
		return totalMembers;
	}

	public void setTotalMembers(int totalMembers) {
		this.totalMembers = totalMembers;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	@Override
	public String toString() {
		return "GroupMessageResponse [id=" + id + ", groupId=" + groupId + ", senderId=" + senderId + ", senderName="
				+ senderName + ", content=" + content + ", timestamp=" + timestamp + ", readBy=" + readBy
				+ ", readCount=" + readCount + ", totalMembers=" + totalMembers + ", edited=" + edited + "]";
	}

}