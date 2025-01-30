package TestKUKUFM;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;

public class Verifyshowsareunlocking extends OpenKUKUFMapp {
	String searchtext = "Hidden CEO";

	@Test
	public void verifyunlockingshows() throws MalformedURLException, InterruptedException {

		Openapp("com.vlv.aravali", "com.vlv.aravali.master.ui.MasterActivity");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement coinswitch = driver.findElement(By.id("com.vlv.aravali:id/cvCoinShop"));
		wait.until(ExpectedConditions.visibilityOf(coinswitch));
		coinswitch.click();
		WebElement coinsdisplayed = driver.findElement(
				By.xpath("//android.widget.TextView[contains(@resource-id,'com.vlv.aravali:id/tv_coins')]"));
		wait.until(ExpectedConditions.visibilityOf(coinsdisplayed));
		Thread.sleep(3000);
		String coinsbeforeused = coinsdisplayed.getText();
		System.out.println(coinsbeforeused);

		driver.pressKey(new KeyEvent(AndroidKey.BACK));

		WebElement searchbar = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@text,'Search on Kuku FM')]"));
		wait.until(ExpectedConditions.visibilityOf(searchbar));

		searchbar.click();
		WebElement searchbar2 = driver.findElement(By.xpath(
				"//android.widget.TextSwitcher[@resource-id='com.vlv.aravali:id/querySwitcher']/..//android.widget.TextView[@text='Search on Kuku FM']"));
		
		int finalattempts = 5;
		while (finalattempts > 0) {
			try {
				searchbar2.isDisplayed();
				wait.until(ExpectedConditions.visibilityOf(searchbar2));
				searchbar2.sendKeys(searchtext);
				break;

			} catch (StaleElementReferenceException e) {
				finalattempts--;
				if (finalattempts == 0) {
					System.out.println("tried maximum tries" + finalattempts);
					throw e;

				}

			} 
		}

		driver.pressKey(new KeyEvent(AndroidKey.NUMPAD_ENTER));

		WebElement listedshow = driver.findElement(
				By.xpath("//android.widget.TextView[@resource-id='com.vlv.aravali:id/tvContentTitle' and @text="
						+ searchtext + "]"));

		wait.until(ExpectedConditions.elementToBeClickable(listedshow));
		listedshow.click();
		WebElement lockshowtext = driver
				.findElement(By.xpath("(//android.widget.TextView[contains(@text,'Unlock with 9 coins')])[1]"));

		SwipeUntilElementVisible swipetillelement = new SwipeUntilElementVisible();
		swipetillelement.swipeUpUntilElementVisible(
				By.xpath("(//android.widget.TextView[contains(@text,'Unlock with 9 coins')])[1]"), 20);
		WebElement unlockedshow = driver.findElement(By.xpath(
				"(//android.widget.TextView[contains(@text,\"Unlock with 9 coins\")])[1]/parent::android.view.View/preceding-sibling::android.view.View/../..//android.view.View[@content-desc=\"Download\"]/parent::android.view.View/parent::android.view.View/child::android.widget.TextView[1]"));

		System.out.println("lockshowtext is displayed");
		wait.until(ExpectedConditions.visibilityOf(unlockedshow));
		wait.until(ExpectedConditions.elementToBeClickable(unlockedshow));
		unlockedshow.click();

		WebElement pauseshow = driver
				.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Play or Pause button\"]"));
		wait.until(ExpectedConditions.visibilityOf(pauseshow));
		wait.until(ExpectedConditions.elementToBeClickable(pauseshow));
		pauseshow.click();

		WebElement SEEKbar = driver.findElement(By.id("com.vlv.aravali:id/exo_progress"));
		SeekBarPointerInputTest seekbar = new SeekBarPointerInputTest();
		seekbar.moveSeekBar(pauseshow, 0.96);

		pauseshow.click();

	}

}
