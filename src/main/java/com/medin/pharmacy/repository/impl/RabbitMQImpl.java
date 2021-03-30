package com.medin.pharmacy.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.repository.QueueRepository;

@Repository("queueRepository")
@PropertySource("classpath:base-config.properties")
public class RabbitMQImpl implements QueueRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQImpl.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Environment env;

	@Override
	public void sendOtp(String otp) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Sending OtpSms to Rabbit MQ");
		}
		rabbitTemplate.convertAndSend(env.getProperty("base-exchange"), env.getProperty("otp-sms-queue"), otp);
	}
}