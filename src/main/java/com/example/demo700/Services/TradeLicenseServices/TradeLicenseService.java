package com.example.demo700.Services.TradeLicenseServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.TradeLicenseResponseDTO;
import com.example.demo700.Model.TradeLicenseModels.TradeLicense;

public interface TradeLicenseService {
	
	public TradeLicense addTradeLicense(TradeLicense tradeLicense, String userId, MultipartFile documents[]);
	public TradeLicense updateTradeLicense(TradeLicense tradeLicense, String userId, String id, MultipartFile documents[]);
	
	public TradeLicenseResponseDTO findById(String id);
	public List<TradeLicenseResponseDTO> findAll();
	public List<TradeLicenseResponseDTO> findByUserId(String userId);
	public List<TradeLicenseResponseDTO> findByBuisnessNameContainingIgnoreCase(String buisnessName);
	public List<TradeLicenseResponseDTO> findByMobileNumberContainingIgnoreCase(String mobileNumber);
	public List<TradeLicenseResponseDTO> findByEmailAdressContainingIgnoreCase(String emailAdress);
	public List<TradeLicenseResponseDTO> findByBuisnessTypeContainingIgnoreCase(String buisnessType);
	public List<TradeLicenseResponseDTO> findByBuisnessCategoryContainingIgnoreCase(String buisnessCategory);
	public List<TradeLicenseResponseDTO> findByDocumentsContainingIgnoreCase(String documents);
	
	public boolean removeTradeLicense(String id, String userId);
	
}
