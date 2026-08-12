package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.CompanyContact;

@Repository
public interface CompanyContactRepository extends MongoRepository<CompanyContact, String> {

	public List<CompanyContact> findByContactPersonNameContainingIgnoreCase(String contactPersonName);
	public List<CompanyContact> findByCompanyId(String companyId);
	public List<CompanyContact> findByContactPersonMobile(String contactPersonMobile);
	public List<CompanyContact> findByContactPersonEmail(String contactPersonEmail);
	public List<CompanyContact> findByHowDidHearContainingIgnoreCase(String howDidHear);
	public List<CompanyContact> findByAnyOtherMessageContainingIgnoreCase(String anyOtherMessage);
	
}
