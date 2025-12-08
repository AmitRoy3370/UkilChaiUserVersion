package com.example.demo700.Services.AdminServices;

import java.util.List;

import com.example.demo700.Model.AdminModels.CenterAdmin;

public interface CenterAdminService {
	
	public CenterAdmin addCenterAdmin(CenterAdmin centerAdmin, String userId);
	public List<CenterAdmin> seeAll();
	public CenterAdmin findByUserId(String userId);
	public CenterAdmin findByAdminsContainingIgnoreCase(String admin);
	public List<CenterAdmin> findByDistrictsContainingIgnoreCase(String districts);
	public CenterAdmin findByAdvocatesContainingIgnoreCase(String advocates);
	public CenterAdmin updateCenterAdmin(CenterAdmin centerAdmin, String userId, String centerAdminId);
	public boolean removeCentralAdmin(String centralAdminId, String userId);

}
