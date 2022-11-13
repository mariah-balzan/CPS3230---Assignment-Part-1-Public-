package org.example.utils;

public interface AlertTypeProvider {
    public static int CAR = 1;
    public static int BOAT = 2;
    public static int PROPERTYFORRENT = 3;
    public static int PROPERTYFORSALE = 4;
    public static int TOYS = 5;
    public static int ELECTRONIC = 6;

    public int getAlertSection();
}
