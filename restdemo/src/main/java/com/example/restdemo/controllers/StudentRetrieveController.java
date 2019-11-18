package com.example.restdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.restdemo.repository.StudentRepository;

@Controller
public class StudentRetrieveController {
	@RequestMapping(method = RequestMethod.GET, value = "/student/allstudent")
	@ResponseBody
	public String getAllStudents() {
		return StudentRepository.getInstance().getAllStudentRecords();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/student/{registrationNo}")
	@ResponseBody
	public String getStudent(@PathVariable("registrationNo") String registrationNumber) {
		return StudentRepository.getInstance().getStudentRecords(registrationNumber);
	}
}
