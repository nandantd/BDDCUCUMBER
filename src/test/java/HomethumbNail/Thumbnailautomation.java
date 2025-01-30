package HomethumbNail;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import TestKUKUFM.OpenKUKUFMapp;
import TestKUKUFM.swipeleftusingCounts;

public class Thumbnailautomation extends OpenKUKUFMapp {

	@Test
	public void testHomeThumbnail() throws MalformedURLException, InterruptedException {

		// Open the application
		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		// Wait for the app to load completely
		Thread.sleep(30000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize swipe helper
		swipeleftusingCounts swipeHelper = new swipeleftusingCounts();

		// Set to track the titles of thumbnails that have already been processed
		Set<String> processedTitles = new HashSet<>();

		// Locate all thumbnails
		List<WebElement> thumbnails = driver
				.findElements(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali:id/thumbnail']"));

		int index = 0;
		while (index < thumbnails.size()) {
			try {
				// Refresh the list of thumbnails to avoid stale element reference
				Thread.sleep(2000);
				WebElement thumbnail = driver.findElement(
						By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali:id/thumbnail']"));

				// Wait for visibility and clickability
				wait.until(ExpectedConditions.visibilityOf(thumbnail));
				wait.until(ExpectedConditions.elementToBeClickable(thumbnail));

				// Click the thumbnail
				thumbnail.click();

				// Attempt to retrieve and print the show title
				String showTitleText = null;
				try {
					WebElement showTitle = driver.findElement(By
							.xpath("//android.widget.TextView[@resource-id='com.vlv.aravali:id/toolbar_show_title']"));
					wait.until(ExpectedConditions.visibilityOf(showTitle));
					showTitleText = showTitle.getText();
					System.out.println("Show Title: " + showTitleText);
				} catch (Exception e) {
					System.out.println("Show Title not found. Attempting fallback elements...");
					try {
						WebElement showText = driver.findElement(By.xpath(
								"//android.widget.ImageButton[@content-desc='Navigate back']/following-sibling::android.widget.TextView"));
						wait.until(ExpectedConditions.visibilityOf(showText));
						showTitleText = showText.getText();
						System.out.println("Show Text: " + showTitleText);
					} catch (Exception e1) {
						System.out.println("Fallback element 'Show Text' not found.");
						try {
							WebElement referNow = driver
									.findElement(By.xpath("//android.widget.TextView[@text='Refer Now']"));
							wait.until(ExpectedConditions.visibilityOf(referNow));
							showTitleText = referNow.getText();
							System.out.println("Fallback Text: " + showTitleText);
						} catch (Exception e2) {
							System.out.println("Fallback element 'Refer Now' not found.");
						}
					}
				}

				// If the same title has been processed, swipe to the next thumbnail
				if (processedTitles.contains(showTitleText)) {
					System.out.println("Duplicate title found: " + showTitleText + ". Swiping to the next thumbnail.");
					driver.navigate().back();
					swipeHelper.swipeLeftToRight(index + 1, 785, 237, 557, 544);
					Thread.sleep(2000);
					continue; // Skip to the next thumbnail without increasing the index
				}

				// Mark the current show title as processed
				processedTitles.add(showTitleText);

				// Navigate back to the main screen
				driver.navigate().back();
				Thread.sleep(2000);


				// Move to the next thumbnail
				Thread.sleep(2000);
				swipeHelper.swipeLeftToRight(1, 785, 237, 557, 544);
				index++;

			} catch (Exception e) {

				System.out.println("Thumbnail not visible at index " + index + ". Swiping...");
				Thread.sleep(2000);
			}
		}
		System.out.println(processedTitles);
		System.out.println("Test completed: All thumbnails processed.");
	}
}
