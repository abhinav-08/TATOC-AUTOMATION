package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import java.util.Set;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    WebDriver chrome;
    @BeforeClass
    public void set()
    {
    System.setProperty("webdriver.chrome.driver","H:/downloads/chromedriver_win32 (1)/chromedriver.exe");
	chrome=new ChromeDriver();
	}
    @Test (priority=1)
    public void setUp() throws InterruptedException
	{
		
		
		//test1: opening the web app
		chrome.get("http://10.0.1.86/tatoc");
		
		//test2: maximizing the window     
		chrome.manage().window().maximize();
		Thread.sleep(1000);
		assertTrue(true);
	}

	@Test (priority=2)
    public void gettingStartedWithBasicCourse() throws InterruptedException
	{
	

	//test3:beginning with the basic course
		chrome.findElement(By.tagName("a")).click();
		Thread.sleep(1000);
	}

	@Test (priority=3)
    public void clickingRedBoxAndProceeding() throws InterruptedException
	{
		
		//test4:clicking on the red box
		chrome.findElement(By.className("greenbox")).click();
		Thread.sleep(1000);
	}

	@Test (priority=4)
    public void frameHandling() throws InterruptedException
	{	
		//test5:making color of both boxes same
		chrome.switchTo().defaultContent();
		chrome.switchTo().frame("main");//switching to the frame which contains both the boxes
		WebElement LeftBox = chrome.findElement(By.id("answer"));//locating the left box
		String colorOfLeftBox=LeftBox.getAttribute("class");//storing the color of the box
		
		chrome.switchTo().frame("child");//going inside the child frame
		WebElement RightBox = chrome.findElement(By.id("answer"));//Locating the right box
		String colorOfRightBox=RightBox.getAttribute("class");//getting the color of right box
		
		
		while(!colorOfLeftBox.equals(colorOfRightBox))//Actually making color same
		{
			//for this frame is getting switched because proceed button is in main frame and we are currently in main frame
			
			chrome.switchTo().defaultContent();			
			chrome.switchTo().frame("main");
			chrome.findElement(By.linkText("Repaint Box 2")).click();
			//switching back to child frame to get the color of right box
			chrome.switchTo().defaultContent();chrome.switchTo().frame("main");chrome.switchTo().frame("child");
			RightBox = chrome.findElement(By.id("answer"));
			colorOfRightBox=RightBox.getAttribute("class");
		}
		
		//Again switching to the main frame to proceed
		chrome.switchTo().defaultContent();
		chrome.switchTo().frame("main");
		chrome.findElement(By.linkText("Proceed")).click();
		Thread.sleep(1000);
	}

	@Test (priority=5)
    public void dragAndDrop() throws InterruptedException
	{
		//task6: drag and drop
		    		
    	WebElement element=chrome.findElement(By.id("dragbox"));// selecting element which needs to be dragged			
        WebElement container=chrome.findElement(By.id("dropbox"));// selecting element which recieve draggable element						
     	Actions act=new Actions(chrome);//Using Action class for drag and drop.				
     	act.dragAndDrop(element, container).build().perform();	
     	chrome.findElement(By.linkText("Proceed")).click();
     	Thread.sleep(1000);
     }

     @Test (priority=6)
    public void tabSwitching() throws InterruptedException
	{
     	//task7: popup window
     	String mainWindow=chrome.getWindowHandle();//storing current window's reference for future purpose
     	chrome.findElement(By.linkText("Launch Popup Window")).click();
     	Set<String> s1=chrome.getWindowHandles();//getting all windows
     	Iterator<String> i1=s1.iterator();
     	
     	while(i1.hasNext())
     	{
     		String secondaryWindow=i1.next();
     		if(!mainWindow.equalsIgnoreCase(secondaryWindow)){
     			chrome.switchTo().window(secondaryWindow);
     			chrome.findElement(By.id("name")).sendKeys("abhinav tyagi");
     			chrome.findElement(By.id("submit")).click();
     		}
     	}
     	
     	chrome.switchTo().window(mainWindow);//coming back to original window
     	chrome.findElement(By.linkText("Proceed")).click();
     	Thread.sleep(1000);
     }

     @Test (priority=7)
    public void cookieHandling() throws InterruptedException
	{
     	//task8:cookies
     	chrome.findElement(By.linkText("Generate Token")).click();
     	String tokenValue=chrome.findElement(By.id("token")).getText().trim().substring(7);
     	//System.out.println("--"+tokenValue+"--");
     	chrome.manage().addCookie(new Cookie("Token", tokenValue));
     	chrome.findElement(By.linkText("Proceed")).click();
     	Thread.sleep(1000);
     }
}
