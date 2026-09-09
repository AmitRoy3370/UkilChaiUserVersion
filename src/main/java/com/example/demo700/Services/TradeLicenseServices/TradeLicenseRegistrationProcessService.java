package com.example.demo700.Services.TradeLicenseServices;

import java.util.List;

import com.example.demo700.Model.TradeLicenseModels.TradeLicenseRegistrationProcess;

public interface TradeLicenseRegistrationProcessService {

	public TradeLicenseRegistrationProcess addProcess(TradeLicenseRegistrationProcess process, String userId);
	public TradeLicenseRegistrationProcess updateProcess(TradeLicenseRegistrationProcess process, String userId, String id);
	
	public TradeLicenseRegistrationProcess findById(String id);
	public List<TradeLicenseRegistrationProcess> findAll();
    public List<TradeLicenseRegistrationProcess> findByUserId(String userId);
    public List<TradeLicenseRegistrationProcess> findByAdvocateId(String advocateId);
    public List<TradeLicenseRegistrationProcess> findByStatus(boolean status);
    public TradeLicenseRegistrationProcess findByTradeLicenseId(String tradeLicenseId);
    public List<TradeLicenseRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    public List<TradeLicenseRegistrationProcess> findByTradeLicenseIdIn(List<String> tradeLicensesId);
    
    public boolean removeTradeLicenseRegistrationProcess(String id, String userId);
	
}
