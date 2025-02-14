package ru.michael.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Arthur {
    private boolean isSliding;
    private Window targetWindow;

    public void startSliding(Window window) {
        this.isSliding = true;
        this.targetWindow = window;
        System.out.printf("Артур начал скользить по воздуху к окну на %d этаже!\n", window.getFloor());
    }

    public void stopSliding() {
        this.isSliding = false;
        this.targetWindow = null;
        System.out.println("Артур остановился.");
    }
}
