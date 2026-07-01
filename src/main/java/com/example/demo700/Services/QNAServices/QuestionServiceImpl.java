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
import com.example.demo700.DTOFiles.AnswerResponse;
import com.example.demo700.DTOFiles.QuestionResponse;
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
	private AnswerQuestionService answerQuestionService;

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
	public List<QuestionResponse> seeAll() {

		return getQuestionResponseFromQuestionList(questionRepository.findAll());
	}

	@Override
	public List<QuestionResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public List<QuestionResponse> findByMessageContainingIgnoreCase(String keyWord) {

		if (keyWord == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByMessageContainingIgnoreCase(keyWord);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public List<QuestionResponse> findByPostTimeAfter(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeAfter(time);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public List<QuestionResponse> findByPostTimeBefore(Instant time) {

		if (time == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeBefore(time);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public List<QuestionResponse> findByPostTimeBetween(Instant start, Instant end) {

		if (start == null || end == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByPostTimeBetween(start, end);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public List<QuestionResponse> findByQuestionType(AdvocateSpeciality questionType) {

		if (questionType == null) {

			throw new NullPointerException("False request...");

		}

		List<AskQuestion> list = questionRepository.findByQuestionType(questionType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such question find at here...");

		}

		return getQuestionResponseFromQuestionList(list);
	}

	@Override
	public QuestionResponse findByQuestionId(String questionId) {

		try {

			AskQuestion question = questionRepository.findById(questionId).get();

			if (question == null) {

				throw new Exception();

			}

			return getQuestionResponseFromQuestion(question);

		} catch (Exception e) {

			throw new NoSuchElementException("No such question find at here..");

		}

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

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private QuestionResponse getQuestionResponseFromQuestion(AskQuestion question) {

		List<AskQuestion> list = new ArrayList<>();

		list.add(question);

		return getQuestionResponseFromQuestionList(list).get(0);

	}

	private List<QuestionResponse> getQuestionResponseFromQuestionList(List<AskQuestion> questions) {

		List<QuestionResponse> responses = new ArrayList<>();

		CompletableFuture<Map<String, User>> userFuture =
		        CompletableFuture.supplyAsync(() -> {

		            List<User> users = userRepository.findAll();

		            if (users == null || users.isEmpty()) {

		                return new HashMap<String, User>();

		            }

		            return users.stream()
		                    .filter(Objects::nonNull)
		                    .filter(user -> user.getId() != null)
		                    .collect(Collectors.toMap(
		                            User::getId,
		                            Function.identity(),
		                            (existing, replacement) -> existing
		                    ));

		        }, executor);

		List<String> allQuestionId = questions.stream().map(AskQuestion::getId).collect(Collectors.toList());

		List<AnswerResponse> answers = answerQuestionService.findByQuestionIdIn(allQuestionId);

		CompletableFuture<Map<String, List<AnswerResponse>>> answerFuture =
		        CompletableFuture.supplyAsync(() -> {

		            if (answers == null || answers.isEmpty()) {

		                return new HashMap<String, List<AnswerResponse>>();

		            }

		            return answers.stream()
		                    .filter(Objects::nonNull)
		                    .filter(answerResponse -> answerResponse.getQuestionId() != null)
		                    .collect(Collectors.groupingBy(
		                            AnswerResponse::getQuestionId,
		                            HashMap::new,
		                            Collectors.toList()
		                    ));

		        }, executor);

		CompletableFuture.allOf(userFuture, answerFuture).join();

		Map<String, User> userMap = userFuture.join();
		Map<String, List<AnswerResponse>> answerMap = answerFuture.join();

		for (AskQuestion question : questions) {

			try {

				QuestionResponse response = new QuestionResponse();

				try {

					response.setId(question.getId());

					try {

						response.setMessage(question.getMessage());

					} catch (Exception e) {

					}

					response.setPostTime(question.getPostTime());

					try {

						response.setAttachmentId(question.getAttachmentId());

					} catch (Exception e) {

					}

					try {

						response.setQuestionType(question.getQuestionType());

					} catch (Exception e) {

					}

					try {

						response.setUserId(question.getUserId());
						response.setUserName(userMap.get(question.getUserId()).getName());

						response.setFullName(userMap.get(question.getUserId()).getFullName());

						
					} catch (Exception e) {

					}

					try {

						response.setAnswers(answerMap.get(question.getId()));

					} catch (Exception e) {

					}

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
