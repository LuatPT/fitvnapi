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
	@Column(name="info_id", unique = true)
	private int infoId;
	
	@Column(name="user_name", unique = true)
	private String userName;
	
	@Column(name="tdee", unique = true)
	private int tdee;
	
	@Column(name="nutrition_type", unique = true)
	private int nutritionType;
}
