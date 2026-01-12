package com.example.demo700.CyclicCleaner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocateJoinRequest;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Model.CaseModels.AppealCase;
import com.example.demo700.Model.CaseModels.AppealHearings;
import com.example.demo700.Model.CaseModels.Case;
import com.example.demo700.Model.CaseModels.CaseJudgment;
import com.example.demo700.Model.CaseModels.CaseRequest;
import com.example.demo700.Model.CaseModels.DocumentDraft;
import com.example.demo700.Model.CaseModels.Hearing;
import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Model.NotificationModel.Notification;
import com.example.demo700.Model.QNAModels.AnswerQuestion;
import com.example.demo700.Model.QNAModels.AskQuestion;
import com.example.demo700.Model.UserModels.AdvocateRating;
import com.example.demo700.Model.UserModels.ClientFeedback;
import com.example.demo700.Model.UserModels.PostReaction;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Model.UserModels.UserContactInfo;
import com.example.demo700.Model.UserModels.UserLocation;

import com.example.demo700.Repositories.AdminRepositories.AdminJoinRequestRepository;
import com.example.demo700.Repositories.AdminRepositories.AdminRepository;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateJoinRequestRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.AdvocateRepositories.PostRepository;
import com.example.demo700.Repositories.CaseRepositories.AppealHearingRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseAppealRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseJudgementRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRepository;
import com.example.demo700.Repositories.CaseRepositories.CaseRequestRepository;
import com.example.demo700.Repositories.CaseRepositories.DocumentDraftRepository;
import com.example.demo700.Repositories.CaseRepositories.HearingRepository;
import com.example.demo700.Repositories.ChatRepositories.ChatMessageRepository;
import com.example.demo700.Repositories.NotificationRepository.NotificationRepository;
import com.example.demo700.Repositories.QNARepositories.AnswerRepository;
import com.example.demo700.Repositories.QNARepositories.QuestionRepository;
import com.example.demo700.Repositories.UserRepositories.AdvocateRatingRepository;
import com.example.demo700.Repositories.UserRepositories.ClientFeedbackRepository;
import com.example.demo700.Repositories.UserRepositories.PostReactionRepository;
import com.example.demo700.Repositories.UserRepositories.UserContactInfoRepository;
import com.example.demo700.Repositories.UserRepositories.UserLocationRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.ImageService;

@Service
public class Cleaner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserContactInfoRepository userContactInfoRepository;

	@Autowired
	private UserLocationRepository userLocationRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private AdvocateJoinRequestRepository advocateJoinRequestRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdminJoinRequestRepository adminJoinRequestRepository;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private PostRepository advocatePostRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private AdvocateRatingRepository advocateRatingRepository;

	@Autowired
	private CaseRequestRepository caseRequestRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CaseAppealRepository caseAppealRepository;

	@Autowired
	private HearingRepository hearingRepository;

	@Autowired
	private AppealHearingRepository appealHearingRepository;

	@Autowired
	private DocumentDraftRepository documentDraftRepository;

	@Autowired
	private CaseJudgementRepository caseJudgmentRepository;

	@Autowired
	private ClientFeedbackRepository clientFeedbackRepository;

	@Autowired
	private PostReactionRepository postReactionRepository;

	public void removeUser(String userId) {

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			long count = userRepository.count();

			userRepository.deleteById(user.getId());

			if (count != userRepository.count()) {

				try {

					List<PostReaction> list = postReactionRepository.findByUserId(user.getId());

					for (PostReaction i : list) {

						removePostReaction(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<ClientFeedback> list = clientFeedbackRepository.findByUserId(user.getId());

					for (ClientFeedback i : list) {

						removeClientFeedback(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<Case> list = caseRepository.findByUserId(user.getId());

					for (Case i : list) {

						removeCase(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<CaseRequest> list = caseRequestRepository.findByUserId(user.getId());

					for (CaseRequest caseRequest : list) {

						removeCaseRequest(caseRequest.getId());

					}

				} catch (Exception e) {

				}

				try {

					imageService.delete(user.getProfileImageId());

				} catch (Exception e) {

				}

				try {

					UserContactInfo userContactInfo = userContactInfoRepository.findByUserId(user.getId());

					if (userContactInfo == null) {

						throw new Exception();

					}

					removeUserContactInfo(userContactInfo.getId());

				} catch (Exception e) {

				}

				try {

					UserLocation userLocation = userLocationRepository.findByUserId(user.getId());

					if (userLocation == null) {

						throw new Exception();

					}

					removeUserLocation(userLocation.getId());

				} catch (Exception e) {

				}

				try {

					AdvocateJoinRequest advocateJoinRequest = advocateJoinRequestRepository.findByUserId(user.getId());

					if (advocateJoinRequest != null) {

						removeAdvocateJoinRequest(advocateJoinRequest.getId());

					}

				} catch (Exception e) {

				}

				try {

					Advocate advocate = advocateRepository.findByUserId(user.getId());

					removeAdvocate(advocate.getId());

				} catch (Exception e) {

				}

				try {

					Admin admin = adminRepository.findByUserId(user.getId());

					if (admin != null) {

						removeAdmin(admin.getId());

					}

				} catch (Exception e) {

				}

				try {

					CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

					removeCenterAdmin(centerAdmin.getId());

				} catch (Exception e) {

				}

				try {

					AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findByUserId(user.getId());

					if (adminJoinRequest != null) {

						removeAdminJoinRequest(adminJoinRequest.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<ChatMessage> list = chatMessageRepository.findByReceiverOrSender(userId, userId);

					for (ChatMessage i : list) {

						removeChatMessage(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<Notification> list = notificationRepository.findByUserId(userId);

					for (Notification i : list) {

						removeNotification(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<AskQuestion> list = questionRepository.findByUserId(user.getId());

					for (AskQuestion i : list) {

						removeQuestion(i.getId());

					}

				} catch (Exception e) {

				}

				try {

					List<AdvocateRating> list = advocateRatingRepository.findByUserId(user.getId());

					for (AdvocateRating i : list) {

						removeAdvocateRating(i.getId());

					}

				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeUserContactInfo(String contactInfoId) {

		try {

			UserContactInfo userContactInfo = userContactInfoRepository.findById(contactInfoId).get();

			if (userContactInfo == null) {

				throw new Exception();

			}

			long count = userContactInfoRepository.count();

			userContactInfoRepository.deleteById(contactInfoId);

			if (count != userContactInfoRepository.count()) {

				removeUser(userContactInfo.getUserId());

			}

		} catch (Exception e) {

		}

	}

	public void removeUserLocation(String userLocationId) {

		try {

			UserLocation userLocation = userLocationRepository.findById(userLocationId).get();

			if (userLocation == null) {

				throw new Exception();

			}

			long count = userLocationRepository.count();

			userLocationRepository.deleteById(userLocationId);

			if (count != userLocationRepository.count()) {

				removeUser(userLocation.getUserId());

			}

		} catch (Exception e) {

		}

	}

	public void removeAdvocateJoinRequest(String advocateJoinRequestId) {

		try {

			long count = advocateJoinRequestRepository.count();

			advocateJoinRequestRepository.deleteById(advocateJoinRequestId);

			if (count != advocateJoinRequestRepository.count()) {

			}

		} catch (Exception e) {

		}

	}

	public void removeAdvocate(String advocateId) {

		try {

			Advocate advocate = advocateRepository.findById(advocateId).get();

			if (advocate != null) {

				long count = advocateRepository.count();

				advocateRepository.deleteById(advocateId);

				if (count != advocateRepository.count()) {

					try {

						List<Case> list = caseRepository.findByAdvocateId(advocate.getId());

						for (Case i : list) {

							removeCase(i.getId());

						}

					} catch (Exception e) {

					}

					try {

						List<AdvocatePost> list = advocatePostRepository.findByAdvocateId(advocate.getId());

						if (!list.isEmpty()) {

							for (AdvocatePost i : list) {

								removePost(i.getId());

							}

						}

					} catch (Exception e) {

					}

					try {

						List<AnswerQuestion> list = answerRepository.findByAdvocateId(advocate.getId());

						for (AnswerQuestion i : list) {

							removeAnswer(i.getId());

						}

					} catch (Exception e) {

					}

					try {

						try {

							List<AdvocateRating> list = advocateRatingRepository.findByAdvocateId(advocate.getId());

							for (AdvocateRating i : list) {

								removeAdvocateRating(i.getId());

							}

						} catch (Exception e) {

						}

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAdmin(String adminId) {

		try {

			Admin admin = adminRepository.findById(adminId).get();

			if (admin != null) {

				long count = adminRepository.count();

				adminRepository.deleteById(adminId);

				if (count != adminRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeCenterAdmin(String centerAdminId) {

		try {

			CenterAdmin centerAdmin = centerAdminRepository.findById(centerAdminId).get();

			if (centerAdmin != null) {

				long count = centerAdminRepository.count();

				centerAdminRepository.deleteById(centerAdminId);

				if (count != centerAdminRepository.count()) {

					for (String i : centerAdmin.getAdmins()) {

						removeAdmin(i);

					}

					for (String i : centerAdmin.getAdvocates()) {

						removeAdvocate(i);

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAdminJoinRequest(String adminJoinRequestId) {

		try {

			AdminJoinRequest adminJoinRequest = adminJoinRequestRepository.findById(adminJoinRequestId).get();

			if (adminJoinRequest != null) {

				long count = adminJoinRequestRepository.count();

				adminJoinRequestRepository.deleteById(adminJoinRequestId);

				if (count != adminJoinRequestRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeChatMessage(String id) {

		try {

			ChatMessage chatMessage = chatMessageRepository.findById(id).get();

			if (chatMessage != null) {

				long count = chatMessageRepository.count();

				chatMessageRepository.deleteById(id);

				if (count != chatMessageRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeNotification(String id) {

		try {

			Notification notification = notificationRepository.findById(id).get();

			if (notification != null) {

				long count = notificationRepository.count();

				notificationRepository.deleteById(id);

				if (count != notificationRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removePost(String postId) {

		try {

			AdvocatePost post = advocatePostRepository.findById(postId).get();

			if (post != null) {

				long count = advocatePostRepository.count();

				advocatePostRepository.deleteById(postId);

				if (advocatePostRepository.count() != count) {

					try {

						List<PostReaction> list = postReactionRepository.findByAdvocatePostId(post.getId());

						for (PostReaction i : list) {

							removePostReaction(i.getId());

						}

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeQuestion(String questionId) {

		try {

			AskQuestion question = questionRepository.findById(questionId).get();

			if (question != null) {

				long count = questionRepository.count();

				questionRepository.deleteById(questionId);

				if (count != questionRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAnswer(String answerId) {

		try {

			AnswerQuestion answer = answerRepository.findById(answerId).get();

			if (answer != null) {

				long count = answerRepository.count();

				answerRepository.deleteById(answerId);

				if (count != answerRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAdvocateRating(String advocateRatingId) {

		try {

			AdvocateRating advocateRating = advocateRatingRepository.findById(advocateRatingId).get();

			if (advocateRating != null) {

				long count = advocateRatingRepository.count();

				advocateRatingRepository.deleteById(advocateRatingId);

				if (count != advocateRatingRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeCaseRequest(String caseRequestId) {

		try {

			CaseRequest caseRequest = caseRequestRepository.findById(caseRequestId).get();

			if (caseRequest != null) {

				long count = caseRequestRepository.count();

				caseRequestRepository.deleteById(caseRequestId);

				if (count != caseRequestRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeCase(String caseId) {

		try {

			Case acceptedCase = caseRepository.findById(caseId).get();

			if (acceptedCase != null) {

				long count = caseRepository.count();

				caseRepository.deleteById(caseId);

				if (count != caseRepository.count()) {

					try {

						List<ClientFeedback> list = clientFeedbackRepository.findByCaseId(acceptedCase.getId());

						for (ClientFeedback i : list) {

							removeClientFeedback(i.getId());

						}

					} catch (Exception e) {

					}

					try {

						CaseJudgment caseJudgment = caseJudgmentRepository.findByCaseId(acceptedCase.getId());

						if (caseJudgment != null) {

							removeCaseJudgment(caseJudgment.getId());

						}

					} catch (Exception e) {

					}

					try {

						DocumentDraft draft = documentDraftRepository.findByCaseId(acceptedCase.getId());

						if (draft != null) {

							removeDocumentDraft(draft.getId());

						}

					} catch (Exception e) {

					}

					try {

						List<Hearing> list = hearingRepository.findByCaseId(acceptedCase.getId());

						for (Hearing i : list) {

							removeHearing(i.getId());

						}

					} catch (Exception e) {

					}

					try {

						AppealCase appeal = caseAppealRepository.findByCaseId(acceptedCase.getId());

						if (appeal != null) {

							removeCaseAppeal(appeal.getId());

						}

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeCaseAppeal(String id) {

		try {

			AppealCase appeal = caseAppealRepository.findById(id).get();

			if (appeal == null) {

				throw new Exception();

			}

			long count = caseAppealRepository.count();

			caseAppealRepository.deleteById(id);

			if (count != caseAppealRepository.count()) {

			}

		} catch (Exception e) {

		}

	}

	public void removeHearing(String id) {

		try {

			Hearing hearing = hearingRepository.findById(id).get();

			if (hearing != null) {

				long count = hearingRepository.count();

				hearingRepository.deleteById(id);

				if (count != hearingRepository.count()) {

					try {

						AppealHearings appeal = appealHearingRepository.findByHearingId(hearing.getId());

						if (appeal != null) {

							removeAppealHearing(appeal.getId());

						}

					} catch (Exception e) {

					}

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeAppealHearing(String id) {

		try {

			AppealHearings appeal = appealHearingRepository.findById(id).get();

			if (appeal != null) {

				long count = appealHearingRepository.count();

				appealHearingRepository.deleteById(id);

				if (count != appealHearingRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeDocumentDraft(String id) {

		try {

			DocumentDraft draft = documentDraftRepository.findById(id).get();

			if (draft == null) {

				throw new Exception();

			}

			long count = documentDraftRepository.count();

			documentDraftRepository.deleteById(id);

			if (count != documentDraftRepository.count()) {

			}

		} catch (Exception e) {

		}

	}

	public void removeCaseJudgment(String id) {

		try {

			CaseJudgment caseJudgment = caseJudgmentRepository.findById(id).get();

			if (caseJudgment != null) {

				long count = caseJudgmentRepository.count();

				caseJudgmentRepository.deleteById(id);

				if (count != caseJudgmentRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removeClientFeedback(String id) {

		try {

			ClientFeedback feedback = clientFeedbackRepository.findById(id).get();

			if (feedback != null) {

				long count = clientFeedbackRepository.count();

				clientFeedbackRepository.deleteById(id);

				if (count != clientFeedbackRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

	public void removePostReaction(String id) {

		try {

			PostReaction postReaction = postReactionRepository.findById(id).get();

			if (postReaction != null) {

				long count = postReactionRepository.count();

				postReactionRepository.deleteById(id);

				if (count != postReactionRepository.count()) {

				}

			}

		} catch (Exception e) {

		}

	}

}
