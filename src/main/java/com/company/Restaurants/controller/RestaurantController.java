package com.company.Restaurants.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.company.Restaurants.entity.Restaurant;
import com.company.Restaurants.entity.Review;
import com.company.Restaurants.entity.User;
import com.company.Restaurants.service.RestaurantService;
import com.company.Restaurants.external.Place;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@GetMapping("/main")
	public String getReviews(Model theModel) {
		List<Review> theReviews = restaurantService.getAllReviews();
		theModel.addAttribute("reviews",theReviews);
		return "main-page";
	}

	@GetMapping("/all")
	public String getRestaurants(Model theModel) {
		List<Restaurant> theRestaurant = restaurantService.findAll();
		theModel.addAttribute("restaurants",theRestaurant);
		return "restaurants-page";
	}
	
	@GetMapping("/places")
	public String getPlaces(Model theModel) {
		List<Place> places = restaurantService.getPlaces();
		theModel.addAttribute("places",places);
		return "place-page" ;
	}
	
	@GetMapping("/reviewForId")
	public String showRestaurantReviews(@RequestParam("restaurantId") int theId, Model theModel) {
		List<Review> theReview = restaurantService.findReviewByRestaurantId(theId);
		theModel.addAttribute("reviews",theReview);
		return "review-page";
	}
	
	@GetMapping("/addReview")
	public String addRestaurantReview(@RequestParam("restaurantId") int theId, Model theModel) {
		Review theReview = new Review();
		theModel.addAttribute("review",theReview);
		theModel.addAttribute("Id",theId);
		return "review-form";
	}
	
	@GetMapping("/search")
	public String searchRestaurant(@RequestParam("name") String name, @RequestParam("vicinity") String vicinity
									, Model theModel) {
		List<Restaurant> theRestaurant = restaurantService.searchRestaurants(name,vicinity);
		theModel.addAttribute("restaurants",theRestaurant);
		return "restaurants-page";
	}
	
	@PostMapping("/saveReview")
	public String saveReview(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("restaurantId") int theId,
							 @RequestParam("comment")String theComment, @RequestParam("image") CommonsMultipartFile file) {
		byte[] data = file.getBytes();
		Restaurant theRestaurant = restaurantService.findById(theId);
		Review theReview = new Review(theRestaurant.getName(),theComment,data);
		User theUser = restaurantService.searchUser(name,email);
		restaurantService.saveReview(theUser,theReview,theId);
		return "redirect:/restaurant/places";
	}
	
	@GetMapping("/user/{id}")
	public String reviewForUser(Model theModel, @PathVariable("id") int theId) {
		List<Review> theReview = restaurantService.findReviewByUserId(theId);
		theModel.addAttribute("reviews",theReview);
		return "review-page";
	}
	
	@GetMapping("/image")
	public void showProductImage(@RequestParam("id") int theId
	                               ,HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		Review theReview = restaurantService.findReviewById(theId);
		InputStream is = new ByteArrayInputStream(theReview.getImage());
		IOUtils.copy(is, response.getOutputStream());
	}
	
}
