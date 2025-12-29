package com.example.demo700.Services.QNAServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.QNAModels.AnswerQuestion;
import com.example.demo700.Model.QNAModels.AskQuestion;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.QNARepositories.AnswerRepository;
import com.example.demo700.Repositories.QNARepositories.QuestionRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdvocateServices.PostContentService;

@Service
public class AnswerQuestionServiceImpl implements AnswerQuestionService {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private PostContentService postContentService;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public AnswerQuestion answer(AnswerQuestion answerQuestion, String userId, MultipartFile file) {

		if (answerQuestion == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to answer...");

		}

		try {

			String attachmentId = postContentService.upload(file);

			if (attachmentId != null) {

				answerQuestion.setAttachmentId(attachmentId);

			}

		} catch (Exception e) {

		}

		try {

			AskQuestion question = questionRepository.findById(answerQuestion.getQuestionId()).get();

			if (question == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No valid question find at here...");

		}

		answerQuestion = answerRepository.save(answerQuestion);

		return answerQuestion;
	}

	@Override
	public AnswerQuestion updateAnswer(AnswerQuestion answerQuestion, String userID, String answerId,
			MultipartFile file) {

		if (answerQuestion == null || userID == null || answerId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			AnswerQuestion answer = answerRepository.findById(answerId).get();

			if (answer == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userID).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

			if (!answer.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such answer find at here...");

		}

		try {

			User user = userRepository.findById(userID).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find at here to answer...");

		}

		try {

			String attachmentId = postContentService.upload(file);

			if (attachmentId != null) {

				answerQuestion.setAttachmentId(attachmentId);

			}

		} catch (Exception e) {

		}

		try {

			AskQuestion question = questionRepository.findById(answerQuestion.getQuestionId()).get();

			if (question == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No valid question find at here...");

		}

		answerQuestion.setId(answerId);

		answerQuestion = answerRepository.save(answerQuestion);

		return answerQuestion;
	}

	@Override
	public List<AnswerQuestion> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findByQuestionId(String questionId) {

		if (questionId == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByQuestionId(questionId);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findByMessageContainingIgnoreCase(String keyword) {

		if (keyword == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByMessageContainingIgnoreCase(keyword);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findByTimeAfter(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeAfter(time);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findByTimeBefore(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeBefore(time);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findByTimeBetween(Instant startTime, Instant endTime) {

		if (startTime == null | endTime == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeBetween(startTime, endTime);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return list;
	}

	@Override
	public List<AnswerQuestion> findAll() {

		List<AnswerQuestion> list = answerRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such answer exist at here..");

		}

		return list;
	}

	@Override
	public AnswerQuestion findByAnswerId(String answerId) {

		if (answerId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AnswerQuestion answer = answerRepository.findById(answerId).get();

			if (answer == null) {

				throw new Exception();

			}

			return answer;

		} catch (Exception e) {

			throw new NoSuchElementException("No such answer find at here...");

		}

	}

	@Override
	public boolean deleteAnswer(String answerId, String userId) {

		if (answerId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid answer find at here...");

		}

		try {

			AnswerQuestion answer = answerRepository.findById(answerId).get();

			if (answer == null) {

				throw new Exception();

			}

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = answerRepository.count();

					cleaner.removeAnswer(answerId);

					return count != answerRepository.count();

				}

			} catch (Exception e) {

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

			if (!answer.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such answer find at here...");

		}

		long count = answerRepository.count();

		cleaner.removeAnswer(answerId);

		return count != answerRepository.count();
	}

}
