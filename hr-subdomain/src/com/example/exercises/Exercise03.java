package com.example.exercises;

import com.example.hr.domain.Money;

public class Exercise03 {

	public static void main(String[] args) {
		var myMoney1 = new Money();
		System.out.println(myMoney1);
		System.out.println(myMoney1.amount());
		System.out.println(myMoney1.currency());
		var myMoney2 = new Money();
		System.out.println(myMoney1.equals(myMoney2));
		System.out.println(myMoney1.hashCode());
		System.out.println(myMoney2.hashCode());
	}

}
