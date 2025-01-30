package HomethumbNail;

import TestKUKUFM.OpenKUKUFMapp;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class PressBack extends OpenKUKUFMapp {

	public void pressBack() {

		AndroidDriver driver1 = (AndroidDriver) driver;
		driver1.pressKey(new KeyEvent(AndroidKey.BACK));
		
		
		
	}
}
