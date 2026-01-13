package com.example.demo700.Controllers.UserControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserModels.AdvocateRating;
import com.example.demo700.Services.UserServices.AdvocateRatingService;

@RestController
@RequestMapping("/api/advocate-rating")
public class AdvocateRatingController {

    @Autowired
    private AdvocateRatingService advocateRatingService;

    // ================================
    // ADD RATING
    // ================================
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> giveRating(@RequestBody AdvocateRating advocateRating,
                                        @PathVariable String userId) {
        try {
            AdvocateRating saved = advocateRatingService.giveAdvocateRating(advocateRating, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================================
    // UPDATE RATING
    // ================================
    @PutMapping("/update/{ratingId}/{userId}")
    public ResponseEntity<?> updateRating(@RequestBody AdvocateRating advocateRating,
                                          @PathVariable String ratingId,
                                          @PathVariable String userId) {
        try {
            AdvocateRating updated = advocateRatingService.updateAdvocateRating(advocateRating, userId, ratingId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================================
    // GET ALL
    // ================================
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<AdvocateRating> list = advocateRatingService.seeAllAdvocateRating();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ================================
    // GET BY USER
    // ================================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(advocateRatingService.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ================================
    // GET BY ADVOCATE
    // ================================
    @GetMapping("/advocate/{advocateId}")
    public ResponseEntity<?> getByAdvocate(@PathVariable String advocateId) {
        try {
            return ResponseEntity.ok(advocateRatingService.findByAdvocateId(advocateId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ================================
    // GET BY RATING ID
    // ================================
    @GetMapping("/{ratingId}")
    public ResponseEntity<?> getById(@PathVariable String ratingId) {
        try {
            return ResponseEntity.ok(advocateRatingService.findByAdvocateRatingId(ratingId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ================================
    // GET BY RATING GREATER THAN
    // ================================
    @GetMapping("/rating-above/{rating}")
    public ResponseEntity<?> getRatingAbove(@PathVariable int rating) {
        try {
            return ResponseEntity.ok(advocateRatingService.findByRatingGreaterThan(rating));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ================================
    // DELETE
    // ================================
    @DeleteMapping("/delete/{ratingId}/{userId}")
    public ResponseEntity<?> delete(@PathVariable String ratingId,
                                    @PathVariable String userId) {
        try {
            boolean deleted = advocateRatingService.deleteAdvocateRating(userId, ratingId);
            return ResponseEntity.ok("Deleted: " + deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

