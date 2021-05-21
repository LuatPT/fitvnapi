package com.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.common.CommonClass.Action;
import com.api.entity.User;

@Repository
@Transactional
public class UserRepository {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public User findByUsername (String username) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String sql = "Select u from User u where u.username = :uName";
		Query query = entityManager.createQuery(sql);
		query.setParameter("uName", username);
		
		User user = (User) query.getSingleResult();
		return user;
		
	}
	
	public User findByUserId (Long userId) {
	
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String sql = "Select u from User u where u.userId = :uId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("uId", userId);
		
		User user =  (User) query.getSingleResult();
		return user;
	}
	public void saveOrUpdateFood(User user, Action e) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			String sql = "Insert into users(user_id, username, password, role) values(?, ?, ?, ?)";
			if(e == Action.ADD) {
				Query query = entityManager.createNativeQuery(sql)
						.setParameter(1, user.getUserId())
						.setParameter(2, user.getUsername())
						.setParameter(3, user.getPassword())
						.setParameter(4, user.getRole());
				query.executeUpdate();
				tx.commit();
			}else {
				entityManager.merge(user);
				tx.commit();
			}
		}catch (RuntimeException e1) {
			tx.rollback();
		}finally {
			entityManager.close();
		}
		
	}
	
}
