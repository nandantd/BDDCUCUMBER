package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class VerifyGenresInSearch extends OpenKUKUFMapp {

	public WebDriverWait wait;

	@Test
	public void genres() throws InterruptedException, MalformedURLException {
		// Locate the search bar and wait until it is visible

		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		Thread.sleep(20000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement searchbar = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Search on Kuku\"]")));
		searchbar.click();

		Thread.sleep(2000);
		// Locate the inner search bar, then go back
		driver.navigate().back();
		

		// Get genres displayed on the first view
		List<WebElement> genres = driver.findElements(By.xpath(
				"//android.widget.GridView//android.widget.ImageView/following-sibling::android.widget.TextView[1]"));

		ArrayList<String> genreList = new ArrayList<>();
		for (WebElement genre : genres) {
			genreList.add(genre.getText());
		}

		// Swipe up to load more genres
		new SwipeUpUsingSwipeCounts().swipeUpUsingSwipeCount(1);
		Thread.sleep(4000);

		// Find genres again after swipe
		List<WebElement> genresAfterSwipe = driver.findElements(By.xpath(
				"//android.widget.GridView//android.widget.ImageView/following-sibling::android.widget.TextView[1]"));
		for (WebElement genre : genresAfterSwipe) {
			genreList.add(genre.getText());
		}

		// Remove duplicates and sort the genre list
		ArrayList<String> finalList = new ArrayList<>(new TreeSet<>(genreList));
		System.out.println("Sorted genres without duplicates: " + finalList);

		// Initialize swipe helpers
		SwipeUntilElementVisible swipeUp = new SwipeUntilElementVisible();
		SwipeDownUsingSwipeCounts swipeDown = new SwipeDownUsingSwipeCounts();

		for (String currentGenre : finalList) {
			// Skip genres containing "Bytes"
			if (currentGenre.equalsIgnoreCase("Bytes")) {
				System.out.println("Skipping genre: Bytes");
				continue;
			}

			swipeDown.swipeDownUsingSwipeCount(1);
			Thread.sleep(2000);
			

			System.out.println("Inner loop, checking for genre: " + currentGenre);

			By genreLocator = By.xpath(
					"//android.widget.GridView//android.widget.ImageView/following-sibling::android.widget.TextView[contains(@text,'"
							+ currentGenre + "')]");

			boolean isDisplayed = false;
			try {
				WebElement genreElement = driver.findElement(genreLocator);
				isDisplayed = genreElement.isDisplayed();
			} catch (Exception e) {
				// Element not found; continue to swipe up
			}

			if (isDisplayed) {
				wait.until(ExpectedConditions.elementToBeClickable(genreLocator)).click();
				Thread.sleep(3000);
			} else {
				swipeUp.swipeUpUntilElementVisible(genreLocator, 2);
				wait.until(ExpectedConditions.elementToBeClickable(genreLocator)).click();
				Thread.sleep(3000);
			}

			WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//android.widget.ImageView[@content-desc='Banner Thumbnail'])[2]")));
			banner.click();

			WebElement subtitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[@text='"+currentGenre+"']")));
			String subtitle = subtitleText.getText();

			// Split subtitle and verify the genre
			
			//String[] subtitleParts = subtitle.split("•");
			//String displayedGenre = subtitleParts[1].trim();
			Assert.assertEquals(subtitle, currentGenre, "Displayed genre does not match the expected genre.");

			driver.navigate().back();
			Thread.sleep(1000);
			driver.navigate().back();
      		Thread.sleep(1000);
      		driver.navigate().back();
			
		}
	}
}
