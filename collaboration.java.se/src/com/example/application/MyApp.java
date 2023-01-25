package com.example.application;

import java.util.stream.Collectors;

import com.example.service.BusinessService;

public class MyApp {

	public static void main(String[] args) {
		System.out.println("Application is just started.");
		var businessService = new BusinessService();
		var numbers = 
		businessService.fun()
		               .stream()
		               .map(Object::toString)
		               .collect(Collectors.joining(",","[","]"));
		System.out.println(numbers);
		System.out.println("Application is done.");
	}

}
