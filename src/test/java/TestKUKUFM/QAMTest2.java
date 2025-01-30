package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class QAMTest2 extends OpenKUKUFMapp {
	int size1;
	int i = 0;

	@Test
	public void testQam() throws MalformedURLException, InterruptedException {

		// Open the app
		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");
		Actions act = new Actions(driver);

		Thread.sleep(20000);
		// Set implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for initial page load (Use explicit wait rather than sleep)
		Thread.sleep(3000); // Reduced from 30 seconds for optimization

		// Click on QAM home element
		WebElement QAMhome = driver
				.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.vlv.aravali:id/ivIcon']"));
		QAMhome.click();

		// Get all TextView elements within the HorizontalScrollView
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

		System.out.println(elementCount);
		// Process each element in the list
		while (i < elementCount) {
			try {
				WebElement currentElement = QAMTextElements.get(i);
				System.out.println(currentElement);
				wait.until(ExpectedConditions.visibilityOf(currentElement));
				System.out.println(currentElement.getText());

				// Swipe left logic - ensure this swipe method is correctly implemented
				swipeleftusingCounts left = new swipeleftusingCounts();
				left.swipeLeftToRight(1, 948, 539, 336, 313);
				Thread.sleep(2000);

				// Refresh the list of TextView elements after the swipe
				List<WebElement> QAMTextElements1 = driver.findElements(
						By.xpath("//android.widget.HorizontalScrollView/descendant::android.widget.TextView"));
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
				List<String> L1 = new ArrayList<>(uniqueTextSet);
				size1 = L1.size();

				System.out.println("Unique texts: " + uniqueTextSet);
				System.out.println(size1);

			
			
			i++;
			
				
				// Increment the index to process the next element

				// Iterate over the list and perform actions
				for (int i1 = 1; i1 <= size1; i1++) {
					// Correct locator string
					By locator = By.xpath(
							"//android.view.ViewGroup[@resource-id=\"com.vlv.aravali:id/flContainer\"]/..//android.widget.TextView[contains(@text,'"
									+ L1.get(i1) + "')]");

					WebElement element = driver.findElement(locator);

					if (element.isDisplayed()) {
						wait.until(ExpectedConditions.elementToBeClickable(element));
						element.click();
					} else if (!element.isDisplayed()) {
						// Swipe until the element is visible and clickable
						Thread.sleep(3000);
						swipeleftusingelement swipeleft = new swipeleftusingelement();
						swipeleft.swipeLeftUntilElementVisible(locator);
						wait.until(ExpectedConditions.elementToBeClickable(element));
						element.click();
					} else {
						Thread.sleep(3000);
						swiperightusingelement swiperight = new swiperightusingelement();
						swiperight.swipeRight(locator);
						wait.until(ExpectedConditions.elementToBeClickable(element));
						element.click();

					}
				}
				
			} catch (Exception e) {
				System.out.println("Error processing element " + i + ": " + e.getMessage());
				break; // Exit if an error occurs
			}
		}
	}
}
