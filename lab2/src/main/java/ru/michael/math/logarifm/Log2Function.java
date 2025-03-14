package ru.michael.math.logarifm;

import static ru.michael.math.logarifm.LnFunction.ln;

public class Log2Function {
    public static double log_2(double x, double epsilon) {
        // log2(x) = ln(x) / ln(2)
        return ln(x, epsilon) / ln(2.0, epsilon);
    }
}
