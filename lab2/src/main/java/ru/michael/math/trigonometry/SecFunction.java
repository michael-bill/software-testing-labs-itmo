package ru.michael.math.trigonometry;

import static ru.michael.math.trigonometry.CosFunction.cos;

public class SecFunction {
    public static double sec(double x, double epsilon) {
        // sec(x) = 1 / cos(x)
        double cosVal = cos(x, epsilon);
        if (cosVal == 0) throw new ArithmeticException("sec неопределен для этого x = " + x);
        return 1.0 / cosVal;
    }
}
