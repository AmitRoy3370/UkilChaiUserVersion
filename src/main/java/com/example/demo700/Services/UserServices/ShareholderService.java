package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.Shareholder;

public interface ShareholderService {

	public Shareholder addShareholder(Shareholder holder, String userId, MultipartFile nid, MultipartFile tin);
	public Shareholder updateShareholder(Shareholder holder, String userId, String id, MultipartFile nid, MultipartFile tin);
	public Shareholder shareProfit(String companyId, double percentage, String shareHolderId, String userId);
	
	public Shareholder findById(String id);
	public List<Shareholder> findAll();
	public Shareholder findByUserId(String userId);
	public List<Shareholder> findByNid(String nid);
	public List<Shareholder> findByTin(String tin);
	public List<Shareholder> findByShareCompanyId(String companyId);
	public List<Shareholder> findByShareCompanyIdAndPercentage(String companyId, Double percentage);
	public List<Shareholder> findByShareCompanyIdAndPercentageGte(String companyId, Double percentage);
	public List<Shareholder> findByShareCompanyIdAndPercentageLte(String companyId, Double percentage);
	public List<Shareholder> findByShareCompanyIdAndPercentageBetween(String companyId, Double minPercentage,
			Double maxPercentage);
	
	public boolean removeShareholder(String id, String userId);
	
}
