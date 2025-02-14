package ru.michael.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Platform {
    private Speaker speaker;

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
        System.out.println("Оратор взошел на помост и начал речь.");
    }

    public boolean isSpeaking() {
        return speaker != null;
    }

    public void stopSpeaking() {
        speaker = null;
        System.out.println("Оратор ушел с помоста.");
    }
}
