package com.snapdeal.service;

import java.util.LinkedList;

import com.snapdeal.model.Mocking;

public class MockingService {
	
	public static Mocking findMocking(LinkedList<Mocking> mockings, String api, String request ){
		for(Mocking m : mockings){
			if(m.getApi().equals(api) && m.getRequest().equals(request)){
				return m;
			}
		}
		return null;
	}
	
	public static Mocking searchMocking(LinkedList<Mocking> mockings, String url, String request){
		for(Mocking m : mockings){
			if(url.contains(m.getApi()) && m.getRequest().equals(request)){
				return m;
			}
		}
		for(Mocking m : mockings){
			if(url.contains(m.getApi()) && m.getRequest().equals("")){
				return m;
			}
		}
		return null;
	}

}
