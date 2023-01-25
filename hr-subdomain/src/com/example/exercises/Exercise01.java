package com.example.exercises;

import java.util.List;

@SuppressWarnings("unused")
public class Exercise01 {

	public static void main(String[] args) {
		Integer i = Integer.valueOf(42);
		Integer j = 42;
		Integer x = 549;
		Integer y = 549;
		System.out.println("i==j? " + (i == j));
		System.out.println("x==y? " + (x == y));
		int u = 42; // 4-byte
		Integer v = Integer.valueOf(42); // 12-Byte + 4-byte = 16-Byte
		List<Integer> numbers = List.of(4, 8, 15, 16, 23, 42);
	}

}
