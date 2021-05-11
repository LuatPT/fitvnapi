package com.api.service;

import java.util.List;

import com.api.entity.ExercisePlan;

public interface ExercisePlanService {
	public List <ExercisePlan> getExercisePlanList () ;
	public void inserExercisePlan(ExercisePlan exercise);
	public void deleteExercisePlan (int exercisePlanId) ;
	public void updateExercisePlan (ExercisePlan exercise) ;
	public ExercisePlan findExercisePlanById(int exercisePlanId);
}
