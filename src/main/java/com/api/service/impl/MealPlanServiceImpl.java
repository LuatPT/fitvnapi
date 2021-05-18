package com.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass.Action;
import com.api.entity.MealPlan;
import com.api.model.RstMealPlanListDto;
import com.api.repository.MealPlanRepository;
import com.api.service.MealPlanService;

@Service
public class MealPlanServiceImpl implements MealPlanService{
	private MealPlanRepository mealPlanRepository;
	
	@Autowired
	public MealPlanServiceImpl(MealPlanRepository mealPlanRepository) {
		super();
		this.mealPlanRepository = mealPlanRepository;
	}

	@Override
	public List<RstMealPlanListDto> getMealPlanList(String userName) {
		return mealPlanRepository.getMealPlanListFromDB(userName);
	}

	@Override
	public void inserMealPlan(MealPlan meal) {
		mealPlanRepository.saveOrUpdateMealPlan(meal, Action.ADD);
	}

	@Override
	public void deleteMealPlan(int mealPlanId) {
		mealPlanRepository.deleteMealPlan(mealPlanId);
	}

	@Override
	public void updateMealPlan(MealPlan meal) {
		mealPlanRepository.saveOrUpdateMealPlan(meal, Action.UPDATE);
	}

	@Override
	public MealPlan findMealPlanById(int mealPlanId) {
		return mealPlanRepository.getMealPlanById(mealPlanId);
	}

}
