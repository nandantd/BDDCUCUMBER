package TestKUKUFM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;

public class SwipetillelementisvisibleLeftorright extends OpenKUKUFMapp {

    // Method to swipe automatically in the necessary direction until the specified element is visible
    public void swipeUntilElementVisible(By locator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Get screen dimensions
        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;
        int startY = screenHeight / 2;

        // Initialize the PointerInput for touch actions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Define default swipe parameters (left swipe)
        int startX = (int) (screenWidth * 0.99);
        int endX = (int) (screenWidth * 0.5);

        // Loop until the element is visible or max swipes reached
        int maxSwipes = 20; // Limit the number of swipes to avoid infinite loop
        int swipeCount = 0;

        while (swipeCount < maxSwipes) {
            try {
                // Check if the element is already visible
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (element.isDisplayed()) {
                    System.out.println("Element is visible");
                    break; // Exit loop if the element is visible
                }
            } catch (Exception e) {
                // Element not found within the timeout, proceed to swipe
                System.out.println("Swiping to locate element... Attempt: " + (swipeCount + 1));

                // Create a leftward swipe sequence
                Sequence swipe = new Sequence(finger, 1)
                        .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
                        .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, startY))
                        .addAction(finger.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));

                // Perform the swipe
                driver.perform(Collections.singletonList(swipe));
                swipeCount++; // Increment swipe attempt count
                Thread.sleep(1000); // Small delay after swipe
                break;
            }
        }

        if (swipeCount == maxSwipes) {
            System.out.println("Max swipes reached. Element not found.");
        }
    }
}
