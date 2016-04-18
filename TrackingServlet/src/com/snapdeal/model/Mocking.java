package com.snapdeal.model;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mocking {

	int mockingId;
	String api;
	String request;
	String response;
	
	
	public Mocking(){
		setApi("");
		setRequest("");
		setResponse("");
	}


	public String getApi() {
		return api;
	}


	public void setApi(String api) {
		this.api = api;
	}


	public String getRequest() {
		return request;
	}


	public void setRequest(String request) {
		this.request = request;
	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}


	public int getMockingId() {
		return mockingId;
	}


	public void setMockingId(int mockingId) {
		this.mockingId = mockingId;
	}
	
	
	
}
