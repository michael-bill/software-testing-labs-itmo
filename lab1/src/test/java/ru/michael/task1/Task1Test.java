package ru.michael.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Task1Test {

    private static final double PRECISION = 1e-15;
    private static final int TERMS = 30;

    @Test
    @DisplayName("cos(0) = 1")
    void testCosZero() {
        double x = 0;
        double expected = 1.0;
        double result = TaylorSeries.cos(x, TERMS);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("cos(pi) = -1")
    void testCosPi() {
        double x = Math.PI;
        double expected = -1.0;
        double result = TaylorSeries.cos(x, TERMS);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("cos(pi / 2) = 0")
    void testCosHalfPi() {
        double x = Math.PI / 2;
        double expected = 0.0;
        double result = TaylorSeries.cos(x, TERMS);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("cos(x) четная")
    void testEvenFunction() {
        double x = 1.5;
        double resultPositive = TaylorSeries.cos(x, TERMS);
        double resultNegative = TaylorSeries.cos(-x, TERMS);
        assertEquals(resultPositive, resultNegative, PRECISION);
    }

    @Test
    @DisplayName("TaylorSeries.cos(x) против Math.cos(x)")
    void testAgainstLibrary() {
        for (double x = -2 * Math.PI; x <= 2 * Math.PI; x += 0.001) {
            double expected = Math.cos(x);
            double result = TaylorSeries.cos(x, TERMS);
            assertEquals(expected, result, PRECISION);
        }
    }

    @Test
    @DisplayName("Уменьшение ошибки при увеличении количества итераций")
    void testError() {
        double x = 2.0;
        double prevError = Double.MAX_VALUE;
        for (int terms = 2; terms <= 10; terms++) {
            double result = TaylorSeries.cos(x, terms);
            double error = Math.abs(result - Math.cos(x));
            assertTrue(error < prevError);
            prevError = error;
        }
    }

}
