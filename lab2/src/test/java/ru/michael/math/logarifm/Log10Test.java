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

public class Log10Test {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void log10ShouldCallLnWithCorrectArguments() {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Настраиваем моки для ln(100) и ln(10)
            double x = 100.0;
            mockedLn.when(() -> LnFunction.ln(x, PRECISION)).thenReturn(4.605170185988092);
            mockedLn.when(() -> LnFunction.ln(10.0, PRECISION)).thenReturn(2.302585092994046);

            // Вызываем тестируемый метод
            double result = Log10Function.log_10(x, PRECISION);

            // Проверяем результат и вызовы
            assertEquals(2.0, result, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, PRECISION));
            mockedLn.verify(() -> LnFunction.ln(10.0, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void log10ShouldCallLnWithParameters(double x, double epsilon) {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Генерируем случайные значения для моков
            double mockLnX = Math.random();
            double mockLn10 = Math.random();

            // Настраиваем моки
            mockedLn.when(() -> LnFunction.ln(x, epsilon)).thenReturn(mockLnX);
            mockedLn.when(() -> LnFunction.ln(10.0, epsilon)).thenReturn(mockLn10);

            // Вычисляем ожидаемый результат
            double expected = mockLnX / mockLn10;
            double actual = Log10Function.log_10(x, epsilon);

            // Проверяем результат и вызовы
            assertEquals(expected, actual, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, epsilon));
            mockedLn.verify(() -> LnFunction.ln(10.0, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия стандартной библиотеке (без мока)")
    void log10ShouldMatchMathLog10(double x, double epsilon) {
        double actual = Log10Function.log_10(x, epsilon);
        double expected = Math.log10(x);
        assertEquals(expected, actual, epsilon * 100);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1.0, PRECISION),
                Arguments.of(100.0, PRECISION),
                Arguments.of(0.1, PRECISION),
                Arguments.of(2.0, PRECISION),
                Arguments.of(0.5, PRECISION),
                Arguments.of(10000.0, PRECISION),
                Arguments.of(1e-5, PRECISION)
        );
    }
}
