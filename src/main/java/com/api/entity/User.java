package com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id", unique = true)
	private Long userId;
	
	@Column(name ="username", unique = true)
	private String username;
	
	@Column(name="password",unique = true)
	private String password;
	
	@Column(name="role")
	private int role;
}
