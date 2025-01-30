package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestswipefromLeftorright extends OpenKUKUFMapp {
	
	
	@Test
	public void test1() throws MalformedURLException, InterruptedException {
		
		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		Thread.sleep(30000);
		// Set implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		
		// Click on QAM home element
		WebElement QAMhome = driver
				.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali:id/ivIcon']"));
		QAMhome.click();
		
		swipeleftusingelement swipeleft = new swipeleftusingelement();
		swipeleft.swipeLeftUntilElementVisible(By.xpath("//android.widget.TextView[contains(@text,'Bedtime Tales')]"));
		 
		Thread.sleep(3000);

		
		swiperightusingelement swiperight = new swiperightusingelement();
		swiperight.swipeRight(By.xpath("//android.widget.TextView[@text='God's Wisdom']"));
		
		
		
		
	}

}
