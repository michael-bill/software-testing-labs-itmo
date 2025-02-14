package ru.michael.task2;

public class HeapSort {

    public static void sort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Ожидался не null массив");
        }
        if (array.length <= 1) {
            return;
        }

        // Построение max-кучи
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // Извлечение элементов из кучи
        for (int i = n - 1; i > 0; i--) {
            // Перемещаем корень в конец
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Перестраиваем кучу для уменьшенного массива
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int heapSize, int rootIndex) {
        int largest = rootIndex;
        int leftChild = 2 * rootIndex + 1;
        int rightChild = 2 * rootIndex + 2;

        // Если левый потомок больше корня
        if (leftChild < heapSize && array[leftChild] > array[largest]) {
            largest = leftChild;
        }

        // Если правый потомок больше текущего наибольшего
        if (rightChild < heapSize && array[rightChild] > array[largest]) {
            largest = rightChild;
        }

        // Если наибольший элемент не корень
        if (largest != rootIndex) {
            int swap = array[rootIndex];
            array[rootIndex] = array[largest];
            array[largest] = swap;

            // Рекурсивно кучифицируем затронутое поддерево
            heapify(array, heapSize, largest);
        }
    }
}