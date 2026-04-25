package com.example.demo700.Services.QNAServices;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.DTOFiles.AnswerResponse;
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
import com.example.demo700.Services.AdvocateServices.AdvocateService;
import com.example.demo700.Services.AdvocateServices.PostContentService;

@Service
public class AnswerQuestionServiceImpl implements AnswerQuestionService {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateService advocateService;

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
	public List<AnswerResponse> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByQuestionId(String questionId) {

		if (questionId == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByQuestionId(questionId);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByMessageContainingIgnoreCase(String keyword) {

		if (keyword == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByMessageContainingIgnoreCase(keyword);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByTimeAfter(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeAfter(time);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByTimeBefore(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeBefore(time);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByTimeBetween(Instant startTime, Instant endTime) {

		if (startTime == null | endTime == null) {

			throw new NullPointerException("False request...");

		}

		List<AnswerQuestion> list = answerRepository.findByTimeBetween(startTime, endTime);

		if (list.isEmpty()) {

			throw new ArithmeticException("No such answer find at here...");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findAll() {

		List<AnswerQuestion> list = answerRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such answer exist at here..");

		}

		return getAnswerResponseFromAnswerList(list);
	}

	@Override
	public List<AnswerResponse> findByQuestionIdIn(List<String> questionId) {

		try {

			List<AnswerQuestion> list = answerRepository.findByQuestionIdIn(questionId);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return getAnswerResponseFromAnswerList(list);

		} catch (Exception e) {

			return new ArrayList<>();

		}

	}

	@Override
	public AnswerResponse findByAnswerId(String answerId) {

		if (answerId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AnswerQuestion answer = answerRepository.findById(answerId).get();

			if (answer == null) {

				throw new Exception();

			}

			return getAnswerResponseFromAnswer(answer);

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

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private AnswerResponse getAnswerResponseFromAnswer(AnswerQuestion answer) {

		List<AnswerQuestion> list = new ArrayList<>();

		list.add(answer);

		return getAnswerResponseFromAnswerList(list).get(0);

	}

	private List<AnswerResponse> getAnswerResponseFromAnswerList(List<AnswerQuestion> answers) {

		List<AnswerResponse> responses = new ArrayList<>();

		CompletableFuture<Map<String, AdvocateResponse>> advocateFuture = CompletableFuture
				.supplyAsync(() -> advocateService.seeAllAdvocate().isEmpty() ? new HashMap<>()
						: advocateService.seeAllAdvocate().stream().filter(Objects::nonNull)
								.filter(advocateResponse -> advocateResponse.getName() != null)
								.collect(Collectors.toMap(AdvocateResponse::getId, Function.identity(),
										(existing, replacement) -> existing)),
						executor);

		CompletableFuture.allOf(advocateFuture).join();

		Map<String, AdvocateResponse> advocateMap = advocateFuture.join();

		for (AnswerQuestion answer : answers) {

			try {

				AnswerResponse response = new AnswerResponse();

				response.setId(answer.getId());

				try {

					response.setMessage(answer.getMessage());

				} catch (Exception e) {

				}

				try {

					response.setQuestionId(answer.getQuestionId());
					response.setAdvocateName(advocateMap.get(answer.getAdvocateId()).getName());

				} catch (Exception e) {

				}

				try {

					response.setAdvocateId(answer.getAdvocateId());

				} catch (Exception e) {

				}

				try {

					response.setAttachmentId(answer.getAttachmentId());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
