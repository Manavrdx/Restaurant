package com.company.Restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.Restaurants.dao.RestaurantDao;
import com.company.Restaurants.dao.ReviewDao;
import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.external.Place;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantDao restaurantDao;
	
	private final ReviewDao reviewDao;
	
	@Autowired
	public RestaurantServiceImpl(RestaurantDao restaurantDao, ReviewDao reviewDao) {
		this.restaurantDao = restaurantDao;
		this.reviewDao = reviewDao;
	}

	@Transactional
	public List<Restaurant> findAll() {
		return restaurantDao.findAll();
	}
	
	@Transactional
	public Restaurant findById(int id) {
		return restaurantDao.findById(id);
	}

	@Transactional
	public void save(Restaurant theRestaurant) {
		restaurantDao.save(theRestaurant);
	}

	@Transactional
	public void deleteById(int id) {
		restaurantDao.deleteById(id);
	}

	@Transactional
	public List<Review> findReviewByRestaurantId(int id) {
		return restaurantDao.findReviewByRestaurantId(id);
	}

	@Transactional
	public void saveReview(User theUser, Review theReview, int theId) {
		restaurantDao.saveReview(theUser, theReview, theId);
	}

	@Transactional
	public List<Place> getPlaces() {
		return restaurantDao.getPlaces();
	}

	@Transactional
	public List<Restaurant> searchRestaurants(String name, String vicinity) {
		return restaurantDao.searchRestaurants(name,vicinity);
	}

	@Transactional
	public List<Review> findReviewByUserId(int theId) {
		return restaurantDao.findReviewByUserId(theId);
	}

	@Transactional
	public Review findReviewById(int id) {
		return reviewDao.findReviewById(id);
	}

	@Transactional
	public List<Review> getAllReviews() {
		return restaurantDao.getAllReviews();
	}

	@Transactional
	public void saveUser(User theUser) {
		restaurantDao.saveUser(theUser);
	}

	@Transactional
	public User searchUser(String name, String email) {
		return restaurantDao.searchUser(name, email);
	}

}
