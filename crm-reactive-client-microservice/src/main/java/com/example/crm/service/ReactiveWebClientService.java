package com.example.crm.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReactiveWebClientService {
	private final WebClient webClient = WebClient.builder()
			                                     .baseUrl("http://localhost:12022/customers")
			                                     .build();
	@Scheduled(fixedRate = 1_000)
	public void makeReactiveCall() {
		webClient.get()
		         .uri(urlBuilder -> urlBuilder.queryParam("page", 0)
		        		                      .queryParam("size", 10).build())
		         .retrieve()
		         .bodyToFlux(String.class)
		         .subscribe(System.out::println);
	}
}
