package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Courier implements Runnable {
    public final int size;
    private final Storage storage;

    public Courier(Storage storage) {
        this.size = new Random().nextInt(20) + 5;
        this.storage = storage;
    }

    @Override
    public void run() {
        List<Order> orders;
        while (!(orders = storage.take(size)).isEmpty()) {
            for (var order : orders) {
                try {
                    Thread.sleep(order.size * 1500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("[%d][Delivered]\n", order.number);
            }
        }

        System.out.println("Courier has finished his workday");
    }
}
