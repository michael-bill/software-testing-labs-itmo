package ru.michael.math.trigonometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {
    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("sin(0) = 0")
    void testSinZero() {
        double x = 0;
        double expected = 0.0;
        double result = SinFunction.sin(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("sin(pi) = 0")
    void testSinPi() {
        double x = Math.PI;
        double expected = 0.0;
        double result = SinFunction.sin(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("sin(pi / 2) = 1")
    void testSinHalfPi() {
        double x = Math.PI / 2;
        double expected = 1.0;
        double result = SinFunction.sin(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("sin(x) нечетная")
    void testOddFunction() {
        double x = 1.5;
        double resultPositive = SinFunction.sin(x, PRECISION);
        double resultNegative = SinFunction.sin(-x, PRECISION);
        assertEquals(resultPositive, -resultNegative, PRECISION);
    }

    @Test
    @DisplayName("SinFunction.sin(x) против Math.sin(x)")
    void testAgainstLibrary() {
        double epsilon = PRECISION;
        for (double x = -2 * Math.PI; x <= 2 * Math.PI; x += 0.001) {
            double expected = Math.sin(x);
            double result = SinFunction.sin(x, epsilon);
            assertEquals(expected, result, epsilon, "Failed at x = " + x);
        }
    }
}
