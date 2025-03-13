package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

/**
 * List of synchronized orders.
 */
public class Orders {
    private final List<Order> ordersList = new ArrayList<>();
    private boolean shiftFinished = false;
    private boolean closedOrders = false;

    /**
     * Add an order to the list.
     *
     * @param order order
     */
    public synchronized void add(Order order) {
        if (closedOrders) {
            throw new IllegalStateException("[" + order.number + "][Order decline]");
        }

        ordersList.add(order);
        notifyAll();

        System.out.printf("[%d][Added to orders]\n", order.number);
    }

    /**
     * Get one order from the list.
     *
     * @return random order
     */
    public synchronized Order take() {
        while (ordersList.isEmpty() && !shiftFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        if (shiftFinished) {
            System.out.print("Shift finished\n");
            return null;
        }

        Order order = ordersList.remove(0);
        notifyAll();
        System.out.printf("[%d][Taken]\n", order.number);
        return order;
    }

    public synchronized void closeShift() {
        shiftFinished = true;
        notifyAll();
    }

    public synchronized void closeOrders() {
        closedOrders = true;
        notifyAll();
    }
}
