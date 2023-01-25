package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.DomainValueObject;

@DomainValueObject
public record Money(double amount, FiatCurrency currency) {
	public Money(double amount, FiatCurrency currency) {
		if (amount <= 0)
			throw new IllegalArgumentException("Amount must be positive.");
		Objects.requireNonNull(currency);
		this.amount = amount;
		this.currency = currency;
	}
	
	public Money(double amount) {
		this(amount,FiatCurrency.TL);
	}
	public Money() {
		this(8_500);
	}

	public Money multiply(double rate) {
		return new Money(rate*amount, this.currency);
	}

	public boolean lessThan(Money multiply) {
		return false;
	}
	
	
}
