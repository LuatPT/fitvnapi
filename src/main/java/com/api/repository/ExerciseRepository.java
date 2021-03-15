package com.api.repository;

import java.io.File;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.entity.Exercise;
import com.api.entity.Food;

@Repository
@Transactional
public class ExerciseRepository {
	//	private EntityManager entityManager;
	// hibernate tạo ra sessionFactory custom và thêm 1 số chức năng cho entityManager của JPA
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	//Nếu dùng sessionFactory thì phải tạo file hibernate.cfg.xml
	
	public List<Exercise> getFoodListFromDB () {
		Session session = sessionFactory.openSession();
		//HQL phải trùng tên với Entity ở đây là Category, còn native thì chỉ cần trùng tên trong db
		String sql = "Select f from Food f";
		//Create query
		Query query = session.createQuery(sql);
		List<Exercise> list = query.getResultList();
		return list;
		
	}
	
	public void saveFood(Food food) {
	//		Add
	//		entityManager.persist(food);
	//		Update
	//		entityManager.merge(food);
	//		Native SQL
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			String sql = "Insert into fitvn_db.food(food_id, food_name, food_img, food_calo, food_serving, food_type, food_content) values(?, ?, ?, ?, ?, ?, ?)";
			Query query = session.createNativeQuery(sql)
					.setParameter(1, food.getFoodId())
					.setParameter(2, food.getFoodName())
					.setParameter(3, food.getFoodImg())
					.setParameter(4, food.getFoodCalo())
					.setParameter(5, food.getFoodServing())
					.setParameter(6, food.getFoodType())
					.setParameter(7, food.getFoodContent());
			query.executeUpdate();
			session.getTransaction().commit();
		}catch (RuntimeException e) {
			session.getTransaction().rollback();
		}finally {
			session.flush();
		    session.close();
		}
		
	}
	
	public void deleteFood(int foodId) {
		Session session = sessionFactory.openSession();
		try {
		session.beginTransaction();
		
		String sql = "Delete Food f where f:foodId = :fId";
		Query query = session.createQuery(sql);
		query.setParameter("fId", foodId);
		query.executeUpdate();
		
		session.getTransaction().commit();
		}catch (RuntimeException e) {
			 session.getTransaction().rollback();
		}finally {
			session.close();
			session.flush();
		}
	}
	
	public Food getFoodById(int foodId) {
		Session session = sessionFactory.openSession();
		String sql = "Select f from Food f where f.foodId = :fId";
		Query query = session.createQuery(sql);
		query.setParameter("fId", foodId);
		Food food = (Food) query.getSingleResult(); 
		return food;
	}
	
}
