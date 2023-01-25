package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.example.dto.TickerDTO;

@Service
public class Exercise01 {
	@Value("${binance.url}")
	String binanceUrl;
	
    @Scheduled(fixedRate = 1_000)
	public void callBinance() {
		var restTemplate = new RestTemplate();
		var ticker = restTemplate.getForEntity(binanceUrl, TickerDTO.class).getBody();
		System.out.println(ticker);
	}
	@Scheduled(fixedRate = 1_000)
	public void asyncCallBinance() {
		var restTemplate = new AsyncRestTemplate();
		restTemplate.getForEntity(binanceUrl, String.class)
		            .addCallback(res -> System.out.println(res.getBody()), System.err::println);
	}
}
