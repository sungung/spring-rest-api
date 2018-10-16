package com.sungung.api.springrestapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	private final static Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@GetMapping("/ping")
	public String ping(){
		return "pong";
	}
	
	@GetMapping(value = "/exception")
	public String exception(){
		// expecting ArithmeticException exception
		// global exception handling will manage response entity
		int i = 1/0;
		return "no way";
	}
	
	@PostMapping("/ping")
	public String postPing(){
		return "post-pong";
	}
	
	
}
