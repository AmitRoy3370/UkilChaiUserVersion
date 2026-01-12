package com.example.demo700.Controllers.UserControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserModels.ClientFeedback;
import com.example.demo700.Services.UserServices.ClientFeedbackService;

@RestController
@RequestMapping("/api/client-feedback")
public class ClientFeedbackController {

    @Autowired
    private ClientFeedbackService clientFeedbackService;

    // ===================== ADD =====================
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ClientFeedback clientFeedback, @RequestParam String userId) {
        try {
            return ResponseEntity.ok(
                    clientFeedbackService.addClientFeedback(
                            clientFeedback,
                            userId
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ===================== UPDATE =====================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") String clientFeedbackId,
            @RequestBody ClientFeedback clientFeedback,
            @RequestParam String userId
    ) {
        try {
            return ResponseEntity.ok(
                    clientFeedbackService.updateCientFeedback(
                            clientFeedback,
                            userId,
                            clientFeedbackId
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // ===================== FIND BY CASE =====================
    @GetMapping("/case/{caseId}")
    public ResponseEntity<?> findByCase(@PathVariable String caseId) {
        try {
            return ResponseEntity.ok(clientFeedbackService.findByCaseId(caseId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ===================== FIND BY USER =====================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUser(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(clientFeedbackService.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ===================== SEARCH =====================
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String feedback) {
        try {
            return ResponseEntity.ok(
                    clientFeedbackService.findByFeedbackContainingIgnoreCase(feedback)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ===================== FIND ALL =====================
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(clientFeedbackService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ===================== FIND BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(clientFeedbackService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id,
            @RequestParam String userId
    ) {
        try {
            return ResponseEntity.ok(
                    clientFeedbackService.removeClientFeedback(id, userId)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
