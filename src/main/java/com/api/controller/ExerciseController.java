package com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.CommonClass;
import com.api.entity.Exercise;
import com.api.model.CustomUserDetail;
import com.api.model.Result;
import com.api.model.ResultList;
import com.api.security.jwt.JwtTokenProvider;
import com.api.security.payload.LoginRequest;
import com.api.security.payload.LoginResponse;
import com.api.service.ExerciseService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/v1")
public class ExerciseController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	private ExerciseService exerciseService;
	
	@Autowired
	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}
	
	@PostMapping(name= "/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Authentication with username and password
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		// If ok then
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		//create jwt
		String jwt = tokenProvider.generateToken((CustomUserDetail) auth.getPrincipal());
		
		//create response and put jwt 
		return new LoginResponse(jwt);
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
