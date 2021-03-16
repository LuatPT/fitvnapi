package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.CommonClass;
import com.api.entity.Exercise;
import com.api.model.Result;
import com.api.model.ResultList;
import com.api.service.ExerciseService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/v1")
public class ExerciseController {

	private ExerciseService exerciseService;
	
	@Autowired
	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercises")
	public ResultList index () {
		return CommonClass.createResultList(exerciseService.getExerciseList());
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/exercises/{exercise_id}")
	public @ResponseBody Result getById (@PathVariable("exercise_id") Integer exerciseId) {
		Exercise exercise = exerciseService.findExerciseById(exerciseId);
		return CommonClass.createResult(exercise);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/exercises/{exercise_id}")
	public @ResponseBody void updateExerciseMethod (@PathVariable("exercise_id") Integer exerciseId, @RequestBody Exercise exercise) {
		exerciseService.updateExercise(exercise);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/exercises")
	public @ResponseBody void addExercise ( @RequestBody Exercise exercise) {
		exerciseService.inserExercise(exercise);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/exercises/{exercise_id}")
	public @ResponseBody void deleteExercise (@PathVariable("exercise_id") Integer exerciseId) {
		exerciseService.deleteExercise(exerciseId);
	}
}
