package com.example.demo700.Model.AdvocateModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "AdvocateWorkingExperienceInJoinRequest")
public class AdvocateWorkingExperienceJoinRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 147570L;

	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String advocateId;

	@Indexed
	private String barAssociation;

	@Indexed
	private String barControllEnrolment;

	@Indexed
	private Instant enrollmentDate = Instant.now();

	@Indexed
	private String practicingCoat;

	@Indexed
	private String primaryPractice;

	@Indexed
	private int yearOfLegalPractice;

	@Indexed
	@NonNull
	private String currentChemberName;

	@Indexed
	private int professionalExperience;

	public AdvocateWorkingExperienceJoinRequest(String advocateId, String barAssociation, String barControllEnrolment,
			Instant enrollmentDate, String practicingCoat, String primaryPractice, int yearOfLegalPractice,
			String currentChemberName, int professionalExperience) {
		super();
		this.advocateId = advocateId;
		this.barAssociation = barAssociation;
		this.barControllEnrolment = barControllEnrolment;
		this.enrollmentDate = enrollmentDate;
		this.practicingCoat = practicingCoat;
		this.primaryPractice = primaryPractice;
		this.yearOfLegalPractice = yearOfLegalPractice;
		this.currentChemberName = currentChemberName;
		this.professionalExperience = professionalExperience;
	}

	public AdvocateWorkingExperienceJoinRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getBarAssociation() {
		return barAssociation;
	}

	public void setBarAssociation(String barAssociation) {
		this.barAssociation = barAssociation;
	}

	public String getBarControllEnrolment() {
		return barControllEnrolment;
	}

	public void setBarControllEnrolment(String barControllEnrolment) {
		this.barControllEnrolment = barControllEnrolment;
	}

	public Instant getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Instant enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public String getPracticingCoat() {
		return practicingCoat;
	}

	public void setPracticingCoat(String practicingCoat) {
		this.practicingCoat = practicingCoat;
	}

	public String getPrimaryPractice() {
		return primaryPractice;
	}

	public void setPrimaryPractice(String primaryPractice) {
		this.primaryPractice = primaryPractice;
	}

	public int getYearOfLegalPractice() {
		return yearOfLegalPractice;
	}

	public void setYearOfLegalPractice(int yearOfLegalPractice) {
		this.yearOfLegalPractice = yearOfLegalPractice;
	}

	public String getCurrentChemberName() {
		return currentChemberName;
	}

	public void setCurrentChemberName(String currentChemberName) {
		this.currentChemberName = currentChemberName;
	}

	public int getProfessionalExperience() {
		return professionalExperience;
	}

	public void setProfessionalExperience(int professionalExperience) {
		this.professionalExperience = professionalExperience;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AdvocateWorkingExperienceJoinRequest [id=" + id + ", advocateId=" + advocateId + ", barAssociation="
				+ barAssociation + ", barControllEnrolment=" + barControllEnrolment + ", enrollmentDate="
				+ enrollmentDate + ", practicingCoat=" + practicingCoat + ", primaryPractice=" + primaryPractice
				+ ", yearOfLegalPractice=" + yearOfLegalPractice + ", currentChemberName=" + currentChemberName
				+ ", professionalExperience=" + professionalExperience + "]";
	}

}
