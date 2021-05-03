package com.medin.pharmacy.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Level;

@RestController
class ELKController {
	private static final Logger LOG = LoggerFactory.getLogger(ELKController.class.getName());

	@Autowired
	RestTemplate restTemplete;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/elk")
	public String helloWorld() {
		String response = "Welcome to JavaInUse" + new Date();
		LOG.debug("test"+response);

		return response;
	}

	@RequestMapping(value = "/exception")
	public String exception() {
		String response = "";
		try {
			throw new Exception("Exception has occured....");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error"+e);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			LOG.error("Exception - " + stackTrace);
			response = stackTrace;
		}

		return response;
	}
}