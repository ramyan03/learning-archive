import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverUtilities
{
    // 1. Private static instance — holds the single instance of this class (Singleton pattern)
    private static DriverUtilities driverUtilities;
    private WebDriver driver;

    // 2. Private constructor — prevents external instantiation
    private DriverUtilities()
    {
        super();
    }

    // 3. Public static getInstance() — returns the existing instance or creates one if null
    public static DriverUtilities getInstance()
    {
        if (driverUtilities == null)
        {
            driverUtilities = new DriverUtilities();
        }
        return driverUtilities;
    }

    // Returns the existing driver or creates a new one if null
    public WebDriver getDriver()
    {
        if (driver == null)
        {
            createDriver();
        }
        return driver;
    }

    // Creates the correct driver based on the browser name read from config
    private void createDriver()
    {
        String driverName = getDriverName();
        switch (driverName)
        {
            case "Chrome":
                this.driver = new ChromeDriver();
                break;
            case "Firefox":
                this.driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser in config: " + driverName);
        }
    }

    // Reads the browser property from config.properties
    private String getDriverName()
    {
        Properties config = new Properties();
        try
        {
            config.load(new FileInputStream("src/test/resources/config.properties"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return config.getProperty("browser");
    }
}