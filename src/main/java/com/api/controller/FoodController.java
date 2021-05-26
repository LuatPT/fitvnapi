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
import com.api.entity.Food;
import com.api.model.Result;
import com.api.model.ResultList;
import com.api.service.FoodService;

@CrossOrigin(origins = "https://fitvn.herokuapp.com")
@RestController
@RequestMapping(value="/v1")
public class FoodController {

	private FoodService foodService;
	
	@Autowired
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/foods")
	public ResultList index () {
		return CommonClass.createResultList(foodService.getFoodList());
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/foods/{food_id}")
	public @ResponseBody Result getById (@PathVariable("food_id") Integer foodId) {
		Food food = foodService.findFoodById(foodId);
		return CommonClass.createResult(food);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/foods/{food_id}")
	public @ResponseBody void updateFoodMethod (@PathVariable("food_id") Integer foodId, @RequestBody Food food) {
		foodService.updateFood(food);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/foods")
	public @ResponseBody void addFood ( @RequestBody Food food) {
		foodService.inserFood(food);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/foods/{food_id}")
	public @ResponseBody void deleteFood (@PathVariable("food_id") Integer foodId) {
		foodService.deleteFood(foodId);
	}
}
