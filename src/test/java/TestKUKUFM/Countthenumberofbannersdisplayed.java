package TestKUKUFM;

import java.io.IOException;

import javax.imageio.stream.ImageInputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Countthenumberofbannersdisplayed extends OpenKUKUFMapp {

	@Test
	public void countbanners() throws IOException {

		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		WebElement Bannerimage = driver.findElement(By.xpath(
				"//androidx.recyclerview.widget.RecyclerView[@resource-id='com.vlv.aravali:id/rcvBanners']/child::android.view.ViewGroup[@resource-id='com.vlv.aravali:id/parentCl'][2]"));

		ClickAndHoldOnElement clickandhold=new ClickAndHoldOnElement();
		clickandhold.clickAndHold(Bannerimage);
		
		Capturescreenshotofimages screenshot=new Capturescreenshotofimages();
		screenshot.getscreenshot(Bannerimage);
		
		
		
	}

}
