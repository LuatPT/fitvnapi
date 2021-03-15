package com.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass;
import com.api.entity.Food;
import com.api.repository.FoodRepository;
import com.api.service.FoodService;;

@Service
public class FoodServiceImpl implements FoodService{

	private FoodRepository foodRepository;
	
	@Autowired
	public FoodServiceImpl(FoodRepository foodRepository) {
		super();
		this.foodRepository = foodRepository;
	}

	public List<Food> getFoodList() {
		
		return foodRepository.getFoodListFromDB();
	}

	public void inserFood(Food food) {
		foodRepository.saveOrUpdateFood(food,CommonClass.Action.ADD);
		
	}

	public void deleteFood(int foodId) {
		foodRepository.deleteFood(foodId);
		
	}

	public void updateFood(Food food) {
		foodRepository.saveOrUpdateFood(food, CommonClass.Action.UPDATE);
	}

	public Food findFoodById(int foodId) {
		return foodRepository.getFoodById(foodId);
		
	}
	
}
