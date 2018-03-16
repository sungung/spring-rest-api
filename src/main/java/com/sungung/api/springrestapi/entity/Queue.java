package com.sungung.api.springrestapi.entity;

import org.springframework.hateoas.ResourceSupport;

public class Queue extends ResourceSupport {
	public static enum Status {
		Pending,
		InProgress;
	}
	
	private Status status;
	private String eta;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
		
}
