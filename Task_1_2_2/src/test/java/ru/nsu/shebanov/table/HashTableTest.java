package ru.nsu.shebanov.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void basicOperationsTest() {
        HashTable<String, Integer> ht = new HashTable<>();

        ht.put("ten", 10);
        ht.put("one", 1);
        ht.put("two", 2);

        assertTrue(ht.contains("one"));
        assertTrue(ht.contains("two"));
        assertTrue(ht.contains("ten"));
        assertFalse(ht.contains("three"));

        ht.remove("one");
        assertFalse(ht.contains("one"));

        assertEquals(ht.get("ten"), 10);
    }

    @Test
    void checkResizing() {
        HashTable<String, Integer> ht = new HashTable<>(1, 2);

        ht.put("ten", 10);
        ht.put("one", 1);
        ht.put("two", 3);
        ht.put("two", 2);

        assertTrue(ht.contains("one"));
        assertTrue(ht.contains("two"));
        assertTrue(ht.contains("ten"));
        assertFalse(ht.contains("three"));

        ht.remove("one");
        assertFalse(ht.contains("one"));

        assertEquals(ht.get("ten"), 10);
    }

    @Test
    void checkUpdate() {
        HashTable<String, Integer> ht = new HashTable<>();

        ht.put("ten", 11);
        ht.update("ten", 10);

        assertEquals(ht.get("ten"), 10);
    }

    @Test
    void checkKeyNotFoundExceptions() {
        HashTable<String, Integer> ht = new HashTable<>();

        try {
            ht.get("one");
            fail();
        } catch (NoSuchElementException e1) {
            try {
                ht.update("one", 1);
                fail();
            } catch (NoSuchElementException e2) {
                assertTrue(true);
            }
        }
    }

    @Test
    void checkEquality() {
        HashTable<String, Integer> ht1 = new HashTable<>();
        ht1.put("one", 1);
        ht1.put("two", 2);
        ht1.put("three", 3);

        HashTable<String, Integer> ht2 = new HashTable<>();
        ht2.put("one", 1);
        ht2.put("two", 2);
        ht2.put("three", 3);

        assertEquals(ht1, ht2);

        ht2.put("four", 4);
        assertNotEquals(ht1, ht2);

        HashTable<Integer, Integer> ht3 = new HashTable<>();
        assertNotEquals(ht1, ht3);
    }

    @Test
    void checkToString() {
        HashTable<String, Integer> ht = new HashTable<>();

        ht.put("ten", 10);
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("four", 4);
        ht.put("five", 6);
        ht.update("five", 5);

        String expected =
                "{\n"
                        + "\tfour : 4\n"
                        + "\tone : 1\n"
                        + "\ttwo : 2\n"
                        + "\tfive : 5\n"
                        + "\tten : 10\n"
                        + "}";
        String actual = ht.toString();

        assertEquals(actual, expected);
    }

    @Test
    void iteratorTest() {
        HashTable<String, Integer> ht = new HashTable<>();

        ht.put("ten", 10);
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("four", 4);
        ht.put("five", 6);
        ht.update("five", 5);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> pare : ht) {
            sb.append(pare.getKey()).append(" : ").append(pare.getValue()).append("\n");
        }

        String expected = "four : 4\n" + "one : 1\n" + "two : 2\n" + "five : 5\n" + "ten : 10\n";
        assertEquals(sb.toString(), expected);
    }

    @Test
    void iterationAssert() {
        HashTable<String, Integer> ht = new HashTable<>();

        ht.put("ten", 10);
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("four", 4);
        ht.put("five", 6);
        ht.update("five", 5);

        boolean rightException = false;
        try {
            for (Map.Entry<String, Integer> pare : ht) {
                ht.put("six", 6);
            }
            fail();
        } catch (ConcurrentModificationException e) {
            rightException = true;
        }

        assertTrue(rightException);
    }
}
