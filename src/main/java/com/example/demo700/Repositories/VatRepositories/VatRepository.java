package com.example.demo700.Repositories.VatRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.VatModels.Vat;

@Repository
public interface VatRepository extends MongoRepository<Vat, String> {

    @Query("{ 'userId': ?0 }")
    public List<Vat> findByUserId(String userId);

    @Query("{ 'adress': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByAdressContainingIgnoreCase(String adress);

    @Query("{ 'tinNo': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByTinNoContainingIgnoreCase(String tinNo);

    @Query("{ 'buisnessName': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByBuisnessNameContainingIgnoreCase(String buisnessName);

    @Query("{ 'tradeLicenseNo': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByTradeLicenseNoContainingIgnoreCase(String tradeLicenseNo);

    @Query("{ 'annualTurnOver': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByAnnualTurnOverContainingIgnoreCase(String annualTurnOver);

    @Query("{ 'mainProduct': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByMainProductContainingIgnoreCase(String mainProduct);

    @Query("{ 'natureOfBuisness': { $regex: ?0, $options: 'i' } }")
    public List<Vat> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness);

    @Query("{ 'numberOfBuisness': { $gte: ?0 } }")
    public List<Vat> findByNumberOfBuisnessGreaterThanEqual(int numberOfBuisness);

    @Query("{ 'numberOfBuisness': { $lte: ?0 } }")
    public List<Vat> findByNumberOfBuisnessLessThanEqual(int numberOfBuisness);

    @Query("{ 'numberOfEmployee': { $gte: ?0 } }")
    public List<Vat> findByNumberOfEmployeeGreaterThanEqual(int numberOfEmployee);

    @Query("{ 'numberOfEmployee': { $lte: ?0 } }")
    public List<Vat> findByNumberOfEmployeeLessThanEqual(int numberOfEmployee);

    @Query("{ 'documents': ?0 }")
    public List<Vat> findByDocuments(String documents);

}