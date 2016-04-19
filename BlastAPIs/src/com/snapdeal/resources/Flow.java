package com.snapdeal.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.snapdeal.modal.DatabaseSingleton;
import com.snapdeal.modal.FlowRequest;

@Path("Flows")
public class Flow {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insert")
	public String insert(FlowRequest f) throws SQLException{
		String sql = "INSERT INTO flows (testName, title, stdName) VALUES ('"+f.getTestName()+"','"+f.getTitle()+"','"+f.getStdName()+"')";
		DatabaseSingleton.getInstance().executeUpdate(sql);
		return "{\"status\":\"successfull\",\"message\":\""+f.getTitle()+" inserted\"}";
	} 
}
