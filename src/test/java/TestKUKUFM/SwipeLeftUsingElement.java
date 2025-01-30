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

public class SwipeLeftUsingElement extends OpenKUKUFMapp {
    
    public void swipeLeftUntilElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Get reference element for swipe coordinates
        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        
        // Calculate swipe coordinates based on container element
        Point containerLocation = container.getLocation();
        int containerWidth = container.getSize().width;
        int containerHeight = container.getSize().height;
        
        // Calculate swipe points based on container dimensions
        int startX = containerLocation.getX() + (int)(containerWidth * 0.8);
        int endX = containerLocation.getX() + (int)(containerWidth * 0.2);
        int swipeY = containerLocation.getY() + (containerHeight / 2);
        
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        int swipeAttempts = 0;
        boolean elementFound = false;
        
        while (swipeAttempts < 10 && !elementFound) {
            try {
                // First check if element exists and is visible
                WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (targetElement.isDisplayed()) {
                    Point elementLocation = targetElement.getLocation();
                    // Check if element is within viewable area
                    if (elementLocation.getX() >= containerLocation.getX() && 
                        elementLocation.getX() <= containerLocation.getX() + containerWidth) {
                        elementFound = true;
                        System.out.println("Element found at position: " + elementLocation.getX() + ", " + elementLocation.getY());
                        break;
                    }
                }
            } catch (Exception e) {
                // Element not found or not visible, perform swipe
                System.out.println("Element not visible, attempting swipe #" + (swipeAttempts + 1));
            }
            
            // Create and perform swipe sequence
            Sequence swipe = createSwipeSequence(finger, startX, endX, swipeY);
            driver.perform(Collections.singletonList(swipe));
            
            swipeAttempts++;
            waitForSwipeAnimation();
        }
        
        if (!elementFound) {
            System.out.println("Element not found after " + swipeAttempts + " swipe attempts");
        }
    }
    
    private Sequence createSwipeSequence(PointerInput finger, int startX, int endX, int swipeY) {
        return new Sequence(finger, 1)
            .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, swipeY))
            .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            .addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, swipeY))
            .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    }
    
    private void waitForSwipeAnimation() {
        try {
            // Using WebDriverWait to wait for a few seconds instead of Thread.sleep
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("xpath-of-any-element-changing-during-swipe")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
