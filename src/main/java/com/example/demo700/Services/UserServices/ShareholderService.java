package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.ShareholderResponse;
import com.example.demo700.Model.UserModels.Shareholder;

public interface ShareholderService {

	public Shareholder addShareholder(Shareholder holder, String userId, MultipartFile nid, MultipartFile tin);
	public Shareholder updateShareholder(Shareholder holder, String userId, String id, MultipartFile nid, MultipartFile tin);
	public Shareholder shareProfit(String companyId, double percentage, String shareHolderId, String userId);
	
	public ShareholderResponse findById(String id);
	public List<ShareholderResponse> findAll();
	public ShareholderResponse findByUserId(String userId);
	public List<ShareholderResponse> findByNid(String nid);
	public List<ShareholderResponse> findByTin(String tin);
	public List<ShareholderResponse> findByShareCompanyId(String companyId);
	public List<ShareholderResponse> findByShareCompanyIdAndPercentage(String companyId, Double percentage);
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageGte(String companyId, Double percentage);
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageLte(String companyId, Double percentage);
	public List<ShareholderResponse> findByShareCompanyIdAndPercentageBetween(String companyId, Double minPercentage,
			Double maxPercentage);
	
	public boolean removeShareholder(String id, String userId);
	
}
