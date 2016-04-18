package com.snapdeal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.TextUtils;


@SuppressWarnings("serial")
public class GenericInterceptor extends GenericServlet {
	static LinkedHashSet<String> calls = new LinkedHashSet<String>();
	static List<Boolean> protocolsList = new ArrayList<Boolean>();

//	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
////		showCalls(request, response);
//		PrintWriter writer = response.getWriter();
////		writer.println(getHttpGetResponse(request));
//		writer.flush();
//		writer.close();
//	}
//
//	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
//		showCalls(request, response);
//	}

	public void showCalls(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			requestDesc += "<p><font color=\"red\">Request URL</font> : "+request.getRequestURL()+"</p>";
			requestDesc += "<p><font color=\"red\">Request protocol</font> : "+request.getProtocol()+"</p>";
			requestDesc += "<p><font color=\"red\">Authentication scheme</font> : "+request.getAuthType()+"</p>";
			requestDesc += "<p><font color=\"red\">Character Encoding</font> : "+request.getCharacterEncoding()+"</p>";
			requestDesc += "<p><font color=\"red\">Client preffered locale</font> : "+request.getLocale()+"</p>";
			requestDesc += "<p><font color=\"red\">Content Length</font> : "+request.getContentLength()+" Bytes, "
					+ "<font color=\"red\">Content Type</font> : "+request.getContentType()+"</p>";
//			requestDesc += "<p><font color=\"red\">Attributes</font> : "+getAttributesDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Headers</font> : "+getHeaderDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Parameters</font> : "+getParameterDetails(request)+"</p>";
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
	
	

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		PrintWriter writer = arg1.getWriter();
		
		writer.println("<table><tr><td>");
		Enumeration attrNames = arg0.getAttributeNames();
		while(attrNames.hasMoreElements()) {
			String attr = attrNames.nextElement().toString();
			writer.println("<p>"+attr+" : "+arg0.getAttribute(attr)+"</p>");
		}
		
		writer.println("<p>content type : "+arg0.getContentType()+"</p>");
		writer.println("<p>Locale : "+arg0.getLocale()+"</p>");
		
		Enumeration paramNames = arg0.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String param = paramNames.nextElement().toString();
			writer.println("<p>"+param+" : "+arg0.getParameter(param)+"</p>");
		}
		
		writer.println("<p>Protocol : "+arg0.getProtocol()+"</p>");
		writer.println("<p>Scheme : "+arg0.getScheme()+"</p>");
		protocolsList.add(arg0.isSecure());
		for(Boolean p : protocolsList){
			writer.println("<p>protocol : "+p+"</p>");
		}
		writer.println("<p>");
		BufferedReader reader = arg0.getReader();
		String line = null;
		while((line=reader.readLine())!=null) {
			writer.println(line);
		}
		writer.println("</p>");
			
		writer.println("</td></tr></table>");
		
		showCalls((HttpServletRequest)arg0, (HttpServletResponse)arg1);
		return;
	}
}
