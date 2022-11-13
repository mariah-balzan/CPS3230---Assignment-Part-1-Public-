package test.cps3230.market;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.example.items.ItemDetails;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MarketRequestsTests {

    WebDriver driver;
    ItemDetails items;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.amazon.co.uk/");
        // Accept cookies box when amazon pages opens
        WebElement acceptAll = driver.findElement(By.id("a-autoid-0"));
        acceptAll.click();

        //Set particular item details
        items = new ItemDetails("WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog", "", "https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg", "1199" );
    }

    //teardown
    @AfterEach
    public void teardown(){
        driver.quit();
    }

    //REST API Requests Tests

    //Test for market status code
    @Test
    public void testGetRequest() {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("https://www.marketalertum.com")
                .asJson();

        Assert.assertEquals(200, jsonResponse.getStatus());
    }

    //Test post specific item to market and return 201 - successful request and item created
    @Test
    public void testPostRequest() {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.post("https://api.marketalertum.com/Alert")
                .header("Content-Type", "application/json")
                .body("{\"alertType\":5," +
                        "\"heading\":\"WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog \"," +
                        "\"description\":\" \"," +
                        "\"url\":\"https://www.amazon.co.uk/WeFine-Teething-Training%EF%BC%8C10pcs-Natural-Cotton/dp/B07WD8QPRB/ref=sr_1_5?crid=2O4UBUX7JYK78&keywords=WeFine+Puppy+Dog+Chew+Toys+Teething+Training%2C+10pcs+Dog+Rope+Toys+100%25+Natural+Cotton+Rope+for+Small+and+Medium+Dog&qid=1668250494&sprefix=wefine+puppy+dog+chew+toys+teething+training+10pcs+dog+rope+toys+100%25+natural+cotton+rope+for+small+and+medium+dog%2Caps%2C134&sr=8-5\"," +
                        "\"imageUrl\":\"https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg\"," +
                        "\"postedBy\":\"d98f7243-8f53-4523-b11f-f80fe9fe8230\",\"priceInCents\":1199}")
                .asJson();

        System.out.println(jsonResponse.getStatusText());

        Assert.assertEquals(201, jsonResponse.getStatus());
    }

    //Delete specific item and return 200 - request received and understood
    @Test
    public void testDeleteRequest() {
        HttpResponse<JsonNode> jsonResponse
        =Unirest.delete("https://api.marketalertum.com/Alert?userId=d98f7243-8f53-4523-b11f-f80fe9fe8230")
                .header("Content-Type", "application/json")
                .body(" {\"alertType\":5,\"heading\":\"WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog \",\"description\":\"\",\"url\":\"https://www.amazon.co.uk/WeFine-Teething-Training%EF%BC%8C10pcs-Natural-Cotton/dp/B07WD8QPRB/ref=sr_1_5?crid=2O4UBUX7JYK78&keywords=WeFine+Puppy+Dog+Chew+Toys+Teething+Training%2C+10pcs+Dog+Rope+Toys+100%25+Natural+Cotton+Rope+for+Small+and+Medium+Dog&qid=1668250494&sprefix=wefine+puppy+dog+chew+toys+teething+training+10pcs+dog+rope+toys+100%25+natural+cotton+rope+for+small+and+medium+dog%2Caps%2C134&sr=8-5\",\"imageUrl\":\"https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg\",\"postedBy\":\"d98f7243-8f53-4523-b11f-f80fe9fe8230\",\"priceInCents\":1199}")
                .asJson();

        System.out.println(jsonResponse.getStatusText());

        Assert.assertEquals(200, jsonResponse.getStatus());
    }

    //Test Heading getters for specific item
    @Test
    public void testSettersGetterHeading(){
        this.items.setHeading("WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog");
        Assertions.assertEquals("WeFine Puppy Dog Chew Toys Teething Training, 10pcs Dog Rope Toys 100% Natural Cotton Rope for Small and Medium Dog", this.items.getHeading());
    }

    //Test Description getters for specific item
    @Test
    public void testSettersGetterDescription(){
        this.items.setItemDetails("");
        Assertions.assertEquals("", this.items.getItemDetails());
    }

    //Test Image getters for specific item
//    @Test
//    public void testSettersGetterImage(){
//        this.items.setImage("https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg");
//        Assertions.assertEquals("https://m.media-amazon.com/images/I/61CMYjFxkfL._AC_SX679_.jpg", this.items.getImage());
//    }

    //Test Price getters for specific item
    @Test
    public void testSettersGetterPrice(){
        this.items.setPrice("1199");
        Assertions.assertEquals("1199", this.items.getPrice());
    }
}
