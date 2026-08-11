package com.example.demo700.Model.UserModels;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Director")
public class Director implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 51L;

	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String userId;

	@Indexed
	private String position;
	
	@NonNull
	private String nid;

	public Director(String userId, String nid, String position) {
		super();
		this.userId = userId;
		this.nid = nid;
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Director [id=" + id + ", userId=" + userId + ", position=" + position + ", nid=" + nid + "]";
	}

}
