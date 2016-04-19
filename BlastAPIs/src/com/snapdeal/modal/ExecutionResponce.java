package com.snapdeal.modal;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ExecutionResponce {
	int id;
	String platform;
	int totalFlows;
	int passedFlows;
	int failedFlows;
	ArrayList<ExecutionFlow> flows;
	
	public ExecutionResponce(){
		flows = new ArrayList<ExecutionFlow>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public int getTotalFlows() {
		return totalFlows;
	}
	public void setTotalFlows(int totalFlows) {
		this.totalFlows = totalFlows;
	}
	public int getPassedFlows() {
		return passedFlows;
	}
	public void setPassedFlows(int passedFlows) {
		this.passedFlows = passedFlows;
	}
	public int getFailedFlows() {
		return failedFlows;
	}
	public void setFailedFlows(int failedFlows) {
		this.failedFlows = failedFlows;
	}
	public ArrayList<ExecutionFlow> getFlows() {
		return flows;
	}
	public void setFlows(ArrayList<ExecutionFlow> flows) {
		this.flows = flows;
	}
	
}
