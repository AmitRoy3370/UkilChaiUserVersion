package com.example.demo700.Controllers.CaseControllers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.CaseModels.AppealHearings;
import com.example.demo700.Services.CaseServices.AppealHearingService;

@RestController
@RequestMapping("/appealHearing")
public class AppealHearingController {

    @Autowired
    private AppealHearingService appealService;

    // ================= ADD =================
    @PostMapping(value = "/add/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(
            @PathVariable String userId,
            @RequestPart("hearingId") String hearingId,
            @RequestPart("reason") String reason,
            @RequestPart(value = "appealHearingTime", required = false) String appealTime) {

        try {
            AppealHearings appeal = new AppealHearings();
            appeal.setHearingId(hearingId);
            appeal.setReason(reason);

            if (appealTime != null)
                appeal.setAppealHearingTime(Instant.parse(appealTime));

            return ResponseEntity.ok(appealService.addAppealHearings(appeal, userId));
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= UPDATE =================
    @PutMapping(value="/update/{appealId}/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable String appealId,
            @PathVariable String userId,
            @RequestPart("hearingId") String hearingId,
            @RequestPart("reason") String reason,
            @RequestPart(value = "appealHearingTime", required = false) String appealTime) {

        try {
            AppealHearings appeal = new AppealHearings();
            appeal.setHearingId(hearingId);
            appeal.setReason(reason);

            if (appealTime != null)
                appeal.setAppealHearingTime(Instant.parse(appealTime));

            return ResponseEntity.ok(
                    appealService.updateAppealHearings(appeal, userId, appealId)
            );
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= BY HEARING =================
    @GetMapping("/hearing/{hearingId}")
    public ResponseEntity<?> byHearing(@PathVariable String hearingId) {
        try {
            return ResponseEntity.ok(appealService.findByHearingId(hearingId));
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= BY REASON =================
    @GetMapping("/reason/{reason}")
    public ResponseEntity<?> byReason(@PathVariable String reason) {
        try {
            return ResponseEntity.ok(
                    appealService.findByReasonContainingIgnoreCase(reason)
            );
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= BEFORE DATE =================
    @GetMapping("/before")
    public ResponseEntity<?> before(@RequestParam String date) {
        try {
            return ResponseEntity.ok(
                    appealService.findByAppealHearingTimeBefore(Instant.parse(date))
            );
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= AFTER DATE =================
    @GetMapping("/after")
    public ResponseEntity<?> after(@RequestParam String date) {
        try {
            return ResponseEntity.ok(
                    appealService.findByAppealHearingTimeAfter(Instant.parse(date))
            );
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= ALL =================
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        try {
            return ResponseEntity.ok(appealService.seeAll());
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(appealService.findById(id));
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> delete(
            @PathVariable String id,
            @PathVariable String userId) {
        try {
            return ResponseEntity.ok(
                    appealService.removeAppealHearings(id, userId)
            );
        } catch (Exception e) {
            return error(e);
        }
    }

    // ================= ERROR HANDLER =================
    private ResponseEntity<?> error(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("error", e.getClass().getSimpleName());
        map.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(map);
    }
}

