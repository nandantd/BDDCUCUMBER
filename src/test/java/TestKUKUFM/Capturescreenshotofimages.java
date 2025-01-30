package TestKUKUFM;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

public class Capturescreenshotofimages extends OpenKUKUFMapp {
	
	@Test
	public void getscreenshot(WebElement element) throws IOException  {
		
		
	
	File image=element.getScreenshotAs(OutputType.FILE);
	
   FileHandler.copy(image, new File("‪C://Image1.png"));
	
	}
}
