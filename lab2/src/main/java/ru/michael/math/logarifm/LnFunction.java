package ru.michael.math.logarifm;

public class LnFunction {
    public static double ln(double x, double epsilon) {
        if (x <= 0) {
            throw new ArithmeticException("ln неопределен для этого x = " + x);
        }
        if (x == 1.0) {
            return 0.0;
        }

        double t = (x - 1) / (x + 1);
        double tSquared = t * t;
        double factor = 1 - tSquared;
        double term = t;
        double sum = term;
        int n = 1;

        while (true) {
            term *= tSquared * (2 * n - 1) / (2 * n + 1);
            if (Math.abs(term) < epsilon * factor) {
                break;
            }
            sum += term;
            n++;
        }

        return 2 * sum;
    }
}
