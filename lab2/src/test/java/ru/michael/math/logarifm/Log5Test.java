package ru.michael.math.logarifm;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class Log5Test {

    private static final double PRECISION = 1e-8;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void log5ShouldCallLnWithCorrectArguments() {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Настраиваем моки для ln(25) и ln(5)
            double x = 25.0;
            mockedLn.when(() -> LnFunction.ln(x, PRECISION)).thenReturn(3.2188758248682006);
            mockedLn.when(() -> LnFunction.ln(5.0, PRECISION)).thenReturn(1.6094379124341003);

            // Вызываем тестируемый метод
            double result = Log5Function.log_5(x, PRECISION);

            // Проверяем результат и вызовы
            assertEquals(2.0, result, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, PRECISION));
            mockedLn.verify(() -> LnFunction.ln(5.0, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void log5ShouldCallLnWithParameters(double x, double epsilon) {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Генерируем случайные значения для моков
            double mockLnX = Math.random();
            double mockLn5 = Math.random();

            // Настраиваем моки
            mockedLn.when(() -> LnFunction.ln(x, epsilon)).thenReturn(mockLnX);
            mockedLn.when(() -> LnFunction.ln(5.0, epsilon)).thenReturn(mockLn5);

            // Вычисляем ожидаемый результат
            double expected = mockLnX / mockLn5;
            double actual = Log5Function.log_5(x, epsilon);

            // Проверяем результат и вызовы
            assertEquals(expected, actual, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, epsilon));
            mockedLn.verify(() -> LnFunction.ln(5.0, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия стандартным вычислениям (без мока)")
    void log5ShouldMatchMathLog(double x, double epsilon) {
        double actual = Log5Function.log_5(x, epsilon);
        double expected = Math.log(x) / Math.log(5.0);  // Вычисление log5 через натуральный логарифм
        assertEquals(expected, actual, epsilon * 100);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1.0, PRECISION),
                Arguments.of(25.0, PRECISION),
                Arguments.of(0.2, PRECISION),    // 1/5
                Arguments.of(125.0, PRECISION),  // 5^3
                Arguments.of(0.04, PRECISION),   // 1/25
                Arguments.of(625.0, PRECISION),  // 5^4
                Arguments.of(1e-5, PRECISION)
        );
    }
}
