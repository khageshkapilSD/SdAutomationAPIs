package com.snapdeal.modal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FlowRequest {

	String testName;
	String title;
	String stdName;
	
	public FlowRequest(){}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStdName() {
		return stdName;
	}

	public void setStdName(String stdName) {
		this.stdName = stdName;
	}	
	
}
