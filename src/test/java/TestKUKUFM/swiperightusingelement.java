package TestKUKUFM;

import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class swiperightusingelement extends OpenKUKUFMapp {

    // Method to swipe right until the specified element is visible, with a limited number of attempts
    public void swipeRight(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Set the maximum number of attempts
        int maxAttempts = 10;
        int attempts = 0;

        // Initialize the PointerInput instance for touch actions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Loop to try a swipe multiple times
        while (attempts < maxAttempts) {
            try {
                // Wait for the element to be visible and get its position and size
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (element.isDisplayed()) {
                    Point elementLocation = element.getLocation();
                    int elementWidth = element.getSize().width;
                    int elementHeight = element.getSize().height;

                    // Define swipe start and end points based on the element's position and size
                    int startX = elementLocation.getX() + elementWidth;  // Right side of the element
                    int endX = elementLocation.getX() + (elementWidth / 2); // Center of the element
                    int startY = elementLocation.getY() + (elementHeight / 2);  // Vertical center of the element

                    System.out.println("Swiping from X=" + startX + ", Y=" + startY + " to X=" + endX + ", Y=" + startY);

                    // Create a swipe sequence
                    Sequence swipe = new Sequence(finger, 1)
                            .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                            .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                            .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, startY))
                            .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    // Perform the swipe action
                    driver.perform(Collections.singletonList(swipe));

                    System.out.println("Element found and swipe performed.");
                    break;  // Exit loop if the element is found and swipe is performed
                }
            } catch (Exception e) {
                // If the element is not found or not visible, attempt the swipe
                System.out.println("Element not found, attempt " + (attempts + 1) + " of " + maxAttempts);
                attempts++;

                // Optional: Add a short delay before retrying
                waitForSwipeAnimation();
            }
        }

        // If the loop ends without finding the element, log a message
        if (attempts == maxAttempts) {
            System.out.println("Failed to find the element after " + maxAttempts + " attempts.");
        }
    }

    // Helper method to wait for the page to stabilize after a swipe (optional)
    private void waitForSwipeAnimation() {
        try {
            // Wait for a brief moment to let the swipe animation complete
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("xpath-of-any-element-changing-during-swipe")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
