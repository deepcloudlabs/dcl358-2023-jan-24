package com.example.random.application.business;

import java.security.SecureRandom;
import java.util.Random;

import com.example.random.application.RandomNumberGenerator;

public class SecureRandomNumberGenerator implements RandomNumberGenerator {

	private Random random = new SecureRandom();

	@Override
	public int generate(int min, int max) {
		return random.nextInt(min, max);
	}

}
