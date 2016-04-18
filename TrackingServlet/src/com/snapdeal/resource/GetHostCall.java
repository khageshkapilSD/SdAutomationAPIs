//package com.snapdeal.resource;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//
//import com.snapdeal.model.Device;
//import com.snapdeal.service.SingletonService;
//
//@Path("getHostCall")
//public class GetHostCall {
//	@QueryParam("host") String host;
//	@QueryParam("deviceIp") String deviceIp;
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Device getCall(){
//		return new SingletonService().getHostCall(deviceIp, host);
//	}
//	
//}
//
