package com.snapdeal.modal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExecutionFlow {

	String flowTitle;
	String status;
	
	public ExecutionFlow(){}

	public String getFlowTitle() {
		return flowTitle;
	}

	public void setFlowTitle(String flowTitle) {
		this.flowTitle = flowTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}