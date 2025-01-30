package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class Dragtheplay extends OpenKUKUFMapp {

	@Test
	public void Dragtheplay() throws MalformedURLException, InterruptedException {
		// Launch the app using the inherited Openapp() method
		Openapp("","");

		// Create an explicit wait object
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the first thumbnail element to become visible and clickable
		WebElement firstThumbnail = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/iv_thumbnail'])[1]")));

		// Click on the first thumbnail
		firstThumbnail.click();

		Thread.sleep(3000);
		// Use SwipeUntilElementVisible to swipe until the "EP 2" element is visible
		SwipeUntilElementVisible swipe = new SwipeUntilElementVisible();
		swipe.swipeUpUntilElementVisible(By.xpath("//android.widget.TextView[contains(@text,'EP 2')]"), 5);

		// Click on the episode 2 text after waiting for it to be clickable
		WebElement episode2 = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[contains(@text,'EP 2')]")));
		episode2.click();

		// Wait for the overlay screen and click it
		WebElement overlayScreen = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//android.widget.FrameLayout[@resource-id='com.vlv.aravali.reels:id/exo_overlay']")));
		wait.until(ExpectedConditions.visibilityOf(overlayScreen));
		overlayScreen.click();

		WebElement Back = driver.findElement(By.xpath("//android.view.View[@content-desc='Back']"));

		wait.until(ExpectedConditions.elementToBeClickable(Back));
		Back.click();
       
		Thread.sleep(3000);
		WebElement miniplay = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Episode 2')]"));
		wait.until(ExpectedConditions.visibilityOf(miniplay));

		WebElement pauseminiplay = driver
				.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/play']"));
		pauseminiplay.click();

		wait.until(ExpectedConditions.elementToBeClickable(pauseminiplay));
		miniplay.click();

		Thread.sleep(3000);
		WebElement duration = driver.findElement(By.id("com.vlv.aravali.reels:id/exo_duration"));

		wait.until(ExpectedConditions.visibilityOf(duration));
		Thread.sleep(3000);
		String duration1 = duration.getText();
		System.out.println(duration1);

		WebElement position = driver.findElement(By.id("com.vlv.aravali.reels:id/exo_position"));
		wait.until(ExpectedConditions.visibilityOf(position));
		Thread.sleep(3000);
		String position1 = position.getText();
		System.out.println(position1);

		WebElement dragbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//android.widget.SeekBar[@resource-id='com.vlv.aravali.reels:id/exo_progress']")));

		SeekBarPointerInputTest seekbar=new SeekBarPointerInputTest();
		seekbar.moveSeekBar(dragbar, 0.90);
		
		
		
		

	}
}
