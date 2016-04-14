package packageName;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.snapdeal.suits.MainClass;

public class TestClass {
	
	public static AndroidDriver driver;
	
	public void abc() {
		new inner();
	}
	
	class inner {
		public inner() {
			sysout();
		}
		private void sysout() {
			System.out.println("inner class printing");

		}
	}

	public static void main(String[] args) throws MalformedURLException {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "5.1");
//		capabilities.setCapability("platformVersion", "8.3");
		capabilities.setCapability("udid", "ZX1D638C8P");
//		capabilities.setCapability("app","/Users/khagesh.kapil/Downloads/SnapdealApp-debug.apk");
//		capabilities.setCapability("app","/Users/khagesh.kapil/Documents/OldDocuments/Repositories/snapdeal-ios-automation/TestSnapdealAutomation/app/Snapdeal-debug-5.4.2.apk");
		capabilities.setCapability("appPackage", "com.snapdeal.main");
		capabilities.setCapability("appActivity","com.snapdeal.ui.material.activity.MaterialMainActivity");
		capabilities.setCapability("deviceName", "Android");
//		capabilities.setCapability("deviceName", "iPhone 6");
		capabilities.setCapability("browserName", "");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("hh:mm:ss:SSS");
		
		for(int i=0; i<5; i++) {
			
			driver.findElement(By.name("Skip"));
			driver.resetApp();
		}
		
		
	}
	
	
}
