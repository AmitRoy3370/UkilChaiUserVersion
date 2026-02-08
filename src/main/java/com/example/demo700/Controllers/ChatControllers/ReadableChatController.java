package com.example.demo700.Controllers.ChatControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.ChatModels.ReadableChat;
import com.example.demo700.Services.ChatServices.ReadableChatService;

@RestController
@RequestMapping("/api/readable-chat")
public class ReadableChatController {

    @Autowired
    private ReadableChatService readableChatService;

    // ================= ADD =================
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addReadableChat(
            @RequestBody ReadableChat readableChat,
            @PathVariable String userId) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(readableChatService.addReadability(readableChat, userId));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= UPDATE =================
    @PutMapping("/update/{id}/{userId}")
    public ResponseEntity<?> updateReadableChat(
            @RequestBody ReadableChat readableChat,
            @PathVariable String userId,
            @PathVariable String id) {

        try {
            return ResponseEntity.ok(
                    readableChatService.updateReadability(readableChat, userId, id));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FIND BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {

        try {
            return ResponseEntity.ok(readableChatService.findById(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= SEE ALL =================
    @GetMapping("/all")
    public ResponseEntity<?> seeAll() {

        try {
            List<ReadableChat> list = readableChatService.seeAll();
            return ResponseEntity.ok(list);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND BY CHAT ID =================
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> findByChatId(@PathVariable String chatId) {

        try {
            return ResponseEntity.ok(
                    readableChatService.findByChatId(chatId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND BY READ STATUS =================
    @GetMapping("/status/{isRead}")
    public ResponseEntity<?> findByIsRead(@RequestParam boolean isRead) {

        try {
            return ResponseEntity.ok(
                    readableChatService.findByIsRead(isRead));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> deleteReadableChat(
            @PathVariable String id,
            @PathVariable String userId) {

        try {
            boolean removed =
                    readableChatService.removeReadability(id, userId);

            return ResponseEntity.ok(removed);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

