package com.snapdeal.resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.snapdeal.modal.DatabaseSingleton;
import com.snapdeal.modal.ExecutionFlow;
import com.snapdeal.modal.ExecutionRequest;
import com.snapdeal.modal.ExecutionResponce;

@Path("Execution")
public class Execution {
	
	@POST
	@Path("insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insert(ExecutionRequest execution) throws SQLException{
		
		String sql = "INSERT INTO "+execution.getPlatform()+"_executions_summary (flows_executed, flows_passed) VALUES ('"+execution.getFlowsTotal()+"','"+execution.getFlowsPassed()+"')";
		DatabaseSingleton.getInstance().executeUpdate(sql);
		sql = "SELECT MAX(execution_id) FROM "+execution.getPlatform()+"_executions_summary";
		ResultSet rs = DatabaseSingleton.getInstance().executeSQL(sql);
		int ex_id=-1;
		while(rs.next()){
			ex_id = Integer.parseInt(rs.getString("MAX(execution_id)"));
		}
		
		for(ExecutionFlow ef : execution.getFlowsExecuted()){
		
			sql = "SELECT flow_id FROM flows WHERE title='"+ef.getFlowTitle()+"'";
			rs = DatabaseSingleton.getInstance().executeSQL(sql);
			int flow_id=-1;
			while(rs.next()){
				flow_id = Integer.parseInt(rs.getString("flow_id"));
			}
			sql = "INSERT INTO "+execution.getPlatform()+"_executions_detailed  (execution_id, flow_id, status) VALUES ('"+ex_id+"','"+flow_id+"','"+ef.getStatus()+"')";
			DatabaseSingleton.getInstance().executeUpdate(sql);
		}
		
		
		
		return "{"
				+ "\"status\":\"successfull\","
				+ "\"message\":\"execution inserted\","
				+ "\"executionId\":\""+ex_id+"\""
				+ "}";
	}
	
	
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ExecutionResponce> getExections(	@DefaultValue("-1") @QueryParam("executionId") int executionId,
								@QueryParam("platform") String platform,
								@DefaultValue("1")@QueryParam("count") int count
								){
		String sql;
		if(executionId==-1){
			sql = "SELECT * FROM "+platform+"_executions_summary ORDER BY execution_id DESC";
		}
		else{
			sql = "SELECT * FROM "+platform+"_executions_summary WHERE execution_id <= "+executionId+" ORDER BY execution_id DESC";
		}
		ArrayList<ExecutionResponce> result = new ArrayList<ExecutionResponce>();
		try{
		ResultSet rs = DatabaseSingleton.getInstance().executeSQL(sql);
		int i = count;
		while(rs.next() && i!=0){
			i--;
			ExecutionResponce temp = new ExecutionResponce();
			temp.setId(rs.getInt("execution_id"));
			temp.setPlatform(platform);
			temp.setTotalFlows(rs.getInt("flows_executed"));
			temp.setPassedFlows(rs.getInt("flows_passed"));
			temp.setFailedFlows(temp.getTotalFlows()-temp.getPassedFlows());
			String sql2 = 	"SELECT "+platform+"_executions_detailed.status, flows.title "
							+ "FROM "+platform+"_executions_detailed JOIN flows "
							+ "ON "+platform+"_executions_detailed.flow_id = flows.flow_id "
							+ "WHERE "+platform+"_executions_detailed.execution_id = "+temp.getId()+"";
			ResultSet rs2 = DatabaseSingleton.getInstance().executeSQL(sql2);
			while(rs2.next()){
				ExecutionFlow tempFlow = new ExecutionFlow();
				tempFlow.setFlowTitle(rs.getString(2));
				tempFlow.setStatus(rs.getString(1));
				temp.getFlows().add(tempFlow);
			}
			result.add(temp);
		}
		}
		catch(Exception e){
			
		}
		return result;
	}
}
