package com.sungung.api.springrestapi.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungung.api.springrestapi.entity.Product;

/***

>> Asynchronous REST operation

>>> POST returns 201 Created status with Location header

$ curl -i -X POST -H 'Content-Type: application/json' -d '{"code":4,"name":"Erase","origin":"China","price":10.0}' http://localhost:8080/products/

HTTP/1.1 201
Location: http://localhost:8080/products/4
Content-Length: 0
Date: Thu, 15 Mar 2018 23:04:57 GMT


>>> POST returns 202 Accepted status 

$ curl -i -X POST -H 'Content-Type: application/json' -d '{"code":4,"name":"Erase","origin":"China","price":10.0}' http://localhost:8080/products/a

HTTP/1.1 202
Location: http://localhost:8080/queue/4
Content-Length: 0
Date: Fri, 16 Mar 2018 01:31:11 GMT


>>> Check status of POSTing, cancel POSTing if status is not in progress

$ curl -i http://localhost:8080/queue/4

HTTP/1.1 200
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 16 Mar 2018 01:28:25 GMT

{"status":"Pending","eta":"10 minutes","_links":{"cancel":{"href":"http://localhost:8080/queue/delete/4"}}}


>>> Check status of POSTing again, cancel POSTing not option for in progress

$ curl -i http://localhost:8080/queue/4
HTTP/1.1 200
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 16 Mar 2018 01:28:28 GMT

{"status":"InProgress","eta":"1 minutes"}


>>> Then finally get location of new resource with 303 Status

$ curl -i http://localhost:8080/queue/4
HTTP/1.1 303
Location: http://localhost:8080/products/4
Content-Length: 0
Date: Fri, 16 Mar 2018 01:28:35 GMT

 *
 */

@RestController
@RequestMapping("/products")
public class ProductController extends Helper {
	
	private final static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping("/{code}")
	public HttpEntity<Product> get(@PathVariable long code){
		Product product = products.get(code);
		product.removeLinks();
		if (product != null){
			product.add(ControllerLinkBuilder.linkTo(ProductController.class).slash(product.getCode()).withSelfRel());
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@PostMapping
	public HttpEntity<Product> create(@RequestBody Product newProduct) throws URISyntaxException{
		products.put((long) 4,  newProduct);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ControllerLinkBuilder.linkTo(ProductController.class).slash(newProduct.getCode()).toUri());
		return new ResponseEntity<Product>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping("/a")
	public HttpEntity asyncCreate(@RequestBody Product newProduct) throws URISyntaxException{
		products.put((long) 4,  newProduct);
		return ResponseEntity.accepted().location(ControllerLinkBuilder.linkTo(QueueController.class).slash(newProduct.getCode()).toUri()).build();
	}
	
	@GetMapping
	public HttpEntity<PagedResources> getAll(Pageable pageable, PagedResourcesAssembler<Product> assembler){
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < 100; i++){
			products.add(new Product(i, "prod"+i, "name"+i, i*10));
		}
		// Slice list through PagedListHolder
		// Actually list slice should be made 
		// by database query with Spring Data
		PagedListHolder holder = new PagedListHolder();
		holder.setSource(products);
		holder.setPage(pageable.getPageNumber());
		holder.setPageSize(pageable.getPageSize());
		log.debug(pageable.toString());
		
		Page<Product> page = new PageImpl(holder.getPageList(), pageable, 100);		
		return new ResponseEntity<PagedResources>(assembler.toResource(page), HttpStatus.OK);		
	}

}
