package com.snapdeal.resource;

import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.LinkedList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.snapdeal.model.Call;
import com.snapdeal.model.Device;
import com.snapdeal.service.CallListFilter;
import com.snapdeal.service.SingletonService;


@Path("CallDuration")
public class CallDuration {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCallDuration(	@QueryParam("deviceId") String deviceId,
									@QueryParam("start") String start,
									@QueryParam("end") String end
									){
		if (deviceId == null || start == null || end == null) {
		    throw new WebApplicationException(
		      Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
		        .entity("deviceID parameter is mandatory")
		        .build()
		    );
		}
		
		Timestamp t1 = Timestamp.valueOf(start);
		Timestamp t2 = Timestamp.valueOf(end);
		LinkedList<Call> calls = SingletonService.getDevice(deviceId).getCalls();
		calls = CallListFilter.filterViaRequestURL(calls, "mobileapi.snapdeal.com");
		calls = CallListFilter.filterViaDuration(calls,t1,t2);
		t2.setTime(0);
		t1 = new Timestamp((new java.util.Date()).getTime());
		String apiCalls = "\"calls\" :[";
		int ctr = 0;
		for(Call c : calls){
			if(ctr!=0)
				apiCalls += ","; 
			apiCalls += "\""+c.getRequestURL()+"\"";
			ctr++;
			
			if(c.getRequestTimeStamp().before(t1))
				t1 = c.getRequestTimeStamp();
			if(c.getResponseTimeStamp().after(t2))
				t2 = c.getResponseTimeStamp();
		}
		apiCalls += "]";
		long diff = t2.getTime() - t1.getTime();
		
		return "{ \"diff\": \""+diff+"\","
				+apiCalls
				+ " }";
	}
}
