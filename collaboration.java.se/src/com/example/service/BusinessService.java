package com.example.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BusinessService {
	private Random random = ThreadLocalRandom.current();

	// synchronous
	public List<Integer> fun() {
		try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
		return IntStream.generate(() -> random .nextInt(1,60))
				        .distinct()
				        .limit(6)
				        .sorted()
				        .boxed()
				        .toList();	
	}
	// asynchronous
	public CompletableFuture<List<Integer>> gun() {
		return CompletableFuture.supplyAsync(() ->{
			try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
			System.err.println("%s runs gun()."
					.formatted(Thread.currentThread().getName()));
			return IntStream.generate(() -> random .nextInt(1,60))
					.distinct()
					.limit(6)
					.sorted()
					.boxed()
					.toList();	
			
		});
	}
}
