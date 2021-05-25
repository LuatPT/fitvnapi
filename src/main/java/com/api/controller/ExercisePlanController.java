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
import com.api.entity.ExercisePlan;
import com.api.model.Result;
import com.api.model.ResultList;
import com.api.service.ExercisePlanService;

@CrossOrigin(origins = "https://fitvn.herokuapp.com")
@RestController
@RequestMapping(value="/v1")
public class ExercisePlanController {
	private ExercisePlanService exercisePlanService;
	
	@Autowired
	public ExercisePlanController(ExercisePlanService exercisePlanService) {
		this.exercisePlanService = exercisePlanService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercisePlans")
	public ResultList index () {
		return CommonClass.createResultList(exercisePlanService.getExercisePlanList());
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/exercisePlans/{exercisePlan_id}")
	public @ResponseBody Result getById (@PathVariable("exercisePlan_id") Integer exercisePlanId) {
		ExercisePlan exercisePlan = exercisePlanService.findExercisePlanById(exercisePlanId);
		return CommonClass.createResult(exercisePlan);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/exercisePlans/{exercisePlan_id}")
	public @ResponseBody void updateExercisePlanMethod (@PathVariable("exercisePlan_id") Integer exercisePlanId, @RequestBody ExercisePlan exercisePlan) {
		exercisePlanService.updateExercisePlan(exercisePlan);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/exercisePlans")
	public @ResponseBody void addExercisePlan ( @RequestBody ExercisePlan exercisePlan) {
		exercisePlanService.inserExercisePlan(exercisePlan);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/exercisePlans/{exercisePlan_id}")
	public @ResponseBody void deleteExercisePlan (@PathVariable("exercisePlan_id") Integer exercisePlanId) {
		exercisePlanService.deleteExercisePlan(exercisePlanId);
	}
}
