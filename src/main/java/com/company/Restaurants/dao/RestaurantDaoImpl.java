package com.company.Restaurants.dao;

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

import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.external.Place;
import com.company.Restaurants.external.PlaceResult;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {
	
	private final RestTemplate restTemplate;
	
	private final String restUrl;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private final EntityManager entityManager;
	
	@Autowired
	public RestaurantDaoImpl(EntityManager entityManager,RestTemplate theRestTemplate,
							@Value("${google.maps.url}") String theUrl) {
		logger.info("Loaded google map URL:: "+ theUrl);
		this.entityManager = entityManager;
		restTemplate = theRestTemplate;
		restUrl = theUrl;
	}

	public List<Restaurant> findAll() {
		TypedQuery<Restaurant> theQuery = entityManager.createQuery("from Restaurant",Restaurant.class);
		return theQuery.getResultList();
	}

	public Restaurant findById(int id) {
		return entityManager.find(Restaurant.class, id);
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
		return theRestaurant.getReviews();
	}

	public void saveReview(User theUser, Review theReview, int theId) {
		Restaurant theRestaurant = entityManager.find(Restaurant.class,theId);
		theUser.getReviews().add(theReview);
		theRestaurant.getReviews().add(theReview);
	}

	public List<Place> getPlaces() {
		ResponseEntity<PlaceResult> responseEntity = restTemplate.exchange(restUrl,
													 HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {
				});

		// get the list of places from response
		List<Place> places = responseEntity.getBody().getResults();
		
		logger.info("In getPlaces(): Places" + places);
		
		return places;
	}


	public List<Restaurant> searchRestaurants(String name, String vicinity) {
		Query theQuery = entityManager.createQuery("from Restaurant where name=:restaurant and vicinity=:location");
		theQuery.setParameter("restaurant",name);
		theQuery.setParameter("location",vicinity);
		List<Restaurant> theRestaurant = theQuery.getResultList();

		if(theRestaurant.isEmpty()) {
			Restaurant restaurant = new Restaurant(name,vicinity);
			save(restaurant);
			theRestaurant.add(restaurant);
		}
		
		return theRestaurant;
	}


	public List<Review> findReviewByUserId(int theId) {
		User theUser = entityManager.find(User.class, theId);
		return theUser.getReviews();
	}


	public List<Review> getAllReviews() {
		TypedQuery<Review> theQuery = entityManager.createQuery("from Review", Review.class);
		return theQuery.getResultList();
	}


	public void saveUser(User theUser) {
		User dbUser = entityManager.merge(theUser);
		theUser.setId(dbUser.getId());
	}

	public User searchUser(String name, String email) {
		Query theQuery = entityManager.createQuery("from User where name=:name and email=:emailId");
		theQuery.setParameter("name",name);
		theQuery.setParameter("emailId",email);
		List<User> theUser = theQuery.getResultList();
		if(theUser.isEmpty()) {
			User user = new User(name,email);
			entityManager.merge(user);
			theUser.add(user);
		}
		
		return theUser.get(0);
	}

}
