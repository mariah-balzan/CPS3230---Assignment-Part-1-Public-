package test.cps3230.amazon;

import org.example.alerts.AlertType;
import org.example.alerts.Toys;
import org.example.utils.AlertTypeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.cps3230.stubs.MarketServiceIsFavourable;
import test.cps3230.stubs.MarketServiceIsNotFavourable;

public class ToysTypeTest {
    final int MAX_ALERTS = 5;
    Toys toys;

    @BeforeEach
    public void setup() {
        toys = new Toys();
    }

    //Test for the maximum number of alerts
    @Test
    public void testMaxAlerts() {
        //Exercise + Verify
        Assertions.assertEquals(MAX_ALERTS, toys.getMaxAlerts());
    }

    //Test type of alert is of TOYS
    @Test
    public void testToysAlertType() throws Exception{
        Assertions.assertEquals(AlertType.ToysType,toys.getAlertType() );
    }

    //Test that alerts are added
    @Test
    public void testAddAlertsWhenMarketServiceIsFavourable() throws Exception{
        //Setup
        toys.setMarketService(new MarketServiceIsFavourable());

        //Assumption: Market is favourable -> displaying latest results
        boolean result = toys.addAlert();

        //Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddAlertsWhenMarketServiceIsNotFavourable() throws Exception{
        //Setup
        toys.setMarketService(new MarketServiceIsNotFavourable());

        //Assumption: Market is not favourable -> displaying old results
        boolean result = toys.addAlert();

        //Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testAlertTypeIsToys() throws Exception{
        //Setup
        AlertTypeProvider alertTypeProvider = Mockito.mock(AlertTypeProvider.class);
        Mockito.when(alertTypeProvider.getAlertSection()).thenReturn(AlertTypeProvider.TOYS);

        toys.setAlertTypeProvider(alertTypeProvider);

        //Exercise
        boolean result = toys.addAlert();
        //Verify
        Assertions.assertTrue(result);

    }
}
