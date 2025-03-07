package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Orders {
  private final List<Order> ordersList = new ArrayList<>();
  private boolean shiftFinished = false;
  private boolean closedOrders = false;

  public synchronized void add(Order order) {
    if (closedOrders) {
      throw new IllegalStateException(
          "[" + order.number + "][Order decline]");
    }

    ordersList.add(order);
    notifyAll();

    System.out.printf("[%d][Added to orders]\n", order.number);
  }

  public synchronized Order take() {
    while (ordersList.isEmpty() && !shiftFinished) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return null;
      }
    }

    if (shiftFinished && ordersList.isEmpty()) {
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
