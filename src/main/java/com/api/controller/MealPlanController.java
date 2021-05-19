package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.CommonClass;
import com.api.entity.MealPlan;
import com.api.model.DxoGetMealDto;
import com.api.model.Result;
import com.api.model.ResultList;
import com.api.service.MealPlanService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/v1")
public class MealPlanController {
	private MealPlanService mealPlanService;

	@Autowired
	public MealPlanController(MealPlanService mealPlanService) {
		this.mealPlanService = mealPlanService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getMealPlans")
	public ResultList index(@RequestBody DxoGetMealDto requestGetMeal) {
		return CommonClass.createResultList(
				mealPlanService.getMealPlanList(requestGetMeal.getUserName(), requestGetMeal.getMealPlanDate()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/mealPlans/{mealPlan_id}")
	public @ResponseBody Result getById(@PathVariable("mealPlan_id") Integer mealPlanId) {
		MealPlan mealPlan = mealPlanService.findMealPlanById(mealPlanId);
		return CommonClass.createResult(mealPlan);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/mealPlans/{mealPlan_id}")
	public @ResponseBody void updateMealPlanMethod(@PathVariable("mealPlan_id") Integer mealPlanId,
			@RequestBody MealPlan mealPlan) {
		mealPlanService.updateMealPlan(mealPlan);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/mealPlans")
	public @ResponseBody void addMealPlan(@RequestBody MealPlan mealPlan) {
		mealPlanService.inserMealPlan(mealPlan);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/mealPlans/{mealPlan_id}")
	public @ResponseBody void deleteMealPlan(@PathVariable("mealPlan_id") Integer mealPlanId) {
		mealPlanService.deleteMealPlan(mealPlanId);
	}
}
