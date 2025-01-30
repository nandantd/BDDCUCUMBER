package TestKUKUFM;
import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import dev.failsafe.Timeout;
import io.appium.java_client.android.AndroidDriver;

public class performSwipeactions extends OpenKUKUFMapp {

//	public static  AndroidDriver driver;

	@Test
	public void testpauseandplay() throws MalformedURLException, InterruptedException {

		OpenKUKUFMapp launchapp = new OpenKUKUFMapp();
		launchapp.Openapp("com.vlv.aravali.reels","com.vlv.aravali.master.ui.MasterActivity");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/iv_thumbnail'])[1]")));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/iv_thumbnail'])[1]")));

		driver.findElement(
				By.xpath("(//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/iv_thumbnail'])[1]"))
				.click();

//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/iv_landscape']"))
//				.click();

		SwipeUntilElementVisible swipe =new SwipeUntilElementVisible();
		swipe.swipeUpUntilElementVisible(By.xpath("(//androidx.compose.ui.platform.ComposeView//android.view.View[@content-desc='Episode thumbnail])[2]"), 30);
		
		driver.findElement(By.xpath("(//androidx.compose.ui.platform.ComposeView//android.view.View[@content-desc='Episode thumbnail])[2]")).click();
		
		

//		SwipeUpUsingSwipeCounts swipeup = new SwipeUpUsingSwipeCounts();
//		swipeup.swipeUpUsingSwipeCount(5);
//		SwipeDownUsingSwipeCounts swipedown = new SwipeDownUsingSwipeCounts();
//		swipedown.swipeDownUsingSwipeCount(5);
		
		
		

	}
}
