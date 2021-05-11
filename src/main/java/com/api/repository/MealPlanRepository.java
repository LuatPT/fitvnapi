package com.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.common.CommonClass.Action;
import com.api.entity.MealPlan;
import com.api.model.RstMealPlanListDto;

@Repository
@Transactional
public class MealPlanRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	public List<RstMealPlanListDto> getMealPlanListFromDB () {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "Select m.mealPlanId, m.userId, f.foodName, f.foodCalo, m.amount from MealPlan m inner join Food f on m.foodId = f.foodId";
		//Create query 
		Query query = entityManager.createQuery(sql);
		List<RstMealPlanListDto> list = query.getResultList();
		return list;
	}
	
	public void saveOrUpdateMealPlan(MealPlan meal, Action e) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			String sql = "Insert into mealplan(mealplan_id, user_id, food_id, amount) values(?, ?, ?, ?)";
			//Check update or add
			if (e == Action.ADD) {
				Query  query = entityManager.createNativeQuery(sql)
						.setParameter(1, meal.getMealPlanId())
						.setParameter(2, meal.getUserId())
						.setParameter(3, meal.getFoodId())
						.setParameter(4, meal.getAmount());
				query.executeUpdate();
				tx.commit();
			}else {
				entityManager.merge(meal);
				tx.commit();
			}
			
		} catch (RuntimeException e2) {
			tx.rollback();
		}finally {
			entityManager.close();
		}
	}
	
	public void deleteMealPlan(int mealPlanId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			
			String sql = "Delete MealPlan m where m.mealPlanId = :mId";
			Query query = entityManager.createQuery(sql);
			query.setParameter("mId", mealPlanId);
			query.executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; 
		}finally {
			entityManager.close();
		}
	}
	
	public MealPlan getMealPlanById(int mealPlanId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "Select m from MealPlan m where m.mealPlanId = :mId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("mId", mealPlanId);
		MealPlan mealPlan = (MealPlan) query.getSingleResult(); 
		return mealPlan;
	}
}
