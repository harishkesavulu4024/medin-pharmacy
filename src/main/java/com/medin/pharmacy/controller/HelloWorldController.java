package com.medin.pharmacy.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value="/v1")
public class HelloWorldController {

	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String index() {
		return "Greetings from Spring Boot!";
	}

}
