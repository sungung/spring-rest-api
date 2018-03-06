package com.sungung.api.springrestapi.controller;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungung.api.springrestapi.entity.Product;

@RestController
@RequestMapping("/products")
public class ProductController extends Helper {
	@GetMapping("/{code}")
	public HttpEntity<Product> get(@PathVariable long code){
		Product product = products.get(code);
		if (product != null){
			product.add(ControllerLinkBuilder.linkTo(ProductController.class).slash(product.getCode()).withSelfRel());
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
}
