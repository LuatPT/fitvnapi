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
@Table(name="exerciseplan")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExercisePlan {
	@Id
	@Column(name="exerciseplan_id")
	private int exercisePlanId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="exercise_id")
	private String exerciseId;
	
	@Column(name="amount")
	private String amount;
}
