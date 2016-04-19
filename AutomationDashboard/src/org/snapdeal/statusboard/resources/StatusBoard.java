package org.snapdeal.statusboard.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.snapdeal.statusboard.model.ModuleIssue;
import org.snapdeal.statusboard.model.ModuleReport;
import org.snapdeal.statusboard.model.ReleaseStructure;
import org.snapdeal.statusboard.model.Status;
import org.snapdeal.statusboard.services.StatusBoardUtils;

@Path("/statusBoard")
public class StatusBoard {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createRelease")
	public Status getNewReleaseData(ReleaseStructure releaseStructure) {
		return new StatusBoardUtils().createRelease(releaseStructure); 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertReport")
	public Status insertReport(ModuleReport moduleReport) {
		return new StatusBoardUtils().updateReport(moduleReport);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertIssue")
	public Status insertIssue(ModuleIssue moduleIssue) {
		return new StatusBoardUtils().updateIssue(moduleIssue);
	}
}
