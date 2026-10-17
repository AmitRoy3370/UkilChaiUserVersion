package com.example.demo700.Services.AdvocateServices;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;

public interface AdvocateAdditionalInfoService {

	public AdvocateAdditionalInfo addAdditional(AdvocateAdditionalInfo info, String userId);
	public AdvocateAdditionalInfo updateAdditional(AdvocateAdditionalInfo info, String userId, String id);
	
	public AdvocateAdditionalInfo findById(String id);
	public List<AdvocateAdditionalInfo> findAll();
	public AdvocateAdditionalInfo findByAdvocateId(String advocateId);
	public List<AdvocateAdditionalInfo> findByParmanentAdressContainingIgnoreCase(String parmanentAdress);
	public List<AdvocateAdditionalInfo> findByCityOfUpozilaContainingIgnoreCase(String cityOfUpozila);
	public List<AdvocateAdditionalInfo> findByNidNumberContainingIgnoreCase(String nidNumber);
	public AdvocateAdditionalInfo findByNidNumber(String nidNumber);
	public List<AdvocateAdditionalInfo> findByDateOfBirthAfter(Instant dateOfBirth);
	public List<AdvocateAdditionalInfo> findByDateOfBirthBefore(Instant dateOfBirth);
	public AdvocateAdditionalInfo findByWhatsAppNumber(String whatsAppNumber);
	
	public boolean deleteAdditionalInfo(String id, String userId);
	
}
