package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final List<Order> ordersList = new ArrayList<>();
    private boolean shiftFinished = false;
    private final int storageCapacity;
    private int currentSize = 0;

    public Storage(int T) {
        this.storageCapacity = T;
    }

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

    public synchronized void closeStorage() {
        shiftFinished = true;
        notifyAll();
    }
}
