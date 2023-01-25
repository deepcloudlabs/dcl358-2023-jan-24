package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class Exercise01 {
	private static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/price?symbol=%s";
	public static void main(String[] args) throws Exception {
		var httpClient = HttpClient.newHttpClient();
		var symbols = List.of("BTCUSDT","ETHBTC","LTCBTC","BNBBTC","NEOBTC","QTUMETH","EOSETH","SNTETH", "BNTETH","BCCBTC");
		var begin = System.currentTimeMillis();
		for (var symbol : symbols) {
			var httpRequest = HttpRequest.newBuilder(new URI(BINANCE_URL.formatted(symbol)))
					.build();
			var httpResponse = httpClient.send(httpRequest,BodyHandlers.ofString());
			System.out.println(httpResponse.body());			
		}
		var end = System.currentTimeMillis();
		System.err.println("Duration: %d ms.".formatted(end-begin));
	}

}
