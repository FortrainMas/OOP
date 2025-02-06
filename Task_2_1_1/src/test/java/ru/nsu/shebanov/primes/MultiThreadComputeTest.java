package ru.nsu.shebanov.primes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiThreadComputeTest {
    private final MultiThreadCompute mtc = new MultiThreadCompute();



    @Test
    void simplePositiveTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

        mtc.setList(list);
        boolean res = mtc.compute();

        assertTrue(res);
    }

    @Test
    void simpleNegativeTest() {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);

        mtc.setList(list);
        boolean res = mtc.compute();

        assertFalse(res);
    }



    @Test
    void emptyListTest() {
        List<Integer> list = new ArrayList<>();

        mtc.setList(list);
        boolean res = mtc.compute();

        assertFalse(res);
    }

    @Test
    void oneElementListTest() {
        List<Integer> list;
        boolean res;

        //Positive case
        list = List.of(7);
        mtc.setList(list);
        res = mtc.compute();
        assertFalse(res);

        //Negative case
        list = List.of(4);
        mtc.setList(list);
        res = mtc.compute();
        assertTrue(res);

    }

    @Test
    void squareElementList() {
        List<Integer> list = Arrays.asList(7, 49, 3);

        mtc.setList(list);
        boolean res = mtc.compute();

        assertTrue(res);
    }

    @Test
    void one() {
        List<Integer> list = List.of(1);
        mtc.setList(list);
        boolean res = mtc.compute();

        assertFalse(res);
    }
}
