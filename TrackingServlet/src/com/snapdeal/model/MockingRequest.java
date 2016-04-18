package com.snapdeal.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MockingRequest {

	String deviceId;
	String api;
	String request;
	String response;
	
	public MockingRequest(){}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	
	
}
