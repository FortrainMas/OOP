package ru.nsu.shebanov;

/** Sample class to simulate 1.1 task functionality */
public class HeapSort {
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        if (largest != i) {
            int tmp = arr[largest];
            arr[largest] = arr[i];
            arr[i] = tmp;

            heapify(arr, n, largest);
        }
    }

    public static int[] sort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--){
          heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            heapify(arr, i, 0);
        }

        return arr;
    }
}