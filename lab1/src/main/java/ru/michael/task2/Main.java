package ru.michael.task2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 3, 8, 1, 9, 2, 7, 4, 6};
        System.out.println(Arrays.toString(array));
        HeapSort.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
