package org.snapdeal.statusboard.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status{

	@XmlElement(name="status") 
	String key;
	String msg;
	
	Status() {
		key = "";
		msg = "";
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msgVal) {
		msg = msgVal;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String keyVal) {
		key = keyVal;
	}
	
	public static Status getSuccessStatus(String msg) {
		Status successStatus = new Status();
		successStatus.setKey("Success");
		successStatus.setMsg(msg);
		return successStatus;
	}
	
	public static Status getFailureStatus(String failureReason) {
		Status failureStatus = new Status();
		failureStatus.setKey("Failure");
		failureStatus.setMsg(failureReason);
		return failureStatus;
	}
}
