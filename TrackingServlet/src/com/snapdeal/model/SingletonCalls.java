package com.snapdeal.model;

import java.util.HashMap;
import java.util.LinkedList;

public class SingletonCalls {
	
//	LinkedList<Device> devices;
	HashMap<String, Device> devices;
//	HashMap<String, String> ipAddrs;
	private static SingletonCalls INSTANCE = new SingletonCalls();
	
	private SingletonCalls(){
//		devices = new LinkedList<Device>();
//		calls = new HashMap<String, LinkedHashMap<String,LinkedList<Call>>>();
		devices = new HashMap<String, Device>();
//		ipAddrs = new HashMap<String, String>();
	}
	
	public static SingletonCalls getInstance(){
		return INSTANCE;
	}
	
	

//	public void addCall(String deviceIp, Call call){
//		synchronized (devices) {
//			for(Device d : devices){
//				if(d.getDeviceIp().equals(deviceIp)){
//					d.getCalls().add(call);
//					return;
//				}
//			}
//			Device temp = new Device();
//			temp.setDeviceIp(deviceIp);
//			temp.getCalls().add(call);
//			devices.add(temp);
//		}
//	}
	
	
	
//	public void addCall(String deviceId, String deviceIp, Call call){
//		synchronized(devices){
////			Call.incrCount();
////			call.setCallId();
////			calls.put(deviceId, value);
//			if(deviceId==null)
//			{
//				deviceId = ipAddrs.get(deviceIp);
//			}
//			
//			Device d = devices.get(deviceId);
//			if(d!=null)
//			{
//				call.setCallId(d.incCallCount());
//				LinkedList<Call> c = d.getCalls();
//				c.add(call);
//			}
//			else{
//				if(deviceId!=null){
//					Device d2 = new Device();
//					call.setCallId(d2.incCallCount());
//					d2.getCalls().add(call);
//					d2.setDeviceUrl(deviceIp);
//					d2.setDeviceId(deviceId);
//					devices.put(deviceId,d2);
//					ipAddrs.put(deviceIp, deviceId);
//				}
//			}
//		}
//	}
	
//	public LinkedList<Device> getDevices(){
//		return devices;
//	}
//	
	public HashMap<String, Device> getDevices()
	{
		return devices;
	}
	
	public void insertIntoDevices(String deviceId, Device device){
		synchronized(devices){
			devices.put(deviceId, device);
		}
	}
//	
}
