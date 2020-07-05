package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class NativeQueriesTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void native_queries_basic() {
		Query query = em.createNativeQuery("SELECT * FROM COURSE", Course.class);
		List resultList= query.getResultList();
		logger.info("SELECT * FROM COURSE->{}", resultList);
	}
	
	@Test
	public void native_queries_with_parameters() {
		Query query = em.createNativeQuery("SELECT * FROM COURSE where id=?", Course.class);
		query.setParameter(1, 10000L);
		List resultList= query.getResultList();
		logger.info("SELECT * FROM COURSE where id=?->{}", resultList);
	}
	
	@Test
	public void native_queries_with_named_parameters() {
		Query query = em.createNativeQuery("SELECT * FROM COURSE where id= :id", Course.class);
		query.setParameter("id", 10000L);
		List resultList= query.getResultList();
		logger.info("SELECT * FROM COURSE where id= :id->{}", resultList);
	}

}
