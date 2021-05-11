package com.api.service;

import java.util.List;

import com.api.entity.MealPlan;

public interface MealPlanService {
	public List <MealPlan> getMealPlanList () ;
	public void inserMealPlan(MealPlan meal);
	public void deleteMealPlan (int mealPlanId) ;
	public void updateMealPlan (MealPlan meal) ;
	public MealPlan findMealPlanById(int mealPlanId);
}
