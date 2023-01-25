package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Exercise2 {
	private static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/price?symbol=%s";
	private static final AtomicInteger counter = new AtomicInteger(0);
	public static void main(String[] args) throws Exception {
		var httpClient = HttpClient.newHttpClient();
		var symbols = List.of("BTCUSDT","ETHBTC","LTCBTC","BNBBTC","NEOBTC","QTUMETH","EOSETH","SNTETH", "BNTETH","BCCBTC");
		var begin = System.currentTimeMillis();
		for (var symbol : symbols) {
			var httpRequest = HttpRequest.newBuilder(new URI(BINANCE_URL.formatted(symbol)))
					.build();
			httpClient.sendAsync(httpRequest,BodyHandlers.ofString())
			          .thenApply(HttpResponse::body)
			          .thenAcceptAsync(ticker -> {
			        	  if(counter.incrementAndGet() == symbols.size()) {
			        		  var end = System.currentTimeMillis();
			        		  System.err.println("Duration: %d ms.".formatted(end-begin));
			        	  }
			        	  System.err.println("[%s] %s".formatted(Thread.currentThread().getName(),ticker));			
			          });
		}
		TimeUnit.SECONDS.sleep(5);
		System.err.println("Application is done.");
	}

}
