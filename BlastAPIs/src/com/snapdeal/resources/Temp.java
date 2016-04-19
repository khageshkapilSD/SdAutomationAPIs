package com.snapdeal.resources;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.snapdeal.modal.DatabaseSingleton;

@Path("insertIntoFlows")
public class Temp {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String insert() throws SQLException{
		String sql = "INSERT INTO flows (testName, title, stdName) VALUES ('aaa','bbb','ccc')";
		int s = DatabaseSingleton.getInstance().executeUpdate(sql);
		return "{\"status\":\"true\",\"message\":\""+s+"\"}";
	} 

}
