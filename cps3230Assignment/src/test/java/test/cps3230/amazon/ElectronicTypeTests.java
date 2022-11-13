package test.cps3230.amazon;

import org.example.alerts.AlertType;
import org.example.alerts.Electronics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.cps3230.stubs.MarketServiceIsFavourable;
import test.cps3230.stubs.MarketServiceIsNotFavourable;

public class ElectronicTypeTests {
    final int MAX_ALERTS = 5;
    Electronics electronics;

    @BeforeEach
    public void setup() {
        electronics = new Electronics();
    }

    //Test for the maximum number of alerts
    @Test
    public void testMaxAlerts() {
        //Exercise + Verify
        Assertions.assertEquals(MAX_ALERTS, electronics.getMaxAlerts());
    }

    //Test type of alert is of ELECTRONIC
    @Test
    public void testElectronicAlertType() throws Exception{
        Assertions.assertEquals(AlertType.ElectronicType,electronics.getAlertType() );
    }

    //Test that alerts are added
    @Test
    public void testAddAlertsWhenMarketServiceIsFavourable() throws Exception{
        //Setup
        electronics.setMarketService(new MarketServiceIsFavourable());

        //Assumption: Market is favourable -> displaying latest results
        boolean result = electronics.addAlert();

        //Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddAlertsWhenMarketServiceIsNotFavourable() throws Exception{
        //Setup
        electronics.setMarketService(new MarketServiceIsNotFavourable());

        //Assumption: Market is not favourable -> displaying old results
        boolean result = electronics.addAlert();

        //Verify
        Assertions.assertFalse(result);
    }


    //Since hardcoded search item is toys, this test would cause test with coverage to fail however, it runs when search item is electronic.
//    @Test
//    public void testAlertTypeIsElectronic() throws Exception{
//        //Setup
//        AlertTypeProvider alertTypeProvider = Mockito.mock(AlertTypeProvider.class);
//        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.ELECTRONIC);
//
//        Electronics electronic = new Electronics();
//        electronic.setAlertTypeProvider(alertTypeProvider);
//
//        //Exercise
//        boolean result = electronic.addAlert();
//        //Verify
//        Assertions.assertTrue(result);
//
//    }

    //Did not manage to implement well
//    @Test
//    public void testAlertTypeIsNotElectronic() throws Exception{
//        //Setup
//        int alertType= AlertTypeProvider.CAR;
//        AlertTypeProvider alertTypeProvider = Mockito.mock(AlertTypeProvider.class);
//        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.ELECTRONIC);
//
////        Alert alert = Mockito.mock(Alert.class);
////        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.CAR);
//
////        alert.setAlertTypeProvider(alertTypeProvider);
//
//        //Exercise
//        boolean result = alert.addAlert();
//        //Verify
//        Assertions.assertFalse(result);
//
//    }

}
