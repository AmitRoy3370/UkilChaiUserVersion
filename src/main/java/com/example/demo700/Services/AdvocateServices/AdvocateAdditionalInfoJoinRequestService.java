package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
import java.util.List;

import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;
import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfoJoinRequest;

public interface AdvocateAdditionalInfoJoinRequestService {

	public AdvocateAdditionalInfoJoinRequest addAdditional(AdvocateAdditionalInfoJoinRequest info, String userId);
	public AdvocateAdditionalInfoJoinRequest updateAdditional(AdvocateAdditionalInfoJoinRequest info, String userId, String id);
	
	public AdvocateAdditionalInfoJoinRequest findById(String id);
	public List<AdvocateAdditionalInfoJoinRequest> findAll();
	public AdvocateAdditionalInfoJoinRequest findByAdvocateId(String advocateId);
	public List<AdvocateAdditionalInfoJoinRequest> findByParmanentAdressContainingIgnoreCase(String parmanentAdress);
	public List<AdvocateAdditionalInfoJoinRequest> findByCityOfUpozilaContainingIgnoreCase(String cityOfUpozila);
	public List<AdvocateAdditionalInfoJoinRequest> findByNidNumberContainingIgnoreCase(String nidNumber);
	public AdvocateAdditionalInfoJoinRequest findByNidNumber(String nidNumber);
	public List<AdvocateAdditionalInfoJoinRequest> findByDateOfBirthAfter(Instant dateOfBirth);
	public List<AdvocateAdditionalInfoJoinRequest> findByDateOfBirthBefore(Instant dateOfBirth);
	public AdvocateAdditionalInfoJoinRequest findByWhatsAppNumber(String whatsAppNumber);
	
	public boolean deleteAdditionalInfo(String id, String userId);
	
}
