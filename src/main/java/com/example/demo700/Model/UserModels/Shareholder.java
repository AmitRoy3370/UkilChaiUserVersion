package com.example.demo700.Model.UserModels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Shareholder")
public class Shareholder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52L;
	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String userId;

	@Indexed
	private String nid, tin;

	@Indexed
	private Map<String, List<Double>> sharePercentage = new HashMap<>();

	public Shareholder(String userId, String nid, String tin, Map<String, List<Double>> sharePercentage) {
		super();
		this.userId = userId;
		this.nid = nid;
		this.tin = tin;
		this.sharePercentage = sharePercentage;
	}

	public Shareholder() {
		super();
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

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public Map<String, List<Double>> getSharePercentage() {
		return sharePercentage;
	}

	public void setSharePercentage(Map<String, List<Double>> sharePercentage) {
		this.sharePercentage = sharePercentage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Shareholder [id=" + id + ", userId=" + userId + ", nid=" + nid + ", tin=" + tin + ", sharePercentage="
				+ sharePercentage + "]";
	}

}
