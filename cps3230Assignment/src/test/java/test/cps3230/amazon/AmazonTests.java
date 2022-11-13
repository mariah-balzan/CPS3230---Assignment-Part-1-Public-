package test.cps3230.amazon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

//These are simple tests to check our browser and searches are functioning properly before we started sending POST Requests
public class AmazonTests {

    WebDriver driver;
    //Setup - Gain access to a browser by selenium
    //Therefore, a chrome driver object will be created before each test and
    //fires up a new instance of chrome and runs under the control of the tests.
    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.amazon.co.uk/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Disable cookies box when amazon pages opens
        WebElement acceptAll = driver.findElement(By.id("a-autoid-0"));
        acceptAll.click();
    }

    //Teardown method: otherwise there will be numerous open browser instances
    @AfterEach
    public void teardown(){
        driver.quit();
    }

    @Test
    public void testSimpleAmazonSearchLegos() throws Exception{
        //Exercise
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("legos");
        driver.findElement(By.id("nav-search-submit-button")).submit();

        //Verify
        String title = driver.getTitle();
        Assertions.assertEquals("Amazon.co.uk : legos", title);

        //Verify - result stats component exists
        List<WebElement> resultStats = driver.findElements(By.xpath("//*[@class='a-section a-spacing-small a-spacing-top-small']"));
        Assertions.assertEquals(1, resultStats.size());

        //Verify - results are listed
        List<WebElement> resultsSection = driver.findElements(By.xpath("//*[@data-component-type='s-search-results']"));
        Assertions.assertEquals(1, resultsSection.size());
    }
}
