package com.api.service;

import java.util.List;

import com.api.entity.Exercise;

public interface ExerciseService {
	public List <Exercise> getExerciseList () ;
	public void inserExercise(Exercise exercise);
	public void deleteExercise (int exerciseId) ;
	public void updateExercise (Exercise exercise) ;
	public Exercise findExerciseById(int exerciseId);
}
