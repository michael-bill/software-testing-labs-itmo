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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class SecTest {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void secShouldCallCosWithCorrectArgument() {
        try (MockedStatic<CosFunction> mockedCos = mockStatic(CosFunction.class)) {
            // Настраиваем мок для cos(PI/3)
            double x = PI/3;
            double mockCosValue = 0.5;
            mockedCos.when(() -> CosFunction.cos(x, PRECISION))
                    .thenReturn(mockCosValue);

            // Вызываем тестируемый метод
            double result = SecFunction.sec(x, PRECISION);

            // Проверяем результат и вызов
            assertEquals(2.0, result, PRECISION);
            mockedCos.verify(() -> CosFunction.cos(x, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void secShouldCallCosWithParameters(double x, double epsilon) {
        try (MockedStatic<CosFunction> mockedCos = mockStatic(CosFunction.class)) {
            // Генерируем случайное значение для мока (исключая ноль)
            double mockCosValue = Math.random() + 0.1;

            // Настраиваем мок
            mockedCos.when(() -> CosFunction.cos(x, epsilon))
                    .thenReturn(mockCosValue);

            // Вычисляем ожидаемый результат
            double expected = 1.0 / mockCosValue;
            double actual = SecFunction.sec(x, epsilon);

            // Проверяем результат и вызов
            assertEquals(expected, actual, PRECISION);
            mockedCos.verify(() -> CosFunction.cos(x, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия математической формуле (без мока)")
    void secShouldMatchMathFormula(double x, double epsilon) {
        double actual = SecFunction.sec(x, epsilon);
        double expected = 1.0 / Math.cos(x);
        assertEquals(expected, actual, epsilon);
    }

    @Test
    @DisplayName("Проверка нелегальных значений")
    void secShouldThrowIllegalArgumentException() {
        assertThrows(ArithmeticException.class, () -> SecFunction.sec(PI / 2, PRECISION));
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(0.0, PRECISION),
                Arguments.of(PI / 6, PRECISION),
                Arguments.of(PI / 4, PRECISION),
                Arguments.of(PI / 3, PRECISION),
                Arguments.of(-PI / 6, PRECISION),
                Arguments.of(2 * PI / 3, PRECISION),
                Arguments.of(1.0, PRECISION),
                Arguments.of(2.0, PRECISION)
        ).filter(args -> Math.abs(Math.cos((Double) args.get()[0])) > 1e-9); // Исключаем точки где cos(x) = 0
    }
}
