package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.Advocate;

@Service
public class AdvocateServiceImpl implements AdvocateService {

	@Override
	public Advocate addVocate(Advocate advocate, String userId) {
		
		return null;
	}

	@Override
	public List<Advocate> seeAllAdvocate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advocate findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advocate> findByAdvocateSpeciality(AdvocateSpeciality AdvocateSpeciality) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advocate findByLicenseKey(String licenseKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advocate> findByExperienceGreaterThan(int experience) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advocate> findByDegreesContainingIgnoreCase(String degree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advocate> findByWorkingExperiencesContainingIgnoreCase(String experience) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advocate updateAdvocate(Advocate advocate, String userId, String advocateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAdvocate(String userId, String advocateId) {
		// TODO Auto-generated method stub
		return false;
	}

}
