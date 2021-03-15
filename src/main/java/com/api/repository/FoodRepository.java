package com.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.common.CommonClass.Action;
import com.api.entity.Food;

@Repository
@Transactional
public class FoodRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	// hibernate tạo ra sessionFactory custom và thêm 1 số chức năng cho entityManager của JPA
	//	private SessionFactory sessionFactory;
	
	public List<Food> getFoodListFromDB () {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		//HQL phải trùng tên với Entity ở đây là Category, còn native thì chỉ cần trùng tên trong db
		String sql = "Select f from Food f";
		//Create query
		Query query = entityManager.createQuery(sql);
		List<Food> list = query.getResultList();
		return list;
		
	}
	
	public void saveOrUpdateFood(Food food, Action e) {
	//		Add
	//		entityManager.persist(food);
	//		Update
	//		entityManager.merge(food);
	//		Native SQL
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			String sql = "Insert into food(food_id, food_name, food_img, food_calo, food_serving, food_type, food_content) values(?, ?, ?, ?, ?, ?, ?)";
			if(e == Action.ADD) {
				Query query = entityManager.createNativeQuery(sql)
						.setParameter(1, food.getFoodId())
						.setParameter(2, food.getFoodName())
						.setParameter(3, food.getFoodImg())
						.setParameter(4, food.getFoodCalo())
						.setParameter(5, food.getFoodServing())
						.setParameter(6, food.getFoodType())
						.setParameter(7, food.getFoodContent());
				query.executeUpdate();
				tx.commit();
			}else {
				entityManager.merge(food);
				tx.commit();
			}
		}catch (RuntimeException e1) {
			tx.rollback();
		}finally {
			entityManager.close();
		}
		
	}
	
	public void deleteFood(int foodId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
		transaction.begin();
		
		String sql = "Delete Food f where f.foodId = :fId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("fId", foodId);
		query.executeUpdate();
		
		transaction.commit();
		}catch (RuntimeException e) {
			 if ( transaction != null && transaction.isActive() ) transaction.rollback();
			    throw e; 
		}finally {
			entityManager.close();
		}
	}
	
	public Food getFoodById(int foodId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "Select f from Food f where f.foodId = :fId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("fId", foodId);
//		String sql = "Select * from food where food_id = "+ foodId;
//		Query query = entityManager.createNativeQuery(sql);
//		query.setParameter(1, foodId);
		Food food = (Food) query.getSingleResult(); 
		return food;
	}
	
}
