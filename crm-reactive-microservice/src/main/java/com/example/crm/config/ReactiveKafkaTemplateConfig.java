package com.example.crm.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import reactor.kafka.sender.SenderOptions;

@Configuration
public class ReactiveKafkaTemplateConfig {

	@Bean
	public ReactiveKafkaProducerTemplate<String, String> createReactiveProducerKafkaTemplate(
			KafkaProperties props){
		var properties= props.buildProducerProperties();
		return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(properties));
	}
}
