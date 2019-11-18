package com.example.restdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.restdemo.repository.StudentRepository;

@Controller
public class StudentRetrieveFilterController {

	@GetMapping("/student/filter/{registrationNumber}")
	@ResponseBody
	public String getStudentSelectedDetails(@PathVariable("registrationNumber") String registrationNumber,
			@RequestParam("select") String requiredFields) {
		return StudentRepository.getInstance().getStudentSelectedDetails(registrationNumber, requiredFields);
	}
}
