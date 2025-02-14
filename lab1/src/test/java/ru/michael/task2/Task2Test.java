package ru.michael.task2;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class Task2Test {

    @Test
    @DisplayName("Сортировка случайного массива с 10^6 элементов")
    void longString() {
        int[] input = new int[1_000_000];
        Random random = new Random();
        for (int i = 0; i < input.length; i++) {
            input[i] = random.nextInt();
        }
        int[] expected = input.clone();
        Arrays.sort(expected);
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    @DisplayName("Сортировка отсортированного массива")
    void sortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    @DisplayName("Сортировка реверса отсортированного массива")
    void reverseSortedArray() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    @DisplayName("Сортировка пустого массива")
    void emptyArray() {
        int[] input = {};
        int[] expected = {};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    @DisplayName("Сортировка массива с 1 элементом")
    void singleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    @DisplayName("Сортировка null массива")
    void nullInput() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                ()-> HeapSort.sort(null)
        );
    }

}
