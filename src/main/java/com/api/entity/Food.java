package com.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="food")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Food implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="food_id")
	private int foodId;
	
	@Column(name="food_name")
	private String foodName;
	
	@Column(name="food_img")
	private String foodImg;
	
	@Column(name="food_calo")
	private int foodCalo;
	
	@Column(name="food_serving")
	private int foodServing;
	
	@Column(name="food_type")
	private String foodType;
	
	@Column(name="food_content")
	private String foodContent;
	
	@Column(name="protein")
	private int protein;
	
	@Column(name="carb")
	private int carb;
	
	@Column(name="fat")
	private int fat;
}
