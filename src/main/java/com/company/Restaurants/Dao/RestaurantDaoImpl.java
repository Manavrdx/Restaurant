package com.company.Restaurants.Dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.company.Restaurants.Entity.Restaurant;
import com.company.Restaurants.Entity.Review;
import com.company.Restaurants.Entity.User;
import com.company.Restaurants.api.Place;
import com.company.Restaurants.api.PlaceResult;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {
	
	private RestTemplate restTemplate;
	
	private String resturl;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private EntityManager entityManager;
	
	@Autowired
	public RestaurantDaoImpl(EntityManager entityManager,RestTemplate theRestTemplate,
							@Value("${myrest.url}") String theUrl) {
		
		this.entityManager = entityManager;
		
		restTemplate = theRestTemplate;
		
		resturl = theUrl;
		
		logger.info("Loaded property: rest.url="+resturl);
	}
	

	public List<Restaurant> findAll() {
		
		TypedQuery<Restaurant> theQuery = entityManager.createQuery("from Restaurant",Restaurant.class);
		
		List<Restaurant> restaurants = theQuery.getResultList();
		
		return restaurants;
	}

	public Restaurant findById(int id) {
		
		Restaurant theRestaurant = entityManager.find(Restaurant.class, id);
		
		return theRestaurant;
	}

	public void save(Restaurant theRestaurant) {
		
		Restaurant dbRestaurant = entityManager.merge(theRestaurant);

		theRestaurant.setId(dbRestaurant.getId());
	}

	public void deleteById(int id) {
		
		Query theQuery = entityManager.createQuery("delete from Restaurant where id=:restaurantId");
		
		theQuery.setParameter("restaurantId", id);
		
		theQuery.executeUpdate();
	}
	
	public List<Review> findReviewByRestaurantId(int id) {
		
		Restaurant theRestaurant = entityManager.find(Restaurant.class,id);
		
		List<Review> theReview = theRestaurant.getReviews();
		
		return theReview;
	}

	public void saveReview(User theUser, Review theReview, int theId) {
		
		Restaurant theRestaurant = entityManager.find(Restaurant.class,theId);
		
		theUser.getReviews().add(theReview);
		
		theRestaurant.getReviews().add(theReview);
		
	}

	public List<Place> getPlaces() {

		ResponseEntity<PlaceResult> responseEntity = restTemplate.exchange(resturl,
													 HttpMethod.GET, null,
													 new ParameterizedTypeReference<PlaceResult>() {});

		// get the list of places from response
		List<Place> places = responseEntity.getBody().getResults();
		
		logger.info("in getPlaces(): Places" + places);
		
		return places;
	
	}


	public List<Restaurant> searchRestaurants(String name, String vicinity) {
		
		Query theQuery = entityManager.createQuery("from Restaurant where name=:restaurant and vicinity=:location");
		
		theQuery.setParameter("restaurant",name);
		
		theQuery.setParameter("location",vicinity);
		
		List<Restaurant> theRestaurant = theQuery.getResultList();
		
		
		if(theRestaurant.isEmpty()) {

			System.out.print("Datas: "+theRestaurant);
			Restaurant restaurant = new Restaurant(name,vicinity);
			save(restaurant);
			theRestaurant.add(restaurant);
		}
		
		return theRestaurant;
	}


	public List<Review> findReviewByUserId(int theId) {
		
		User theUser = entityManager.find(User.class, theId);
		
		List<Review> theReview =  theUser.getReviews();
		
		return theReview;
	}


	public List<Review> getallReviews() {
		
		TypedQuery<Review> theQuery = entityManager.createQuery("from Review",Review.class);
		
		List<Review> reviews = theQuery.getResultList();
		
		return reviews;
		
	}


	public void saveUser(User theUser) {
		
		User dbuser = entityManager.merge(theUser);

		theUser.setId(dbuser.getId());
		
	}


	public User searchUser(String name, String email) {
		
		Query theQuery = entityManager.createQuery("from User where name=:name and email=:emailId");
		
		theQuery.setParameter("name",name);
		
		theQuery.setParameter("emailId",email);
		
		List<User> theUser = theQuery.getResultList();
		
		if(theUser.isEmpty()) {
			
			User nuser = new User(name,email);
			entityManager.merge(nuser);
			theUser.add(nuser);
		}
		
		return theUser.get(0);
	}
	

}
