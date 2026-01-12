package com.example.demo700.Services.UserServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Model.UserModels.PostReaction;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.PostRepository;
import com.example.demo700.Repositories.UserRepositories.PostReactionRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class PostReactionServiceImpl implements PostReactionService {

	@Autowired
	private PostReactionRepository postReactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository advocatePostRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public PostReaction addPostReaction(PostReaction postReaction, String userId) {

		if (postReaction == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postReaction.getAdvocatePostId()).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post find at here...");

		}

		postReaction = postReactionRepository.save(postReaction);

		if (postReaction == null) {

			throw new ArithmeticException("Data is not added at here....");

		}

		return postReaction;
	}

	@Override
	public PostReaction updatePostReaction(PostReaction postReaction, String userId, String postReactionId) {

		if (postReaction == null || userId == null || postReactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			PostReaction reaction = postReactionRepository.findById(postReactionId).get();

			if (reaction == null) {

				throw new Exception();

			}

			if (!reaction.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post reaction find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postReaction.getAdvocatePostId()).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post find at here...");

		}

		postReaction.setId(postReactionId);

		postReaction = postReactionRepository.save(postReaction);

		if (postReaction == null) {

			throw new ArithmeticException("Data is not added at here....");

		}

		return postReaction;
	}

	@Override
	public List<PostReaction> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return list;
	}

	@Override
	public List<PostReaction> findByAdvocatePostId(String advocatePostId) {

		if (advocatePostId == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByAdvocatePostId(advocatePostId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return list;
	}

	@Override
	public List<PostReaction> findByAdvocatePostIdAndPostReaction(String advocatePostId, PostReactions postReaction) {

		if (advocatePostId == null || postReaction == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByAdvocatePostIdAndPostReaction(advocatePostId,
				postReaction);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return list;
	}

	@Override
	public List<PostReaction> findByCommentContainingIgnoreCase(String comment) {

		if (comment == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByCommentContainingIgnoreCase(comment);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return list;
	}

	@Override
	public List<PostReaction> findAll() {

		List<PostReaction> list = postReactionRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return list;
	}

	@Override
	public PostReaction findById(String id) {

		try {

			PostReaction postReaction = postReactionRepository.findById(id).get();

			if (postReaction == null) {

				throw new Exception();

			}

			return postReaction;

		} catch (Exception e) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

	}

	@Override
	public boolean removePostReaction(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin != null) {

				long count = postReactionRepository.count();

				cleaner.removePostReaction(id);

				return count != postReactionRepository.count();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			PostReaction reaction = postReactionRepository.findById(id).get();

			if (reaction == null) {

				throw new Exception();

			}

			if (!reaction.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post reaction find at here...");

		}

		long count = postReactionRepository.count();

		cleaner.removePostReaction(id);

		return count != postReactionRepository.count();
	}

}
