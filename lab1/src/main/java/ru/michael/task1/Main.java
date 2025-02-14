package ru.michael.task1;

public class Main {
    public static void main(String[] args) {
        for (double x = -2 * Math.PI; x < 2 * Math.PI; x += 0.1) {
            System.out.println("cos(" + x + ") = " + TaylorSeries.cos(x, 10));
        }
    }
}
