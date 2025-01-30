package TestKUKUFM;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class OpenKUKUFMapp {
	public static AppiumDriver driver;

	@Test
	public void Openapp(String appPackage, String appActivity) throws MalformedURLException {

		UiAutomator2Options options = new UiAutomator2Options();
		options.setUdid("RZ8TA0PDHLX");
		options.setDeviceName("A13");
		options.setPlatformName("Android");
		options.setPlatformVersion("14");
		options.setAppPackage(appPackage);
		options.setAppActivity(appActivity);
		options.setAutomationName("UiAutomator2");
		options.setCapability("noReset", false);
		options.setAutoGrantPermissions(true);
		
		driver = new AppiumDriver(options);
		
	
		

	}

}
