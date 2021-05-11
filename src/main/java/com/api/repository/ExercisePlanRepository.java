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
import com.api.entity.ExercisePlan;

@Repository
@Transactional
public class ExercisePlanRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	public List<ExercisePlan> getExercisePlanListFromDB () {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "Select e from ExercisePlan e";
		//Create query 
		Query query = entityManager.createQuery(sql);
		List<ExercisePlan> list = query.getResultList();
		return list;
	}
	
	public void saveOrUpdateExercisePlan(ExercisePlan exercise, Action e) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			String sql = "Insert into exerciseplan(exerciseplan_id, user_id, exercise_id, amount) values(?, ?, ?, ?)";
			//Check update or add
			if (e == Action.ADD) {
				Query query = entityManager.createNativeQuery(sql)
						.setParameter(1, exercise.getExercisePlanId())
						.setParameter(2, exercise.getUserId())
						.setParameter(3, exercise.getExerciseId())
						.setParameter(4, exercise.getAmount());
				query.executeUpdate();
				tx.commit();
			}else {
				entityManager.merge(exercise);
				tx.commit();
			}
			
		} catch (RuntimeException e2) {
			tx.rollback();
		}finally {
			entityManager.close();
		}
	}
	
	public void deleteExercisePlan(int exercisePlanId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			
			String sql = "Delete ExercisePlan e where e.exercisePlanId = :eId";
			Query query = entityManager.createQuery(sql);
			query.setParameter("eId", exercisePlanId);
			query.executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; 
		}finally {
			entityManager.close();
		}
	}
	
	public ExercisePlan getExercisePlanById(int exercisePlanId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "Select e from ExercisePlan e where e.exercisePlanId = :eId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("eId", exercisePlanId);
		ExercisePlan exercisePlan = (ExercisePlan) query.getSingleResult(); 
		return exercisePlan;
	}
}
