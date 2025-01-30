package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class NavigateshowscreenfromNotificationbar extends OpenKUKUFMapp {

	@Test
	public void playfromnotification() throws MalformedURLException, InterruptedException {

		// Launch the app using the inherited Openapp() method
		Openapp("com.vlv.aravali.reels","com.vlv.aravali.master.ui.MasterActivity");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Create an explicit wait object
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		

		// Wait for the first thumbnail element to become visible and clickable
		WebElement firstThumbnail = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//android.widget.ImageView[@resource-id=\"com.vlv.aravali.reels:id/ivThumb\"])[1]")));

		Thread.sleep(8000);
		// Click on the first thumbnail
		firstThumbnail.click();

		Thread.sleep(3000);
		
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		
		Thread.sleep(3000);
		
		// Use SwipeUntilElementVisible to swipe until the "EP 2" element is visible
		SwipeUntilElementVisible swipe = new SwipeUntilElementVisible();
		swipe.swipeUpUntilElementVisible(By.xpath("(//android.view.View[@content-desc=\"Episode thumbnail\"]/..//android.widget.TextView[1])[5]"), 5);

		// Click on the episode 2 text after waiting for it to be clickable
		WebElement episode2 = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//android.view.View[@content-desc=\"Episode thumbnail\"]/..//android.widget.TextView[1])[5]")));
		episode2.click();

		// Wait for the overlay screen and click it
		Thread.sleep(2000);
		WebElement overlayScreen = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//android.widget.FrameLayout[@resource-id='com.vlv.aravali.reels:id/exo_overlay']")));
		wait.until(ExpectedConditions.visibilityOf(overlayScreen));
		overlayScreen.click();

		Thread.sleep(5000);
		WebElement episodenametitle = driver.findElement(By.xpath(
				"//androidx.compose.ui.platform.ComposeView/android.view.View/child::android.view.View[2]/child::android.widget.TextView"));
		
        wait.until(ExpectedConditions.visibilityOf(episodenametitle));
		String episodenameinoverlay = episodenametitle.getText();
		System.out.println("episodenametitle" + episodenameinoverlay);

		driver.pressKey(new KeyEvent(AndroidKey.BACK));

		WebElement closebottomplayer = driver
				.findElement(By.xpath("//android.widget.ImageView[@content-desc='close bottom player']"));

		if (closebottomplayer.isDisplayed()) {
			//driver.pressKey(new KeyEvent(AndroidKey.HOME));
			driver.runAppInBackground(Duration.ofSeconds(10)); // Duration in seconds

			driver.openNotifications();

		} else {
			WebElement pauseminiplay = driver
					.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali.reels:id/play']"));
			wait.until(ExpectedConditions.elementToBeClickable(pauseminiplay));
			pauseminiplay.click();
			//driver.pressKey(new KeyEvent(AndroidKey.HOME));
			driver.runAppInBackground(Duration.ofSeconds(10)); // Duration in seconds

			driver.openNotifications();
		}

		Thread.sleep(2000);
		WebElement notifiicationshowname = driver.findElement(
				By.xpath("//android.widget.TextView[@resource-id=\"com.android.systemui:id/header_title\"]"));
		wait.until(ExpectedConditions.visibilityOf(notifiicationshowname));
		String notifiicationplayname = notifiicationshowname.getText();
		System.out.println("notifiicationplayname"+ notifiicationplayname);

		Thread.sleep(2000);
		notifiicationshowname.click();
				System.out.println(" launching through notification");   
				
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement element = driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/child::android.view.View[2]/child::android.widget.TextView"));
                element.isDisplayed();
                Assert.assertEquals(episodenameinoverlay, element.getText());
                
                break;  // Break out of the loop if no exception
            } catch (StaleElementReferenceException e) {
                retries--;
                if (retries == 0) {
                    throw e;  // Rethrow the exception if retries are exhausted
                }
            }
        }


	}

}
