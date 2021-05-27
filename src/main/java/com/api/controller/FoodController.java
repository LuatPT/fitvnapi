package com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.Food;
import com.api.service.FoodService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value = "/v1")
public class FoodController {

	private FoodService foodService;

	@Autowired
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/foods")
	public ResponseEntity<List<Food>> index() {
		List<Food> listFood = foodService.getFoodList();
		if (listFood.isEmpty()) {
			return new ResponseEntity<List<Food>>(HttpStatus.NO_CONTENT);// You many decide to return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Food>>(listFood, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/foods/{food_id}")
	public ResponseEntity<Food> getById(@PathVariable("food_id") Integer foodId) {
		Food food = foodService.findFoodById(foodId);
		if (food == null) {
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/foods/{food_id}")
	public ResponseEntity<Food> updateFoodMethod(@PathVariable("food_id") Integer foodId, @RequestBody Food food) {
		Food currentFood = foodService.findFoodById(foodId);
		if (currentFood == null) {
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		foodService.updateFood(food);
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/foods")
	public ResponseEntity<Void> addFood(@RequestBody Food food) {
		foodService.inserFood(food);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/foods/{food_id}")
	public ResponseEntity<Food> deleteFood(@PathVariable("food_id") Integer foodId) {
		Food food = foodService.findFoodById(foodId);
		if (food == null) {
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		foodService.deleteFood(foodId);
		return new ResponseEntity<Food>(HttpStatus.NO_CONTENT);
	}
}
