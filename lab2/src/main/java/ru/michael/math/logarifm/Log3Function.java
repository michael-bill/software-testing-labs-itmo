package ru.michael.math.logarifm;

import static ru.michael.math.logarifm.LnFunction.ln;

public class Log3Function {
    public static double log_3(double x, double epsilon) {
        // log3(x) = ln(x) / ln(3)
        return ln(x, epsilon) / ln(3.0, epsilon);
    }
}
