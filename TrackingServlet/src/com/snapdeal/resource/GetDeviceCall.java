package com.snapdeal.resource;

import java.net.HttpURLConnection;
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

@Path("getDeviceCalls")
public class GetDeviceCall {
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDeviceCalls(	@QueryParam("deviceId") String deviceId,
									@QueryParam("duration") String duration,
									@QueryParam("count") String count,
									@QueryParam("callType") String callType
								){
		
		if (deviceId == null) {
		    throw new WebApplicationException(
		      Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
		        .entity("deviceID parameter is mandatory")
		        .build()
		    );
		}
		Device resDevice = new Device();
		resDevice.setDeviceId("");
		resDevice.setApsalarId("");
		Device temp = SingletonService.getDevice(deviceId);
		if(temp!=null){
			resDevice.setDeviceId(temp.getDeviceId());
			resDevice.setApsalarId(temp.getApsalarId());
			resDevice.setCalls(temp.getCalls());
			if(callType!=null && !callType.equals("")){
				resDevice.setCalls(
						CallListFilter.filterViaRequestURL(resDevice.getCalls(), callType));
			}
			if(duration!=null && !duration.equals("")){
				long lDuration = Long.parseLong(duration);
				resDevice.setCalls(
						CallListFilter.filterViaTimeStamp(resDevice.getCalls(), lDuration));
			}
			if(count!=null && !count.equals("")){
				int lCount = Integer.parseInt(count);
				resDevice.setCalls(
						CallListFilter.filterViaCount(resDevice.getCalls(), lCount));
			}
		}
		return resDevice;
	}
	
}
