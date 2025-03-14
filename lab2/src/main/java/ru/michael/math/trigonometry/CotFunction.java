package ru.michael.math.trigonometry;

import static ru.michael.math.trigonometry.CosFunction.cos;
import static ru.michael.math.trigonometry.SinFunction.sin;

public class CotFunction {
    public static double cot(double x, double epsilon) {
        // cot(x) = cos(x) / sin(x)
        double sinVal = sin(x, epsilon);
        if (sinVal == 0) throw new ArithmeticException("cot неопределен для этого x = " + x);
        return cos(x, epsilon) / sinVal;
    }
}
