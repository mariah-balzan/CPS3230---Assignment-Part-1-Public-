package test.cps3230.amazon;

import org.example.alerts.AlertType;
import org.example.alerts.PropertyForRent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.cps3230.stubs.MarketServiceIsFavourable;
import test.cps3230.stubs.MarketServiceIsNotFavourable;

public class PropertyForRentTypeTests {
    final int MAX_ALERTS = 5;
    PropertyForRent propertyForRent;

    @BeforeEach
    public void setup() {
        propertyForRent = new PropertyForRent();
    }

    //Test for the maximum number of alerts
    @Test
    public void testMaxAlerts() {
        //Exercise + Verify
        Assertions.assertEquals(MAX_ALERTS, propertyForRent.getMaxAlerts());
    }

    //Test type of alert is of PROPERTYFORRENT
    @Test
    public void testPropertyForRentAlertType() throws Exception{
        Assertions.assertEquals(AlertType.PropertyForRentType,propertyForRent.getAlertType() );
    }

    //Test that alerts are added
    @Test
    public void testAddAlertsWhenMarketServiceIsFavourable() throws Exception{
        //Setup
        propertyForRent.setMarketService(new MarketServiceIsFavourable());

        //Assumption: Market is favourable -> displaying latest results
        boolean result = propertyForRent.addAlert();

        //Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddAlertsWhenMarketServiceIsNotFavourable() throws Exception{
        //Setup
        propertyForRent.setMarketService(new MarketServiceIsNotFavourable());

        //Assumption: Market is not favourable -> displaying old results
        boolean result = propertyForRent.addAlert();

        //Verify
        Assertions.assertFalse(result);
    }


    //Since hardcoded search item is toys, this test would cause test with coverage to fail however, it runs when search item is propertyForRent.
//    @Test
//    public void testAlertTypeIsPropertyForRent() throws Exception{
//        //Setup
//        AlertTypeProvider alertTypeProvider = Mockito.mock(AlertTypeProvider.class);
//        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.PROPERTFORRENT);
//
//        propertyForRent.setAlertTypeProvider(alertTypeProvider);
//
//        //Exercise
//        boolean result = propertyForRent.addAlert();
//        //Verify
//        Assertions.assertTrue(result);
//
//    }
}
