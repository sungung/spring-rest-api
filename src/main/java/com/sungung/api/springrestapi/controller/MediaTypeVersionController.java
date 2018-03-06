package com.sungung.api.springrestapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sungung.api.springrestapi.entity.Customer;

/***
 * API version with MEDIA_TYPE property
 * $ curl -i http://localhost:8080/customers/1 -H "Content-Type: application/vnd.api-v1.0+json"
 * Will get 415(Unsupported Media Type) response if Content-Type header is missing or wrong version.
 * 
 * With missing Content-Type header or invalid version, will get 415 response.
 * 
 * $ curl -i http://localhost:8080/customers/1 -H "Content-Type: application/vnd.api-v1.1+json"
 * HTTP/1.1 415
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 28 Feb 2018 03:50:04 GMT
 * 
 * {"cause":"Content type 'application/vnd.api-v1.1+json' not supported",
 * "error":"Unsupported version",
 * "supported":[{"type":"application","subtype":"vnd.api-v1.0+json","parameters":{},"qualityValue":1.0,"concrete":true,"charset":null,"wildcardType":false,"charSet":null,"wildcardSubtype":false}]}
 * 
 * @author spark
 *
 */
@RestController
@RequestMapping(value = "/customers", consumes = "application/vnd.api-v1.0+json")
public class MediaTypeVersionController extends Helper {
	
	@GetMapping(value = "/{customerId}")
	public Map get(@PathVariable long customerId){
		return findCustomer(Version.v1, customerId);
	}
	
	@PostMapping("/")
	public Customer update(@RequestBody Customer customer){
		customers.put(customer.getId(), customer);
		return customers.get(customer.getId());
	}
	
	// delete method should be with NO CONTENT, otherwise get confused with get method
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long customerId){
		customers.remove(customerId);
	}
}
