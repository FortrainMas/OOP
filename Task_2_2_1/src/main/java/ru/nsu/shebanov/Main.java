package ru.nsu.shebanov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Restaurant rest = new Restaurant(3, 1, 10);

        for(int i = 0; i < 8; i++) {

            rest.createOrder(i);
        }


        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        rest.closeOrders();
        rest.createOrder(5);

        rest.closeShift();
    }
}