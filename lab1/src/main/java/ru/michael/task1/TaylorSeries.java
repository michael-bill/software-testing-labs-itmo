package ru.michael.task1;

public class TaylorSeries {

    public static double cos(double x, int terms) {
        // Приведение x к диапазону [0, π]
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        if (x > Math.PI) {
            x = 2 * Math.PI - x;
        }

        double result = 1.0;
        double term = 1.0;

        for (int n = 1; n < terms; n++) {
            // Ряд Тейлора для косинуса
            term *= -x * x / ((2 * n) * (2 * n - 1));
            result += term;
        }

        return result;
    }
}