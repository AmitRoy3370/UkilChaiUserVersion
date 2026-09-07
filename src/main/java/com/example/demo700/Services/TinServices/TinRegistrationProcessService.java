package com.example.demo700.Services.TinServices;

import java.util.List;

import com.example.demo700.Model.TinModels.TinRegistrationProcess;

public interface TinRegistrationProcessService {

	public TinRegistrationProcess addTinRegistrationProcess(TinRegistrationProcess process, String userId);
	public TinRegistrationProcess updateTinRegistrationProcess(TinRegistrationProcess process, String userId, String id);
	
	public TinRegistrationProcess findById(String id);
	public List<TinRegistrationProcess> findAll();
	public List<TinRegistrationProcess> findByCenterAdminId(String centerAdminId);
	public List<TinRegistrationProcess> findByAdvocateId(String advocateId);
	public TinRegistrationProcess findByTinId(String tinId);
	public List<TinRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
	
	public boolean removeTinRegistrationProcess(String id, String userId);
	
}
