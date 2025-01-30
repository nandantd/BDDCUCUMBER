package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class QAM2Flow2 extends OpenKUKUFMapp {

	@Test
	public void testQam() throws MalformedURLException, InterruptedException {
		// Handle any major exceptions that might occur during the test flow.
		try {
			// Open the app
			Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");
			Thread.sleep(10000);

			// Set up waits
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			// Wait for app to load and click QAM home
			WebElement qamHomeIcon = waitForElement(
					By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali:id/ivIcon']"), wait);
			qamHomeIcon.click();

			// Collect all unique text elements with scrolling
			List<String> uniqueTexts = collectUniqueTextElements(wait);
			System.out.println("Total unique elements found: " + uniqueTexts.size());
			System.out.println("Unique texts: " + uniqueTexts);

			// Click each element in the unique list
			for (String text : uniqueTexts) {
				clickElementWithText(text, wait);
				Thread.sleep(1000); // Small delay after navigation
			}
		} catch (Exception e) {
			System.out.println("Test failed due to: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private List<String> collectUniqueTextElements(WebDriverWait wait) throws InterruptedException {
		Set<String> uniqueTexts = new TreeSet<>();
		List<String> L1 = new ArrayList<>();  // Initialize the list to store unique texts
		int maxScrolls = 5;
		int scrollCount = 0;

		// Collect the initial list of elements
		List<WebElement> QAMTextElements = driver
				.findElements(By.xpath("//android.widget.HorizontalScrollView/descendant::android.widget.TextView"));
		List<String> QAMTextList = new ArrayList<>();

		// Loop through each WebElement to retrieve and print text
		for (WebElement element : QAMTextElements) {
			wait.until(ExpectedConditions.visibilityOf(element));
			String elementText = element.getText();
			System.out.println(elementText);
			QAMTextList.add(elementText);
		}

		// Print the list of retrieved texts
		System.out.println("List of texts from QAM elements: " + QAMTextList);

		// Count the number of elements
		int elementCount = QAMTextElements.size();
		System.out.println("Number of elements: " + elementCount);

		// Scroll and collect more elements
		while (scrollCount < maxScrolls) {
			// Swipe left logic - ensure this swipe method is correctly implemented
			swipeLeftToRight(1, 948, 539, 336, 313);
			Thread.sleep(2000);

			// Refresh the list of TextView elements after the swipe
			List<WebElement> QAMTextElements1 = driver
					.findElements(By.xpath("//android.widget.HorizontalScrollView/descendant::android.widget.TextView"));
			List<String> QAMTextList1 = new ArrayList<>();

			for (WebElement element : QAMTextElements1) {
				wait.until(ExpectedConditions.visibilityOf(element));
				String elementText = element.getText();
				System.out.println(elementText);
				QAMTextList1.add(elementText);
			}

			// Combine lists
			List<String> combinedTextList = new ArrayList<>(QAMTextList);
			combinedTextList.addAll(QAMTextList1);
			System.out.println("Combined text list: " + combinedTextList);

			// Remove duplicates using TreeSet
			Set<String> uniqueTextSet = new TreeSet<>(combinedTextList);
			L1 = new ArrayList<>(uniqueTextSet);

			System.out.println("Unique texts: " + uniqueTextSet);

			scrollCount++; // Increment the scroll count
		}

		return L1; // Return the unique text list
	}

	private void clickElementWithText(String text, WebDriverWait wait) throws InterruptedException {
		By elementLocator = By.xpath("//android.view.ViewGroup[@resource-id='com.vlv.aravali:id/flContainer']"
				+ "/..//android.widget.TextView[contains(@text,'" + text + "')]");

		// Attempt to click on the element using a helper method
		boolean success = tryClickElement(elementLocator, wait);

		// If clicking was unsuccessful, attempt swiping to find the element
		if (!success) {
			swipeUntilElementFound(elementLocator, wait);
		}
	}

	private boolean tryClickElement(By locator, WebDriverWait wait) {
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if (element.isDisplayed() && element.isEnabled()) {
				element.click();
				return true;
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element not found immediately: " + locator);
		}
		return false;
	}

	private void swipeUntilElementFound(By locator, WebDriverWait wait) throws InterruptedException {
		int maxSwipes = 5;
		int swipeCount = 0;
		boolean elementFound = false;

		while (swipeCount < maxSwipes && !elementFound) {
			try {
				WebElement element = driver.findElement(locator);
				if (element.isDisplayed() && element.isEnabled()) {
					wait.until(ExpectedConditions.elementToBeClickable(element)).click();
					elementFound = true;
				} else {
					swipeLeftToRight(1, 948, 539, 336, 313);
					Thread.sleep(1000);
					swipeCount++;
				}
			} catch (NoSuchElementException e) {
				System.out.println("Element not found during swipe attempt, retrying.");
				swipeLeftToRight(1, 948, 539, 336, 313);
				Thread.sleep(1000);
				swipeCount++;
			} catch (Exception e) {
				System.out.println("Error during swipe: " + e.getMessage());
				e.printStackTrace();
				break;
			}
		}

		if (!elementFound) {
			throw new RuntimeException("Could not find or click the element with locator: " + locator);
		}
	}

	// This method provides a more structured way to handle swipe actions
	private void swipeLeftToRight(int count, int startX, int startY, int endX, int endY) {
		try {
			System.out.println("Performing swipe left to right...");
			// Implement actual swipe logic here, for example:
			// TouchAction action = new TouchAction(driver);
			// action.press(PointOption.point(startX, startY))
			// .moveTo(PointOption.point(endX, endY))
			// .release()
			// .perform();
		} catch (Exception e) {
			System.out.println("Error during swipeLeftToRight: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// A helper method to wait for an element's visibility and return it
	private WebElement waitForElement(By locator, WebDriverWait wait) {
		WebElement element = null;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			System.out.println("Element not found: " + locator);
			e.printStackTrace();
		}
		return element;
	}
}
