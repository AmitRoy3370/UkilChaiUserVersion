package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.CompanyInformation;

public interface CompanyInformationService {

	public CompanyInformation addCompanyInformation(CompanyInformation companyInformation, String userId, MultipartFile files[]);
	public CompanyInformation updateCompanyInformation(CompanyInformation companyInformation, String id, String userId, MultipartFile files[]);
	public CompanyInformation addDirector(String id, String directorId, String userId);
	public CompanyInformation addShareHolder(String id, String holderId, String userId);
	
	public List<CompanyInformation> findAll();
	public CompanyInformation findById(String id);
	public List<CompanyInformation> findByCompanyNameContainingIgnoreCase(String companyName);
	public List<CompanyInformation> findByTypeContainingIgnoreCase(String type);
	public List<CompanyInformation> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness);
	public List<CompanyInformation> findByCategoryContainingIgnoreCase(String category);
	public List<CompanyInformation> findByOfficeRegistryId(String officeRegistryId);
	public List<CompanyInformation> findByShareHoldersContainingIgnoreCase(String shareHoldersId);
	public List<CompanyInformation> findByDocumentsContainingIgnoreCase(String documentsId);
	public List<CompanyInformation> findByDirectorsIdContainingIgnoreCase(String directorsId);
	public List<CompanyInformation> findByAuthorizedContainingIgnoreCase(String authorized);
	public List<CompanyInformation> findByCapital(String capital);
	
	public boolean deleteCompanyInformation(String id, String userId);
	
}
