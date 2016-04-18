package com.snapdeal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Interceptor2 extends HttpServlet {
	static LinkedHashSet<String> calls = new LinkedHashSet<String>();

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		showCalls(request, response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		showCalls(request, response);
	}

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
			requestDesc += "<p><font color=\"red\">Request protocol</font> : "+request.getProtocol()+"</p>";
			requestDesc += "<p><font color=\"red\">Authentication scheme</font> : "+request.getAuthType()+"</p>";
			requestDesc += "<p><font color=\"red\">Character Encoding</font> : "+request.getCharacterEncoding()+"</p>";
			requestDesc += "<p><font color=\"red\">Client preffered locale</font> : "+request.getLocale()+"</p>";
			requestDesc += "<p><font color=\"red\">Content Length</font> : "+request.getContentLength()+" Bytes, "
					+ "<font color=\"red\">Content Type</font> : "+request.getContentType()+"</p>";
			requestDesc += "<p><font color=\"red\">Attributes</font> : "+getAttributesDetails(request)+"</p>";
			requestDesc += "<p><font color=\"red\">Headers</font> : "+getHeaderDetails(request)+"</p>";
			requestDesc += "<p><font color=\"red\">Parameters</font> : "+getParameterDetails(request)+"</p>";
			requestDesc += "<p><font color=\"red\">Query String</font> : "+request.getQueryString()+"</p>";
			requestDesc += "<p><font color=\"red\">Request URL</font> : "+request.getRequestURI()+"</p>";
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
	
	/*public String getCookieDetails(HttpServletRequest request) {
		String cookiesData = "";
		Cookie[] cookies = request.getCookies();
		if(cookies.length==0)
			cookiesData = "No cookies sent";
		for(Cookie cookie : cookies) {
			cookiesData += cookie.getName()+" : "+cookie.getValue()+"</br>";
		}
		return cookiesData;
	}*/
	
	public String getAttributesDetails(HttpServletRequest request) {
		String attributesData = "";
		Enumeration<String> attributes = request.getAttributeNames();
		while(attributes.hasMoreElements()) {
			String attribute = attributes.nextElement();
			attributesData += attribute+" : "+request.getAttribute(attribute)+"</br>";
		}
		if(attributesData.equals(""))
			attributesData += "No attributes sent";
		return attributesData;
	}
	
	public String getParameterDetails(HttpServletRequest request) {
		String parameterData = "";
		Map<String,String[]> cookies = (Map<String,String[]>) request.getParameterMap();
		Set<Map.Entry<String,String[]>> entrySet = cookies.entrySet();
		Iterator<Entry<String, String[]>> itr = entrySet.iterator();
		while(itr.hasNext()) {
			Map.Entry<String,String[]> parameterEntry = itr.next();
			parameterData += parameterEntry.getKey()+" : [";
			for(String value : parameterEntry.getValue())
				parameterData +=value+",";
			parameterData +="]</br>";
		}
		if(parameterData.equals(""))
			parameterData = "No parameters sent";
		return parameterData;
	}
	
	public String getHeaderDetails(HttpServletRequest request) {
		String headerData = "";
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			headerData += header+" : "+request.getHeader(header)+"</br>";
		}
		if(headerData.equals(""))
			headerData += "No headers sent";
		return headerData;
	}
	
}
