package com.sungung.api.springrestapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/header")
public class CustomerHeaderVersionController extends Helper{
	
	@GetMapping("/customers/{customerId}")
	public Map get(@RequestHeader("apiVersion") Version version, @PathVariable long customerId){
		return findCustomer(version, customerId);
	}
}
