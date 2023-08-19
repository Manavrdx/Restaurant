package com.company.Restaurants.repository.restaurant;

import java.util.List;
import java.util.Objects;
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
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	private final RestTemplate restTemplate;
	
	private final String googleMapUrl;
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private final EntityManager entityManager;
	
	@Autowired
	public RestaurantRepositoryImpl(EntityManager entityManager, RestTemplate theRestTemplate,
									@Value("${google.maps.url}") String theUrl) {
		logger.info("Loaded google map URL:: "+ theUrl);
		this.entityManager = entityManager;
		restTemplate = theRestTemplate;
		googleMapUrl = theUrl;
	}

	public List<Restaurant> findAll() {
		TypedQuery<Restaurant> query = entityManager.createQuery("from Restaurant",Restaurant.class);
		return query.getResultList();
	}

	public Restaurant findById(int id) {
		return entityManager.find(Restaurant.class, id);
	}

	public void save(Restaurant theRestaurant) {
		entityManager.merge(theRestaurant);
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
		ResponseEntity<PlaceResult> responseEntity = restTemplate.exchange(googleMapUrl,
													 HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {
				});

		// get the list of places from response
		List<Place> places = Objects.requireNonNull(responseEntity.getBody()).getResults();
		
		logger.info("In getPlaces(): Places" + places);
		
		return places;
	}


	public List<Restaurant> searchRestaurants(String name, String vicinity) {
		Query theQuery = entityManager.createQuery("from Restaurant where name=:restaurant and vicinity=:location");
		theQuery.setParameter("restaurant", name);
		theQuery.setParameter("location", vicinity);
		List<Restaurant> restaurants = theQuery.getResultList();

		if(restaurants.isEmpty()) {
			Restaurant restaurant = new Restaurant(name,vicinity);
			save(restaurant);
			restaurants.add(restaurant);
		}
		
		return restaurants;
	}

	public List<Review> findReviewByUserId(int id) {
		User user = entityManager.find(User.class, id);
		return user.getReviews();
	}

	public List<Review> getAllReviews() {
		TypedQuery<Review> query = entityManager.createQuery("from Review", Review.class);
		return query.getResultList();
	}


	public void saveUser(User user) {
		entityManager.merge(user);
	}

	public User searchUser(String name, String email) {
		Query query = entityManager.createQuery("from User where name=:name and email=:emailId");
		query.setParameter("name",name);
		query.setParameter("emailId",email);
		List<User> theUser = query.getResultList();
		if(theUser.isEmpty()) {
			User user = new User(name,email);
			entityManager.merge(user);
			theUser.add(user);
		}
		
		return theUser.get(0);
	}

}
