package org.severinu.demoapi.observerpattern.weatherstation;

public class PhoneDisplay implements WeatherObserver{
    @Override
    public void update(float temperature) {
        System.out.println("Phone Display: The temperature is " + temperature);
    }
}

class TVDisplay implements WeatherObserver{
    @Override
    public void update(float temperature) {
        System.out.println("TV Display: The temperature is " + temperature);
    }
}

class ComputerDisplay implements WeatherObserver{
    @Override
    public void update(float temperature) {
        System.out.println("Computer Display: The temperature is " + temperature);
    }
}