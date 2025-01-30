package TestKUKUFM;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;

public class SeekBarPointerInputTest extends OpenKUKUFMapp {

	// Method to move the SeekBar using PointerInput
	@Test
	public void moveSeekBar(WebElement seekBar, double position) {

		//Get SeekBar dimensions and calculate positions
		
		int startX = seekBar.getLocation().getX();
		System.out.println("start X value" + startX);
		int endX = startX + seekBar.getSize().getWidth();
		System.out.println("End x" + endX);
		int y = seekBar.getLocation().getY() + (seekBar.getSize().getHeight() / 2);

		// Calculate the exact x-coordinate for the target position
		
		int targetX = (int) (startX + position * (endX - startX));
		System.out.println("Target value" + targetX);
		// Create PointerInput instance for touch action
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

		// Define a sequence for dragging
		Sequence dragSeekBar = new Sequence(finger, 0);

		// Add start action (press at startX)
		dragSeekBar
				.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, y));
		dragSeekBar.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

		// Add move action (dragging to the targetX)
		dragSeekBar.addAction(
				finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), targetX, y));

		// Add release action
		dragSeekBar.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		// Perform the drag action
		driver.perform(Collections.singletonList(dragSeekBar));
		
	}
}
