package com.example.demo700.Repositories.RJSCRepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.RJSCModels.RJSC;

@Repository
public interface RJSCRepository extends MongoRepository<RJSC, String> {

    @Query("{ 'userId': ?0 }")
    public List<RJSC> findByUserId(String userId);

    @Query("{ 'compilenceService': { $regex: ?0, $options: 'i' } }")
    public List<RJSC> findByCompilenceServiceContainingIgnoreCase(String compilenceService);

    @Query("{ 'registrationNo': { $regex: ?0, $options: 'i' } }")
    public List<RJSC> findByRegistrationNoContainingIgnoreCase(String registrationNo);

    @Query("{ 'email': ?0 }")
    public RJSC findByEmail(String email);

    @Query("{ 'companyName': { $regex: ?0, $options: 'i' } }")
    public List<RJSC> findByCompanyNameContainingIgnoreCase(String companyName);

    @Query("{ 'year': { $gte: ?0 } }")
    public List<RJSC> findByYearAfter(Instant year);

    @Query("{ 'year': { $lte: ?0 } }")
    public List<RJSC> findByYearBefore(Instant year);

    @Query("{ 'documents': ?0 }")
    public List<RJSC> findByDocuments(String documents);

}