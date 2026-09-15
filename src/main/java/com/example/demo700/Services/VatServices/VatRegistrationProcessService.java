package com.example.demo700.Services.VatServices;

import java.util.List;

import com.example.demo700.Model.VatModels.VatRegistrationProcess;

public interface VatRegistrationProcessService {

	public VatRegistrationProcess addProcess(VatRegistrationProcess process , String userId);
	public VatRegistrationProcess updateProcess(VatRegistrationProcess process, String userId, String id);
	
	public VatRegistrationProcess findById(String id);
	public List<VatRegistrationProcess> findAll();
    public VatRegistrationProcess findByVatId(String vatId);
    public List<VatRegistrationProcess> findByVatIdIn(List<String> vatsId);
    public List<VatRegistrationProcess> findByUserId(String userId);
    public List<VatRegistrationProcess> findByAdvocateId(String advocateId);
    public List<VatRegistrationProcess> findByStatus(boolean status);
    public List<VatRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    
    public boolean deleteProcess(String id, String userId);
    
}
