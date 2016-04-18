package com.snapdeal;




import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.proxy.ProxyServlet;


@SuppressWarnings("serial")
public class Interceptor4 extends ProxyServlet {

	
	 @Override
	    protected URI rewriteURI(HttpServletRequest request) {
	        // Forward all requests to another port on this machine
	        String uri = "http://localhost:8060";

	        // Take the current path and append it to the new url
	        String path = request.getRequestURI();
	        uri += path;

	        // Add query params
	        String query = request.getQueryString();
	        if (query != null && query.length() > 0) {
	            uri += "?" + query;
	        }
	        return URI.create(uri).normalize();
	    }
}
