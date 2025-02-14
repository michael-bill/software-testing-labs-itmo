package ru.michael.task3;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Task3Test {

    @Test
    @DisplayName("Артуру удается прокатиться по воздуху и попасть в нужное окно")
    void startSliding() {
        Arthur arthur = new Arthur();
        Window window = new Window(2, new Position(10, 10));

        arthur.startSliding(window);

        assertTrue(arthur.isSliding());
        assertEquals(window, arthur.getTargetWindow());
    }

    @Test
    @DisplayName("Артуру удается остановиться по время скольжения по воздуху")
    void stopSliding() {
        Arthur arthur = new Arthur();
        arthur.startSliding(new Window(2, new Position(0, 0)));
        arthur.stopSliding();

        assertFalse(arthur.isSliding());
        assertNull(arthur.getTargetWindow());
    }

    @Test
    @DisplayName("Толпа разоряется ликующими криками")
    void startCheering() {
        Crowd crowd = new Crowd();
        crowd.startCheering();
        assertTrue(crowd.isCheering());
    }

    @Test
    @DisplayName("Толпа успокоилась")
    void stopCheering() {
        Crowd crowd = new Crowd();
        crowd.startCheering();
        crowd.stopCheering();
        assertFalse(crowd.isCheering());
    }

    @Test
    @DisplayName("Окно успешно установилось в здание на втором этаже")
    void addWindow() {
        Building building = new Building();
        building.addWindow(new Window(2, new Position(5, 5)));
        assertEquals(1, building.getWindows(2).size());
    }

    @Test
    @DisplayName("Мы можем устанавливать и доставать несколько окон правильно")
    void getWindowsByFloor() {
        Building building = new Building();
        Window window1 = new Window(2, new Position(0, 0));
        Window window2 = new Window(3, new Position(0, 0));
        building.addWindow(window1);
        building.addWindow(window2);

        List<Window> secondFloorWindows = building.getWindows(2);
        assertTrue(secondFloorWindows.contains(window1));
        assertEquals(1, secondFloorWindows.size());
    }

    @Test
    @DisplayName("Оратор смог установиться на платформе и начать что-то говорить")
    void setSpeaker() {
        Platform platform = new Platform();
        Speaker speaker = new Speaker();
        platform.setSpeaker(speaker);
        assertTrue(platform.isSpeaking());
    }

}
