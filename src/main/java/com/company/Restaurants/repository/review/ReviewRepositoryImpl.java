package com.company.Restaurants.repository.review;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.Restaurants.entity.Review;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
	
	private final EntityManager entityManager;
	
	@Autowired
	public ReviewRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Review> findAll() {
		TypedQuery<Review> theQuery = entityManager.createQuery("from Review",Review.class);
		return theQuery.getResultList();
	}

	public Review findReviewById(int id) {
		return entityManager.find(Review.class,id);
	}

}
