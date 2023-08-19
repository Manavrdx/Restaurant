package com.company.Restaurants.dao;

import java.util.List;

import com.company.Restaurants.entity.Review;

public interface ReviewDao {

	List<Review> findAll();
	
	Review findReviewById(int id);
	
}
