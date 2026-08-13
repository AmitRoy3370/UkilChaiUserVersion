package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.CompanyInformation;

@Repository
public interface CompanyInformationRepository extends MongoRepository<CompanyInformation, String> {

	public List<CompanyInformation> findByCompanyNameContainingIgnoreCase(String companyName);
	public CompanyInformation findByCompanyNameIgnoreCase(String name);
	public List<CompanyInformation> findByTypeContainingIgnoreCase(String type);
	public List<CompanyInformation> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness);
	public List<CompanyInformation> findByCategoryContainingIgnoreCase(String category);
	public List<CompanyInformation> findByOfficeRegistryId(String officeRegistryId);
	public List<CompanyInformation> findByShareHoldersContainingIgnoreCase(String shareHoldersId);
	public List<CompanyInformation> findByDocumentsContainingIgnoreCase(String documentsId);
	public List<CompanyInformation> findByDirectorsIdContainingIgnoreCase(String directorsId);
	public List<CompanyInformation> findByAuthorizedContainingIgnoreCase(String authorized);
	public List<CompanyInformation> findByCapitalContainingIgnoreCase(String capital);
	List<CompanyInformation> findByCreatorId(String creatorId);
	
}
