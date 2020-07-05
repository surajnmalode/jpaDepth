package com.in28minutes.jpa.hibernate.demo.repository;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Passport;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;
	
	public Student findById(Long id) {
		return em.find(Student.class, id);
	}
	
	public Student save(Student student) {
		if (student.getId() == null) {
			em.persist(student);
		} else {
			em.merge(student);
		}
		return student;
	}
	
	public void deleteById(Long id) {
		Student student = findById(id);
		em.remove(student);
	}
	
	public void saveStudentWithPassport() {
		Passport passport = new Passport("Z123456");
		em.persist(passport);
		
		Student student = new Student("Mike");
		student.setPassport(passport);
		em.persist(student);
		
		
	}
	
	public void someOperationToUnderstandPersistanceContext() {
		// Database Operation1 - Retreive Student
		Student student = em.find(Student.class, 20001L);
		
		// Database Operation2 - Retreive passport
		Passport passport = student.getPassport();
		
		// Database Operation3 - update passport
		passport.setNumber("E123457");
		
		// Database Operation4 - update student
		student.setName("Ranga - Updated");
		
	}
	
	public void insertHardcodedStudentAndCourse() {
		Student student = new Student("Jack");
		Course course = new Course("Micorservices in 100 steps");
		em.persist(student);
		em.persist(course);
		
		student.addCourses(course);
		course.addStudent(student);
		em.persist(student);
	}

	public void insertStudentAndCourse(Student student, Course course) {
//		Student student = new Student("Jack");
//		Course course = new Course("Micorservices in 100 steps");
		
		student.addCourses(course);
		course.addStudent(student);
		em.persist(student);
		em.persist(course);

	}

}
