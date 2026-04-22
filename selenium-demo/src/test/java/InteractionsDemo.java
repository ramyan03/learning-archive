import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class InteractionsDemo
{
    static DriverUtilities driverUtilities;
    static WebDriver driver;

    private static final String LOGIN_URL =
        "http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx";

    private static final String ORDERS_URL =
        "http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/";

    @Before
    public void init()
    {
        driverUtilities = DriverUtilities.getInstance();
        driver = driverUtilities.getDriver();
        driver.manage().window().maximize();
    }

    // ── TEXT INPUTS AND BUTTONS ──────────────────────────────────────────────
    @Test
    public void testTextInputsAndButtons() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        Thread.sleep(1000);

        WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));

        // sendKeys() — type text into a field
        usernameField.sendKeys("Tester");
        Thread.sleep(500);

        // clear() — clears any existing text in the field
        usernameField.clear();
        Thread.sleep(500);

        // sendKeys() again after clearing
        usernameField.sendKeys("Tester");
        Thread.sleep(500);

        WebElement passwordField = driver.findElement(By.name("ctl00$MainContent$password"));
        passwordField.sendKeys("test");
        Thread.sleep(500);

        // sendKeys(Keys.TAB) — press the Tab key to move focus to the next field
        usernameField.sendKeys(Keys.TAB);
        Thread.sleep(500);

        // sendKeys(Keys.RETURN) — press Enter/Return key
        passwordField.sendKeys(Keys.RETURN);
        Thread.sleep(1000);

        driver.get(LOGIN_URL);
        Thread.sleep(500);
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");

        WebElement loginButton = driver.findElement(By.className("btn_login"));

        // click() — clicks the element. Element must be visible with height/width > 0
        loginButton.click();
        Thread.sleep(2000);
    }

    // ── TABLES ───────────────────────────────────────────────────────────────
    @Test
    public void testTables() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
        driver.findElement(By.className("btn_login")).click();
        Thread.sleep(2000);

        driver.get(ORDERS_URL);
        Thread.sleep(1000);

        // Locate the whole table first as a WebElement
        WebElement table = driver.findElement(By.xpath("//*[@class='SampleTable']"));

        // By rows — findElements on the table returns each <tr> as a WebElement in a list
        List<WebElement> rowsInTable = table.findElements(By.tagName("tr"));
        System.out.println("Number of rows: " + rowsInTable.size());

        // Loop through all rows and print the text of each
        for (WebElement row : rowsInTable)
        {
            System.out.println("Row text: " + row.getText());
        }
        Thread.sleep(1000);

        // Refer to a specific row by index — get the second row (index 1)
        WebElement specificRow = rowsInTable.get(1);
        System.out.println("Specific row text: " + specificRow.getText());

        // By cells — findElements on a specific row to get individual <td> cells
        List<WebElement> cellsInRow = specificRow.findElements(By.tagName("td"));
        System.out.println("Number of cells in row: " + cellsInRow.size());

        // Refer to a specific cell — get the first cell in the row
        WebElement specificCell = cellsInRow.get(0);
        System.out.println("Specific cell text: " + specificCell.getText());
        Thread.sleep(1000);
    }

    // ── VERIFY ───────────────────────────────────────────────────────────────
    @Test
    public void testVerify() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        Thread.sleep(1000);

        WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));
        WebElement loginButton = driver.findElement(By.className("btn_login"));

        // isEnabled() — returns true if the element can be interacted with
        System.out.println("Username enabled: " + usernameField.isEnabled());

        // isDisplayed() — returns true if the element is visible to the user
        System.out.println("Login button displayed: " + loginButton.isDisplayed());

        // isSelected() — returns true if a checkbox or radio button is selected
        // Navigate to orders page to find a checkbox to check against
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
        loginButton.click();
        Thread.sleep(2000);

        WebElement checkbox = driver.findElement(By.cssSelector("[type='checkbox']"));
        System.out.println("Checkbox selected before click: " + checkbox.isSelected());
        checkbox.click();
        System.out.println("Checkbox selected after click: " + checkbox.isSelected());
        Thread.sleep(1000);
    }

    // ── SELECT DROP DOWN ─────────────────────────────────────────────────────
    @Test
    public void testSelectDropDown() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
        driver.findElement(By.className("btn_login")).click();
        Thread.sleep(2000);

        // Navigate to the order form which contains a dropdown
        WebElement newOrderLink = driver.findElement(By.linkText("Order"));
        newOrderLink.click();
        Thread.sleep(1000);

        // Create a Select object by wrapping the <select> WebElement
        Select productDropdown = new Select(
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$ddlProduct")));

        // selectByVisibleText() — select by the text shown in the dropdown menu
        productDropdown.selectByVisibleText("MyMoney");
        Thread.sleep(500);

        // selectByIndex() — select by position, 0 is the first option
        productDropdown.selectByIndex(1);
        Thread.sleep(500);

        // selectByValue() — select by the value attribute in the DOM structure
        productDropdown.selectByValue("FamilyAlbum");
        Thread.sleep(500);

        // getOptions() — returns a list of all options in the dropdown
        List<WebElement> options = productDropdown.getOptions();
        System.out.println("Number of options: " + options.size());
        for (WebElement option : options)
        {
            System.out.println("Option: " + option.getText());
        }

        // isMultiple() — returns true if the dropdown allows multiple selections
        System.out.println("Allows multiple: " + productDropdown.isMultiple());
        Thread.sleep(1000);
    }

    // ── RETRIEVING INFORMATION ───────────────────────────────────────────────
    @Test
    public void testRetrievingInformation() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        Thread.sleep(1000);

        WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));
        WebElement loginButton = driver.findElement(By.className("btn_login"));

        // getText() — retrieves the visible text written inside the element
        System.out.println("Button text: " + loginButton.getText());

        // getTagName() — retrieves the HTML tag type of the element
        System.out.println("Username tag: " + usernameField.getTagName());
        System.out.println("Button tag: " + loginButton.getTagName());

        // getAttribute() — retrieves the value of a specific HTML attribute
        System.out.println("Username ID attribute: " + usernameField.getAttribute("id"));
        System.out.println("Username type attribute: " + usernameField.getAttribute("type"));
        System.out.println("Button class attribute: " + loginButton.getAttribute("class"));
        Thread.sleep(1000);
    }

    // ── ASSERTIONS ───────────────────────────────────────────────────────────
    @Test
    public void testAssertions() throws InterruptedException
    {
        driver.get(LOGIN_URL);
        Thread.sleep(1000);

        // assertEquals(expected, actual) — checks if two values are equal
        // Test fails immediately if they do not match
        Assert.assertEquals("Web Orders Login", driver.getTitle());

        WebElement loginButton = driver.findElement(By.className("btn_login"));

        // assertTrue(condition) — checks if the boolean condition is true
        Assert.assertTrue("Login button should be displayed", loginButton.isDisplayed());
        Assert.assertTrue("Login button should be enabled", loginButton.isEnabled());

        System.out.println("All assertions passed");
        Thread.sleep(1000);
    }

    // ── WAITS ────────────────────────────────────────────────────────────────
    @Test
    public void testWaits() throws InterruptedException
    {
        // --- Implicit Wait ---
        // Global wait applied to every findElement call for the whole session.
        // WebDriver will keep trying to find the element for up to the set duration
        // before throwing NoSuchElementException.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(LOGIN_URL);

        // With implicit wait set, WebDriver waits up to 10 seconds for this element
        WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));
        usernameField.sendKeys("Tester");
        Thread.sleep(500);

        // --- Explicit Wait ---
        // Waits for a specific condition on a specific element before continuing.
        // More precise than implicit — applied per element rather than globally.
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement passwordField = explicitWait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.name("ctl00$MainContent$password")));

        passwordField.sendKeys("test");
        Thread.sleep(500);

        // Wait until the login button is clickable before clicking it
        WebElement loginButton = explicitWait.until(
            ExpectedConditions.elementToBeClickable(By.className("btn_login")));
        loginButton.click();
        Thread.sleep(2000);

        // --- Fluent Wait ---
        // Like explicit wait but lets you set how frequently to check the condition
        // and which exceptions to ignore while waiting.
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))       // max time to wait
            .pollingEvery(Duration.ofMillis(500))       // check every 500ms
            .ignoring(NoSuchElementException.class);    // ignore this exception while waiting

        WebElement heading = fluentWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        System.out.println("Fluent wait found heading: " + heading.getText());
        Thread.sleep(1000);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }
}