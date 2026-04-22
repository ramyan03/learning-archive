import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LocatorsDemo
{
    static DriverUtilities driverUtilities;
    static WebDriver driver;

    @Before
    public void init()
    {
        driverUtilities = DriverUtilities.getInstance();
        driver = driverUtilities.getDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void locatingElementsTest() throws InterruptedException
    {
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        Thread.sleep(1000);

        // 1 Locating by ID
        WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));
        usernameField.sendKeys("Tester");
        Thread.sleep(1000);

        // 2 Locating by Name
        WebElement passwordField = driver.findElement(By.name("ctl00$MainContent$password"));
        passwordField.sendKeys("test");
        Thread.sleep(1000);

        // 3 Locating by Class Name
        WebElement submitButton = driver.findElement(By.className("btn_login"));
        submitButton.click();
        Thread.sleep(2000);

        // 4 Locating by Tag Name — get the page heading text
        WebElement heading = driver.findElement(By.tagName("h2"));
        System.out.println("Tag name result: " + heading.getText());
        Thread.sleep(1000);

        // 5 Locating by CSS Selector — find all checkboxes and select them
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("[type='checkbox']"));
        System.out.println("CSS selector result: found " + checkboxes.size() + " checkboxes");
        for (WebElement checkbox : checkboxes)
        {
            checkbox.click();
        }
        Thread.sleep(2000);

        // 6 Locating by Link Text — exact match on anchor text
        WebElement allProductsLink = driver.findElement(By.linkText("View all products"));
        allProductsLink.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        driver.navigate().back();
        Thread.sleep(1000);

        // 7 Locating by Partial Link Text — partial match on anchor text
        WebElement allOrdersLink = driver.findElement(By.partialLinkText("All orders"));
        allOrdersLink.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        driver.navigate().back();
        Thread.sleep(1000);

        // 8 Locating by XPath — relative xpath with contains() on attribute
        WebElement usernameXPath = driver.findElement(By.xpath("//*[contains(@id, 'username')]"));
        System.out.println("XPath result: " + usernameXPath.getTagName());
        Thread.sleep(1000);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }
}