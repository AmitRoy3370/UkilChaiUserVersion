package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CompanyResponse;
import com.example.demo700.Model.UserModels.CompanyInformation;

public interface CompanyInformationService {

	public CompanyInformation addCompanyInformation(CompanyInformation companyInformation, String userId, MultipartFile files[]);
	public CompanyInformation updateCompanyInformation(CompanyInformation companyInformation, String id, String userId, MultipartFile files[]);
	public CompanyInformation addDirector(String id, String directorId, String userId);
	public CompanyInformation addShareHolder(String id, String holderId, String userId);
	
	public List<CompanyResponse> findAll();
	public CompanyResponse findById(String id);
	public List<CompanyResponse> findByCompanyNameContainingIgnoreCase(String companyName);
	public List<CompanyResponse> findByTypeContainingIgnoreCase(String type);
	public List<CompanyResponse> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness);
	public List<CompanyResponse> findByCategoryContainingIgnoreCase(String category);
	public List<CompanyResponse> findByOfficeRegistryId(String officeRegistryId);
	public List<CompanyResponse> findByShareHoldersContainingIgnoreCase(String shareHoldersId);
	public List<CompanyResponse> findByDocumentsContainingIgnoreCase(String documentsId);
	public List<CompanyResponse> findByDirectorsIdContainingIgnoreCase(String directorsId);
	public List<CompanyResponse> findByAuthorizedContainingIgnoreCase(String authorized);
	public List<CompanyResponse> findByCapital(String capital);
	
	public boolean deleteCompanyInformation(String id, String userId);
	
}
