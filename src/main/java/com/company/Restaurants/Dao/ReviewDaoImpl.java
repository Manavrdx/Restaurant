package com.company.Restaurants.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.Restaurants.Entity.Review;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	
	private EntityManager entityManager;
	
	@Autowired
	public ReviewDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Review> findAll() {
		
		TypedQuery<Review> theQuery = entityManager.createQuery("from Review",Review.class);
		
		List<Review> reviews = theQuery.getResultList();
		
		return reviews;

	}

	public Review findReviewById(int id) {
		
		Review theReview = entityManager.find(Review.class,id);
		
		return theReview;
		
	}


}
