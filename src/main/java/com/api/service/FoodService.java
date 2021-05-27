package com.api.service;

import java.util.List;

import com.api.entity.Food;


public interface FoodService {
	public List<Food> getFoodList () ;
	public void inserFood(Food food);
	public void deleteFood (int foodId) ;
	public void updateFood (Food food) ;
	public Food findFoodById(int foodId);
}
