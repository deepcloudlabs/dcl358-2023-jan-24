package com.example.crm.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveBinanceRestClient {
	private static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3/ticker";
	private static final List<String> SYMBOLS = List.of(
			"BTCUSDT", "LTCBTC", "BNBBTC", "NEOBTC", "EOSETH",
			"SNTETH","BNTETH","BCCBTC","SALTBTC","SALTETH","XVGETH",
			"XVGBTC", "SUBETH","EOSBTC","MTHBTC","ETCETH","DNTBTC","ENGBTC"
			
	);
	private WebClient webClient = WebClient.builder().baseUrl(BINANCE_BASE_URL).build();
	private static final Comparator<Ticker> BY_PRICE =
		(t1,t2) -> Double.parseDouble(t1.getPrice()) <= Double.parseDouble(t2.getPrice()) ?	
			-1 : 1 ;
	@Scheduled(fixedRate = 20_000)
	public void callBinance() {
		
		Flux.fromIterable(SYMBOLS)
		    .parallel()
		    .runOn(Schedulers.boundedElastic()) // like Executors.newFixedThreadPool(8)
		    .flatMap(this::getTicker)
		    .sorted(BY_PRICE)
		    .subscribe(ticker -> {
		    	System.out.println(Thread.currentThread().getName()+": "+ticker);
		    });
	}
	
	public Mono<Ticker> getTicker(String symbol) { // async
		System.out.println(Thread.currentThread().getName()+" is sending the request to "+symbol);
		return webClient.get() // IO Bound
				        .uri(uriBuilder -> uriBuilder.path("/price")
				        		            .queryParam("symbol", symbol).build())
				        .retrieve()
				        .bodyToMono(Ticker.class);
	}
}

@Data
class Ticker {
	private String symbol;
	private String price;
}