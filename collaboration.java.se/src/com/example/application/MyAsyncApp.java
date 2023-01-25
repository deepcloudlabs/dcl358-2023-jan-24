package com.example.application;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.service.BusinessService;

public class MyAsyncApp {

	public static void main(String[] args) {
		System.err.println("Application is just started.");
		var businessService = new BusinessService();
		businessService.gun() // asynchronous: i. non-blocking -> thread, ii. observer/event
		               .thenAcceptAsync(numbers -> {
		            	   var result = numbers.stream()
		            	   .map(Object::toString)
		            	   .collect(Collectors.joining(",","[","]"));
		            	   System.err.println("[%s] %s".formatted(Thread.currentThread().getName(),result)); 
		               });
		System.err.println("[%s] Application is done."
				.formatted(Thread.currentThread().getName()));
		try {TimeUnit.SECONDS.sleep(10);}catch (Exception e) {}
	}

}
