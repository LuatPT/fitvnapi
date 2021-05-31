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

import com.api.entity.ExercisePlan;
import com.api.service.ExercisePlanService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value = "/v1")
public class ExercisePlanController {
	private ExercisePlanService exercisePlanService;

	@Autowired
	public ExercisePlanController(ExercisePlanService exercisePlanService) {
		this.exercisePlanService = exercisePlanService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercisePlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExercisePlan>> index() {
		List<ExercisePlan> listExPlan = exercisePlanService.getExercisePlanList();
		if (listExPlan.isEmpty()) {
			return new ResponseEntity<List<ExercisePlan>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ExercisePlan>>(listExPlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exercisePlans/{exercisePlan_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExercisePlan> getById(@PathVariable("exercisePlan_id") Integer exercisePlanId) {

		ExercisePlan exercisePlan = exercisePlanService.findExercisePlanById(exercisePlanId);
		if (exercisePlan == null) {
			return new ResponseEntity<ExercisePlan>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ExercisePlan>(exercisePlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/exercisePlans/{exercisePlan_id}")
	public ResponseEntity<ExercisePlan> updateExercisePlanMethod(
			@PathVariable("exercisePlan_id") Integer exercisePlanId, @RequestBody ExercisePlan exercisePlan) {
		ExercisePlan currentExPlan = exercisePlanService.findExercisePlanById(exercisePlanId);
		if (currentExPlan == null) {
			return new ResponseEntity<ExercisePlan>(HttpStatus.NOT_FOUND);
		}
		exercisePlanService.updateExercisePlan(exercisePlan);
		return new ResponseEntity<ExercisePlan>(exercisePlan, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/exercisePlans")
	public ResponseEntity<Void> addExercisePlan(@RequestBody ExercisePlan exercisePlan) {
		exercisePlanService.inserExercisePlan(exercisePlan);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/exercisePlans/{exercisePlan_id}")
	public ResponseEntity<ExercisePlan> deleteExercisePlan(@PathVariable("exercisePlan_id") Integer exercisePlanId) {
		ExercisePlan exercisePlan = exercisePlanService.findExercisePlanById(exercisePlanId);
		if (exercisePlan == null) {
			return new ResponseEntity<ExercisePlan>(HttpStatus.NOT_FOUND);
		}
		exercisePlanService.deleteExercisePlan(exercisePlanId);
		return new ResponseEntity<ExercisePlan>(HttpStatus.NO_CONTENT);
	}
}
