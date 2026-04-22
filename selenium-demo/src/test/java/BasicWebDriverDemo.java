import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BasicWebDriverDemo
{
    static DriverUtilities driverUtilities;
    static WebDriver driver;

    @BeforeClass
    public static void init()
    {
        driverUtilities = DriverUtilities.getInstance();
        driver = driverUtilities.getDriver();
    }

    @Test
    public void testNavigationCommands() throws InterruptedException
    {
        // Load the SmartBear login page in the current window
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        Thread.sleep(10000);

        // Store the handle of the first window
        String window1 = driver.getWindowHandle();
        System.out.println("Window 1 handle: " + window1);

        // Open a brand new browser window and navigate to FDM
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("http://www.fdmgroup.com");

        // Store the handle of the second window
        String window2 = driver.getWindowHandle();
        System.out.println("Window 2 handle: " + window2);

        // Switch back to the first window (SmartBear login page)
        driver.switchTo().window(window1);

        // Switch back to the second window (FDM)
        driver.switchTo().window(window2);

        // Navigate to a specific FDM page
        driver.get("https://www.fdmgroup.com/en-ca/about-us/");

        // Go back to the previous page (fdmgroup.com homepage)
        driver.navigate().back();

        // Go forward again to the about-us page
        driver.navigate().forward();
    }

    @Test
    public void testBrowserCommands()
    {
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        // Maximize the browser window
        driver.manage().window().maximize();

        // Get and print the page title
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertEquals("Web Orders Login", title);

        // Get and print the current URL
        String URL = driver.getCurrentUrl();
        System.out.println(URL);
        Assert.assertEquals("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx", URL);
    }

    @Test
    public void testCapabilities()
    {
        // Downcast WebDriver to RemoteWebDriver to access browser metadata
        RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;

        // Get and print the browser name
        String browserName = remoteDriver.getCapabilities().getBrowserName();
        System.out.println("Browser name: " + browserName);

        // Get and print the browser version
        String browserVersion = remoteDriver.getCapabilities().getBrowserVersion();
        System.out.println("Browser version: " + browserVersion);
    }

    @Test
    public void testScreenshot() throws IOException
    {
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        // Downcast WebDriver to TakesScreenshot to access screenshot functionality
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Capture the screenshot as a File object
        File screenshot = ts.getScreenshotAs(OutputType.FILE);

        // Copy the screenshot to the desired location with a meaningful name
        FileHandler.copy(screenshot, new File("src/test/resources/images/screenshot.png"));
        System.out.println("Screenshot saved to: src/test/resources/images/screenshot.png");
    }

    @AfterClass
    public static void tearDown()
    {
        driver.quit();
    }
}