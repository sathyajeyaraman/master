package com.example.restdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.restdemo.beans.Student;
import com.example.restdemo.beans.StudentRegistrationReply;
import com.example.restdemo.repository.StudentRepository;

@Controller
public class StudentRegistrationController {
	@RequestMapping(method = RequestMethod.POST, value = "/register/student")
	@ResponseBody
	public StudentRegistrationReply registerStudent(@RequestBody Student student) {
		StudentRegistrationReply stdregreply = new StudentRegistrationReply();
		StudentRepository.getInstance().add(student);
		// We are setting the below value just to reply a message back to the caller
		stdregreply.setName(student.getName());
		stdregreply.setAge(student.getAge());
		stdregreply.setRegistrationNumber(student.getRegistrationNumber());
		stdregreply.setRegistrationStatus("Successful");
		return stdregreply;
	}
}
