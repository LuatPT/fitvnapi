package com.api.repository;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.api.common.CommonClass.Action;
import com.api.entity.Exercise;

@Repository
@Transactional
public class ExerciseRepository {
	//	private EntityManager entityManager;
	// hibernate tạo ra sessionFactory custom và thêm 1 số chức năng cho entityManager của JPA
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	//Nếu dùng sessionFactory thì phải tạo file hibernate.cfg.xml
	
	public List<Exercise> getExerciseListFromDB () {
		Session session = sessionFactory.openSession();
		//HQL phải trùng tên với Entity ở đây là Exercise, còn native thì chỉ cần trùng tên trong db
		String sql = "Select f from Exercise f";
		//Create query
		Query query = session.createQuery(sql);
		List<Exercise> list = query.getResultList();
		return list;
	}
	
	public void saveOrUpdateExercise(Exercise exercise, Action e) {
	//		Add
	//		entityManager.persist(exercise);
	//		Update
	//		entityManager.merge(exercise);
	//		Native SQL
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			String sql = "Insert into exercise(exercise_id, exercise_name, exercise_img, exercise_set, exercise_rep, exercise_type, exercise_content) values(?, ?, ?, ?, ?, ?, ?)";
			if(e == Action.ADD) {
			Query query = session.createNativeQuery(sql)
					.setParameter(1, exercise.getExerciseId())
					.setParameter(2, exercise.getExerciseName())
					.setParameter(3, exercise.getExerciseImg())
					.setParameter(4, exercise.getExerciseSet())
					.setParameter(5, exercise.getExerciseRep())
					.setParameter(6, exercise.getExerciseType())
					.setParameter(7, exercise.getExerciseContent());
			query.executeUpdate();
			session.getTransaction().commit();
			}else {
				session.merge(exercise);
				session.getTransaction().commit();
			}
		}catch (RuntimeException e1) {
			session.getTransaction().rollback();
		}finally {
		    session.close();
		}
		
	}
	
	public void deleteExercise(int exerciseId) {
		Session session = sessionFactory.openSession();
		try {
		session.beginTransaction();
		String sql = "Delete Exercise e where e.exerciseId = :eId";
		Query query = session.createQuery(sql);
		query.setParameter("eId", exerciseId);
		query.executeUpdate();
		
		session.getTransaction().commit();
		}catch (RuntimeException e1) {
			 session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}
	
	public Exercise getExerciseById(int exerciseId) {
		Session session = sessionFactory.openSession();
		String sql = "Select e from Exercise e where e.exerciseId = :eId";
		Query query = session.createQuery(sql);
		query.setParameter("eId", exerciseId);
		Exercise exercise = (Exercise) query.getSingleResult(); 
		return exercise;
	}
	
}
