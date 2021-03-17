package com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="user_id", unique = true)
	private Long userId;
	
	@Column(name ="username", unique = true)
	private String username;
	
	@Column(name="password",unique = true)
	private String password;
	
	
}
