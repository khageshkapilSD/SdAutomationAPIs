package com.snapdeal.model;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Call {

	String requestURL;
	String response;
	String requestParameters;
	String requestType;
	Timestamp requestTimeStamp;
	Timestamp responseTimeStamp;
	Timestamp timeStamp;
	
	
	public Call(){
		setTimeStamp(new Timestamp((new java.util.Date()).getTime()));
	}

	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
		this.requestTimeStamp = new Timestamp((new java.util.Date()).getTime());
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
		this.responseTimeStamp = new Timestamp((new java.util.Date()).getTime());
	}
	public String getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(String requestParameters) {
		this.requestParameters = requestParameters;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Timestamp getRequestTimeStamp() {
		return requestTimeStamp;
	}

	public void setRequestTimeStamp(Timestamp requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}

	public Timestamp getResponseTimeStamp() {
		return responseTimeStamp;
	}

	public void setResponseTimeStamp(Timestamp responseTimeStamp) {
		this.responseTimeStamp = responseTimeStamp;
	}	
	
	
}
