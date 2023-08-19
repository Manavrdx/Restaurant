package com.company.Restaurants.controller;

import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.external.Place;
import com.company.Restaurants.service.RestaurantService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@GetMapping("/main")
	public String getReviews(Model model) {
		List<Review> reviews = restaurantService.getAllReviews();
		model.addAttribute("reviews", reviews);
		return "main-page";
	}

	@GetMapping("/all")
	public String getRestaurants(Model model) {
		List<Restaurant> restaurants = restaurantService.findAll();
		model.addAttribute("restaurants", restaurants);
		return "restaurants-page";
	}
	
	@GetMapping("/places")
	public String getPlaces(Model model) {
		List<Place> places = restaurantService.getPlaces();
		model.addAttribute("places", places);
		return "place-page" ;
	}
	
	@GetMapping("/reviewForId")
	public String showRestaurantReviews(@RequestParam("restaurantId") int id, Model model) {
		List<Review> reviews = restaurantService.findReviewByRestaurantId(id);
		model.addAttribute("reviews", reviews);
		return "review-page";
	}
	
	@GetMapping("/addReview")
	public String addRestaurantReview(@RequestParam("restaurantId") int id, Model model) {
		Review review = new Review();
		model.addAttribute("review", review);
		model.addAttribute("Id", id);
		return "review-form";
	}
	
	@GetMapping("/search")
	public String searchRestaurant(@RequestParam("name") String name, @RequestParam("vicinity") String vicinity,
								   Model model) {
		List<Restaurant> theRestaurant = restaurantService.searchRestaurants(name, vicinity);
		model.addAttribute("restaurants", theRestaurant);
		return "restaurants-page";
	}
	
	@PostMapping("/saveReview")
	public String saveReview(@RequestParam("name") String name, @RequestParam("email") String email,
							 @RequestParam("restaurantId") int restaurantId, @RequestParam("comment") String theComment,
							 @RequestParam("image") CommonsMultipartFile file) {
		byte[] data = file.getBytes();
		Restaurant theRestaurant = restaurantService.findById(restaurantId);
		Review theReview = new Review(theRestaurant.getName(), theComment,data);
		User theUser = restaurantService.searchUser(name, email);
		restaurantService.saveReview(theUser, theReview, restaurantId);
		return "redirect:/restaurant/places";
	}
	
	@GetMapping("/user/{id}")
	public String reviewForUser(Model model, @PathVariable("id") int id) {
		List<Review> theReview = restaurantService.findReviewByUserId(id);
		model.addAttribute("reviews",theReview);
		return "review-page";
	}
	
	@GetMapping("/image")
	public void showProductImage(@RequestParam("id") int id,
								 HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		Review theReview = restaurantService.findReviewById(id);
		InputStream is = new ByteArrayInputStream(theReview.getImage());
		IOUtils.copy(is, response.getOutputStream());
	}
	
}
