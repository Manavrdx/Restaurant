package com.company.Restaurants.dao;

import java.util.List;

import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.external.Place;

public interface RestaurantDao {
	
	List<Restaurant> findAll();
	
	Restaurant findById(int id);
	
	void save(Restaurant theRestaurant);
	
	void deleteById(int id);
	
	List<Review> findReviewByRestaurantId(int id);

	void saveReview(User theUser, Review theReview, int theId);
	
	List<Place> getPlaces();

	List<Restaurant> searchRestaurants(String name, String vicinity);

	List<Review> findReviewByUserId(int theId);

	List<Review> getAllReviews();

	void saveUser(User theUser);

	User searchUser(String name, String email);
}
