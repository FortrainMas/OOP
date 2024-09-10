package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void checkMain() {
        HeapSort.main(new String[] {});
        assertTrue(true);
    }

    @Test
    void checkPrinting() {
        HeapSort.printHelloWorld();
    }

    @Test
    void doINeedToCheckDefaultConstructor() {
        final var sampleInstance = new HeapSort();
    }
}