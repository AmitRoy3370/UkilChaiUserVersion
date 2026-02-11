package com.example.demo700.Services.PaymentServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.PaymentModels.PaymentDetails;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.HearingRepository;
import com.example.demo700.Repositories.PaymentRepositories.PaymentDetailsRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private HearingRepository hearingRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public PaymentDetails addPaymentDetails(PaymentDetails paymentDetails, String userId) {

		if (paymentDetails == null || userId == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(paymentDetails.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new ArithmeticException();

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("No such valid advocate find at here to add the cost at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		if (paymentDetails.getPrice() <= 0.0) {

			throw new ArithmeticException("Invalid cost....");

		}

		try {

			if (paymentDetails.getPaymentFor() == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Payment must have a type....");

		}

		try {

			if (paymentDetails.getPaymentFor() != CasePayment.CASE_HEARING_PAYMENT) {

				List<PaymentDetails> payment = paymentDetailsRepository
						.findByCaseIdAndPaymentFor(paymentDetails.getCaseId(), paymentDetails.getPaymentFor());

				if (!payment.isEmpty()) {

					throw new ArithmeticException();

				}

			} else {

				List<PaymentDetails> payment = paymentDetailsRepository
						.findByCaseIdAndPaymentFor(paymentDetails.getCaseId(), paymentDetails.getPaymentFor());

				int total = payment.size();

				List<Hearing> hearings = hearingRepository.findByCaseId(paymentDetails.getCaseId());

				if (total != hearings.size()) {

					throw new Exception();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("this price can be uploaded only for one times....");

		} catch (Exception e) {

			throw new ArithmeticException("You can add hearing price for just next hearing price....");

		}

		paymentDetails = paymentDetailsRepository.save(paymentDetails);

		if (paymentDetails == null) {

			throw new ArithmeticException("Your payment details is not saved....");

		}

		return paymentDetails;

	}

	@Override
	public PaymentDetails updatePaymentDetails(PaymentDetails paymentDetails, String userId, String paymentDetailsId) {

		if (paymentDetails == null || userId == null || paymentDetailsId == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			PaymentDetails payment = paymentDetailsRepository.findById(paymentDetailsId).get();

			if (payment == null) {

				throw new Exception();

			}

			if (!payment.getCaseId().equals(paymentDetails.getCaseId())) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is changes at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such case payment find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here....");

		}

		try {

			Case acceptedCase = caseRepository.findById(paymentDetails.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new ArithmeticException();

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("No such valid advocate find at here to add the cost at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such case find at here....");

		}

		if (paymentDetails.getPrice() <= 0.0) {

			throw new ArithmeticException("Invalid cost....");

		}

		try {

			if (paymentDetails.getPaymentFor() == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("Payment must have a type....");

		}

		try {

			if (paymentDetails.getPaymentFor() != CasePayment.CASE_HEARING_PAYMENT) {

			} else {

				List<PaymentDetails> payment = paymentDetailsRepository
						.findByCaseIdAndPaymentFor(paymentDetails.getCaseId(), paymentDetails.getPaymentFor());

				if (!payment.get(payment.size() - 1).getId().equals(paymentDetailsId)) {

					throw new Exception();

				}

			}

		} catch (Exception e) {

			throw new ArithmeticException("You can update last stage pag only price for just next hearing price....");

		}

		paymentDetails.setId(paymentDetailsId);

		paymentDetails = paymentDetailsRepository.save(paymentDetails);

		if (paymentDetails == null) {

			throw new ArithmeticException("Your payment details is not saved....");

		}

		return paymentDetails;
	}

	@Override
	public PaymentDetails findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			PaymentDetails paymentDetails = paymentDetailsRepository.findById(id).get();

			if (paymentDetails == null) {

				throw new Exception();

			}

			return paymentDetails;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findAll() {

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByPaymentFor(CasePayment paymentFor) {

		if (paymentFor == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByPaymentFor(paymentFor);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByPriceGreaterThanEqual(double price) {

		if (price <= 0.0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByPriceGreaterThanEqual(price);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByPriceLessThanEqual(double price) {

		if (price <= 0.0) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByPriceLessThanEqual(price);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByUserId(userId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByCaseId(String caseId) {

		if (caseId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByCaseId(caseId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public List<PaymentDetails> findByCaseIdAndPaymentFor(String caseId, CasePayment paymentFor) {

		if (caseId == null || paymentFor == null) {

			throw new NullPointerException("False request....");

		}

		try {

			List<PaymentDetails> list = paymentDetailsRepository.findByCaseIdAndPaymentFor(caseId, paymentFor);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such payment details find at here...");

		}

	}

	@Override
	public boolean deletePaymentDetails(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			PaymentDetails payment = paymentDetailsRepository.findById(id).get();

			if (payment == null) {

				throw new Exception();

			}

			Case acceptedCase = caseRepository.findById(payment.getCaseId()).get();

			if (acceptedCase == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findById(acceptedCase.getAdvocateId()).get();

			if (advocate == null) {

				throw new Exception();

			}

			try {

				User user = userRepository.findById(userId).get();

				if (user == null) {

					throw new Exception();

				}

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = paymentDetailsRepository.count();

					cleaner.removePaymentDetails(id);

					return count != paymentDetailsRepository.count();

				}

			} catch (Exception e) {

			}

			if (!advocate.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("Case is changes at here...");

		} catch (Exception e) {

			throw new NoSuchElementException("No such case payment find at here...");

		}

		long count = paymentDetailsRepository.count();

		cleaner.removePaymentDetails(id);

		return count != paymentDetailsRepository.count();

	}

}
