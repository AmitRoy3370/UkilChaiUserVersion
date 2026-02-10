package com.example.demo700.Controllers.CaseControllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.CaseModels.CaseClose;
import com.example.demo700.Services.CaseServices.CaseCloseService;

@RestController
@RequestMapping("/api/case-close")
public class CaseCloseController {

    @Autowired
    private CaseCloseService caseCloseService;

    // ================= ADD =================
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addCaseClose(
            @RequestBody CaseClose caseClose,
            @PathVariable String userId) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(caseCloseService.addCaseClose(caseClose, userId));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= UPDATE =================
    @PutMapping("/update/{closedCaseId}/{userId}")
    public ResponseEntity<?> updateCaseClose(
            @RequestBody CaseClose caseClose,
            @PathVariable String userId,
            @PathVariable String closedCaseId) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.updateCaseClose(caseClose, userId, closedCaseId));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FIND BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {

        try {
            return ResponseEntity.ok(caseCloseService.findById(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND ALL =================
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {

        try {
            List<CaseClose> list = caseCloseService.findAll();
            return ResponseEntity.ok(list);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= FIND BY CASE ID =================
    @GetMapping("/case/{caseId}")
    public ResponseEntity<?> findByCaseId(@PathVariable String caseId) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.findByCaseId(caseId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= DATE FILTER =================
    @GetMapping("/before")
    public ResponseEntity<?> findBefore(@RequestParam Instant closedDate) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.findByClosedDateBefore(closedDate));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/after")
    public ResponseEntity<?> findAfter(@RequestParam Instant closedDate) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.findByClosedDateAfter(closedDate));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDate(@RequestParam Instant closedDate) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.findByClosedDate(closedDate));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FIND BY OPEN =================
    @GetMapping("/open")
    public ResponseEntity<?> findByOpen(@RequestParam boolean open) {

        try {
            return ResponseEntity.ok(
                    caseCloseService.findByOpen(open));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{closedCaseId}/{userId}")
    public ResponseEntity<?> deleteCaseClose(
            @PathVariable String closedCaseId,
            @PathVariable String userId) {

        try {
            boolean removed =
                    caseCloseService.deleteClosedCase(closedCaseId, userId);

            return ResponseEntity.ok(removed);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

