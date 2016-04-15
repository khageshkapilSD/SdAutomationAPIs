package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;

public class MAINCLASS {
	
	static String temp1 = "[\r\n  {\r\n    \"name\": \"table_name\",\r\n    \"columns\": [\"draw\", \"prepare\", \"process\", \"execute\"],\r\n    \"points\": [";
	static String temp2 = "]\r\n  }\r\n]";
	String DRAW="0", PREPARE="0", PROCESS="0", EXECUTE="0";
	
	
	public static void main(String[] args) throws JSONException, JsonProcessingException, IOException, InterruptedException, URISyntaxException {
		
		MAINCLASS m = new MAINCLASS();
		
		for(int i=0; i<10; i++) {
			Thread.sleep(2000);
			
			ArrayList<String[]> array = m.readArray();
			String measurements = "";
			String avgMeasurement = "";
			if(array.size() > 0){
				measurements = m.prepareMeasurements(array);
				avgMeasurement = m.prepareAverageMeasurement(array);
			}
			
			System.out.println(measurements);
			System.out.println();
			m.postRequest("http://10.20.86.83:8086/write?db=myDB&u=admin&p=admin", measurements);
			m.postRequest("http://10.20.86.83:8086/write?db=myDB&u=admin&p=admin", avgMeasurement);
		}
	}
	
	public String prepareMeasurements(ArrayList<String[]> data) {
		int size = data.size();
		String s = "";
		long time = System.currentTimeMillis();
		long del = 2000/(size+1);
		for(int i=0; i<size; i++) {
			String[] a = data.get(i);
			if(a.length == 5){
				DRAW = a[1];
				PREPARE = a[2];
				PROCESS = a[3];
				EXECUTE = a[4];
			}
			time += del;
			s = s + "frames " + "draw="+DRAW+ ",prepare="+PREPARE+ ",process="+PROCESS+ ",execute="+EXECUTE +" "+time+"000000"+"\n";
		}
		return s;
	}
	
	public String prepareAverageMeasurement(ArrayList<String[]> data) {
		int size = data.size();
		String s = "";
		float f_draw=0, f_prepare=0, f_process=0, f_execute=0;
		for(int i=0; i<size; i++) {
			String[] a = data.get(i);
			if(a.length == 5) {
				f_draw = f_draw + Float.valueOf(a[1]);
				f_prepare = f_prepare + Float.valueOf(a[2]);
				f_process = f_process + Float.valueOf(a[3]);
				f_execute = f_execute + Float.valueOf(a[4]);
			}
		}
		s = "Avg_Frames draw="+(f_draw/(size))+",prepare="+(f_prepare/(size+1))+ ",process="+(f_process/(size+1))+ ",execute="+(f_execute/(size+1));
		return s;
	}
	
	public void postRequest (String url1, String str) throws IOException, URISyntaxException {
		URI url = new URI(url1);
		HttpPost post = new HttpPost(url);
		HttpClient client = HttpClientBuilder.create().build();
		post.addHeader("Content-Type", "text");
		post.setEntity(new StringEntity(str));
		client.execute(post);
	}
	
	public ArrayList<String[]> readArray(){
		ArrayList<String[]> data = new ArrayList<String[]>();
		String command = "adb shell dumpsys gfxinfo com.snapdeal.main";
		String[] a;
		try
		{
		    Process process = Runtime.getRuntime().exec(command);
		    InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		    String line = null;
		    boolean flag = false;
		    while ( (line = br.readLine()) != null) {
		    	if(line.contains("Draw	Prepare	Process	Execute")) {
		    		line = br.readLine();
		    		flag = true;
		    	}
		    	if(line.contains("View hierarchy:"))
		    		flag = false;
		    	
		    	if (flag) {
		    		a = line.split("\t");
		    		if(a.length > 1){
		    			data.add(a);
		    		}
		    		
				}
		    }
		} catch (IOException e)
		{
		    e.printStackTrace();
		}
		return data;
	}
	
}
