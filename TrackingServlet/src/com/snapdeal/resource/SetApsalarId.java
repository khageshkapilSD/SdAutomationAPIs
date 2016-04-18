package com.snapdeal.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.snapdeal.service.SingletonService;

@Path("setApsalarId")
public class SetApsalarId {
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String setApsalarId(	@QueryParam("deviceId") String deviceId, 
								@QueryParam("apsalarId") String apsalarId
								){
		SingletonService.setApsalarId(deviceId, apsalarId);
		return "{\"status\":\"successfull\"}";
	}
	
}

