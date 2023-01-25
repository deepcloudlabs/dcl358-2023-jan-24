package com.example.lottery.application;

import java.util.ServiceLoader;

import com.example.lottery.service.business.StandardLotteryService;
import com.example.random.application.RandomNumberGenerator;

public class LotteryApplication {

	public static void main(String[] args) {
		var lotteryApplication = new StandardLotteryService();
		var services = ServiceLoader.load(RandomNumberGenerator.class);
				
		var randomNumberGenerator = services.findFirst().get();
		lotteryApplication.setRandomNumberGenerator(randomNumberGenerator);
		lotteryApplication.draw(60, 6, 10)
		                  .forEach(System.out::println);
	}

}
