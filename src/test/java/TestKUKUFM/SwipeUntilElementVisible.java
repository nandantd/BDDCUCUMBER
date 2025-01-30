package TestKUKUFM;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SwipeUntilElementVisible extends OpenKUKUFMapp {



	// Method to swipe up on the screen
	@Test
	public void swipeUp() {
		// Get the size of the screen for swipe calculation
		int height = driver.manage().window().getSize().height;
		int width = driver.manage().window().getSize().width;

		int startX = width / 2;
		int startY = (int) (height * 0.8); // Starting point from the bottom of the screen
		int endY = (int) (height * 0.2); // End point near the top of the screen

		
		// Create a PointerInput instance
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

		// Create a sequence of actions to perform the swipe
		Sequence swipe = new Sequence(finger, 0);
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
		swipe.addAction(finger.createPointerDown(0));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(2000), PointerInput.Origin.viewport(), startX, endY));
		swipe.addAction(finger.createPointerUp(0));

		// Perform the swipe action
		driver.perform(Arrays.asList(swipe));
	}

	// Method to swipe until an element is visible or until max swipes is reached
	@Test
	public void swipeUpUntilElementVisible(By locator, int maxSwipes) {
		int swipeCount = 0;

		// Swipe up until the element is visible or max swipes reached
		while (swipeCount < maxSwipes) {
			try {
				// Wait for the element to be visible
				WebElement element = new WebDriverWait(driver, Duration.ofSeconds(3))
						.until(ExpectedConditions.visibilityOfElementLocated(locator));

				if (element.isDisplayed()) {
					System.out.println("Element is visible.");
					break; // Break the loop if the element is visible
				}
			} catch (Exception e) {
				// If the element is not visible, continue swiping up
				swipeUp();
				swipeCount++;
			}
		}

		if (swipeCount == maxSwipes) {
			System.out.println("Element not found after maximum swipes.");
		}
	}
}
