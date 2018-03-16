package com.sungung.api.springrestapi.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sungung.api.springrestapi.entity.Queue;

@RestController
@RequestMapping("queue")
public class QueueController {
	
	private final static Map<Long, Queue.Status> stats = new HashMap<>();
	
	@GetMapping("/{code}")
	public HttpEntity<Queue> getStatus(@PathVariable long code) throws NoSuchMethodException, SecurityException{
		
		Queue q = null;
		Queue.Status status = stats.get(code);
		
		if (status == null){
			q = new Queue();
			q.setStatus(Queue.Status.Pending);
			q.setEta("10 minutes");
			Method method = QueueController.class.getMethod("delete", long.class);
			Link link = ControllerLinkBuilder.linkTo(method, code).withRel("cancel");
			q.add(link);
			stats.put(code, Queue.Status.Pending);
		} else if (status == Queue.Status.Pending) {
			q = new Queue();
			q.setStatus(Queue.Status.InProgress);
			q.setEta("1 minutes");
			stats.put(code, Queue.Status.InProgress);
		}
		
		if (q != null) return ResponseEntity.ok(q);
		return ResponseEntity.status(HttpStatus.SEE_OTHER).location(ControllerLinkBuilder.linkTo(ProductController.class).slash(code).toUri()).build();
	}
	
	@DeleteMapping("/delete/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long code){
		
	}
}
