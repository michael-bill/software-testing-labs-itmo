package ru.michael.math.logarifm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LnTest {
    private static final double PRECISION = 1e-10;
    private static final double E = Math.E;

    @Test
    @DisplayName("ln(1) = 0")
    void testLnOne() {
        double x = 1.0;
        double expected = 0.0;
        double result = LnFunction.ln(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("ln(e) = 1")
    void testLnE() {
        double x = E;
        double expected = 1.0;
        double result = LnFunction.ln(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("ln(1/e) = -1")
    void testLnInverseE() {
        double x = 1/E;
        double expected = -1.0;
        double result = LnFunction.ln(x, PRECISION);
        assertEquals(expected, result, PRECISION);
    }

    @Test
    @DisplayName("Свойство ln(a*b) = ln(a) + ln(b)")
    void testLogarithmProperty() {
        double a = 2.5;
        double b = 3.2;
        double lnProduct = LnFunction.ln(a * b, PRECISION);
        double sumLogs = LnFunction.ln(a, PRECISION) + LnFunction.ln(b, PRECISION);
        assertEquals(lnProduct, sumLogs, PRECISION);
    }

    @Test
    @DisplayName("LnFunction.ln(x) против Math.log(x)")
    void testAgainstLibrary() {
        double epsilon = PRECISION;
        // Тестируем в диапазоне (0, 2] для лучшей сходимости ряда
        for (double x = 0.1; x <= 2.0; x += 0.001) {
            double expected = Math.log(x);
            double result = LnFunction.ln(x, epsilon);
            assertEquals(expected, result, epsilon * 10, "Failed at x = " + x);
        }
    }

    @Test
    @DisplayName("Обработка недопустимых значений")
    void testInvalidInput() {
        assertThrows(ArithmeticException.class,
                () -> LnFunction.ln(-1.0, PRECISION));
        assertThrows(ArithmeticException.class,
                () -> LnFunction.ln(0.0, PRECISION));
    }
}
