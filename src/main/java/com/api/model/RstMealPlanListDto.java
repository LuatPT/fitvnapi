package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RstMealPlanListDto {
	private int mealPlanId;
	
	private String userId;
	
	private String foodName;
	
	private String foodCalo;
	
	private String amount;
}
