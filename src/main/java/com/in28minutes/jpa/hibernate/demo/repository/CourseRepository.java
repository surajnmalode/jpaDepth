package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;

@Repository
@Transactional
public class CourseRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;
	
	public Course findById(Long id) {
		return em.find(Course.class, id);
	}
	
	public Course save(Course course) {
		if (course.getId() == null) {
			em.persist(course);
		} else {
			em.merge(course);
		}
		return course;
	}
	
	public void deleteById(Long id) {
		Course course = findById(id);
		em.remove(course);
	}
	
	public void playWithEntityManger() {
		
		Course course1 = new Course("Web Services in 100 Steps");
		em.persist(course1);
		
		Course course2 = findById(10001L);
		
		course2.setName("JPA in 50 Steps - Updated");
		
	}

	public void addHardcodedReviewsForCourse() {
		//1. Get the course 10003
		Course course = findById(10003L);
		logger.info("course.getReviews() -> {}", course.getReviews());
		//2. Add 2 reviews to it
		Review review1 = new Review("5", "Great Hands-on Stuff");
		Review review2 = new Review("5", "Hatsoff");
		course.addReview(review1);
		review1.setCourse(course);
		
		course.addReview(review2);
		review2.setCourse(course);
		//3. save it to the database
		em.persist(review1);
		em.persist(review2);
	}
	
	public void addReviewsForCourse(Long courseId, List<Review> reviews) {
		//1. Get the course 10003
		Course course = findById(10003L);
		//2. Add 2 reviews to it
		for(Review review : reviews) {
			course.addReview(review);
			review.setCourse(course);
			//3. save it to the database
			em.persist(review);			
		}
		
	}
}
