package ru.michael.math.trigonometry;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class CosTest {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void cosShouldCallSinWithShiftedArgument() {
        try (MockedStatic<SinFunction> mockedSin = mockStatic(SinFunction.class)) {
            // Настраиваем мок
            mockedSin.when(() -> SinFunction.sin(PI, PRECISION))
                    .thenReturn(0.0);

            // Вызываем тестируемый метод
            double result = CosFunction.cos(PI / 2, PRECISION);

            // Проверяем результат и вызов метода
            assertEquals(0.0, result, PRECISION);
            mockedSin.verify(() -> SinFunction.sin(PI, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void cosShouldCallSinWithCorrectParameters(double x, double epsilon) {
        try (MockedStatic<SinFunction> mockedSin = mockStatic(SinFunction.class)) {
            // Ожидаемый аргумент для sin
            double expectedArgument = x + PI / 2;

            // Настраиваем мок возвращать случайное значение
            double mockValue = Math.random();
            mockedSin.when(() -> SinFunction.sin(expectedArgument, epsilon))
                    .thenReturn(mockValue);

            // Вызываем тестируемый метод
            double result = CosFunction.cos(x, epsilon);

            // Проверяем корректность вызова и возврата значения
            mockedSin.verify(() -> SinFunction.sin(expectedArgument, epsilon));
            assertEquals(mockValue, result, PRECISION);
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия стандартной библиотеке (без мока)")
    void cosShouldMatchMathCos(double x, double epsilon) {
        // Вычисляем значение с помощью нашей реализации
        double actual = CosFunction.cos(x, epsilon);

        // Ожидаемое значение через стандартную библиотеку
        double expected = Math.cos(x);

        // Проверяем совпадение с точностью до epsilon
        assertEquals(expected, actual, epsilon);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(0.0, PRECISION),
                Arguments.of(PI / 3, PRECISION),
                Arguments.of(PI / 4, PRECISION),
                Arguments.of(PI / 2, PRECISION),
                Arguments.of(3 * PI / 2, PRECISION),
                Arguments.of(2 * PI, PRECISION),
                Arguments.of(-PI / 4, PRECISION),
                Arguments.of(100 * PI, PRECISION)
        );
    }
}