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

public class Log2Test {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void log2ShouldCallLnWithCorrectArguments() {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Настраиваем моки для ln(4) и ln(2)
            double x = 4.0;
            mockedLn.when(() -> LnFunction.ln(x, PRECISION)).thenReturn(1.3862943611198906);
            mockedLn.when(() -> LnFunction.ln(2.0, PRECISION)).thenReturn(0.6931471805599453);

            // Вызываем тестируемый метод
            double result = Log2Function.log_2(x, PRECISION);

            // Проверяем результат и вызовы
            assertEquals(2.0, result, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, PRECISION));
            mockedLn.verify(() -> LnFunction.ln(2.0, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void log2ShouldCallLnWithParameters(double x, double epsilon) {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Генерируем случайные значения для моков
            double mockLnX = Math.random();
            double mockLn2 = Math.random();

            // Настраиваем моки
            mockedLn.when(() -> LnFunction.ln(x, epsilon)).thenReturn(mockLnX);
            mockedLn.when(() -> LnFunction.ln(2.0, epsilon)).thenReturn(mockLn2);

            // Вычисляем ожидаемый результат
            double expected = mockLnX / mockLn2;
            double actual = Log2Function.log_2(x, epsilon);

            // Проверяем результат и вызовы
            assertEquals(expected, actual, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, epsilon));
            mockedLn.verify(() -> LnFunction.ln(2.0, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия стандартным вычислениям (без мока)")
    void log2ShouldMatchMathLog(double x, double epsilon) {
        double actual = Log2Function.log_2(x, epsilon);
        double expected = Math.log(x) / Math.log(2.0);
        assertEquals(expected, actual, epsilon * 100);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1.0, PRECISION),
                Arguments.of(4.0, PRECISION),
                Arguments.of(8.0, PRECISION),
                Arguments.of(16.0, PRECISION),
                Arguments.of(0.5, PRECISION),
                Arguments.of(0.25, PRECISION),
                Arguments.of(0.125, PRECISION),
                Arguments.of(1e-5, PRECISION)
        );
    }
}
