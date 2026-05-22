package com.example.demo700.Services.AdminServices;

import java.util.List;

import com.example.demo700.DTOFiles.CenterAdminDTO;
import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.CenterAdmin;

public interface CenterAdminService {
	
	public CenterAdmin addCenterAdmin(CenterAdmin centerAdmin, String userId);
	public List<CenterAdminDTO> seeAll();
	public CenterAdminDTO findByUserId(String userId);
	
	public List<CenterAdminDTO> findByDistrictsContainingIgnoreCase(String districts);
	public List<Admin> findAdminByDistricts(String district);
	public CenterAdminDTO findByAdminsContainingIgnoreCase(String adminId);
	public CenterAdminDTO findByAdvocatesContainingIgnoreCase(String advocates);
	public CenterAdmin updateCenterAdmin(CenterAdmin centerAdmin, String userId, String centerAdminId);
	public boolean removeCentralAdmin(String centralAdminId, String userId);

}
