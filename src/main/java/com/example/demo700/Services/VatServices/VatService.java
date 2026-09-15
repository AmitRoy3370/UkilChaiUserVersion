package com.example.demo700.Services.VatServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.VatResponseDTO;
import com.example.demo700.Model.VatModels.Vat;

public interface VatService {

	public Vat addVat(Vat vat, String userId, MultipartFile documents[]);
	public Vat updateVat(String id, Vat vat, String userId, MultipartFile documents[]);

	public VatResponseDTO findById(String id);
	public List<VatResponseDTO> findAll();
	public List<VatResponseDTO> findByUserId(String userId);
	public List<VatResponseDTO> findByAdressContainingIgnoreCase(String adress);
	public List<VatResponseDTO> findByTinNoContainingIgnoreCase(String tinNo);
	public List<VatResponseDTO> findByBuisnessNameContainingIgnoreCase(String buisnessName);
	public List<VatResponseDTO> findByTradeLicenseNoContainingIgnoreCase(String tradeLicenseNo);
	public List<VatResponseDTO> findByAnnualTurnOverContainingIgnoreCase(String annualTurnOver);
	public List<VatResponseDTO> findByMainProductContainingIgnoreCase(String mainProduct);
	public List<VatResponseDTO> findByNatureOfBuisnessContainingIgnoreCase(String natureOfBuisness);
	public List<VatResponseDTO> findByNumberOfBuisnessGreaterThanEqual(int numberOfBuisness);
	public List<VatResponseDTO> findByNumberOfBuisnessLessThanEqual(int numberOfBuisness);
	public List<VatResponseDTO> findByNumberOfEmployeeGreaterThanEqual(int numberOfEmployee);
	public List<VatResponseDTO> findByNumberOfEmployeeLessThanEqual(int numberOfEmployee);
	public List<VatResponseDTO> findByDocuments(String documents);

	public boolean deleteVat(String id, String userId);
	
}
