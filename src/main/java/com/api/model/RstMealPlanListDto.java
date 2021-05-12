package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RstMealPlanListDto {
	private String mealPlanId;
	
	private String userId;
	
	private String foodId;	

	private String amount;
	
	private String foodName;
	
	private String foodImg;
	
	private String foodCalo;

	private String foodServing;
	
	private String foodType;
	
	private String foodContent;
}
