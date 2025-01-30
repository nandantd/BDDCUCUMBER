package TestKUKUFM;
import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;

public class SwipeUpUsingSwipeCounts extends OpenKUKUFMapp {

	// Merged method to swipe up on the screen using swipe count
	@Test
	public void swipeUpUsingSwipeCount(int maxSwipes) {
		// Get the size of the screen for swipe calculation
		int height = driver.manage().window().getSize().height;
		int width = driver.manage().window().getSize().width;

		int startX = width / 2;
		int startY = (int) (height * 0.8); // Starting point from the bottom of the screen
		int endY = (int) (height * 0.4); // End point near the top of the screen

		// Create a PointerInput instance
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

		// Define the maximum number of swipes (change as needed)
		int swipeCount = 0;

		// Swipe up until max swipes is reached
		while (swipeCount < maxSwipes) {
			try {
				// Create a sequence of actions to perform the swipe
				Sequence swipe = new Sequence(finger, 0);
				swipe.addAction(
						finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
				swipe.addAction(finger.createPointerDown(0));
				swipe.addAction(
						finger.createPointerMove(Duration.ofMillis(2000), PointerInput.Origin.viewport(), startX, endY));
				swipe.addAction(finger.createPointerUp(0));

				// Perform the swipe action
				driver.perform(Arrays.asList(swipe));

				swipeCount++;

				Thread.sleep(2000); // Wait before the next swipe for stability
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupted status
				System.out.println("Swipe interrupted: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Failed to swipe up: " + e.getMessage());
			}
		}
	}
}
