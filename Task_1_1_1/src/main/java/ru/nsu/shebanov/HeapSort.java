package ru.nsu.shebanov;

/**
 * Sample class to simulate 1.1 task functionality
 */
public class HeapSort {
    private static void heapify(int[] arr, int N, int i){
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < N && arr[l] > arr[largest]) largest = l;
        if (r < N && arr[r] > arr[largest]) largest = r;

        if (largest != i) {
            int tmp = arr[largest];
            arr[largest] = arr[i];
            arr[i] = tmp;

            heapify(arr, N, largest);
        }

    }

    public static int[] sort(int[] arr){
        int N = arr.length;

        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(arr, N, i);

        for (int i = N - 1; i > 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            heapify(arr, i, 0);
        }

        return arr;

    }
}