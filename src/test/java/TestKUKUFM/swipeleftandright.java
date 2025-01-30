package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;

import org.testng.annotations.Test;

public class swipeleftandright extends OpenKUKUFMapp {

	@Test
	public void swipeleftandright1() throws MalformedURLException, InterruptedException {
		// TODO Auto-generated method stub
		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		swipeleftusingCounts swipeleft = new swipeleftusingCounts();
		swipeleft.swipeLeftToRight(10, 851, 320, 522, 526);

		Thread.sleep(2000);

		Swiperight swiperight = new Swiperight();
		swiperight.swipeLeftToRight(10, 202, 776, 487, 474);
        
	}

}
