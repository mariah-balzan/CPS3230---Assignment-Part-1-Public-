package test.cps3230.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MarketAlertUMSteps {

    //Attributes
    static WebDriver driver;

    //Teardown method: otherwise there will be numerous open browser instances
    @AfterEach
    public void teardown(){
        driver.quit();
    }


    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.marketalertum.com");
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("d98f7243-8f53-4523-b11f-f80fe9fe8230");
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//table[@border = '1']")).size() > 0;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@border = '1']")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("1111");
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        boolean isPresent = driver.findElements(By.xpath("//form[@action='/Alerts/LoginForm']")).size() > 0;
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@action='/Alerts/LoginForm']")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int alerts) {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.marketalertum.com");

        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("d98f7243-8f53-4523-b11f-f80fe9fe8230");
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();

        alerts = 2;
        for (int i = 0; i<alerts;i++) {
            Unirest.post("https://api.marketalertum.com/Alert")
                    .header("Content-Type", "application/json")
                    .body("{\"alertType\":5," +
                            "\"heading\":\"WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog \"," +
                            "\"description\":\" \"," +
                            "\"url\":\"https://www.amazon.co.uk/WeFine-Teething-Training%EF%BC%8C10pcs-Natural-Cotton/dp/B07WD8QPRB/ref=sr_1_5?crid=2O4UBUX7JYK78&keywords=WeFine+Puppy+Dog+Chew+Toys+Teething+Training%2C+10pcs+Dog+Rope+Toys+100%25+Natural+Cotton+Rope+for+Small+and+Medium+Dog&qid=1668250494&sprefix=wefine+puppy+dog+chew+toys+teething+training+10pcs+dog+rope+toys+100%25+natural+cotton+rope+for+small+and+medium+dog%2Caps%2C134&sr=8-5\"," +
                            "\"imageUrl\":\"https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg\"," +
                            "\"postedBy\":\"d98f7243-8f53-4523-b11f-f80fe9fe8230\",\"priceInCents\":1199}")
                    .asJson();
        }
    }

    @Given("I am a user of marketalertum that is logged in")
    public void iAmAUserOfMarketalertumThatIsLoggedIn() {
        //Exercise
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.marketalertum.com");

        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("d98f7243-8f53-4523-b11f-f80fe9fe8230");
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        boolean isPresent = driver.findElements(By.xpath("//table[@border = '1']")).size() > 0;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@border = '1']")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//h4//img[contains(@src,'icon')]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//img[contains(@src,'icon')]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//h4[text()]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//tr//td[text()]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//td[text()]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//tr//td//img[contains(@src,'amazon')]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//td//img[contains(@src,'amazon')]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//tr//td//b[contains(text(),'Price: ')]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//td//b[contains(text(),'Price: ')]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        //Exercise
        boolean isPresent = driver.findElements(By.xpath("//tr//td//a[contains(text(),'Visit item')]")).size() > 1;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//td//a[contains(text(),'Visit item')]")));

        //Verify
        Assertions.assertTrue(isPresent);
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int alerts) {
        //Exercise
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.marketalertum.com");

        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("d98f7243-8f53-4523-b11f-f80fe9fe8230");
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();

        alerts = 5;
        for (int i = 0; i<alerts+1;i++) {
            Unirest.post("https://api.marketalertum.com/Alert")
                    .header("Content-Type", "application/json")
                    .body("{\"alertType\":5," +
                            "\"heading\":\"WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog \"," +
                            "\"description\":\" \"," +
                            "\"url\":\"https://www.amazon.co.uk/WeFine-Teething-Training%EF%BC%8C10pcs-Natural-Cotton/dp/B07WD8QPRB/ref=sr_1_5?crid=2O4UBUX7JYK78&keywords=WeFine+Puppy+Dog+Chew+Toys+Teething+Training%2C+10pcs+Dog+Rope+Toys+100%25+Natural+Cotton+Rope+for+Small+and+Medium+Dog&qid=1668250494&sprefix=wefine+puppy+dog+chew+toys+teething+training+10pcs+dog+rope+toys+100%25+natural+cotton+rope+for+small+and+medium+dog%2Caps%2C134&sr=8-5\"," +
                            "\"imageUrl\":\"https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg\"," +
                            "\"postedBy\":\"d98f7243-8f53-4523-b11f-f80fe9fe8230\",\"priceInCents\":1199}")
                    .asJson();
        }
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int alerts) {
        //Exercise
        List<WebElement> alertBoxes = driver.findElements(By.xpath("//table[@border = '1']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@border = '1']")));

        //Verify
        Assertions.assertEquals(alerts,alertBoxes.size());

    }

//    @Given("I am an administrator of the website and I upload an alert of type <alert-type>")
//    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType() {
//        //Exercise
//        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//        driver.get("https://www.marketalertum.com");
//
//        driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();
//        driver.findElement(By.xpath("//input[@id = 'UserId']")).sendKeys("d98f7243-8f53-4523-b11f-f80fe9fe8230");
//        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
//
//        //upload an alert of type <alert-type>
//
//        //specifying alert type 1 - car etc.
//        //alertTypeProvider.car --> 1;
//        //alertTypeProvider.getAlertSection(1) --> car;
//    }
//
//    @And("the icon displayed should be <icon file name>")
//    public void theIconDisplayedShouldBeIconFileName() {
//        //whatever the alert type number it matches with icon file
//        //car --> car img
//        //alertTypeProvider.getAlertSection(1 --> car img
//    }
}
