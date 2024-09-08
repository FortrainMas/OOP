package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {
    @Test
    void checkSort(){
        int [] arr = {2,3,1,4,53,3};
        int [] expected = {1,2,3,3,4,53};
        int [] actual = HeapSort.sort(arr);
        assertArrayEquals(actual, expected);
    }

    @Test
    void checkEmpty(){
        int [] arr = {};
        int [] expected = {};
        int [] actual = HeapSort.sort(arr);
        assertArrayEquals(actual, expected);
    }

    @Test
    void checkOneElement(){
        int [] arr = {2};
        int [] expected = {2};
        int [] actual = HeapSort.sort(arr);
        assertArrayEquals(actual, expected);
    }

    @Test
    void checkNegative(){
        int [] arr = {-3, 2, -3, 9, 10};
        int [] expected = {-3, -3, 2, 9, 10};
        int [] actual = HeapSort.sort(arr);
        assertArrayEquals(actual, expected);
    }

    @Test
    void checkSorted(){
        int [] arr = {1, 2, 3, 4, 5};
        int [] expected = {1,2,3,4,5};
        int [] actual = HeapSort.sort(arr);
        assertArrayEquals(actual, expected);
    }

    @Test
    void checkLarge(){
        Random random = new Random();
        int[] arr = new int[10000000];
        int[] expected = new int[10000000];

        for (int i = 0; i < arr.length; i++) {
            int val = random.nextInt();
            arr[i] = val;
            expected[i] = val;
        }
        Arrays.sort(expected);

        int [] actual = HeapSort.sort(arr);

        assertArrayEquals(actual, expected);
    }
}