package ru.michael.math.trigonometry;

import static java.lang.Math.PI;
import static ru.michael.math.trigonometry.SinFunction.sin;

public class CosFunction {
    public static double cos(double x, double epsilon) {
        // cos(x) = sin(x + π/2)
        return sin(x + PI / 2, epsilon);
    }
}
