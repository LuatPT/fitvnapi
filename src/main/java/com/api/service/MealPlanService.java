package com.api.service;

import java.util.List;

import com.api.entity.MealPlan;
import com.api.model.RstMealPlanListDto;

public interface MealPlanService {
	public List <RstMealPlanListDto> getMealPlanList (String userName) ;
	public void inserMealPlan(MealPlan meal);
	public void deleteMealPlan (int mealPlanId) ;
	public void updateMealPlan (MealPlan meal) ;
	public MealPlan findMealPlanById(int mealPlanId);
}
