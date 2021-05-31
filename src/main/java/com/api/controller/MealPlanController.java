package com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.MealPlan;
import com.api.model.DxoGetCaloMapDto;
import com.api.model.DxoGetMealDto;
import com.api.model.RstGetCaloMapDto;
import com.api.model.RstMealPlanListDto;
import com.api.service.MealPlanService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value = "/v1")
public class MealPlanController {
	private MealPlanService mealPlanService;

	@Autowired
	public MealPlanController(MealPlanService mealPlanService) {
		this.mealPlanService = mealPlanService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getMealPlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RstMealPlanListDto>> index(@RequestBody DxoGetMealDto requestGetMeal) {
		List<RstMealPlanListDto> listMealPlan = mealPlanService.getMealPlanList(requestGetMeal.getUserName(),
				requestGetMeal.getMealPlanDate());
		if (listMealPlan.isEmpty()) {
			return new ResponseEntity<List<RstMealPlanListDto>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<RstMealPlanListDto>>(listMealPlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getCaloMap", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RstGetCaloMapDto>> getCaloMap(@RequestBody DxoGetCaloMapDto dxoGetCaloMapDto) {
		List<RstGetCaloMapDto> listCaloMap = mealPlanService.getCaloMap(dxoGetCaloMapDto.getUserName());
		if (listCaloMap.isEmpty()) {
			return new ResponseEntity<List<RstGetCaloMapDto>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<RstGetCaloMapDto>>(listCaloMap, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/mealPlans/{mealPlan_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MealPlan> getById(@PathVariable("mealPlan_id") Integer mealPlanId) {
		MealPlan mealPlan = mealPlanService.findMealPlanById(mealPlanId);
		if (mealPlan == null) {
			return new ResponseEntity<MealPlan>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MealPlan>(mealPlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/mealPlans/{mealPlan_id}")
	public ResponseEntity<MealPlan> updateMealPlanMethod(@PathVariable("mealPlan_id") Integer mealPlanId,
			@RequestBody MealPlan mealPlan) {
		MealPlan currentMealPlan = mealPlanService.findMealPlanById(mealPlanId);
		if (currentMealPlan == null) {
			return new ResponseEntity<MealPlan>(HttpStatus.NOT_FOUND);
		}
		mealPlanService.updateMealPlan(mealPlan);
		return new ResponseEntity<MealPlan>(mealPlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/mealPlans")
	public ResponseEntity<Void> addMealPlan(@RequestBody MealPlan mealPlan) {
		mealPlanService.inserMealPlan(mealPlan);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/mealPlans/{mealPlan_id}")
	public ResponseEntity<MealPlan> deleteMealPlan(@PathVariable("mealPlan_id") Integer mealPlanId) {
		MealPlan currentMealPlan = mealPlanService.findMealPlanById(mealPlanId);
		if (currentMealPlan == null) {
			return new ResponseEntity<MealPlan>(HttpStatus.NOT_FOUND);
		}
		mealPlanService.deleteMealPlan(mealPlanId);
		return new ResponseEntity<MealPlan>(HttpStatus.NO_CONTENT);
	}
}
