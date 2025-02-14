package ru.michael.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crowd {
    private boolean isCheering;

    public void startCheering() {
        this.isCheering = true;
        System.out.println("Толпа разразилась ликующими криками!");
    }

    public void stopCheering() {
        this.isCheering = false;
        System.out.println("Толпа успокоилась.");
    }
}
