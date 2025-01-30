package TestKUKUFM;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.touch.offset.PointOption;

public class OpenNotification extends OpenKUKUFMapp {
    
    public void openNotification() {
        
        // Create PointerInput instance (finger gesture)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        
        // Create a sequence of actions for the swipe gesture
        Sequence swipeDown = new Sequence(finger, 0);
        
        // Starting near the top of the screen, swipe down to open notifications
        swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(0), PointOption.point(500, 100))) // Start near the top of the screen
                  .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())) // Press down
                  .addAction(finger.createPointerMove(Duration.ofMillis(200), PointOption.point(500, 1000))) // Swipe down
                  .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); // Release

        // Perform the swipe gesture
        driver.perform(Arrays.asList(swipeDown)); // Execute swipe gesture

    }
}
