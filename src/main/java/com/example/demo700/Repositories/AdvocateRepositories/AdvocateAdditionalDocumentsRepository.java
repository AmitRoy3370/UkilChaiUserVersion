package com.example.demo700.Repositories.AdvocateRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdvocateModels.AdvocateAdditionalInfo;

@Repository
public interface AdvocateAdditionalDocumentsRepository extends MongoRepository<AdvocateAdditionalInfo, String> {

	// ==================== BY ADVOCATE ID ====================
	@Query("{ 'advocateId': ?0 }")
	AdvocateAdditionalInfo findByAdvocateId(String advocateId);
	
	List<AdvocateAdditionalInfo> findByAdvocateIdIn(List<String> advocateId);

	// ==================== PERMANENT ADDRESS (case-insensitive contains) ====================
	@Query("{ 'parmanentAdress': { $regex: ?0, $options: 'i' } }")
	List<AdvocateAdditionalInfo> findByParmanentAdressContainingIgnoreCase(String parmanentAdress);

	// ==================== CITY OF UPOZILA (case-insensitive contains) ====================
	@Query("{ 'cityOfUpozila': { $regex: ?0, $options: 'i' } }")
	List<AdvocateAdditionalInfo> findByCityOfUpozilaContainingIgnoreCase(String cityOfUpozila);

	// ==================== NID NUMBER (case-insensitive contains) ====================
	@Query("{ 'nidNumber': { $regex: ?0, $options: 'i' } }")
	List<AdvocateAdditionalInfo> findByNidNumberContainingIgnoreCase(String nidNumber);

	// ==================== NID NUMBER (exact match) ====================
	@Query("{ 'nidNumber': ?0 }")
	AdvocateAdditionalInfo findByNidNumber(String nidNumber);

	// ==================== DATE OF BIRTH AFTER ====================
	@Query("{ 'dateOfBirth': { $gte: ?0 } }")
	List<AdvocateAdditionalInfo> findByDateOfBirthAfter(Instant dateOfBirth);

	// ==================== DATE OF BIRTH BEFORE ====================
	@Query("{ 'dateOfBirth': { $lte: ?0 } }")
	List<AdvocateAdditionalInfo> findByDateOfBirthBefore(Instant dateOfBirth);

	// ==================== WHATSAPP NUMBER (exact match) ====================
	@Query("{ 'whatsAppNumber': ?0 }")
	AdvocateAdditionalInfo findByWhatsAppNumber(String whatsAppNumber);
}