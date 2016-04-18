/*package com.snapdeal.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.snapdeal.model.Call;
import com.snapdeal.service.SingletonService;

@Path("getCall")
public class GetCall {
	@QueryParam("callId") String callId;
	@QueryParam("deviceId") String deviceId;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Call getCall(){
		return new SingletonService().getCall(deviceId, callId);
	}
	
}

*/