package com.snapdeal.service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.ListIterator;

import com.snapdeal.model.Call;

public class CallListFilter {

	public static LinkedList<Call> filterViaTimeStamp(LinkedList<Call> calls, long duration){
		LinkedList<Call> result = new LinkedList<Call>();
		Long durationInMilliSec = duration*60000;
		Timestamp t2 = new Timestamp((new java.util.Date()).getTime());
		Timestamp t1 = new Timestamp(t2.getTime()-durationInMilliSec);
		for(Call c : calls){
			if(c.getTimeStamp().after(t1) && c.getTimeStamp().before(t2)){
				result.add(c);
			}
		}
		return result;
	}
	
	public static LinkedList<Call> filterViaRequestURL(LinkedList<Call> calls, String type){
		LinkedList<Call> result = new LinkedList<Call>();
		for(Call c : calls){
			if(c.getRequestURL().contains(type)){
				result.add(c);
			}
		}		
		return result;
	}
	
	public static LinkedList<Call> filterViaCount(LinkedList<Call> calls, int count){
		if(calls.size()>count){
			LinkedList<Call> result = new LinkedList<Call>();
			int diff = calls.size() - count;
			ListIterator<Call> itr = calls.listIterator();
			for(int i=0;i<diff;i++)
				itr.next();
			while(itr.hasNext())			
				result.add(itr.next());
			return result;
		}
		else{
			return calls;
		}
	}
	
	public static LinkedList<Call> filterViaDuration(LinkedList<Call> calls, Timestamp t1, Timestamp t2){
		LinkedList<Call> result = new LinkedList<Call>();
		for(Call c : calls){
			if(c.getRequestTimeStamp().after(t1)){
				if(c.getResponseTimeStamp().before(t2))
					result.add(c);
			}
		}		
		return result;
	}
}
