package org.example.alerts;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.example.market.MarketService;
import org.example.items.ItemDetails;
import org.example.utils.AlertTypeProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Alert {
    //Attributes & objects
    protected int MAX_ALERTS;
    protected static String uniqueID;
    static WebDriver driver;
    //Stores required details of each search item
    static ItemDetails items = new ItemDetails();
    //JSONObject object to transmit (.put) data (itemDetails)
    static JSONObject jsonObject = new JSONObject();
    public static MarketService marketService;
    public static AlertTypeProvider alertTypeProvider;

    //Setter injection
    public void setMarketService(MarketService marketService){
        this.marketService = marketService;
    }
    //Inject the interface with a setter method
    public void setAlertTypeProvider(AlertTypeProvider alertTypeProvider){
        this.alertTypeProvider = alertTypeProvider;
    }

    //Constructor for alert class
    public Alert(){
        MAX_ALERTS = 5;
    }

    //Method to return 5 max alerts
    public abstract int getMaxAlerts();

    //Method to return alert type
    public abstract AlertType getAlertType();

    //Setup - Gain access to a browser by selenium
    //Therefore, a chrome driver object will be created before each test and
    //fires up a new instance of chrome and runs under the control of the tests.
    public static void setup() {
        uniqueID = "d98f7243-8f53-4523-b11f-f80fe9fe8230";
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\webtesting\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        //Specified time for driver to wait in searching for element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Disbale Google cookies
        driver.manage().deleteAllCookies();
        driver.get("https://www.amazon.co.uk/");
        WebElement button = driver.findElement(By.id("sp-cc-accept"));
        button.click();
        WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
        searchField.sendKeys("dog toys");
        WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
        searchButton.submit();
    }

    public static boolean addAlert() throws Exception {

        //Call setup method
        Alert.setup();

        //PATTERNS: Check space in marketUM to actually add alerts to market
        if (marketService != null && !marketService.isMarketDisplayingLatestAlerts()) {
            return false;
        }

        //Assuming that first 5 elements are the latest items:
        //Find first 5 web elements - this also make sure that not more than the max alerts are clicked
        List<WebElement> link = driver.findElements(By.xpath("(//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal'])[position() < 6]"));
        //FileWriter file to send scraped values from json to file
        FileWriter file = new FileWriter("C:\\Users\\balza\\OneDrive\\Desktop\\cps3230Assignment\\search_file.json");

        //Loop for 5 latest items
                for (int i = 0; i < link.size(); i++) {
                    //MOCK SETUP ---- Check alert type to actually make a search
                    int alertType= AlertTypeProvider.TOYS;
                    jsonObject.put("alertType", alertType);
                    if (alertType == AlertTypeProvider.TOYS) {
                        //Click on first 5 results' link
                        link = driver.findElements(By.xpath("(//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal'])[position() < 6]"));
                        WebElement element = link.get(i);
                        element.click();
                        System.out.println("Item " + (i + 1) + ": ");
                        //Item Heading
                        Alert.itemHeading();
                        //Item description
                        Alert.itemDescription();
                        //System.out.println();
                        //Item URL
                        String strUrl = driver.getCurrentUrl();
                        jsonObject.put("url", strUrl);
                        //System.out.println();
                        //Item image
                        Alert.itemImage();
                        //System.out.println();
                        //to get user id
                        jsonObject.put("postedBy", uniqueID);
                        //to get product price - assuming in euro instead of pounds
                        Alert.itemPrice();
                        //System.out.println();
                        //Write json object to the file
                        file.write(jsonObject.toString());
                        //After details fetched, go back to results page for next item
                        driver.navigate().back();
                        //Output to display details of fetched item
                        System.out.println("JSON file created: " + jsonObject +"\n");

                        //POST Request to send item to marketUM website after web scraping
                        Unirest.post("https://api.marketalertum.com/Alert")
                                .header("Content-Type", "application/json")
                                .body(jsonObject.toString())
                                .asJson();
                    } else{
                        //Item will not be scraped if search does not match any specific alert types(1-6)
                        System.out.println("No type found");
                        return false;
                    }
        }
        return true;
    }


    //Method - get item heading
    public static void itemHeading(){
        List<WebElement> itemHeading = driver.findElements(By.xpath("//span[@id='productTitle']"));
        for(int t =0;t<itemHeading.size();t++) {
            System.out.println("Heading: " + itemHeading.get(t).getText());
            items.setItemDetails(itemHeading.get(t).getText());
            jsonObject.put("heading", itemHeading.get(t).getText());
        }
        //System.out.println();
    }

    //Method - get item description
    public static void itemDescription(){
        List<WebElement> itemDetails = driver.findElements(By.xpath("//ul[contains(@class,'a-unordered-list a-vertical')]/li"));
        System.out.print("Details: ");
        for (WebElement details: itemDetails){
            System.out.print(details.getText());
            items.setItemDetails(details.getText());
            jsonObject.put("description", details.getText());
        }
       // System.out.println();
    }

    //Method - get item image
    public static void itemImage(){
        List<WebElement> itemImage = driver.findElements(By.xpath("//*[@id=\"landingImage\"]"));
        for(WebElement image: itemImage) {
            System.out.println("Image: " + image.getAttribute("src"));
            items.setImage(image.getAttribute("src"));
            jsonObject.put("imageUrl", image.getAttribute("src"));
        }

       // System.out.println();
    }

    //Method - get item price/s
    public static void itemPrice(){
        List<WebElement> priceType1 = driver.findElements(By.xpath("//span[@id = 'sns-base-price']"));
        List<WebElement> priceType2 = driver.findElements(By.xpath("//span[@class = 'a-price aok-align-center reinventPricePriceToPayMargin priceToPay']"));
        List<WebElement> priceType3 = driver.findElements(By.xpath("//span[@id = 'a-size-base a-color-price a-color-price']"));

        //Nested if statement for different price types
        //if price type one:
        if(!priceType1.isEmpty()){
            for (WebElement price: priceType1){
                String pt = price.getText().replace("£","").replace(".","").replace("\n","");
                System.out.println("Product Price: " + pt + "c");
                items.setPrice(price.getText());
                jsonObject.put("priceInCents", Integer.parseInt(pt));
            }
        }
        //if price type two:
        else if (!priceType2.isEmpty()){
            for (WebElement price: priceType2){
                String pt = price.getText().replace("£","").replace(".","").replace("\n","");
                System.out.println("Price: " + pt + "c");
                items.setPrice(price.getText());
                jsonObject.put("priceInCents", Integer.parseInt(pt));
            }
        }
        //if price type three:
        else if (!priceType3.isEmpty()){
            for (WebElement price: priceType3){
                String pt = price.getText().replace("£","").replace(".","").replace("\n","");
                System.out.println("Price: " + pt + "c");
                items.setPrice(price.getText());
                jsonObject.put("priceInCents", Integer.parseInt(pt));
            }
        }
        //if not covered by above if statements (not manage to implement well):
        else {
            System.out.println("No price Value found");
        }
    }
}
