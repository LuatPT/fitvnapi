package com.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.common.CommonClass.Action;
import com.api.entity.UserInfo;

@Repository
@Transactional
public class UserInfoRepository {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public UserInfo getUserInfoFromDB(String userName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "SELECT u FROM UserInfo u WHERE u.userName = :uName";
		//Create query 
		Query query = entityManager.createQuery(sql);
		query.setParameter("uName", userName);
		UserInfo userInfo = (UserInfo) query.getSingleResult();
		return userInfo;
	}

	public UserInfo getUserInfoById(int infoId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "SELECT u FROM UserInfo u WHERE u.infoId = :uId";
		//Create query 
		Query query = entityManager.createQuery(sql);
		query.setParameter("uId", infoId);
		UserInfo userInfo = (UserInfo) query.getSingleResult();
		return userInfo;
	}

	public void saveOrUpdateUserInfo(UserInfo userInfo, Action e) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			//Check current max id 
			int id = getMaxId() + 1;
			userInfo.setInfoId(id);
			//Check update or add
			if (e == Action.ADD) {
				entityManager.persist(userInfo);
				tx.commit();
			}else {
				entityManager.merge(userInfo);
				tx.commit();
			}
			
		} catch (RuntimeException e2) {
			tx.rollback();
		}finally {
			entityManager.close();
		}
	}

	public void deleteUserInfo(int infoId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			
			String sql = "DELETE UserInfo u where u.infoId = :uId";
			Query query = entityManager.createQuery(sql);
			query.setParameter("uId", infoId);
			query.executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; 
		}finally {
			entityManager.close();
		}
	}

	public int getMaxId() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "SELECT MAX(u.infoId) FROM UserInfo u";
		Query query = entityManager.createQuery(sql);
		int maxId =  (int) query.getSingleResult(); 
		return maxId;
	}

}
