package com.company.Restaurants.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.Restaurants.Dao.RestaurantDao;
import com.company.Restaurants.Dao.ReviewDao;
import com.company.Restaurants.Entity.Restaurant;
import com.company.Restaurants.Entity.Review;
import com.company.Restaurants.Entity.User;
import com.company.Restaurants.api.Place;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private RestaurantDao restaurantDao;
	
	private ReviewDao reviewDao;
	
	@Autowired
	public RestaurantServiceImpl(RestaurantDao restaurantDao, ReviewDao reviewDao) {
		this.restaurantDao = restaurantDao;
		this.reviewDao = reviewDao;
	}

	@Transactional
	public List<Restaurant> findAll() {
		
		List<Restaurant> theRestaurant = restaurantDao.findAll();
		
		return theRestaurant;
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
		
		restaurantDao.saveReview(theUser,theReview,theId);
		
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
	public Review findReviewbyId(int id) {
		
		return reviewDao.findReviewById(id);
		
	}

	@Transactional
	public List<Review> getallReviews() {
		
		return restaurantDao.getallReviews();
		
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
