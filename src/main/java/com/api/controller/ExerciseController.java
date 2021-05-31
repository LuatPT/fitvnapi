package com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.Exercise;
import com.api.service.ExerciseService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value = "/v1")
public class ExerciseController {

	private ExerciseService exerciseService;

	@Autowired
	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercises", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Exercise>> index() {
		List<Exercise> listExercise = exerciseService.getExerciseList();
		if (listExercise.isEmpty()) {
			return new ResponseEntity<List<Exercise>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Exercise>>(listExercise, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercises/{exercise_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Exercise> getById(@PathVariable("exercise_id") Integer exerciseId) {
		Exercise exercise = exerciseService.findExerciseById(exerciseId);
		if (exercise == null) {
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/exercises/{exercise_id}")
	public ResponseEntity<Exercise> updateExerciseMethod(@PathVariable("exercise_id") Integer exerciseId,
			@RequestBody Exercise exercise) {
		Exercise currentExercise = exerciseService.findExerciseById(exerciseId);
		if (currentExercise == null) {
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
		}
		exerciseService.updateExercise(exercise);
		return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/exercises")
	public ResponseEntity<Void> addExercise(@RequestBody Exercise exercise) {
		exerciseService.inserExercise(exercise);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/exercises/{exercise_id}")
	public ResponseEntity<Exercise> deleteExercise(@PathVariable("exercise_id") Integer exerciseId) {
		Exercise exercise = exerciseService.findExerciseById(exerciseId);
		if (exercise == null) {
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
		}
		exerciseService.deleteExercise(exerciseId);
		return new ResponseEntity<Exercise>(HttpStatus.NO_CONTENT);
	}
}
