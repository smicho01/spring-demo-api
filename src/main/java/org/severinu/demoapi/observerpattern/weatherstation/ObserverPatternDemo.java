package org.severinu.demoapi.observerpattern.weatherstation;

public class ObserverPatternDemo {

    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        PhoneDisplay phoneDisplay = new PhoneDisplay();
        TVDisplay tvDisplay = new TVDisplay();
        ComputerDisplay computerDisplay = new ComputerDisplay();

        weatherStation.addObserver(phoneDisplay);
        weatherStation.addObserver(tvDisplay);
        weatherStation.addObserver(computerDisplay);

        weatherStation.setTemperature(25.0f);
        weatherStation.setTemperature(31.2f);

        weatherStation.removeObserver(phoneDisplay);
        System.out.println("=== Removed Phone Display ===");
        weatherStation.setTemperature(22.0f);

    }
}
