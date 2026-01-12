package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "AppealHearings")
public class AppealHearings {

	@Id
	private String id;

	@NonNull
	private String hearingId;

	@NonNull
	private String reason;
	
	private Instant appealHearingTime = Instant.now();

	public AppealHearings(String hearingId, String reason) {
		super();
		this.hearingId = hearingId;
		this.reason = reason;
	}

	public AppealHearings() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHearingId() {
		return hearingId;
	}

	public void setHearingId(String hearingId) {
		this.hearingId = hearingId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Instant getAppealHearingTime() {
		return appealHearingTime;
	}

	public void setAppealHearingTime(Instant appealHearingTime) {
		this.appealHearingTime = appealHearingTime;
	}

	@Override
	public String toString() {
		return "AppealHearings [id=" + id + ", hearingId=" + hearingId + ", reason=" + reason + ", appealHearingTime="
				+ appealHearingTime + "]";
	}

}
