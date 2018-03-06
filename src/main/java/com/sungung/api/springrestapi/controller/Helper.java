package com.sungung.api.springrestapi.controller;

import java.util.HashMap;
import java.util.Map;

import com.sungung.api.springrestapi.controller.Helper.Version;
import com.sungung.api.springrestapi.entity.Customer;
import com.sungung.api.springrestapi.entity.Product;

public class Helper {
	protected static Map<Long, Customer> customers = new HashMap<Long, Customer>(){{
		put(1l, new Customer(1l, "John", "Smith", "john@company.com"));
		put(2l, new Customer(2l, "Sam", "Rudd", "sam@company.com"));
		put(3l, new Customer(3l, "Mark", "Andrews", "mark@company.com"));
	}};
	protected static Map<Long, Product> products = new HashMap<Long, Product>(){{
		put(1l, new Product(1l, "Notepad", "China", 10.0));
		put(2l, new Product(2l, "Pen", "Australia", 20.0));
		put(3l, new Product(3l, "Pencil", "Japan", 30.0));
	}};	
	
	protected static enum Version { v1, v2 };
	
	protected Map findCustomer(Version version, long customerId) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		Customer customer = customers.get(customerId);
		
		if (customer == null) return response;
		
		if (Version.v1 == version) {
			response.put("firstName", customer.getFirstName());
			response.put("lastName", customer.getLastName());			
		} else if (Version.v2 == version) {
			response.put("firstName", customer.getFirstName());
			response.put("lastName", customer.getLastName());
			response.put("email", customer.getEmail());			
		}
		
		return response;
	}	
}
