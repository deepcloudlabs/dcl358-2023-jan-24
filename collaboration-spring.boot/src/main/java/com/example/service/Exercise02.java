package com.example.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import com.example.dto.TradeEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Exercise02 implements WebSocketHandler {
	private final WebSocketClient webSocketClient;
	private final String binanceWsUrl;
	private final ObjectMapper objectMapper;

	public Exercise02(@Value("${binance.ws.url}") String binanceWsUrl, 
			WebSocketClient webSocketClient,
			ObjectMapper objectMapper) {
		this.binanceWsUrl = binanceWsUrl;
		this.webSocketClient = webSocketClient;
		this.objectMapper = objectMapper;
	}

	@PostConstruct
	public void connect() {
		webSocketClient.doHandshake(this, binanceWsUrl);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Connected to the binance ws server.");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		var eventAsString = message.getPayload().toString();
		var event = objectMapper.readValue(eventAsString, TradeEventDTO.class);
		System.err.println(event);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		System.err.println("An error has occured: %s.".formatted(e.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.err.println("Disconnected from the binance ws server.");
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
}
