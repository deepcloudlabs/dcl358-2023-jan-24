package com.example.random.application;

public interface RandomNumberGenerator {
	int generate(int min, int max);

	default int generate(int max) {
		return generate(1, max);
	}
}
