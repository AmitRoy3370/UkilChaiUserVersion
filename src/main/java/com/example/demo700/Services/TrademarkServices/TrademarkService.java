package com.example.demo700.Services.TrademarkServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.TrademarkResponse;
import com.example.demo700.Model.Trademarkmodels.Trademark;

public interface TrademarkService {

	public Trademark addTrademark(Trademark trademark, String userId, MultipartFile documents[]);
	public Trademark updateTrademark(Trademark trademark, String userId, String id, MultipartFile documents[]);
	
	public TrademarkResponse findById(String id);
	public List<TrademarkResponse> findAll();
    public List<TrademarkResponse> findByLegalProtectionContainingIgnoreCase(String legalProtection);
    public List<TrademarkResponse> findByNationWiseValidityContainingIgnoreCase(String nationWiseValidity);
    public List<TrademarkResponse> findByApplicationTypeContainingIgnoreCase(String applicationType);
    public List<TrademarkResponse> findByApplicationNameContainingIgnoreCase(String applicationName);
    public List<TrademarkResponse> findByGovernmentFeeGreaterThanEqual(double governmentFee);
    public List<TrademarkResponse> findByGovernmentFeeLessThanEqual(double governmentFee);
    public TrademarkResponse findByEmailIgnoreCase(String email);
    public List<TrademarkResponse> findByEmailContainingIgnoreCase(String email);
    public TrademarkResponse findByMobileNumberIgnoreCase(String mobileNumber);
    public List<TrademarkResponse> findByMobileNumberContainingIgnoreCase(String mobileNumber);
    public List<TrademarkResponse> findByDocumentsContainingIgnoreCase(String documents);
    public List<TrademarkResponse> findByUserId(String userId);
    public List<TrademarkResponse> findByAdressContainingIgnoreCase(String adress);
    public List<TrademarkResponse> findByTrademarkNameContainingIgnoreCase(String trademarkName);
    public List<TrademarkResponse> findByTrademarkTypeContainingIgnoreCase(String trademarkType);
    public List<TrademarkResponse> findByClassOfGoodsContainingIgnoreCase(String classOfGoods);
	
    public boolean deleteTrademark(String id, String userId);
    
}
