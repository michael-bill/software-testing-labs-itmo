package ru.michael.math.logarifm;

import static ru.michael.math.logarifm.LnFunction.ln;

public class Log5Function {
    public static double log_5(double x, double epsilon) {
        // log5(x) = ln(x) / ln(5)
        return ln(x, epsilon) / ln(5.0, epsilon);
    }
}
