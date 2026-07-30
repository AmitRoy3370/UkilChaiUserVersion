package com.example.demo700.Model.ChatModels;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "chat_groups")
@Data
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 98L;
	@Id
	private String id;
	private String groupName;
	private String createdBy; // গ্রুপ ক্রিয়েটরের ইউজার আইডি
	private List<String> members = new ArrayList<>(); // গ্রুপের সদস্য লিস্ট
	private Instant createdAt;
	private String groupIcon; // অপশনাল: গ্রুপের ছবি

	public Group() {
		this.createdAt = Instant.now();
	}

	public Group(String groupName, String createdBy, List<String> members) {
		this.groupName = groupName;
		this.createdBy = createdBy;
		this.members = members;
		this.createdAt = Instant.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", groupName=" + groupName + ", createdBy=" + createdBy + ", members=" + members
				+ ", createdAt=" + createdAt + ", groupIcon=" + groupIcon + "]";
	}

}
