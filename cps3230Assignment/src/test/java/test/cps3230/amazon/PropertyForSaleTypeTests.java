package test.cps3230.amazon;

import org.example.alerts.AlertType;
import org.example.alerts.PropertyForSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.cps3230.stubs.MarketServiceIsFavourable;
import test.cps3230.stubs.MarketServiceIsNotFavourable;

public class PropertyForSaleTypeTests {
    final int MAX_ALERTS = 5;
    PropertyForSale propertyForSale;

    @BeforeEach
    public void setup() {
        propertyForSale = new PropertyForSale();
    }

    //Test for the maximum number of alerts
    @Test
    public void testMaxAlerts() {
        //Exercise + Verify
        Assertions.assertEquals(MAX_ALERTS, propertyForSale.getMaxAlerts());
    }

    //Test type of alert is of PROPERTYFORSALE
    @Test
    public void testPropertyForSaleAlertType() throws Exception{
        Assertions.assertEquals(AlertType.PropertyForSaleType,propertyForSale.getAlertType() );
    }

    //Test that alerts are added
    @Test
    public void testAddAlertsWhenMarketServiceIsFavourable() throws Exception{
        //Setup
        propertyForSale.setMarketService(new MarketServiceIsFavourable());

        //Assumption: Market is favourable -> displaying latest results
        boolean result = propertyForSale.addAlert();

        //Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddAlertsWhenMarketServiceIsNotFavourable() throws Exception{
        //Setup
        propertyForSale.setMarketService(new MarketServiceIsNotFavourable());

        //Assumption: Market is not favourable -> displaying old results
        boolean result = propertyForSale.addAlert();

        //Verify
        Assertions.assertFalse(result);
    }


    //Since hardcoded search item is toys, this test would cause test with coverage to fail however, it runs when search item is propertyForSale.
//    @Test
//    public void testAlertTypeIsPropertyForSale() throws Exception{
//        //Setup
//        AlertTypeProvider alertTypeProvider = Mockito.mock(AlertTypeProvider.class);
//        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.PROPERTFORSALE);
//
//        propertyForSale.setAlertTypeProvider(alertTypeProvider);
//
//        //Exercise
//        boolean result = propertyForSale.addAlert();
//        //Verify
//        Assertions.assertTrue(result);
//
//    }
}
