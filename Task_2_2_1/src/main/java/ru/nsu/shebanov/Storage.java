package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final List<Order> ordersList = new ArrayList<>();
    private boolean shiftFinished = false;
    private final int maxSize;
    private int currentSize = 0;

    public Storage(int T) {
        this.maxSize = T;
    }

    public synchronized boolean add(Order order) {
        while ((currentSize + order.size > maxSize) && !shiftFinished) {
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

    public synchronized List<Order> take(int maxAmount) {
        while (ordersList.isEmpty() && !shiftFinished) {
            try {
                System.out.println(maxAmount);
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ArrayList<>();
            }
        }

        if (shiftFinished && ordersList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Order> takenOrders = new ArrayList<>();
        int takenSize = 0;

        while (!ordersList.isEmpty() && takenSize + ordersList.get(0).size <= maxAmount) {
            Order order = ordersList.remove(0);
            takenSize += order.size;
            takenOrders.add(order);
            System.out.printf("[%d][Granted to the courier]\n", order.number);
        }

        currentSize -= takenSize;
        notifyAll();
        return takenOrders;
    }

    public synchronized void closeStorage() {
        shiftFinished = true;
        notifyAll();
    }
}
