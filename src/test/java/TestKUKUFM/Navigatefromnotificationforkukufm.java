package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Navigatefromnotificationforkukufm extends OpenKUKUFMapp {

	@Test
	public void testnotification() throws MalformedURLException, InterruptedException {

		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Create an explicit wait object
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the first thumbnail element to become visible and clickable
		WebElement firstThumbnail = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//android.widget.ImageView[@resource-id=\"com.vlv.aravali:id/thumbnail\"])[2]")));

		// Click on the first thumbnail
		firstThumbnail.click();

		Thread.sleep(3000);

		// Use SwipeUntilElementVisible to swipe until the "EP 2" element is visible
		SwipeUntilElementVisible swipe = new SwipeUntilElementVisible();
		swipe.swipeUpUntilElementVisible(
				By.xpath(
						"(//android.view.View[@content-desc=\"Episode thumbnail\"]/..//android.widget.TextView[1])[5]"),
				5);

		// Click on the episode 2 text after waiting for it to be clickable
		WebElement episode2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"(//android.view.View[@content-desc=\"Episode thumbnail\"]/..//android.widget.TextView[1])[5]")));
		episode2.click();

		WebElement showname = driver.findElement(By.xpath(
				"//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View/child::android.widget.TextView"));
		Thread.sleep(4000);
		String showtext = showname.getText();
		System.out.println(showtext);

		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(5000);
		
		WebElement pauseminiplay = driver
				.findElement(By.xpath("//android.widget.ImageView[@resource-id=\"com.vlv.aravali:id/play\"]"));

		try {

			if (pauseminiplay.isDisplayed()) {

				wait.until(ExpectedConditions.elementToBeClickable(pauseminiplay));
				pauseminiplay.click();
				// driver.pressKey(new KeyEvent(AndroidKey.HOME));
				driver.runAppInBackground(Duration.ofSeconds(10)); // Duration in seconds

				driver.openNotifications();
				Thread.sleep(3000);
			} else {
				WebElement closebottomplayer = driver
						.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"close bottom player\"]"));

				// driver.pressKey(new KeyEvent(AndroidKey.HOME));
				driver.runAppInBackground(Duration.ofSeconds(10)); // Duration in seconds

				driver.openNotifications();

			}

		} catch (Exception e) {

			System.out.println("get e message" + e);

		}
		Thread.sleep(2000);
		WebElement notifiicationshowname = driver.findElement(
				By.xpath("//android.widget.TextView[@resource-id='com.android.systemui:id/sec_header_title']"));
		wait.until(ExpectedConditions.visibilityOf(notifiicationshowname));
		String notifiicationplayname = notifiicationshowname.getText();
		System.out.println("notifiicationplayname" + notifiicationplayname);

		Thread.sleep(2000);
		System.out.println(" launching through notification");
		notifiicationshowname.click();	
		Thread.sleep(2000);
       Assert.assertEquals(showtext, notifiicationplayname);
		
		int retries = 3;
		while (retries > 0) {
			try {
				WebElement element = driver.findElement(By
						.xpath("//android.widget.TextView[@resource-id='com.android.systemui:id/sec_header_title']"));
				element.isDisplayed();
				wait.until(ExpectedConditions.visibilityOf(element));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Assert.assertEquals(showtext, notifiicationplayname);

				break; // Break out of the loop if no exception
			} catch (NoSuchElementException e) {
				retries--;
				if (retries == 0) {
					throw e; // Rethrow the exception if retries are exhausted
				}
			}
		}

	}

}
