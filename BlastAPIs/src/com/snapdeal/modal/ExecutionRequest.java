package com.snapdeal.modal;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExecutionRequest {
	

	
	String platform;
	int flowsTotal;
	int flowsPassed;
	ArrayList<ExecutionFlow> flowsExecuted;
	
	public ExecutionRequest(){}

	public int getFlowsTotal() {
		return flowsTotal;
	}

	public void setFlowsTotal(int flowsTotal) {
		this.flowsTotal = flowsTotal;
	}

	public int getFlowsPassed() {
		return flowsPassed;
	}

	public void setFlowsPassed(int flowsPassed) {
		this.flowsPassed = flowsPassed;
	}

	public ArrayList<ExecutionFlow> getFlowsExecuted() {
		return flowsExecuted;
	}

	public void setFlowsExecuted(ArrayList<ExecutionFlow> flowsExecuted) {
		this.flowsExecuted = flowsExecuted;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	
	
}
