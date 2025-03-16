package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

/**
 * List of synchronized cooked non-delivered orders.
 */
public class Storage {
    private final List<Order> ordersList = new ArrayList<>();
    private boolean shiftFinished = false;
    private final int storageCapacity;
    private int currentSize = 0;

    /**
     * Creates storage with capacity.
     *
     * @param capacity capacity of the storage
     */
    public Storage(int capacity) {
        this.storageCapacity = capacity;
    }

    /**
     * Add cooked order to the storage.
     *
     * @param order order
     * @return return false is storage is closed, true, otherwise
     */
    public synchronized boolean add(Order order) {
        while ((currentSize + order.size > storageCapacity) && !shiftFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        if (shiftFinished) {
            return false;
        }

        ordersList.add(order);
        currentSize += order.size;

        System.out.printf("[%d][Added to storage]\n", order.number);

        notifyAll();
        return true;
    }

    /**
     * Grants list of orders to the courier.
     *
     * @param maxSize maximum accumulated size of orders
     * @return list of orders that total less than the maxSize
     */
    public synchronized List<Order> take(int maxSize) {
        while (ordersList.isEmpty() && !shiftFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ArrayList<>();
            }
        }

        if (shiftFinished) {
            return new ArrayList<>();
        }

        List<Order> takenOrders = new ArrayList<>();
        int takenSize = 0;

        for (var order : ordersList) {
            if (order.size + takenSize <= maxSize) {
                takenOrders.add(order);
                takenSize += order.size;
                System.out.printf("[%d][Granted to the courier]\n", order.number);
            }
        }

        ordersList.removeAll(takenOrders);

        currentSize -= takenSize;
        notifyAll();
        return takenOrders;
    }

    /**
     * Closes the storage.
     * After call to closeStorage no more orders can be added or taken
     */
    public synchronized void closeStorage() {
        shiftFinished = true;
        notifyAll();
    }
}
