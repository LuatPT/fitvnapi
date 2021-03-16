package com.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass;
import com.api.entity.Exercise;
import com.api.repository.ExerciseRepository;
import com.api.service.ExerciseService;;

@Service
public class ExerciseServiceImpl implements ExerciseService{

	private ExerciseRepository exerciseRepository;
	
	@Autowired
	public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
		super();
		this.exerciseRepository = exerciseRepository;
	}

	public List<Exercise> getExerciseList() {
		
		return exerciseRepository.getExerciseListFromDB();
	}

	public void inserExercise(Exercise exercise) {
		exerciseRepository.saveOrUpdateExercise(exercise,CommonClass.Action.ADD);
		
	}

	public void deleteExercise(int exerciseId) {
		exerciseRepository.deleteExercise(exerciseId);
		
	}

	public void updateExercise(Exercise exercise) {
		exerciseRepository.saveOrUpdateExercise(exercise, CommonClass.Action.UPDATE);
	}

	public Exercise findExerciseById(int exerciseId) {
		return exerciseRepository.getExerciseById(exerciseId);
		
	}
	
}
