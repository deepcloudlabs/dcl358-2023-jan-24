package com.example.crm.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class ReactiveKafkaTemplateConfig {
	@Bean
	public ReceiverOptions<String, String> createReceiverOpts(KafkaProperties props) {
		ReceiverOptions<String, String> basicReceiverOpts = ReceiverOptions.create(props.buildConsumerProperties());
		return basicReceiverOpts.subscription(Collections.singletonList("crm-events"));
	}

	@Bean
	public ReactiveKafkaConsumerTemplate<String, String> createReactiveConsumerKafkaTemplate(
			ReceiverOptions<String, String> kafkaReceiverOpts) {
		return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOpts);
	}
}
