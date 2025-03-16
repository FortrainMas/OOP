package ru.nsu.shebanov;

/**
 * Class for thread simulating cook.
 */
public class Cook implements Runnable {
    private final Orders orders;
    private final Storage storage;

    /**
     * Constructor for cook.
     *
     * @param orders list of orders
     * @param storage storage to save cooked orders
     */
    public Cook(Orders orders, Storage storage) {
        this.orders = orders;
        this.storage = storage;
    }

    /**
     * Thread behaviour.
     */
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
