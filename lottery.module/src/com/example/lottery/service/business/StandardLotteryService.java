package com.example.lottery.service.business;

import java.util.List;
import java.util.stream.IntStream;

import com.example.lottery.service.LotteryService;
import com.example.random.application.RandomNumberGenerator;

public class StandardLotteryService implements LotteryService {
	private RandomNumberGenerator randomNumberGenerator;
	
	public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	@Override
	public List<Integer> draw(int max, int size) {
		return IntStream.generate(() -> randomNumberGenerator.generate(max))
				        .distinct()
				        .limit(size)
				        .sorted()
				        .boxed()
				        .toList();
	}

}
