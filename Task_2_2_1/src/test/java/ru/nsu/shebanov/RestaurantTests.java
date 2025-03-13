package ru.nsu.shebanov;

import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Test;

public class RestaurantTests {
    @Test
    void simpleTest() throws InterruptedException {
        Restaurant rest = ConfigLoader.loadConfig();

        for (int i = 0; i < 8; i++) {
            rest.createOrder(i);
            sleep(2000);
        }

        sleep(2000);
        rest.closeOrders();
        rest.createOrder(5);
        sleep(500);
        rest.closeShift();
    }

    @Test
    void orderSplit() throws InterruptedException {
        Restaurant rest = new Restaurant(5, 1, 30);
        rest.createOrder(100);

        sleep(70000);
        rest.closeOrders();
        rest.createOrder(5);
        sleep(500);
        rest.closeShift();
    }
}
