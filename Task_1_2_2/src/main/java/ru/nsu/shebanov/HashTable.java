package ru.nsu.shebanov;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Iterable<Entry<K, V>> {

    private int tableLength;
    private ArrayList<ArrayList<Entry<K, V>>> table;
    private int modCount = 0;

    private int collisionMaximum = 100;

    HashTable() {
        tableLength = 1000;
        table = new ArrayList<>();
        for (int i = 0; i < tableLength; i++) {
            table.add(new ArrayList<>());
        }
    }

    HashTable(int initialSize, int collisionMaximum) {
        tableLength = initialSize;
        this.collisionMaximum = collisionMaximum;
        table = new ArrayList<>();
        for (int i = 0; i < tableLength; i++) {
            table.add(new ArrayList<>());
        }
    }

    private void resize() {
        int newTableLength = tableLength * 2;
        ArrayList<ArrayList<Entry<K, V>>> newTable = new ArrayList<>();
        for (int i = 0; i < newTableLength; i++) {
            newTable.add(new ArrayList<>());
        }

        for (int i = 0; i < tableLength; i++) {
            for (Entry<K, V> pair : table.get(i)) {
                int hashCode = pair.getKey().hashCode() % newTableLength;
                newTable.get(hashCode).add(new SimpleEntry<>(pair.getKey(), pair.getValue()));
            }
        }

        tableLength = newTableLength;
        table = newTable;
    }

    public void put(K key, V value) {
        modCount += 1;
        int hashCode = key.hashCode() % tableLength;

        ArrayList<Entry<K, V>> collisionList = table.get(hashCode);
        for (Entry<K, V> pair : collisionList) {
            K curKey = pair.getKey();
            if (curKey.equals(key)) {
                pair.setValue(value);
            }
        }

        collisionList.add(new SimpleEntry<>(key, value));

        if (collisionList.size() >= collisionMaximum) {
            resize();
        }
    }

    public void remove(K key) {
        modCount += 1;
        int hashCode = key.hashCode() % tableLength;

        int entryPosition = -1;
        ArrayList<Entry<K, V>> collisionList = table.get(hashCode);
        for (int i = 0; i < collisionList.size(); i++) {
            if (collisionList.get(i).getKey().equals(key)) {
                entryPosition = i;
                break;
            }
        }

        collisionList.remove(entryPosition);
    }

    public V get(K key) {
        int hashCode = key.hashCode() % tableLength;

        ArrayList<Entry<K, V>> collisionList = table.get(hashCode);

        if (collisionList == null) {
            throw new NoSuchElementException("Key not found");
        }

        for (Entry<K, V> entry : collisionList) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        throw new NoSuchElementException("Key not found");
    }

    public boolean contains(K key) {
        int hashCode = key.hashCode() % tableLength;

        ArrayList<Entry<K, V>> collisionList = table.get(hashCode);
        for (Entry<K, V> entry : collisionList) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    public void update(K key, V value) {
        modCount += 1;
        int hashCode = key.hashCode() % tableLength;

        ArrayList<Entry<K, V>> collisionList = table.get(hashCode);
        for (Entry<K, V> pair : collisionList) {
            K curKey = pair.getKey();
            if (curKey.equals(key)) {
                pair.setValue(value);
                return;
            }
        }

        throw new NoSuchElementException("Key not found");
    }

    public ArrayList<K> keys() {
        ArrayList<K> keys = new ArrayList<>();
        for (ArrayList<Entry<K, V>> collisionList : table) {
            for (Entry<K, V> pair : collisionList) {
                keys.add(pair.getKey());
            }
        }

        return keys;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {

            private int tableIndex = 0;
            private int collisionIndex = 0;

            private final int previousMods = modCount;

            @Override
            public boolean hasNext() {
                if (previousMods != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (table.get(tableIndex).size() > collisionIndex) {
                    return true;
                }
                tableIndex += 1;
                collisionIndex = 0;
                while (tableIndex != tableLength
                        && table.get(tableIndex).size() == collisionIndex) {
                    tableIndex += 1;
                }

                return tableIndex != tableLength;
            }

            @Override
            public Entry<K, V> next() {
                if (previousMods != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (table.get(tableIndex).size() > collisionIndex) {
                    collisionIndex += 1;
                    return table.get(tableIndex).get(collisionIndex - 1);
                }
                tableIndex += 1;
                collisionIndex = 0;
                while (tableIndex != tableLength
                        && table.get(tableIndex).size() == collisionIndex) {
                    tableIndex += 1;
                }

                collisionIndex += 1;
                if (tableIndex == tableLength) {
                    throw new NoSuchElementException();
                }

                return table.get(tableIndex).get(0);
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        ArrayList<K> keysList1;
        ArrayList<K> keysList2;
        try {
            keysList1 = keys();
            keysList2 = ((HashTable<K, V>) obj).keys();
        } catch (Exception e) {
            return false;
        }

        if (!(keysList1.equals(keysList2))) {
            return false;
        }

        for (K key : keysList1) {
            if (!(get(key).equals(((HashTable<K, V>) obj).get(key)))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("{\n");

        for (ArrayList<Entry<K, V>> collisionList : table) {
            for (Entry<K, V> pair : collisionList) {
                output.append("\t")
                        .append(pair.getKey())
                        .append(" : ")
                        .append(pair.getValue())
                        .append("\n");
            }
        }

        output.append("}");

        return output.toString();
    }
}
