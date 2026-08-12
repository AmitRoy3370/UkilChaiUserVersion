package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.Capital;

public interface CapitalService {

	public Capital addCapital(Capital capital, String userId);
	public Capital updateCapital(Capital capital, String id, String userId);
	
	public Capital findById(String id);
	public List<Capital> findAll();
	public List<Capital> findByCompanyId(String companyId);
	public List<Capital> findByAuthorizedCapitalLte(double authorizedCapital);
	public List<Capital> findByAuthorizedCapitalGte(double authorizedCapital);
	public List<Capital> findByTotalShareLte(int totalShare);
	public List<Capital> findByTotalShareGte(int totalShare);
	public List<Capital> findByNumberOfShareLte(int numberOfShare);
	public List<Capital> findByNumberOfShareGte(int numberOfShare);
	public List<Capital> findByShareValueLte(double shareValue);
	public List<Capital> findByShareValueGte(double shareValue);
	
	public boolean deleteCapital(String id, String userId);
	
}
