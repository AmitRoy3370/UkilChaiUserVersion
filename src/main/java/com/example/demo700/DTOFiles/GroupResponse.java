package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GroupResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 96L;
	private String id;
	private String groupName;
	private String createdBy, creatorName; // গ্রুপ ক্রিয়েটরের ইউজার আইডি
	private List<String> members = new ArrayList<>(); // গ্রুপের সদস্য লিস্ট
	private List<String> membersName = new ArrayList<>();
	private Instant createdAt;
	private String groupIcon; // অপশনাল: গ্রুপের ছবি

	public GroupResponse(String id, String groupName, String createdBy, String creatorName, List<String> members,
			List<String> membersName, Instant createdAt, String groupIcon) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.createdBy = createdBy;
		this.creatorName = creatorName;
		this.members = members;
		this.membersName = membersName;
		this.createdAt = createdAt;
		this.groupIcon = groupIcon;
	}

	public GroupResponse() {
		super();
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public List<String> getMembersName() {
		return membersName;
	}

	public void setMembersName(List<String> membersName) {
		this.membersName = membersName;
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
		return "GroupResponse [id=" + id + ", groupName=" + groupName + ", createdBy=" + createdBy + ", creatorName="
				+ creatorName + ", members=" + members + ", membersName=" + membersName + ", createdAt=" + createdAt
				+ ", groupIcon=" + groupIcon + "]";
	}

}
