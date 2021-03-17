package com.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.api.entity.User;

@Repository
@Transactional
public class UserRepository {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public User findByUsername (String username) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String sql = "Select * from User u where u.username = :uName";
		Query query = entityManager.createQuery(sql);
		query.setParameter("uName", username);
		
		User user = (User) query.getSingleResult();
		return user;
		
	}
	
	public User findByUserId (Long userId) {
	
EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String sql = "Select * from User u where u.userId = :uId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("uId", userId);
		
		User user = (User) query.getSingleResult();
		return user;
	}
	
}
