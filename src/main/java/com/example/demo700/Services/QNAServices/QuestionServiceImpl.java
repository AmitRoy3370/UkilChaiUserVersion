package com.example.demo700.Services.QNAServices;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.QNAModels.AskQuestion;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.QNARepositories.QuestionRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.AdvocateServices.PostContentService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private PostContentService postContentService;

	@Autowired
	private Cleaner cleaner;

	@Override
	public AskQuestion AskQuestion(AskQuestion askQuestion, String userId, MultipartFile file) {

		if (askQuestion == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!askQuestion.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No Such valid user find at here..");

		}

		try {

			if (file != null && !file.isEmpty()) {

				String postContentId = postContentService.upload(file);
				askQuestion.setAttachmentId(postContentId);

			}

		} catch (Exception e) {

		}

		askQuestion = questionRepository.save(askQuestion);

		if (askQuestion == null) {

			throw new ArithmeticException("Question not added...");

		}

		return askQuestion;
	}

	@Override
	public List<AskQuestion> seeAll() {

		return questionRepository.findAll();
	}

	@Override
	public List<AskQuestion> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public List<AskQuestion> findByMessageContainingIgnoreCase(String keyWord) {

		if (keyWord == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByMessageContainingIgnoreCase(keyWord);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public List<AskQuestion> findByPostTimeAfter(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeAfter(time);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public List<AskQuestion> findByPostTimeBefore(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeBefore(time);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public List<AskQuestion> findByPostTimeBetween(Instant start, Instant end) {

		if (start == null || end == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeBetween(start, end);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public List<AskQuestion> findByQuestionType(AdvocateSpeciality questionType) {

		if (questionType == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByQuestionType(questionType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return list;
	}

	@Override
	public AskQuestion updateQuestion(AskQuestion askQuestion, String userId, String questionId, MultipartFile file) {

		if (askQuestion == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AskQuestion question = questionRepository.findById(questionId).get();

			if (question == null) {

				throw new Exception();

			}

			if (!question.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid question find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			if (!askQuestion.getUserId().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No Such valid user find at here..");

		}

		try {

			if (file != null && !file.isEmpty()) {

				String postContentId = postContentService.upload(file);
				askQuestion.setAttachmentId(postContentId);

			}

		} catch (Exception e) {

		}

		askQuestion.setId(questionId);

		askQuestion = questionRepository.save(askQuestion);

		if (askQuestion == null) {

			throw new ArithmeticException("Question not added...");

		}

		return askQuestion;
	}

	@Override
	public boolean removeQuestion(String userId, String questionId) {

		if (userId == null || questionId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			AskQuestion question = questionRepository.findById(questionId).get();

			if (question == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(userId);

				if (centerAdmin == null) {

					throw new Exception();

				}

				long count = questionRepository.count();

				cleaner.removeQuestion(questionId);

				return count != questionRepository.count();

			} catch (Exception e) {

			}

			if (!question.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such valid user find it to delete the question...");

		}

		long count = questionRepository.count();

		cleaner.removeQuestion(questionId);

		return count != questionRepository.count();
	}

}
