package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.CompanyContact;

public interface CompanyContactService {

	public CompanyContact addCompanyContact(CompanyContact companyContact, String userId);
	public CompanyContact updateCompanyContact(CompanyContact companyContact, String userId, String id);
	
	public List<CompanyContact> findAll();
	public CompanyContact findById(String id);
	public List<CompanyContact> findByContactPersonNameContainingIgnoreCase(String contactPersonName);
	public List<CompanyContact> findByCompanyId(String companyId);
	public List<CompanyContact> findByContactPersonMobile(String contactPersonMobile);
	public List<CompanyContact> findByContactPersonEmail(String contactPersonEmail);
	public List<CompanyContact> findByHowDidHear(String howDidHear);
	public List<CompanyContact> findByAnyOtherMessageContainingIgnoreCase(String anyOtherMessage);
	
	public boolean deleteCompanyContact(String id, String userId);
	
}
