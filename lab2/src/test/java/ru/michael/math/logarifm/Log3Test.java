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

public class Log3Test {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void log3ShouldCallLnWithCorrectArguments() {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Настраиваем моки для ln(9) и ln(3)
            double x = 9.0;
            mockedLn.when(() -> LnFunction.ln(x, PRECISION)).thenReturn(2.1972245773362196);
            mockedLn.when(() -> LnFunction.ln(3.0, PRECISION)).thenReturn(1.0986122886681098);

            // Вызываем тестируемый метод
            double result = Log3Function.log_3(x, PRECISION);

            // Проверяем результат и вызовы
            assertEquals(2.0, result, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, PRECISION));
            mockedLn.verify(() -> LnFunction.ln(3.0, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void log3ShouldCallLnWithParameters(double x, double epsilon) {
        try (MockedStatic<LnFunction> mockedLn = mockStatic(LnFunction.class)) {
            // Генерируем случайные значения для моков
            double mockLnX = Math.random();
            double mockLn3 = Math.random();

            // Настраиваем моки
            mockedLn.when(() -> LnFunction.ln(x, epsilon)).thenReturn(mockLnX);
            mockedLn.when(() -> LnFunction.ln(3.0, epsilon)).thenReturn(mockLn3);

            // Вычисляем ожидаемый результат
            double expected = mockLnX / mockLn3;
            double actual = Log3Function.log_3(x, epsilon);

            // Проверяем результат и вызовы
            assertEquals(expected, actual, PRECISION);
            mockedLn.verify(() -> LnFunction.ln(x, epsilon));
            mockedLn.verify(() -> LnFunction.ln(3.0, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия стандартным вычислениям (без мока)")
    void log3ShouldMatchMathLog(double x, double epsilon) {
        double actual = Log3Function.log_3(x, epsilon);
        double expected = Math.log(x) / Math.log(3.0);
        assertEquals(expected, actual, epsilon * 100);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1.0, PRECISION),
                Arguments.of(9.0, PRECISION),
                Arguments.of(27.0, PRECISION),
                Arguments.of(0.3333333333, PRECISION),
                Arguments.of(0.1111111111, PRECISION),
                Arguments.of(81.0, PRECISION),
                Arguments.of(1e-5, PRECISION)
        );
    }
}
