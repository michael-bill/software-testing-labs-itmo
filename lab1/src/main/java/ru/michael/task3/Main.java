package ru.michael.task3;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(2, new Position(150, 300));
        Building townHall = new Building();
        townHall.addWindow(window);

        Platform platform = new Platform();
        platform.setSpeaker(new Speaker());

        Crowd crowd = new Crowd();
        crowd.startCheering();
        Arthur arthur = new Arthur();
        arthur.startSliding(window);
        arthur.stopSliding();
        crowd.stopCheering();
    }
}
