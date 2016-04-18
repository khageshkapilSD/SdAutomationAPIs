package com.snapdeal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Interceptor3 extends GenericServlet {
	static LinkedHashSet<String> calls = new LinkedHashSet<String>();

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
//		response.setHeader("refresh", "2");
		String requestDesc = "";
		PrintWriter writer = response.getWriter();
		String localAddr = request.getLocalAddr();
		writer.println("<html><title>Device network calls</title><body>");
		String remoteAddr = request.getRemoteAddr();
		if(!remoteAddr.equals(localAddr)) {
			requestDesc += "<p><font color=\"red\">Request from</font> : "+request.getRemoteAddr()+", "
					+ "<font color=\"red\">host</font> : "+request.getRemoteHost()+" and <font color=\"red\">port</font> : "+request.getRemotePort()+"</p>";
//			request.
			requestDesc += "<p><font color=\"red\">Request protocol</font> : "+request.getProtocol()+"</p>";
//			requestDesc += "<p><font color=\"red\">Authentication scheme</font> : "+request.getContentType()+"</p>";
			requestDesc += "<p><font color=\"red\">Character Encoding</font> : "+request.getCharacterEncoding()+"</p>";
			requestDesc += "<p><font color=\"red\">Client preffered locale</font> : "+request.getLocale()+"</p>";
			requestDesc += "<p><font color=\"red\">Content Length</font> : "+request.getContentLength()+" Bytes, "
					+ "<font color=\"red\">Content Type</font> : "+request.getContentType()+"</p>";
//			requestDesc += "<p><font color=\"red\">Attributes</font> : "+getAttributesDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Headers</font> : "+getHeaderDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Parameters</font> : "+getParameterDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Query String</font> : "+request.getQueryString()+"</p>";
//			requestDesc += "<p><font color=\"red\">Request URL</font> : "+request.getRequestURI()+"</p>";
			//requestDesc += "<p><font color=\"red\">Cookies sent</font> : "+getCookieDetails(request)+"</p>";
			requestDesc += "<p><font color=\"red\">Request was going to</font> : "+request.getServerName()+" on its "
					+ "<font color=\"red\">port</font> : "+request.getServerPort()+" and "
							+ "<font color=\"red\">SSL</font> : "+request.isSecure()+"</p>";
			calls.add(requestDesc);
		}
		
		writer.println("<table border=\"1\">");
		for(String call : calls)
			writer.println("<tr><td>"+call+"</td></tr>");
		writer.println("</table>");
		writer.println("</body></html>");
		writer.flush();
		writer.close();
		
	}

	
	
}
