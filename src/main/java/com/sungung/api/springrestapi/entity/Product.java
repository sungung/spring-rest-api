package com.sungung.api.springrestapi.entity;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product extends ResourceSupport {
	
	private final long code;
	private final String name;
	private final String origin;
	private final double price;
	
	@JsonCreator
	public Product(@JsonProperty("code") long code, 
			@JsonProperty("name") String name, 
			@JsonProperty("origin") String origin, 
			@JsonProperty("price") double price) {
		super();
		this.code = code;
		this.name = name;
		this.origin = origin;
		this.price = price;
	}
	public long getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getOrigin() {
		return origin;
	}
	public double getPrice() {
		return price;
	}
	
	
}
