package com.medin.pharmacy.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.repository.QueueConsumer;

@Repository
public class RabbitMQConsumer implements QueueConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

	
	
	@Override
	@RabbitListener(queues = "sms-queue")
	public void receiveOtpMessage(String otpSms) {
		System.out.println("otpSms "+otpSms);
	}

}
