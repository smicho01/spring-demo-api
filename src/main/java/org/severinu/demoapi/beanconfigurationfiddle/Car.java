package org.severinu.demoapi.beanconfigurationfiddle;

public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        System.out.println("Car is starting with " + engine.getType() + " engine.");
    }
}