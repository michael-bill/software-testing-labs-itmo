package ru.michael.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Building {
    private final Map<Integer, List<Window>> windows = new HashMap<>();

    public void addWindow(Window window) {
        windows.computeIfAbsent(window.getFloor(), x -> new ArrayList<>()).add(window);
        System.out.printf("Добавлено окно в здание на %d этаже\n", window.getFloor());
    }

    public List<Window> getWindows(int floor) {
        return windows.getOrDefault(floor, new ArrayList<>());
    }
}
