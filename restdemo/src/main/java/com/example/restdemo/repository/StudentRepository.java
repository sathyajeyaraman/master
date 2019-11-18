package com.example.restdemo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.example.restdemo.beans.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class StudentRepository {
	private List<Student> studentRecords;
	private static StudentRepository stdregd = null;

	private StudentRepository() {
		studentRecords = new ArrayList<Student>();
	}

	public static StudentRepository getInstance() {
		if (stdregd == null) {
			stdregd = new StudentRepository();
			return stdregd;
		} else {
			return stdregd;
		}
	}

	public void add(Student std) {
		studentRecords.add(std);
	}

	public String upDateStudent(Student std) {
		for (int i = 0; i < studentRecords.size(); i++) {
			Student stdn = studentRecords.get(i);
			if (stdn.getRegistrationNumber().equals(std.getRegistrationNumber())) {
				studentRecords.set(i, std);// update the new record
				return "Update successful";
			}
		}
		return "Update un-successful";
	}

	public String deleteStudent(String registrationNumber) {
		for (int i = 0; i < studentRecords.size(); i++) {
			Student stdn = studentRecords.get(i);
			if (stdn.getRegistrationNumber().equals(registrationNumber)) {
				studentRecords.remove(i);// update the new record
				return "Delete successful";
			}
		}
		return "Delete un-successful";
	}

	public String getAllStudentRecords() {
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("myFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mapper.setFilterProvider(filterProvider);
		try {
			String json = mapper.writeValueAsString(studentRecords);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getStudentRecords(String registrationNumber) {
		Student matchingObject = studentRecords.stream()
				.filter(p -> p.getRegistrationNumber().equals(registrationNumber)).findAny().orElse(null);

		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("myFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mapper.setFilterProvider(filterProvider);
		try {
			String json = mapper.writeValueAsString(matchingObject);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	public String getStudentSelectedDetails(String registrationNumber, String requiredFields) {
		Student matchingObject = studentRecords.stream()
				.filter(p -> p.getRegistrationNumber().equals(registrationNumber)).findAny().orElse(null);
		StringTokenizer st = new StringTokenizer(requiredFields, ",");
		Set<String> filterProperties = new HashSet<String>();
		while (st.hasMoreTokens()) {
			filterProperties.add(st.nextToken());
		}
		System.out.println(filterProperties);
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",
				SimpleBeanPropertyFilter.filterOutAllExcept(filterProperties));
		mapper.setFilterProvider(filters);
		try {
			String json = mapper.writeValueAsString(matchingObject);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
