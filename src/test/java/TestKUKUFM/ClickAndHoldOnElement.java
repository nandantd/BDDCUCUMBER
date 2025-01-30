package TestKUKUFM;

import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class ClickAndHoldOnElement extends OpenKUKUFMapp {

    public static void clickAndHold(WebElement element) {
        // Get the center coordinates of the element
        int centerX = element.getRect().x + (element.getRect().width / 2);
        int centerY = element.getRect().y + (element.getRect().height / 2);

        // Define pointer input (finger)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a click and hold action sequence on the element
        Sequence clickAndHold = new Sequence(finger, 1);

        // Move to the element’s center and press down (click)
        clickAndHold.addAction(
                finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX, centerY));
        clickAndHold.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Hold for the specified duration by remaining in the same position
        clickAndHold.addAction(
                finger.createPointerMove(Duration.ofMillis(3000), PointerInput.Origin.viewport(), centerX, centerY));

        // Release (lift finger up)
        clickAndHold.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the click and hold action
        driver.perform(Collections.singletonList(clickAndHold));
    }
}
