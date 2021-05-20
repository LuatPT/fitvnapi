package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RstGetCaloMapDto {

	private String userName;
	private Long totalCalo;
	private String mealPlanDate;
}
