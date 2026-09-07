package com.example.demo700.Repositories.TinRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.TinModels.Tin;

@Repository
public interface TinRepository extends MongoRepository<Tin, String> {

	@Query("{ 'fullName': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByFullNameContainingIgnoreCase(String fullName);

	@Query("{ 'fatherName': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByFatherNameContainingIgnoreCase(String fatherName);

	@Query("{'phone' : ?0}")
	public Tin findByPhone(String phone);
	
	@Query("{ 'motherName': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByMotherNameContainingIgnoreCase(String motherName);

	@Query("{ 'phone': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByPhoneContainingIgnoreCase(String phone);

	@Query("{ 'dateOfBirth': { $lte: ?0 } }")
	public List<Tin> findByDateOfBirthBefore(Instant dateOfBirth);

	@Query("{ 'dateOfBirth': { $gte: ?0 } }")
	public List<Tin> findByDateOfBirthAfter(Instant dateOfBirth);

	@Query("{ 'presentAdress': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByPresentAdressContainingIgnoreCase(String presentAdress);

	@Query("{ 'permanentAdress': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByPermanentAdressContainingIgnoreCase(String permanentAdress);

	@Query("{ 'documents': { $regex: ?0, $options: 'i' } }")
	public List<Tin> findByDocumentsContainingIgnoreCase(String document);

	@Query("{'userId' : ?0}")
	public List<Tin> findByUserId(String userId);

}