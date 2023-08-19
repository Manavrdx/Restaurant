package com.company.Restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.Restaurants.repository.restaurant.RestaurantRepository;
import com.company.Restaurants.repository.review.ReviewRepository;
import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.external.Place;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantRepository restaurantRepository;
	
	private final ReviewRepository reviewRepository;
	
	@Autowired
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
		this.restaurantRepository = restaurantRepository;
		this.reviewRepository = reviewRepository;
	}

	@Transactional
	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}
	
	@Transactional
	public Restaurant findById(int id) {
		return restaurantRepository.findById(id);
	}

	@Transactional
	public void save(Restaurant theRestaurant) {
		restaurantRepository.save(theRestaurant);
	}

	@Transactional
	public void deleteById(int id) {
		restaurantRepository.deleteById(id);
	}

	@Transactional
	public List<Review> findReviewByRestaurantId(int id) {
		return restaurantRepository.findReviewByRestaurantId(id);
	}

	@Transactional
	public void saveReview(User theUser, Review theReview, int theId) {
		restaurantRepository.saveReview(theUser, theReview, theId);
	}

	@Transactional
	public List<Place> getPlaces() {
		return restaurantRepository.getPlaces();
	}

	@Transactional
	public List<Restaurant> searchRestaurants(String name, String vicinity) {
		return restaurantRepository.searchRestaurants(name,vicinity);
	}

	@Transactional
	public List<Review> findReviewByUserId(int theId) {
		return restaurantRepository.findReviewByUserId(theId);
	}

	@Transactional
	public Review findReviewById(int id) {
		return reviewRepository.findReviewById(id);
	}

	@Transactional
	public List<Review> getAllReviews() {
		return restaurantRepository.getAllReviews();
	}

	@Transactional
	public void saveUser(User theUser) {
		restaurantRepository.saveUser(theUser);
	}

	@Transactional
	public User searchUser(String name, String email) {
		return restaurantRepository.searchUser(name, email);
	}

}
