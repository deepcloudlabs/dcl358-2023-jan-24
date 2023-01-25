package com.example.application;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class LegacyObserver {

	public static void main(String[] args) {
	var tradeEvents = List.of(
			new TradeEvent("msft", 100, 10),
			new TradeEvent("orcl", 102, 20),
			new TradeEvent("gogle", 130, 30),
			new TradeEvent("msft", 102, 40),
			new TradeEvent("orcl", 120, 50)
	);
	var tradeEventObservable = new TradeEventObservable();
	Observer slowObserver = (o,event) -> {
		try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
		System.err.println("Slow Observer has processed the event %s.".formatted(event));
	};
	Observer fastObserver = (o,event) -> {
		System.err.println("Fast Observer has processed the event %s.".formatted(event));
	};
	tradeEventObservable.addObserver(slowObserver);
	tradeEventObservable.addObserver(fastObserver);
	tradeEvents.forEach( tradeEventObservable::notifyObservers);
	}

}

@SuppressWarnings("deprecation")
class TradeEventObservable extends Observable{

	@Override
	public void notifyObservers(Object event) {
		setChanged();
		super.notifyObservers(event);
	}
	
}
record TradeEvent(String symbol, double price,double quantity) {}
