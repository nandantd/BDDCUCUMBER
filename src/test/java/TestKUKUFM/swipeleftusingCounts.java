package TestKUKUFM;

import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class swipeleftusingCounts extends OpenKUKUFMapp {

    // Method to swipe using specified coordinates
    public static void swipeLeftToRight(int swipeCount, int startX, int endX, int startY, int endY) throws InterruptedException {

        // Define pointer input (finger)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        for (int i = 0; i < swipeCount; i++) {
            // Create a swipe action sequence
            Sequence swipe = new Sequence(finger, 1);

            // Move to the start point (press)
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.RIGHT.asArg()));

            // Move to the end point (drag)
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(2000), PointerInput.Origin.viewport(), endX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.RIGHT.asArg()));

            // Perform the swipe
            driver.perform(Collections.singletonList(swipe));
            Thread.sleep(1000);
        }
    }
}
