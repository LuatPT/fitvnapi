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
@Table(name="exercise")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="exercise_id")
	private int exerciseId;
	
	@Column(name="exercise_name")
	private String exerciseName;
	
	@Column(name="exercise_img")
	private String exerciseImg;
	
	@Column(name="exercise_set")
	private String exerciseSet;
	
	@Column(name="exercise_rep")
	private String exerciseRep;
	
	@Column(name="exercise_type")
	private String exerciseType;
	
	@Column(name="exercise_content")
	private String exerciseContent;
	
}
