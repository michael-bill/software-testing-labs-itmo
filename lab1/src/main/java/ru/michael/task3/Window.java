package ru.michael.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Window {
    private final int floor;
    private final Position position;

    public Window(int floor, Position position) {
        this.floor = floor;
        this.position = position;
        System.out.printf("Создано окно: этаж %d, позиция %s\n", floor, position);
    }
}
