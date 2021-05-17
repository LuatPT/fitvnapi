package com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="mealplan")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MealPlan {
	@Id
	@Column(name="mealplan_id")
	private int mealPlanId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="food_id")
	private int foodId;
	
	@Column(name="amount")
	private String amount;
}
