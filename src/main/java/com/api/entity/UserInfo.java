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
@Table(name="user_info")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserInfo {
	@Id
	@Column(name="info_id")
	private int infoId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="tdee")
	private int tdee;
	
	@Column(name="nutrition_type")
	private int nutritionType;
	
	@Column(name="full_name", unique = true)
	private String fullName;
	
	@Column(name="age")
	private int age;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="height")
	private int height;
	
	@Column(name="weight")
	private int weight;
	
	@Column(name="body_fat")
	private int bodyFat;
	
	@Column(name="target")
	private String target;
}
