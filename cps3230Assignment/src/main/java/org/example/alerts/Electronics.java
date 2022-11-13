package org.example.alerts;

import java.util.List;

public class Electronics extends Alert{

    //Max alerts
    public int getMaxAlerts() {
        return 5;
    }

    //Type of alert
    public AlertType getAlertType() {
        return AlertType.ElectronicType;
    }
}
