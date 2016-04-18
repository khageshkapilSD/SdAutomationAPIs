package com.snapdeal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.javascript.host.ReadableStream;
import com.snapdeal.model.Call;
import com.snapdeal.model.Device;
import com.snapdeal.model.SingletonCalls;
import com.snapdeal.service.SingletonService;




@SuppressWarnings("serial")
public class Interceptor extends HttpServlet {
	static LinkedList<String> calls = new LinkedList<String>();
	static LinkedList<String> resLL = new LinkedList<String>();
//	String res = "";

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		String localAddr = request.getLocalAddr();
		String remoteAddr = request.getRemoteAddr();
		if(!remoteAddr.equals(localAddr))
		{	
			String res = getResponse(request);
//			response.setContentType(request.getContentType());
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.write(res);
		}
		else
			showCalls(request, response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		String localAddr = request.getLocalAddr();
		String remoteAddr = request.getRemoteAddr();
		if(!remoteAddr.equals(localAddr))
		{
			String res = postResponse(request,response);
//			response.setContentType(request.getContentType());
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.write(res);
		}
		else	
			showCalls(request, response);
	}


	public void showCalls(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		SingletonCalls instance = SingletonCalls.getInstance();
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
//		String localAddr = request.getLocalAddr();
		writer.println("<html><title>Device network calls</title><body>");
		writer.println("<table border=\"1\">");
		HashMap<String, Device> devices = instance.getDevices();
		for(Entry<String, Device> e : devices.entrySet()){
			Device d = e.getValue();
			LinkedList<Call> c = d.getCalls();
			for(Call call : c){
				writer.println("<tr>");
				writer.println("<td>"+d.getDeviceId()+"</td>");
				writer.println("<td>"+d.getApsalarId()+"</td>");
				writer.println("<td>"+call.getRequestType()+"</td>");
				writer.println("<td>"+call.getRequestURL()+"</td>");
				writer.println("<td>"+call.getRequestParameters()+"</td>");
				writer.println("<td>"+call.getTimeStamp()+"</td>");
				writer.println("</tr>");
			}
		}
		writer.println("</table>");
		writer.println("</body></html>");
		writer.flush();
		writer.close();
	}
	
//	public void showCalls(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
//		response.setContentType("text/x-json;charset=UTF-8");
//		response.setContentType("text/html");
//		response.setHeader("refresh", "2");
//		String requestDesc = "";
//		PrintWriter writer = response.getWriter();
//		String localAddr = request.getLocalAddr();
//		writer.println("<html><title>Device network calls</title><body>");
//		String remoteAddr = request.getRemoteAddr();
//		if(!remoteAddr.equals(localAddr)) {
//			requestDesc += "<p><font color=\"red\">Request from</font> : "+request.getRemoteAddr()+", "
//					+ "<font color=\"red\">host</font> : "+request.getRemoteHost()+" and <font color=\"red\">port</font> : "+request.getRemotePort()+"</p>";
//			requestDesc += "<p><font color=\"red\">Request protocol</font> : "+request.getProtocol()+"</p>";
//			requestDesc += "<p><font color=\"red\">Authentication scheme</font> : "+request.getAuthType()+"</p>";
//			requestDesc += "<p><font color=\"red\">Character Encoding</font> : "+request.getCharacterEncoding()+"</p>";
//			requestDesc += "<p><font color=\"red\">Client preffered locale</font> : "+request.getLocale()+"</p>";
//			requestDesc += "<p><font color=\"red\">Content Length</font> : "+request.getContentLength()+" Bytes, "
//					+ "<font color=\"red\">Content Type</font> : "+request.getContentType()+"</p>";
//			requestDesc += "<p><font color=\"red\">Attributes</font> : "+getAttributesDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Headers</font> : "+getHeaderDetails(request)+"</p>";
//			StringBuffer jb = new StringBuffer();
//			  String line = null;
//			  try {
//			    BufferedReader reader = request.getReader();
//			    while ((line = reader.readLine()) != null)
//			      jb.append(line);
//			  } catch (Exception e) { /*report an error*/ }
//			  
//			String pNames = jb.toString();
//			 
//			requestDesc += "<p><font color=\"red\">Parameters</font> : "+pNames+"</p>";
//			//requestDesc += "<p><font color=\"red\">Cookies sent</font> : "+getCookieDetails(request)+"</p>";
//			requestDesc += "<p><font color=\"red\">Request was going to</font> : "+request.getServerName()+" on its "
//					+ "<font color=\"red\">port</font> : "+request.getServerPort()+" and "
//							+ "<font color=\"red\">SSL</font> : "+request.isSecure()+"</p>";
//			String urlText = request.getRequestURL()+"?"+request.getQueryString();
//			requestDesc += "<p><font color=\"red\">URL</font> : "+urlText+"</p>";
//			requestDesc+=urlText;
//			calls.add(requestDesc);
//			calls.add(urlText);
//			getResponse(request);
//			
//		}
//
//		getResponse("https://mobileapi.snapdeal.com/service/get/recommended/getTrendingProducts?start=0&count=10");
//
//		writer.println("<table border=\"1\">");
//		for(String call : calls)
//			writer.println("<tr><td>"+call+"</td></tr>");
//		writer.println("</tr><tr>");
//		for(String res : resLL)
//			writer.println("<td>"+res+"</td>");
//		writer.println("</table>");
//		writer.println("</body></html>");
//		writer.flush();
////		writer.close();
//		writer.println(res);
//		RequestDispatcher rd = request.getRequestDispatcher("");
//		rd.forward(request, response);
//		
//	}
	
	public String getResponse(HttpServletRequest request)
	{
		Call call = new Call();
		String deviceId = request.getHeader("deviceid");
		String deviceIp = request.getRemoteAddr();
		call.setRequestType("GET");
		String urlText = request.getRequestURL()+"?"+request.getQueryString();
		call.setRequestURL(request.getRequestURL().toString());
		call.setRequestParameters(request.getQueryString());
//		call.setDeviceId(request.getHeader("deviceid"));
		String res = "";
		
		String mockedResponse = getMockingResponse(deviceId, call);
		if(mockedResponse!=null){
			res = mockedResponse;
		}
		else{	
		try {
			URL url = new URL(urlText);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			Enumeration<String> headers = request.getHeaderNames();
			while(headers.hasMoreElements()) {
				String header = headers.nextElement();
				if(header.equals("accept-encoding"))
					continue;
				conn.setRequestProperty(header, request.getHeader(header));
			}
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			String output;			
			while ((output = br.readLine()) != null) {
				res = res+output;
			}
			
			
			conn.disconnect();			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		}
//		res = res.replaceAll("https://", "http://");
		
		call.setResponse(res);
		insertCall(deviceId,call);
		return res;
	}
	
	
		
	public String postResponse(HttpServletRequest request, HttpServletResponse originalResponse)
	{
		Call call = new Call();
		call.setRequestType("POST");
		String deviceId = request.getHeader("deviceid");
		String apsalarId = request.getHeader("u");
		String deviceIp = request.getRemoteAddr();
		String urlText = request.getRequestURL().toString();
		call.setRequestURL(urlText);
		String res="";
		StringBuffer jb = new StringBuffer();
		String line = "";
		String encoding = request.getHeader("content-encoding");
//		call.setRequestType("POST "+encoding);
		
		try {  
			BufferedReader reader;
			if(encoding!=null && encoding.equalsIgnoreCase("gzip")){
				GZIPInputStream gis = new GZIPInputStream(request.getInputStream());
				reader = new BufferedReader(new InputStreamReader(gis,"UTF-8"));
			}
			else{
				InputStream requsetInStream = request.getInputStream();
				reader = new BufferedReader(new InputStreamReader(requsetInStream));
			}
			
			while ((line = reader.readLine()) != null)
		      jb.append(line);

			
			
			call.setRequestParameters(jb.toString());
			
		} catch (Exception e) {  }
				
		
		String mockedResponse = getMockingResponse(deviceId, call);
		if(mockedResponse!=null){
			res = mockedResponse;
		}
		else{	
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(urlText);
			try {
				StringEntity params =new StringEntity(jb.toString());
	//			String params = jb.toString();
//				post.addHeader("content-type", "application/json;charset=UTF-8");
				Enumeration<String> headers = request.getHeaderNames();
				while(headers.hasMoreElements()) {
					String header = headers.nextElement();
					if(header.equals("accept-encoding")||header.equals("content-length"))
						continue;
					post.addHeader(header, request.getHeader(header));
				}
				post.setEntity(params);
				HttpResponse response = client.execute(post);
	
				BufferedReader rd = new BufferedReader(
				                new InputStreamReader(response.getEntity().getContent()));
	
				StringBuffer result = new StringBuffer();
				line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
				res = result.toString();
				for(Header h : response.getAllHeaders()){
					originalResponse.addHeader(h.getName(), h.getValue());
				}
//				originalResponse.setContentType("text/html");
//				PrintWriter writer = originalResponse.getWriter();
//				writer.write(res);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		res = res.replaceAll("https://", "http://");
		
		call.setResponse(res);
		insertCall(deviceId,call);
		
		
		
		return res;
		
	}

	
	public String getMockingResponse(String deviceId, Call call){
		if(deviceId!=null){
			return SingletonService.getMockedResponce(deviceId, call);
		}
		else{
			return null;
		}
	}

	public void insertCall(String deviceId, Call call){
		if(deviceId!=null){
			SingletonService.addCall(deviceId, call);
		}
		else{
			if(call.getRequestURL().contains("eventDataV2")){
				try {
					JSONObject jobj = new JSONObject(call.getRequestParameters());
					String dId = jobj.optString("deviceId");
					if(dId!=null && !dId.equals("")){
						SingletonService.addCall(dId, call);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			else if(call.getRequestURL().contains("e.apsalar.com")){
				String str = call.getRequestParameters();
				String apsalarId = "";
				String[] arr1 = str.split("&");
				for(String s : arr1){
					String[] arr2 = s.split("=");
					if(arr2[0].equalsIgnoreCase("u")){
						apsalarId = arr2[1];
						break;
					}
				}
				if(apsalarId!=null){
					String dId = SingletonService.getDeviceIdForApsalarId(apsalarId);
					if(dId!=null)
						SingletonService.addCall(dId, call);
				}
			}
		}
	}
}
