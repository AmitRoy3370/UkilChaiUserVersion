package com.example.demo700.Repositories.ChatRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.ChatModels.ReadableChat;

@Repository
public interface ReadableChatRepository extends MongoRepository<ReadableChat, String> {
	
	public ReadableChat findByChatId(String chatId);
	public List<ReadableChat> findByRead(boolean read);

}
