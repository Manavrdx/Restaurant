package com.company.Restaurants.repository.review;

import java.util.List;

import com.company.Restaurants.entity.Review;

public interface ReviewRepository {

	List<Review> findAll();
	
	Review findReviewById(int id);
	
}
