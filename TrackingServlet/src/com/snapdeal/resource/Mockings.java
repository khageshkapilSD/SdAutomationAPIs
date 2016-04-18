package com.snapdeal.resource;

import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.snapdeal.model.Device;
import com.snapdeal.model.Mocking;
import com.snapdeal.model.MockingRequest;
import com.snapdeal.service.MockingService;
import com.snapdeal.service.SingletonService;


@Path("Mockings")
public class Mockings {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setMocking")
	public String setMocking(MockingRequest request){
		Device d = SingletonService.getDevice(request.getDeviceId());
		Mocking mocking = new Mocking();
		mocking.setApi(request.getApi());
		mocking.setRequest(request.getRequest());
		mocking.setResponse(request.getResponse());
		if(d!=null){
			String msg = d.insertMocking(mocking);
			return "{\"status\":\""+msg+"\"}";
		}
		else{
			return "{\"status\":\"fail\",\"message\":\"Device not found\"}";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getMockings")
	public LinkedList<Mocking> getMocking(@QueryParam("deviceId") String deviceId){
		Device d = SingletonService.getDevice(deviceId);
		return d.getMockings();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("removeMocking")
	public String getMocking(@QueryParam("deviceId") String deviceId, @QueryParam("mockingId") int mockingId){
		Device d = SingletonService.getDevice(deviceId);
		String msg = d.deleteMocking(mockingId);
		return "{\"status\":\"successfull\",\"message\":\""+msg+"\"}";
	}
	
}
