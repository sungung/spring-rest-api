package com.sungung.api.springrestapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
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
}
