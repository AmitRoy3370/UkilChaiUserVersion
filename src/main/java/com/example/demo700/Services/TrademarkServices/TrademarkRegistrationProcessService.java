package com.example.demo700.Services.TrademarkServices;

import java.util.List;

import com.example.demo700.Model.Trademarkmodels.TrademarkRegistrationProcess;

public interface TrademarkRegistrationProcessService {

	public TrademarkRegistrationProcess addTrademarkRegistrationProcess(TrademarkRegistrationProcess process, String userId);
	public TrademarkRegistrationProcess updateTrademarkRegistrationProcess(TrademarkRegistrationProcess process, String userId, String id);
	
	public TrademarkRegistrationProcess findById(String id);
	public List<TrademarkRegistrationProcess> findAll();
    public List<TrademarkRegistrationProcess> findByUserId(String userId);
    public List<TrademarkRegistrationProcess> findByAdvocateId(String advocateId);
    public TrademarkRegistrationProcess findByTradeMarkId(String tradeMarkId);
    public List<TrademarkRegistrationProcess> findByStatus(boolean status);
    public List<TrademarkRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    public List<TrademarkRegistrationProcess> findByTrademarkIdIn(List<String> trademarksId);
	
    public boolean deleteTrademarkRegistrationProcess(String id, String userId);
    
}
