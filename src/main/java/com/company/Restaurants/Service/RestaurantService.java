package com.company.Restaurants.Service;

import java.util.List;

import com.company.Restaurants.Entity.Restaurant;
import com.company.Restaurants.Entity.Review;
import com.company.Restaurants.Entity.User;
import com.company.Restaurants.api.Place;

public interface RestaurantService {
	
	public List<Restaurant> findAll();
	
	public Restaurant findById(int id);
	
	public void save(Restaurant theRestaurant);
	
	public void deleteById(int id);
	
	public List<Review> findReviewByRestaurantId(int id);

	public void saveReview(User theUser, Review theReview, int theId);
	
	public List<Place> getPlaces();

	public List<Restaurant> searchRestaurants(String name, String vicinity);

	public List<Review> findReviewByUserId(int theId);
	
	public Review findReviewbyId(int id);

	public List<Review> getallReviews();

	public void saveUser(User theUser);

	public User searchUser(String name, String email);

}
