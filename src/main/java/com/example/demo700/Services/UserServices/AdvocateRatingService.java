package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.AdvocateRating;

public interface AdvocateRatingService {

	public AdvocateRating giveAdvocateRating(AdvocateRating advocateRating, String userId);
	public AdvocateRating updateAdvocateRating(AdvocateRating advocateRating, String userId, String advocateRatingId);
	public List<AdvocateRating> seeAllAdvocateRating();
	public List<AdvocateRating> findByUserId(String userId);
	public List<AdvocateRating> findByAdvocateId(String advocateId);
	public AdvocateRating findByAdvocateRatingId(String advocateRatingId);
	public List<AdvocateRating> findByRatingGreaterThan(int rating);
	public boolean deleteAdvocateRating(String userId, String ratingId);
	
}
