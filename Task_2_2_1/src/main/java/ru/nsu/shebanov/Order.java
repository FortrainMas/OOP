package ru.nsu.shebanov;

/**
 * Order class.
 */
public class Order {
    public final int number;
    public final int size;

    /**
     * Order constructor.
     *
     * @param number id for an order
     * @param size size of order
     */
    public Order(int number, int size) {
        this.number = number;
        this.size = size;
    }
}
