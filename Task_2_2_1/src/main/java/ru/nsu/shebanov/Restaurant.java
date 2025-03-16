package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for simulating restaurant behaviour.
 */
public class Restaurant {
    List<Thread> couriers = new ArrayList<>();
    List<Thread> cooks = new ArrayList<>();
    Orders orders;
    Storage storage;
    int ordersCount = 0;
    int courierCapacity = 0;

    /**
     * Constructor for restaurant.
     *
     * @param numCooks number of cooks
     * @param numCouriers number of couriers
     * @param storageSize size of storage
     */
    public Restaurant(int numCooks, int numCouriers, int storageSize) {
        orders = new Orders();
        storage = new Storage(storageSize);

        for (int i = 0; i < numCooks; i++) {
            cooks.add(new Thread(new Cook(orders, storage)));
            cooks.get(cooks.size() - 1).start();
        }

        for (int i = 0; i < numCouriers; i++) {
            Courier courier = new Courier(storage);
            courierCapacity = Math.max(courierCapacity, courier.size);
            couriers.add(new Thread(courier));
            couriers.get(couriers.size() - 1).start();
        }
    }

    /**
     * Create an order in the restaurant.
     * Possibly order may be split into multiple orders.
     * Such behaviour depends on the maximum delivery size.
     *
     * @param amount amount of pizzas in the order
     */
    public synchronized void createOrder(int amount) {
        while (amount > 0) {
            int orderSize = 1;
            Order order = new Order(ordersCount, orderSize);
            amount -= orderSize;
            ordersCount += 1;
            try {
                orders.add(order);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Close orders in the restaurant.
     * After call to closeOrders() no orders may be created
     */
    public synchronized void closeOrders() {
        orders.closeOrders();
    }

    /**
     * Close all operation on storage and orders.
     * No more orders can be taken by couriers or cooks
     */
    public synchronized void closeShift() {
        orders.closeShift();
        storage.closeStorage();

        for (var cook : cooks) {
            try {
                cook.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (var courier : couriers) {
            try {
                courier.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
