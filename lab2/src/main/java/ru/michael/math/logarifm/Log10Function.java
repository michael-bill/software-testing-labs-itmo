package ru.michael.math.logarifm;

import static ru.michael.math.logarifm.LnFunction.ln;

public class Log10Function {
    public static double log_10(double x, double epsilon) {
        // log10(x) = ln(x) / ln(10)
        return ln(x, epsilon) / ln(10.0, epsilon);
    }
}
