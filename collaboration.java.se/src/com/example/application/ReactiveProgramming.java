package com.example.application;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveProgramming {

	public static void main(String[] args) {
		System.err.println("Application is just started.");
		var tradeEvents = List.of(
				new TradeEvent("msft", 100, 10),
				new TradeEvent("orcl", 102, 20),
				new TradeEvent("gogle", 130, 30),
				new TradeEvent("msft", 102, 40),
				new TradeEvent("orcl", 120, 50)
		);
		var slowSubscriber = new AlgotradingProcessor();
		var fastSubscriber = new SignalProcessor();
		try(var publisher = new SubmissionPublisher<TradeEvent>()){
			publisher.subscribe(slowSubscriber);
			publisher.subscribe(fastSubscriber);
			System.err.println("Before submitting all events");
			tradeEvents.parallelStream().forEach(publisher::submit);
			System.err.println("After submitting all events");
		}
		try {TimeUnit.SECONDS.sleep(30);}catch (Exception e) {}
		System.err.println("Application is done.");
	}

}

class AlgotradingProcessor implements Subscriber<TradeEvent>{
	private Subscription subscription;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(10);
		System.err.println("AlgotradingProcessor is subscribed to the publisher");
	}

	@Override
	public void onNext(TradeEvent event) {
		try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
		System.err.println("[%s] AlgotradingProcessor processing an event: %s".formatted(Thread.currentThread().getName(),event));
		subscription.request(1);
	}

	@Override
	public void onError(Throwable t) {
		System.err.println("AlgotradingProcessor receives an error: %s".formatted(t.getMessage()));
	}

	@Override
	public void onComplete() {
		System.err.println("AlgotradingProcessor has processed all events!");
		
	}
	
}

class SignalProcessor implements Subscriber<TradeEvent>{
	private Subscription subscription;
	

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
		System.err.println("SignalProcessor is subscribed to the publisher");
	}
	
	@Override
	public void onNext(TradeEvent event) {
		System.err.println("[%s] SignalProcessor processing an event: %s".formatted(Thread.currentThread().getName() ,event));
		subscription.request(1);
	}
	
	@Override
	public void onError(Throwable t) {
		System.err.println("SignalProcessor receives an error: %s".formatted(t.getMessage()));
	}
	
	@Override
	public void onComplete() {
		System.err.println("SignalProcessor has processed all events!");
		
	}
	
}