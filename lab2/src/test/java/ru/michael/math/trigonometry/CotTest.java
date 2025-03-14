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

public class CotTest {

    private static final double PRECISION = 1e-10;

    @Test
    @DisplayName("Тест с моком для проверки базового сценария")
    void cotShouldUseCosAndSin() {
        try (MockedStatic<SinFunction> mockedSin = mockStatic(SinFunction.class)) {
            // Настраиваем моки для связанных вызовов
            double x = PI/4;

            // Мок для sin(x)
            mockedSin.when(() -> SinFunction.sin(x, PRECISION))
                    .thenReturn(0.7071067812);

            // Мок для sin(x + PI/2) который используется в cos(x)
            mockedSin.when(() -> SinFunction.sin(x + PI/2, PRECISION))
                    .thenReturn(0.7071067812);

            // Вызываем тестируемый метод
            double result = CotFunction.cot(x, PRECISION);

            // Проверяем результат и вызовы
            assertEquals(1.0, result, PRECISION);
            mockedSin.verify(() -> SinFunction.sin(x, PRECISION));
            mockedSin.verify(() -> SinFunction.sin(x + PI/2, PRECISION));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Параметризованный тест для проверки разных значений")
    void cotShouldCallSinWithCorrectParameters(double x, double epsilon) {
        try (MockedStatic<SinFunction> mockedSin = mockStatic(SinFunction.class)) {
            // Генерируем случайные значения для моков
            double mockSinX = Math.random() + 0.1;
            double mockCosX = Math.random();

            // Настраиваем моки
            mockedSin.when(() -> SinFunction.sin(x, epsilon)).thenReturn(mockSinX);
            mockedSin.when(() -> SinFunction.sin(x + PI / 2, epsilon)).thenReturn(mockCosX);

            // Вычисляем ожидаемый результат
            double expected = mockCosX / mockSinX;
            double actual = CotFunction.cot(x, epsilon);

            // Проверяем результат и вызовы
            assertEquals(expected, actual, PRECISION);
            mockedSin.verify(() -> SinFunction.sin(x, epsilon));
            mockedSin.verify(() -> SinFunction.sin(x + PI/2, epsilon));
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Тест для проверки соответствия математической формуле (без мока)")
    void cotShouldMatchMathFormula(double x, double epsilon) {
        double actual = CotFunction.cot(x, epsilon);
        double expected = Math.cos(x) / Math.sin(x);
        assertEquals(expected, actual, epsilon);
    }

    @Test
    @DisplayName("Проверка нелегальных значений")
    void secShouldThrowIllegalArgumentException() {
        assertThrows(ArithmeticException.class, () -> CotFunction.cot(0, PRECISION));
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(PI/6, PRECISION),
                Arguments.of(PI/4, PRECISION),
                Arguments.of(PI/3, PRECISION),
                Arguments.of(3*PI/4, PRECISION),
                Arguments.of(0.1, PRECISION),
                Arguments.of(1.0, PRECISION),
                Arguments.of(2.0, PRECISION),
                Arguments.of(5*PI/6, PRECISION)
        ).filter(args -> Math.abs(Math.sin((Double) args.get()[0])) > 1e-9); // исключаем точки где sin(x)=0
    }
}
