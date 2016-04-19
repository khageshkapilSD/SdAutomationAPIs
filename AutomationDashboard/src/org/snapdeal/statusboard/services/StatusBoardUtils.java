package org.snapdeal.statusboard.services;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snapdeal.statusboard.model.ModuleIssue;
import org.snapdeal.statusboard.model.ModuleReport;
import org.snapdeal.statusboard.model.ReleaseStructure;
import org.snapdeal.statusboard.model.Status;

public class StatusBoardUtils {
	
	private static Map<String,String> releases = new HashMap<String,String>();
	
	public StatusBoardUtils() {
	}
	
	public Status createRelease(ReleaseStructure releaseStructure) {
		Status status = null;
		String msg = "";
		File f = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+releaseStructure.getReleaseName());
		try {
			if(f.exists()) {
				msg = "Failure....Release name already exists..!!";
				throw new Exception(msg);
			}
			else {
				msg = f.getAbsolutePath();
				f.mkdirs();
				
			}
			for(String platform : releaseStructure.getPlatforms())
				createPlatform(releaseStructure.getReleaseName(), platform, releaseStructure.getModules());
			
			
			File releaseHtml = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+releaseStructure.getReleaseName()+"/"+releaseStructure.getReleaseName()+".html");
			releaseHtml.createNewFile();
			BufferedWriter releaseHtmlWriter = new BufferedWriter(new FileWriter(releaseHtml));
			releaseHtmlWriter.write("<!DOCTYPE html>\r\n<html lang=\"en\">\r\n  <head>\r\n    <meta charset=\"utf-8\">\r\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->\r\n    <title>Mobile APPs Automation</title>\r\n\t\r\n    <!-- Bootstrap -->\r\n    <link href=\"../../css/bootstrap.min.css\" rel=\"stylesheet\">\r\n\t<link href=\"../../css/style.css\" rel=\"stylesheet\">\r\n    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\r\n    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n    <!--[if lt IE 9]>\r\n      <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\r\n      <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n    <![endif]-->\r\n  </head>\r\n  <body>\r\n  \r\n\t\t<nav class=\"navbar navbar-inverse navbar-fixed-top\">\r\n\t\t\t<div class=\"container-fluid\">\r\n\t\t\t\t<!-- Brand and toggle get grouped for better mobile display -->\r\n\t\t\t\t<div class=\"navbar-header\">\r\n\t\t\t\t\t<a class=\"navbar-brand\" href=\"../../index.html\">Mobile APPs Automation</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div><!-- /.container-fluid -->\r\n\t\t</nav>\r\n  \r\n  \r\n\t\t<div>\r\n\r\n\t\t  <!-- Nav tabs -->\r\n\t\t  <ul class=\"nav nav-tabs\" role=\"tablist\">");
			int flag = 0;
			for(String platform : releaseStructure.getPlatforms()){
				releaseHtmlWriter.write("<li role=\"presentation\" class=\""+(flag++==0?"active":"")+"\"><a href=\"#"+platform+"\" aria-controls=\""+platform+"\" role=\"tab\" data-toggle=\"tab\">"+platform+"</a></li>");
			}
			releaseHtmlWriter.write("</ul>\r\n\r\n\t\t  <!-- Tab panes -->\r\n\t\t  <div class=\"tab-content\" style=\"margin-top: 20px;\">");
			
			File releaseJS = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+releaseStructure.getReleaseName()+"/"+releaseStructure.getReleaseName()+".js");
			releaseJS.createNewFile();
			BufferedWriter releaseJSWriter = new BufferedWriter(new FileWriter(releaseJS));
			releaseJSWriter.write("$(document).ready(function(){");
			
			flag = 0;
			for(String platform : releaseStructure.getPlatforms()){
				releaseHtmlWriter.write("<div role=\"tabpanel\" class=\"tab-pane "+(flag++==0?"active":"")+"\" id=\""+platform+"\">\r\n\t\t\t\t\r\n\t\t\t\t<table class=\"table table-hover\">\r\n\t\t\t\t\t<thead>\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<th>Component</th>\r\n\t\t\t\t\t\t\t<th>Scripts Executed</th>\r\n\t\t\t\t\t\t\t<th>Scripts Passed</th>\r\n\t\t\t\t\t\t\t<th>Scripts Failed</th>\r\n\t\t\t\t\t\t\t<th></th>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</thead>\r\n\t\t\t\t\t<tbody>");
				for(String module : releaseStructure.getModules()){
					releaseHtmlWriter.write("<tr>\r\n<td>"+module+"</td><td id=\""+platform+"_"+module+"_executed\"></td><td id=\""+platform+"_"+module+"_passed\"></td><td id=\""+platform+"_"+module+"_failed\"></td>\r\n<td>"
							+ "\n<button type=\"button\" class=\"btn btn-primary btn-xs\"  data-toggle=\"collapse\" data-target=\"#"+platform+"_"+module+"_reports\" aria-expanded=\"false\" aria-controls=\""+platform+"_"+module+"_reports\" id=\"button\">VIEW</button>"
							+ "\n<button type=\"button\" class=\"btn btn-success btn-xs\"  data-toggle=\"collapse\" data-target=\"#"+platform+"_"+module+"_issues\" aria-expanded=\"false\" aria-controls=\""+platform+"_"+module+"_issues\" id=\"button2\">ISSUES <span id=\""+platform+"_"+module+"_issues_status\" class=\"badge\"></span></button>" 
							+ "</td>\r\n</tr>\r\n"
							+ "<tr class=\"collapse\" id=\""+platform+"_"+module+"_reports\">\r\n<td colspan=\"4\"><div id=\""+platform+"_"+module+"\"></div><td>\r\n</tr>\r\n"
							+ "<tr class=\"collapse\" id=\""+platform+"_"+module+"_issues\">\r\n<td colspan=\"4\"><div  class=\"list-group\" id=\""+platform+"_"+module+"_issues_div\"></div><td>\r\n</tr>\r\n");
					releaseJSWriter.write("\r\n$(\"#"+platform+"_"+module+"_executed\").load(\""+platform+"/"+module+"_status.txt #total\");\r\n$(\"#"+platform+"_"+module+"_passed\").load(\""+platform+"/"+module+"_status.txt #pass\");\r\n$(\"#"+platform+"_"+module+"_failed\").load(\""+platform+"/"+module+"_status.txt #fail\");");
					releaseJSWriter.write("\r\n$(\"#"+platform+"_"+module+"\").load(\""+platform+"/"+module+".html\");");
					releaseJSWriter.write("\r\n$(\"#"+platform+"_"+module+"_issues_div\").load(\""+platform+"/"+module+"_issues.html\");");
					releaseJSWriter.write("\r\n$(\"#"+platform+"_"+module+"_issues_status\").load(\""+platform+"/"+module+"_issue_status.txt #issues\");");
				}
				releaseHtmlWriter.write("</tbody>\r\n</table>\r\n</div>");
			}
			releaseHtmlWriter.write("\r\n</div>\r\n</div>\r\n<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>\r\n<script src=\"../../js/bootstrap.min.js\"></script>\r\n</script>\r\n<script src=\""+releaseStructure.getReleaseName()+".js\"></script>\r\n</body>\r\n</html>");
			releaseJSWriter.write("\r\n});");
			releaseHtmlWriter.close();
			
			releaseJSWriter.close();
			
			File releaseTxt = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases.txt");
//			releaseTxt.;
			BufferedWriter releaseTxtWriter = new BufferedWriter(new FileWriter(releaseTxt, true));
			releaseTxtWriter.write("\r\n<a href=\"releases/"+releaseStructure.getReleaseName()+"/"+releaseStructure.getReleaseName()+".html\" class=\"list-group-item\">"+releaseStructure.getReleaseName()+"</a>");
			releaseTxtWriter.close();
			
			status = Status.getSuccessStatus("Release : "+releaseStructure.getReleaseName()+" created successfully");
		}
		catch(Exception e) {
			status = Status.getFailureStatus(msg);
		}
		return status;
	}
	
	public void createPlatform(String releaseName, String platform, List<String> modules) throws Exception{
		File f = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+releaseName+"/"+platform);
		if(!f.mkdir())
			throw new Exception("Could not create platform "+platform+" for release "+releaseName);
		else {
			for(String module : modules) {
				File moduleHtml = new File(f.getAbsolutePath()+"/"+module+".html");
				moduleHtml.createNewFile();
				File moduleIssueHtml = new File(f.getAbsolutePath()+"/"+module+"_issues.html");
				moduleIssueHtml.createNewFile();
				File moduleStatus = new File(f.getAbsolutePath()+"/"+module+"_status.txt");
				moduleStatus.createNewFile();
				BufferedWriter moduleStatusWriter = new BufferedWriter(new FileWriter(moduleStatus));
				moduleStatusWriter.write("<p id=\"total\">-</p>\n<p id=\"pass\">-</p>\n<p id=\"fail\">-</p>");
				moduleStatusWriter.close();
				File moduleIssueStatus = new File(f.getAbsolutePath()+"/"+module+"_issue_status.txt");
				moduleIssueStatus.createNewFile();
				BufferedWriter moduleIssueStatusWriter = new BufferedWriter(new FileWriter(moduleIssueStatus));
				moduleIssueStatusWriter.write("<p style=\"margin:0;\" id=\"issues\">0</p>");
				moduleIssueStatusWriter.close();
			}
		}
	}
	
	
	public Status updateReport(ModuleReport moduleReport){
		Status status = null;
		File moduleHtml = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+moduleReport.getReleaseName()+"/"+moduleReport.getPlatform()+"/"+moduleReport.getModuleName()+".html");
		try{
			File temp = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+moduleReport.getReleaseName()+"/"+moduleReport.getPlatform()+"/"+"temp.html");
			temp.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
			BufferedReader reader = new BufferedReader(new FileReader(moduleHtml));
			String line;
			while((line = reader.readLine()) != null){
				writer.write(line);
			}
			writer.close();
			reader.close();
			writer = new BufferedWriter(new FileWriter(moduleHtml));
			reader = new BufferedReader(new FileReader(temp));
			writer.write("<div class=\"well\">\r\n<h4>"+moduleReport.getHeading1()+"</h4>\r\n<h5>"+moduleReport.getHeading2()+"</h5>\r\n\r\n"+moduleReport.getReport()+"</div>");
			while((line = reader.readLine()) != null){
				writer.write(line);
			}
			writer.close();
			reader.close();
			temp.delete();
			File moduleStatus = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+moduleReport.getReleaseName()+"/"+moduleReport.getPlatform()+"/"+moduleReport.getModuleName()+"_status.txt");
			BufferedWriter moduleStatusWriter = new BufferedWriter(new FileWriter(moduleStatus));
			moduleStatusWriter.write("<p id=\"total\">"+moduleReport.getTotal()+"</p>\n<p id=\"pass\">"+moduleReport.getPassed()+"</p>\n<p id=\"fail\">"+moduleReport.getFailed()+"</p>");
			moduleStatusWriter.close();
			status = Status.getSuccessStatus("Report created successfully");
		}
		catch(Exception e){
			status = Status.getFailureStatus(e.getMessage());
		}
		
		return status;
	} 
	
	
	public Status updateIssue(ModuleIssue moduleIssue){
		Status status = null;
		File moduleHtml = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+moduleIssue.getReleaseName()+"/"+moduleIssue.getPlatform()+"/"+moduleIssue.getModuleName()+"_issues.html");
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(moduleHtml,true));
			writer.write("<a href=\""+moduleIssue.getLink()+"\" class=\"list-group-item\">"+moduleIssue.getTitle()+" - "+moduleIssue.getMsg()+"</a>");
			writer.close();
			status = Status.getSuccessStatus("Issue created successfully");
			File moduleIssueStatus = new File(System.getProperty("catalina.home")+"/webapps/Dashboard/releases/"+moduleIssue.getReleaseName()+"/"+moduleIssue.getPlatform()+"/"+moduleIssue.getModuleName()+"_issue_status.txt");
			BufferedReader reader = new BufferedReader(new FileReader(moduleIssueStatus));
			String line = reader.readLine();
			int diff = line.length()-37;
			line = line.substring(33, 33+diff);
			reader.close();
			int issues = Integer.parseInt(line);
			BufferedWriter moduleIssueStatusWriter = new BufferedWriter(new FileWriter(moduleIssueStatus));
			moduleIssueStatusWriter.write("<p style=\"margin:0;\" id=\"issues\">"+ (issues+1) +"</p>");
			moduleIssueStatusWriter.close();
		}
		catch(Exception e){
			status = Status.getFailureStatus(e.getMessage());
		}
		
		return status;
	} 
}
