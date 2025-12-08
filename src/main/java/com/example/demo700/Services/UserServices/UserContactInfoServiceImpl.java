package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Validators.EmailValidator;
import com.example.demo700.Validators.PhoneValidator;

@Service
public class UserContactInfoServiceImpl implements UserContactInfoService {

	@Autowired
	private UserContactInfoRepository userContactInfoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Cleaner cleaner;

	private PhoneValidator phoneValidator;

	private EmailValidator emailValidator;

	@Override
	public UserContactInfo addUserContactInfo(UserContactInfo userContactInfo, String userId) {

		if (userContactInfo == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		if (userContactInfo.getEmail() == null || userContactInfo.getPhone() == null) {

			throw new NullPointerException("Have to put all the data perfectly at here...");

		}

		phoneValidator = new PhoneValidator(userContactInfo.getPhone());
		emailValidator = new EmailValidator();

		if (!phoneValidator.isValid()) {

			throw new ArithmeticException("Phone number is not valid...");

		} else if (!emailValidator.isValidEmail(userContactInfo.getEmail())) {

			throw new ArithmeticException("Email adress is not valid...");

		}

		try {

			UserContactInfo _userContactInfo = userContactInfoRepository.findByEmail(userContactInfo.getEmail());

			if (_userContactInfo != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Email adress already exist at here...");

		} catch (Exception e) {

		}

		try {

			UserContactInfo _userContactInfo = userContactInfoRepository.findByPhone(userContactInfo.getPhone());

			if (_userContactInfo != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Phone adress already exist at here...");

		} catch (Exception e) {

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such exist at here...");

		}
		
		try {
			
			UserContactInfo _userContactInfo = userContactInfoRepository.findByUserId(userContactInfo.getUserId());
			
			if(_userContactInfo != null) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new ArithmeticException("This user is already add in the contact info");
			
		}

		userContactInfo = userContactInfoRepository.save(userContactInfo);

		return userContactInfo;
	}

	@Override
	public List<UserContactInfo> seeAllUserContactInfo() {

		List<UserContactInfo> list = userContactInfoRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such user contact info exist at here...");

		}

		return list;
	}

	@Override
	public UserContactInfo searchByEmail(String email) {

		if (email == null) {

			throw new NullPointerException("False request...");

		}

		UserContactInfo userContactInfo = userContactInfoRepository.findByEmail(email);

		if (userContactInfo == null) {

			throw new NoSuchElementException("No such user exiat at here...");

		}

		return userContactInfo;
	}

	@Override
	public UserContactInfo searchByPhone(String phone) {

		if (phone == null) {

			throw new NullPointerException("False request...");

		}

		System.out.println("searching phone :- " + phone);
		
		UserContactInfo userContactInfo = userContactInfoRepository.findByPhone(phone);

		if (userContactInfo == null) {

			throw new NoSuchElementException("No such user exiat at here...");

		}

		return userContactInfo;
	}

	@Override
	public UserContactInfo searchByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		UserContactInfo userContactInfo = userContactInfoRepository.findByUserId(userId);

		if (userContactInfo == null) {

			throw new NoSuchElementException("No such user exiat at here...");

		}

		return userContactInfo;
	}

	@Override
	public UserContactInfo updateUserContactInfo(String userContactInfoId, String userId,
			UserContactInfo userContactInfo) {

		if (userContactInfo == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		if (userContactInfo.getEmail() == null || userContactInfo.getPhone() == null) {

			throw new NullPointerException("Have to put all the data perfectly at here...");

		}
		
		try {
			
			UserContactInfo _userContactInfo = userContactInfoRepository.findById(userContactInfoId).get();
			
			if(_userContactInfo == null) {
				
				throw new Exception();
				
			}
			
			if(!_userContactInfo.getUserId().equals(userId)) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No such user conatct info exiat at here...");
			
		}

		phoneValidator = new PhoneValidator(userContactInfo.getPhone());
		emailValidator = new EmailValidator();

		if (!phoneValidator.isValid()) {

			throw new ArithmeticException("Phone number is not valid...");

		} else if (!emailValidator.isValidEmail(userContactInfo.getEmail())) {

			throw new ArithmeticException("Email adress is not valid...");

		}

		try {

			UserContactInfo _userContactInfo = userContactInfoRepository.findByEmail(userContactInfo.getEmail());

			if (_userContactInfo != null) {

				if (!_userContactInfo.getUserId().equals(userId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can only update yourself......");

		} catch (Exception e) {

		}

		try {

			UserContactInfo _userContactInfo = userContactInfoRepository.findByPhone(userContactInfo.getPhone());

			if (_userContactInfo != null) {

				if (!_userContactInfo.getUserId().equals(userId)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("You can only update yourself.....");

		} catch (Exception e) {

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such exist at here...");

		}

		try {
			
			UserContactInfo _userContactInfo = userContactInfoRepository.findByUserId(userId);
			
			if(_userContactInfo == null) {
				
				throw new Exception();
				
			}
			
			if(!_userContactInfo.getId().equals(userContactInfoId)) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new ArithmeticException("You can only update yourself...");
			
		}
		
		userContactInfo.setId(userContactInfoId);

		userContactInfo = userContactInfoRepository.save(userContactInfo);

		return userContactInfo;

	}

	@Override
	public boolean deleteUserContactInfo(String userContactInfoId, String userId) {
		
		if(userContactInfoId == null || userId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			UserContactInfo userContactInfo = userContactInfoRepository.findById(userContactInfoId).get();
			
			if(userContactInfo == null) {
				
				throw new Exception();
				
			}
			
			if(!userContactInfo.getUserId().equals(userId)) {
				
				throw new Exception();
				
			}
			
		} catch(Exception e) {
			
			throw new ArithmeticException("In valid credintial...");
			
		}
		
		long count = userContactInfoRepository.count();
		
		cleaner.removeUserContactInfo(userContactInfoId);
		
		return count != userContactInfoRepository.count();
	}

}
