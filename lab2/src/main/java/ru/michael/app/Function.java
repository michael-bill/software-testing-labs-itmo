package ru.michael.app;

import static ru.michael.math.logarifm.LnFunction.ln;
import static ru.michael.math.logarifm.Log10Function.log_10;
import static ru.michael.math.logarifm.Log2Function.log_2;
import static ru.michael.math.logarifm.Log3Function.log_3;
import static ru.michael.math.logarifm.Log5Function.log_5;
import static ru.michael.math.trigonometry.CosFunction.cos;
import static ru.michael.math.trigonometry.CotFunction.cot;
import static ru.michael.math.trigonometry.SecFunction.sec;
import static ru.michael.math.trigonometry.SinFunction.sin;

public class Function {

    @SuppressWarnings("all")
    public static double f(double x, double epsilon) {
        if (x <= 0) {
            // Тригонометрическая ветка для x ≤ 0
            // Основные компоненты:
            double cosX = cos(x, epsilon);          // Косинус x
            double cotX = cot(x, epsilon);          // Котангенс x
            double secX = sec(x, epsilon);          // Секанс x
            double sinX = sin(x, epsilon);          // Синус x

            // Упрощение выражения:
            // 1. cos(x)/cos(x) = 1
            // 2. (1 + cot(x) + sec(x)) * sec(x)
            double numerator = (((1.0 + cotX) + secX) * secX);

            // Итог: (числитель) / sin(x)
            return numerator / sinX;

        } else {
            // Логарифмическая ветка для x > 0
            if (Math.abs(x - 1.0) < epsilon) {
                throw new ArithmeticException("Функция не определена при x = 1");
            }

            // Основные логарифмические компоненты:
            double log10 = log_10(x, epsilon);      // log_10(x)
            double lnVal = ln(x, epsilon);          // ln(x)
            double log5 = log_5(x, epsilon);        // log_5(x)
            double log3 = log_3(x, epsilon);        // log_3(x)
            double log2 = log_2(x, epsilon);        // log_2(x)

            // Вычисление первой части дроби:
            // Числитель: (2*log_10(x) * ln(x)) + (2*log_10(x) - ln(x))
            double partA = (2 * log10) * lnVal;     // (log_10 + log_10) * ln(x)
            double partB = (2 * log10) - lnVal;     // (log_10 + (log_10 - ln(x)))
            double numeratorFirst = partA + partB;  // Сумма partA и partB

            // Знаменатель: ln(x) - (log_5(x)/log_5(x)) = ln(x) - 1
            double denominatorFirst = lnVal - 1.0;  // log_5(x)/log_5(x) = 1

            // Первая часть выражения: (числитель) / (знаменатель)
            double firstPart = numeratorFirst / denominatorFirst;

            // Вычисление вычитаемой части:
            // (log_3(x) * log_2(x) * ln(x)) + (log_3(x)^3)^2
            double term1 = log3 * log2 * lnVal;     // Произведение трёх компонентов
            double term2 = Math.pow(log3, 6);       // (log_3(x)^3)^2 = log_3(x)^6
            double subtractPart = term1 + term2;    // Сумма term1 и term2

            // Итог: firstPart - subtractPart
            return firstPart - subtractPart;
        }
    }

}
