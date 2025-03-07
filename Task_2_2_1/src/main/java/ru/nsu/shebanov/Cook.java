package ru.nsu.shebanov;

public class Cook implements Runnable {
    private final Orders orders;
    private final Storage storage;

    public Cook(Orders orders, Storage storage) {
        this.orders = orders;
        this.storage = storage;
    }

    @Override
    public void run() {
        Order order;
        while ((order = orders.take()) != null) {
            try {
                Thread.sleep(order.size * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("[%d][Cooked]\n", order.number);
            boolean isAdded = storage.add(order);
            if (!isAdded) {
                break;
            }
        }

        System.out.println("Cook has gone home!");
    }
}
