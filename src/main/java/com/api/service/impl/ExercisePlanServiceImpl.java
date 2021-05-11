package com.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass.Action;
import com.api.entity.ExercisePlan;
import com.api.repository.ExercisePlanRepository;
import com.api.service.ExercisePlanService;

@Service
public class ExercisePlanServiceImpl implements ExercisePlanService{
private ExercisePlanRepository exercisePlanRepository;
	
	@Autowired
	public ExercisePlanServiceImpl(ExercisePlanRepository exercisePlanRepository) {
		super();
		this.exercisePlanRepository = exercisePlanRepository;
	}

	@Override
	public List<ExercisePlan> getExercisePlanList() {
		return exercisePlanRepository.getExercisePlanListFromDB();
	}

	@Override
	public void inserExercisePlan(ExercisePlan meal) {
		exercisePlanRepository.saveOrUpdateExercisePlan(meal, Action.ADD);
	}

	@Override
	public void deleteExercisePlan(int exercisePlanId) {
		exercisePlanRepository.deleteExercisePlan(exercisePlanId);
	}

	@Override
	public void updateExercisePlan(ExercisePlan meal) {
		exercisePlanRepository.saveOrUpdateExercisePlan(meal, Action.UPDATE);
	}

	@Override
	public ExercisePlan findExercisePlanById(int exercisePlanId) {
		return exercisePlanRepository.getExercisePlanById(exercisePlanId);
	}
}
