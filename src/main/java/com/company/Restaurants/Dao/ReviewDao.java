package com.company.Restaurants.Dao;

import java.util.List;

import com.company.Restaurants.Entity.Review;

public interface ReviewDao {

	public List<Review> findAll();
	
	public Review findReviewById(int id);
	
}
