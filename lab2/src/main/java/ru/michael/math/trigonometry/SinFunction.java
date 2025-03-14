package ru.michael.math.trigonometry;

public class SinFunction {
    public static double sin(double x, double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Погрешность должна быть положительной");
        }

        // Приведение x к диапазону [0, 2π]
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        int sign = 1;
        if (x > Math.PI) {
            x = 2 * Math.PI - x;
            sign = -1;
        }
        if (x > Math.PI / 2) {
            x = Math.PI - x;
        }

        double result = 0.0;
        double term = x; // Первый член ряда: x
        result += term;
        int n = 1;

        while (Math.abs(term) >= epsilon) {
            term *= -x * x / ((2 * n) * (2 * n + 1));
            result += term;
            n++;

            // Защита от расходящихся рядов
            if (n > 1000) {
                break;
            }
        }

        return sign * result;
    }
}
