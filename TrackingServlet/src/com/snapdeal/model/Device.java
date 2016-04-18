package com.snapdeal.model;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

import com.snapdeal.service.MockingService;

@XmlRootElement
public class Device {

	String deviceId;
	String apsalarId;
	LinkedList<Call> calls;
	LinkedList<Mocking> mockings;
	static int mockingCount = 0;
	
	public Device(){
		calls = new LinkedList<Call>();
		mockings = new LinkedList<Mocking>();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public LinkedList<Call> getCalls() {
		return calls;
	}

	public void setCalls(LinkedList<Call> calls) {
		this.calls = calls;
	}

	public String getApsalarId() {
		return apsalarId;
	}

	public void setApsalarId(String apsalarId) {
		this.apsalarId = apsalarId;
	}
	
	public void insertCall(Call call)
	{
		calls.add(call);
	}

	public LinkedList<Mocking> getMockings() {
		return mockings;
	}

	public void setMockings(LinkedList<Mocking> mockings) {
		this.mockings = mockings;
	}
	
	public String insertMocking(Mocking mocking){
		Mocking m = MockingService.findMocking(mockings, mocking.getApi(), mocking.getRequest());
		if(m==null){
			mocking.setMockingId(++mockingCount);
			mockings.add(mocking);
			return "mocking created";
		}
		else{
			m.setResponse(mocking.getResponse());
			return "mocking updated";
		}
	}
	
	public String deleteMocking(int mockingId){
		for(Mocking m : mockings){
			if(m.getMockingId()==mockingId){
				mockings.remove(m);
				return "Mocking Removed";
			}
		}
		return "Mocking not Found";
	}
}
