package com.medin.pharmacy.repository.impl;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSMSMQProducer {

	private final static String QUEUENAME = "sms-queue";

	private final static String EXCHANGE_NAME = "medin-exchange";
	

	@Bean
	Queue smsQueue() {
		return new Queue(QUEUENAME, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	Binding smsBinding(Queue smsQueue, DirectExchange exchange) {
		return BindingBuilder.bind(smsQueue).to(exchange).with(QUEUENAME);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

}
