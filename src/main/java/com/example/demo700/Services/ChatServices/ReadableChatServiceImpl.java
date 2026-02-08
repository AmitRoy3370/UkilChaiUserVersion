package com.example.demo700.Services.ChatServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.ChatModels.ChatMessage;
import com.example.demo700.Model.ChatModels.ReadableChat;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.ChatRepositories.ChatMessageRepository;
import com.example.demo700.Repositories.ChatRepositories.ReadableChatRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class ReadableChatServiceImpl implements ReadableChatService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private ReadableChatRepository readableChatRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public ReadableChat addReadability(ReadableChat readableChat, String userId) {

		if (readableChat == null || userId == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			ChatMessage chat = chatMessageRepository.findById(readableChat.getChatId()).get();

			if (chat == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			ChatMessage chat = chatMessageRepository.findById(readableChat.getChatId()).get();

			if (chat == null) {

				throw new Exception();

			}

			if (!chat.getReceiver().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to change the readability of the chat.....");

		}

		try {

			ReadableChat chatRead = readableChatRepository.findByChatId(readableChat.getChatId());

			if (chatRead != null) {

				throw new ArithmeticException();

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This chat readable update is already setted....");

		} catch (Exception e) {

		}

		readableChat = readableChatRepository.save(readableChat);

		if (readableChat == null) {

			throw new ArithmeticException("Chat readability is not added at here.....");

		}

		return readableChat;
	}

	@Override
	public ReadableChat updateReadability(ReadableChat readableChat, String userId, String id) {

		if (readableChat == null || userId == null || id == null) {

			throw new NullPointerException("False request.....");

		}

		try {

			ReadableChat readChat = readableChatRepository.findById(id).get();

			if (readChat == null) {

				throw new Exception();

			}

			if (!readChat.getChatId().equals(readableChat.getChatId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat readability find at here....");

		}

		try {

			ChatMessage chat = chatMessageRepository.findById(readableChat.getChatId()).get();

			if (chat == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			ChatMessage chat = chatMessageRepository.findById(readableChat.getChatId()).get();

			if (chat == null) {

				throw new Exception();

			}

			if (!chat.getReceiver().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to change the readability of the chat.....");

		}

		try {

			ReadableChat chatRead = readableChatRepository.findByChatId(readableChat.getChatId());

			if (chatRead != null) {

				if (!chatRead.getId().equals(id)) {

					throw new ArithmeticException();

				}

			}

		} catch (ArithmeticException e) {

			throw new ArithmeticException("This chat readable update is already setted....");

		} catch (Exception e) {

		}

		readableChat.setId(id);

		readableChat = readableChatRepository.save(readableChat);

		if (readableChat == null) {

			throw new ArithmeticException("Chat readability is not added at here.....");

		}

		return readableChat;
	}

	@Override
	public ReadableChat findById(String id) {

		if (id == null) {

			throw new NullPointerException("False request...");

		}

		try {

			ReadableChat readChat = readableChatRepository.findById(id).get();

			if (readChat == null) {

				throw new Exception();

			}

			return readChat;

		} catch (Exception e) {

			throw new NoSuchElementException("No such readable chat find at here...");

		}

	}

	@Override
	public List<ReadableChat> seeAll() {

		try {

			List<ReadableChat> list = readableChatRepository.findAll();

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat read find at here...");

		}

	}

	@Override
	public ReadableChat findByChatId(String chatId) {

		if (chatId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			ReadableChat readChat = readableChatRepository.findByChatId(chatId);

			if (readChat == null) {

				throw new Exception();

			}

			return readChat;

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat read find at here...");

		}

	}

	@Override
	public List<ReadableChat> findByIsRead(boolean isRead) {

		try {

			List<ReadableChat> list = readableChatRepository.findByIsRead(isRead);

			if (list.isEmpty()) {

				throw new Exception();

			}

			return list;

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat read find at here...");

		}
	}

	@Override
	public boolean removeReadability(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("Fals request....");

		}

		try {

			ReadableChat readChat = readableChatRepository.findById(id).get();

			if (readChat == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such chat readability find at here....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = readableChatRepository.count();

					cleaner.removeReadableChat(id);

					return count != readableChatRepository.count();

				}

			} catch (Exception e) {

			}

			ReadableChat readableChat = readableChatRepository.findById(id).get();

			if (readableChat == null) {

				throw new Exception();

			}

			ChatMessage chat = chatMessageRepository.findById(readableChat.getChatId()).get();

			if (chat == null) {

				throw new Exception();

			}

			if (!chat.getReceiver().equals(user.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here to change the readability of the chat.....");

		}

		long count = readableChatRepository.count();

		cleaner.removeReadableChat(id);

		return count != readableChatRepository.count();
	}

}
