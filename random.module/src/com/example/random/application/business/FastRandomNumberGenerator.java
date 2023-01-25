package com.example.random.application.business;

import java.util.concurrent.ThreadLocalRandom;

import com.example.random.application.RandomNumberGenerator;

public class FastRandomNumberGenerator implements RandomNumberGenerator {

	@Override
	public int generate(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min,max);
	}

}
