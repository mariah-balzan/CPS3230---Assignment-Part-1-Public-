package org.example.alerts;

public class PropertyForSale extends Alert{
    //Max alerts
    public int getMaxAlerts() {
        return 5;
    }

    //Type of alert
    public AlertType getAlertType() {
        return AlertType.PropertyForSaleType;
    }
}
