package com.sungung.api.springrestapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sungung.api.springrestapi.entity.Customer;

/***
 * API version with URI path
 * 
 * Return 415 UNSUPPORTED_MEDIA_TYPE response when version is missing or invalid
 * 
 * $ curl -i http://localhost:8080/v1/customers/1
 * HTTP/1.1 200
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 28 Feb 2018 05:15:29 GMT
 * 
 * {"firstName":"John","lastName":"Smith"}
 * 
 * 
 * $ curl -i http://localhost:8080/v3/customers/1
 * HTTP/1.1 415
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 28 Feb 2018 05:15:35 GMT
 * 
 * {"message":"Unsupported version"}
 * 
 * @author spark
 *
 */
@RestController
public class UriVersionController extends Helper{		
	
	@GetMapping("/{apiVersion}/customers/{customerId}")
	public Map get(@PathVariable Version apiVersion, @PathVariable long customerId){		
		return findCustomer(apiVersion, customerId);
	}		
	
}
