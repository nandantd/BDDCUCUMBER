package TestKUKUFM;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

public class Verifyswipepercentage extends OpenKUKUFMapp {

	
	@Test
	public void testswipe() throws MalformedURLException, InterruptedException {
		
		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");
		
		Thread.sleep(2000);
		Swipeupinpercentagewise swipeup=new Swipeupinpercentagewise();
	   swipeup.swipeUpWithPercentage(1, 0.40);
	}
}
