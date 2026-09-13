package com.example.demo700.Repositories.CopyrightRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CopyrightModels.Copyright;

@Repository
public interface CopyrightRepository extends MongoRepository<Copyright, String> {

    @Query("{ 'userId': ?0 }")
    public List<Copyright> findByUserId(String userId);

    @Query("{ 'author': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByAuthorContainingIgnoreCase(String author);

    @Query("{ 'typeOfWork': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByTypeOfWorkContainingIgnoreCase(String typeOfWork);

    @Query("{ 'yearOfCreation': { $gte: ?0 } }")
    public List<Copyright> findByYearOfCreationAfter(Instant yearOfCreation);

    @Query("{ 'yearOfCreation': { $lte: ?0 } }")
    public List<Copyright> findByYearOfCreationBefore(Instant yearOfCreation);

    @Query("{ 'titleOfWork': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByTitleOfWorkContainingIgnoreCase(String titleOfWork);

    @Query("{ 'description': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByDescriptionContainingIgnoreCase(String description);

    @Query("{ 'applicationName': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByApplicationNameContainingIgnoreCase(String applicationName);

    @Query("{ 'mobileNumber': ?0 }")
    public Copyright findByMobileNumber(String mobileNumber);

    @Query("{ 'email': ?0 }")
    public Copyright findByEmail(String email);

    @Query("{ 'adress': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByAdressContainingIgnoreCase(String adress);

    @Query("{ 'documents': { $regex: ?0, $options: 'i' } }")
    public List<Copyright> findByDocumentsContainingIgnoreCase(String documents);

}