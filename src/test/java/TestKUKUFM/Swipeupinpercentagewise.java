package TestKUKUFM;


import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class Swipeupinpercentagewise extends OpenKUKUFMapp {

    // Method to swipe up a given number of times based on percentage input
    public void swipeUpWithPercentage(int maxSwipes, double swipePercentage) {
        // Validate swipe percentage
        if (swipePercentage <= 0 || swipePercentage > 1) {
            System.out.println("Swipe percentage must be between 0 and 1.");
            return;
        }

        // Get the size of the screen for swipe calculation
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        // Calculate start and end points for swipe based on screen height and swipe percentage
        int startX = width / 2;
        int startY = (int) (height * 0.8); // Starting point from the bottom of the screen
        int endY = (int) (height * (1 - swipePercentage)); // End point based on swipe percentage

        // Create a PointerInput instance
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Loop to perform the swipe action for the specified number of times
        for (int swipeCount = 0; swipeCount < maxSwipes; swipeCount++) {
            System.out.println("Performing swipe #" + (swipeCount + 1));
            // Create a sequence of actions to perform the swipe
            Sequence swipe = new Sequence(finger, 0);
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(2000), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(0));

            // Perform the swipe action
            driver.perform(Arrays.asList(swipe));
        }
    }
}
