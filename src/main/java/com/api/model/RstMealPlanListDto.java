package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RstMealPlanListDto {
	
	private int mealPlanId;
	
	private String userName;
	
	private int foodId;	

	private int amount;
	
	private String foodName;
	
	private String foodImg;
	
	private int foodCalo;

	private int foodServing;
	
	private String foodType;
	
	private String foodContent;
	
	private int protein;
	
	private int carb;
	
	private int fat;
}
