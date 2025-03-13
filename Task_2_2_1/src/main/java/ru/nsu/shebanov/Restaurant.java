package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    List<Thread> couriers = new ArrayList<>();
    List<Thread> cooks = new ArrayList<>();
    Orders orders;
    Storage storage;
    int ordersCount = 0;
    int courierCapacity = 0;

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

    public synchronized void createOrder(int amount) {
        while (amount > 0) {
            int orderSize = Math.min(amount, courierCapacity);
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

    public synchronized void closeOrders() {
        orders.closeOrders();
    }

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
